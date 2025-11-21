package com.itson.persistencia.util;

import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MongoUtil {

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
}
