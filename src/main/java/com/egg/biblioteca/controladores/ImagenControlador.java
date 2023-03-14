
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.servicios.LibroServicio;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    LibroServicio libroServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]>imagenUsuario(@PathVariable String id){
        
       Usuario usuario = usuarioServicio.getOne(id);
       
       byte[] imagen = usuario.getImagen().getContenido();
       
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
        
    }
    
    @GetMapping("/portada/{isbn}")
    public ResponseEntity<byte[]>imagenUsuario(@PathVariable Long isbn){
        
       Libro libro = libroServicio.getOne(isbn);
       
       byte[] imagen = libro.getImagen().getContenido();
       
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
        
    }
    
}
