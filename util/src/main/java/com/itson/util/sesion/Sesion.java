package com.itson.util.sesion;

import com.itson.persistencia.dominio.Usuario;

public class Sesion {

    private static Sesion instancia;
    private Usuario usuarioLogueado;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void cerrarSesion() {
        this.usuarioLogueado = null;
    }

    public boolean haySesionActiva() {
        return this.usuarioLogueado != null;
    }
}
