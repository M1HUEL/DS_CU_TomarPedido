package com.itson.presentacion.frame;

import com.itson.persistencia.dominio.Insumo;
import com.itson.presentacion.controller.impl.AlertasStockControllerImpl;
import com.itson.presentacion.util.Colores;
import com.itson.presentacion.util.Fuentes;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AlertasStockFrame extends JFrame {

    private final Color ROJO_ALERTA = new Color(229, 57, 53);
    private final Color CREMA = Colores.CREMA;
    private final Color BLANCO = Colores.BLANCO;

    private JTable tablaAlertas;
    private DefaultTableModel modeloTabla;
    private final AlertasStockControllerImpl controlador;

    public AlertasStockFrame() {
        super("Alertas de Stock Bajo");
        this.controlador = new AlertasStockControllerImpl(this);

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarUI();
        cargarDatos();
    }

    private void inicializarUI() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ROJO_ALERTA);
        header.setPreferredSize(new Dimension(900, 100));
        header.setBorder(new EmptyBorder(0, 40, 0, 40));

        JLabel lblTitulo = new JLabel("⚠️ Insumos Críticos", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.getPoppinsBold(26f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSub = new JLabel("Estos productos están por agotarse o se agotaron.", SwingConstants.LEFT);
        lblSub.setFont(Fuentes.getPoppinsRegular(14f));
        lblSub.setForeground(new Color(255, 230, 230));

        JPanel panelTextos = new JPanel();
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
        panelTextos.setBackground(ROJO_ALERTA);
        panelTextos.add(Box.createVerticalGlue());
        panelTextos.add(lblTitulo);
        panelTextos.add(Box.createVerticalStrut(5));
        panelTextos.add(lblSub);
        panelTextos.add(Box.createVerticalGlue());

        header.add(panelTextos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(CREMA);
        panelBotones.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton btnGenerarLista = new JButton("Generar Lista de Compra");
        btnGenerarLista.setBackground(new Color(56, 142, 60));
        btnGenerarLista.setForeground(BLANCO);
        btnGenerarLista.setFont(Fuentes.getPoppinsBold(14f));
        btnGenerarLista.setFocusPainted(false);
        btnGenerarLista.addActionListener(e -> controlador.generarListaCompra());

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(Color.GRAY);
        btnCerrar.setForeground(BLANCO);
        btnCerrar.setFont(Fuentes.getPoppinsBold(14f));
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> controlador.cerrar());

        panelBotones.add(btnGenerarLista);
        panelBotones.add(btnCerrar);

        String[] columnas = {"Insumo", "Stock Actual", "Mínimo Requerido", "Déficit"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaAlertas = new JTable(modeloTabla);
        estilizarTabla();

        JScrollPane scroll = new JScrollPane(tablaAlertas);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(CREMA);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(CREMA);
        panelCentral.setBorder(new EmptyBorder(20, 40, 20, 40));
        panelCentral.add(scroll, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void estilizarTabla() {
        tablaAlertas.setRowHeight(35);
        tablaAlertas.setBackground(BLANCO);
        tablaAlertas.setFont(Fuentes.getPoppinsRegular(14f));
        tablaAlertas.setShowVerticalLines(false);

        JTableHeader header = tablaAlertas.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(ROJO_ALERTA);
                setForeground(BLANCO);
                setFont(Fuentes.getPoppinsBold(14f));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });
        header.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);
        tablaAlertas.getColumnModel().getColumn(1).setCellRenderer(centro);
        tablaAlertas.getColumnModel().getColumn(2).setCellRenderer(centro);
        tablaAlertas.getColumnModel().getColumn(3).setCellRenderer(centro);
    }

    private void cargarDatos() {
        List<Insumo> lista = controlador.cargarAlertas();
        modeloTabla.setRowCount(0);

        for (Insumo i : lista) {
            double deficit = i.getStockMinimo() - i.getStockActual();
            String deficitStr = String.format("%.2f %s", deficit, i.getUnidadMedida());

            Object[] fila = {
                i.getNombre(),
                String.format("%.2f", i.getStockActual()),
                String.format("%.2f", i.getStockMinimo()),
                deficitStr
            };
            modeloTabla.addRow(fila);
        }
    }
}
