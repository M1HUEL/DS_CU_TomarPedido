package com.itson.presentacion.interfaces;

import com.itson.presentacion.pantallas.componentes.Boton;
import java.util.List;

public interface ISeleccionarMetodoPago {

    List<Boton> generarBotonesMetodoPago();

    void seleccionarMetodoPago(String metodo);

    void mostrarPantalla();
}
