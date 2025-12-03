package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ProductoMapper {

    // Convierte un Document de Mongo a Producto
    public static Producto fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Producto producto = new Producto();

        // Asignar el id generado por Mongo
        if (doc.getObjectId("_id") != null) {
            producto.setId(doc.getObjectId("_id").toHexString());
        }

        producto.setNombre(doc.getString("nombre"));
        producto.setPrecio(doc.getDouble("precio"));

        // Ingredientes
        List<Document> docIngredientes = (List<Document>) doc.get("ingredientes");
        if (docIngredientes != null) {
            List<Ingrediente> ingredientes = new ArrayList<>();
            for (Document d : docIngredientes) {
                Ingrediente i = new Ingrediente();
                i.setId(d.getObjectId("_id") != null ? d.getObjectId("_id").toHexString() : d.getString("id"));
                i.setNombre(d.getString("nombre"));
                i.setDescripcion(d.getString("descripcion"));
                i.setPrecio(d.getDouble("precio"));
                ingredientes.add(i);
            }
            producto.setIngredientes(ingredientes);
        }

        // Complementos
        List<Document> docComplementos = (List<Document>) doc.get("complementos");
        if (docComplementos != null) {
            List<Complemento> complementos = new ArrayList<>();
            for (Document d : docComplementos) {
                Complemento c = new Complemento();
                c.setId(d.getObjectId("_id") != null ? d.getObjectId("_id").toHexString() : d.getString("id"));
                c.setNombre(d.getString("nombre"));
                c.setDescripcion(d.getString("descripcion"));
                c.setPrecio(d.getDouble("precio"));
                complementos.add(c);
            }
            producto.setComplementos(complementos);
        }

        // Extras
        List<Document> docExtras = (List<Document>) doc.get("extras");
        if (docExtras != null) {
            List<Extra> extras = new ArrayList<>();
            for (Document d : docExtras) {
                Extra e = new Extra();
                e.setId(d.getObjectId("_id") != null ? d.getObjectId("_id").toHexString() : d.getString("id"));
                e.setNombre(d.getString("nombre"));
                e.setDescripcion(d.getString("descripcion"));
                e.setPrecio(d.getDouble("precio"));
                extras.add(e);
            }
            producto.setExtras(extras);
        }

        return producto;
    }

    // Convierte un Producto a Document para Mongo
    public static Document toDocument(Producto producto) {
        if (producto == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("nombre", producto.getNombre());
        doc.append("precio", producto.getPrecio());

        // Ingredientes
        if (producto.getIngredientes() != null) {
            List<Document> ingredientes = new ArrayList<>();
            for (Ingrediente i : producto.getIngredientes()) {
                Document d = new Document();
                d.append("nombre", i.getNombre());
                d.append("descripcion", i.getDescripcion());
                d.append("precio", i.getPrecio());
                ingredientes.add(d);
            }
            doc.append("ingredientes", ingredientes);
        }

        // Complementos
        if (producto.getComplementos() != null) {
            List<Document> complementos = new ArrayList<>();
            for (Complemento c : producto.getComplementos()) {
                Document d = new Document();
                d.append("nombre", c.getNombre());
                d.append("descripcion", c.getDescripcion());
                d.append("precio", c.getPrecio());
                complementos.add(d);
            }
            doc.append("complementos", complementos);
        }

        // Extras
        if (producto.getExtras() != null) {
            List<Document> extras = new ArrayList<>();
            for (Extra e : producto.getExtras()) {
                Document d = new Document();
                d.append("nombre", e.getNombre());
                d.append("descripcion", e.getDescripcion());
                d.append("precio", e.getPrecio());
                extras.add(d);
            }
            doc.append("extras", extras);
        }

        return doc;
    }
}
