package com.itson.controlPedido;

import com.itson.controlPedido.util.PedidoConvertidor;
import com.itson.dto.PedidoDTO;
import com.itson.persistencia.dominio.Pedido;
import com.itson.controlPedido.servicio.PedidoServicio;
import com.itson.controlPedido.servicio.impl.PedidoServicioImpl;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoFachadaImpl implements PedidoFachada {

    private final PedidoServicio servicio;
    private final PedidoConvertidor convertidor;

    public PedidoFachadaImpl() {
        this.servicio = new PedidoServicioImpl();
        this.convertidor = new PedidoConvertidor();
    }

//    @Override
//    public List<PedidoDTO> consultarPedidosPorIdProducto(String idProducto) {
//        List<Pedido> pedidos = servicio.obtenerPedidoPorId(idProducto);
//        return pedidos.stream()
//                .map(convertidor::dominioADTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<PedidoDTO> consultarPedidosPorNombreProducto(String nombreProducto) {
//        List<Pedido> pedidos = servicio.obtenerPedidoPorId(nombreProducto);
//        return pedidos.stream()
//                .map(convertidor::dominioADTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public boolean crearPedido(PedidoDTO pedidoDTO) {
        if (pedidoDTO == null) {
            System.out.println("PedidoDTO nulo. No se puede crear el pedido.");
            return false;
        }

        Pedido pedido = convertidor.dtoADominio(pedidoDTO);

        return servicio.crearPedido(pedido);
    }

    @Override
    public List<PedidoDTO> consultarPedidosPorIdProducto(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<PedidoDTO> consultarPedidosPorNombreProducto(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
