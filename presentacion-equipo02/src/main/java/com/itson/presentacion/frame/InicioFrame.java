package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.InicioController;
import com.itson.presentacion.controller.impl.InicioControllerImpl;
import com.itson.util.sesion.Sesion;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class InicioFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color CREMA = Colores.CREMA;
    private final Color GRIS = Colores.GRIS;

    private final Usuario usuarioLogueado;
    private final InicioController controlador;

    public InicioFrame() {
        this.usuarioLogueado = Sesion.getInstancia().getUsuarioLogueado();

        if (this.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "No hay sesión activa. Por favor inicie sesión.");
            dispose();
            throw new IllegalStateException("No user logged in");
        }

        setTitle("Inicio - Bienvenido " + usuarioLogueado.getNombre());
        this.controlador = new InicioControllerImpl(this);

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

        JPanel panelBlanco = new JPanel(new GridLayout(0, 2, 20, 20));
        panelBlanco.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(38, 38, 38, 38)
        );
        panelBlanco.setBorder(bordePanelBlanco);

        Rol rol = usuarioLogueado.getRol();

        if (rol == Rol.CAJERO || rol == Rol.ADMINISTRADOR) {
            JButton btnCrearPedido = crearBoton("Crear Pedido");
            btnCrearPedido.addActionListener(e -> controlador.crearPedido());
            panelBlanco.add(btnCrearPedido);

            JButton btnVerPedidos = crearBoton("Ver Pedidos (En Curso)");
            btnVerPedidos.addActionListener(e -> controlador.verPedidos());
            panelBlanco.add(btnVerPedidos);
        }

        if (rol == Rol.COCINERO || rol == Rol.ADMINISTRADOR) {
            JButton btnCocina = crearBoton("Ver Cocina");
            btnCocina.addActionListener(e -> controlador.verCocina());
            panelBlanco.add(btnCocina);

            JButton btnInventario = crearBoton("Ver Inventario");
            btnInventario.addActionListener(e -> controlador.verInventario());
            panelBlanco.add(btnInventario);
        }

        if (rol != Rol.COCINERO) {
            JButton btnDashboard = crearBoton("Dashboard");
            btnDashboard.addActionListener(e -> controlador.mostrarDashboard());
            panelBlanco.add(btnDashboard);
        }

        if (rol == Rol.ADMINISTRADOR) {
            JButton btnHistorial = crearBoton("Ver Pedidos Finalizados");
            btnHistorial.addActionListener(e -> controlador.verHistorialVentas());
            panelBlanco.add(btnHistorial);

            JButton btnGestionMenu = crearBoton("Gestión del Menú");
            btnGestionMenu.addActionListener(e -> {
                new GestionMenuFrame().setVisible(true);
                dispose();
            });
            panelBlanco.add(btnGestionMenu);

            JButton btnConfiguracion = crearBoton("Configuración");
            btnConfiguracion.addActionListener(e -> controlador.configurar());
            panelBlanco.add(btnConfiguracion);
        }

        JButton btnCerrarSesion = crearBoton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> controlador.cerrarSesion());
        panelBlanco.add(btnCerrarSesion);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);

        int margenLateral = 200;
        int margenVertical = 40;

        if (rol == Rol.COCINERO) {
            margenVertical = 150;
        }

        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(margenVertical, margenLateral, margenVertical, margenLateral));
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
        btn.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(NARANJA.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(NARANJA);
            }
        });
        return btn;
    }
}
