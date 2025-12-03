package com.itson.persistencia.exception;

public class PersistenciaException extends Exception {
    
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }
    
    public PersistenciaException(Throwable causa) {
        super(causa);
    }
    
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
