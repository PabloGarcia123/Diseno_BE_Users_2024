package edu.uclm.es.sqeusers.http;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public void login(@RequestBody User user) {
        String email = info.get("email");
        String pwd = info.get("pwd");
        User user = this.userService.login(email, pwd);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
    }


}
