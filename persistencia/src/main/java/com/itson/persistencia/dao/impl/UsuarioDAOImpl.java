package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.UsuarioDAO;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Sexo;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.persistencia.mapper.UsuarioMapper;
import com.itson.persistencia.util.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UsuarioDAOImpl implements UsuarioDAO {

    private final MongoCollection<Document> coleccion;

    public UsuarioDAOImpl() {
        this.coleccion = Conn.getInstance().getDatabase().getCollection("usuarios");
    }

    @Override
    public List<Usuario> consultarTodos() throws PersistenciaException {
        try {
            List<Usuario> usuarios = new ArrayList<>();
            for (Document doc : coleccion.find()) {
                usuarios.add(UsuarioMapper.fromDocument(doc));
            }
            return usuarios;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando usuarios", e);
        }
    }

    @Override
    public List<Usuario> consultarTodosPorSexo(Sexo sexo) throws PersistenciaException {
        try {
            List<Usuario> usuarios = new ArrayList<>();
            for (Document doc : coleccion.find(Filters.eq("sexo", sexo.name()))) {
                usuarios.add(UsuarioMapper.fromDocument(doc));
            }
            return usuarios;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando por sexo", e);
        }
    }

    @Override
    public List<Usuario> consultarTodosPorRol(Rol rol) throws PersistenciaException {
        try {
            List<Usuario> usuarios = new ArrayList<>();
            for (Document doc : coleccion.find(Filters.eq("rol", rol.name()))) {
                usuarios.add(UsuarioMapper.fromDocument(doc));
            }
            return usuarios;
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando por rol", e);
        }
    }

    @Override
    public Usuario consultarPorId(String usuarioId) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(usuarioId))).first();
            return UsuarioMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando por id", e);
        }
    }

    @Override
    public Usuario consultarPorNombre(String nombre) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();
            return UsuarioMapper.fromDocument(doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error consultando por nombre", e);
        }
    }

    @Override
    public void agregar(Usuario usuario) throws PersistenciaException {
        try {
            Document doc = UsuarioMapper.toDocument(usuario);
            coleccion.insertOne(doc);

            ObjectId id = doc.getObjectId("_id");
            if (id != null) {
                usuario.setId(id.toHexString());
            }

        } catch (Exception e) {
            throw new PersistenciaException("Error agregando usuario", e);
        }
    }

    @Override
    public void actualizar(String usuarioId, Usuario usuario) throws PersistenciaException {
        try {
            Document doc = UsuarioMapper.toDocument(usuario);
            coleccion.replaceOne(Filters.eq("_id", new ObjectId(usuarioId)), doc);
        } catch (Exception e) {
            throw new PersistenciaException("Error actualizando usuario", e);
        }
    }

    @Override
    public void eliminar(String usuarioId) throws PersistenciaException {
        try {
            coleccion.deleteOne(Filters.eq("_id", new ObjectId(usuarioId)));
        } catch (Exception e) {
            throw new PersistenciaException("Error eliminando usuario", e);
        }
    }
}
