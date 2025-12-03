package com.itson.fachada;

import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.inventario.exception.InventarioException;
import com.itson.inventario.service.InventarioService;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.service.UsuarioService;
import com.itson.negocios.usuario.service.impl.UsuarioServiceImpl;
import com.itson.pedido.exception.PedidoException;
import com.itson.pedido.service.PedidoService;
import com.itson.pedido.service.impl.PedidoServiceImpl;
import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Usuario;
import com.itson.producto.exception.ProductoException;
import com.itson.producto.service.ProductoService;
import com.itson.producto.service.impl.ProductoServiceImpl;
import java.util.List;

public class RestauranteFachadaImpl implements RestauranteFachada {

    private final UsuarioService usuarioServicio;
    private final ProductoService productoServicio;
    private final PedidoService pedidoServicio;
    private final InventarioService inventarioServicio;

    public RestauranteFachadaImpl() {
        this.usuarioServicio = new UsuarioServiceImpl();
        this.productoServicio = new ProductoServiceImpl();
        this.pedidoServicio = new PedidoServiceImpl();
        this.inventarioServicio = new InventarioServiceImpl();
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
    public void crearPedido(Pedido pedido) throws RestauranteFachadaException {
        try {
            pedidoServicio.agregarPedido(pedido);
        } catch (PedidoException e) {
            throw new RestauranteFachadaException("Error al procesar el pedido: " + e.getMessage(), e);
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
    public List<Insumo> obtenerInsumos() throws RestauranteFachadaException {
        try {
            return inventarioServicio.obtenerTodosLosInsumos();
        } catch (InventarioException e) {
            throw new RestauranteFachadaException("Error al obtener inventario", e);
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
}
