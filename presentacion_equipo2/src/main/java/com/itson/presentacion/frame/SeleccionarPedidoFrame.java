package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.SeleccionarPedidoControlador;
import com.itson.presentacion.util.Colores;
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
import javax.swing.border.Border;

public class SeleccionarPedidoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public SeleccionarPedidoFrame() {
        super("Seleccionar Pedido");
        setTitle("Seleccionar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarFuentePoppins();

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel();
        headerContenido.setLayout(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Seleccionar el pedido", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);

        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new GridLayout(0, 3, 20, 20));
        panelTarjetas.setBackground(CREMA);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(40, 100, 60, 100));

        SeleccionarPedidoControlador controlador = new SeleccionarPedidoControlador();

        for (int i = 0; i < 12; i++) {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BorderLayout(0, 10));
            tarjeta.setBackground(BLANCO);

            Border bordeTarjeta = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(GRIS, 1),
                    BorderFactory.createEmptyBorder(10, 10, 20, 10)
            );

            tarjeta.setBorder(bordeTarjeta);

            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/images/burger.png"));

            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

            JLabel imagen = new JLabel();
            imagen.setHorizontalAlignment(SwingConstants.CENTER);
            imagen.setIcon(new ImageIcon(imagenEscalada));

            JLabel lblNombrePedido = new JLabel("Pedido", SwingConstants.CENTER);
            lblNombrePedido.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 14f));
            lblNombrePedido.setForeground(Color.BLACK);

            JButton btnSeleccionar = new JButton("Seleccionar");
            btnSeleccionar.setBackground(NARANJA);
            btnSeleccionar.setForeground(Color.WHITE);
            btnSeleccionar.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 14f));
            btnSeleccionar.setFocusPainted(false);
            btnSeleccionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            btnSeleccionar.addActionListener(e -> controlador.seleccionarPedido(this));

            tarjeta.add(imagen, BorderLayout.NORTH);
            tarjeta.add(lblNombrePedido, BorderLayout.CENTER);
            tarjeta.add(btnSeleccionar, BorderLayout.SOUTH);

            panelTarjetas.add(tarjeta);
        }

        JScrollPane scrollTarjetas = new JScrollPane(panelTarjetas);
        scrollTarjetas.setBorder(null);
        scrollTarjetas.getVerticalScrollBar().setUnitIncrement(16);
        scrollTarjetas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTarjetas.getViewport().setBackground(CREMA);
        scrollTarjetas.setBackground(CREMA);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);
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
