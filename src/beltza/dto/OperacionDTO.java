package beltza.dto;

import java.util.Date;

import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;

public class OperacionDTO {

	protected OperacionType tipo;
	protected OperacionSubType subtipo;

	protected String clienteCod;
	protected String especieEntraCod;
	protected String especieSaleCod;
	protected Double valorizacion;
	protected Double cantidad;
	protected Double cantidadSale;
	protected String notas;
	protected Long diaId;

	protected Boolean liquidado;
	protected Date fechaLiquidacion;

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

	public String getClienteCod() {
		return clienteCod;
	}

	public void setClienteCod(String clienteCod) {
		this.clienteCod = clienteCod;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public OperacionSubType getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(OperacionSubType subTipo) {
		this.subtipo = subTipo;
	}

	public Boolean getLiquidado() {
		return liquidado;
	}

	public void setLiquidado(Boolean liquidado) {
		this.liquidado = liquidado;
	}

	public String getEspecieEntraCod() {
		return especieEntraCod;
	}

	public void setEspecieEntraCod(String especieEntraCod) {
		this.especieEntraCod = especieEntraCod;
	}

	public String getEspecieSaleCod() {
		return especieSaleCod;
	}

	public void setEspecieSaleCod(String especieSaleCod) {
		this.especieSaleCod = especieSaleCod;
	}

	public Double getCantidadSale() {
		return cantidadSale;
	}

	public void setCantidadSale(Double cantidadSale) {
		this.cantidadSale = cantidadSale;
	}

	public Long getDiaId() {
		return diaId;
	}

	public void setDiaId(Long diaId) {
		this.diaId = diaId;
	}

}
