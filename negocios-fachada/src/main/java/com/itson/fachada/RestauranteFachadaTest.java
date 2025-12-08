package com.itson.fachada;

import com.itson.inventario.service.InventarioService;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.negocios.transaccion.service.TransaccionService; // <--- Importar
import com.itson.negocios.transaccion.service.impl.TransaccionServiceImpl; // <--- Importar
import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.service.UsuarioService;
import com.itson.negocios.usuario.service.impl.UsuarioServiceImpl;
import com.itson.pedido.service.PedidoService;
import com.itson.pedido.service.impl.PedidoServiceImpl;
import com.itson.persistencia.dominio.*;
import com.itson.producto.service.ProductoService;
import com.itson.producto.service.impl.ProductoServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class RestauranteFachadaTest {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO CARGA DE DATOS DE PRUEBA ===");

        // 1. Inicializar Servicios
        InventarioService inventarioService = new InventarioServiceImpl();
        ProductoService productoService = new ProductoServiceImpl();
        PedidoService pedidoService = new PedidoServiceImpl();
        UsuarioService usuarioService = new UsuarioServiceImpl();
        TransaccionService transaccionService = new TransaccionServiceImpl(); // <--- Servicio de Pagos

        try {
            // ==========================================
            // 2. USUARIOS
            // ==========================================
            System.out.println("\n>> Cargando Usuarios...");

            Usuario cajero = new Usuario("Juan Pérez", "1234", Sexo.MASCULINO, Rol.CAJERO);
            Usuario cocinero = new Usuario("Beto el Chef", "cocina123", Sexo.MASCULINO, Rol.COCINERO);
            Usuario admin = new Usuario("ElAdmin", "545", Sexo.MASCULINO, Rol.ADMINISTRADOR);

            registrarUsuarioSeguro(usuarioService, cajero);
            registrarUsuarioSeguro(usuarioService, cocinero);
            registrarUsuarioSeguro(usuarioService, admin);

            // ==========================================
            // 3. INSUMOS (STOCK)
            // ==========================================
            System.out.println("\n>> Cargando Insumos...");

            Insumo pan = crearInsumo("Pan Hamburguesa", "INS-PAN", "pza", 100.0);
            Insumo carne = crearInsumo("Carne Res", "INS-CARNE", "kg", 20.0);
            Insumo pollo = crearInsumo("Pechuga Crispy", "INS-POLLO", "pza", 50.0);
            Insumo queso = crearInsumo("Queso Americano", "INS-QUESO", "rebanada", 200.0);
            Insumo tocino = crearInsumo("Tocino Ahumado", "INS-TOCINO", "gr", 5000.0);
            Insumo lechuga = crearInsumo("Lechuga Fresca", "INS-LECHUGA", "pza", 30.0);
            Insumo tomate = crearInsumo("Tomate", "INS-TOMATE", "kg", 15.0);
            Insumo cebolla = crearInsumo("Cebolla", "INS-CEBOLLA", "kg", 15.0);
            Insumo catsup = crearInsumo("Salsa Catsup", "INS-CATSUP", "lt", 10.0);
            Insumo mayo = crearInsumo("Mayonesa", "INS-MAYO", "lt", 10.0);
            Insumo refresco = crearInsumo("Coca Cola", "INS-COCA", "pza", 100.0);

            registrarInsumoSeguro(inventarioService, pan);
            registrarInsumoSeguro(inventarioService, carne);
            registrarInsumoSeguro(inventarioService, pollo);
            registrarInsumoSeguro(inventarioService, queso);
            registrarInsumoSeguro(inventarioService, tocino);
            registrarInsumoSeguro(inventarioService, lechuga);
            registrarInsumoSeguro(inventarioService, tomate);
            registrarInsumoSeguro(inventarioService, cebolla);
            registrarInsumoSeguro(inventarioService, catsup);
            registrarInsumoSeguro(inventarioService, mayo);
            registrarInsumoSeguro(inventarioService, refresco);

            // ==========================================
            // 4. PRODUCTOS
            // ==========================================
            System.out.println("\n>> Cargando Productos...");

            // P1: Hamburguesa Clásica
            Producto p1 = new Producto();
            p1.setNombre("Hamburguesa Clásica");
            p1.setPrecio(85.0);
            p1.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", pan.getId(), 1.0),
                    crearIngrediente("Carne", carne.getId(), 0.150),
                    crearIngrediente("Lechuga", lechuga.getId(), 0.1)
            ));
            p1.setComplementos(Arrays.asList(
                    crearComplemento("Queso", "Rebanada", 0.0, queso.getId(), 1.0)
            ));
            p1.setExtras(new ArrayList<>());

            // P2: Cheeseburger Deluxe
            Producto p2 = new Producto();
            p2.setNombre("Cheeseburger Deluxe");
            p2.setPrecio(95.0);
            p2.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", pan.getId(), 1.0),
                    crearIngrediente("Carne", carne.getId(), 0.150),
                    crearIngrediente("Queso", queso.getId(), 2.0)
            ));
            p2.setComplementos(Arrays.asList(
                    crearComplemento("Tocino", "Crocante", 0.0, tocino.getId(), 30.0)
            ));
            p2.setExtras(new ArrayList<>());

            // P3: BBQ Bacon Burger
            Producto p3 = new Producto();
            p3.setNombre("BBQ Bacon Burger");
            p3.setPrecio(115.0);
            p3.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", pan.getId(), 1.0),
                    crearIngrediente("Carne", carne.getId(), 0.150),
                    crearIngrediente("Queso", queso.getId(), 1.0),
                    crearIngrediente("Tocino", tocino.getId(), 30.0)
            ));
            p3.setComplementos(Arrays.asList(
                    crearComplemento("Lechuga", "Fresca", 0.0, lechuga.getId(), 0.05)
            ));
            p3.setExtras(Arrays.asList(
                    crearExtra("Extra Queso", "Gratinado", 15.0, queso.getId(), 1.0)
            ));

            // P4: Hot Dog
            Producto p4 = new Producto();
            p4.setNombre("Hot Dog Jumbo");
            p4.setPrecio(65.0);
            p4.setIngredientes(Arrays.asList(
                    crearIngrediente("Pan", pan.getId(), 1.0),
                    crearIngrediente("Pollo", pollo.getId(), 1.0)
            ));
            p4.setComplementos(Arrays.asList(
                    crearComplemento("Tomate", "Picado", 0.0, tomate.getId(), 0.05),
                    crearComplemento("Cebolla", "Picada", 0.0, cebolla.getId(), 0.05)
            ));
            p4.setExtras(new ArrayList<>());

            registrarProductoSeguro(productoService, p1);
            registrarProductoSeguro(productoService, p2);
            registrarProductoSeguro(productoService, p3);
            registrarProductoSeguro(productoService, p4);

            // ==========================================
            // 5. PEDIDOS
            // ==========================================
            System.out.println("\n>> Cargando Pedidos de Prueba...");

            // --- PEDIDO 1 (ENTREGADO) ---
            Pedido pedido1 = new Pedido();
            pedido1.setNombre("Mesa 1");
            pedido1.setProductos(Arrays.asList(p1, p4));
            pedido1.setPrecio(p1.getPrecio() + p4.getPrecio());
            pedido1.setComentario("Sin cebolla en el hotdog");
            pedido1.setEstado(EstadoPedido.ENTREGADO);

            try {
                pedidoService.agregarPedido(pedido1);
                // Forzar estado entregado (agregarPedido suele ponerlo en PENDIENTE)
                pedidoService.actualizarPedido(pedido1.getId(), pedido1);
                System.out.println("Pedido 1 (Entregado) creado. ID: " + pedido1.getId());
            } catch (Exception e) {
                System.err.println("Error pedido 1: " + e.getMessage());
            }

            // --- PEDIDO 2 (PENDIENTE) ---
            Pedido pedido2 = new Pedido();
            pedido2.setNombre("Para Llevar - Juan");
            pedido2.setProductos(Arrays.asList(p2, p3));
            pedido2.setPrecio(p2.getPrecio() + p3.getPrecio() + 15.0);
            pedido2.setComentario("La BBQ bien cocida por favor");
            pedido2.setEstado(EstadoPedido.PENDIENTE);

            try {
                pedidoService.agregarPedido(pedido2);
                System.out.println("Pedido 2 (Pendiente) creado. ID: " + pedido2.getId());
            } catch (Exception e) {
                System.err.println("Error pedido 2: " + e.getMessage());
            }

            // ==========================================
            // 6. PAGOS (NUEVO)
            // ==========================================
            System.out.println("\n>> Registrando Pagos de Prueba...");

            if (pedido1.getId() != null) {
                Pago pago1 = new Pago();
                pago1.setPedidoId(pedido1.getId());
                pago1.setMonto(pedido1.getPrecio());
                pago1.setMetodoPago(MetodoPago.EFECTIVO);
                pago1.setFechaPago(LocalDateTime.now());

                try {
                    transaccionService.agregarPago(pago1);
                    System.out.println("Pago 1 registrado (Efectivo) para pedido " + pedido1.getId());
                } catch (Exception e) {
                    System.err.println("Error pago 1: " + e.getMessage());
                }
            }

            if (pedido2.getId() != null) {
                Pago pago2 = new Pago();
                pago2.setPedidoId(pedido2.getId());
                pago2.setMonto(pedido2.getPrecio());
                pago2.setMetodoPago(MetodoPago.TARJETA_CREDITO);
                pago2.setFechaPago(LocalDateTime.now());

                try {
                    transaccionService.agregarPago(pago2);
                    System.out.println("Pago 2 registrado (Tarjeta) para pedido " + pedido2.getId());
                } catch (Exception e) {
                    System.err.println("Error pago 2: " + e.getMessage());
                }
            }

            System.out.println("\n=== ¡SISTEMA DE PRUEBA INICIALIZADO EXITOSAMENTE! ===");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR CRÍTICO: " + e.getMessage());
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
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
        return new Ingrediente(nombre, "Base", 0.0, insumoId, cantidad);
    }

    private static Complemento crearComplemento(String nombre, String desc, Double precio, String insumoId, Double cantidad) {
        return new Complemento(nombre, desc, precio, insumoId, cantidad);
    }

    private static Extra crearExtra(String nombre, String desc, Double precio, String insumoId, Double cantidad) {
        return new Extra(nombre, desc, precio, insumoId, cantidad);
    }

    // --- Métodos "Seguros" ---
    private static void registrarUsuarioSeguro(UsuarioService s, Usuario u) throws UsuarioException {
        Usuario existe = s.obtenerUsuarioPorNombre(u.getNombre());
        if (existe == null) {
            s.agregarUsuario(u);
            System.out.println("Usuario creado: " + u.getNombre());
        } else {
            System.out.println("Usuario ya existe: " + u.getNombre());
        }
    }

    private static void registrarInsumoSeguro(InventarioService s, Insumo i) throws Exception {
        Insumo existe = s.obtenerInsumoPorCodigo(i.getCodigo());
        if (existe == null) {
            s.registrarInsumo(i);
            System.out.println("Insumo creado: " + i.getNombre());
        } else {
            i.setId(existe.getId()); // Recuperar ID para usarlo
            System.out.println("Insumo ya existe: " + i.getNombre());
        }
    }

    private static void registrarProductoSeguro(ProductoService s, Producto p) throws Exception {
        Producto existe = s.obtenerProductoPorNombre(p.getNombre());
        if (existe == null) {
            s.agregarProducto(p);
            System.out.println("Producto creado: " + p.getNombre());
        } else {
            p.setId(existe.getId()); // Recuperar ID
            System.out.println("Producto ya existe: " + p.getNombre());
        }
    }
}
