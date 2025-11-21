package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.IniciarSesionControlador;
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
import javax.swing.border.Border;

public class IniciarSesionFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public IniciarSesionFrame() {
        super("Iniciar Sesión");
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

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Introduce tus credenciales para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);

        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelBlanco = new JPanel();
        panelBlanco.setLayout(new GridLayout(4, 1, 0, 20));
        panelBlanco.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        );

        panelBlanco.setBorder(bordePanelBlanco);

        JPanel panelCampoNombre = new JPanel();
        panelCampoNombre.setLayout(new BorderLayout());
        panelCampoNombre.setBackground(BLANCO);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuentePoppinsRegular.deriveFont(14f));

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(fuentePoppinsRegular.deriveFont(14f));
        txtNombre.setBackground(CREMA);
        txtNombre.setBorder(BorderFactory.createLineBorder(GRIS, 1));

        panelCampoNombre.add(lblNombre, BorderLayout.NORTH);
        panelCampoNombre.add(txtNombre, BorderLayout.CENTER);
        panelBlanco.add(panelCampoNombre);

        JPanel panelCampoContrasena = new JPanel();
        panelCampoContrasena.setLayout(new BorderLayout());
        panelCampoContrasena.setBackground(BLANCO);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(fuentePoppinsRegular.deriveFont(14f));

        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setFont(fuentePoppinsRegular.deriveFont(14f));
        txtContrasena.setBackground(CREMA);
        txtContrasena.setBorder(BorderFactory.createLineBorder(GRIS, 1));

        panelCampoContrasena.add(lblContrasena, BorderLayout.NORTH);
        panelCampoContrasena.add(txtContrasena, BorderLayout.CENTER);

        panelBlanco.add(panelCampoContrasena);

        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(BLANCO);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(NARANJA);
        btnIniciarSesion.setForeground(BLANCO);
        btnIniciarSesion.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setBorder(null);
        btnIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.setPreferredSize(new Dimension(200, 40));

        IniciarSesionControlador controlador = new IniciarSesionControlador();

        btnIniciarSesion.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String contrasena = new String(txtContrasena.getPassword());
            controlador.iniciarSesion(nombre, contrasena, this);
        });

        panelBoton.add(btnIniciarSesion);
        panelBlanco.add(panelBoton);

        JPanel contenedorCentral = new JPanel();
        contenedorCentral.setLayout(new BorderLayout());
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
        SwingUtilities.invokeLater(() -> new IniciarSesionFrame().setVisible(true));
    }
}
