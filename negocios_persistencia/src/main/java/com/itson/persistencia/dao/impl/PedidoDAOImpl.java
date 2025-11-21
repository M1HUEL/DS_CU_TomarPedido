package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Pedido;
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
        for (Document doc : pedidosCollection.find()) {
            lista.add(documentoAPedido(doc));
        }
        return lista;
    }

    @Override
    public Pedido consultar(String id) {
        try {
            Document doc = pedidosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return documentoAPedido(doc);
            }
        } catch (Exception e) {
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

            Document doc = new Document("_id", new ObjectId(pedido.getId()))
                    .append("nombre", pedido.getNombre())
                    .append("productos", productosADocumento(pedido.getProductos()))
                    .append("comentario", pedido.getComentario())
                    .append("precio", pedido.getPrecio())
                    .append("fechaPedido", java.util.Date.from(pedido.getFechaPedido().atZone(ZoneId.systemDefault()).toInstant()));

            InsertOneResult result = pedidosCollection.insertOne(doc);

            if (result == null || result.getInsertedId() == null) {
                System.out.println("Error al insertar pedido");
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
            Document updateDoc = new Document()
                    .append("nombre", pedido.getNombre())
                    .append("productos", productosADocumento(pedido.getProductos()))
                    .append("comentario", pedido.getComentario())
                    .append("precio", pedido.getPrecio())
                    .append("fechaPedido", java.util.Date.from(pedido.getFechaPedido().atZone(ZoneId.systemDefault()).toInstant()));

            UpdateResult result = pedidosCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", updateDoc)
            );

            if (result.getMatchedCount() == 0) {
                System.out.println("No existe un pedido con el ID: " + id);
                return false;
            }

            if (result.getModifiedCount() == 0) {
                System.out.println("Pedido encontrado, pero no hubo cambios.");
                return false;
            }

            System.out.println("Pedido actualizado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el pedido con ID: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult result = pedidosCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (result.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún pedido. No existe un pedido con el ID: " + id);
                return false;
            }

            System.out.println("Pedido eliminado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el pedido con ID: " + id);
            return false;
        }
    }

    private Pedido documentoAPedido(Document doc) {
        List<Producto> productos = new ArrayList<>();
        List<Document> productosDocs = (List<Document>) doc.get("productos");
        if (productosDocs != null) {
            for (Document pdoc : productosDocs) {
                List<Ingrediente> ingredientes = new ArrayList<>();
                List<Complemento> complementos = new ArrayList<>();
                List<Extra> extras = new ArrayList<>();

                List<Document> ingDocs = (List<Document>) pdoc.get("ingredientes");
                if (ingDocs != null) {
                    for (Document idoc : ingDocs) {
                        ingredientes.add(new Ingrediente(
                                idoc.getString("id"),
                                idoc.getString("nombre"),
                                BigDecimal.valueOf(idoc.getDouble("precio"))
                        ));
                    }
                }

                List<Document> compDocs = (List<Document>) pdoc.get("complementos");
                if (compDocs != null) {
                    for (Document cdoc : compDocs) {
                        complementos.add(new Complemento(
                                cdoc.getString("id"),
                                cdoc.getString("nombre"),
                                BigDecimal.valueOf(cdoc.getDouble("precio"))
                        ));
                    }
                }

                List<Document> extDocs = (List<Document>) pdoc.get("extras");
                if (extDocs != null) {
                    for (Document edoc : extDocs) {
                        extras.add(new Extra(
                                edoc.getString("id"),
                                edoc.getString("nombre"),
                                BigDecimal.valueOf(edoc.getDouble("precio"))
                        ));
                    }
                }

                productos.add(new Producto(
                        pdoc.getString("id"),
                        pdoc.getString("nombre"),
                        ingredientes,
                        complementos,
                        extras,
                        BigDecimal.valueOf(pdoc.getDouble("precio"))
                ));
            }
        }

        LocalDateTime fechaPedido = LocalDateTime.ofInstant(
                doc.getDate("fechaPedido").toInstant(),
                ZoneId.systemDefault()
        );

        return new Pedido(
                doc.getObjectId("_id").toHexString(),
                doc.getString("nombre"),
                productos,
                doc.getString("comentario"),
                BigDecimal.valueOf(doc.getDouble("precio")),
                fechaPedido
        );
    }

    private List<Document> productosADocumento(List<Producto> productos) {
        List<Document> lista = new ArrayList<>();
        if (productos != null) {
            for (Producto p : productos) {
                if (p.getId() == null || p.getId().isEmpty()) {
                    p.setId(new ObjectId().toHexString());
                }

                Document doc = new Document()
                        .append("id", p.getId())
                        .append("nombre", p.getNombre())
                        .append("ingredientes", MongoUtil.mapIngredientes(p.getIngredientes()))
                        .append("complementos", MongoUtil.mapComplementos(p.getComplementos()))
                        .append("extras", MongoUtil.mapExtras(p.getExtras()))
                        .append("precio", p.getPrecio());

                lista.add(doc);
            }
        }
        return lista;
    }
}
