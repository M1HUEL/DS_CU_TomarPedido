package com.itson.controlPedido;

import com.itson.dto.ComplementoDTO;
import com.itson.dto.ExtraDTO;
import com.itson.dto.IngredienteDTO;
import com.itson.dto.PedidoDTO;
import com.itson.dto.ProductoDTO;
import com.itson.persistencia.dao.InventarioDAO;
import com.itson.persistencia.dao.impl.InventarioDAOImpl;
import com.itson.persistencia.dominio.InventarioItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PruebaCrearPedido {

    private static final InventarioDAO inventarioDAO = new InventarioDAOImpl();
    private static final PedidoFachada pedidoFachada = new PedidoFachadaImpl();

//    public static void main(String[] args) {
//        // Crear inventario inicial
//        crearInventarioInicial();
//
//        // Pedido 1: producto que ya existe en la BD
//        ProductoDTO productoExistente = obtenerProductoExistente("Hamburguesa Deluxe");
//        crearPedidoConProducto(productoExistente, "Juan Perez", "Sin cebolla extra");
//
//        // Pedido 2: producto personalizado para la orden
//        ProductoDTO productoPersonalizado = crearProductoDePrueba();
//        crearPedidoConProducto(productoPersonalizado, "Maria Lopez", "Extra ketchup");
//    }

    // -----------------------------
    // Crear inventario inicial
    // -----------------------------
    public static void crearInventarioInicial() {
        crearItemInventario("Pan", 50);
        crearItemInventario("Carne", 40);
        crearItemInventario("Queso", 30);
        crearItemInventario("Tocino", 20);
        crearItemInventario("Cebolla", 25);

        crearItemInventario("Papas fritas", 20);
        crearItemInventario("Mayonesa", 10);

        crearItemInventario("Extra Queso", 15);
        crearItemInventario("Extra Tocino", 10);

        System.out.println("Inventario inicial generado correctamente.\n");
    }

    private static void crearItemInventario(String nombre, double cantidad) {
        InventarioItem item = new InventarioItem();
        item.setId(null);
        item.setNombre(nombre);
        item.setCantidadDisponible(cantidad);
        inventarioDAO.agregar(item);

        System.out.println("Inventario agregado: " + nombre);
    }

//    // -----------------------------
//    // Obtener producto existente desde BD
//    // -----------------------------
//    private static ProductoDTO obtenerProductoExistente(String nombre) {
//        ProductoDTO producto = pedidoFachada.consultarProductoPorNombre(nombre);
//        if (producto == null) {
//            throw new RuntimeException("No se encontró el producto: " + nombre);
//        }
//        return producto;
//    }

    // -----------------------------
    // Crear producto personalizado (temporal)
    // -----------------------------
    public static ProductoDTO crearProductoDePrueba() {
        String panId = inventarioDAO.consultarPorNombre("Pan").getId();
        String carneId = inventarioDAO.consultarPorNombre("Carne").getId();
        String quesoId = inventarioDAO.consultarPorNombre("Queso").getId();
        String tocinoId = inventarioDAO.consultarPorNombre("Tocino").getId();
        String cebollaId = inventarioDAO.consultarPorNombre("Cebolla").getId();

        String papasId = inventarioDAO.consultarPorNombre("Papas fritas").getId();
        String mayoId = inventarioDAO.consultarPorNombre("Mayonesa").getId();

        String extraQuesoId = inventarioDAO.consultarPorNombre("Extra Queso").getId();
        String extraTocinoId = inventarioDAO.consultarPorNombre("Extra Tocino").getId();

        ProductoDTO producto = new ProductoDTO();
        producto.setId(null); // temporal, no se guarda en BD
        producto.setNombre("Hamburguesa Personalizada");
        producto.setPrecio(new BigDecimal("89.00"));

        producto.setIngredientes(Arrays.asList(
                new IngredienteDTO("i1", "Pan", BigDecimal.ZERO, panId, 1.0),
                new IngredienteDTO("i2", "Carne", BigDecimal.ZERO, carneId, 1.0),
                new IngredienteDTO("i3", "Queso", BigDecimal.ZERO, quesoId, 1.0),
                new IngredienteDTO("i4", "Tocino", BigDecimal.ZERO, tocinoId, 1.0),
                new IngredienteDTO("i5", "Cebolla", BigDecimal.ZERO, cebollaId, 0.5)
        ));

        producto.setComplementos(Arrays.asList(
                new ComplementoDTO("c1", "Papas fritas", BigDecimal.ZERO, papasId, 1.0),
                new ComplementoDTO("c2", "Mayonesa", BigDecimal.ZERO, mayoId, 0.1)
        ));

        producto.setExtras(Arrays.asList(
                new ExtraDTO("e1", "Extra Queso", BigDecimal.ZERO, extraQuesoId, 1.0),
                new ExtraDTO("e2", "Extra Tocino", BigDecimal.ZERO, extraTocinoId, 1.0)
        ));

        System.out.println("Producto DTO personalizado generado correctamente.\n");

        return producto;
    }

    // -----------------------------
    // Crear pedido mediante fachada
    // -----------------------------
    public static void crearPedidoConProducto(ProductoDTO producto, String cliente, String comentario) {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(null); // Mongo genera ID
        pedido.setNombre(cliente);
        pedido.setComentario(comentario);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setPrecio(producto.getPrecio());
        pedido.setProductos(List.of(producto));

        boolean resultado = pedidoFachada.crearPedido(pedido);

        System.out.println("Pedido creado para " + cliente + " = " + resultado + "\n");
    }
}
