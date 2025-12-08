package com.itson.util.sesion;

import com.itson.persistencia.dominio.Usuario;

public class Sesion {

    private static Sesion instancia; // La única instancia
    private Usuario usuarioLogueado; // El dato que queremos compartir

    // Constructor privado para que nadie pueda hacer "new Sesion()"
    private Sesion() {
    }

    // Método estático para obtener la instancia global
    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    // Métodos para guardar y obtener el usuario
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        this.usuarioLogueado = null;
    }

    // Método utilitario para verificar si hay alguien
    public boolean haySesionActiva() {
        return this.usuarioLogueado != null;
    }
}
