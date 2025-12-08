package com.itson.presentacion.controller.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.HistorialPedidosCompletadosController;
import com.itson.presentacion.frame.InicioFrame;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HistorialPedidosCompletadosControllerImpl implements HistorialPedidosCompletadosController {

    private final JFrame frame;
    private final RestauranteFachada fachada;

    public HistorialPedidosCompletadosControllerImpl(JFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Pedido> cargarVentas() {
        try {
            return fachada.obtenerPedidosCompletados();
        } catch (RestauranteFachadaException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void salir() {
        frame.dispose();
        new InicioFrame().setVisible(true);
    }

    @Override
    public void generarReportePDF() {
        List<Pedido> ventas = cargarVentas();
        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No hay ventas para generar reporte.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Document document = new Document();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte de Ventas");
            String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            fileChooser.setSelectedFile(new java.io.File("Reporte_Ventas_" + fechaHoy + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                Paragraph titulo = new Paragraph("REPORTE HISTÓRICO DE VENTAS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.ORANGE));
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                document.add(new Paragraph("Generado el: " + new Date().toString()));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{2, 4, 2, 2});

                addTableHeader(table, "ID / Folio");
                addTableHeader(table, "Cliente");
                addTableHeader(table, "Estado");
                addTableHeader(table, "Monto");

                double granTotal = 0.0;

                for (Pedido p : ventas) {
                    String idCorto = (p.getId() != null && p.getId().length() > 6) ? "..." + p.getId().substring(p.getId().length() - 6) : p.getId();

                    table.addCell(idCorto);
                    table.addCell(p.getNombre());
                    table.addCell(p.getEstado().name());

                    PdfPCell celdaPrecio = new PdfPCell(new Phrase("$" + String.format("%.2f", p.getPrecio())));
                    celdaPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(celdaPrecio);

                    granTotal += p.getPrecio();
                }

                PdfPCell cellTotalLabel = new PdfPCell(new Phrase("TOTAL RECAUDADO:"));
                cellTotalLabel.setColspan(3);
                cellTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTotalLabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cellTotalLabel);

                PdfPCell cellTotalValue = new PdfPCell(new Phrase("$" + String.format("%.2f", granTotal), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cellTotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellTotalValue.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cellTotalValue);

                document.add(table);
                document.close();

                JOptionPane.showMessageDialog(frame, "Reporte guardado exitosamente.");
            }
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(frame, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTableHeader(PdfPTable table, String title) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.ORANGE);
        header.setPhrase(new Phrase(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setPadding(5);
        table.addCell(header);
    }
}
