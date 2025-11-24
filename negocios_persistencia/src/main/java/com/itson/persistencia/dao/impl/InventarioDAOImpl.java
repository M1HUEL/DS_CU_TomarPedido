package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.InventarioDAO;
import com.itson.persistencia.dominio.InventarioItem;
import com.itson.persistencia.dominio.InventarioTipo;
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
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public class InventarioDAOImpl implements InventarioDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> inventarioCollection;

    public InventarioDAOImpl() {
        this.database = Mongo.getInstance().getDatabase();
        this.inventarioCollection = database.getCollection("inventario");
    }

    @Override
    public List<InventarioItem> consultarTodos() {
        List<InventarioItem> lista = new ArrayList<>();
        for (Document documento : inventarioCollection.find()) {
            lista.add(documentoAItem(documento));
        }
        return lista;
    }

    @Override
    public InventarioItem consultarPorId(String id) {
        try {
            Document documento = inventarioCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (documento != null) {
                return documentoAItem(documento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public InventarioItem consultarPorNombre(String nombre) {
        try {
            Document documento = inventarioCollection.find(Filters.eq("nombre", nombre)).first();
            if (documento != null) {
                return documentoAItem(documento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<InventarioItem> consultarPorTipo(InventarioTipo tipo) {
        List<InventarioItem> lista = new ArrayList<>();
        for (Document documento : inventarioCollection.find(Filters.eq("tipo", tipo.name()))) {
            lista.add(documentoAItem(documento));
        }
        return lista;
    }

    @Override
    public boolean agregar(InventarioItem item) {
        try {
            if (item.getId() == null || item.getId().isEmpty()) {
                item.setId(new ObjectId().toHexString());
            }

            Document documento = new Document()
                    .append("_id", new ObjectId(item.getId()))
                    .append("nombre", item.getNombre())
                    .append("cantidadDisponible", item.getCantidadDisponible())
                    .append("precioUnidad", new Decimal128(item.getPrecioUnidad()))
                    .append("tipo", item.getTipo().name());

            InsertOneResult resultado = inventarioCollection.insertOne(documento);

            if (resultado == null || resultado.getInsertedId() == null) {
                System.out.println("Error al agregar el item al inventario");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(String id, InventarioItem item) {
        try {
            Document documentoActualizado = new Document()
                    .append("nombre", item.getNombre())
                    .append("cantidadDisponible", item.getCantidadDisponible())
                    .append("precioUnidad", new Decimal128(item.getPrecioUnidad()))
                    .append("tipo", item.getTipo().name());

            UpdateResult resultado = inventarioCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", documentoActualizado)
            );

            if (resultado.getMatchedCount() == 0) {
                System.out.println("No existe un item en inventario con el _id: " + id);
                return false;
            }

            if (resultado.getModifiedCount() == 0) {
                System.out.println("Item encontrado, pero no hubo cambios en los datos.");
                return false;
            }

            System.out.println("Item de inventario actualizado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el item con _id: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult resultado = inventarioCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (resultado.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún item. No existe un item con el _id: " + id);
                return false;
            }

            System.out.println("Item eliminado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el item con _id: " + id);
            return false;
        }
    }

    private InventarioItem documentoAItem(Document documento) {
        InventarioItem item = new InventarioItem();
        item.setId(documento.getObjectId("_id").toHexString());
        item.setNombre(documento.getString("nombre"));
        item.setCantidadDisponible(
                Util.convertirADouble(documento.get("cantidadDisponible"))
        );
        item.setPrecioUnidad(
                Util.convertirABigDecimal(documento.get("precioUnidad"))
        );
        item.setTipo(InventarioTipo.valueOf(documento.getString("tipo")));
        return item;
    }

}
