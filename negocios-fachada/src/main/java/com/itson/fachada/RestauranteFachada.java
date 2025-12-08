package com.itson.fachada;

import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.EstadoPedido;
import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.dominio.Usuario;
import java.util.List;

public interface RestauranteFachada {

    Usuario login(String nombre, String password) throws RestauranteFachadaException;

    Usuario obtenerUsuarioPorId(String id) throws RestauranteFachadaException;

    List<Usuario> obtenerUsuarios() throws RestauranteFachadaException;

    void crearUsuario(Usuario usuario) throws RestauranteFachadaException;

    void actualizarUsuario(String id, Usuario usuario) throws RestauranteFachadaException;

    void eliminarUsuario(String id) throws RestauranteFachadaException;

    Producto obtenerProductoPorId(String id) throws RestauranteFachadaException;

    List<Producto> obtenerProductos() throws RestauranteFachadaException;

    void crearProducto(Producto producto) throws RestauranteFachadaException;

    void actualizarProducto(String id, Producto producto) throws RestauranteFachadaException;

    void eliminarProducto(String id) throws RestauranteFachadaException;

    Pedido obtenerPedidoPorId(String id) throws RestauranteFachadaException;

    List<Pedido> obtenerPedidos() throws RestauranteFachadaException;

    void crearPedido(Pedido pedido) throws RestauranteFachadaException;

    void actualizarPedido(String id, Pedido pedido) throws RestauranteFachadaException;

    void eliminarPedido(String id) throws RestauranteFachadaException;

    List<Insumo> obtenerInsumos() throws RestauranteFachadaException;

    void registrarInsumo(Insumo insumo) throws RestauranteFachadaException;

    void reabastecerInsumo(String insumoId, Double cantidad) throws RestauranteFachadaException;

    void guardarPago(Pago pago) throws RestauranteFachadaException;

    void realizarVentaCompleta(Pedido pedido, Pago pago) throws RestauranteFachadaException;

    void actualizarEstadoPedido(String id, EstadoPedido nuevoEstado) throws RestauranteFachadaException;

    List<Pedido> obtenerPedidosCocina() throws RestauranteFachadaException;
}
