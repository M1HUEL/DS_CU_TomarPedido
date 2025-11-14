package com.itson.presentacion.gui;

import com.itson.presentacion.util.Colores;
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

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color COLOR_PRIMARIO = Colores.NARANJA;
    private final Color COLOR_PRIMARIO_CLARO = Colores.NARANJA_CLARO;
    private final Color COLOR_FONDO = Colores.CREMA;
    private final Color COLOR_BLANCO = Colores.BLANCO;
    private final Color COLOR_BORDE = Colores.GRIS_BORDE;
    private final Color COLOR_INPUT = Colores.NARANJA_MUY_CLARO;

    private JButton btnIniciarSesion;
    private JButton btnCancelar;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblNombre;
    private JLabel lblContrasena;

    private JTextField txtNombre;
    private JPasswordField txtContrasena;

    private JPanel panelHeader;
    private JPanel panelHeaderContenido;
    private JPanel panelPrincipal;
    private JPanel panelCentro;

    private JPanel panelFormulario;
    private JPanel panelCampoNombre;
    private JPanel panelCampoContrasena;
    private JPanel panelBoton;

    public IniciarSesionPantalla() {
        cargarFuentePoppins();

        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_PRIMARIO);
        panelHeader.setPreferredSize(new Dimension(1440, 160));

        panelHeaderContenido = new JPanel(new GridLayout(2, 1));
        panelHeaderContenido.setBackground(COLOR_PRIMARIO);
        panelHeaderContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        lblSubtitulo = new JLabel("Introduce tus credenciales para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        panelHeaderContenido.add(lblTitulo);
        panelHeaderContenido.add(lblSubtitulo);
        panelHeader.add(panelHeaderContenido, BorderLayout.CENTER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);

        panelFormulario = new JPanel(new GridLayout(3, 1, 0, 20));
        panelFormulario.setBackground(COLOR_BLANCO);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        ));

        panelCampoNombre = new JPanel(new BorderLayout());
        panelCampoNombre.setBackground(COLOR_BLANCO);

        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuentePoppinsRegular.deriveFont(16f));

        txtNombre = new JTextField();
        txtNombre.setFont(fuentePoppinsRegular.deriveFont(16f));
        txtNombre.setBackground(COLOR_INPUT);
        txtNombre.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        txtNombre.setPreferredSize(new Dimension(400, 45));

        panelCampoNombre.add(lblNombre, BorderLayout.NORTH);
        panelCampoNombre.add(txtNombre, BorderLayout.CENTER);
        panelFormulario.add(panelCampoNombre);

        panelCampoContrasena = new JPanel(new BorderLayout());
        panelCampoContrasena.setBackground(COLOR_BLANCO);

        lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(fuentePoppinsRegular.deriveFont(16f));

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(fuentePoppinsRegular.deriveFont(16f));
        txtContrasena.setBackground(COLOR_INPUT);
        txtContrasena.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        txtContrasena.setPreferredSize(new Dimension(400, 45));

        panelCampoContrasena.add(lblContrasena, BorderLayout.NORTH);
        panelCampoContrasena.add(txtContrasena, BorderLayout.CENTER);
        panelFormulario.add(panelCampoContrasena);

        panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(COLOR_BLANCO);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(COLOR_PRIMARIO);
        btnIniciarSesion.setForeground(COLOR_BLANCO);
        btnIniciarSesion.setFont(fuentePoppinsRegular.deriveFont(15f));
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.setBorder(BorderFactory.createEmptyBorder(16, 46, 16, 46));

        panelBoton.add(btnIniciarSesion);
        panelFormulario.add(panelBoton);

        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(COLOR_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

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
        SwingUtilities.invokeLater(() -> new IniciarSesionPantalla().setVisible(true));
    }
}
