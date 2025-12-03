package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controlador.PersonalizarPedidoControlador;
import com.itson.presentacion.controlador.impl.PersonalizarPedidoControladorImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class PersonalizarPedidoFrame extends JFrame {

    // Colors
    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;
    private final Color GRIS_SUAVE = new Color(220, 220, 220);

    // Logic
    private final Producto productoBase;
    private final PersonalizarPedidoControlador controlador;
    private final Map<Object, JCheckBox> mapaSeleccion = new HashMap<>();

    // Components
    private JTextArea areaComentario;

    public PersonalizarPedidoFrame(Producto producto) {
        this.productoBase = producto;
        this.controlador = new PersonalizarPedidoControladorImpl();

        setTitle("Personalizar: " + producto.getNombre());
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Don't exit app, just close window

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 140));

        JLabel lblTitulo = new JLabel("Personalizar " + producto.getNombre(), SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);
        lblTitulo.setBorder(new EmptyBorder(30, 100, 30, 100));
        header.add(lblTitulo, BorderLayout.CENTER);

        // --- CONTENT BODY ---
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(CREMA);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Safely retrieve lists (handle nulls)
        List<Ingrediente> ings = producto.getIngredientes() != null ? producto.getIngredientes() : List.of();
        List<Complemento> comps = producto.getComplementos() != null ? producto.getComplementos() : List.of();
        List<Extra> extras = producto.getExtras() != null ? producto.getExtras() : List.of();

        // Add Sections
        if (!ings.isEmpty()) {
            panelContenido.add(crearSeccion("Ingredientes", ings));
            panelContenido.add(Box.createVerticalStrut(15));
        }
        if (!comps.isEmpty()) {
            panelContenido.add(crearSeccion("Complementos", comps));
            panelContenido.add(Box.createVerticalStrut(15));
        }
        if (!extras.isEmpty()) {
            panelContenido.add(crearSeccion("Extras", extras));
            panelContenido.add(Box.createVerticalStrut(15));
        }

        panelContenido.add(crearSeccionComentarios("Instrucciones Especiales / Comentario"));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // --- BUTTONS ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        panelBotones.setBackground(CREMA);

        JButton btnConfirmar = crearBoton("Crear Pedido", e -> {
            controlador.completarPedido(productoBase, mapaSeleccion, areaComentario.getText());
            dispose(); // Close window after creating order
        });

        JButton btnCancelar = crearBoton("Cancelar", e -> {
            // Re-open selection frame or just close this one
            new SeleccionarPedidoFrame().setVisible(true);
            dispose();
        });

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // --- HELPER METHODS ---
    private JPanel crearSeccion(String titulo, List<?> elementos) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(CREMA);

        // Title Strip
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelTitulo.setBackground(NARANJA);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Fuentes.getPoppinsBold(16f));
        lblTitulo.setForeground(BLANCO);
        panelTitulo.add(lblTitulo);

        // Grid Content
        JPanel opcionesPanel = new JPanel(new GridLayout(0, 2, 15, 8));
        opcionesPanel.setBackground(CREMA);
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for (Object obj : elementos) {
            opcionesPanel.add(crearTarjetaOpcion(obj));
        }

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(opcionesPanel, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearTarjetaOpcion(Object obj) {
        String nombre = "";
        double precio = 0.0;

        // Determine type cleanly
        if (obj instanceof Ingrediente i) {
            nombre = i.getNombre();
            precio = 0.0;
        } else if (obj instanceof Complemento c) {
            nombre = c.getNombre();
            precio = c.getPrecio();
        } else if (obj instanceof Extra e) {
            nombre = e.getNombre();
            precio = e.getPrecio();
        }

        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_SUAVE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Icon
        JLabel lblImagen = new JLabel();
        ImageIcon icono = cargarIcono("/images/burger.png", 40, 40);
        if (icono != null) {
            lblImagen.setIcon(icono);
        }

        // Checkbox with Price formatting
        String precioStr = precio > 0 ? String.format(" ($%.2f)", precio) : "";
        JCheckBox check = new JCheckBox("<html>" + nombre + "<font color='#888888'>" + precioStr + "</font></html>");
        check.setFont(Fuentes.getPoppinsRegular(14f));
        check.setBackground(BLANCO);
        check.setFocusPainted(false);
        check.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        check.setSelected(true); // Default to selected

        // IMPORTANT: Map the Object to the Checkbox for the Controller
        mapaSeleccion.put(obj, check);

        card.add(lblImagen, BorderLayout.WEST);
        card.add(check, BorderLayout.CENTER);

        return card;
    }

    private JPanel crearSeccionComentarios(String titulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(CREMA);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelTitulo.setBackground(NARANJA);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Fuentes.getPoppinsBold(16f));
        lblTitulo.setForeground(BLANCO);
        panelTitulo.add(lblTitulo);

        areaComentario = new JTextArea(3, 20);
        areaComentario.setFont(Fuentes.getPoppinsRegular(14f));
        areaComentario.setLineWrap(true);
        areaComentario.setWrapStyleWord(true);

        Border bordeArea = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1, true),
                new EmptyBorder(8, 8, 8, 8)
        );
        areaComentario.setBorder(bordeArea);

        JScrollPane scroll = new JScrollPane(areaComentario);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);
        return contenedor;
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsSemiBold(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
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
        } // Fail silently
        return null;
    }
}
