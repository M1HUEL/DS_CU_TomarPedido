package com.itson.presentacion.frame;

import com.itson.presentacion.controller.InicioController;
import com.itson.presentacion.controller.impl.InicioControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class InicioFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color BLANCO = Colores.BLANCO;
    private final Color CREMA = Colores.CREMA;
    private final Color NARANJA_OSCURO = new Color(230, 81, 0);
    private final Color GRIS = Colores.GRIS;

    // Los botones deben ser atributos de clase para asignarles listeners después
    private JButton btnCrearPedido;
    private JButton btnVerPedidos;
    private JButton btnDashboard;
    private JButton btnConfiguracion;
    private JButton btnCerrarSesion;

    private InicioController controller;

    public InicioFrame() {
        super("Inicio");
        inicializarComponentes();
    }

    /**
     * Este método es CLAVE. Se llama desde fuera para conectar la lógica una
     * vez que el Frame y el Controller ya existen.
     */
    public void setController(InicioController controller) {
        this.controller = controller;
        asignarEventos();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1624, 864);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 140));

        JPanel headerContenido = new JPanel(new BorderLayout());
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel lblTitulo = new JLabel("Inicio", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(30f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Selecciona una opción para continuar", SwingConstants.LEFT);
        lblSubtitulo.setFont(Fuentes.getPoppinsRegular(18f));
        lblSubtitulo.setForeground(BLANCO);

        JPanel titulosHeader = new JPanel();
        titulosHeader.setLayout(new BoxLayout(titulosHeader, BoxLayout.Y_AXIS));
        titulosHeader.setBackground(NARANJA);
        titulosHeader.add(lblTitulo);
        titulosHeader.add(Box.createVerticalStrut(5));
        titulosHeader.add(lblSubtitulo);

        headerContenido.add(titulosHeader, BorderLayout.CENTER);
        header.add(headerContenido, BorderLayout.CENTER);

        // --- PANEL DE BOTONES ---
        JPanel panelBlanco = new JPanel();
        panelBlanco.setLayout(new GridLayout(5, 1, 0, 15));
        panelBlanco.setBackground(BLANCO);

        Border bordePanelBlanco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS, 1),
                BorderFactory.createEmptyBorder(30, 150, 30, 150)
        );
        panelBlanco.setBorder(bordePanelBlanco);

        // Instanciamos los botones (sin listeners todavía)
        btnCrearPedido = crearBotonVisual("Crear Pedido", "/images/add.png");
        btnVerPedidos = crearBotonVisual("Ver Pedidos", "/images/list.png");
        btnDashboard = crearBotonVisual("Dashboard", "/images/chart.png");
        btnConfiguracion = crearBotonVisual("Configuración", "/images/settings.png");
        btnCerrarSesion = crearBotonVisual("Cerrar Sesión", "/images/logout.png");

        panelBlanco.add(btnCrearPedido);
        panelBlanco.add(btnVerPedidos);
        panelBlanco.add(btnDashboard);
        panelBlanco.add(btnConfiguracion);
        panelBlanco.add(btnCerrarSesion);

        // --- CONTENEDOR CENTRAL ---
        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(50, 350, 80, 350));
        contenedorCentral.add(panelBlanco, BorderLayout.CENTER);

        // --- ARMADO FINAL ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    /**
     * Aquí conectamos los botones con los métodos del controlador.
     */
    private void asignarEventos() {
        // Limpiamos listeners anteriores si hubiera
        for (ActionListener al : btnCrearPedido.getActionListeners()) {
            btnCrearPedido.removeActionListener(al);
        }
        // ... (repetir para otros si se reasigna el controlador dinámicamente)

        btnCrearPedido.addActionListener(e -> controller.crearPedido());
        btnVerPedidos.addActionListener(e -> controller.verPedidos());
        btnDashboard.addActionListener(e -> controller.mostrarDashboard());
        btnConfiguracion.addActionListener(e -> controller.configurar());
        btnCerrarSesion.addActionListener(e -> controller.cerrarSesion());
    }

    /**
     * Crea solo la parte visual del botón.
     */
    private JButton crearBotonVisual(String texto, String iconPath) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(BLANCO);
        btn.setFont(Fuentes.getPoppinsSemiBold(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon = cargarIcono(iconPath, 20, 20);
        if (icon != null) {
            btn.setIcon(icon);
            btn.setIconTextGap(10);
        }

        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

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

        return btn;
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getResource(ruta);
            if (url == null) {
                return null;
            }
            ImageIcon original = new ImageIcon(url);
            Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        } catch (Exception e) {
            return null;
        }
    }

    // --- MAIN DE PRUEBA O CONFIGURACIÓN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Crear la Vista (Frame)
            InicioFrame frame = new InicioFrame();

            // 2. Crear el Controlador (inyectándole la Vista)
            InicioController controller = new InicioControllerImpl(frame);

            // 3. Conectar el Controlador a la Vista
            frame.setController(controller);

            // 4. Mostrar
            frame.setVisible(true);
        });
    }
}
