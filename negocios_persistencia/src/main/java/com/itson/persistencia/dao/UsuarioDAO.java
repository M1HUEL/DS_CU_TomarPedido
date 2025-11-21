package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Usuario;
import java.util.List;

public interface UsuarioDAO {

    List<Usuario> consultarTodos();

    Usuario consultar(String id);

    boolean agregar(Usuario usuario);

    boolean actualizar(String id, Usuario usuario);

    boolean eliminar(String id);

}
