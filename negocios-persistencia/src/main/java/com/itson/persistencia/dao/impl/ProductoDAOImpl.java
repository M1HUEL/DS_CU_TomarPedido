package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.persistencia.mapper.ProductoMapper;
import com.itson.persistencia.util.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ProductoDAOImpl implements ProductoDAO {

    private final MongoCollection<Document> coleccion;

    public ProductoDAOImpl() {
        this.coleccion = Conn.getInstance().getDatabase().getCollection("productos");
    }

    @Override
    public List<Producto> consultarTodos() throws PersistenciaException {
        try {
            List<Producto> productos = new ArrayList<>();
            for (Document doc : coleccion.find()) {
                productos.add(ProductoMapper.fromDocument(doc));
            }
            return productos;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando todos los productos", e);
        }
    }

    @Override
    public Producto consultarPorId(String productoId) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(productoId))).first();
            return ProductoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando producto por id", e);
        }
    }

    @Override
    public Producto consultarPorNombre(String nombre) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();
            return ProductoMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando producto por nombre", e);
        }
    }

    @Override
    public void agregar(Producto producto) throws PersistenciaException {
        try {
            Document doc = ProductoMapper.toDocument(producto);
            coleccion.insertOne(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error agregando producto", e);
        }
    }

    @Override
    public void actualizar(String productoId, Producto producto) throws PersistenciaException {
        try {
            Document doc = ProductoMapper.toDocument(producto);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(productoId)), doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando producto", e);
        }
    }

    @Override
    public void eliminar(String productoId) throws PersistenciaException {
        try {
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(productoId)));
        } catch (Exception e) {
            throw new PersistenciaException("Error eliminando producto", e);
        }
    }
}
