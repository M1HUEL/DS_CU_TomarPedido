package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.impl.ConfirmacionPedidoControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import com.itson.presentacion.controller.ConfirmacionPedidoController;

public class ConfirmacionPedidoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;
    private final Color GRIS_CLARO = new Color(248, 248, 248);

    private Pedido pedido;
    // Add the Controller
    private ConfirmacionPedidoController controlador;

    public ConfirmacionPedidoFrame(Pedido pedido) {
        this.pedido = pedido;
        // Initialize Controller
        this.controlador = new ConfirmacionPedidoControllerImpl();

        setTitle("Confirmación de Pedido");
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1680, 130));

        JPanel headerContenido = new JPanel(new BorderLayout());
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel lblTitulo = new JLabel("Confirmación de Pedido", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(32f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Revisa tu selección antes de proceder al pago", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(20f));
        lblSubtitulo.setForeground(new Color(255, 255, 255, 200));

        JPanel titulosHeader = new JPanel();
        titulosHeader.setLayout(new BoxLayout(titulosHeader, BoxLayout.Y_AXIS));
        titulosHeader.setBackground(NARANJA);
        titulosHeader.add(lblTitulo);
        titulosHeader.add(Box.createVerticalStrut(5));
        titulosHeader.add(lblSubtitulo);

        headerContenido.add(titulosHeader, BorderLayout.CENTER);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- TARJETA DE RESUMEN ---
        JPanel tarjetaResumen = crearTarjetaResumen();
        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.add(tarjetaResumen);

        JScrollPane scrollPrincipal = new JScrollPane(contenedorCentral);
        scrollPrincipal.setBorder(null);
        scrollPrincipal.getVerticalScrollBar().setUnitIncrement(20);
        scrollPrincipal.setBackground(CREMA);
        scrollPrincipal.getViewport().setBackground(CREMA);
        scrollPrincipal.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // --- BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        panelBotones.setBackground(CREMA);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // ACTION: Save to Database
        JButton btnAceptar = crearBoton("Confirmar y Pagar", e -> {
            // This calls the controller, saves to DB, and restarts the app
            controlador.guardarPedido(this.pedido);
            dispose(); // Close this window
        });

        // ACTION: Go back to start
        JButton btnCancelar = crearBoton("Volver / Cancelar", e -> {
            dispose();
            new SeleccionarPedidoFrame().setVisible(true);
        });

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(header, BorderLayout.NORTH);
        add(scrollPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel crearTarjetaResumen() {
        JPanel tarjeta = new JPanel(new BorderLayout(50, 0));
        tarjeta.setBackground(BLANCO);
        tarjeta.setPreferredSize(new Dimension(1400, 600));

        Border bordeTarjeta = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(50, 50, 50, 50)
        );
        tarjeta.setBorder(bordeTarjeta);

        // --- IMAGEN ---
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);

        ImageIcon icono = cargarIcono("/images/burger.png", 450, 450);
        if (icono != null) {
            lblImagen.setIcon(icono);
        }

        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(BLANCO);
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        panelImagen.setPreferredSize(new Dimension(500, 0));

        // --- DETALLES ---
        JPanel panelDerecho = new JPanel(new BorderLayout(0, 20));
        panelDerecho.setBackground(BLANCO);

        JPanel panelHeaderDerecho = new JPanel(new BorderLayout());
        panelHeaderDerecho.setBackground(BLANCO);

        // Handle case where Order Name might be null
        String nombrePedido = pedido.getNombre() != null ? pedido.getNombre().toUpperCase() : "PEDIDO PERSONALIZADO";
        JLabel lblNombre = new JLabel(nombrePedido);
        lblNombre.setFont(Fuentes.getPoppinsBold(28f));
        lblNombre.setForeground(Color.BLACK);

        JLabel lblDetalle = new JLabel("Resumen de consumo");
        lblDetalle.setFont(Fuentes.getPoppinsRegular(16f));
        lblDetalle.setForeground(GRIS);

        JPanel encabezadoTexto = new JPanel();
        encabezadoTexto.setLayout(new BoxLayout(encabezadoTexto, BoxLayout.Y_AXIS));
        encabezadoTexto.setBackground(BLANCO);
        encabezadoTexto.add(lblNombre);
        encabezadoTexto.add(lblDetalle);

        panelHeaderDerecho.add(encabezadoTexto, BorderLayout.WEST);
        panelHeaderDerecho.add(new JSeparator(), BorderLayout.SOUTH);
        panelHeaderDerecho.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panelDerecho.add(panelHeaderDerecho, BorderLayout.NORTH);

        // LISTA
        JPanel listaProductosPanel = new JPanel();
        listaProductosPanel.setLayout(new BoxLayout(listaProductosPanel, BoxLayout.Y_AXIS));
        listaProductosPanel.setBackground(BLANCO);

        List<Producto> productos = pedido.getProductos();
        if (productos != null && !productos.isEmpty()) {
            for (Producto p : productos) {
                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.setBackground(BLANCO);
                itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));

                JLabel lblProd = new JLabel(p.getNombre());
                lblProd.setFont(Fuentes.getPoppinsRegular(18f));
                lblProd.setForeground(Color.DARK_GRAY);

                JLabel lblPrecioProd = new JLabel("$" + String.format("%.2f", p.getPrecio()));
                lblPrecioProd.setFont(Fuentes.getPoppinsBold(18f));
                lblPrecioProd.setForeground(Color.BLACK);

                itemPanel.add(lblProd, BorderLayout.WEST);
                itemPanel.add(lblPrecioProd, BorderLayout.EAST);
                listaProductosPanel.add(itemPanel);
            }
        }

        JScrollPane scrollLista = new JScrollPane(listaProductosPanel);
        scrollLista.setBorder(null);
        scrollLista.getViewport().setBackground(BLANCO);
        scrollLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLista.getVerticalScrollBar().setUnitIncrement(16);

        panelDerecho.add(scrollLista, BorderLayout.CENTER);

        // FOOTER
        JPanel panelFooter = new JPanel();
        panelFooter.setLayout(new BoxLayout(panelFooter, BoxLayout.Y_AXIS));
        panelFooter.setBackground(BLANCO);

        JSeparator separadorFooter = new JSeparator();
        separadorFooter.setForeground(new Color(230, 230, 230));

        JPanel panelComentarioBox = new JPanel(new BorderLayout());
        panelComentarioBox.setBackground(GRIS_CLARO);
        panelComentarioBox.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblNotaTitulo = new JLabel("Instrucciones especiales:");
        lblNotaTitulo.setFont(Fuentes.getPoppinsBold(14f));
        lblNotaTitulo.setForeground(GRIS);

        String textoNota = (pedido.getComentario() != null && !pedido.getComentario().isEmpty())
                ? pedido.getComentario() : "Ninguna instrucción adicional.";

        JTextArea areaNota = new JTextArea(textoNota);
        areaNota.setFont(Fuentes.getPoppinsRegular(15f));
        areaNota.setForeground(Color.DARK_GRAY);
        areaNota.setBackground(GRIS_CLARO);
        areaNota.setLineWrap(true);
        areaNota.setWrapStyleWord(true);
        areaNota.setEditable(false);
        areaNota.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JScrollPane scrollNota = new JScrollPane(areaNota);
        scrollNota.setBorder(null);
        scrollNota.setBackground(GRIS_CLARO);
        scrollNota.setPreferredSize(new Dimension(100, 70));

        panelComentarioBox.add(lblNotaTitulo, BorderLayout.NORTH);
        panelComentarioBox.add(scrollNota, BorderLayout.CENTER);

        JPanel panelTotal = new JPanel(new BorderLayout());
        panelTotal.setBackground(BLANCO);
        panelTotal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTotalLabel = new JLabel("Total a Pagar");
        lblTotalLabel.setFont(Fuentes.getPoppinsRegular(22f));
        lblTotalLabel.setForeground(GRIS);

        JLabel lblTotalValue = new JLabel("$" + String.format("%.2f", pedido.getPrecio()));
        lblTotalValue.setFont(Fuentes.getPoppinsBold(42f));
        lblTotalValue.setForeground(NARANJA);

        panelTotal.add(lblTotalLabel, BorderLayout.WEST);
        panelTotal.add(lblTotalValue, BorderLayout.EAST);

        panelFooter.add(Box.createVerticalStrut(10));
        panelFooter.add(separadorFooter);
        panelFooter.add(Box.createVerticalStrut(20));
        panelFooter.add(panelComentarioBox);
        panelFooter.add(Box.createVerticalStrut(25));
        panelFooter.add(panelTotal);

        panelDerecho.add(panelFooter, BorderLayout.SOUTH);

        tarjeta.add(panelImagen, BorderLayout.WEST);
        tarjeta.add(panelDerecho, BorderLayout.CENTER);

        return tarjeta;
    }

    private JButton crearBoton(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsSemiBold(18f));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(18, 50, 18, 50));
        btn.addActionListener(listener);
        return btn;
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                url = getClass().getResource("/" + ruta);
            }
            if (url != null) {
                return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
        }
        return null;
    }
}
