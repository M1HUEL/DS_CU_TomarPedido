package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.PersonalizarPedidoControlador;
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

public class PersonalizarPedidoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color COLOR_PRIMARIO = new Color(255, 140, 0);
    private final Color COLOR_FONDO = new Color(255, 245, 230);
    private final Color COLOR_BLANCO = new Color(255, 255, 255);
    private final Color COLOR_BORDE = new Color(200, 200, 200);

    private JPanel header, headerContenido, panelPrincipal, panelContenido, panelBotones;
    private JLabel lblTitulo, lblSubtitulo;
    private JScrollPane scrollPane;
    private JButton btnConfirmar, btnCancelar;

    private PersonalizarPedidoControlador controlador = new PersonalizarPedidoControlador();

    public PersonalizarPedidoFrame() {
        cargarFuentePoppins();

        setTitle("Personalizar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setPreferredSize(new Dimension(1440, 160));

        headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(COLOR_PRIMARIO);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblTitulo = new JLabel("Personalizar el pedido", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(COLOR_FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        panelContenido.add(crearSeccion("Ingrediente Principal"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccion("Complemento"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccion("Extras"));
        panelContenido.add(Box.createVerticalStrut(20));

        panelContenido.add(crearSeccionComentarios("Comentarios"));

        scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(COLOR_FONDO);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setBackground(COLOR_FONDO);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(COLOR_PRIMARIO);
        btnConfirmar.setForeground(COLOR_BLANCO);
        btnConfirmar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(COLOR_PRIMARIO);
        btnCancelar.setForeground(COLOR_BLANCO);
        btnCancelar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(COLOR_FONDO);

        JLabel lblSeccionTitulo = new JLabel(titulo);
        lblSeccionTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 14f));
        lblSeccionTitulo.setForeground(COLOR_BLANCO);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(COLOR_PRIMARIO);
        panelTitulo.add(lblSeccionTitulo);

        JPanel opcionesPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        opcionesPanel.setBackground(COLOR_FONDO);
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        for (int i = 0; i < 8; i++) {
            JPanel opcion = new JPanel(new FlowLayout(FlowLayout.LEFT));
            opcion.setBackground(COLOR_BLANCO);
            opcion.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));

            JCheckBox check = new JCheckBox("Opción " + (i + 1));
            check.setBackground(COLOR_BLANCO);
            check.setFont(fuentePoppinsRegular.deriveFont(14f));

            opcion.add(check);
            opcionesPanel.add(opcion);
        }

        contenedor.add(panelTitulo, BorderLayout.NORTH);
        contenedor.add(opcionesPanel, BorderLayout.CENTER);

        return contenedor;
    }

    private JPanel crearSeccionComentarios(String titulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(COLOR_FONDO);

        JLabel lblSeccionTitulo = new JLabel(titulo);
        lblSeccionTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 14f));
        lblSeccionTitulo.setForeground(COLOR_BLANCO);

        JPanel panelComentariosTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelComentariosTitulo.setBackground(COLOR_PRIMARIO);
        panelComentariosTitulo.add(lblSeccionTitulo);

        JTextArea areaTextoComentarios = new JTextArea(5, 20);
        areaTextoComentarios.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 13f));
        areaTextoComentarios.setLineWrap(true);
        areaTextoComentarios.setWrapStyleWord(true);
        areaTextoComentarios.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
        );

        JScrollPane scrollComentarios = new JScrollPane(areaTextoComentarios);
        scrollComentarios.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        scrollComentarios.setPreferredSize(new Dimension(400, 120));
        scrollComentarios.getViewport().setBackground(COLOR_FONDO);
        scrollComentarios.setBackground(COLOR_FONDO);

        contenedor.add(panelComentariosTitulo, BorderLayout.NORTH);
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
