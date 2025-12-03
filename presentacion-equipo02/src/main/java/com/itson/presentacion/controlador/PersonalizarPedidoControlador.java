package com.itson.presentacion.controlador;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import java.util.Map;
import javax.swing.JCheckBox;

public interface PersonalizarPedidoControlador {

    void completarPedido(Producto producto, Map<Object, JCheckBox> selecciones, String comentario);
}
