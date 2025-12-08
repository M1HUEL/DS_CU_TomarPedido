package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Insumo;
import com.itson.presentacion.controller.GestionInventarioController;
import com.itson.presentacion.frame.InicioFrame;
import com.itson.presentacion.frame.StockFrame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GestionInventarioControllerImpl implements GestionInventarioController {

    private final StockFrame frame;
    private final RestauranteFachada fachada;

    public GestionInventarioControllerImpl(StockFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Insumo> cargarInventario() {
        try {
            return fachada.obtenerInsumos();
        } catch (RestauranteFachadaException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar inventario: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void agregarInsumo() {
        JTextField txtNombre = new JTextField();
        JTextField txtCodigo = new JTextField();
        JTextField txtUnidad = new JTextField();
        JTextField txtStock = new JTextField();

        JCheckBox chkIngrediente = new JCheckBox("Ingrediente (Base)");
        JCheckBox chkExtra = new JCheckBox("Extra (Costo Adicional)");
        JCheckBox chkComplemento = new JCheckBox("Complemento/Salsa");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre del Insumo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Código (Único):"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Unidad de Medida (kg, lt, pza):"));
        panel.add(txtUnidad);
        panel.add(new JLabel("Stock Inicial:"));
        panel.add(txtStock);

        panel.add(new JLabel("Categoría (Seleccione al menos una):"));
        panel.add(chkIngrediente);
        panel.add(chkExtra);
        panel.add(chkComplemento);

        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Registrar Nuevo Insumo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El nombre y el código son obligatorios.");
                    return;
                }

                double stock = Double.parseDouble(txtStock.getText());

                Insumo nuevo = new Insumo();
                nuevo.setNombre(txtNombre.getText());
                nuevo.setCodigo(txtCodigo.getText());
                nuevo.setUnidadMedida(txtUnidad.getText());
                nuevo.setStockActual(stock);
                nuevo.setStockMinimo(5.0);

                fachada.registrarInsumo(nuevo);

                JOptionPane.showMessageDialog(frame, "Insumo registrado exitosamente.");
                frame.cargarDatos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "El stock debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RestauranteFachadaException ex) {
                JOptionPane.showMessageDialog(frame, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void editarInsumo(Insumo insumo) {
        if (insumo == null) {
            return;
        }

        JTextField txtNombre = new JTextField(insumo.getNombre());
        JTextField txtCodigo = new JTextField(insumo.getCodigo());
        JTextField txtUnidad = new JTextField(insumo.getUnidadMedida());
        JTextField txtStock = new JTextField(String.valueOf(insumo.getStockActual()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre del Insumo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Unidad:"));
        panel.add(txtUnidad);
        panel.add(new JLabel("Stock Actual:"));
        panel.add(txtStock);

        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Editar Insumo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double stock = Double.parseDouble(txtStock.getText());

                insumo.setNombre(txtNombre.getText());
                insumo.setCodigo(txtCodigo.getText());
                insumo.setUnidadMedida(txtUnidad.getText());
                insumo.setStockActual(stock);

                fachada.actualizarInsumo(insumo);

                JOptionPane.showMessageDialog(frame, "Insumo actualizado correctamente.");
                frame.cargarDatos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "El stock debe ser numérico.");
            } catch (RestauranteFachadaException ex) {
                JOptionPane.showMessageDialog(frame, "Error al actualizar: " + ex.getMessage());
            }
        }
    }

    @Override
    public void eliminarInsumo(Insumo insumo) {
        if (insumo == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "¿Estás seguro de eliminar el insumo: " + insumo.getNombre() + "?\nEsta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                fachada.eliminarInsumo(insumo);

                JOptionPane.showMessageDialog(frame, "Insumo eliminado.");
                frame.cargarDatos();
            } catch (RestauranteFachadaException ex) {
                JOptionPane.showMessageDialog(frame, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    @Override
    public void regresar() {
        frame.dispose();
        new InicioFrame().setVisible(true);
    }
}
