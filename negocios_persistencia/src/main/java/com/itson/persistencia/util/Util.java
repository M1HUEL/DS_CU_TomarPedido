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
        List<Document> lista = new ArrayList<>();
        if (ingredientes != null) {
            for (Ingrediente i : ingredientes) {
                lista.add(new Document()
                        .append("id", i.getId())
                        .append("nombre", i.getNombre())
                        .append("precio", i.getPrecio()));
            }
        }
        return lista;
    }

    public static List<Document> mapComplementos(List<Complemento> complementos) {
        List<Document> lista = new ArrayList<>();
        if (complementos != null) {
            for (Complemento c : complementos) {
                lista.add(new Document()
                        .append("id", c.getId())
                        .append("nombre", c.getNombre())
                        .append("precio", c.getPrecio()));
            }
        }
        return lista;
    }

    public static List<Document> mapExtras(List<Extra> extras) {
        List<Document> lista = new ArrayList<>();
        if (extras != null) {
            for (Extra e : extras) {
                lista.add(new Document()
                        .append("id", e.getId())
                        .append("nombre", e.getNombre())
                        .append("precio", e.getPrecio()));
            }
        }
        return lista;
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
