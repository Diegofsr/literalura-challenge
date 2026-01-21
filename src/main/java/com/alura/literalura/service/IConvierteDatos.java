package com.alura.literalura.service;

public interface IConvierteDatos {
    // Este método toma un JSON (String) y lo convierte a una Clase T cualquiera
    <T> T obtenerDatos(String json, Class<T> clase);
}