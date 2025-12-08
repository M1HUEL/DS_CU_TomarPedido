package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.PagoDAO;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.persistencia.mapper.PagoMapper;
import com.itson.persistencia.util.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class PagoDAOImpl implements PagoDAO {

    private final MongoCollection<Document> coleccion;

    public PagoDAOImpl() {
        this.coleccion = Conn.getInstance().getDatabase().getCollection("pagos");
    }

    @Override
    public List<Pago> consultarTodos() throws PersistenciaException {
        try {
            List<Pago> pagos = new ArrayList<>();
            for (Document doc : coleccion.find()) {
                pagos.add(PagoMapper.fromDocument(doc));
            }
            return pagos;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando todos los pagos", e);
        }
    }

    @Override
    public Pago consultarPorId(String id) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(id))).first();
            return PagoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando pago por ID: " + id, e);
        }
    }

    @Override
    public Pago consultarPorPedidoId(String pedidoId) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("pedidoId", pedidoId)).first();
            return PagoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando pago por pedido ID: " + pedidoId, e);
        }
    }

    @Override
    public void agregar(Pago pago) throws PersistenciaException {
        try {
            Document doc = PagoMapper.toDocument(pago);
            coleccion.insertOne(doc);

            ObjectId id = doc.getObjectId("_id");
            if (id != null) {
                pago.setId(id.toHexString());
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error agregando el pago", e);
        }
    }

    @Override
    public void actualizar(String id, Pago pago) throws PersistenciaException {
        try {
            Document doc = PagoMapper.toDocument(pago);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando el pago completo", e);
        }
    }

    @Override
    public void actualizarEstado(String id, String nuevoEstado) throws PersistenciaException {
        try {
            coleccion.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    Updates.set("estado", nuevoEstado)
            );
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando el estado del pago", e);
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (Exception e) {
            throw new PersistenciaException("Error eliminando el pago", e);
        }
    }
}
