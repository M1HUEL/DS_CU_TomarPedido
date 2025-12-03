package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.InsumoDAO;
import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.persistencia.mapper.InsumoMapper;
import com.itson.persistencia.util.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class InsumoDAOImpl implements InsumoDAO {

    private final MongoCollection<Document> coleccion;

    public InsumoDAOImpl() {
        this.coleccion = Conn.getInstance().getDatabase().getCollection("insumos");
    }

    @Override
    public List<Insumo> consultarTodos() throws PersistenciaException {
        try {
            List<Insumo> insumos = new ArrayList<>();
            for (Document doc : coleccion.find()) {
                insumos.add(InsumoMapper.fromDocument(doc));
            }
            return insumos;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando todos los insumos", e);
        }
    }

    @Override
    public Insumo consultarPorId(String id) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(id))).first();
            return InsumoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando insumo por id", e);
        }
    }

    @Override
    public Insumo consultarPorCodigo(String codigo) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("codigo", codigo)).first();
            return InsumoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando insumo por código", e);
        }
    }

    @Override
    public void agregar(Insumo insumo) throws PersistenciaException {
        try {
            Document doc = InsumoMapper.toDocument(insumo);
            coleccion.insertOne(doc);

            ObjectId id = doc.getObjectId("_id");
            if (id != null) {
                insumo.setId(id.toHexString());
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error agregando insumo", e);
        }
    }

    @Override
    public void actualizar(String id, Insumo insumo) throws PersistenciaException {
        try {
            Document doc = InsumoMapper.toDocument(insumo);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando insumo", e);
        }
    }

    @Override
    public void eliminar(String id) throws PersistenciaException {
        try {
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
        } catch (Exception e) {
            throw new PersistenciaException("Error eliminando insumo", e);
        }
    }

    @Override
    public void aumentarStock(String id, Double cantidad) throws PersistenciaException {
        try {
            // $inc incrementa el valor numérico existente
            coleccion.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.inc("stockActual", cantidad));
        } catch (Exception e) {
            throw new PersistenciaException("Error aumentando stock", e);
        }
    }

    @Override
    public boolean descontarStock(String id, Double cantidad) throws PersistenciaException {
        try {
            // Filtro de seguridad: Coincidir ID Y que el stock sea mayor o igual (gte) a lo requerido
            // Esto evita que el inventario quede en números negativos
            UpdateResult resultado = coleccion.updateOne(
                    Filters.and(
                            Filters.eq("_id", new ObjectId(id)),
                            Filters.gte("stockActual", cantidad)
                    ),
                    Updates.inc("stockActual", -cantidad) // Restamos usando negativo
            );

            // Si se modificó al menos 1 documento, significa que había stock y se descontó
            if (resultado.getModifiedCount() > 0) {
                return true;
            } else {
                return false; // No había suficiente stock
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error descontando stock", e);
        }
    }
}
