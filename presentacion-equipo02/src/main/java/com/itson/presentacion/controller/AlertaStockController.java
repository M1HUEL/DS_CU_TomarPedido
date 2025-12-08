package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Insumo;
import java.util.List;

public interface AlertaStockController {

    List<Insumo> cargarAlertas();

    void generarListaCompra();

    void cerrar();

}
