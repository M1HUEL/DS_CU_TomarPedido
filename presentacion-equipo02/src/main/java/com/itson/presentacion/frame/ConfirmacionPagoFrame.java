package com.itson.presentacion.frame;

import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class ConfirmacionPagoFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color NARANJA_OSCURO = new Color(230, 81, 0);
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS_CLARO = new Color(248, 248, 248); // Fondo muy sutil
    private final Color VERDE_EXITO = new Color(76, 175, 80);

    public ConfirmacionPagoFrame() {
        super("Pago Confirmado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 100));

        JPanel headerContenido = new JPanel(new BorderLayout());
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        JLabel lblTitulo = new JLabel("Proceso Finalizado", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(24f));
        lblTitulo.setForeground(BLANCO);

        JPanel titulosHeader = new JPanel();
        titulosHeader.setLayout(new BoxLayout(titulosHeader, BoxLayout.Y_AXIS));
        titulosHeader.setBackground(NARANJA);
        titulosHeader.add(Box.createVerticalGlue()); // Centrar verticalmente en header
        titulosHeader.add(lblTitulo);
        titulosHeader.add(Box.createVerticalGlue());

        headerContenido.add(titulosHeader, BorderLayout.CENTER);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- TARJETA DE ÉXITO (CENTRO) ---
        JPanel tarjetaExito = crearTarjetaExito();

        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.add(tarjetaExito);

        // --- ARMADO FINAL ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearTarjetaExito() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);

        // Tarjeta más compacta y vertical (Estilo Recibo)
        tarjeta.setPreferredSize(new Dimension(420, 500));

        Border bordeTarjeta = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );
        tarjeta.setBorder(bordeTarjeta);

        // 1. ICONO
        JLabel lblIcono = new JLabel();
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        // Icono tamaño medio (80x80)
        ImageIcon icono = cargarIcono("/images/check.png", 80, 80);
        if (icono != null) {
            lblIcono.setIcon(icono);
        } else {
            lblIcono.setText("✔");
            lblIcono.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 60));
            lblIcono.setForeground(VERDE_EXITO);
        }

        // 2. TEXTO PRINCIPAL
        JLabel lblExito = new JLabel("¡Pago Exitoso!");
        lblExito.setAlignmentX(CENTER_ALIGNMENT);
        // Fuente reducida (36 -> 22)
        lblExito.setFont(Fuentes.getPoppinsBold(22f));
        lblExito.setForeground(NARANJA);

        JLabel lblSub = new JLabel("Orden enviada a cocina");
        lblSub.setAlignmentX(CENTER_ALIGNMENT);
        // Fuente reducida (18 -> 14)
        lblSub.setFont(Fuentes.getPoppinsRegular(14f));
        lblSub.setForeground(Color.GRAY);

        // 3. CAJA DE DETALLES (Ticket)
        JPanel panelContenidoDetalles = new JPanel();
        panelContenidoDetalles.setLayout(new BoxLayout(panelContenidoDetalles, BoxLayout.Y_AXIS));
        panelContenidoDetalles.setBackground(GRIS_CLARO);
        // Padding interno del ticket
        panelContenidoDetalles.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Datos simulados
        String folio = "ORD-" + (new Random().nextInt(9000) + 1000);
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

        // Filas compactas
        agregarFilaDetalle(panelContenidoDetalles, "Folio:", folio);
        agregarFilaDetalle(panelContenidoDetalles, "Fecha:", fecha);
        agregarFilaDetalle(panelContenidoDetalles, "Hora:", hora);

        // Línea divisoria dentro del ticket
        JSeparator lineaTicket = new JSeparator();
        lineaTicket.setForeground(Color.LIGHT_GRAY);
        lineaTicket.setMaximumSize(new Dimension(300, 1));

        panelContenidoDetalles.add(Box.createVerticalStrut(5));
        panelContenidoDetalles.add(lineaTicket);
        panelContenidoDetalles.add(Box.createVerticalStrut(5));

        agregarFilaDetalle(panelContenidoDetalles, "Método:", "Tarjeta **** 1234");
        agregarFilaDetalle(panelContenidoDetalles, "Estado:", "Aprobado");

        // ScrollPane pequeño para los detalles
        JScrollPane scrollDetalles = new JScrollPane(panelContenidoDetalles);
        scrollDetalles.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240))); // Borde muy sutil
        scrollDetalles.setBackground(GRIS_CLARO);
        scrollDetalles.getViewport().setBackground(GRIS_CLARO);
        scrollDetalles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Tamaño fijo para la caja de detalles
        scrollDetalles.setPreferredSize(new Dimension(300, 140));
        scrollDetalles.setMaximumSize(new Dimension(300, 140));

        // 4. BOTÓN
        JButton btnInicio = crearBotonInteractivo("Volver al Inicio", e -> {
            dispose();
            new InicioFrame().setVisible(true);
        });
        btnInicio.setAlignmentX(CENTER_ALIGNMENT);
        // Botón más delgado
        btnInicio.setMaximumSize(new Dimension(250, 45));

        // --- ENSAMBLAJE ---
        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(lblExito);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(scrollDetalles);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(btnInicio);
        tarjeta.add(Box.createVerticalGlue());

        return tarjeta;
    }

    private void agregarFilaDetalle(JPanel panel, String etiqueta, String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(GRIS_CLARO);
        fila.setMaximumSize(new Dimension(350, 22)); // Fila delgada (22px)

        JLabel lblEtiqueta = new JLabel(etiqueta);
        // Fuente pequeña y legible (13f)
        lblEtiqueta.setFont(Fuentes.getPoppinsBold(13f));
        lblEtiqueta.setForeground(Color.GRAY);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(Fuentes.getPoppinsRegular(13f));
        lblValor.setForeground(Color.DARK_GRAY);

        fila.add(lblEtiqueta, BorderLayout.WEST);
        fila.add(lblValor, BorderLayout.EAST);

        panel.add(fila);
    }

    private JButton crearBotonInteractivo(String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsSemiBold(14f)); // Fuente botón moderada
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(NARANJA_OSCURO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(NARANJA);
            }
        });

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
                ImageIcon original = new ImageIcon(url);
                Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                return new ImageIcon(escalada);
            }
        } catch (Exception e) {
            System.err.println("No se encontró la imagen: " + ruta);
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConfirmacionPagoFrame::new);
    }
}
