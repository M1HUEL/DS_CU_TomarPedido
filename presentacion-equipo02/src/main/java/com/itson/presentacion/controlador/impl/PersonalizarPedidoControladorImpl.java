package com.itson.presentacion.controlador.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controlador.PersonalizarPedidoControlador;
import com.itson.presentacion.frame.ConfirmacionPedidoFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

public class PersonalizarPedidoControladorImpl implements PersonalizarPedidoControlador {

    private final RestauranteFachada restauranteFachada;

    public PersonalizarPedidoControladorImpl() {
        this.restauranteFachada = new RestauranteFachadaImpl();
    }

    @Override
    public void completarPedido(Producto productoOriginal, Map<Object, JCheckBox> selecciones, String comentario) {

        // --- 1. CLONE STRATEGY ---
        Producto productoPersonalizado = new Producto();
        productoPersonalizado.setId(null);
        productoPersonalizado.setNombre(productoOriginal.getNombre());

        double totalPrecio = productoOriginal.getPrecio();

        // --- 2. FILTER INGREDIENTS ---
        List<Ingrediente> ingredientesFinales = new ArrayList<>();
        if (productoOriginal.getIngredientes() != null) {
            for (Ingrediente ing : productoOriginal.getIngredientes()) {
                JCheckBox chk = selecciones.get(ing);
                if (chk != null && chk.isSelected()) {
                    ingredientesFinales.add(ing);
                }
            }
        }
        productoPersonalizado.setIngredientes(ingredientesFinales);

        // --- 3. FILTER EXTRAS ---
        List<Extra> extrasFinales = new ArrayList<>();
        if (productoOriginal.getExtras() != null) {
            for (Extra ext : productoOriginal.getExtras()) {
                JCheckBox chk = selecciones.get(ext);
                if (chk != null && chk.isSelected()) {
                    extrasFinales.add(ext);
                    totalPrecio += ext.getPrecio();
                }
            }
        }
        productoPersonalizado.setExtras(extrasFinales);

        // --- 4. FILTER COMPLEMENTS ---
        List<Complemento> complementosFinales = new ArrayList<>();
        if (productoOriginal.getComplementos() != null) {
            for (Complemento comp : productoOriginal.getComplementos()) {
                JCheckBox chk = selecciones.get(comp);
                if (chk != null && chk.isSelected()) {
                    complementosFinales.add(comp);
                    totalPrecio += comp.getPrecio();
                }
            }
        }
        productoPersonalizado.setComplementos(complementosFinales);

        // Set Final Price
        productoPersonalizado.setPrecio(totalPrecio);

        // --- 5. INSTANTIATE ORDER ---
        Pedido nuevoPedido = new Pedido();
        // You might want to set a default name like "Custom Order" or the product name
        nuevoPedido.setNombre(productoPersonalizado.getNombre());
        nuevoPedido.setComentario(comentario);
        nuevoPedido.setPrecio(totalPrecio);

        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(productoPersonalizado);
        nuevoPedido.setProductos(listaProductos);

        // --- 6. NAVIGATE TO CONFIRMATION ---
        System.out.println("Order created in memory. Proceeding to confirmation...");

        // We use invokeLater to ensure thread safety when opening the new UI
        SwingUtilities.invokeLater(() -> {
            new ConfirmacionPedidoFrame(nuevoPedido).setVisible(true);
        });
    }
}
