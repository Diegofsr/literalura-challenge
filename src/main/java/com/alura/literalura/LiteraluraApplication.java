package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    // Inyectamos los repositorios aquí para pasárselos a la clase Principal
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // --- AQUÍ ES DONDE NACE EL MENÚ ---
        // Creamos la instancia de Principal pasando los repositorios
        Principal principal = new Principal(libroRepository, autorRepository);

        // Ejecutamos el menú
        principal.muestraElMenu();
    }
}