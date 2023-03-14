package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearLibro(MultipartFile archivo, Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {

            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {

            editorial = respuestaEditorial.get();
        }

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        Imagen imagen = imagenServicio.guardar(archivo);

        libro.setImagen(imagen);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {

        List<Libro> libro = new ArrayList();

        libro = libroRepositorio.findAll();

        return libro;
    }

    @Transactional
    public void modificarLibro(MultipartFile archivo, Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)
            throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {

            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {

            editorial = respuestaEditorial.get();
        }

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            libro.setTitulo(titulo);

            libro.setAutor(autor);

            libro.setEditorial(editorial);

            libro.setEjemplares(ejemplares);

            String idImagen = null;

            if (libro.getImagen() != null) {
                idImagen = libro.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            libro.setImagen(imagen);

            libroRepositorio.save(libro);
        }
    }

    public Libro getOne(Long id) {
        return libroRepositorio.getOne(id);
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        if (isbn == null) {
            throw new MiException("El isbn no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares == null) {
            throw new MiException("Ejemplares no puede ser nulo");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("El idAutor no puede ser nulo o estar vacio");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("El idEditorial no puede ser nulo o estar vacio");
        }

    }

}
