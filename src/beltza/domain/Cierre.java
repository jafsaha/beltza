package beltza.domain;

public class Cierre extends BusinessObject {

	protected Dia dia;
	protected Especie especie;
	protected Double aforo;
	protected Double stock;
	protected Double posicion;

	public Dia getDia() {
		return dia;
	}
	public void setDia(Dia dia) {
		this.dia = dia;
	}
	public Especie getEspecie() {
		return especie;
	}
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
	public Double getAforo() {
		return aforo;
	}
	public void setAforo(Double aforo) {
		this.aforo = aforo;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public Double getPosicion() {
		return posicion;
	}
	public void setPosicion(Double posicion) {
		this.posicion = posicion;
	}
	
}
