package com.itson.persistencia.dao.impl;

import com.itson.persistencia.dao.UsuarioDAO;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.dominio.UsuarioTipo;
import com.itson.persistencia.util.Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UsuarioDAOImpl implements UsuarioDAO {

    private final MongoDatabase database;
    private final MongoCollection<Document> usuariosCollection;

    public UsuarioDAOImpl() {
        this.database = Mongo.getInstance().getDatabase();
        this.usuariosCollection = database.getCollection("usuarios");
    }

    @Override
    public List<Usuario> consultarTodos() {
        List<Usuario> lista = new ArrayList<>();
        for (Document documento : usuariosCollection.find()) {
            lista.add(documentoAUsuario(documento));
        }
        return lista;
    }

    @Override
    public Usuario consultar(String id) {
        try {
            Document documento = usuariosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (documento != null) {
                return documentoAUsuario(documento);
            }
        } catch (Exception e) {
            System.out.println("_id del usuario inválido: " + id);
            return null;
        }
        return null;
    }

    @Override
    public boolean agregar(Usuario usuario) {
        try {
            if (usuario.getId() == null || usuario.getId().isEmpty()) {
                usuario.setId(new ObjectId().toHexString());
            }

            Document documento = new Document("_id", new ObjectId(usuario.getId()))
                    .append("nombre", usuario.getNombre())
                    .append("contraseña", usuario.getContraseña())
                    .append("rol", usuario.getRol().name());

            InsertOneResult resultado = usuariosCollection.insertOne(documento);

            if (resultado == null || resultado.getInsertedId() == null) {
                System.out.println("Error al agregar el usuario");
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar usuario");
            return false;
        }
    }

    @Override
    public boolean actualizar(String id, Usuario usuario) {
        try {
            Document documentoActualizado = new Document()
                    .append("nombre", usuario.getNombre())
                    .append("contraseña", usuario.getContraseña())
                    .append("rol", usuario.getRol().name());

            UpdateResult resultado = usuariosCollection.updateOne(
                    Filters.eq("_id", new ObjectId(id)),
                    new Document("$set", documentoActualizado)
            );

            if (resultado.getMatchedCount() == 0) {
                System.out.println("No existe un usuario con el _id: " + id);
                return false;
            }

            if (resultado.getModifiedCount() == 0) {
                System.out.println("Usuario encontrado pero sin cambios.");
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar usuario con _id: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            DeleteResult resultado = usuariosCollection.deleteOne(
                    Filters.eq("_id", new ObjectId(id))
            );

            if (resultado.getDeletedCount() == 0) {
                System.out.println("No se eliminó ningún usuario. No existe un usuario con el _id: " + id);
                return false;
            }

            System.out.println("Usuario eliminado correctamente: " + id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar usuario: " + id);
            return false;
        }
    }

    private Usuario documentoAUsuario(Document documento) {
        return new Usuario(
                documento.getObjectId("_id").toHexString(),
                documento.getString("nombre"),
                documento.getString("contraseña"),
                UsuarioTipo.valueOf(documento.getString("rol"))
        );
    }
}
