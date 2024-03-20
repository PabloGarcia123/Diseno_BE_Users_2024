package edu.uclm.es.sqeusers.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.uclm.es.sqeusers.model.User;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User,String>{

    Optional<User> findByEmailAndPwd(String email, String pwd);
    
}
