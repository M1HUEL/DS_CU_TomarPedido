package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.SeleccionarPedidoController;
import com.itson.presentacion.controller.impl.SeleccionarPedidoControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.*;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

public class SeleccionarPedidoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;

    private SeleccionarPedidoController controlador;
    private JPanel panelTarjetas;

    public SeleccionarPedidoFrame() {
        super("Seleccionar Pedido");

        // Inicializar el controlador pasándole esta vista
        this.controlador = new SeleccionarPedidoControllerImpl(this);

        inicializarComponentes();
        cargarContenidoPanel();
    }

    private void inicializarComponentes() {
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

        JLabel lblTitulo = new JLabel("Selecciona tu producto", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige los productos disponibles y personaliza tu pedido", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- BODY ---
        panelTarjetas = new JPanel();
        panelTarjetas.setBackground(CREMA);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(40, 100, 60, 100));

        JScrollPane scrollTarjetas = new JScrollPane(panelTarjetas);
        scrollTarjetas.setBorder(null);
        scrollTarjetas.getVerticalScrollBar().setUnitIncrement(20);
        scrollTarjetas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTarjetas.getViewport().setBackground(CREMA);
        scrollTarjetas.setBackground(CREMA);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(scrollTarjetas, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void cargarContenidoPanel() {
        panelTarjetas.removeAll();

        // Llamada al controlador para obtener datos
        List<Producto> productos = controlador.obtenerProductos();

        if (productos.isEmpty()) {
            panelTarjetas.setLayout(new GridBagLayout());
            panelTarjetas.add(crearTarjetaVacia());
        } else {
            panelTarjetas.setLayout(new GridLayout(0, 3, 30, 30));
            for (Producto producto : productos) {
                panelTarjetas.add(crearTarjeta(producto));
            }
        }

        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    private JPanel crearTarjetaVacia() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setPreferredSize(new Dimension(450, 350));

        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        JLabel lblIcono = new JLabel("☹");
        lblIcono.setFont(new Font("SansSerif", Font.PLAIN, 60));
        lblIcono.setForeground(Color.LIGHT_GRAY);
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("We are sorry!");
        lblTitulo.setFont(Fuentes.getPoppinsBold(24f));
        lblTitulo.setForeground(NARANJA);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel("<html><div style='text-align: center;'>Sorry, we couldn't find any products<br>available at this moment.</div></html>");
        lblDesc.setFont(Fuentes.getPoppinsRegular(14f));
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(BLANCO);
        btnPanel.setMaximumSize(new Dimension(400, 50));

        JButton btnCancelar = new JButton("Cancel / Go Back");
        btnCancelar.setBackground(Color.LIGHT_GRAY);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFont(Fuentes.getPoppinsBold(13f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // DELEGACIÓN AL CONTROLADOR
        btnCancelar.addActionListener(e -> controlador.cancelarSeleccion());

        JButton btnRecargar = new JButton("Retry");
        btnRecargar.setBackground(NARANJA);
        btnRecargar.setForeground(BLANCO);
        btnRecargar.setFont(Fuentes.getPoppinsBold(13f));
        btnRecargar.setFocusPainted(false);
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecargar.addActionListener(e -> cargarContenidoPanel());

        btnPanel.add(btnCancelar);
        btnPanel.add(btnRecargar);

        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblDesc);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(btnPanel);

        return tarjeta;
    }

    private JPanel crearTarjeta(Producto producto) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 10));
        tarjeta.setBackground(BLANCO);

        Border bordeTarjeta = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        tarjeta.setBorder(bordeTarjeta);

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        String rutaImagen = obtenerRutaImagen(producto.getNombre());
        ImageIcon icono = cargarIcono(rutaImagen, 180, 180);

        if (icono != null) {
            lblImagen.setIcon(icono);
        } else {
            lblImagen.setText("Sin imagen");
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagen.setPreferredSize(new Dimension(180, 180));
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(BLANCO);

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(Fuentes.getPoppinsBold(18f));
        lblNombre.setForeground(Color.BLACK);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);

        String descTexto = "Deliciosa " + producto.getNombre().toLowerCase();
        JLabel lblDescripcion = new JLabel("<html><div style='text-align: center; width: 180px;'>" + descTexto + "</div></html>");
        lblDescripcion.setFont(Fuentes.getPoppinsRegular(12f));
        lblDescripcion.setForeground(Color.GRAY);
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblPrecio = new JLabel("$" + String.format("%.2f", producto.getPrecio()));
        lblPrecio.setFont(Fuentes.getPoppinsBold(20f));
        lblPrecio.setForeground(NARANJA);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblNombre);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblDescripcion);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblPrecio);
        infoPanel.add(Box.createVerticalStrut(10));

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBackground(NARANJA);
        btnSeleccionar.setForeground(Color.WHITE);
        btnSeleccionar.setFont(Fuentes.getPoppinsBold(14f));
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSeleccionar.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // DELEGACIÓN AL CONTROLADOR
        btnSeleccionar.addActionListener(e -> {
            controlador.seleccionarProducto(producto);
        });

        tarjeta.add(lblImagen, BorderLayout.NORTH);
        tarjeta.add(infoPanel, BorderLayout.CENTER);
        tarjeta.add(btnSeleccionar, BorderLayout.SOUTH);

        return tarjeta;
    }

    private String obtenerRutaImagen(String nombre) {
        return "/images/burger.png";
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                return null;
            }
            ImageIcon original = new ImageIcon(url);
            Image imagenOriginal = original.getImage();
            Image escalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeleccionarPedidoFrame().setVisible(true));
    }
}
