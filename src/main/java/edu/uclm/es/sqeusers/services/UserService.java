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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    private Map<String, User> users = new HashMap<>();
    private Map<String, Token> tokens = new HashMap<>();

    public void registrar(User user) {
        //this.users.put(user.getEmail(), user);
    	try {
            String contraseña_cod = codificar_pwd(user.getPwd());
            user.setPwd(contraseña_cod);
            this.userDAO.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No se pudo registrar el usuario");
        }
    }

    public String login(User user) {
        Optional<User> optUser = this.userDAO.findByEmailAndPwd(user.getEmail(), codificar_pwd(user.getPwd()));
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
    private static String codificar_pwd(String password) {
    	try {
            // Crear una instancia de MessageDigest con SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // Actualizar el MessageDigest con la contraseña en bytes
            md.update(password.getBytes());

            // Obtener el hash en bytes
            byte[] bytes = md.digest();

            // Convertir los bytes en una representación hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Devolver la contraseña codificada
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción si el algoritmo SHA-512 no está disponible
            throw new RuntimeException(e);
        }
    }

}
