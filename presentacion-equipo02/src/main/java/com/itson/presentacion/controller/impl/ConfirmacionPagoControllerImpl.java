package com.itson.presentacion.controller.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.ConfirmacionPagoController;
import com.itson.presentacion.frame.InicioFrame;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfirmacionPagoControllerImpl implements ConfirmacionPagoController {

    private final JFrame frame;

    public ConfirmacionPagoControllerImpl(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void terminarProceso() {
        if (frame != null) {
            frame.dispose();
        }
        new InicioFrame().setVisible(true);
    }

    @Override
    public void generarReciboPDF(Pedido pedido, Pago pago) {
        Document document = new Document();

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Recibo PDF");
            fileChooser.setSelectedFile(new java.io.File("Ticket_" + pedido.getId() + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
                Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
                Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

                Paragraph titulo = new Paragraph("RESTAURANTE ITSON", fontTitulo);
                titulo.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(titulo);

                document.add(new Paragraph("----------------------------------------------------------------", fontNormal));

                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                document.add(new Paragraph("Folio: " + pedido.getId(), fontNegrita));
                document.add(new Paragraph("Fecha: " + fecha, fontNormal));
                document.add(new Paragraph("Cliente: " + pedido.getNombre(), fontNormal));

                document.add(new Paragraph("\n"));

                document.add(new Paragraph("DETALLE DE CONSUMO:", fontNegrita));
                if (pedido.getProductos() != null) {
                    for (Producto p : pedido.getProductos()) {
                        String linea = String.format("- %s   $%.2f", p.getNombre(), p.getPrecio());
                        document.add(new Paragraph(linea, fontNormal));
                    }
                }

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("----------------------------------------------------------------", fontNormal));

                document.add(new Paragraph("TOTAL A PAGAR:   $" + String.format("%.2f", pedido.getPrecio()), fontTitulo));
                document.add(new Paragraph("Método de Pago: " + pago.getMetodoPago(), fontNormal));
                document.add(new Paragraph("Monto Recibido: $" + String.format("%.2f", pago.getMonto()), fontNormal));

                document.add(new Paragraph("\n"));
                Paragraph footer = new Paragraph("¡Gracias por su preferencia!", fontNormal);
                footer.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(footer);

                document.close();
                JOptionPane.showMessageDialog(frame, "Ticket guardado exitosamente en:\n" + filePath);
            }
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(frame, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
