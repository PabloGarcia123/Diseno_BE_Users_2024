package edu.uclm.es.sqeusers.http;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import edu.uclm.es.sqeusers.model.CredencialesRegistro;
import edu.uclm.es.sqeusers.model.User;
import edu.uclm.es.sqeusers.services.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registrar")
    public void registrar(@RequestBody CredencialesRegistro cr) {
        cr.comprobar();
        User user = new User();
        user.setEmail(cr.getEmail());
        user.setPwd(cr.getPwd1());

        this.userService.registrar(user);
    }

    @PutMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Map<String, String> result = new HashMap<>();
        result.put("token", this.userService.login(user));
        return result;
    }
    /* 
    @GetMapping("/validarToken")
    public void validarToken(@RequestParam String token) {
        this.userService.validarToken(token);
    */
    @GetMapping("/validarToken/{token}")
    public void validarToken(@PathVariable String token) {
        System.out.println("Token B_U: " + token);
        this.userService.validarToken(token);
    }

}
