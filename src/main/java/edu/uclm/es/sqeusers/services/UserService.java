package edu.uclm.es.sqeusers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.es.sqeusers.model.Token;
import edu.uclm.es.sqeusers.model.User;

@Service
public class UserService {

    private Map<String,User> users = new HashMap<>();
    private Map<String,Token> tokens = new HashMap<>();
    
    public void registrar(User user) {
        this.users.put(user.getEmail(),user);
    }

    public String login(User user) {
    	User existente= this.users.get(user.getEmail());
        if (existente==null)
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
        if(user.getPwd().equals(existente.getPwd()))
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
        if(existente.getToken()!=null) {
        	this.tokens.remove(existente.getToken().getId());
        	existente.setToken(null); 		
        }
        
        String idToken = UUID.randomUUID().toString();
        Token token= new Token(idToken,existente);
        this.tokens.put(idToken,token);
        existente.setToken(token);
        return token.getId();
    }
    
    public void validarToken(String idToken) {
    	Token token= this.tokens.get(idToken);
    	if(token==null)
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permiso para acceder a este recurso"); 
    	if(token.caducado()) {
    		this.tokens.remove(idToken);
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token ha caducado"); 
    	}
    	token.incrementarTiempo();
    }
}
