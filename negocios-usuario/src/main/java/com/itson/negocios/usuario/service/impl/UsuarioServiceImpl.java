package com.itson.negocios.usuario.service.impl;

import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.service.UsuarioService;
import com.itson.negocios.usuario.validator.UsuarioValidator;
import com.itson.persistencia.dao.UsuarioDAO;
import com.itson.persistencia.dao.impl.UsuarioDAOImpl;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioValidator validador;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.validador = new UsuarioValidator(this.usuarioDAO);
    }

    @Override
    public List<Usuario> obtenerUsuarios() throws UsuarioException {
        try {
            return usuarioDAO.consultarTodos();
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al obtener el catálogo de usuarios.", ex);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) throws UsuarioException {
        try {
            return usuarioDAO.consultarPorId(id);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al buscar el usuario por ID: " + id, ex);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorNombre(String nombre) throws UsuarioException {
        try {
            return usuarioDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al buscar el usuario: " + nombre, ex);
        }
    }

    @Override
    public void agregarUsuario(Usuario usuario) throws UsuarioException {
        validador.validarRegistro(usuario);
        try {
            usuarioDAO.agregar(usuario);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al registrar el nuevo usuario.", ex);
        }
    }

    @Override
    public void actualizarUsuario(String id, Usuario usuario) throws UsuarioException {
        validador.validarActualizacion(id, usuario);
        try {
            usuarioDAO.actualizar(id, usuario);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al actualizar la información del usuario.", ex);
        }
    }

    @Override
    public void eliminarUsuario(String id) throws UsuarioException {
        validador.validarEliminacion(id);
        try {
            usuarioDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al eliminar el usuario.", ex);
        }
    }
}
