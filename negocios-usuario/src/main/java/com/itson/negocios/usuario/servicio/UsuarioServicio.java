package com.itson.negocios.usuario.servicio;

import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.persistencia.dominio.Usuario;
import java.util.List;

public interface UsuarioServicio {

    List<Usuario> obtenerUsuarios() throws UsuarioException;

    Usuario obtenerUsuarioPorId(String id) throws UsuarioException;

    Usuario obtenerUsuarioPorNombre(String nombre) throws UsuarioException;

    void agregarUsuario(Usuario usuario) throws UsuarioException;

    void actualizarUsuario(String id, Usuario usuario) throws UsuarioException;

    void eliminarUsuario(String id) throws UsuarioException;
}
