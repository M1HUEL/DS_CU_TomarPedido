package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.SeleccionarMetodoPagoController;
import com.itson.presentacion.controller.impl.SeleccionarMetodoPagoControllerImpl;
import com.itson.util.sesion.Sesion;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class SeleccionarMetodoPagoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color NARANJA_OSCURO = new Color(230, 81, 0);
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    private final Pedido pedido;
    private final SeleccionarMetodoPagoController controlador = new SeleccionarMetodoPagoControllerImpl(this);

    public SeleccionarMetodoPagoFrame(Pedido pedido) {
        this.pedido = pedido;

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Acceso Denegado: No hay sesión activa.", "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        Rol rol = usuario.getRol();
        if (rol != Rol.CAJERO && rol != Rol.ADMINISTRADOR) {
            JOptionPane.showMessageDialog(null,
                    "Acceso Denegado: El rol " + rol + " no tiene permiso para procesar pagos.",
                    "Permisos Insuficientes", JOptionPane.WARNING_MESSAGE);
            dispose();
            new InicioFrame().setVisible(true);
            return;
        }

        setTitle("Seleccionar Método de Pago");
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

        JLabel lblTitulo = new JLabel("Seleccionar Método de Pago", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige una opción para finalizar tu pedido", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelOpcionesPago = new JPanel();
        panelOpcionesPago.setLayout(new GridLayout(3, 1, 0, 20));
        panelOpcionesPago.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(38, 38, 38, 38)
        );
        panelOpcionesPago.setBorder(bordePanelBlanco);

        JButton btnEfectivo = crearBotonMetodo("Efectivo", e -> procesar("Efectivo"));
        JButton btnPayPal = crearBotonMetodo("PayPal", e -> procesar("PayPal"));
        JButton btnTarjeta = crearBotonMetodo("Tarjeta de Crédito / Débito", e -> procesar("Tarjeta"));

        panelOpcionesPago.add(btnEfectivo);
        panelOpcionesPago.add(btnPayPal);
        panelOpcionesPago.add(btnTarjeta);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(40, 300, 60, 300));
        contenedorCentral.add(panelOpcionesPago, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        setVisible(true);
    }

    private void procesar(String metodo) {
        controlador.procesarPago(this.pedido, metodo);
    }

    private JButton crearBotonMetodo(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsSemiBold(16f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(NARANJA_OSCURO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(NARANJA);
            }
        });

        btn.addActionListener(listener);
        return btn;
    }
}
