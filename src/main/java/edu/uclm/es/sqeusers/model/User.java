package edu.uclm.es.sqeusers.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "users")
@Table(indexes = {
    @Index(columnList = "email", unique = true)
})
public class User {
    @Id @Column(length = 36)
    private String id;
    @Column(length = 100, nullable = false)
    private String email;
    @Column(length = 100, nullable = false)
    private String pwd;
    @Transient
    private Token token;

    public User() {
        this.id= UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }
    public Token getToken() {
        return token;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public void setToken(Token token) {
        this.token = token;
    }

}
