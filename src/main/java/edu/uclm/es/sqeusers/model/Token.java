package edu.uclm.es.sqeusers.model;

public class Token {
    private final static long DURACION = 1000000000;

    private String id;
    private User user;
    private long horaFin;

    public Token(String id, User user) {
        this.id = id;
        this.user = user;
        this.horaFin = System.currentTimeMillis() + DURACION;
    }
    public String getId() {
        return id;
    }
    public boolean caducado() {
        return System.currentTimeMillis() > this.horaFin;
    }
    public void incrementarTiempo() {
        this.horaFin = System.currentTimeMillis() + DURACION;
    }
    

}
