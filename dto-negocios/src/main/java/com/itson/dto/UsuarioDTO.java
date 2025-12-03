package com.itson.dto;

public class UsuarioDTO {

    private String id;
    private String nombre;
    private String contrasena;
    private String sexo;
    private String rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String nombre, String contrasena, String sexo, String rol) {
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", nombre=" + nombre + ", contrasena=" + contrasena + ", sexo=" + sexo + ", rol=" + rol + '}';
    }

}
