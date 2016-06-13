package beltza.domain;

import java.util.Date;

public class Dia extends BusinessObject {

	protected Date fechaAsociada;
	protected String descripcion;
	protected Boolean abierto;

	public Date getFechaAsociada() {
		return fechaAsociada;
	}
	public void setFechaAsociada(Date fechaAsociada) {
		this.fechaAsociada = fechaAsociada;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean isAbierto() {
		return abierto;
	}
	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	
}
