package com.itson.presentacion.frame;

import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class SeleccionarMetodoPagoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS = Colores.GRIS;

    public SeleccionarMetodoPagoFrame() {
        super("Seleccionar Método de Pago");
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

        JLabel lblTitulo = new JLabel("Seleccionar Método de Pago", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- PANEL DE OPCIONES (TARJETA BLANCA) ---
        JPanel panelOpcionesPago = new JPanel();
        // 3 filas (Efectivo, PayPal, Tarjeta), 1 columna, espacio vertical de 20px
        panelOpcionesPago.setLayout(new GridLayout(3, 1, 0, 20));
        panelOpcionesPago.setBackground(BLANCO);

        // Borde compuesto: Línea gris + Padding interno grande
        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(60, 120, 60, 120)
        );
        panelOpcionesPago.setBorder(bordePanelBlanco);

        // Crear botones usando método auxiliar
        JButton btnEfectivo = crearBotonMetodo("Efectivo", e -> seleccionarMetodo("Efectivo"));
        JButton btnPayPal = crearBotonMetodo("PayPal", e -> seleccionarMetodo("PayPal"));
        JButton btnTarjeta = crearBotonMetodo("Tarjeta", e -> seleccionarMetodo("Tarjeta"));

        panelOpcionesPago.add(btnEfectivo);
        panelOpcionesPago.add(btnPayPal);
        panelOpcionesPago.add(btnTarjeta);

        // --- CONTENEDOR CENTRAL ---
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        // Margen externo para centrar la tarjeta blanca
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 350, 100, 350));
        contenedorCentral.add(panelOpcionesPago, BorderLayout.CENTER);

        // --- ARMADO FINAL ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    /**
     * Método auxiliar para crear los botones de método de pago con estilo
     * uniforme.
     */
    private JButton crearBotonMetodo(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsRegular(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Padding interno del botón
        btn.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btn.addActionListener(listener);
        return btn;
    }

    private void seleccionarMetodo(String metodo) {
        // Aquí iría la llamada a tu controlador real
        JOptionPane.showMessageDialog(this, "Has seleccionado: " + metodo);

        // Ejemplo: controlador.seleccionarMetodoPago(metodo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeleccionarMetodoPagoFrame().setVisible(true));
    }
}
