package com.itson.presentacion.frame;

import com.itson.presentacion.controller.InicioController;
import com.itson.presentacion.controller.impl.InicioControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class InicioFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color CREMA = Colores.CREMA;
    private final Color GRIS = Colores.GRIS;

    private JButton btnCrearPedido;
    private JButton btnVerPedidos;
    private JButton btnDashboard;
    private JButton btnConfiguracion;
    private JButton btnCerrarSesion;

    private final InicioController controlador = new InicioControllerImpl(this);

    public InicioFrame() {
        super("Inicio");
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 140));

        JPanel headerContenido = new JPanel(new BorderLayout());
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel lblTitulo = new JLabel("Inicio", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(24f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Selecciona una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        JPanel titulosHeader = new JPanel();
        titulosHeader.setLayout(new BoxLayout(titulosHeader, BoxLayout.Y_AXIS));
        titulosHeader.setBackground(NARANJA);
        titulosHeader.add(lblTitulo);
        titulosHeader.add(Box.createVerticalStrut(5));
        titulosHeader.add(lblSubtitulo);

        headerContenido.add(titulosHeader, BorderLayout.CENTER);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelBlanco = new JPanel(new GridLayout(5, 1, 0, 15));
        panelBlanco.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(38, 38, 38, 38)
        );
        panelBlanco.setBorder(bordePanelBlanco);

        btnCrearPedido = crearBoton("Crear Pedido");
        btnVerPedidos = crearBoton("Ver Pedidos");
        btnDashboard = crearBoton("Dashboard");
        btnConfiguracion = crearBoton("Configuración");
        btnCerrarSesion = crearBoton("Cerrar Sesión");

        panelBlanco.add(btnCrearPedido);
        panelBlanco.add(btnVerPedidos);
        panelBlanco.add(btnDashboard);
        panelBlanco.add(btnConfiguracion);
        panelBlanco.add(btnCerrarSesion);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(40, 200, 60, 200));
        contenedorCentral.add(panelBlanco, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        for (ActionListener al : btnCrearPedido.getActionListeners()) {
            btnCrearPedido.removeActionListener(al);
        }

        btnCrearPedido.addActionListener(e -> controlador.crearPedido());
        btnVerPedidos.addActionListener(e -> controlador.verPedidos());
        btnDashboard.addActionListener(e -> controlador.mostrarDashboard());
        btnConfiguracion.addActionListener(e -> controlador.configurar());
        btnCerrarSesion.addActionListener(e -> controlador.cerrarSesion());
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsRegular(14f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InicioFrame frame = new InicioFrame();
            frame.setVisible(true);
        });
    }
}
