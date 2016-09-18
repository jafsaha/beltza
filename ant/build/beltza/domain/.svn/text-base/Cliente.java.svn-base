package beltza.domain;

public class Cliente extends BusinessObject {

	protected String codigo;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Cliente)) return false;
		Cliente cliente = (Cliente)o;
		return this.getCodigo().equals(cliente.getCodigo());
	}
	
}
