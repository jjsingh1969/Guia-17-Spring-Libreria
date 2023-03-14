
package com.egg.biblioteca.controladores;


import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("usuario", usuarioServicio.getOne(id));

        return "usuario_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo, String idUsuario, String nombre, String email, 
            String password, String password2, ModelMap modelo) {

        try {
            

            usuarioServicio.actualizar(archivo, idUsuario, nombre, email, password, password2);

            return "redirect:../lista";

        } catch (MiException ex) {

             modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";
        }
    }
    
}
