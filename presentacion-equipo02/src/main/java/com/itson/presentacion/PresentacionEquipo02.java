package com.itson.presentacion;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.Producto;
import java.util.ArrayList;
import java.util.List;

public class PresentacionEquipo02 {

    public static void main(String[] args) {
        System.out.println("Iniciando carga de datos...");

        RestauranteFachada fachada = new RestauranteFachadaImpl();

        try {
            // --- PRODUCTO 1: HAMBURGUESA ---
            List<Ingrediente> ingBurger = new ArrayList<>();
            ingBurger.add(new Ingrediente("Carne Res", "test", 19.21));
            ingBurger.add(new Ingrediente("Lechuga", "test", 53.32));

            List<Extra> extrasBurger = new ArrayList<>();
            extrasBurger.add(new Extra("Tocino", "Tira de tocino crujiente", 15.0));
            extrasBurger.add(new Extra("Queso Extra", "Rebanada de queso americano", 10.0));

            Producto hamburguesa = new Producto(
                    "Hamburguesa Clásica",
                    ingBurger,
                    new ArrayList<>(), // Sin complementos
                    extrasBurger,
                    85.00
            );
            fachada.crearProducto(hamburguesa);
            System.out.println(" + Agregada: Hamburguesa Clásica");

            // --- PRODUCTO 2: PIZZA ---
            List<Ingrediente> ingPizza = new ArrayList<>();
            ingPizza.add(new Ingrediente("Masa", "test", 21.23));
            ingPizza.add(new Ingrediente("Salsa Tomate", "test", 54.32));

            List<Complemento> compPizza = new ArrayList<>();
            compPizza.add(new Complemento("Salsa Chimichurri", "Salsa especial de la casa", 0.0));

            List<Extra> extrasPizza = new ArrayList<>();
            extrasPizza.add(new Extra("Orilla de Queso", "Relleno de queso crema", 25.0));

            Producto pizza = new Producto(
                    "Pizza Pepperoni",
                    ingPizza,
                    compPizza,
                    extrasPizza,
                    140.00
            );
            fachada.crearProducto(pizza);
            System.out.println(" + Agregada: Pizza Pepperoni");

            // --- PRODUCTO 3: ENSALADA ---
            List<Ingrediente> ingEnsalada = new ArrayList<>();
            ingEnsalada.add(new Ingrediente("Lechuga Romana", "test", 30.54));
            ingEnsalada.add(new Ingrediente("Crutones", "test", 23.54));

            Producto ensalada = new Producto(
                    "Ensalada César",
                    ingEnsalada,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    95.00
            );
            fachada.crearProducto(ensalada);
            System.out.println(" + Agregada: Ensalada César");

            // --- PRODUCTO 4: REFRESCO ---
            Producto refresco = new Producto(
                    "Refresco Cola",
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    30.00
            );
            fachada.crearProducto(refresco);
            System.out.println(" + Agregado: Refresco Cola");

            System.out.println("¡Carga de datos completada con éxito!");

        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
