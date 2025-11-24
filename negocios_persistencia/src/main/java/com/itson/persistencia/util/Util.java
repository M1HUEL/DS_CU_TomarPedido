package com.itson.persistencia.util;

import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.Decimal128;

public class Util {

    public static List<Document> mapIngredientes(List<Ingrediente> ingredientes) {
        List<Document> docs = new ArrayList<>();
        if (ingredientes == null) {
            return docs;
        }

        for (Ingrediente ing : ingredientes) {
            docs.add(new Document()
                    .append("id", ing.getId())
                    .append("nombre", ing.getNombre())
                    .append("precio", ing.getPrecio())
                    .append("inventarioItemId", ing.getInventarioItemId())
                    .append("cantidadRequerida", ing.getCantidadRequerida())
            );
        }
        return docs;
    }

    public static List<Document> mapComplementos(List<Complemento> complementos) {
        List<Document> docs = new ArrayList<>();
        if (complementos == null) {
            return docs;
        }

        for (Complemento c : complementos) {
            docs.add(new Document()
                    .append("id", c.getId())
                    .append("nombre", c.getNombre())
                    .append("precio", c.getPrecio())
                    .append("inventarioItemId", c.getInventarioItemId())
                    .append("cantidadRequerida", c.getCantidadRequerida())
            );
        }
        return docs;
    }

    public static List<Document> mapExtras(List<Extra> extras) {
        List<Document> docs = new ArrayList<>();
        if (extras == null) {
            return docs;
        }

        for (Extra e : extras) {
            docs.add(new Document()
                    .append("id", e.getId())
                    .append("nombre", e.getNombre())
                    .append("precio", e.getPrecio())
                    .append("inventarioItemId", e.getInventarioItemId())
                    .append("cantidadRequerida", e.getCantidadRequerida())
            );
        }
        return docs;
    }

    public static double convertirADouble(Object valor) {
        if (valor == null) {
            return 0.0;
        }

        return switch (valor) {
            case Double d ->
                d;
            case Integer i ->
                i.doubleValue();
            case Long l ->
                l.doubleValue();
            case Decimal128 d ->
                d.doubleValue();
            default ->
                throw new IllegalArgumentException(
                        "No se puede convertir a double: " + valor.getClass()
                );
        };
    }

    public static BigDecimal convertirABigDecimal(Object valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }

        return switch (valor) {
            case BigDecimal b ->
                b;
            case Double d ->
                BigDecimal.valueOf(d);
            case Integer i ->
                BigDecimal.valueOf(i);
            case Long l ->
                BigDecimal.valueOf(l);
            case Decimal128 d ->
                d.bigDecimalValue();
            default ->
                throw new IllegalArgumentException(
                        "No se puede convertir a BigDecimal: " + valor.getClass()
                );
        };
    }
}
