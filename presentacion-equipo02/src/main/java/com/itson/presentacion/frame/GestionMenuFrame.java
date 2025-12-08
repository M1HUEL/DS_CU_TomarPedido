package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Rol;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.GestionMenuController;
import com.itson.presentacion.controller.impl.GestionMenuControllerImpl;
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

public class GestionMenuFrame extends JFrame {

    private final Color NARANJA = Colores.NARANJA;
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;

    private JTable tablaMenu;
    private DefaultTableModel modeloTabla;
    private List<Producto> listaProductosActual;

    private final GestionMenuController controlador = new GestionMenuControllerImpl(this);

    public GestionMenuFrame() {
        super("Gestión del Menú Digital");

        Usuario usuario = Sesion.getInstancia().getUsuarioLogueado();

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Acceso Denegado: No hay sesión activa.", "Seguridad", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        if (usuario.getRol() != Rol.ADMINISTRADOR) {
            JOptionPane.showMessageDialog(null,
                    "Acceso Denegado: Esta pantalla es exclusiva para el Administrador.",
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

        JLabel lblTitulo = new JLabel("Catálogo de Productos", SwingConstants.LEFT);
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

        JButton btnAgregar = crearBoton("+ Nuevo Platillo", true);
        btnAgregar.addActionListener(e -> controlador.agregarProducto());

        JButton btnEditar = crearBoton("Editar Precio/Nombre", true);
        btnEditar.addActionListener(e -> accionEditar());

        JButton btnEliminar = crearBoton("Eliminar Platillo", true);
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> accionEliminar());

        toolbar.add(btnAgregar);
        toolbar.add(btnEditar);
        toolbar.add(btnEliminar);

        String[] columnas = {"ID", "Nombre del Platillo", "Precio ($)", "Detalles"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaMenu = new JTable(modeloTabla);
        estilizarTabla();

        JScrollPane scrollTabla = new JScrollPane(tablaMenu);
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
        tablaMenu.setRowHeight(40);
        tablaMenu.setBackground(BLANCO);
        tablaMenu.setFont(Fuentes.getPoppinsRegular(14f));
        tablaMenu.setSelectionBackground(new Color(255, 224, 178));
        tablaMenu.setSelectionForeground(Color.BLACK);
        tablaMenu.setShowVerticalLines(false);
        tablaMenu.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tablaMenu.getTableHeader();
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
        tablaMenu.getColumnModel().getColumn(0).setCellRenderer(centro);
        tablaMenu.getColumnModel().getColumn(2).setCellRenderer(centro);

        tablaMenu.getColumnModel().getColumn(1).setPreferredWidth(300);
    }

    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        this.listaProductosActual = controlador.cargarMenu();

        for (Producto p : listaProductosActual) {
            int ings = (p.getIngredientes() != null) ? p.getIngredientes().size() : 0;
            int extras = (p.getExtras() != null) ? p.getExtras().size() : 0;
            String info = ings + " Ingredientes, " + extras + " Extras config.";

            String id = (p.getId() != null && p.getId().length() > 5) ? "..." + p.getId().substring(p.getId().length() - 6) : p.getId();

            Object[] fila = {
                id,
                p.getNombre(),
                String.format("$%.2f", p.getPrecio()),
                info
            };
            modeloTabla.addRow(fila);
        }
    }

    private void accionEditar() {
        int fila = tablaMenu.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un platillo.");
            return;
        }
        Producto seleccionado = listaProductosActual.get(fila);
        controlador.editarProducto(seleccionado);
    }

    private void accionEliminar() {
        int fila = tablaMenu.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un platillo.");
            return;
        }
        Producto seleccionado = listaProductosActual.get(fila);
        controlador.eliminarProducto(seleccionado);
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
