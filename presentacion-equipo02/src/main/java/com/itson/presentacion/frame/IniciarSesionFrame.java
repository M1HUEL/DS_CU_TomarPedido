package com.itson.presentacion.frame;

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
import javax.swing.JOptionPane;
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

    public IniciarSesionFrame() {
        super("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Introduce tus credenciales para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- PANEL TARJETA BLANCA (Login) ---
        JPanel panelBlanco = new JPanel();
        panelBlanco.setLayout(new GridLayout(4, 1, 0, 20)); // 4 filas, espaciado de 20
        panelBlanco.setBackground(BLANCO);

        // Borde compuesto: Línea gris + Padding interno grande
        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        );
        panelBlanco.setBorder(bordePanelBlanco);

        // 1. Campo Nombre
        JPanel panelCampoNombre = new JPanel(new BorderLayout(0, 5));
        panelCampoNombre.setBackground(BLANCO);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(Fuentes.getPoppinsRegular(14f));

        txtNombre = new JTextField();
        txtNombre.setFont(Fuentes.getPoppinsRegular(14f));
        txtNombre.setBackground(CREMA);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding dentro del input
        ));

        panelCampoNombre.add(lblNombre, BorderLayout.NORTH);
        panelCampoNombre.add(txtNombre, BorderLayout.CENTER);
        panelBlanco.add(panelCampoNombre);

        // 2. Campo Contraseña
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

        // 3. Botón Iniciar Sesión
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(BLANCO);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBackground(NARANJA);
        btnIniciarSesion.setForeground(BLANCO);
        btnIniciarSesion.setFont(Fuentes.getPoppinsSemiBold(14f));
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btnIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIniciarSesion.setPreferredSize(new Dimension(200, 45));

        // Acción del botón
        btnIniciarSesion.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String contrasena = new String(txtContrasena.getPassword());
            JOptionPane.showMessageDialog(this, "Intentando acceder...\nUsuario: " + nombre);
        });

        panelBoton.add(btnIniciarSesion);
        panelBlanco.add(panelBoton);

        // --- CONTENEDOR CENTRAL ---
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        // Margen externo para centrar la tarjeta blanca
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 350, 100, 350));
        contenedorCentral.add(panelBlanco, BorderLayout.CENTER);

        // --- ARMADO FINAL ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IniciarSesionFrame().setVisible(true));
    }
}
