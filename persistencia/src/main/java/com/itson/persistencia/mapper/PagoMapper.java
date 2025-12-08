package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.EstadoPago;
import com.itson.persistencia.dominio.MetodoPago;
import com.itson.persistencia.dominio.Pago;
import org.bson.Document;
import java.time.ZoneId;
import java.util.Date;

public class PagoMapper {

    public static Pago fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Pago pago = new Pago();

        if (doc.getObjectId("_id") != null) {
            pago.setId(doc.getObjectId("_id").toHexString());
        }

        pago.setPedidoId(doc.getString("pedidoId"));
        pago.setMonto(doc.getDouble("monto"));

        Date fechaMongo = doc.getDate("fechaPago");
        if (fechaMongo != null) {
            pago.setFechaPago(fechaMongo.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        try {
            String metodoStr = doc.getString("metodoPago");
            if (metodoStr != null) {
                pago.setMetodoPago(MetodoPago.valueOf(metodoStr));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error mapeando MetodoPago: " + e.getMessage());
        }

        try {
            String estadoStr = doc.getString("estado");
            if (estadoStr != null) {
                pago.setEstado(EstadoPago.valueOf(estadoStr));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error mapeando EstadoPago: " + e.getMessage());
        }

        return pago;
    }

    public static Document toDocument(Pago pago) {
        if (pago == null) {
            return null;
        }

        Document doc = new Document();

        doc.append("pedidoId", pago.getPedidoId());
        doc.append("monto", pago.getMonto());

        if (pago.getFechaPago() != null) {
            doc.append("fechaPago", Date.from(pago.getFechaPago()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }

        doc.append("metodoPago", pago.getMetodoPago() != null ? pago.getMetodoPago().name() : null);
        doc.append("estado", pago.getEstado() != null ? pago.getEstado().name() : null);

        return doc;
    }
}
