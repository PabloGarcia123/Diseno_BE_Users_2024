package edu.uclm.es.sqeusers.services;


import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.es.sqeusers.dao.UserDAO;
import edu.uclm.es.sqeusers.model.Token;
import edu.uclm.es.sqeusers.model.User;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    private Map<String, User> users = new HashMap<>();
    private Map<String, Token> tokens = new HashMap<>();

    public void registrar(User user) {
        //this.users.put(user.getEmail(), user);
        this.userDAO.save(user);
    }

    public String login(User user) {
        Optional<User> optUser = this.userDAO.findByEmailAndPwd(user.getEmail(), user.getPwd());
        if(optUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
        }
        //user.setId(optUser.get().getId());
        user = optUser.get();
        if(this.users.get(user.getId()) != null) {
            this.users.remove(user.getId());
            user.setToken(null);
        }
        this.users.put(user.getId(), user);

        String idToken = UUID.randomUUID().toString();
        Token token = new Token(idToken, user);
        this.tokens.put(idToken, token);
        user.setToken(token);
        return token.getId();
    }

    public void validarToken(String idToken) {
        Token token = this.tokens.get(idToken);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token no válido");
        }
        if (token.caducado()) {
            this.tokens.remove(idToken);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token caducado");
        }
        token.incrementarTiempo();
    }

}
