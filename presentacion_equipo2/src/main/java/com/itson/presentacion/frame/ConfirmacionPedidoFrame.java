package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.ConfirmacionPedidoControlador;
import com.itson.presentacion.util.Colores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class ConfirmacionPedidoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public ConfirmacionPedidoFrame() {
        super("Confirmación de Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarFuentePoppins();

        /* ───────────────────────── HEADER ───────────────────────── */
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel();
        headerContenido.setLayout(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Confirmación del Pedido", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Revisa la información antes de confirmar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);

        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setLayout(new BorderLayout());
        panelMensaje.setBackground(BLANCO);

        Border bordePanelMensaje = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(80, 80, 80, 80)
        );

        panelMensaje.setBorder(bordePanelMensaje);

        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new GridLayout(2, 1, 0, 10));
        panelTexto.setBackground(BLANCO);

        JLabel lblNombrePedido = new JLabel("Combo Hamburguesa", SwingConstants.CENTER);
        lblNombrePedido.setFont(fuentePoppinsBold.deriveFont(24f));
        lblNombrePedido.setForeground(GRIS);

        JLabel lblPrecio = new JLabel("$30.00", SwingConstants.CENTER);
        lblPrecio.setFont(fuentePoppinsRegular.deriveFont(18f));
        lblPrecio.setForeground(GRIS);

        panelTexto.add(lblNombrePedido);
        panelTexto.add(lblPrecio);

        panelMensaje.add(panelTexto, BorderLayout.CENTER);

        JButton btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setBackground(NARANJA);
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(NARANJA);
        btnCancelar.setForeground(BLANCO);
        btnCancelar.setFont(fuentePoppinsRegular.deriveFont(14f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        btnCancelar.setPreferredSize(btnConfirmar.getPreferredSize());

        ConfirmacionPedidoControlador controlador = new ConfirmacionPedidoControlador();

        btnConfirmar.addActionListener(e -> controlador.confirmarPedido(this));
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(CREMA);
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));

        contenedorCentral.add(panelMensaje, BorderLayout.CENTER);
        contenedorCentral.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
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
        SwingUtilities.invokeLater(() -> new ConfirmacionPedidoFrame().setVisible(true));
    }
}
