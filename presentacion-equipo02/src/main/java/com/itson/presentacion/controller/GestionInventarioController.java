package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Insumo;
import java.util.List;

public interface GestionInventarioController {

    List<Insumo> cargarInventario();

    void agregarInsumo();

    void editarInsumo(Insumo insumo);

    void eliminarInsumo(Insumo insumo);

    void regresar();
}
