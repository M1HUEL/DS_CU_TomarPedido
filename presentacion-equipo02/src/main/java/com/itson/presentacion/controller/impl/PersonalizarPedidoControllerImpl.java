package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.*;
import com.itson.presentacion.controller.PersonalizarPedidoController;
import com.itson.presentacion.frame.ConfirmacionPedidoFrame;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import com.itson.util.sesion.Sesion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
                if (!i.getNombre().contains("Papas") && !i.getNombre().contains("Coca") && !i.getNombre().contains("Agua")) {
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
                if (i.getCodigo().contains("TOCINO") || i.getCodigo().contains("QUESO") || i.getCodigo().contains("CARNE")) {
                    double precio = 15.0;
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
                if (i.getNombre().contains("Salsa") || i.getNombre().contains("Ketchup")
                        || i.getNombre().contains("Mayonesa") || i.getNombre().contains("Pepinillos")) {
                    double precio = 5.0;
                    Complemento comp = new Complemento("Aderezo " + i.getNombre(), "Al lado", precio, i.getId(), 0.050);
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
        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(frame, "Error de sesión: Usuario no identificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuario.getRol() != Rol.CAJERO && usuario.getRol() != Rol.ADMINISTRADOR) {
            JOptionPane.showMessageDialog(frame, "No tienes permisos para procesar pedidos.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto productoFinal = new Producto();
        productoFinal.setNombre(productoBase.getNombre() + " (Personalizado)");
        productoFinal.setIngredientes(ingredientes);
        productoFinal.setExtras(extras);
        productoFinal.setComplementos(complementos);

        double total = productoBase.getPrecio();
        for (Extra e : extras) {
            total += e.getPrecio();
        }
        for (Complemento c : complementos) {
            total += c.getPrecio();
        }
        productoFinal.setPrecio(total);

        Pedido pedido = new Pedido();
        pedido.setNombre("Orden de " + productoBase.getNombre());
        pedido.setComentario(comentario);
        pedido.setPrecio(total);
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<Producto> listaProds = new ArrayList<>();
        listaProds.add(productoFinal);
        pedido.setProductos(listaProds);

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
        new SeleccionarPedidoFrame().setVisible(true);
    }
}
