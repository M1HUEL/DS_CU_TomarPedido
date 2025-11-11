package com.itson.controlPedido;

import com.itson.controlPedido.controlador.PedidoControlador;
import com.itson.dtos.PedidoDTO;

public class PedidoFachadaImpl implements PedidoFachada {

    private PedidoControlador pedidoControlador;

    public PedidoFachadaImpl() {
        this.pedidoControlador = new PedidoControlador();
    }

    @Override
    public void crearPedido(PedidoDTO pedidoDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
