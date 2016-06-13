package beltza.dto;

import java.util.Date;

public class SearchMovimientosDTO {

	protected String clienteCod;
	protected String especieCod;

	protected SearchDateType tipoFechaAlta;
	protected Date fechaAltaDesde;
	protected Date fechaAltaHasta;

	protected SearchDateType tipoFechaLiq;
	protected Date fechaLiqDesde;
	protected Date fechaLiqHasta;

	protected Boolean liquidado;

	public String getClienteCod() {
		return clienteCod;
	}

	public void setClienteCod(String clienteCod) {
		this.clienteCod = clienteCod;
	}

	public SearchDateType getTipoFechaAlta() {
		return tipoFechaAlta;
	}

	public void setTipoFechaAlta(SearchDateType tipoFechaAlta) {
		this.tipoFechaAlta = tipoFechaAlta;
	}

	public Date getFechaAltaDesde() {
		return fechaAltaDesde;
	}

	public void setFechaAltaDesde(Date fechaAltaDesde) {
		this.fechaAltaDesde = fechaAltaDesde;
	}

	public Date getFechaAltaHasta() {
		return fechaAltaHasta;
	}

	public void setFechaAltaHasta(Date fechaAltaHasta) {
		this.fechaAltaHasta = fechaAltaHasta;
	}

	public SearchDateType getTipoFechaLiq() {
		return tipoFechaLiq;
	}

	public void setTipoFechaLiq(SearchDateType tipoFechaLiq) {
		this.tipoFechaLiq = tipoFechaLiq;
	}

	public Date getFechaLiqDesde() {
		return fechaLiqDesde;
	}

	public void setFechaLiqDesde(Date fechaLiqDesde) {
		this.fechaLiqDesde = fechaLiqDesde;
	}

	public Date getFechaLiqHasta() {
		return fechaLiqHasta;
	}

	public void setFechaLiqHasta(Date fechaLiqHasta) {
		this.fechaLiqHasta = fechaLiqHasta;
	}

	public String getEspecieCod() {
		return especieCod;
	}

	public void setEspecieCod(String especieCod) {
		this.especieCod = especieCod;
	}

	public Boolean getLiquidado() {
		return liquidado;
	}

	public void setLiquidado(Boolean liquidado) {
		this.liquidado = liquidado;
	}

}
