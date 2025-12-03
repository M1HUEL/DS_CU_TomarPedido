package com.itson.negocios.usuario.exception;

public class UsuarioException extends Exception {

    public UsuarioException(String mensaje) {
        super(mensaje);
    }

    public UsuarioException(Throwable causa) {
        super(causa);
    }

    public UsuarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}
