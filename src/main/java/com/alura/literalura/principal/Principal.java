package com.alura.literalura.principal;

import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.model.DatosGutendex;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=Quijote");
        System.out.println("JSON PURO: " + json); // Imprime el json crudo

        // Convertimos el JSON a objetos Java
        var datos = conversor.obtenerDatos(json, DatosGutendex.class);

        System.out.println("\n--- LIBRO ENCONTRADO ---");

        if (!datos.resultados().isEmpty()) {
            System.out.println("Título: " + datos.resultados().get(0).titulo());
        } else {
            System.out.println("No se encontraron libros");
        }
    }
}