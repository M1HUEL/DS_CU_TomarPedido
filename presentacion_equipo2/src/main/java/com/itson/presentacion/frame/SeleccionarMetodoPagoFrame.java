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

public class SeleccionarMetodoPagoFrame extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color COLOR_PRIMARIO = Colores.NARANJA;
    private final Color COLOR_PRIMARIO_CLARO = Colores.NARANJA_CLARO;
    private final Color COLOR_FONDO = Colores.CREMA;
    private final Color COLOR_BLANCO = Colores.BLANCO;
    private final Color COLOR_BORDE = Colores.GRIS_BORDE;

    private JPanel panelHeader, panelHeaderContenido, panelPrincipal, panelOpcionesPago, panelCentro;
    private JLabel lblTitulo, lblSubtitulo;

    private SeleccionarMetodoPagoControlador controlador = new SeleccionarMetodoPagoControlador();

    public SeleccionarMetodoPagoFrame() {
        cargarFuentePoppins();

        setTitle("Seleccionar Método de Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(COLOR_PRIMARIO);
        panelHeader.setPreferredSize(new Dimension(1440, 160));

        panelHeaderContenido = new JPanel(new GridLayout(2, 1));
        panelHeaderContenido.setBackground(COLOR_PRIMARIO);
        panelHeaderContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblTitulo = new JLabel("Seleccionar Método de Pago", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        lblSubtitulo = new JLabel("Elige una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        panelHeaderContenido.add(lblTitulo);
        panelHeaderContenido.add(lblSubtitulo);
        panelHeader.add(panelHeaderContenido, BorderLayout.CENTER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);

        panelOpcionesPago = new JPanel(new GridLayout(4, 1, 0, 20));
        panelOpcionesPago.setBackground(COLOR_BLANCO);
        panelOpcionesPago.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1),
                        BorderFactory.createEmptyBorder(60, 120, 60, 120)
                )
        );

        String[] metodosPago = {
            "Efectivo",
            "Tarjeta de Crédito",
            "Tarjeta de Débito",
            "Transferencia"
        };

        for (String metodo : metodosPago) {

            JButton btnMetodoPago = new JButton(metodo);
            btnMetodoPago.setBackground(COLOR_PRIMARIO);
            btnMetodoPago.setForeground(COLOR_BLANCO);
            btnMetodoPago.setFont(fuentePoppinsRegular.deriveFont(15f));
            btnMetodoPago.setFocusPainted(false);
            btnMetodoPago.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnMetodoPago.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            btnMetodoPago.addActionListener(e -> controlador.seleccionarMetodoPago(this, metodo));

            btnMetodoPago.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btnMetodoPago.setBackground(COLOR_PRIMARIO_CLARO);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btnMetodoPago.setBackground(COLOR_PRIMARIO);
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    btnMetodoPago.setBackground(COLOR_PRIMARIO_CLARO.darker());
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    btnMetodoPago.setBackground(COLOR_PRIMARIO_CLARO);
                }
            });

            panelOpcionesPago.add(btnMetodoPago);
        }

        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(COLOR_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        panelCentro.add(panelOpcionesPago, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

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
