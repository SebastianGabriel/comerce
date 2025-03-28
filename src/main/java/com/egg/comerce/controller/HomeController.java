package com.egg.comerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.egg.comerce.exception.UnfilledFormException;
import com.egg.comerce.service.UserService;
import com.egg.comerce.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/") 
    public String index() {
        return "index.html"; 
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrousuario.html";
    }

    @PostMapping("/alta")
    public String alta(@RequestParam String name,@RequestParam String email,@RequestParam String password,@RequestParam String password2,@RequestParam String surname,ModelMap model) {
        try{
            userService.createUser(name, surname, email, password,password2);
            model.put("exito", "El usuario fue creado correctamente.");
            return "index.html";
        }catch (UnfilledFormException ex){
            model.put("error", ex.getMessage());
            model.put("nombre", name);
            model.put("email", email);
            return "registrousuario.html";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login.html";
    }   
}
