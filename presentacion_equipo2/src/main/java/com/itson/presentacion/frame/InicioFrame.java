package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.InicioControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InicioFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = new Color(255, 140, 0);
    private final Color CREMA = new Color(255, 245, 230);
    private final Color BLANCO = new Color(255, 255, 255);
    private final Color GRIS_BORDE = new Color(200, 200, 200);

    private JPanel header, headerContenido, panelPrincipal, panelBlanco, contenedorCentral;
    private JLabel lblTitulo, lblSubtitulo;
    private JButton btnCrearPedido, btnVerPedidos, btnDashboard, btnCerrarSesion;

    private final InicioControlador controlador = new InicioControlador();

    public InicioFrame() {
        cargarFuentePoppins();

        setTitle("Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblTitulo = new JLabel("Inicio", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        lblSubtitulo = new JLabel("Selecciona una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        panelBlanco = new JPanel(new GridLayout(4, 1, 0, 20));
        panelBlanco.setBackground(BLANCO);
        panelBlanco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        ));

        btnCrearPedido = new JButton("Crear Pedido");
        btnCrearPedido.setBackground(NARANJA);
        btnCrearPedido.setForeground(BLANCO);
        btnCrearPedido.setFont(fuentePoppinsRegular.deriveFont(16f));
        btnCrearPedido.setFocusPainted(false);
        btnCrearPedido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCrearPedido.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnVerPedidos = new JButton("Ver Pedidos");
        btnVerPedidos.setBackground(NARANJA);
        btnVerPedidos.setForeground(BLANCO);
        btnVerPedidos.setFont(fuentePoppinsRegular.deriveFont(16f));
        btnVerPedidos.setFocusPainted(false);
        btnVerPedidos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVerPedidos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnDashboard = new JButton("Dashboard");
        btnDashboard.setBackground(NARANJA);
        btnDashboard.setForeground(BLANCO);
        btnDashboard.setFont(fuentePoppinsRegular.deriveFont(16f));
        btnDashboard.setFocusPainted(false);
        btnDashboard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDashboard.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(NARANJA);
        btnCerrarSesion.setForeground(BLANCO);
        btnCerrarSesion.setFont(fuentePoppinsRegular.deriveFont(16f));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnCrearPedido.addActionListener(e -> controlador.abrirSeleccionarPedido(this));

        btnVerPedidos.addActionListener(e -> controlador.verPedidos(this));

        btnDashboard.addActionListener(e -> controlador.abrirDashboard(this));

        btnCerrarSesion.addActionListener(e -> controlador.cerrarSesion(this));

        panelBlanco.add(btnCrearPedido);
        panelBlanco.add(btnVerPedidos);
        panelBlanco.add(btnDashboard);
        panelBlanco.add(btnCerrarSesion);

        contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        contenedorCentral.add(panelBlanco, BorderLayout.CENTER);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

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
        SwingUtilities.invokeLater(() -> new InicioFrame().setVisible(true));
    }
}
