package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.util.Mongo;
import com.itson.persistencia.util.Util;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ProductoDAOImpl implements ProductoDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> productosCollection;

    public ProductoDAOImpl() {
        this.database = Mongo.getInstance().getDatabase();
        this.productosCollection = database.getCollection("productos");
    }

    @Override
    public List<Producto> consultarTodos() {
        List<Producto> lista = new ArrayList<>();
        for (Document documento : productosCollection.find()) {
            lista.add(documentoAProducto(documento));
        }
        return lista;
    }

    @Override
    public Producto consultar(String id) {
        try {
            Document documento = productosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (documento != null) {
                return documentoAProducto(documento);
            }
        } catch (Exception e) {
            System.out.println("_id del producto inválido: " + id);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(Producto producto) {
        try {
            if (producto.getId() == null || producto.getId().isEmpty()) {
                producto.setId(new ObjectId().toHexString());
            }

            Document documento = new Document()
                    .append("_id", new ObjectId(producto.getId()))
                    .append("nombre", producto.getNombre())
                    .append("ingredientes", Util.mapIngredientes(producto.getIngredientes()))
                    .append("complementos", Util.mapComplementos(producto.getComplementos()))
                    .append("extras", Util.mapExtras(producto.getExtras()))
                    .append("precio", producto.getPrecio());

            InsertOneResult resultado = productosCollection.insertOne(documento);

            if (resultado == null || resultado.getInsertedId() == null) {
                System.out.println("Error al agregar el producto");
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(String id, Producto producto) {
        try {
            Document documentoActualizado = new Document()
                    .append("nombre", producto.getNombre())
                    .append("ingredientes", Util.mapIngredientes(producto.getIngredientes()))
                    .append("complementos", Util.mapComplementos(producto.getComplementos()))
                    .append("extras", Util.mapExtras(producto.getExtras()))
                    .append("precio", producto.getPrecio());

            UpdateResult result = productosCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", documentoActualizado)
            );

            if (result.getMatchedCount() == 0) {
                System.out.println("No existe un producto con el _id: " + id);
                return false;
            }

            if (result.getModifiedCount() == 0) {
                System.out.println("Producto encontrado, pero no hubo cambios.");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto con _id: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult resultado = productosCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (resultado.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún producto. No existe un producto con el _id: " + id);
                return false;
            }

            System.out.println("Producto eliminado correctamente: " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el producto con _id: " + id);
            return false;
        }
    }

    private Producto documentoAProducto(Document documento) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        List<Document> ingredienteDocumentos = (List<Document>) documento.get("ingredientes");

        if (ingredienteDocumentos != null) {
            for (Document doc : ingredienteDocumentos) {
                ingredientes.add(new Ingrediente(
                        doc.getString("id"),
                        doc.getString("nombre"),
                        Util.convertirABigDecimal(doc.get("precio")),
                        doc.getString("inventarioItemId"),
                        Util.convertirADouble(doc.get("cantidadRequerida"))
                ));
            }
        }

        List<Complemento> complementos = new ArrayList<>();
        List<Document> complementoDocumentos = (List<Document>) documento.get("complementos");

        if (complementoDocumentos != null) {
            for (Document doc : complementoDocumentos) {
                complementos.add(new Complemento(
                        doc.getString("id"),
                        doc.getString("nombre"),
                        Util.convertirABigDecimal(doc.get("precio")),
                        doc.getString("inventarioItemId"),
                        Util.convertirADouble(doc.get("cantidadRequerida"))
                ));
            }
        }

        List<Extra> extras = new ArrayList<>();
        List<Document> extraDocumentos = (List<Document>) documento.get("extras");

        if (extraDocumentos != null) {
            for (Document doc : extraDocumentos) {
                extras.add(new Extra(
                        doc.getString("id"),
                        doc.getString("nombre"),
                        Util.convertirABigDecimal(doc.get("precio")),
                        doc.getString("inventarioItemId"),
                        Util.convertirADouble(doc.get("cantidadRequerida"))
                ));
            }
        }

        return new Producto(
                documento.getObjectId("_id").toHexString(),
                documento.getString("nombre"),
                ingredientes,
                complementos,
                extras,
                Util.convertirABigDecimal(documento.get("precio"))
        );
    }
}
