package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.util.Mongo;
import com.itson.persistencia.util.Util;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class PedidoDAOImpl implements PedidoDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> pedidosCollection;

    public PedidoDAOImpl() {
        this.database = Mongo.getInstance().getDatabase();
        this.pedidosCollection = database.getCollection("pedidos");
    }

    @Override
    public List<Pedido> consultarTodos() {
        List<Pedido> lista = new ArrayList<>();
        for (Document documento : pedidosCollection.find()) {
            lista.add(documentoAPedido(documento));
        }
        return lista;
    }

    @Override
    public Pedido consultar(String id) {
        try {
            Document documento = pedidosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (documento != null) {
                return documentoAPedido(documento);
            }
        } catch (Exception e) {
            System.out.println("_id del pedido inválido: " + id);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean agregar(Pedido pedido) {
        try {
            if (pedido.getId() == null || pedido.getId().isEmpty()) {
                pedido.setId(new ObjectId().toHexString());
            }

            Document documento = new Document()
                    .append("_id", new ObjectId(pedido.getId()))
                    .append("nombre", pedido.getNombre())
                    .append("productos", productosADocumento(pedido.getProductos()))
                    .append("comentario", pedido.getComentario())
                    .append("precio", pedido.getPrecio())
                    .append("fechaPedido", java.util.Date.from(pedido.getFechaPedido().atZone(ZoneId.systemDefault()).toInstant()));

            InsertOneResult resultado = pedidosCollection.insertOne(documento);

            if (resultado == null || resultado.getInsertedId() == null) {
                System.out.println("Error al agregar pedido");
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(String id, Pedido pedido) {
        try {
            Document documentoActualizado = new Document()
                    .append("nombre", pedido.getNombre())
                    .append("productos", productosADocumento(pedido.getProductos()))
                    .append("comentario", pedido.getComentario())
                    .append("precio", pedido.getPrecio())
                    .append("fechaPedido", java.util.Date.from(pedido.getFechaPedido().atZone(ZoneId.systemDefault()).toInstant()));

            UpdateResult resultado = pedidosCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", documentoActualizado)
            );

            if (resultado.getMatchedCount() == 0) {
                System.out.println("No existe un pedido con el _id: " + id);
                return false;
            }

            if (resultado.getModifiedCount() == 0) {
                System.out.println("Pedido encontrado, pero no hubo cambios.");
                return false;
            }

            System.out.println("Pedido actualizado correctamente: " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el pedido con _id: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult resultado = pedidosCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (resultado.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún pedido. No existe un pedido con el _id: " + id);
                return false;
            }

            System.out.println("Pedido eliminado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el pedido con _id: " + id);
            return false;
        }
    }

    private List<Document> productosADocumento(List<Producto> productos) {
        List<Document> documentos = new ArrayList<>();
        if (productos == null) {
            return documentos;
        }

        for (Producto p : productos) {
            documentos.add(new Document()
                    .append("id", p.getId())
                    .append("nombre", p.getNombre())
                    .append("ingredientes", Util.mapIngredientes(p.getIngredientes()))
                    .append("complementos", Util.mapComplementos(p.getComplementos()))
                    .append("extras", Util.mapExtras(p.getExtras()))
                    .append("precio", p.getPrecio())
            );
        }
        return documentos;
    }

    private Pedido documentoAPedido(Document documento) {
        List<Producto> productos = new ArrayList<>();
        List<Document> productosDocs = (List<Document>) documento.get("productos");
        if (productosDocs != null) {
            for (Document pdoc : productosDocs) {
                List<Ingrediente> ingredientes = new ArrayList<>();
                List<Complemento> complementos = new ArrayList<>();
                List<Extra> extras = new ArrayList<>();

                List<Document> ingDocs = (List<Document>) pdoc.get("ingredientes");
                if (ingDocs != null) {
                    for (Document doc : ingDocs) {
                        ingredientes.add(new Ingrediente(
                                doc.getString("id"),
                                doc.getString("nombre"),
                                Util.convertirABigDecimal(doc.get("precio")),
                                doc.getString("inventarioItemId"),
                                Util.convertirADouble(doc.get("cantidadRequerida"))
                        ));
                    }
                }

                List<Document> compDocs = (List<Document>) pdoc.get("complementos");
                if (compDocs != null) {
                    for (Document doc : compDocs) {
                        complementos.add(new Complemento(
                                doc.getString("id"),
                                doc.getString("nombre"),
                                Util.convertirABigDecimal(doc.get("precio")),
                                doc.getString("inventarioItemId"),
                                Util.convertirADouble(doc.get("cantidadRequerida"))
                        ));
                    }
                }

                List<Document> extDocs = (List<Document>) pdoc.get("extras");
                if (extDocs != null) {
                    for (Document doc : extDocs) {
                        extras.add(new Extra(
                                doc.getString("id"),
                                doc.getString("nombre"),
                                Util.convertirABigDecimal(doc.get("precio")),
                                doc.getString("inventarioItemId"),
                                Util.convertirADouble(doc.get("cantidadRequerida"))
                        ));
                    }
                }

                productos.add(new Producto(
                        pdoc.getString("id"),
                        pdoc.getString("nombre"),
                        ingredientes,
                        complementos,
                        extras,
                        Util.convertirABigDecimal(pdoc.get("precio"))
                ));
            }
        }

        LocalDateTime fechaPedido = LocalDateTime.ofInstant(
                documento.getDate("fechaPedido").toInstant(),
                ZoneId.systemDefault()
        );

        return new Pedido(
                documento.getObjectId("_id").toHexString(),
                documento.getString("nombre"),
                productos,
                documento.getString("comentario"),
                Util.convertirABigDecimal(documento.get("precio")),
                fechaPedido
        );
    }
}
