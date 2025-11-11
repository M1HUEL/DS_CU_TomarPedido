package com.itson.presentacion.pantallas.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CampoFormulario extends JPanel {

    private JTextField campo;

    public CampoFormulario(String labelTexto, int alto) {
        setLayout(new BorderLayout(0, 5));
        setBackground(new Color(255, 252, 245));

        JLabel lbl = new JLabel(labelTexto);
        lbl.setFont(new Font("Poppins", Font.BOLD, 16));
        lbl.setForeground(new Color(90, 55, 20));

        campo = new JTextField();
        campo.setFont(new Font("Poppins", Font.PLAIN, 15));
        campo.setPreferredSize(new Dimension(0, alto));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 180, 120), 2, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        campo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                campo.setBackground(new Color(255, 245, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                campo.setBackground(Color.WHITE);
            }
        });

        add(lbl, BorderLayout.NORTH);
        add(campo, BorderLayout.CENTER);
    }

    public JTextField getCampo() {
        return campo;
    }

}
