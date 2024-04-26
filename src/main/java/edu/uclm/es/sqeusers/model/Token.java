package edu.uclm.es.sqeusers.model;

public class Token {
	private String id;
	private User user;
	private long horaFin;
	private final static int DURACION=15000;
	
	public Token(String id, User user) {
		this.id = id;
		this.user = user;
		this.horaFin=System.currentTimeMillis()+DURACION;
	}
	
	public String getId() {
		return id;
	}
	public long getHoraFin() {
		return horaFin;
	}

	public boolean caducado() {
		return System.currentTimeMillis()>this.horaFin;
	}

	public void incrementarTiempo() {
		this.horaFin=System.currentTimeMillis()+DURACION;	
	}
}
