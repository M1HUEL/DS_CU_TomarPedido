package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.SeleccionarPedidoControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class SeleccionarPedidoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color COLOR_NARANJA = new Color(255, 140, 0);
    private final Color COLOR_NARANJA_CLARO = new Color(255, 183, 77);
    private final Color COLOR_CREMA = new Color(254, 249, 239);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_BORDE_GRIS = new Color(230, 230, 230);

    private JPanel header, headerContenido, panelTarjetas, tarjeta, panelPrincipal;
    private JLabel lblTitulo, lblSubtitulo, lblImagen, lblNombrePedido;
    private JButton btnSeleccionar;
    private JScrollPane scrollTarjetas;

    SeleccionarPedidoControlador controlador = new SeleccionarPedidoControlador();

    public SeleccionarPedidoFrame() {

        cargarFuentePoppins();

        setTitle("Seleccionar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(COLOR_NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblTitulo = new JLabel("Selecciona el pedido", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        panelTarjetas = new JPanel(new GridLayout(0, 3, 20, 20));
        panelTarjetas.setBackground(COLOR_CREMA);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(40, 100, 60, 100));

        for (int i = 0; i < 12; i++) {

            tarjeta = new JPanel(new BorderLayout(0, 10));
            tarjeta.setBackground(COLOR_BLANCO);
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE_GRIS, 1),
                    BorderFactory.createEmptyBorder(10, 10, 20, 10)
            ));

            lblImagen = new JLabel();
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/images/burger.png"));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imagenEscalada));

            lblNombrePedido = new JLabel("Pedido", SwingConstants.CENTER);
            lblNombrePedido.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 14f));
            lblNombrePedido.setForeground(Color.BLACK);

            btnSeleccionar = new JButton("Seleccionar");
            btnSeleccionar.setBackground(COLOR_NARANJA);
            btnSeleccionar.setForeground(Color.WHITE);
            btnSeleccionar.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 14f));
            btnSeleccionar.setFocusPainted(false);
            btnSeleccionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            btnSeleccionar.addActionListener(e -> controlador.seleccionarPedido(this));

            btnSeleccionar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btnSeleccionar.setBackground(COLOR_NARANJA_CLARO);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnSeleccionar.setBackground(COLOR_NARANJA);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    btnSeleccionar.setBackground(COLOR_NARANJA_CLARO.darker());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    btnSeleccionar.setBackground(COLOR_NARANJA_CLARO);
                }
            });

            tarjeta.add(lblImagen, BorderLayout.NORTH);
            tarjeta.add(lblNombrePedido, BorderLayout.CENTER);
            tarjeta.add(btnSeleccionar, BorderLayout.SOUTH);

            panelTarjetas.add(tarjeta);
        }

        scrollTarjetas = new JScrollPane(panelTarjetas);
        scrollTarjetas.setBorder(null);
        scrollTarjetas.getVerticalScrollBar().setUnitIncrement(16);
        scrollTarjetas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTarjetas.getViewport().setBackground(COLOR_CREMA);
        scrollTarjetas.setBackground(COLOR_CREMA);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_CREMA);
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(scrollTarjetas, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regularStream = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream boldStream = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            fuentePoppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            fuentePoppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePoppinsRegular);
            ge.registerFont(fuentePoppinsBold);

        } catch (FontFormatException | IOException e) {
            fuentePoppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            fuentePoppinsBold = new Font("SansSerif", Font.BOLD, 14);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeleccionarPedidoFrame().setVisible(true));
    }
}
