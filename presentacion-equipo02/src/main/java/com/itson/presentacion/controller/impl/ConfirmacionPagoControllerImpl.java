package com.itson.presentacion.controller.impl;

import com.itson.presentacion.controller.ConfirmacionPagoController;
import com.itson.presentacion.frame.InicioFrame;
import javax.swing.JFrame;

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
}
