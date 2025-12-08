package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.EstadoPedido;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.CocinaController;
import com.itson.presentacion.controller.impl.CocinaControllerImpl;
import com.itson.util.sesion.Sesion;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class CocinaFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS_OSCURO = Color.DARK_GRAY;

    private final Color COLOR_PENDIENTE = new Color(255, 82, 82);
    private final Color COLOR_PREPARACION = new Color(255, 179, 0);
    private final Color COLOR_LISTO = new Color(76, 175, 80);

    private JPanel panelContenedorTarjetas;
    private final CocinaController controlador = new CocinaControllerImpl(this);
    private final Usuario usuarioLogueado;

    public CocinaFrame() {
        this.usuarioLogueado = Sesion.getInstancia().getUsuarioLogueado();

        if (this.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null,
                    "Acceso Denegado: Debes iniciar sesión primero.",
                    "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            throw new IllegalStateException("No user logged in");
        }

        Rol rol = this.usuarioLogueado.getRol();
        if (rol != Rol.COCINERO && rol != Rol.ADMINISTRADOR && rol != Rol.CAJERO) {
            JOptionPane.showMessageDialog(null,
                    "Acceso Denegado: Tu rol (" + rol + ") no tiene acceso a la cocina.",
                    "Permisos Insuficientes", JOptionPane.WARNING_MESSAGE);
            dispose();
            new InicioFrame().setVisible(true);
            return;
        }

        setTitle("Monitor de Cocina - Usuario: " + usuarioLogueado.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        cargarPedidos();
    }

    private void inicializarComponentes() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 100));
        header.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));

        JLabel lblTitulo = new JLabel("Pedidos en Curso", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JButton btnActualizar = crearBotonHeader("Actualizar");
        btnActualizar.addActionListener(e -> cargarPedidos());

        JButton btnSalir = crearBotonHeader("Volver / Salir");
        btnSalir.addActionListener(e -> controlador.regresarInicio());

        JPanel panelBotonesHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 30));
        panelBotonesHeader.setOpaque(false);
        panelBotonesHeader.add(btnActualizar);
        panelBotonesHeader.add(btnSalir);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(panelBotonesHeader, BorderLayout.EAST);

        panelContenedorTarjetas = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        panelContenedorTarjetas.setBackground(CREMA);
        panelContenedorTarjetas.setPreferredSize(new Dimension(1400, 1000));

        JScrollPane scroll = new JScrollPane(panelContenedorTarjetas);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void cargarPedidos() {
        panelContenedorTarjetas.removeAll();
        List<Pedido> pedidos = controlador.obtenerPedidosPendientes();

        if (pedidos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay pedidos pendientes.", SwingConstants.CENTER);
            lblVacio.setFont(Fuentes.getPoppinsBold(24f));
            lblVacio.setForeground(GRIS_OSCURO);
            lblVacio.setPreferredSize(new Dimension(1300, 100));
            panelContenedorTarjetas.add(lblVacio);
        } else {
            for (Pedido p : pedidos) {
                panelContenedorTarjetas.add(crearTarjetaPedido(p));
            }
        }

        panelContenedorTarjetas.revalidate();
        panelContenedorTarjetas.repaint();
    }

    private JPanel crearTarjetaPedido(Pedido p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(320, 420));
        tarjeta.setBackground(BLANCO);

        Border borde = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        );
        tarjeta.setBorder(borde);

        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 0, 5));
        panelInfo.setBackground(BLANCO);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        String idTexto = (p.getId() != null) ? "ORD-" + p.getId().substring(Math.max(0, p.getId().length() - 4)) : "N/A";
        JLabel lblId = new JLabel(idTexto, SwingConstants.CENTER);
        lblId.setFont(Fuentes.getPoppinsBold(20f));
        lblId.setForeground(NARANJA);

        JLabel lblNombre = new JLabel(p.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(Fuentes.getPoppinsBold(14f));
        lblNombre.setForeground(GRIS_OSCURO);

        panelInfo.add(lblId);
        panelInfo.add(lblNombre);

        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(new Color(248, 248, 248));
        panelProductos.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        if (p.getProductos() != null) {
            for (Producto prod : p.getProductos()) {
                JLabel lblProd = new JLabel("• " + prod.getNombre());
                lblProd.setFont(Fuentes.getPoppinsRegular(14f));
                lblProd.setForeground(Color.BLACK);
                panelProductos.add(lblProd);
                panelProductos.add(Box.createVerticalStrut(8));
            }
        }

        if (p.getComentario() != null && !p.getComentario().isEmpty()) {
            panelProductos.add(Box.createVerticalStrut(10));
            JTextArea areaNota = new JTextArea("Nota: " + p.getComentario());
            areaNota.setFont(Fuentes.getPoppinsBold(12f));
            areaNota.setForeground(new Color(200, 0, 0));
            areaNota.setBackground(new Color(255, 240, 240));
            areaNota.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            areaNota.setLineWrap(true);
            areaNota.setWrapStyleWord(true);
            areaNota.setEditable(false);
            panelProductos.add(areaNota);
        }

        JScrollPane scrollProd = new JScrollPane(panelProductos);
        scrollProd.setBorder(null);
        scrollProd.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton btnAccion = new JButton();
        btnAccion.setFont(Fuentes.getPoppinsBold(14f));
        btnAccion.setForeground(Color.WHITE);
        btnAccion.setFocusPainted(false);
        btnAccion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAccion.setPreferredSize(new Dimension(0, 50));
        btnAccion.setBorder(BorderFactory.createEmptyBorder());

        EstadoPedido estado = p.getEstado();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }

        switch (estado) {
            case PENDIENTE -> {
                btnAccion.setText("Empezar a Cocinar");
                btnAccion.setBackground(COLOR_PENDIENTE);
            }
            case EN_PREPARACION -> {
                btnAccion.setText("Marcar como Listo");
                btnAccion.setBackground(COLOR_PREPARACION);
            }
            case LISTO -> {
                btnAccion.setText("Entregar Pedido");
                btnAccion.setBackground(COLOR_LISTO);
            }
            default -> {
                btnAccion.setText("Completado");
                btnAccion.setBackground(Color.GRAY);
            }
        }

        if (usuarioLogueado.getRol() == Rol.CAJERO) {
            btnAccion.setText("Vista Solo Lectura");
            btnAccion.setBackground(Color.GRAY);
            btnAccion.setEnabled(false);
        } else {
            btnAccion.addActionListener(e -> controlador.avanzarEstado(p));
        }

        tarjeta.add(panelInfo, BorderLayout.NORTH);
        tarjeta.add(scrollProd, BorderLayout.CENTER);
        tarjeta.add(btnAccion, BorderLayout.SOUTH);

        return tarjeta;
    }

    private JButton crearBotonHeader(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(BLANCO);
        btn.setForeground(NARANJA);
        btn.setFont(Fuentes.getPoppinsBold(14f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(255, 240, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BLANCO);
            }
        });

        return btn;
    }

}
