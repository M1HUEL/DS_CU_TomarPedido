package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.InventarioDAO;
import com.itson.persistencia.dominio.InventarioItem;
import com.itson.persistencia.dominio.InventarioTipo;
import com.itson.persistencia.util.Mongo;
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
        for (Document doc : inventarioCollection.find()) {
            lista.add(documentoAItem(doc));
        }
        return lista;
    }

    @Override
    public InventarioItem consultarPorId(String id) {
        try {
            Document doc = inventarioCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            return (doc != null) ? documentoAItem(doc) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InventarioItem consultarPorNombre(String nombre) {
        try {
            Document doc = inventarioCollection.find(Filters.eq("nombre", nombre)).first();
            return (doc != null) ? documentoAItem(doc) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<InventarioItem> consultarPorTipo(InventarioTipo tipo) {
        List<InventarioItem> lista = new ArrayList<>();
        for (Document doc : inventarioCollection.find(Filters.eq("tipo", tipo.name()))) {
            lista.add(documentoAItem(doc));
        }
        return lista;
    }

    @Override
    public boolean agregar(InventarioItem item) {
        try {
            ObjectId id = new ObjectId();
            item.setId(id.toHexString());

            Document doc = new Document("_id", id)
                    .append("nombre", item.getNombre())
                    .append("cantidadDisponible", item.getCantidadDisponible())
                    .append("precioUnidad", new Decimal128(item.getPrecioUnidad()))
                    .append("tipo", item.getTipo().name());

            InsertOneResult result = inventarioCollection.insertOne(doc);

            if (result == null) {
                System.out.println("La operación de inserción no devolvió resultado.");
                return false;
            }

            if (result.getInsertedId() == null) {
                System.out.println("No se generó un ID al insertar el item.");
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
            Document updateDoc = new Document()
                    .append("nombre", item.getNombre())
                    .append("cantidadDisponible", item.getCantidadDisponible())
                    .append("precioUnidad", new Decimal128(item.getPrecioUnidad()))
                    .append("tipo", item.getTipo().name());

            UpdateResult result = inventarioCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", updateDoc)
            );

            if (result.getMatchedCount() == 0) {
                System.out.println("No existe un item en inventario con el ID: " + id);
                return false;
            }

            if (result.getModifiedCount() == 0) {
                System.out.println("Item encontrado, pero no hubo cambios en los datos.");
                return false;
            }

            System.out.println("Item de inventario actualizado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el item con ID: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult result = inventarioCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (result.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún item. No existe un item con el ID: " + id);
                return false;
            }

            System.out.println("Item eliminado correctamente: " + id);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el item con ID: " + id);
            return false;
        }
    }

    private InventarioItem documentoAItem(Document doc) {
        InventarioItem item = new InventarioItem();

        item.setId(doc.getObjectId("_id").toHexString());
        item.setNombre(doc.getString("nombre"));

        item.setCantidadDisponible(
                convertirADouble(doc.get("cantidadDisponible"))
        );

        item.setPrecioUnidad(
                convertirABigDecimal(doc.get("precioUnidad"))
        );

        item.setTipo(InventarioTipo.valueOf(doc.getString("tipo")));

        return item;
    }

    private double convertirADouble(Object valor) {
        if (valor == null) {
            return 0.0;
        }

        return switch (valor) {
            case Double d ->
                d;
            case Integer i ->
                i.doubleValue();
            case Long l ->
                l.doubleValue();
            case Decimal128 d ->
                d.doubleValue();
            default ->
                throw new IllegalArgumentException(
                        "No se puede convertir a double: " + valor.getClass()
                );
        };
    }

    private BigDecimal convertirABigDecimal(Object valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }

        return switch (valor) {
            case BigDecimal b ->
                b;
            case Double d ->
                BigDecimal.valueOf(d);
            case Integer i ->
                BigDecimal.valueOf(i);
            case Long l ->
                BigDecimal.valueOf(l);
            case Decimal128 d ->
                d.bigDecimalValue();
            default ->
                throw new IllegalArgumentException(
                        "No se puede convertir a BigDecimal: " + valor.getClass()
                );
        };
    }
}
