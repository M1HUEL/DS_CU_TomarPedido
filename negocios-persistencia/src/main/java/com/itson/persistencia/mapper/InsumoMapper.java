package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.Insumo;
import org.bson.Document;

public class InsumoMapper {

    public static Insumo fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Insumo insumo = new Insumo();

        if (doc.getObjectId("_id") != null) {
            insumo.setId(doc.getObjectId("_id").toHexString());
        }

        insumo.setNombre(doc.getString("nombre"));
        insumo.setCodigo(doc.getString("codigo"));
        insumo.setUnidadMedida(doc.getString("unidadMedida"));

        // Manejo explícito de números para evitar NullPointerException
        Double stockActual = doc.getDouble("stockActual");
        if (stockActual != null) {
            insumo.setStockActual(stockActual);
        } else {
            insumo.setStockActual(0.0);
        }

        Double stockMinimo = doc.getDouble("stockMinimo");
        if (stockMinimo != null) {
            insumo.setStockMinimo(stockMinimo);
        } else {
            insumo.setStockMinimo(0.0);
        }

        return insumo;
    }

    public static Document toDocument(Insumo insumo) {
        if (insumo == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("nombre", insumo.getNombre());
        doc.append("codigo", insumo.getCodigo());
        doc.append("unidadMedida", insumo.getUnidadMedida());
        doc.append("stockActual", insumo.getStockActual());
        doc.append("stockMinimo", insumo.getStockMinimo());

        return doc;
    }
}
