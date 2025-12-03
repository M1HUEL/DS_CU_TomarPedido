package com.itson.pedido.exception;

public class PedidoException extends Exception {
    
    public PedidoException(String mensaje) {
        super(mensaje);
    }
    
    public PedidoException(Throwable causa) {
        super(causa);
    }
    
    public PedidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
