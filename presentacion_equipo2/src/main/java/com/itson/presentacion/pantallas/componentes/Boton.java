package com.itson.presentacion.pantallas.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

public class Boton extends JButton {

    public Boton(String texto) {
        super(texto);
        setFont(new Font("Poppins", Font.BOLD, 17));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color colorInicio = getModel().isRollover() ? new Color(245, 130, 30) : new Color(230, 126, 34);
        Color colorFin = getModel().isRollover() ? new Color(255, 160, 60) : new Color(245, 156, 49);

        GradientPaint gp = new GradientPaint(0, 0, colorInicio, 0, getHeight(), colorFin);
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        super.paintComponent(g); // Llamar al super **antes** de g2d.dispose()
        g2d.dispose();
    }

}
