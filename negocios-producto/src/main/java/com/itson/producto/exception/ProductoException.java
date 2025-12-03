package com.itson.producto.exception;

public class ProductoException extends Exception {

    public ProductoException(String mensaje) {
        super(mensaje);
    }

    public ProductoException(Throwable causa) {
        super(causa);
    }

    public ProductoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
