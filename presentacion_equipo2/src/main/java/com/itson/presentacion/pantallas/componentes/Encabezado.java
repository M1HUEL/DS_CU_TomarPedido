package com.itson.presentacion.pantallas.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Encabezado extends JPanel {

    public Encabezado(String texto) {
        setPreferredSize(new Dimension(0, 180));
        setLayout(new GridBagLayout());

        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("Poppins", Font.BOLD, 32));
        titulo.setForeground(new Color(70, 40, 0));
        add(titulo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        GradientPaint gp = new GradientPaint(0, 0,
                new Color(240, 150, 70),
                getWidth(), getHeight(),
                new Color(255, 220, 180));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillRect(0, getHeight() - 4, getWidth(), 4);
        g2d.dispose();
    }

}
