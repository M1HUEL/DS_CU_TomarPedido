package com.itson.presentacion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SeleccionarPedidoPantalla extends JFrame {

    private Font poppinsRegular;
    private Font poppinsBold;

    private final Color NARANJA = new Color(255, 140, 0);
    private final Color NARANJA_CLARO = new Color(255, 183, 77);
    private final Color CREMA = new Color(254, 249, 239);
    private final Color BLANCO = Color.WHITE;
    private final Color GRIS_BORDE = new Color(230, 230, 230);

    public SeleccionarPedidoPantalla() {
        cargarFuentePoppins();

        setTitle("Seleccionar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContent = new JPanel(new GridLayout(2, 1));
        headerContent.setBackground(NARANJA);
        headerContent.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Selecciona el pedido", SwingConstants.LEFT);
        lblTitulo.setFont(poppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(poppinsRegular.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContent.add(lblTitulo);
        headerContent.add(lblSubtitulo);
        header.add(headerContent, BorderLayout.CENTER);

        JPanel panelCentral = new JPanel(new GridLayout(0, 3, 20, 20));
        panelCentral.setBackground(CREMA);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(40, 100, 60, 100));

        for (int i = 0; i < 12; i++) {
            panelCentral.add(crearTarjetaPedido(NARANJA, NARANJA_CLARO, BLANCO, GRIS_BORDE));
        }

        JScrollPane scrollPane = new JScrollPane(panelCentral);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(CREMA);
        scrollPane.setBackground(CREMA);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private JPanel crearTarjetaPedido(Color NARANJA, Color NARANJA_CLARO, Color BLANCO, Color GRIS_BORDE) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 10));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(10, 10, 20, 10)
        ));

        JLabel imagen = new JLabel();
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/images/burger.png"));
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(imagenEscalada));

        JLabel lblTitulo = new JLabel("Pedido", SwingConstants.CENTER);
        lblTitulo.setFont(poppinsRegular.deriveFont(Font.PLAIN, 14f));
        lblTitulo.setForeground(Color.BLACK);

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBackground(NARANJA);
        btnSeleccionar.setForeground(Color.WHITE);
        btnSeleccionar.setFont(poppinsRegular.deriveFont(Font.PLAIN, 14f));
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnSeleccionar.setBackground(NARANJA_CLARO);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnSeleccionar.setBackground(NARANJA);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                btnSeleccionar.setBackground(NARANJA_CLARO.darker());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                btnSeleccionar.setBackground(NARANJA_CLARO);
            }
        });

        tarjeta.add(imagen, BorderLayout.NORTH);
        tarjeta.add(lblTitulo, BorderLayout.CENTER);
        tarjeta.add(btnSeleccionar, BorderLayout.SOUTH);

        return tarjeta;
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regularStream = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream boldStream = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            if (regularStream != null) {
                poppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            } else {
                poppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            }

            if (boldStream != null) {
                poppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            } else {
                poppinsBold = new Font("SansSerif", Font.BOLD, 14);
            }

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsRegular);
            ge.registerFont(poppinsBold);

        } catch (FontFormatException | IOException e) {
            poppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            poppinsBold = new Font("SansSerif", Font.BOLD, 14);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeleccionarPedidoPantalla().setVisible(true));
    }
}
