package com.itson.fachada;

import com.itson.inventario.service.InventarioService;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.pedido.service.PedidoService;
import com.itson.pedido.service.impl.PedidoServiceImpl;
import com.itson.persistencia.dominio.*;
import com.itson.producto.service.ProductoService;
import com.itson.producto.service.impl.ProductoServiceImpl;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE RESTAURANTE ===");

        // 1. Inicializar Servicios
        InventarioService inventarioService = new InventarioServiceImpl();
        ProductoService productoService = new ProductoServiceImpl();
        PedidoService pedidoService = new PedidoServiceImpl();

        try {
            // =================================================================
            // PASO 1: SIMULACIÓN DE CAJERO
            // =================================================================
            Usuario cajero = new Usuario();
            cajero.setNombre("Juan Pérez");
            cajero.setRol(Rol.CAJERO);
            System.out.println(">> Cajero en turno: " + cajero.getNombre());

            // =================================================================
            // PASO 2: ALTA DE INVENTARIO (18 INSUMOS)
            // =================================================================
            System.out.println("\n--- 1. Dando de alta Insumos en Almacén ---");

            // --- Panes ---
            Insumo insPan = crearInsumo("Pan de Hamburguesa", "INS-PAN-01", "pza", 100.0);
            Insumo insPanHD = crearInsumo("Pan de Hot Dog", "INS-PAN-02", "pza", 80.0);

            // --- Proteínas ---
            Insumo insCarne = crearInsumo("Carne Sirloin", "INS-CARNE", "kg", 20.0);
            Insumo insPollo = crearInsumo("Pechuga Crispy", "INS-POLLO", "pza", 50.0);
            Insumo insSalchicha = crearInsumo("Salchicha Jumbo", "INS-SALCHICHA", "pza", 100.0);
            Insumo insTocino = crearInsumo("Tocino Ahumado", "INS-TOCINO", "gr", 5000.0);

            // --- Lacteos y Vegetales ---
            Insumo insQueso = crearInsumo("Queso Americano", "INS-QUESO", "rebanada", 200.0);
            Insumo insLechuga = crearInsumo("Lechuga Fresca", "INS-LECHUGA", "pza", 30.0);
            Insumo insTomate = crearInsumo("Tomate Bola", "INS-TOMATE", "kg", 15.0);
            Insumo insCebolla = crearInsumo("Cebolla Morada", "INS-CEBOLLA", "kg", 10.0);
            Insumo insPepinillos = crearInsumo("Pepinillos", "INS-PEPINILLO", "gr", 2000.0);

            // --- Salsas y Aderezos ---
            Insumo insMayonesa = crearInsumo("Mayonesa", "INS-MAYO", "lt", 10.0);
            Insumo insKetchup = crearInsumo("Ketchup", "INS-KETCHUP", "lt", 10.0);
            Insumo insMostaza = crearInsumo("Mostaza", "INS-MOSTAZA", "lt", 5.0);
            Insumo insBBQ = crearInsumo("Salsa BBQ", "INS-BBQ", "lt", 5.0);

            // --- Congelados y Bebidas ---
            Insumo insPapas = crearInsumo("Papas Congeladas", "INS-PAPAS", "gr", 10000.0);
            Insumo insRefresco = crearInsumo("Lata Coca-Cola", "INS-COCA", "pza", 100.0);
            Insumo insAgua = crearInsumo("Agua Natural", "INS-AGUA", "pza", 50.0);

            // Registrar todo en BD
            registrarInsumoSeguro(inventarioService, insPan);
            registrarInsumoSeguro(inventarioService, insPanHD);
            registrarInsumoSeguro(inventarioService, insCarne);
            registrarInsumoSeguro(inventarioService, insPollo);
            registrarInsumoSeguro(inventarioService, insSalchicha);
            registrarInsumoSeguro(inventarioService, insTocino);
            registrarInsumoSeguro(inventarioService, insQueso);
            registrarInsumoSeguro(inventarioService, insLechuga);
            registrarInsumoSeguro(inventarioService, insTomate);
            registrarInsumoSeguro(inventarioService, insCebolla);
            registrarInsumoSeguro(inventarioService, insPepinillos);
            registrarInsumoSeguro(inventarioService, insMayonesa);
            registrarInsumoSeguro(inventarioService, insKetchup);
            registrarInsumoSeguro(inventarioService, insMostaza);
            registrarInsumoSeguro(inventarioService, insBBQ);
            registrarInsumoSeguro(inventarioService, insPapas);
            registrarInsumoSeguro(inventarioService, insRefresco);
            registrarInsumoSeguro(inventarioService, insAgua);

            // =================================================================
            // PASO 3: CREACIÓN DE PRODUCTOS CON COMPLEMENTOS
            // =================================================================
            System.out.println("\n--- 2. Creando el Menú (5 Productos con Complementos) ---");

            // --- Producto 1: Hamburguesa Clásica ---
            Producto p1 = new Producto();
            p1.setNombre("Hamburguesa Clásica");
            p1.setPrecio(85.0);
            p1.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", insPan.getId(), 1.0),
                    crearIngrediente("Carne", insCarne.getId(), 0.150), // 150gr carne
                    crearIngrediente("Lechuga", insLechuga.getId(), 0.1),
                    crearIngrediente("Tomate", insTomate.getId(), 0.050)
            ));
            // Complementos (Salsas o Vegetales opcionales que van incluidos pero se pueden quitar)
            p1.setComplementos(Arrays.asList(
                    crearComplemento("Mayonesa", "Untada", 0.0, insMayonesa.getId(), 0.010),
                    crearComplemento("Ketchup", "Sobre la carne", 0.0, insKetchup.getId(), 0.010),
                    crearComplemento("Pepinillos", "Encurtidos", 0.0, insPepinillos.getId(), 15.0)
            ));

            // --- Producto 2: Cheeseburger Deluxe ---
            Producto p2 = new Producto();
            p2.setNombre("Cheeseburger Deluxe");
            p2.setPrecio(95.0);
            p2.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", insPan.getId(), 1.0),
                    crearIngrediente("Carne", insCarne.getId(), 0.150),
                    crearIngrediente("Queso", insQueso.getId(), 2.0) // Doble queso
            ));
            // Complementos
            p2.setComplementos(Arrays.asList(
                    crearComplemento("Mayonesa", "Untada", 0.0, insMayonesa.getId(), 0.010),
                    crearComplemento("Mostaza", "Untada", 0.0, insMostaza.getId(), 0.005),
                    crearComplemento("Cebolla", "Picada", 0.0, insCebolla.getId(), 0.015)
            ));

            // --- Producto 3: BBQ Bacon Burger ---
            Producto p3 = new Producto();
            p3.setNombre("BBQ Bacon Burger");
            p3.setPrecio(115.0);
            p3.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", insPan.getId(), 1.0),
                    crearIngrediente("Carne", insCarne.getId(), 0.150),
                    crearIngrediente("Queso", insQueso.getId(), 1.0),
                    crearIngrediente("Tocino", insTocino.getId(), 30.0) // Ya incluye tocino base
            ));
            // Complementos
            p3.setComplementos(Arrays.asList(
                    crearComplemento("Salsa BBQ", "Abundante", 0.0, insBBQ.getId(), 0.030),
                    crearComplemento("Cebolla Asada", "Caramelizada", 0.0, insCebolla.getId(), 0.030)
            ));
            // Extras (Costo adicional)
            p3.setExtras(Arrays.asList(
                    crearExtra("Extra Tocino", "Más crujiente", 20.0, insTocino.getId(), 30.0),
                    crearExtra("Extra Queso", "Gratinado", 15.0, insQueso.getId(), 1.0)
            ));

            // --- Producto 4: Hot Dog Jumbo ---
            Producto p4 = new Producto();
            p4.setNombre("Hot Dog Jumbo");
            p4.setPrecio(65.0);
            p4.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan Hot Dog", insPanHD.getId(), 1.0),
                    crearIngrediente("Salchicha", insSalchicha.getId(), 1.0)
            ));
            // Complementos
            p4.setComplementos(Arrays.asList(
                    crearComplemento("Tomate Picado", "Fresco", 0.0, insTomate.getId(), 0.030),
                    crearComplemento("Cebolla Picada", "Fina", 0.0, insCebolla.getId(), 0.020),
                    crearComplemento("Mayonesa", "Linea", 0.0, insMayonesa.getId(), 0.010),
                    crearComplemento("Ketchup", "Linea", 0.0, insKetchup.getId(), 0.010),
                    crearComplemento("Mostaza", "Linea", 0.0, insMostaza.getId(), 0.005)
            ));

            // --- Producto 5: Combo Pollo Crispy ---
            Producto p5 = new Producto();
            p5.setNombre("Sandwich Pollo Crispy");
            p5.setPrecio(90.0);
            p5.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", insPan.getId(), 1.0),
                    crearIngrediente("Pollo", insPollo.getId(), 1.0),
                    crearIngrediente("Lechuga", insLechuga.getId(), 0.1)
            ));
            // Complementos
            p5.setComplementos(Arrays.asList(
                    crearComplemento("Mayonesa", "Untada", 0.0, insMayonesa.getId(), 0.015),
                    crearComplemento("Tomate", "Rebanada", 0.0, insTomate.getId(), 0.040)
            ));

            // Guardamos productos
            registrarProductoSeguro(productoService, p1);
            registrarProductoSeguro(productoService, p2);
            registrarProductoSeguro(productoService, p3);
            registrarProductoSeguro(productoService, p4);
            registrarProductoSeguro(productoService, p5);

            // =================================================================
            // PASO 4: REGISTRAR PEDIDOS (PRUEBA DE DESCUENTO)
            // =================================================================
            System.out.println("\n--- 3. Cajero registrando Pedidos ---");

            imprimirStock("ANTES DE VENDER", inventarioService);

            // --- PEDIDO 1: Cheeseburger Deluxe (con todo) ---
            Pedido pedido1 = new Pedido();
            pedido1.setNombre("Cliente Mesa 1");
            pedido1.setPrecio(p2.getPrecio());
            pedido1.setProductos(Arrays.asList(p2));

            System.out.println("\n>> Procesando Pedido 1 (Cheeseburger Deluxe)...");
            pedidoService.agregarPedido(pedido1);
            System.out.println("   ¡Pedido 1 registrado!");

            // --- PEDIDO 2: BBQ Bacon Burger + Extra Tocino ---
            // Simulamos que el usuario eligió el extra en la interfaz
            // (En código real clonaríamos el producto, pero aquí usamos el objeto p3 que ya tiene el extra configurado)
            // *Nota*: Para que el descuento del extra funcione en el test, el objeto p3
            // debe tener el extra en su lista getExtras(), lo cual ya hicimos arriba.
            Pedido pedido2 = new Pedido();
            pedido2.setNombre("Cliente Para Llevar");
            // Precio base + precio extra
            pedido2.setPrecio(p3.getPrecio() + 20.0);
            pedido2.setProductos(Arrays.asList(p3));

            System.out.println("\n>> Procesando Pedido 2 (BBQ Burger con Extra Tocino)...");
            pedidoService.agregarPedido(pedido2);
            System.out.println("   ¡Pedido 2 registrado!");

            // =================================================================
            // PASO 5: VERIFICACIÓN
            // =================================================================
            System.out.println("\n--- 4. Auditoría de Inventario ---");
            imprimirStock("DESPUÉS DE VENDER", inventarioService);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES
    // =========================================================================
    private static Insumo crearInsumo(String nombre, String codigo, String unidad, Double stock) {
        Insumo i = new Insumo();
        i.setNombre(nombre);
        i.setCodigo(codigo);
        i.setUnidadMedida(unidad);
        i.setStockActual(stock);
        i.setStockMinimo(5.0);
        return i;
    }

    private static Ingrediente crearIngrediente(String nombre, String insumoId, Double cantidad) {
        return new Ingrediente(nombre, "Componente base", 0.0, insumoId, cantidad);
    }

    private static Complemento crearComplemento(String nombre, String desc, Double precio, String insumoId, Double cantidad) {
        return new Complemento(nombre, desc, precio, insumoId, cantidad);
    }

    private static Extra crearExtra(String nombre, String desc, Double precio, String insumoId, Double cantidad) {
        return new Extra(nombre, desc, precio, insumoId, cantidad);
    }

    private static void registrarInsumoSeguro(InventarioService s, Insumo i) {
        try {
            Insumo existe = s.obtenerInsumoPorCodigo(i.getCodigo());
            if (existe != null) {
                i.setId(existe.getId());
                System.out.println("Existente: " + i.getNombre());
            } else {
                s.registrarInsumo(i);
                System.out.println("Registrado: " + i.getNombre());
            }
        } catch (Exception e) {
            System.err.println("Error insumo: " + e.getMessage());
        }
    }

    private static void registrarProductoSeguro(ProductoService s, Producto p) {
        try {
            Producto existe = s.obtenerProductoPorNombre(p.getNombre());
            if (existe != null) {
                p.setId(existe.getId());
                System.out.println("Existente: " + p.getNombre());
            } else {
                s.agregarProducto(p);
                System.out.println("Registrado: " + p.getNombre());
            }
        } catch (Exception e) {
            System.err.println("Error producto: " + e.getMessage());
        }
    }

    private static void imprimirStock(String momento, InventarioService s) {
        try {
            System.out.println("\n>>> ESTADO DEL STOCK (" + momento + ") <<<");
            System.out.printf("%-25s %-10s %-10s%n", "NOMBRE", "STOCK", "UNIDAD");
            System.out.println("--------------------------------------------------");
            for (Insumo i : s.obtenerTodosLosInsumos()) {
                System.out.printf("%-25s %-10.2f %-10s%n", i.getNombre(), i.getStockActual(), i.getUnidadMedida());
            }
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
