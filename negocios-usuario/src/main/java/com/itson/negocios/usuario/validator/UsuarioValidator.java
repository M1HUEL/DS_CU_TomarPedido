package com.itson.negocios.usuario.validator;

import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.persistencia.dao.UsuarioDAO;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.exception.PersistenciaException;

public class UsuarioValidator {

    private final UsuarioDAO usuarioDAO;

    public UsuarioValidator(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void validarRegistro(Usuario usuario) throws UsuarioException {
        validarCamposObligatorios(usuario);
        validarNombreUnico(usuario.getNombre(), null);
    }

    public void validarActualizacion(String id, Usuario usuario) throws UsuarioException {
        validarTextoObligatorio(id, "El ID es obligatorio para actualizar.");
        validarExistencia(id);
        validarCamposObligatorios(usuario);
        validarNombreUnico(usuario.getNombre(), id);
    }

    public void validarEliminacion(String id) throws UsuarioException {
        validarTextoObligatorio(id, "El ID es obligatorio.");
        validarExistencia(id);
    }

    private void validarCamposObligatorios(Usuario usuario) throws UsuarioException {
        if (usuario == null) {
            throw new UsuarioException("No se enviaron datos del usuario.");
        }

        validarTextoObligatorio(usuario.getNombre(), "El nombre de usuario es obligatorio.");
        validarTextoObligatorio(usuario.getContrasena(), "La contraseña es obligatoria.");

        if (usuario.getRol() == null) {
            throw new UsuarioException("Debe asignar un rol al usuario.");
        }
    }

    private void validarNombreUnico(String nombre, String idExcluir) throws UsuarioException {
        try {
            Usuario existente = usuarioDAO.consultarPorNombre(nombre);

            if (existente == null) {
                return;
            }

            if (idExcluir != null && existente.getId().equals(idExcluir)) {
                return;
            }

            throw new UsuarioException("El nombre de usuario '" + nombre + "' ya está ocupado.");

        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al validar la unicidad del nombre.", ex);
        }
    }

    private void validarExistencia(String id) throws UsuarioException {
        try {
            if (usuarioDAO.consultarPorId(id) == null) {
                throw new UsuarioException("El usuario con ID " + id + " no existe.");
            }
        } catch (PersistenciaException ex) {
            throw new UsuarioException("Error al verificar la existencia del usuario.", ex);
        }
    }

    private void validarTextoObligatorio(String valor, String mensaje) throws UsuarioException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new UsuarioException(mensaje);
        }
    }
}
