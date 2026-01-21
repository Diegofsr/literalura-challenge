package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;
    private Double numeroDeDescargas;

    // Constructor vacío
    public Libro() {}

    // Constructor personalizado para convertir el Record a Entidad
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return  "----- LIBRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor != null ? autor.getNombre() : "Desconocido") + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + numeroDeDescargas + "\n" +
                "-----------------\n";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Double getNumeroDeDescargas() { return numeroDeDescargas; }
    public void setNumeroDeDescargas(Double numeroDeDescargas) { this.numeroDeDescargas = numeroDeDescargas; }
}