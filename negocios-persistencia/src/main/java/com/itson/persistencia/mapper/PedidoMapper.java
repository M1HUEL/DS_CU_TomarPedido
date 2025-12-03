package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class PedidoMapper {

    // Convierte un Document de Mongo a Pedido
    public static Pedido fromDocument(Document doc) {
        if (doc == null) {
            return null;
        }

        Pedido pedido = new Pedido();

        if (doc.getObjectId("_id") != null) {
            pedido.setId(doc.getObjectId("_id").toHexString());
        }

        pedido.setNombre(doc.getString("nombre"));
        pedido.setComentario(doc.getString("comentario"));
        pedido.setPrecio(doc.getDouble("precio") != null ? doc.getDouble("precio") : 0.0);

        // Productos
        List<Document> productosDocs = (List<Document>) doc.get("productos");
        if (productosDocs != null) {
            List<Producto> productos = new ArrayList<>();
            for (Document prodDoc : productosDocs) {
                productos.add(ProductoMapper.fromDocument(prodDoc));
            }
            pedido.setProductos(productos);
        }

        return pedido;
    }

    // Convierte un Pedido a Document para Mongo
    public static Document toDocument(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("nombre", pedido.getNombre());
        doc.append("comentario", pedido.getComentario());
        doc.append("precio", pedido.getPrecio());

        // Productos
        List<Producto> productos = pedido.getProductos();
        if (productos != null) {
            List<Document> productosDocs = new ArrayList<>();
            for (Producto producto : productos) {
                productosDocs.add(ProductoMapper.toDocument(producto));
            }
            doc.append("productos", productosDocs);
        }

        return doc;
    }
}
