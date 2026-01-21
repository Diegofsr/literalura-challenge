package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    // Inyección de repositorios
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    // Constructor que recibe los repositorios desde el Main
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n---------------------------------------
                    ELIJA LA OPCIÓN A TRAVÉS DE SU NÚMERO:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ---------------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    // OPCIÓN 1: BUSCAR Y GUARDAR
    private void buscarLibroWeb() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();

        try {
            var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda = conversor.obtenerDatos(json, DatosGutendex.class);

            Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                    .findFirst();

            if (libroBuscado.isPresent()) {
                DatosLibro datos = libroBuscado.get();

                // --- VALIDACIÓN DE DUPLICADOS MEJORADA ---
                // Usamos findByTituloIgnoreCase para ser más precisos
                Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datos.titulo());

                if (libroExistente.isPresent()) {
                    System.out.println("\n-----------------------------------------");
                    System.out.println("⚠ ALERTA: Este libro ya existe en tu BD ⚠");
                    System.out.println("Título: " + datos.titulo());
                    System.out.println("-----------------------------------------\n");
                    return; // Volvemos al menú sin guardar nada
                }

                // --- LÓGICA DE GUARDADO ---
                DatosAutor datosAutor = datos.autor().get(0);
                Autor autor;

                Optional<Autor> autorDb = autorRepository.findByNombre(datosAutor.nombre());
                if (autorDb.isPresent()) {
                    autor = autorDb.get();
                } else {
                    autor = autorRepository.save(new Autor(datosAutor));
                }

                Libro libro = new Libro(datos);
                libro.setAutor(autor);
                libroRepository.save(libro);

                System.out.println("\n--- LIBRO GUARDADO EXITOSAMENTE ---");
                System.out.println(libro);
            } else {
                System.out.println("Libro no encontrado en la API de Gutendex");
            }
        } catch (Exception e) {
            // SI ALGO FALLA, ENTRARÁ AQUÍ EN LUGAR DE "PEGARSE"
            System.out.println("\n**************************************");
            System.out.println("OCURRIÓ UN ERROR AL GUARDAR:");
            System.out.println(e.getMessage());
            System.out.println("**************************************\n");
        }
    }

    // OPCIÓN 2
    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    // OPCIÓN 3
    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    // OPCIÓN 4
    private void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
        try {
            var anio = teclado.nextInt();
            teclado.nextLine(); // Limpiar buffer después de nextInt

            List<Autor> autores = autorRepository.autoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Debes ingresar un número válido.");
            teclado.nextLine(); // Limpiar error
        }
    }

    // OPCIÓN 5
    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }
}