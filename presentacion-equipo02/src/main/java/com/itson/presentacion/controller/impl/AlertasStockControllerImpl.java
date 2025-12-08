package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Insumo;
import com.itson.presentacion.controller.AlertaStockController;
import com.itson.presentacion.frame.AlertasStockFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlertasStockControllerImpl implements AlertaStockController {

    private final AlertasStockFrame frame;
    private final RestauranteFachada fachada;

    public AlertasStockControllerImpl(AlertasStockFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Insumo> cargarAlertas() {
        try {
            return fachada.obtenerAlertasStock();
        } catch (RestauranteFachadaException e) {
            System.err.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void generarListaCompra() {
        List<Insumo> alertas = cargarAlertas();
        if (alertas.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay insumos bajos. ¡Todo está bien!");
            return;
        }

        StringBuilder lista = new StringBuilder();
        lista.append("=== LISTA DE COMPRA SUGERIDA ===\n\n");
        for (Insumo i : alertas) {
            double faltante = (i.getStockMinimo() * 2) - i.getStockActual();
            lista.append("- ").append(i.getNombre())
                    .append(": Comprar ").append(String.format("%.2f", faltante))
                    .append(" ").append(i.getUnidadMedida()).append("\n");
        }

        JOptionPane.showMessageDialog(frame, new javax.swing.JScrollPane(new javax.swing.JTextArea(lista.toString(), 20, 40)),
                "Generador de Pedido a Proveedor", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void cerrar() {
        frame.dispose();
    }
}
