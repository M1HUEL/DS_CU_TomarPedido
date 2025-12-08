package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.GestionMenuController;
import com.itson.presentacion.frame.GestionMenuFrame;
import com.itson.presentacion.frame.InicioFrame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GestionMenuControllerImpl implements GestionMenuController {

    private final GestionMenuFrame frame;
    private final RestauranteFachada fachada;

    public GestionMenuControllerImpl(GestionMenuFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Producto> cargarMenu() {
        try {
            return fachada.obtenerProductos();
        } catch (RestauranteFachadaException e) {
            JOptionPane.showMessageDialog(frame, "Error al cargar menú: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void agregarProducto() {
        JTextField txtNombre = new JTextField();
        JTextField txtPrecio = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre del Platillo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio de Venta ($):"));
        panel.add(txtPrecio);

        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Nuevo Platillo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El nombre es obligatorio.");
                    return;
                }

                double precio = Double.parseDouble(txtPrecio.getText());

                Producto nuevo = new Producto();
                nuevo.setNombre(txtNombre.getText());
                nuevo.setPrecio(precio);
                nuevo.setIngredientes(new ArrayList<>());
                nuevo.setExtras(new ArrayList<>());
                nuevo.setComplementos(new ArrayList<>());

                fachada.crearProducto(nuevo);

                JOptionPane.showMessageDialog(frame, "Producto agregado al menú.");
                frame.cargarDatos();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RestauranteFachadaException e) {
                JOptionPane.showMessageDialog(frame, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void editarProducto(Producto producto) {
        if (producto == null) {
            return;
        }

        JTextField txtNombre = new JTextField(producto.getNombre());
        JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre del Platillo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio de Venta ($):"));
        panel.add(txtPrecio);

        int result = JOptionPane.showConfirmDialog(frame, panel,
                "Editar Platillo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double precio = Double.parseDouble(txtPrecio.getText());

                producto.setNombre(txtNombre.getText());
                producto.setPrecio(precio);

                fachada.actualizarProducto(producto.getId(), producto);

                JOptionPane.showMessageDialog(frame, "Producto actualizado.");
                frame.cargarDatos();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Precio inválido.");
            } catch (RestauranteFachadaException e) {
                JOptionPane.showMessageDialog(frame, "Error al actualizar: " + e.getMessage());
            }
        }
    }

    @Override
    public void eliminarProducto(Producto producto) {
        if (producto == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "¿Eliminar del menú: " + producto.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                fachada.eliminarProducto(producto.getId());
                JOptionPane.showMessageDialog(frame, "Producto eliminado.");
                frame.cargarDatos();
            } catch (RestauranteFachadaException e) {
                JOptionPane.showMessageDialog(frame, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    @Override
    public void regresar() {
        frame.dispose();
        new InicioFrame().setVisible(true);
    }
}
