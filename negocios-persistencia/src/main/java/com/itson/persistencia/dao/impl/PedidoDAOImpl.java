package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.persistencia.mapper.PedidoMapper;
import com.itson.persistencia.util.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class PedidoDAOImpl implements PedidoDAO {

    private final MongoCollection<Document> coleccion;

    public PedidoDAOImpl() {
        this.coleccion = Conn.getInstance().getDatabase().getCollection("pedidos");
    }

    @Override
    public List<Pedido> consultarTodos() throws PersistenciaException {
        try {
            List<Pedido> pedidos = new ArrayList<>();
            for (Document doc : coleccion.find()) {
                pedidos.add(PedidoMapper.fromDocument(doc));
            }
            return pedidos;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando todos los pedidos", e);
        }
    }

    @Override
    public Pedido consultarPorId(String pedidoId) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(pedidoId))).first();
            return PedidoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando pedido por id", e);
        }
    }

    @Override
    public Pedido consultarPorNombre(String nombre) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();
            return PedidoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando pedido por nombre", e);
        }
    }

    @Override
    public void agregar(Pedido pedido) throws PersistenciaException {
        try {
            Document doc = PedidoMapper.toDocument(pedido);
            coleccion.insertOne(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error agregando pedido", e);
        }
    }

    @Override
    public void actualizar(String pedidoId, Pedido pedido) throws PersistenciaException {
        try {
            Document doc = PedidoMapper.toDocument(pedido);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(pedidoId)), doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando pedido", e);
        }
    }

    @Override
    public void eliminar(String pedidoId) throws PersistenciaException {
        try {
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(pedidoId)));
        } catch (Exception e) {
            throw new PersistenciaException("Error eliminando pedido", e);
        }
    }
}
