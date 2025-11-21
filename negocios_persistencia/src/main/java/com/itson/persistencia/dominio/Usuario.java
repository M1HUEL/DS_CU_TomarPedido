package com.itson.persistencia.dominio;

public class Usuario {

    private String id;
    private String nombre;
    private String contraseña;
    private UsuarioTipo rol;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String contraseña, UsuarioTipo rol) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public UsuarioTipo getRol() {
        return rol;
    }

    public void setRol(UsuarioTipo rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + ", rol=" + rol + '}';
    }

}
