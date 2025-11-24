package com.itson.persistencia;

import com.itson.persistencia.dao.InventarioDAO;
import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dao.impl.InventarioDAOImpl;
import com.itson.persistencia.dao.impl.ProductoDAOImpl;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.InventarioItem;
import com.itson.persistencia.dominio.InventarioTipo;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PersistenciaTest {

    private static final InventarioDAO inventarioDAO = new InventarioDAOImpl();
    private static final ProductoDAO productoDAO = new ProductoDAOImpl();

    public static void main(String[] args) {
        crearInventario();
        crearProductos();
    }

    // -----------------------------
    // Crear inventario
    // -----------------------------
    private static void crearInventario() {
        agregarItem("Pan", 50, InventarioTipo.INGREDIENTE);
        agregarItem("Carne", 40, InventarioTipo.INGREDIENTE);
        agregarItem("Queso", 30, InventarioTipo.INGREDIENTE);
        agregarItem("Tocino", 20, InventarioTipo.INGREDIENTE);
        agregarItem("Cebolla", 25, InventarioTipo.INGREDIENTE);
        agregarItem("Papas fritas", 20, InventarioTipo.COMPLEMENTO);
        agregarItem("Mayonesa", 10, InventarioTipo.COMPLEMENTO);
        agregarItem("Extra Queso", 15, InventarioTipo.EXTRA);
        agregarItem("Extra Tocino", 10, InventarioTipo.EXTRA);

        System.out.println("Inventario inicial generado correctamente.\n");
    }

    private static void agregarItem(String nombre, double cantidad, InventarioTipo tipo) {
        InventarioItem item = new InventarioItem();
        item.setNombre(nombre);
        item.setTipo(tipo);
        item.setCantidadDisponible(cantidad);
        item.setPrecioUnidad(BigDecimal.valueOf(1.0));
        inventarioDAO.agregar(item);
        System.out.println("Inventario agregado: " + nombre + " | Tipo: " + tipo);
    }

    // -----------------------------
    // Crear y persistir productos
    // -----------------------------
    private static void crearProductos() {
        Producto hamburguesaDeluxe = crearProducto(
                "Hamburguesa Deluxe", BigDecimal.valueOf(89.0),
                new String[]{"Pan", "Carne", "Carne", "Queso", "Tocino", "Cebolla"},
                new String[]{"Papas fritas", "Mayonesa"},
                new String[]{"Extra Queso", "Extra Tocino"}
        );

        Producto hamburguesaSimple = crearProducto(
                "Hamburguesa Simple", BigDecimal.valueOf(59.0),
                new String[]{"Pan", "Carne", "Queso"},
                new String[]{"Mayonesa"},
                new String[]{"Extra Queso"}
        );

        System.out.println("Productos creados y guardados correctamente.\n");
    }

    private static Producto crearProducto(String nombre, BigDecimal precio,
            String[] ingredientesNombres,
            String[] complementosNombres,
            String[] extrasNombres) {

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);

        // Ingredientes
        producto.setIngredientes(Arrays.stream(ingredientesNombres)
                .map(n -> new Ingrediente(
                "i-" + n.replace(" ", ""),
                n,
                BigDecimal.ZERO,
                inventarioDAO.consultarPorNombre(n).getId(),
                1.0
        )).toList());

        // Complementos
        producto.setComplementos(Arrays.stream(complementosNombres)
                .map(n -> new Complemento(
                "c-" + n.replace(" ", ""),
                n,
                BigDecimal.ZERO,
                inventarioDAO.consultarPorNombre(n).getId(),
                1.0
        )).toList());

        // Extras
        producto.setExtras(Arrays.stream(extrasNombres)
                .map(n -> new Extra(
                "e-" + n.replace(" ", ""),
                n,
                BigDecimal.ZERO,
                inventarioDAO.consultarPorNombre(n).getId(),
                1.0
        )).toList());

        // Persistir producto
        productoDAO.agregar(producto);
        System.out.println("Producto guardado: " + nombre + " | ID: ");
        return producto;
    }
}
