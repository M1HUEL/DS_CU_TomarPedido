package com.itson.inventario.exception;

public class InventarioException extends Exception {

    public InventarioException() {
        super();
    }

    public InventarioException(String mensaje) {
        super(mensaje);
    }

    public InventarioException(Throwable causa) {
        super(causa);
    }

    public InventarioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
