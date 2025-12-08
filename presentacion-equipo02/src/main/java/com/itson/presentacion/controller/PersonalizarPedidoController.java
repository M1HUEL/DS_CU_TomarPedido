package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import java.util.List;

public interface PersonalizarPedidoController {

    List<Ingrediente> obtenerIngredientesDisponibles();

    List<Extra> obtenerExtrasDisponibles();

    List<Complemento> obtenerComplementosDisponibles();

    void procesarPedido(Producto productoBase, List<Ingrediente> ingredientes, List<Extra> extras, List<Complemento> complementos, String comentario);

    void cancelar();
}
