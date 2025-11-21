package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.PersonalizarPedidoControlador;
import com.itson.presentacion.util.Colores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class PersonalizarPedidoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public PersonalizarPedidoFrame() {
        cargarFuentePoppins();

        setTitle("Personalizar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel();
        headerContenido.setLayout(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Personalizar el pedido", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);

        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(CREMA);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        panelContenido.add(crearSeccion("Ingrediente Principal"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccion("Complemento"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccion("Extras"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccionComentarios("Comentarios"));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(CREMA);
        scrollPane.setBackground(CREMA);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setBackground(CREMA);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(NARANJA);
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(NARANJA);
        btnCancelar.setForeground(BLANCO);
        btnCancelar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        PersonalizarPedidoControlador controlador = new PersonalizarPedidoControlador();

        btnConfirmar.addActionListener(e -> controlador.confirmarPersonalizacion(this));
        btnCancelar.addActionListener(e -> controlador.cancelarPersonalizacion(this));

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearSeccion(String titulo) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setBackground(CREMA);

        JLabel lblSeccionTitulo = new JLabel(titulo);
        lblSeccionTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 14f));
        lblSeccionTitulo.setForeground(BLANCO);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(NARANJA);
        panelTitulo.add(lblSeccionTitulo);

        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new GridLayout(4, 2, 10, 10));
        opcionesPanel.setBackground(CREMA);
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        for (int i = 0; i < 8; i++) {
            JPanel opcion = new JPanel();
            opcion.setLayout(new FlowLayout(FlowLayout.LEFT));
            opcion.setBackground(BLANCO);
            opcion.setBorder(BorderFactory.createLineBorder(GRIS));

            JCheckBox check = new JCheckBox("Opción " + (i + 1));
            check.setBackground(BLANCO);
            check.setFont(fuentePoppinsRegular.deriveFont(14f));

            opcion.add(check);
            opcionesPanel.add(opcion);
        }

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(opcionesPanel, BorderLayout.CENTER);

        return contenedor;
    }

    private JPanel crearSeccionComentarios(String titulo) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setBackground(CREMA);

        JLabel lblSeccionTitulo = new JLabel(titulo);
        lblSeccionTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 14f));
        lblSeccionTitulo.setForeground(BLANCO);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(NARANJA);
        panelTitulo.add(lblSeccionTitulo);

        JTextArea areaTextoComentarios = new JTextArea(5, 20);
        areaTextoComentarios.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 14f));
        areaTextoComentarios.setLineWrap(true);
        areaTextoComentarios.setWrapStyleWord(true);

        Border bordeAreaComentarios = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        areaTextoComentarios.setBorder(bordeAreaComentarios);

        JScrollPane scrollComentarios = new JScrollPane(areaTextoComentarios);
        scrollComentarios.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        scrollComentarios.setPreferredSize(new Dimension(400, 120));
        scrollComentarios.getViewport().setBackground(CREMA);
        scrollComentarios.setBackground(CREMA);

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(scrollComentarios, BorderLayout.CENTER);

        return contenedor;
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regularStream = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream boldStream = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            fuentePoppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            fuentePoppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePoppinsRegular);
            ge.registerFont(fuentePoppinsBold);

        } catch (FontFormatException | IOException e) {
            fuentePoppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            fuentePoppinsBold = new Font("SansSerif", Font.BOLD, 14);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PersonalizarPedidoFrame().setVisible(true));
    }
}
