package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.*;
import com.itson.presentacion.controller.HistorialPedidosController;
import com.itson.presentacion.controller.impl.HistorialPedidosControllerImpl;
import com.itson.util.sesion.Sesion;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class HistorialPedidosFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS_OSCURO = Color.DARK_GRAY;
    private final Color COLOR_PENDIENTE = new Color(255, 82, 82);
    private final Color COLOR_PREPARACION = new Color(255, 179, 0);
    private final Color COLOR_LISTO = new Color(76, 175, 80);

    private JPanel panelContenedorTarjetas;
    private final HistorialPedidosController controlador;
    private final Usuario usuarioLogueado;

    public HistorialPedidosFrame() {
        this.usuarioLogueado = Sesion.getInstancia().getUsuarioLogueado();
        if (this.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "Acceso Denegado.", "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            throw new IllegalStateException("No user logged in");
        }

        this.controlador = new HistorialPedidosControllerImpl(this);

        setTitle("Monitor de Pedidos en Curso");
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

        JLabel lblTitulo = new JLabel("Monitor de Pedidos", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JButton btnActualizar = crearBotonHeader("Actualizar");
        btnActualizar.addActionListener(e -> cargarPedidos());

        JButton btnSalir = crearBotonHeader("Volver");
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
        List<Pedido> pedidos = controlador.obtenerPedidosEnCurso();

        if (pedidos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay pedidos en curso.", SwingConstants.CENTER);
            lblVacio.setFont(Fuentes.getPoppinsBold(24f));
            lblVacio.setForeground(GRIS_OSCURO);
            lblVacio.setPreferredSize(new Dimension(1300, 100));
            panelContenedorTarjetas.add(lblVacio);
        } else {
            for (Pedido p : pedidos) {
                panelContenedorTarjetas.add(crearTarjetaSoloLectura(p));
            }
        }

        panelContenedorTarjetas.revalidate();
        panelContenedorTarjetas.repaint();
    }

    private JPanel crearTarjetaSoloLectura(Pedido p) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(320, 450));
        tarjeta.setBackground(BLANCO);

        Border sombra = new MatteBorder(1, 1, 4, 4, new Color(210, 210, 210));
        Border margenExterno = new EmptyBorder(10, 10, 10, 10);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(margenExterno, sombra));

        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(BLANCO);
        panelInfo.setBorder(new EmptyBorder(15, 15, 5, 15));

        String idTexto = (p.getId() != null) ? "#" + p.getId().substring(Math.max(0, p.getId().length() - 4)) : "N/A";
        JLabel lblId = new JLabel(idTexto, SwingConstants.CENTER);
        lblId.setFont(Fuentes.getPoppinsBold(14f));
        lblId.setForeground(Color.WHITE);
        lblId.setOpaque(true);
        lblId.setBackground(NARANJA);
        lblId.setPreferredSize(new Dimension(60, 24));

        JPanel panelIdWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelIdWrapper.setBackground(BLANCO);
        panelIdWrapper.add(lblId);

        JLabel lblNombre = new JLabel(p.getNombre());
        lblNombre.setFont(Fuentes.getPoppinsBold(16f));
        lblNombre.setForeground(GRIS_OSCURO);
        lblNombre.setBorder(new EmptyBorder(5, 0, 0, 0));

        panelInfo.add(panelIdWrapper, BorderLayout.NORTH);
        panelInfo.add(lblNombre, BorderLayout.CENTER);
        panelInfo.add(new JSeparator(), BorderLayout.SOUTH);

        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(BLANCO);
        panelProductos.setBorder(new EmptyBorder(10, 15, 10, 15));

        if (p.getProductos() != null) {
            for (Producto prod : p.getProductos()) {
                JLabel lblProd = new JLabel("• " + prod.getNombre());
                lblProd.setFont(Fuentes.getPoppinsBold(14f));
                lblProd.setForeground(Color.BLACK);
                panelProductos.add(lblProd);

                JPanel panelDetalles = new JPanel();
                panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
                panelDetalles.setBackground(BLANCO);
                panelDetalles.setBorder(new EmptyBorder(2, 15, 8, 0));

                if (prod.getIngredientes() != null) {
                    for (Ingrediente ing : prod.getIngredientes()) {
                        JLabel lblIng = new JLabel("- " + ing.getNombre());
                        lblIng.setFont(new Font("SansSerif", Font.PLAIN, 12));
                        lblIng.setForeground(Color.DARK_GRAY);
                        panelDetalles.add(lblIng);
                    }
                }
                if (prod.getExtras() != null) {
                    for (Extra ext : prod.getExtras()) {
                        JLabel lblExt = new JLabel("+ " + ext.getNombre());
                        lblExt.setFont(new Font("SansSerif", Font.BOLD, 12));
                        lblExt.setForeground(new Color(0, 100, 0));
                        panelDetalles.add(lblExt);
                    }
                }
                if (prod.getComplementos() != null) {
                    for (Complemento comp : prod.getComplementos()) {
                        JLabel lblComp = new JLabel("~ " + comp.getNombre());
                        lblComp.setFont(new Font("SansSerif", Font.ITALIC, 12));
                        lblComp.setForeground(Color.GRAY);
                        panelDetalles.add(lblComp);
                    }
                }
                panelProductos.add(panelDetalles);
                panelProductos.add(Box.createVerticalStrut(5));
            }
        }

        if (p.getComentario() != null && !p.getComentario().isEmpty()) {
            panelProductos.add(Box.createVerticalStrut(5));
            JTextArea areaNota = new JTextArea("Nota: " + p.getComentario());
            areaNota.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
            areaNota.setForeground(new Color(180, 0, 0));
            areaNota.setBackground(new Color(255, 245, 230));
            areaNota.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(1, 1, 1, 1, new Color(255, 200, 150)),
                    new EmptyBorder(5, 5, 5, 5)
            ));
            areaNota.setLineWrap(true);
            areaNota.setWrapStyleWord(true);
            areaNota.setEditable(false);
            panelProductos.add(areaNota);
        }

        JScrollPane scrollProd = new JScrollPane(panelProductos);
        scrollProd.setBorder(null);
        scrollProd.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollProd.getViewport().setBackground(BLANCO);

        JLabel lblEstado = new JLabel();
        lblEstado.setFont(Fuentes.getPoppinsBold(14f));
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setOpaque(true);
        lblEstado.setPreferredSize(new Dimension(0, 45));

        EstadoPedido estado = p.getEstado();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }

        switch (estado) {
            case PENDIENTE -> {
                lblEstado.setText("EN ESPERA");
                lblEstado.setBackground(COLOR_PENDIENTE);
            }
            case EN_PREPARACION -> {
                lblEstado.setText("PREPARANDO...");
                lblEstado.setBackground(COLOR_PREPARACION);
                lblEstado.setForeground(Color.DARK_GRAY);
            }
            case LISTO -> {
                lblEstado.setText("¡LISTO PARA RECOGER!");
                lblEstado.setBackground(COLOR_LISTO);
            }
            default -> {
                lblEstado.setText("FINALIZADO");
                lblEstado.setBackground(Color.GRAY);
            }
        }

        tarjeta.add(panelInfo, BorderLayout.NORTH);
        tarjeta.add(scrollProd, BorderLayout.CENTER);
        tarjeta.add(lblEstado, BorderLayout.SOUTH);

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
