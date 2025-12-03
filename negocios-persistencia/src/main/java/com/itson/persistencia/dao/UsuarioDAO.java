package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Sexo;
import com.itson.persistencia.dominio.Usuario;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public interface UsuarioDAO {

    List<Usuario> consultarTodos() throws PersistenciaException;

    List<Usuario> consultarTodosPorSexo(Sexo sexo) throws PersistenciaException;

    List<Usuario> consultarTodosPorRol(Rol rol) throws PersistenciaException;

    Usuario consultarPorId(String usuarioId) throws PersistenciaException;

    Usuario consultarPorNombre(String nombre) throws PersistenciaException;

    void agregar(Usuario usuario) throws PersistenciaException;

    void actualizar(String usuarioId, Usuario usuario) throws PersistenciaException;

    void eliminar(String usuarioId) throws PersistenciaException;

}
