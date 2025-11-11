package com.itson.presentacion.pantallas;

import com.itson.presentacion.controladores.IniciarSesionControlador;
import com.itson.presentacion.pantallas.componentes.Boton;
import com.itson.presentacion.pantallas.componentes.CampoFormulario;
import com.itson.presentacion.pantallas.componentes.Encabezado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class IniciarSesionPantalla extends JFrame {

    private CampoFormulario campoNombre;
    private CampoFormulario campoContraseña;
    private final Boton btnIniciarSesion;

    private final IniciarSesionControlador controlador;

    public IniciarSesionPantalla() {
        controlador = new IniciarSesionControlador();

        setTitle("Iniciar Sesión");
        setSize(new Dimension(540, 820));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Encabezado header = new Encabezado("Iniciar Sesión");
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 252, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formulario = new JPanel();
        formulario.setBackground(new Color(255, 252, 245));
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        campoNombre = new CampoFormulario("Nombre", 35);
        campoContraseña = new CampoFormulario("Contraseña", 35);

        formulario.add(campoNombre);
        formulario.add(Box.createVerticalStrut(25));
        formulario.add(campoContraseña);
        formulario.add(Box.createVerticalStrut(40));

        btnIniciarSesion = new Boton("Iniciar Sesión");
        formulario.add(btnIniciarSesion);

        panel.add(formulario, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnIniciarSesion.addActionListener(e -> {
            String nombre = campoNombre.getCampo().getText().trim();
            String contraseña = campoContraseña.getCampo().getText().trim();
            controlador.iniciarSesion(nombre, contraseña, this);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IniciarSesionPantalla::new);
    }
}
