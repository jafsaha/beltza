package beltza.domain;

import java.util.Collection;
import java.util.Date;

public class Operacion extends BusinessObject {

	protected Long numero;

	protected Date fechaAlta;
	protected Date fechaLiquidacion;

	protected OperacionType tipo;
	protected OperacionSubType subtipo;

	protected Cliente cliente;
	protected Especie especieEntra;
	protected Especie especieSale;

	protected Dia dia;

	protected Double valorizacion;
	protected Double cantidad;
	protected Double cantidadSale;

	protected String notas;

	protected Boolean liquidado;

	protected Collection<Movimiento> movimientos;

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public OperacionType getTipo() {
		return tipo;
	}

	public void setTipo(OperacionType tipo) {
		this.tipo = tipo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Double getValorizacion() {
		return valorizacion;
	}

	public void setValorizacion(Double valorizacion) {
		this.valorizacion = valorizacion;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Especie getEspecieEntra() {
		return especieEntra;
	}

	public void setEspecieEntra(Especie especieEntra) {
		this.especieEntra = especieEntra;
	}

	public Especie getEspecieSale() {
		return especieSale;
	}

	public void setEspecieSale(Especie especieSale) {
		this.especieSale = especieSale;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Operacion))
			return false;
		Operacion operacion = (Operacion) o;
		return this.getId().equals(operacion.getId());
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public OperacionSubType getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(OperacionSubType subtipo) {
		this.subtipo = subtipo;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Boolean isLiquidado() {
		Boolean _liquidado = true;
		if (movimientos != null) {
			for (Movimiento movimiento : movimientos) {
				if (!movimiento.getLiquidado()) {
					_liquidado = false;
					break;
				}
			}
		}
		return _liquidado;
	}

	public Collection<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Collection<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Double getCantidadSale() {
		return cantidadSale;
	}

	public void setCantidadSale(Double cantidadSale) {
		this.cantidadSale = cantidadSale;
	}

	public Boolean getLiquidado() {
		return liquidado;
	}

	public void setLiquidado(Boolean liquidado) {
		this.liquidado = liquidado;
	}

}
