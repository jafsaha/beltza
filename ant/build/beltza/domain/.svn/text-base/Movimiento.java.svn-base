package beltza.domain;

import java.util.Date;

public class Movimiento extends BusinessObject {

	protected Operacion operacion;
	protected Especie especie;
	protected Cliente cliente;
	protected Dia dia;
	protected Double cantidad;
	protected Double valorizacion;
	protected MovimientoType tipo;
	protected Boolean liquidado;
	protected Date fechaAlta;
	protected Date fechaConcrecion;
	protected Dia diaConcrecion;

	public Operacion getOperacion() {
		return operacion;
	}
	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}
	public Especie getEspecie() {
		return especie;
	}
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getValorizacion() {
		return valorizacion;
	}
	public void setValorizacion(Double valorizacion) {
		this.valorizacion = valorizacion;
	}
	public Boolean getLiquidado() {
		return liquidado;
	}
	public void setLiquidado(Boolean liquidado) {
		this.liquidado = liquidado;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaConcrecion() {
		return fechaConcrecion;
	}
	public void setFechaConcrecion(Date fechaConcrecion) {
		this.fechaConcrecion = fechaConcrecion;
	}
	public MovimientoType getTipo() {
		return tipo;
	}
	public void setTipo(MovimientoType tipo) {
		this.tipo = tipo;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Dia getDia() {
		return dia;
	}
	public void setDia(Dia dia) {
		this.dia = dia;
	}
	
	public Dia getDiaConcrecion() {
		return diaConcrecion;
	}
	public void setDiaConcrecion(Dia diaConcrecion) {
		this.diaConcrecion = diaConcrecion;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Movimiento)) return false;
		Movimiento movimiento = (Movimiento)o;
		return this.getId().equals(movimiento.getId());
	}
	
	
}
