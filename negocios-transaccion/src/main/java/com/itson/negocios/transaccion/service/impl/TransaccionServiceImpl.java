package com.itson.negocios.transaccion.service.impl;

import com.itson.negocios.transaccion.exception.TransaccionException;
import com.itson.negocios.transaccion.service.TransaccionService;
import com.itson.persistencia.dao.PagoDAO;
import com.itson.persistencia.dao.impl.PagoDAOImpl;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public class TransaccionServiceImpl implements TransaccionService {

    private final PagoDAO pagoDAO;

    public TransaccionServiceImpl() {
        this.pagoDAO = new PagoDAOImpl();
    }

    @Override
    public List<Pago> obtenerPagos() throws TransaccionException {
        try {
            return pagoDAO.consultarTodos();
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al obtener la lista de pagos.", e);
        }
    }

    @Override
    public Pago obtenerPagoPorId(String id) throws TransaccionException {
        try {
            return pagoDAO.consultarPorId(id);
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al buscar el pago por ID: " + id, e);
        }
    }

    @Override
    public Pago obtenerPagoPorPedidoId(String pedidoId) throws TransaccionException {
        try {
            return pagoDAO.consultarPorPedidoId(pedidoId);
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al buscar el pago del pedido: " + pedidoId, e);
        }
    }

    @Override
    public void agregarPago(Pago pago) throws TransaccionException {
        if (pago == null) {
            throw new TransaccionException("El objeto Pago no puede ser nulo.");
        }
        if (pago.getMonto() <= 0) {
            throw new TransaccionException("El monto del pago debe ser mayor a 0.");
        }

        try {
            pagoDAO.agregar(pago);
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al registrar el pago en el sistema.", e);
        }
    }

    @Override
    public void actualizarPago(String id, Pago pago) throws TransaccionException {
        try {
            pagoDAO.actualizar(id, pago);
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al actualizar el pago.", e);
        }
    }

    @Override
    public void eliminarPago(String id) throws TransaccionException {
        try {
            pagoDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new TransaccionException("Error al eliminar el pago.", e);
        }
    }
}
