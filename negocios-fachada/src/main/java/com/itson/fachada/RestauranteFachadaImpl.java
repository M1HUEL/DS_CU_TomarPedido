package com.itson.fachada;

import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.inventario.exception.InventarioException;
import com.itson.inventario.service.InventarioService;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.negocios.transaccion.exception.TransaccionException;
import com.itson.negocios.transaccion.service.TransaccionService;
import com.itson.negocios.transaccion.service.impl.TransaccionServiceImpl;
import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.service.UsuarioService;
import com.itson.negocios.usuario.service.impl.UsuarioServiceImpl;
import com.itson.pedido.exception.PedidoException;
import com.itson.pedido.service.PedidoService;
import com.itson.pedido.service.impl.PedidoServiceImpl;
import com.itson.persistencia.dominio.EstadoPedido;
import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Usuario;
import com.itson.producto.exception.ProductoException;
import com.itson.producto.service.ProductoService;
import com.itson.producto.service.impl.ProductoServiceImpl;
import java.util.Arrays;
import java.util.List;

public class RestauranteFachadaImpl implements RestauranteFachada {

    private final UsuarioService usuarioServicio;
    private final ProductoService productoServicio;
    private final PedidoService pedidoServicio;
    private final InventarioService inventarioServicio;
    private final TransaccionService transaccionServicio;

    public RestauranteFachadaImpl() {
        this.usuarioServicio = new UsuarioServiceImpl();
        this.productoServicio = new ProductoServiceImpl();
        this.pedidoServicio = new PedidoServiceImpl();
        this.inventarioServicio = new InventarioServiceImpl();
        this.transaccionServicio = new TransaccionServiceImpl();
    }

