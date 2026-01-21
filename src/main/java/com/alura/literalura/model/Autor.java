package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    // Constructor vacío
    public Autor() {}

    // Constructor que usa nuestros Records para crear el Autor
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();

        try {
            this.fechaDeNacimiento = Integer.valueOf(datosAutor.fechaDeNacimiento());
        } catch (NumberFormatException e) {
            this.fechaDeNacimiento = null;
        }
        try {
            this.fechaDeFallecimiento = Integer.valueOf(datosAutor.fechaDeFallecimiento());
        } catch (NumberFormatException e) {
            this.fechaDeFallecimiento = null;
        }
    }

    @Override
    public String toString() {
        return "Autor: " + nombre + " (" + fechaDeNacimiento + "-" + fechaDeFallecimiento + ")";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(Integer fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }
    public Integer getFechaDeFallecimiento() { return fechaDeFallecimiento; }
    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) { this.fechaDeFallecimiento = fechaDeFallecimiento; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        for (Libro libro : libros) {
            libro.setAutor(this);
        }
    }
}