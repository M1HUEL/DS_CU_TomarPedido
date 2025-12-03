package com.itson.negocios.usuario.servicio.impl;

import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.servicio.UsuarioServicio;
import com.itson.persistencia.dao.UsuarioDAO;
import com.itson.persistencia.dao.impl.UsuarioDAOImpl;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServicioImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public List<Usuario> obtenerUsuarios() throws UsuarioException {
        try {
            return usuarioDAO.consultarTodos();
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al obtener todos los usuarios", ex);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) throws UsuarioException {
        try {
            return usuarioDAO.consultarPorId(id);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al obtener el usuario por id: " + id, ex);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorNombre(String nombre) throws UsuarioException {
        try {
            return usuarioDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al obtener el usuario por nombre: " + nombre, ex);
        }
    }

    @Override
    public void agregarUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuarioDAO.agregar(usuario);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al agregar el usuario", ex);
        }
    }

    @Override
    public void actualizarUsuario(String id, Usuario usuario) throws UsuarioException {
        try {
            usuarioDAO.actualizar(id, usuario);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al actualizar el usuario con id: " + id, ex);
        }
    }

    @Override
    public void eliminarUsuario(String id) throws UsuarioException {
        try {
            usuarioDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al eliminar el usuario con id: " + id, ex);
        }
    }
}
