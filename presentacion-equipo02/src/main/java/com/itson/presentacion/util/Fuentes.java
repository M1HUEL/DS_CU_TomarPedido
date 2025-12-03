package com.itson.presentacion.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class Fuentes {

    private static Font poppinsRegular;
    private static Font poppinsBold;
    private static Font poppinsItalic;
    private static Font poppinsSemiBold;

    private static void cargarFuentes() {
        if (poppinsRegular != null) {
            return; // ya cargadas
        }
        poppinsRegular = cargarFuente("/fonts/Poppins-Regular.ttf", Font.PLAIN);
        poppinsBold = cargarFuente("/fonts/Poppins-Bold.ttf", Font.BOLD);
        poppinsItalic = cargarFuente("/fonts/Poppins-Italic.ttf", Font.ITALIC);
        poppinsSemiBold = cargarFuente("/fonts/Poppins-SemiBold.ttf", Font.BOLD);
    }

    private static Font cargarFuente(String ruta, int estiloFallback) {
        try (InputStream is = Fuentes.class.getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("No se encontró la fuente: " + ruta);
                return new Font("SansSerif", estiloFallback, 14);
            }
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuente);
            return fuente;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", estiloFallback, 14);
        }
    }

    public static Font getPoppinsRegular(float size) {
        cargarFuentes();
        return poppinsRegular.deriveFont(size);
    }

    public static Font getPoppinsBold(float size) {
        cargarFuentes();
        return poppinsBold.deriveFont(size);
    }

    public static Font getPoppinsItalic(float size) {
        cargarFuentes();
        return poppinsItalic.deriveFont(size);
    }

    public static Font getPoppinsSemiBold(float size) {
        cargarFuentes();
        return poppinsSemiBold.deriveFont(size);
    }
}
