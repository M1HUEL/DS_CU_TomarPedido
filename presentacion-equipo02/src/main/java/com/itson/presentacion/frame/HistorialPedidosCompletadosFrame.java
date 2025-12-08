package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.HistorialPedidosCompletadosController;
import com.itson.presentacion.controller.impl.HistorialPedidosCompletadosControllerImpl;
import com.itson.util.sesion.Sesion;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class HistorialPedidosCompletadosFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private final HistorialPedidosCompletadosController controlador;

    public HistorialPedidosCompletadosFrame() {
        super("Historial de Ventas Finalizadas");

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Acceso Denegado: No hay sesión activa.", "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            throw new IllegalStateException("Sesión nula");
        }

        if (usuario.getRol() != Rol.ADMINISTRADOR) {
            JOptionPane.showMessageDialog(null, "Acceso Restringido: Solo el Administrador puede ver el historial.", "Permisos", JOptionPane.WARNING_MESSAGE);
            dispose();
            new InicioFrame().setVisible(true);
            throw new IllegalStateException("Rol incorrecto");
        }

        this.controlador = new HistorialPedidosCompletadosControllerImpl(this);

        setSize(1440, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarUI();
        cargarDatos();
    }

    private void inicializarUI() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 100));
        header.setBorder(new EmptyBorder(0, 50, 0, 50));

        JLabel lblTitulo = new JLabel("Historial de Ventas", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 30));
        pnlRight.setOpaque(false);

        JButton btnPDF = crearBoton("Exportar PDF", true);
        btnPDF.addActionListener(e -> controlador.generarReportePDF());

        JButton btnVolver = crearBoton("Volver al Menú", false);
        btnVolver.addActionListener(e -> controlador.salir());

        pnlRight.add(btnPDF);
        pnlRight.add(btnVolver);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(pnlRight, BorderLayout.EAST);

        String[] columnas = {"Folio / ID", "Cliente", "Descripción Breve", "Total ($)", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaVentas = new JTable(modeloTabla);
        estilizarTabla();

        JScrollPane scrollTabla = new JScrollPane(tablaVentas);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.getViewport().setBackground(CREMA);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(CREMA);
        panelCentral.setBorder(new EmptyBorder(40, 50, 40, 50));
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(CREMA);

        add(header, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private void estilizarTabla() {
        tablaVentas.setRowHeight(40);
        tablaVentas.setBackground(BLANCO);
        tablaVentas.setFont(Fuentes.getPoppinsRegular(14f));
        tablaVentas.setSelectionBackground(new Color(255, 224, 178));
        tablaVentas.setSelectionForeground(Color.BLACK);
        tablaVentas.setShowVerticalLines(false);
        tablaVentas.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tablaVentas.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(NARANJA);
                setForeground(BLANCO);
                setFont(Fuentes.getPoppinsBold(14f));
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                return this;
            }
        });
        header.setPreferredSize(new Dimension(0, 50));

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        tablaVentas.getColumnModel().getColumn(3).setCellRenderer(centro);
        tablaVentas.getColumnModel().getColumn(4).setCellRenderer(centro);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = controlador.cargarVentas();

        double granTotal = 0.0;

        for (Pedido p : lista) {
            String resumenProd = "Sin productos";
            if (p.getProductos() != null && !p.getProductos().isEmpty()) {
                resumenProd = p.getProductos().size() + " arts: " + p.getProductos().get(0).getNombre();
                if (p.getProductos().size() > 1) {
                    resumenProd += "...";
                }
            }

            String idCorto = (p.getId() != null && p.getId().length() > 5)
                    ? "..." + p.getId().substring(p.getId().length() - 6)
                    : p.getId();

            Object[] fila = {
                idCorto,
                p.getNombre(),
                resumenProd,
                String.format("$%.2f", p.getPrecio()),
                p.getEstado().name()
            };
            modeloTabla.addRow(fila);

            granTotal += p.getPrecio();
        }

        if (!lista.isEmpty()) {
            Object[] filaTotal = {"", "", "TOTAL RECAUDADO:", String.format("$%.2f", granTotal), ""};
            modeloTabla.addRow(filaTotal);
        }
    }

    private JButton crearBoton(String texto, boolean estiloPrincipal) {
        JButton btn = new JButton(texto);
        btn.setFont(Fuentes.getPoppinsBold(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));

        if (estiloPrincipal) {
            btn.setBackground(BLANCO);
            btn.setForeground(new Color(230, 81, 0));
        } else {
            btn.setBackground(new Color(255, 200, 150));
            btn.setForeground(new Color(100, 50, 0));
        }

        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return btn;
    }
}
