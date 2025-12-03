package com.itson.fachada.exception;

public class RestauranteFachadaException extends Exception {

    public RestauranteFachadaException() {
        super();
    }

    public RestauranteFachadaException(String mensaje) {
        super(mensaje);
    }

    public RestauranteFachadaException(Throwable causa) {
        super(causa);
    }

    public RestauranteFachadaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
