package com.itson.presentacion.pantallas.componentes;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Texto extends JLabel {

    Color color = new Color(90, 50, 10);

    public Texto() {
        setFont(new Font("Poppins", Font.BOLD, 20));
        setForeground(color);
    }

    public Texto(String texto, int estiloFuente, int tamano, boolean centrado) {
        super(texto);
        setFont(new Font("Poppins", estiloFuente, tamano));
        setForeground(color);
        if (centrado) {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

}
