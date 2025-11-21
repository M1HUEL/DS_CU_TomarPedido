package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.util.Mongo;
import com.itson.persistencia.util.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.math.BigDecimal;
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
        for (Document doc : productosCollection.find()) {
            lista.add(documentoAProducto(doc));
        }
        return lista;
    }

    @Override
    public Producto consultar(String id) {
        try {
            Document doc = productosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return documentoAProducto(doc);
            }
        } catch (Exception e) {
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

            Document doc = new Document()
                    .append("nombre", producto.getNombre())
                    .append("ingredientes", MongoUtil.mapIngredientes(producto.getIngredientes()))
                    .append("complementos", MongoUtil.mapComplementos(producto.getComplementos()))
                    .append("extras", MongoUtil.mapExtras(producto.getExtras()))
                    .append("precio", producto.getPrecio());

            InsertOneResult result = productosCollection.insertOne(doc);

            if (result == null || result.getInsertedId() == null) {
                System.out.println("Error al insertar producto");
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
            Document updateDoc = new Document()
                    .append("nombre", producto.getNombre())
                    .append("ingredientes", MongoUtil.mapIngredientes(producto.getIngredientes()))
                    .append("complementos", MongoUtil.mapComplementos(producto.getComplementos()))
                    .append("extras", MongoUtil.mapExtras(producto.getExtras()))
                    .append("precio", producto.getPrecio());

            UpdateResult result = productosCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", updateDoc)
            );

            if (result.getMatchedCount() == 0) {
                System.out.println("No existe un producto con el ID: " + id);
                return false;
            }

            if (result.getModifiedCount() == 0) {
                System.out.println("Producto encontrado, pero no hubo cambios.");
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el producto con ID: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult result = productosCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (result.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún producto. No existe un producto con el ID: " + id);
                return false;
            }

            System.out.println("Producto eliminado correctamente: " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el producto con ID: " + id);
            return false;
        }
    }

    private Producto documentoAProducto(Document doc) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        List<Document> ingDocs = (List<Document>) doc.get("ingredientes");
        if (ingDocs != null) {
            for (Document idoc : ingDocs) {
                ingredientes.add(new Ingrediente(
                        idoc.getString("id"),
                        idoc.getString("nombre"),
                        BigDecimal.valueOf(idoc.getDouble("precio"))
                ));
            }
        }

        List<Complemento> complementos = new ArrayList<>();
        List<Document> compDocs = (List<Document>) doc.get("complementos");
        if (compDocs != null) {
            for (Document cdoc : compDocs) {
                complementos.add(new Complemento(
                        cdoc.getString("id"),
                        cdoc.getString("nombre"),
                        BigDecimal.valueOf(cdoc.getDouble("precio"))
                ));
            }
        }

        List<Extra> extras = new ArrayList<>();
        List<Document> extDocs = (List<Document>) doc.get("extras");
        if (extDocs != null) {
            for (Document edoc : extDocs) {
                extras.add(new Extra(
                        edoc.getString("id"),
                        edoc.getString("nombre"),
                        BigDecimal.valueOf(edoc.getDouble("precio"))
                ));
            }
        }

        return new Producto(
                doc.getObjectId("_id").toHexString(),
                doc.getString("nombre"),
                ingredientes,
                complementos,
                extras,
                BigDecimal.valueOf(doc.getDouble("precio"))
        );
    }
}
