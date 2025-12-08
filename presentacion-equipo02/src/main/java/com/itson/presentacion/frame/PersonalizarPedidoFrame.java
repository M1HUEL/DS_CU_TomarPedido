package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.*;
import com.itson.presentacion.controller.PersonalizarPedidoController;
import com.itson.presentacion.controller.impl.PersonalizarPedidoControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PersonalizarPedidoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    private final Producto producto;
    private final PersonalizarPedidoController controlador = new PersonalizarPedidoControllerImpl(this);

    private final Map<Ingrediente, JCheckBox> checkIngredientes = new HashMap<>();
    private final Map<Extra, JCheckBox> checkExtras = new HashMap<>();
    private final Map<Complemento, JCheckBox> checkComplementos = new HashMap<>();

    private JTextArea areaComentario;

    public PersonalizarPedidoFrame(Producto producto) {
        this.producto = producto;

        inicializarVentana();
        inicializarComponentes();
    }

    private void inicializarVentana() {
        setTitle("Personalizar: " + producto.getNombre());
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 140));

        JLabel lblTitulo = new JLabel("Personalizar " + producto.getNombre(), SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);
        lblTitulo.setBorder(new EmptyBorder(30, 100, 30, 100));
        header.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(CREMA);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        List<Ingrediente> ings = producto.getIngredientes() != null ? producto.getIngredientes() : new ArrayList<>();
        ings.addAll(controlador.obtenerIngredientesDisponibles());

        if (!ings.isEmpty()) {
            panelContenido.add(crearSeccionIngredientes("Ingredientes (Quitar/Poner)", ings));
            panelContenido.add(Box.createVerticalStrut(15));
        }

        List<Extra> extras = producto.getExtras() != null ? producto.getExtras() : new ArrayList<>();
        extras.addAll(controlador.obtenerExtrasDisponibles());

        if (!extras.isEmpty()) {
            panelContenido.add(crearSeccionExtras("Extras (Costo Adicional)", extras));
            panelContenido.add(Box.createVerticalStrut(15));
        }

        List<Complemento> complementos = producto.getComplementos() != null ? producto.getComplementos() : new ArrayList<>();
        complementos.addAll(controlador.obtenerComplementosDisponibles());

        if (!complementos.isEmpty()) {
            panelContenido.add(crearSeccionComplementos("Complementos y Salsas", complementos));
            panelContenido.add(Box.createVerticalStrut(15));
        }

        panelContenido.add(crearSeccionComentarios("Instrucciones Especiales"));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        panelBotones.setBackground(CREMA);

        JButton btnConfirmar = crearBoton("Continuar", e -> accionConfirmar());
        JButton btnCancelar = crearBoton("Cancelar", e -> controlador.cancelar());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void accionConfirmar() {
        List<Ingrediente> selIngredientes = new ArrayList<>();
        for (Map.Entry<Ingrediente, JCheckBox> entry : checkIngredientes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selIngredientes.add(entry.getKey());
            }
        }

        List<Extra> selExtras = new ArrayList<>();
        for (Map.Entry<Extra, JCheckBox> entry : checkExtras.entrySet()) {
            if (entry.getValue().isSelected()) {
                selExtras.add(entry.getKey());
            }
        }

        List<Complemento> selComplementos = new ArrayList<>();
        for (Map.Entry<Complemento, JCheckBox> entry : checkComplementos.entrySet()) {
            if (entry.getValue().isSelected()) {
                selComplementos.add(entry.getKey());
            }
        }

        controlador.procesarPedido(producto, selIngredientes, selExtras, selComplementos, areaComentario.getText());
    }

    private JPanel crearSeccionIngredientes(String titulo, List<Ingrediente> lista) {
        JPanel contenedor = crearContenedorSeccion(titulo);
        JPanel grid = (JPanel) contenedor.getComponent(1);

        for (Ingrediente i : lista) {
            JCheckBox chk = crearCheckBox(i.getNombre(), 0.0, true);
            checkIngredientes.put(i, chk);
            grid.add(crearTarjetaOpcion(chk));
        }
        return contenedor;
    }

    private JPanel crearSeccionExtras(String titulo, List<Extra> lista) {
        JPanel contenedor = crearContenedorSeccion(titulo);
        JPanel grid = (JPanel) contenedor.getComponent(1);

        for (Extra e : lista) {
            JCheckBox chk = crearCheckBox(e.getNombre(), e.getPrecio(), false);
            checkExtras.put(e, chk);
            grid.add(crearTarjetaOpcion(chk));
        }
        return contenedor;
    }

    private JPanel crearSeccionComplementos(String titulo, List<Complemento> lista) {
        JPanel contenedor = crearContenedorSeccion(titulo);
        JPanel grid = (JPanel) contenedor.getComponent(1);

        for (Complemento c : lista) {
            JCheckBox chk = crearCheckBox(c.getNombre(), c.getPrecio(), false);
            checkComplementos.put(c, chk);
            grid.add(crearTarjetaOpcion(chk));
        }
        return contenedor;
    }

    private JPanel crearContenedorSeccion(String titulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(CREMA);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelTitulo.setBackground(NARANJA);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(Fuentes.getPoppinsBold(16f));
        lblTitulo.setForeground(BLANCO);
        panelTitulo.add(lblTitulo);

        JPanel grid = new JPanel(new GridLayout(0, 3, 15, 8));
        grid.setBackground(CREMA);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(grid, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearTarjetaOpcion(JCheckBox checkBox) {
        JPanel card = new JPanel(new BorderLayout(5, 0));
        card.setBackground(BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        card.add(checkBox, BorderLayout.CENTER);
        return card;
    }

    private JCheckBox crearCheckBox(String nombre, double precio, boolean selected) {
        String precioStr = precio > 0 ? String.format(" (+$%.2f)", precio) : "";
        JCheckBox check = new JCheckBox("<html>" + nombre + " <font color='#888888'>" + precioStr + "</font></html>");
        check.setFont(Fuentes.getPoppinsRegular(14f));
        check.setBackground(BLANCO);
        check.setFocusPainted(false);
        check.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        check.setSelected(selected);
        return check;
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
        areaComentario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1, true),
                new EmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane scroll = new JScrollPane(areaComentario);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);
        return contenedor;
    }

    private JButton crearBoton(String texto, ActionListener e) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsRegular(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        btn.addActionListener(e);
        return btn;
    }
}
