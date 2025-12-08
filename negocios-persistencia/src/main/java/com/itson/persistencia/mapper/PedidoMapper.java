package com.itson.persistencia.mapper;

import com.itson.persistencia.dominio.EstadoPedido;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class PedidoMapper {

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

        List<Document> productosDocs = (List<Document>) doc.get("productos");
        if (productosDocs != null) {
            List<Producto> productos = new ArrayList<>();
            for (Document prodDoc : productosDocs) {
                productos.add(ProductoMapper.fromDocument(prodDoc));
            }
            pedido.setProductos(productos);
        }

        try {
            String estadoStr = doc.getString("estado");
            if (estadoStr != null) {
                pedido.setEstado(EstadoPedido.valueOf(estadoStr));
            } else {
                pedido.setEstado(EstadoPedido.PENDIENTE);
            }
        } catch (IllegalArgumentException e) {
            pedido.setEstado(EstadoPedido.PENDIENTE);
        }

        return pedido;
    }

    public static Document toDocument(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("nombre", pedido.getNombre());
        doc.append("comentario", pedido.getComentario());
        doc.append("precio", pedido.getPrecio());

        List<Producto> productos = pedido.getProductos();
        if (productos != null) {
            List<Document> productosDocs = new ArrayList<>();
            for (Producto prod : productos) {
                productosDocs.add(ProductoMapper.toDocument(prod));
            }
            doc.append("productos", productosDocs);
        }

        if (pedido.getEstado() != null) {
            doc.append("estado", pedido.getEstado().name());
        } else {
            doc.append("estado", EstadoPedido.PENDIENTE.name());
        }

        return doc;
    }
}
