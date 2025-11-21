package com.itson.presentacion.frame;

import com.itson.presentacion.controlador.SeleccionarMetodoPagoControlador;
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

public class SeleccionarMetodoPagoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public SeleccionarMetodoPagoFrame() {
        super("Seleccionar Método de Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarFuentePoppins();

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel();
        headerContenido.setLayout(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Seleccionar Método de Pago", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);

        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelOpcionesPago = new JPanel();
        panelOpcionesPago.setLayout(new GridLayout(4, 1, 0, 20));
        panelOpcionesPago.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        );

        panelOpcionesPago.setBorder(bordePanelBlanco);

        String[] metodosPago = {
            "Efectivo",
            "Transferencia"
        };

        SeleccionarMetodoPagoControlador controlador = new SeleccionarMetodoPagoControlador();

        for (String metodo : metodosPago) {
            JButton btnMetodoPago = new JButton(metodo);
            btnMetodoPago.setBackground(NARANJA);
            btnMetodoPago.setForeground(BLANCO);
            btnMetodoPago.setFont(fuentePoppinsRegular.deriveFont(14f));
            btnMetodoPago.setFocusPainted(false);
            btnMetodoPago.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnMetodoPago.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            btnMetodoPago.addActionListener(e -> controlador.seleccionarMetodoPago(this, metodo));

            panelOpcionesPago.add(btnMetodoPago);
        }

        JPanel contenedorCentral = new JPanel();
        contenedorCentral.setLayout(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        contenedorCentral.add(panelOpcionesPago, BorderLayout.CENTER);

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
        SwingUtilities.invokeLater(() -> new SeleccionarMetodoPagoFrame().setVisible(true));
    }
}
