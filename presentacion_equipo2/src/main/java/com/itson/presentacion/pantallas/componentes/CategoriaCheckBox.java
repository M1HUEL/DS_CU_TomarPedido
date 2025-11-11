package com.itson.presentacion.pantallas.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CategoriaCheckBox extends JPanel {

    public CategoriaCheckBox(String titulo, String[] opciones, List<JCheckBox> listaCheckBoxes) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        Texto lblTitulo = new Texto();
        lblTitulo.setText(titulo);
        lblTitulo.setFont(new Font("Poppins", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(80, 45, 0));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(255, 245, 220));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 10));
        grid.setBackground(Color.WHITE);

        for (String op : opciones) {
            JCheckBox cb = new JCheckBox(op);
            cb.setFont(new Font("Poppins", Font.PLAIN, 15));
            cb.setBackground(Color.WHITE);
            cb.setFocusPainted(false);
            cb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cb.setOpaque(true);

            cb.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    cb.setBackground(new Color(255, 245, 220));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    cb.setBackground(Color.WHITE);
                }
            });

            listaCheckBoxes.add(cb);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(Color.WHITE);
            wrapper.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            wrapper.add(cb, BorderLayout.CENTER);
            grid.add(wrapper);
        }

        add(grid, BorderLayout.CENTER);
    }
}
