package com.itson.controlPedido;

import com.itson.controlPedido.controlador.PedidoControlador;
import com.itson.controlPedido.util.PedidoConvertidor;
import com.itson.dto.PedidoDTO;
import com.itson.persistencia.dominio.Pedido;

public class PedidoFachadaImpl implements PedidoFachada {

    private final PedidoControlador pedidoControlador;
    private final PedidoConvertidor convertidor;

    public PedidoFachadaImpl() {
        this.pedidoControlador = new PedidoControlador();
        this.convertidor = new PedidoConvertidor();
    }

    @Override
    public void crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = convertidor.dtoADominio(pedidoDTO);
        pedidoControlador.crearPedido(pedido);
    }

}
