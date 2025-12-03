package com.itson.fachada;

import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.inventario.servicio.InventarioServicio;
import com.itson.inventario.servicio.impl.InventarioServicioImpl;
import com.itson.negocios.usuario.exception.UsuarioException;
import com.itson.negocios.usuario.servicio.UsuarioServicio;
import com.itson.negocios.usuario.servicio.impl.UsuarioServicioImpl;
import com.itson.pedido.exception.PedidoException;
import com.itson.pedido.servicio.PedidoServicio;
import com.itson.pedido.servicio.impl.PedidoServicioImpl;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Usuario;
import com.itson.producto.exception.ProductoException;
import com.itson.producto.servicio.ProductoServicio;
import com.itson.producto.servicio.impl.ProductoServicioImpl;
import java.util.List;

public class RestauranteFachadaImpl implements RestauranteFachada {

    private final UsuarioServicio usuarioServicio;
    private final ProductoServicio productoServicio;
    private final PedidoServicio pedidoServicio;
    private final InventarioServicio inventarioServicio;

    public RestauranteFachadaImpl() {
        this.usuarioServicio = new UsuarioServicioImpl();
        this.productoServicio = new ProductoServicioImpl();
        this.pedidoServicio = new PedidoServicioImpl();
        this.inventarioServicio = new InventarioServicioImpl(); // todavía no se usa
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) throws RestauranteFachadaException {
        try {
            return usuarioServicio.obtenerUsuarioPorId(id);
        } catch (UsuarioException e) {
            throw new RestauranteFachadaException("Error obteniendo usuario por id", e);
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
            throw new RestauranteFachadaException("Error obteniendo producto por id", e);
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
            throw new RestauranteFachadaException("Error creando pedido", e);
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
    public void actualizarInventario(String productoId, int cantidad) throws RestauranteFachadaException {
        throw new RestauranteFachadaException("Funcionalidad de inventario no implementada todavía");
    }
}
