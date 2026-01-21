package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obtenerDatos(String url) {
        // 1. Creamos el cliente
        HttpClient client = HttpClient.newHttpClient();

        // 2. Construimos la petición
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // 3. Enviamos la petición y recibimos la respuesta
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al conectar con la API: " + e.getMessage());
        }

        // 4. Devolvemos solo el cuerpo de la respuesta (el JSON)
        return response.body();
    }
}