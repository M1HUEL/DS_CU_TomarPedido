package com.itson.negocios.transaccion.exception;

public class TransaccionException extends Exception {

    public TransaccionException() {
        super();
    }

    public TransaccionException(String mensaje) {
        super(mensaje);
    }

    public TransaccionException(Throwable causa) {
        super(causa);
    }

    public TransaccionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
