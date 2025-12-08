package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.ConfirmacionPagoController;
import com.itson.presentacion.controller.impl.ConfirmacionPagoControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.Border;

public class ConfirmacionPagoFrame extends JFrame {
    
    private final Color NARANJA = Colores.NARANJA;
    private final Color NARANJA_OSCURO = new Color(230, 81, 0);
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color GRIS_CLARO = Colores.GRIS_CLARO;
    private final Color VERDE_EXITO = Colores.VERDE;
    
    private final Pedido pedido;
    private final Pago pago;
    private final ConfirmacionPagoController controlador = new ConfirmacionPagoControllerImpl(this);
    
    public ConfirmacionPagoFrame(Pedido pedido, Pago pago) {
        this.pedido = pedido;
        this.pago = pago;
        
        setTitle("Confirmación!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
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
        titulosHeader.add(Box.createVerticalGlue());
        titulosHeader.add(lblTitulo);
        titulosHeader.add(Box.createVerticalGlue());
        
        headerContenido.add(titulosHeader, BorderLayout.CENTER);
        header.add(headerContenido, BorderLayout.CENTER);
        
        JPanel tarjetaExito = crearTarjetaExito();
        tarjetaExito.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnInicio = crearBoton("Volver al Inicio", e -> {
            controlador.terminarProceso();
        });
        btnInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInicio.setPreferredSize(new Dimension(300, 50));
        btnInicio.setMaximumSize(new Dimension(300, 50));
        
        JPanel contenidoVertical = new JPanel();
        contenidoVertical.setLayout(new BoxLayout(contenidoVertical, BoxLayout.Y_AXIS));
        contenidoVertical.setBackground(CREMA);
        contenidoVertical.add(tarjetaExito);
        contenidoVertical.add(Box.createVerticalStrut(40));
        contenidoVertical.add(btnInicio);
        contenidoVertical.add(Box.createVerticalStrut(60));
        
        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.add(contenidoVertical);
        
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
        tarjeta.setPreferredSize(new Dimension(500, 450));
        
        Border bordeTarjeta = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        );
        tarjeta.setBorder(bordeTarjeta);
        
        JLabel lblIcono = new JLabel();
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon icono = cargarIcono("/images/check.png", 90, 90);
        if (icono != null) {
            lblIcono.setIcon(icono);
        } else {
            lblIcono.setText("✔");
            lblIcono.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 70));
            lblIcono.setForeground(VERDE_EXITO);
        }
        
        JLabel lblExito = new JLabel("¡Pago Exitoso!");
        lblExito.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblExito.setFont(Fuentes.getPoppinsBold(26f));
        lblExito.setForeground(NARANJA);
        
        JLabel lblSub = new JLabel("Orden enviada a cocina");
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setFont(Fuentes.getPoppinsRegular(16f));
        lblSub.setForeground(Color.GRAY);
        
        JPanel panelContenidoDetalles = new JPanel();
        panelContenidoDetalles.setLayout(new BoxLayout(panelContenidoDetalles, BoxLayout.Y_AXIS));
        panelContenidoDetalles.setBackground(GRIS_CLARO);
        panelContenidoDetalles.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String folioReal = (pedido.getId() != null) ? pedido.getId().toString() : "PENDIENTE";
        String folioMostrar = folioReal.length() > 8 ? "..." + folioReal.substring(folioReal.length() - 8) : folioReal;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        String totalMostrar = String.format("$%.2f", pedido.getPrecio());
        
        String metodoMostrar = "Desconocido";
        if (pago != null && pago.getMetodoPago() != null) {
            switch (pago.getMetodoPago()) {
                case EFECTIVO:
                    metodoMostrar = "Efectivo";
                    break;
                case PAYPAL:
                    metodoMostrar = "PayPal";
                    break;
                case TARJETA_CREDITO:
                    metodoMostrar = "Tarjeta de Crédito";
                    break;
                case TARJETA_DEBITO:
                    metodoMostrar = "Tarjeta de Débito";
                    break;
                default:
                    metodoMostrar = pago.getMetodoPago().name();
            }
        }
        
        agregarFilaDetalle(panelContenidoDetalles, "Folio:", folioMostrar);
        agregarFilaDetalle(panelContenidoDetalles, "Fecha:", fecha);
        agregarFilaDetalle(panelContenidoDetalles, "Hora:", hora);
        
        JSeparator lineaTicket = new JSeparator();
        lineaTicket.setForeground(Color.LIGHT_GRAY);
        lineaTicket.setMaximumSize(new Dimension(400, 1));
        
        panelContenidoDetalles.add(Box.createVerticalStrut(10));
        panelContenidoDetalles.add(lineaTicket);
        panelContenidoDetalles.add(Box.createVerticalStrut(10));
        
        agregarFilaDetalle(panelContenidoDetalles, "Método:", metodoMostrar);
        agregarFilaDetalle(panelContenidoDetalles, "Total Pagado:", totalMostrar);
        
        JScrollPane scrollDetalles = new JScrollPane(panelContenidoDetalles);
        scrollDetalles.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        scrollDetalles.setBackground(GRIS_CLARO);
        scrollDetalles.getViewport().setBackground(GRIS_CLARO);
        scrollDetalles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDetalles.setPreferredSize(new Dimension(380, 160));
        scrollDetalles.setMaximumSize(new Dimension(380, 160));
        
        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lblExito);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(scrollDetalles);
        tarjeta.add(Box.createVerticalGlue());
        
        return tarjeta;
    }
    
    private void agregarFilaDetalle(JPanel panel, String etiqueta, String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(GRIS_CLARO);
        fila.setMaximumSize(new Dimension(400, 24));
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(Fuentes.getPoppinsBold(14f));
        lblEtiqueta.setForeground(Color.GRAY);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(Fuentes.getPoppinsRegular(14f));
        lblValor.setForeground(Color.DARK_GRAY);
        
        fila.add(lblEtiqueta, BorderLayout.WEST);
        fila.add(lblValor, BorderLayout.EAST);
        panel.add(fila);
    }
    
    private JButton crearBoton(String texto, ActionListener e) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsRegular(14f));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(240, 45));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(NARANJA_OSCURO);
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(NARANJA);
            }
        });
        
        btn.addActionListener(e);
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
        }
        return null;
    }
}
