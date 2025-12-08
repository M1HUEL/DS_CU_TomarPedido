package com.itson.persistencia.dominio;

public class Usuario {

    private String id;
    private String nombre;
    private String contrasena;
    private Sexo sexo;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(String nombre, String contrasena, Sexo sexo, Rol rol) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.sexo = sexo;
        this.rol = rol;
    }

    public Usuario(String id, String nombre, String contrasena, Sexo sexo, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.sexo = sexo;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", sexo=" + sexo + ", rol=" + rol + '}';
    }

}
