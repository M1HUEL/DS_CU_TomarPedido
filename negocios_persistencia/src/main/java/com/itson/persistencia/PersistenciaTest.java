package com.itson.persistencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PersistenciaTest {

//    private static final InventarioService inventarioService = new InventarioService();
//    private static final ProductoService productoService = new ProductoService();
//    private static final PedidoDAO pedidoDAO = new PedidoDAOImpl();
//
//    public static void main(String[] args) {
//        generarInventario();
//        Producto producto = generarProductos();
//        generarPedidos(producto);
//    }
//
//    public static void generarInventario() {
//        poblarItems(
//                new String[]{"Pan", "Carne", "Queso", "Lechuga", "Tomate", "Cebolla", "Pepinillos", "Jalapeños", "Huevo", "Tocino"},
//                InventarioTipo.INGREDIENTE, 50, 100, 5, 10
//        );
//
//        poblarItems(
//                new String[]{"Papas fritas", "Salsa BBQ", "Ketchup", "Mayonesa", "Mostaza", "Refresco"},
//                InventarioTipo.COMPLEMENTO, 20, 30, 3, 5
//        );
//
//        poblarItems(
//                new String[]{"Extra Queso", "Extra Carne", "Extra Tocino", "Extra Huevo", "Extra Jalapeños"},
//                InventarioTipo.EXTRA, 10, 20, 5, 10
//        );
//
//        System.out.println("Inventario generado correctamente.");
//    }
//
//    private static void poblarItems(String[] nombres, InventarioTipo tipo,
//            double minCant, double maxCant, double minPrecio, double maxPrecio) {
//
//        for (String nombre : nombres) {
//            double cantidad = minCant + Math.random() * (maxCant - minCant);
//            double precio = minPrecio + Math.random() * (maxPrecio - minPrecio);
//            inventarioService.agregarItem(nombre, cantidad, BigDecimal.valueOf(precio), tipo);
//            System.out.println(tipo + " agregado: " + nombre);
//        }
//    }
//
//    public static Producto generarProductos() {
//
//        Ingrediente pan = inventarioService.obtenerIngrediente("Pan")
//                .orElseThrow(() -> new RuntimeException("No existe Pan en inventario"));
//
//        Ingrediente carne = inventarioService.obtenerIngrediente("Carne")
//                .orElseThrow(() -> new RuntimeException("No existe Carne"));
//
//        Ingrediente queso = inventarioService.obtenerIngrediente("Queso")
//                .orElseThrow(() -> new RuntimeException("No existe Queso"));
//
//        Ingrediente tocino = inventarioService.obtenerIngrediente("Tocino")
//                .orElseThrow(() -> new RuntimeException("No existe Tocino"));
//
//        Ingrediente cebolla = inventarioService.obtenerIngrediente("Cebolla")
//                .orElseThrow(() -> new RuntimeException("No existe Cebolla"));
//
//        Complemento papas = inventarioService.obtenerComplemento("Papas fritas")
//                .orElseThrow(() -> new RuntimeException("No existe Papas fritas"));
//
//        Complemento mayonesa = inventarioService.obtenerComplemento("Mayonesa")
//                .orElseThrow(() -> new RuntimeException("No existe Mayonesa"));
//
//        Extra extraQueso = inventarioService.obtenerExtra("Extra Queso")
//                .orElseThrow(() -> new RuntimeException("No existe Extra Queso"));
//
//        Extra extraTocino = inventarioService.obtenerExtra("Extra Tocino")
//                .orElseThrow(() -> new RuntimeException("No existe Extra Tocino"));
//
//        Producto hamburguesaDeluxe = new Producto(
//                "Hamburguesa Doble Deluxe",
//                Arrays.asList(pan, carne, carne, queso, tocino, cebolla),
//                Arrays.asList(papas, mayonesa),
//                Arrays.asList(extraQueso, extraTocino),
//                BigDecimal.valueOf(89.0)
//        );
//
//        Producto productoCreado = productoService.crearProducto(hamburguesaDeluxe)
//                .orElseThrow(() -> new RuntimeException("No se pudo crear el producto"));
//
//        System.out.println("Producto creado correctamente: " + productoCreado.getNombre()
//                + " | ID = " + productoCreado.getId());
//
//        return productoCreado;
//    }
//
//    public static void generarPedidos(Producto hamburguesa) {
//
//        if (hamburguesa == null) {
//            System.out.println("No se pueden crear pedidos sin productos.");
//            return;
//        }
//
//        List<Pedido> pedidos = Arrays.asList(
//                new Pedido(null, "Juan Perez",
//                        Arrays.asList(hamburguesa),
//                        "Sin cebolla",
//                        hamburguesa.getPrecio(),
//                        LocalDateTime.now()),
//                new Pedido(null, "Maria Lopez",
//                        Arrays.asList(hamburguesa),
//                        "Extra ketchup",
//                        hamburguesa.getPrecio(),
//                        LocalDateTime.now())
//        );
//
//        for (Pedido pedido : pedidos) {
//            boolean agregado = pedidoDAO.agregar(pedido);
//            System.out.println(
//                    agregado
//                            ? "Pedido creado para: " + pedido.getNombre()
//                            : "Error al crear pedido para: " + pedido.getNombre()
//            );
//        }
//    }
}