    @Override
    public void crearPedido(Pedido pedido) throws RestauranteFachadaException {
        try {
            pedidoServicio.agregarPedido(pedido);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error al crear el pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido obtenerPedidoPorId(String id) throws RestauranteFachadaException {
        try {
            return pedidoServicio.obtenerPedidoPorId(id);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error obteniendo pedido por id", e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidos() throws RestauranteFachadaException {
        try {
            return pedidoServicio.obtenerPedidos();
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error obteniendo pedidos", e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosCompletados() throws RestauranteFachadaException {
        try {
            return pedidoServicio.obtenerPedidosCompletados();
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error en historial", e);
        }
    }

    @Override
    public void actualizarPedido(String id, Pedido pedido) throws RestauranteFachadaException {
        try {
            pedidoServicio.actualizarPedido(id, pedido);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error actualizando pedido", e);
        }
    }

    @Override
    public void eliminarPedido(String id) throws RestauranteFachadaException {
        try {
            pedidoServicio.eliminarPedido(id);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error eliminando pedido", e);
        }
    }

    @Override
    public void guardarPago(Pago pago) throws RestauranteFachadaException {
        try {
            transaccionServicio.agregarPago(pago);
        } catch (TransaccionException e) {
            throw new RestauranteFachadaException("Error al registrar el pago", e);
        }
    }

    @Override
    public void realizarVentaCompleta(Pedido pedido, Pago pago) throws RestauranteFachadaException {
        try {
            if (pago.getMonto() < pedido.getPrecio()) {
                throw new RestauranteFachadaException("El monto del pago es menor al total del pedido.");
            }

            pedidoServicio.agregarPedido(pedido);

            if (pedido.getId() == null) {
                throw new RestauranteFachadaException("Error crítico: El pedido no generó ID.");
            }

            pago.setPedidoId(pedido.getId());
            transaccionServicio.agregarPago(pago);

        } catch (PedidoException | TransaccionException e) {
            if (pedido.getId() != null) {
                try {
                    pedidoServicio.eliminarPedido(pedido.getId());
                } catch (PedidoException ex) {
                    System.err.println("Error grave: Rollback fallido.");
                }
            }
            throw new RestauranteFachadaException("Error en la venta completa: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario login(String nombre, String password) throws RestauranteFachadaException {
        try {
            return usuarioServicio.login(nombre, password);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException(e.getMessage(), e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) throws RestauranteFachadaException {
        try {
            return usuarioServicio.obtenerUsuarioPorId(id);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error obteniendo usuario", e);
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios() throws RestauranteFachadaException {
        try {
            return usuarioServicio.obtenerUsuarios();
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error obteniendo usuarios", e);
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) throws RestauranteFachadaException {
        try {
            usuarioServicio.agregarUsuario(usuario);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error creando usuario", e);
        }
    }

    @Override
    public void actualizarUsuario(String id, Usuario usuario) throws RestauranteFachadaException {
        try {
            usuarioServicio.actualizarUsuario(id, usuario);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error actualizando usuario", e);
        }
    }

    @Override
    public void eliminarUsuario(String id) throws RestauranteFachadaException {
        try {
            usuarioServicio.eliminarUsuario(id);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error eliminando usuario", e);
        }
    }

    @Override
    public Producto obtenerProductoPorId(String id) throws RestauranteFachadaException {
        try {
            return productoServicio.obtenerProductoPorId(id);
        } catch (ProductoException e) {
            throw new RestauranteFachadaException("Error obteniendo producto", e);
        }
    }

    @Override
    public List<Producto> obtenerProductos() throws RestauranteFachadaException {
        try {
            return productoServicio.obtenerProductos();
        } catch (ProductoException e) {
            throw new RestauranteFachadaException("Error obteniendo productos", e);
        }
    }

    @Override
    public void crearProducto(Producto producto) throws RestauranteFachadaException {
        try {
            productoServicio.agregarProducto(producto);
        } catch (ProductoException e) {
            throw new RestauranteFachadaException("Error creando producto", e);
        }
    }

    @Override
    public void actualizarProducto(String id, Producto producto) throws RestauranteFachadaException {
        try {
            productoServicio.actualizarProducto(id, producto);
        } catch (ProductoException e) {
            throw new RestauranteFachadaException("Error actualizando producto", e);
        }
    }

    @Override
    public void eliminarProducto(String id) throws RestauranteFachadaException {
        try {
            productoServicio.eliminarProducto(id);
        } catch (ProductoException e) {
            throw new RestauranteFachadaException("Error eliminando producto", e);
        }
    }

    @Override
    public List<Insumo> obtenerInsumos() throws RestauranteFachadaException {
        try {
            return inventarioServicio.obtenerTodosLosInsumos();
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error al obtener inventario", e);
        }
    }

    @Override
    public List<Insumo> obtenerAlertasStock() throws RestauranteFachadaException {
        try {
            return inventarioServicio.obtenerInsumosConStockBajo();
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error obteniendo alertas", e);
        }
    }

    @Override
    public void registrarInsumo(Insumo insumo) throws RestauranteFachadaException {
        try {
            inventarioServicio.registrarInsumo(insumo);
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error al registrar insumo", e);
        }
    }

    @Override
    public void reabastecerInsumo(String insumoId, Double cantidad) throws RestauranteFachadaException {
        try {
            inventarioServicio.reabastecerStock(insumoId, cantidad);
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error al reabastecer stock", e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosCocina() throws RestauranteFachadaException {
        try {
            List<String> estados = Arrays.asList(
                    EstadoPedido.PENDIENTE.name(),
                    EstadoPedido.EN_PREPARACION.name(),
                    EstadoPedido.LISTO.name()
            );
            return pedidoServicio.obtenerPedidosPorEstado(estados);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error al cargar pedidos de cocina", e);
        }
    }

    @Override
    public void actualizarEstadoPedido(String id, EstadoPedido nuevoEstado) throws RestauranteFachadaException {
        try {
            Pedido p = pedidoServicio.obtenerPedidoPorId(id);
            if (p != null) {
                p.setEstado(nuevoEstado);
                pedidoServicio.actualizarPedido(id, p);
            }
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("No se pudo cambiar el estado del pedido", e);
        }
    }

    @Override
    public void actualizarInsumo(Insumo insumo) throws RestauranteFachadaException {
        try {
            inventarioServicio.actualizarInsumo(insumo);
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error al actualizar el insumo: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarInsumo(Insumo insumo) throws RestauranteFachadaException {
        try {
            if (insumo.getId() != null) {
                inventarioServicio.eliminarInsumo(insumo.getId());
            } else {
                throw new RestauranteFachadaException("No se puede eliminar un insumo sin ID.");
            }
        } catch (RestauranteFachadaException | InventarioException e) {
            throw new RestauranteFachadaException("Error al eliminar el insumo: " + e.getMessage(), e);
        }
    }
}
