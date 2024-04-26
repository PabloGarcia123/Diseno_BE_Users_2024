package edu.uclm.es.sqeusers.model;

public class User {
    private String email;
    private String pwd;
    private Token token;

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }
    public Token getToken() {
    	return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public void setToken(Token token) {
    	this.token=token;
    }


}
