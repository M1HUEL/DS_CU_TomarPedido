package com.itson.presentacion.controladores;

import com.itson.presentacion.interfaces.ISeleccionarMetodoPago;
import com.itson.presentacion.pantallas.SeleccionarMetodoPagoPantalla;
import com.itson.presentacion.pantallas.componentes.Boton;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SeleccionarMetodoPagoControlador implements ISeleccionarMetodoPago {

    private final SeleccionarMetodoPagoPantalla vista;
    private final String[] metodos = {
        "Efectivo 💵",
        "Tarjeta de crédito 💳",
        "Transferencia bancaria 🏦",
        "Pago con QR 📱"
    };

    public SeleccionarMetodoPagoControlador(SeleccionarMetodoPagoPantalla vista) {
        this.vista = vista;
    }

    @Override
    public List<Boton> generarBotonesMetodoPago() {
        List<Boton> botones = new ArrayList<>();

        for (String metodo : metodos) {
            Boton boton = new Boton(metodo);
            boton.addActionListener((ActionEvent e) -> {
                seleccionarMetodoPago(metodo);
            });
            botones.add(boton);
        }

        return botones;
    }

    @Override
    public void seleccionarMetodoPago(String metodo) {
        switch (metodo) {
            case "Efectivo 💵" -> {
                JOptionPane.showMessageDialog(vista,
                        "Pago en efectivo registrado correctamente.",
                        "Pago exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            }
            case "Tarjeta de crédito 💳" -> {
                vista.dispose();
            }
            case "Transferencia bancaria 🏦" -> {
                JOptionPane.showMessageDialog(vista,
                        "Por favor, realiza tu transferencia bancaria al número de cuenta proporcionado.",
                        "Instrucciones de pago",
                        JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            }
            case "Pago con QR 📱" -> {
                JOptionPane.showMessageDialog(vista,
                        "Escanea el código QR mostrado en pantalla para completar tu pago.",
                        "Pago con QR",
                        JOptionPane.INFORMATION_MESSAGE);
                vista.dispose();
            }
            default ->
                JOptionPane.showMessageDialog(vista,
                        "Método de pago no reconocido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mostrarPantalla() {
        vista.setVisible(true);
    }
}
