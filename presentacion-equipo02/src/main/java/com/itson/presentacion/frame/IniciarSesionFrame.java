package com.itson.presentacion.frame;

import com.itson.presentacion.controller.IniciarSesionController;
import com.itson.presentacion.controller.impl.IniciarSesionControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    private JTextField txtNombre;
    private JPasswordField txtContrasena;

    private final IniciarSesionController controlador = new IniciarSesionControllerImpl(this);

    public IniciarSesionFrame() {
        super("Iniciar Sesión");
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
        lblTitulo.setFont(Fuentes.getPoppinsBold(24f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Introduce tus credenciales para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelBlanco = new JPanel();
        panelBlanco.setLayout(new GridLayout(4, 1, 0, 20));
        panelBlanco.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(38, 38, 38, 38)
        );
        panelBlanco.setBorder(bordePanelBlanco);

        JPanel panelCampoNombre = new JPanel(new BorderLayout(0, 5));
        panelCampoNombre.setBackground(BLANCO);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(Fuentes.getPoppinsRegular(14f));

        txtNombre = new JTextField();
        txtNombre.setFont(Fuentes.getPoppinsRegular(14f));
        txtNombre.setBackground(CREMA);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelCampoNombre.add(lblNombre, BorderLayout.NORTH);
        panelCampoNombre.add(txtNombre, BorderLayout.CENTER);
        panelBlanco.add(panelCampoNombre);

        JPanel panelCampoContrasena = new JPanel(new BorderLayout(0, 5));
        panelCampoContrasena.setBackground(BLANCO);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(Fuentes.getPoppinsRegular(14f));

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(Fuentes.getPoppinsRegular(14f));
        txtContrasena.setBackground(CREMA);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelCampoContrasena.add(lblContrasena, BorderLayout.NORTH);
        panelCampoContrasena.add(txtContrasena, BorderLayout.CENTER);
        panelBlanco.add(panelCampoContrasena);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(BLANCO);

        JButton btnIniciarSesion = crearBoton("Iniciar Sesión");

        btnIniciarSesion.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String contrasena = new String(txtContrasena.getPassword());

            controlador.iniciarSesion(nombre, contrasena);
        });
        panelBoton.add(btnIniciarSesion);
        panelBlanco.add(panelBoton);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(40, 200, 60, 200));
        contenedorCentral.add(panelBlanco, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsRegular(14f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(240, 45));

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IniciarSesionFrame().setVisible(true));
    }
}
