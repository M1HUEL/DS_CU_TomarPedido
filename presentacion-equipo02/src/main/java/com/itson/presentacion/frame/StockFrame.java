package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.GestionInventarioController;
import com.itson.presentacion.controller.impl.GestionInventarioControllerImpl;
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

public class StockFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private List<Insumo> listaInsumosActual;

    private final GestionInventarioController controlador = new GestionInventarioControllerImpl(this);
    private final Usuario usuarioLogueado;

    public StockFrame() {
        super("Gestión de Inventario");

        this.usuarioLogueado = Sesion.getInstancia().getUsuarioLogueado();

        if (usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "Acceso Denegado: No hay sesión activa.", "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        Rol rol = usuarioLogueado.getRol();
        if (rol != Rol.ADMINISTRADOR && rol != Rol.COCINERO) {
            JOptionPane.showMessageDialog(null,
                    "Acceso Denegado: Tu rol (" + rol + ") no tiene permiso para ver el inventario.",
                    "Permisos Insuficientes", JOptionPane.WARNING_MESSAGE);
            dispose();
            new InicioFrame().setVisible(true);
            return;
        }

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

        JLabel lblTitulo = new JLabel("Inventario de Insumos", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(28f));
        lblTitulo.setForeground(BLANCO);

        JButton btnVolver = crearBoton("Volver al Menú", false);
        btnVolver.addActionListener(e -> controlador.regresar());

        JPanel pnlRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 30));
        pnlRight.setOpaque(false);
        pnlRight.add(btnVolver);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(pnlRight, BorderLayout.EAST);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        toolbar.setBackground(CREMA);

        JButton btnAgregar = crearBoton("+ Nuevo Insumo", true);
        btnAgregar.addActionListener(e -> controlador.agregarInsumo());

        JButton btnEditar = crearBoton("Editar Seleccionado", true);
        btnEditar.addActionListener(e -> accionEditar());

        JButton btnEliminar = crearBoton("Eliminar Seleccionado", true);
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> accionEliminar());

        JButton btnAlertas = new JButton("Ver Alertas");
        btnAlertas.setBackground(new Color(229, 57, 53));
        btnAlertas.setForeground(Color.WHITE);
        btnAlertas.setFont(Fuentes.getPoppinsBold(14f));
        btnAlertas.addActionListener(e -> new AlertasStockFrame().setVisible(true));

        if (usuarioLogueado.getRol() == Rol.ADMINISTRADOR) {
            toolbar.add(btnAgregar);
            toolbar.add(btnEditar);
            toolbar.add(btnEliminar);
            toolbar.add(btnAlertas);
        } else {
            JLabel lblInfo = new JLabel("<html><b style='color:#E65100'>MODO LECTURA:</b> Solo el administrador puede modificar el inventario.</html>");
            lblInfo.setFont(Fuentes.getPoppinsRegular(16f));
            toolbar.add(lblInfo);
            toolbar.add(btnAlertas);
        }

        String[] columnas = {"Código", "Nombre del Insumo", "Stock Actual", "Unidad", "Estado Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaInventario = new JTable(modeloTabla);
        estilizarTabla();

        JScrollPane scrollTabla = new JScrollPane(tablaInventario);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.getViewport().setBackground(CREMA);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(CREMA);
        panelCentral.setBorder(new EmptyBorder(0, 50, 40, 50));

        panelCentral.add(toolbar, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void estilizarTabla() {
        tablaInventario.setRowHeight(40);
        tablaInventario.setBackground(BLANCO);
        tablaInventario.setFont(Fuentes.getPoppinsRegular(14f));
        tablaInventario.setSelectionBackground(new Color(255, 224, 178));
        tablaInventario.setSelectionForeground(Color.BLACK);
        tablaInventario.setShowVerticalLines(false);
        tablaInventario.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tablaInventario.getTableHeader();
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
        tablaInventario.getColumnModel().getColumn(0).setCellRenderer(centro);
        tablaInventario.getColumnModel().getColumn(2).setCellRenderer(centro);
        tablaInventario.getColumnModel().getColumn(3).setCellRenderer(centro);

        tablaInventario.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(Fuentes.getPoppinsBold(12f));
                String estado = (String) value;
                if ("BAJO".equals(estado)) {
                    setForeground(Color.RED);
                } else {
                    setForeground(new Color(0, 128, 0));
                }
                return c;
            }
        });
    }

    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        this.listaInsumosActual = controlador.cargarInventario();

        for (Insumo i : listaInsumosActual) {
            String estadoStock = (i.getStockActual() <= 5.0) ? "BAJO" : "OK";
            Object[] fila = {
                i.getCodigo(),
                i.getNombre(),
                String.format("%.2f", i.getStockActual()),
                i.getUnidadMedida(),
                estadoStock
            };
            modeloTabla.addRow(fila);
        }
    }

    private void accionEditar() {
        int fila = tablaInventario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo primero.");
            return;
        }
        Insumo seleccionado = listaInsumosActual.get(fila);
        controlador.editarInsumo(seleccionado);
    }

    private void accionEliminar() {
        int fila = tablaInventario.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo primero.");
            return;
        }
        Insumo seleccionado = listaInsumosActual.get(fila);
        controlador.eliminarInsumo(seleccionado);
    }

    private JButton crearBoton(String texto, boolean estiloAccion) {
        JButton btn = new JButton(texto);
        if (estiloAccion) {
            btn.setBackground(NARANJA);
            btn.setForeground(BLANCO);
            btn.setPreferredSize(new Dimension(200, 40));
        } else {
            btn.setBackground(BLANCO);
            btn.setForeground(NARANJA);
            btn.setPreferredSize(new Dimension(150, 40));
        }
        btn.setFont(Fuentes.getPoppinsBold(14f));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground().equals(NARANJA)) {
                    btn.setBackground(new Color(230, 81, 0));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getForeground().equals(BLANCO) && !btn.getText().contains("Eliminar")) {
                    btn.setBackground(NARANJA);
                }
            }
        });

        return btn;
    }
}
