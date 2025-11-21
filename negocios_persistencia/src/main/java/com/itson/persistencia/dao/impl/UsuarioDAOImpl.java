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
        for (Document doc : usuariosCollection.find()) {
            lista.add(documentoAUsuario(doc));
        }
        return lista;
    }

    @Override
    public Usuario consultar(String id) {
        try {
            ObjectId oid = new ObjectId(id);

            Document doc = usuariosCollection
                    .find(Filters.eq("_id", oid))
                    .first();

            return doc != null ? documentoAUsuario(doc) : null;

        } catch (Exception e) {
            System.out.println("ID de usuario inválido: " + id);
            return null;
        }
    }

    @Override
    public boolean agregar(Usuario usuario) {
        try {
            if (usuario.getId() == null || usuario.getId().isEmpty()) {
                usuario.setId(new ObjectId().toHexString());
            }

            Document doc = new Document("_id", new ObjectId(usuario.getId()))
                    .append("nombre", usuario.getNombre())
                    .append("contraseña", usuario.getContraseña())
                    .append("rol", usuario.getRol().name());

            InsertOneResult result = usuariosCollection.insertOne(doc);

            return result != null && result.getInsertedId() != null;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar usuario");
            return false;
        }
    }

    @Override
    public boolean actualizar(String id, Usuario usuario) {
        try {
            ObjectId oid = new ObjectId(id);

            Document update = new Document()
                    .append("nombre", usuario.getNombre())
                    .append("contraseña", usuario.getContraseña())
                    .append("rol", usuario.getRol().name());

            UpdateResult result = usuariosCollection.updateOne(
                    Filters.eq("_id", oid),
                    new Document("$set", update)
            );

            if (result.getMatchedCount() == 0) {
                System.out.println("Usuario no encontrado: " + id);
                return false;
            }

            if (result.getModifiedCount() == 0) {
                System.out.println("Usuario encontrado pero sin cambios.");
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar usuario con ID: " + id);
            return false;
        }
    }

    @Override
    public boolean eliminar(String id) {
        try {
            ObjectId oid = new ObjectId(id);

            DeleteResult result = usuariosCollection.deleteOne(
                    Filters.eq("_id", oid)
            );

            return result.getDeletedCount() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar usuario: " + id);
            return false;
        }
    }

    private Usuario documentoAUsuario(Document doc) {
        return new Usuario(
                doc.getObjectId("_id").toHexString(),
                doc.getString("nombre"),
                doc.getString("contraseña"),
                UsuarioTipo.valueOf(doc.getString("rol"))
        );
    }
}
