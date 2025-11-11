package com.itson.presentacion.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class IniciarSesionPantalla extends JFrame {

    private Font poppinsRegular;
    private Font poppinsBold;

    private final Color NARANJA = new Color(255, 140, 0);
    private final Color NARANJA_CLARO = new Color(255, 183, 77);
    private final Color NARANJA_MUY_CLARO = new Color(255, 250, 240);
    private final Color CREMA = new Color(254, 249, 239);
    private final Color BLANCO = Color.WHITE;
    private final Color GRIS_BORDE = new Color(230, 230, 230);

    private final JTextField txtNombre;
    private final JPasswordField txtContrasena;
    private final JButton btnIniciar;

    public IniciarSesionPantalla() {
        cargarFuentePoppins();

        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.LEFT);
        lblTitulo.setFont(poppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Introduce tus credenciales para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(poppinsRegular.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelBlanco = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBlanco.setBackground(BLANCO);
        panelBlanco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        ));

        JPanel panelNombre = new JPanel(new BorderLayout(0, 8));
        panelNombre.setBackground(BLANCO);
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(poppinsRegular.deriveFont(16f));
        txtNombre = new JTextField();
        txtNombre.setFont(poppinsRegular.deriveFont(16f));
        txtNombre.setBackground(NARANJA_MUY_CLARO);
        txtNombre.setBorder(BorderFactory.createLineBorder(NARANJA, 2));
        txtNombre.setPreferredSize(new Dimension(400, 45));
        panelNombre.add(lblNombre, BorderLayout.NORTH);
        panelNombre.add(txtNombre, BorderLayout.CENTER);
        panelBlanco.add(panelNombre);

        JPanel panelContrasena = new JPanel(new BorderLayout(0, 8));
        panelContrasena.setBackground(BLANCO);
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(poppinsRegular.deriveFont(16f));
        txtContrasena = new JPasswordField();
        txtContrasena.setFont(poppinsRegular.deriveFont(16f));
        txtContrasena.setBackground(NARANJA_MUY_CLARO);
        txtContrasena.setBorder(BorderFactory.createLineBorder(NARANJA, 2));
        txtContrasena.setPreferredSize(new Dimension(400, 45));
        panelContrasena.add(lblContrasena, BorderLayout.NORTH);
        panelContrasena.add(txtContrasena, BorderLayout.CENTER);
        panelBlanco.add(panelContrasena);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(BLANCO);
        btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBackground(NARANJA);
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(poppinsRegular.deriveFont(15f));
        btnIniciar.setFocusPainted(false);
        btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIniciar.setBorder(BorderFactory.createEmptyBorder(16, 46, 16, 46));
        panelBoton.add(btnIniciar);
        panelBlanco.add(panelBoton);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
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
        SwingUtilities.invokeLater(() -> {
            new IniciarSesionPantalla().setVisible(true);
        });
    }
}
