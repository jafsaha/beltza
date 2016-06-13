package beltza.business;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import beltza.business.event.BeltzaObservable;
import beltza.commons.Operator;
import beltza.domain.Cierre;
import beltza.domain.Cliente;
import beltza.domain.Dia;
import beltza.domain.Especie;
import beltza.domain.Movimiento;
import beltza.domain.Operacion;
import beltza.dto.EspecieDTO;
import beltza.dto.OperacionDTO;
import beltza.dto.SearchMovimientosDTO;
import beltza.dto.SearchOperacionesDTO;

public interface BeltzaBusinessModel extends BeltzaObservable {

	public void setPassword(String password);
	public Boolean verifyPassword(String password);

	public Especie getEspecieDeReferencia();
	public Collection<Especie> getEspeciesMonitoreo();
	public Integer getVersion();
	
	public Long addEspecie(EspecieDTO especieDTO);
	public void saveEspecie(Especie especie);
	public Especie getEspecieByCodigo(String codigo);
	public Especie getEspecieById(Long id);
	public Collection<Especie> searchEspecies(Especie especie);
	public Collection<Especie> getAllEspecies();

	public Long addCliente(String codigo);
	public Cliente getClienteByCodigo(String codigo);
	public Cliente getClienteById(Long id);
	public Collection<Cliente> searchClientes(Cliente cliente);
	public Collection<Cliente> getAllClientes();
	
	public Long addOperacion(OperacionDTO operacionDTO);
	public void saveOperacion(Operacion operacion);
	public void deleteOperacion(Operacion operacion, Boolean actualizaCierres);
	public Operacion getOperacionByNumero(Long numero);
	public Operacion getOperacionById(Long id);
	public Collection<Operacion> searchOperaciones(Operacion operacion);
	public Collection<Operacion> searchOperaciones(Operacion operacion, Date fechaAlta);
	public Collection<Map<String, Object>> getOperacionSubTypeListFromDiaAbierto();
	public Collection<Map<String, Object>> getOperacionSubTypeListByExample(SearchOperacionesDTO searchDTO);
	public void liquidarOperacion(Operacion operacion);
	public void desliquidarOperacion(Operacion operacion);
	
	public Collection<Movimiento> searchMovimientos(Movimiento movimiento);
	public Collection<Movimiento> searchMovimientosPositivos(Movimiento movimiento, Operator operator);
	public Collection<Movimiento> searchMovimientosNegativos(Movimiento movimiento, Operator operator);
	public Collection<Movimiento> searchMovimientosPositivos(Movimiento movimiento, Date fechaAlta, Operator operator);
	public Collection<Movimiento> searchMovimientosNegativos(Movimiento movimiento, Date fechaAlta, Operator operator);
	public Collection<Movimiento> searchMovimientos(SearchMovimientosDTO searchDTO);
	
	public Collection<Map<String, Object>> searchMovimientosConsolidados(Especie especie, Cliente cliente, int signo, boolean filtrarCeros);
	public Collection<Movimiento> searchMovimientosNoLiqByCliente(String clienteCod, String especieCod);
	public void liquidarMovimiento(Movimiento movimiento);
	public void desliquidarMovimiento(Long movimientoId);
	
	public void abrirDia();
	public void abrirDia(Date fechaAsociada);
	public void reabrirUltimoDia();
	public void cerrarDia();
	public Dia getDiaAbierto();
	public Dia getDiaByFechaAsociada(Date fechaAsociada);
	
	public void saveCierre(Cierre cierre);
	public Collection<Cierre> getCierresByFechaEspecie(Date fecha, Especie especie);
	
	public void release();
	public Collection<Map<String, Object>> searchMovimientosConsolidadosByFechaLiq(Especie especie, Cliente cliente, int signo, Date fechaLiquidacion, boolean filtrarCeros);

	public void compact(Date fechaLimite);
	public boolean borrarUltimoDiaCerrado();
	public Collection<Operacion> searchOperaciones(SearchOperacionesDTO search);
	public abstract void liquidarDesliquidarMovimientos(Collection<Movimiento> movimientos);
	
}
