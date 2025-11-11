package com.itson.controlPedido;

import com.itson.dtos.PedidoDTO;

public class FachadaImpl implements Fachada {

    private static FachadaImpl instancia;
    private final PedidoService pedidoService;

    private FachadaImpl() {
        this.pedidoService = new PedidoService();
    }

    public static FachadaImpl getInstancia() {
        if (instancia == null) {
            instancia = new FachadaImpl();
        }
        return instancia;
    }

    @Override
    public void crearPedido(PedidoDTO pedidoDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
