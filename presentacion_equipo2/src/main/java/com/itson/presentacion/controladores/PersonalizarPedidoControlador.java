package com.itson.presentacion.controladores;

import com.itson.presentacion.pantallas.SeleccionarMetodoPagoPantalla;
import com.itson.presentacion.pantallas.componentes.Texto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PersonalizarPedidoControlador {

    private final JFrame ventana;
    private final List<JCheckBox> ingredientes;
    private final String nombrePedido;
    private final JTextArea txtComentario;

    public PersonalizarPedidoControlador(JFrame ventana, List<JCheckBox> ingredientes, String nombrePedido) {
        this.ventana = ventana;
        this.ingredientes = ingredientes;
        this.nombrePedido = nombrePedido;
        this.txtComentario = new JTextArea(4, 20);
        configurarTextArea();
    }

    private void configurarTextArea() {
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setFont(new Font("Poppins", Font.PLAIN, 15));
        txtComentario.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        txtComentario.setBackground(new Color(255, 250, 240));
    }

    public JPanel crearPanelComentario() {
        JPanel comentarioPanel = new JPanel(new BorderLayout(5, 5));
        comentarioPanel.setBackground(Color.WHITE);
        comentarioPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        Texto lblComentario = new Texto();
        lblComentario.setText("Comentarios adicionales:");
        comentarioPanel.add(lblComentario, BorderLayout.NORTH);
        comentarioPanel.add(new JScrollPane(txtComentario), BorderLayout.CENTER);

        return comentarioPanel;
    }

    public void confirmarPedido() {
        List<String> seleccionados = new ArrayList<>();
        for (JCheckBox cb : ingredientes) {
            if (cb.isSelected()) {
                seleccionados.add(cb.getText());
            }
        }

        String mensaje = seleccionados.isEmpty()
                ? "No seleccionaste ingredientes adicionales."
                : "Tu " + nombrePedido + " incluye:\n• " + String.join("\n• ", seleccionados);

        if (!txtComentario.getText().isBlank()) {
            mensaje += "\n\nComentarios:\n" + txtComentario.getText();
        }

        JOptionPane.showMessageDialog(ventana, mensaje, "Pedido confirmado", JOptionPane.INFORMATION_MESSAGE);

        new SeleccionarMetodoPagoPantalla().setVisible(true);
        ventana.dispose();
    }

    public void cancelar() {
        int opcion = JOptionPane.showConfirmDialog(ventana,
                "¿Deseas cancelar la personalización?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            ventana.dispose();
        }
    }
}
