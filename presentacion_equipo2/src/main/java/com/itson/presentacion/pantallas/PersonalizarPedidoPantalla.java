package com.itson.presentacion.pantallas;

import com.itson.presentacion.controladores.PersonalizarPedidoControlador;
import com.itson.presentacion.pantallas.componentes.Boton;
import com.itson.presentacion.pantallas.componentes.CategoriaCheckBox;
import com.itson.presentacion.pantallas.componentes.Encabezado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class PersonalizarPedidoPantalla extends JFrame {

    private final String nombrePedido;
    private final List<JCheckBox> ingredientes = new ArrayList<>();
    private final PersonalizarPedidoControlador controlador;

    public PersonalizarPedidoPantalla(String nombrePedido) {
        this.nombrePedido = nombrePedido;
        this.controlador = new PersonalizarPedidoControlador(this, ingredientes, nombrePedido);

        setTitle("Personalizar " + nombrePedido);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new Encabezado("Personalizar Pedido"), BorderLayout.NORTH);

        JPanel contenido = crearContenidoPrincipal();

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(scroll, BorderLayout.CENTER);
        panelCentral.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearContenidoPrincipal() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(new Color(255, 252, 245));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Secciones de ingredientes
        contenido.add(new CategoriaCheckBox("🍔 Ingredientes principales", new String[]{
            "Queso extra", "Tocino", "Cebolla caramelizada",
            "Aguacate", "Lechuga", "Tomate fresco",
            "Pepinillos", "Champiñones"
        }, ingredientes));

        contenido.add(Box.createVerticalStrut(15));
        contenido.add(new JSeparator());
        contenido.add(Box.createVerticalStrut(15));

        contenido.add(new CategoriaCheckBox("🍟 Complementos", new String[]{
            "Papas fritas", "Aros de cebolla", "Pan integral",
            "Aderezo especial", "Kétchup", "Mostaza",
            "Salsa BBQ", "Mayonesa de ajo"
        }, ingredientes));

        contenido.add(Box.createVerticalStrut(15));
        contenido.add(new JSeparator());
        contenido.add(Box.createVerticalStrut(15));

        contenido.add(new CategoriaCheckBox("⭐ Extras", new String[]{
            "Refresco", "Malteada", "Postre",
            "Doble carne", "Extra salsa", "Queso cheddar",
            "Café", "Agua mineral"
        }, ingredientes));

        contenido.add(Box.createVerticalStrut(20));
        contenido.add(new JSeparator());
        contenido.add(Box.createVerticalStrut(15));

        // Comentarios
        contenido.add(controlador.crearPanelComentario());

        return contenido;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(255, 250, 240));

        Boton btnConfirmar = new Boton("Confirmar");
        btnConfirmar.addActionListener(e -> controlador.confirmarPedido());

        Boton btnCancelar = new Boton("Cancelar");
        btnCancelar.addActionListener(e -> controlador.cancelar());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        return panelBotones;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PersonalizarPedidoPantalla("Ejemplo"));
    }
}
