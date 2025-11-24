package com.itson.controlPedido.util;

import com.itson.dto.PedidoDTO;
import com.itson.dto.ProductoDTO;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import java.util.stream.Collectors;

public class PedidoConvertidor {

    public Pedido dtoADominio(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setNombre(dto.getNombre());
        pedido.setComentario(dto.getComentario());
        pedido.setPrecio(dto.getPrecio());
        pedido.setFechaPedido(dto.getFechaPedido());

        if (dto.getProductos() != null) {
            pedido.setProductos(
                    dto.getProductos()
                            .stream()
                            .map(this::productoADominio)
                            .collect(Collectors.toList())
            );
        }

        return pedido;
    }

    private Producto productoADominio(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());

        if (dto.getIngredientes() != null) {
            producto.setIngredientes(
                    dto.getIngredientes().stream()
                            .map(i -> {
                                Ingrediente ing = new Ingrediente();
                                ing.setId(i.getId());
                                ing.setNombre(i.getNombre());
                                ing.setPrecio(i.getPrecio());
                                ing.setInventarioItemId(i.getInventarioItemId());
                                ing.setCantidadRequerida(i.getCantidadRequerida());
                                return ing;
                            })
                            .collect(Collectors.toList())
            );
        }

        if (dto.getComplementos() != null) {
            producto.setComplementos(
                    dto.getComplementos().stream()
                            .map(c -> {
                                Complemento comp = new Complemento();
                                comp.setId(c.getId());
                                comp.setNombre(c.getNombre());
                                comp.setPrecio(c.getPrecio());
                                comp.setInventarioItemId(c.getInventarioItemId());
                                comp.setCantidadRequerida(c.getCantidadRequerida());
                                return comp;
                            })
                            .collect(Collectors.toList())
            );
        }

        if (dto.getExtras() != null) {
            producto.setExtras(
                    dto.getExtras().stream()
                            .map(e -> {
                                Extra extra = new Extra();
                                extra.setId(e.getId());
                                extra.setNombre(e.getNombre());
                                extra.setPrecio(e.getPrecio());
                                extra.setInventarioItemId(e.getInventarioItemId());
                                extra.setCantidadRequerida(e.getCantidadRequerida());
                                return extra;
                            })
                            .collect(Collectors.toList())
            );
        }

        return producto;
    }
}
