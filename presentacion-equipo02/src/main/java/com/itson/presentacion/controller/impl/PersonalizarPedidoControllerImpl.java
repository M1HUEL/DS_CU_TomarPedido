package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.*;
import com.itson.presentacion.controller.PersonalizarPedidoController;
import com.itson.presentacion.frame.ConfirmacionPedidoFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PersonalizarPedidoControllerImpl implements PersonalizarPedidoController {

    private final RestauranteFachada fachada;
    private final JFrame frame;

    public PersonalizarPedidoControllerImpl(JFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Ingrediente> obtenerIngredientesDisponibles() {
        List<Ingrediente> lista = new ArrayList<>();
        try {
            for (Insumo i : fachada.obtenerInsumos()) {
                // Filtro simple: Si es pieza, rebanada o kilo, puede ser ingrediente extra
                // Excluimos las bebidas o papas congeladas (que son Productos)
                if (!i.getNombre().contains("Papas") && !i.getNombre().contains("Coca") && !i.getNombre().contains("Agua")) {
                    // Creamos el ingrediente "base"
                    // Precio 0.0 porque asumimos que es para poner/quitar de la burger
                    Ingrediente ing = new Ingrediente(i.getNombre(), "Opción Base", 0.0, i.getId(), 1.0);
                    lista.add(ing);
                }
            }
        } catch (RestauranteFachadaException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Extra> obtenerExtrasDisponibles() {
        List<Extra> lista = new ArrayList<>();
        try {
            for (Insumo i : fachada.obtenerInsumos()) {
                // Lógica de negocio: ¿Qué vendemos como EXTRA?
                // Ejemplo: Tocino, Queso, Carne Extra
                if (i.getCodigo().contains("TOCINO") || i.getCodigo().contains("QUESO") || i.getCodigo().contains("CARNE")) {
                    double precio = 15.0; // Precio default
                    if (i.getNombre().contains("Carne")) {
                        precio = 35.0;
                    }

                    Extra extra = new Extra("Extra " + i.getNombre(), "Porción Adicional", precio, i.getId(), 1.0);
                    lista.add(extra);
                }
            }
        } catch (RestauranteFachadaException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Complemento> obtenerComplementosDisponibles() {
        List<Complemento> lista = new ArrayList<>();
        try {
            for (Insumo i : fachada.obtenerInsumos()) {
                // Lógica: ¿Qué son COMPLEMENTOS? (Salsas, Aderezos)
                if (i.getNombre().contains("Salsa") || i.getNombre().contains("Ketchup")
                        || i.getNombre().contains("Mayonesa") || i.getNombre().contains("Pepinillos")) {

                    // A veces los complementos son gratis, a veces no. Ponemos precio bajo.
                    double precio = 5.0;
                    Complemento comp = new Complemento("Aderezo " + i.getNombre(), "Al lado", precio, i.getId(), 0.050); // 50ml/gr
                    lista.add(comp);
                }
            }
        } catch (RestauranteFachadaException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void procesarPedido(Producto productoBase, List<Ingrediente> ingredientes, List<Extra> extras, List<Complemento> complementos, String comentario) {

        // 1. Clonar y Personalizar
        Producto productoFinal = new Producto();
        productoFinal.setNombre(productoBase.getNombre() + " (Personalizado)");
        productoFinal.setIngredientes(ingredientes);
        productoFinal.setExtras(extras);
        productoFinal.setComplementos(complementos);

        // 2. Calcular Precio
        double total = productoBase.getPrecio();
        for (Extra e : extras) {
            total += e.getPrecio();
        }
        for (Complemento c : complementos) {
            total += c.getPrecio();
        }
        productoFinal.setPrecio(total);

        // 3. Crear Pedido
        Pedido pedido = new Pedido();
        pedido.setNombre("Orden de " + productoBase.getNombre());
        pedido.setComentario(comentario);
        pedido.setPrecio(total);

        List<Producto> listaProds = new ArrayList<>();
        listaProds.add(productoFinal);
        pedido.setProductos(listaProds);

        // 4. Navegar
        if (frame != null) {
            frame.dispose();
        }

        SwingUtilities.invokeLater(() -> {
            new ConfirmacionPedidoFrame(pedido).setVisible(true);
        });
    }

    @Override
    public void cancelar() {
        if (frame != null) {
            frame.dispose();
        }
        // Opcional: Volver al menú anterior
    }
}
