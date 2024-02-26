package edu.uclm.es.sqeusers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.uclm.es.sqeusers.model.User;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    
    public void registrar(User user) {
        this.users.add(user);
    }

    public String login(String email, String pwd) {
        if (user.g)
        String token = UUID.randomUUID().toString();
        
        return null;
    }
}
