package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Sexo;
import com.itson.persistencia.dominio.Usuario;
import org.bson.Document;

public class UsuarioMapper {

    public static Usuario fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Usuario usuario = new Usuario();

        if (doc.getObjectId("_id") != null) {
            usuario.setId(doc.getObjectId("_id").toHexString());
        }

        usuario.setNombre(doc.getString("nombre"));
        usuario.setContrasena(doc.getString("contrasena"));

        try {
            String sexoStr = doc.getString("sexo");

            if (sexoStr != null) {
                usuario.setSexo(Sexo.valueOf(sexoStr));
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error mapeando Sexo: " + e.getMessage());
        }

        try {
            String rolStr = doc.getString("rol");

            if (rolStr != null) {
                usuario.setRol(Rol.valueOf(rolStr));
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error mapeando Rol: " + e.getMessage());
        }

        return usuario;
    }

    public static Document toDocument(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        Document doc = new Document();

        doc.append("nombre", usuario.getNombre());
        doc.append("contrasena", usuario.getContrasena());
        doc.append("sexo", usuario.getSexo() != null ? usuario.getSexo().name() : null);
        doc.append("rol", usuario.getRol() != null ? usuario.getRol().name() : null);

        return doc;
    }
}
