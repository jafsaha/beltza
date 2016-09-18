package beltza.domain;

public class Especie extends BusinessObject {

	protected String codigo;
	protected Double aforo;
	protected Double stock;
	protected Double posicion;
	protected Boolean aforoInverso;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public Boolean getAforoInverso() {
		return aforoInverso;
	}

	public void setAforoInverso(Boolean aforoInverso) {
		this.aforoInverso = aforoInverso;
	}

	public Double getAforoInvertido() {
		if (getAforo().equals(0.0)) {
			return 0.0;
		} else {
			return (1 / getAforo());
		}
	}

	public String toString() {
		return "Especie[" + "Id:" + getId() + "Codigo:" + getCodigo() + "]";
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Especie)) return false;
		Especie especie = (Especie)o;
		return this.getCodigo().equals(especie.getCodigo());
	}

	
}
