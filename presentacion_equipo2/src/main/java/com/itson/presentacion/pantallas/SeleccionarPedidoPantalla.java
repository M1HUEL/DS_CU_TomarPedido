package com.itson.presentacion.pantallas;

import com.itson.presentacion.controladores.SeleccionarPedidoControlador;
import com.itson.presentacion.interfaces.ISeleccionarPedido;
import com.itson.presentacion.pantallas.componentes.PedidoTarjeta;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SeleccionarPedidoPantalla extends JFrame {

    private final ISeleccionarPedido controlador;

    public SeleccionarPedidoPantalla() {
        this.controlador = new SeleccionarPedidoControlador();

        setTitle("Sistema Restaurante - Seleccionar Pedido");
        setSize(1440, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(230, 126, 34));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titulo = new JLabel("Selecciona el pedido", SwingConstants.LEFT);
        titulo.setFont(new Font("Poppins", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden 🍔", SwingConstants.LEFT);
        subtitulo.setFont(new Font("Poppins", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(255, 240, 220));

        JPanel textosHeader = new JPanel();
        textosHeader.setLayout(new BoxLayout(textosHeader, BoxLayout.Y_AXIS));
        textosHeader.setOpaque(false);
        textosHeader.add(titulo);
        textosHeader.add(Box.createVerticalStrut(5));
        textosHeader.add(subtitulo);

        headerPanel.add(textosHeader, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(new Color(255, 252, 245));
        contenedor.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel pedidosPanel = new JPanel(new GridLayout(0, 3, 30, 30));
        pedidosPanel.setBackground(new Color(255, 252, 245));

        ImageIcon imagenOriginal = new ImageIcon("./banner.png");
        Image imagenRedimensionada = imagenOriginal.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        ImageIcon iconoFinal = new ImageIcon(imagenRedimensionada);

        for (int i = 1; i <= 15; i++) {
            pedidosPanel.add(new PedidoTarjeta(i, iconoFinal, controlador));
        }

        JScrollPane scrollPane = new JScrollPane(pedidosPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 252, 245));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        contenedor.add(scrollPane, BorderLayout.CENTER);
        add(contenedor, BorderLayout.CENTER);

        controlador.mostrarPedidos();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SeleccionarPedidoPantalla::new);
    }
}
