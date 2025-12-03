package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.*;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ProductoMapper {

    public static Producto fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Producto p = new Producto();

        if (doc.getObjectId("_id") != null) {
            p.setId(doc.getObjectId("_id").toHexString());
        }

        p.setNombre(doc.getString("nombre"));

        Double precioDoc = doc.getDouble("precio");
        if (precioDoc != null) {
            p.setPrecio(precioDoc);
        } else {
            p.setPrecio(0.0);
        }

        // --- INGREDIENTES ---
        List<Document> ingDocs = (List<Document>) doc.get("ingredientes");
        if (ingDocs != null) {
            List<Ingrediente> ingredientes = new ArrayList<>();
            for (Document d : ingDocs) {
                Ingrediente i = new Ingrediente();

                // ID sin ternaria
                if (d.getObjectId("_id") != null) {
                    i.setId(d.getObjectId("_id").toHexString());
                } else {
                    i.setId(d.getString("id"));
                }

                i.setNombre(d.getString("nombre"));
                i.setDescripcion(d.getString("descripcion"));

                // Precio sin ternaria
                Double precioIng = d.getDouble("precio");
                if (precioIng != null) {
                    i.setPrecio(precioIng);
                } else {
                    i.setPrecio(0.0);
                }

                // NUEVOS ATRIBUTOS DE INVENTARIO
                i.setInsumoId(d.getString("insumoId"));

                Double cant = d.getDouble("cantidadNecesaria");
                if (cant != null) {
                    i.setCantidadNecesaria(cant);
                } else {
                    i.setCantidadNecesaria(0.0);
                }

                ingredientes.add(i);
            }
            p.setIngredientes(ingredientes);
        }

        // --- COMPLEMENTOS ---
        List<Document> compDocs = (List<Document>) doc.get("complementos");
        if (compDocs != null) {
            List<Complemento> complementos = new ArrayList<>();
            for (Document d : compDocs) {
                Complemento c = new Complemento();

                if (d.getObjectId("_id") != null) {
                    c.setId(d.getObjectId("_id").toHexString());
                } else {
                    c.setId(d.getString("id"));
                }

                c.setNombre(d.getString("nombre"));
                c.setDescripcion(d.getString("descripcion"));

                Double precioComp = d.getDouble("precio");
                if (precioComp != null) {
                    c.setPrecio(precioComp);
                } else {
                    c.setPrecio(0.0);
                }

                // NUEVOS ATRIBUTOS DE INVENTARIO
                c.setInsumoId(d.getString("insumoId"));

                Double cant = d.getDouble("cantidadNecesaria");
                if (cant != null) {
                    c.setCantidadNecesaria(cant);
                } else {
                    c.setCantidadNecesaria(0.0);
                }

                complementos.add(c);
            }
            p.setComplementos(complementos);
        }

        // --- EXTRAS ---
        List<Document> extraDocs = (List<Document>) doc.get("extras");
        if (extraDocs != null) {
            List<Extra> extras = new ArrayList<>();
            for (Document d : extraDocs) {
                Extra e = new Extra();

                if (d.getObjectId("_id") != null) {
                    e.setId(d.getObjectId("_id").toHexString());
                } else {
                    e.setId(d.getString("id"));
                }

                e.setNombre(d.getString("nombre"));
                e.setDescripcion(d.getString("descripcion"));

                Double precioExtra = d.getDouble("precio");
                if (precioExtra != null) {
                    e.setPrecio(precioExtra);
                } else {
                    e.setPrecio(0.0);
                }

                // NUEVOS ATRIBUTOS DE INVENTARIO
                e.setInsumoId(d.getString("insumoId"));

                Double cant = d.getDouble("cantidadNecesaria");
                if (cant != null) {
                    e.setCantidadNecesaria(cant);
                } else {
                    e.setCantidadNecesaria(0.0);
                }

                extras.add(e);
            }
            p.setExtras(extras);
        }

        return p;
    }

    public static Document toDocument(Producto p) {
        if (p == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("nombre", p.getNombre());
        doc.append("precio", p.getPrecio());

        // --- INGREDIENTES ---
        if (p.getIngredientes() != null) {
            List<Document> listaDocs = new ArrayList<>();
            for (Ingrediente i : p.getIngredientes()) {
                Document d = new Document();
                d.append("nombre", i.getNombre());
                d.append("descripcion", i.getDescripcion());
                d.append("precio", i.getPrecio());
                // Nuevos campos
                d.append("insumoId", i.getInsumoId());
                d.append("cantidadNecesaria", i.getCantidadNecesaria());

                listaDocs.add(d);
            }
            doc.append("ingredientes", listaDocs);
        }

        // --- COMPLEMENTOS ---
        if (p.getComplementos() != null) {
            List<Document> listaDocs = new ArrayList<>();
            for (Complemento c : p.getComplementos()) {
                Document d = new Document();
                d.append("nombre", c.getNombre());
                d.append("descripcion", c.getDescripcion());
                d.append("precio", c.getPrecio());
                // Nuevos campos
                d.append("insumoId", c.getInsumoId());
                d.append("cantidadNecesaria", c.getCantidadNecesaria());

                listaDocs.add(d);
            }
            doc.append("complementos", listaDocs);
        }

        // --- EXTRAS ---
        if (p.getExtras() != null) {
            List<Document> listaDocs = new ArrayList<>();
            for (Extra e : p.getExtras()) {
                Document d = new Document();
                d.append("nombre", e.getNombre());
                d.append("descripcion", e.getDescripcion());
                d.append("precio", e.getPrecio());
                // Nuevos campos
                d.append("insumoId", e.getInsumoId());
                d.append("cantidadNecesaria", e.getCantidadNecesaria());

                listaDocs.add(d);
            }
            doc.append("extras", listaDocs);
        }

        return doc;
    }
}
