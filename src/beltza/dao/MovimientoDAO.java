package beltza.dao;

import java.util.Collection;
import java.util.Map;

import beltza.commons.Operator;
import beltza.dto.SearchMovimientosDTO;




public interface MovimientoDAO {

	public Long addMovimiento(Map<String, Object> movimientoMap);
	public Boolean saveMovimiento(Map<String, Object> movimientoMap);
	public Boolean deleteMovimiento(Long movimientoId);
	public Collection<Map<String, Object>> searchMovimientos(Map<String, Object> movimientoMap);
	public Collection<Map<String, Object>> searchPositiveMovimientos(Map<String, Object> movimientoMap, Operator operator);
	public Collection<Map<String, Object>> searchNegativeMovimientos(Map<String, Object> movimientoMap, Operator operator);
	public Collection<Map<String, Object>> searchMovimientosCtaCteByCliente(Long id, Long especieId);
	
	public Map<String, Object> getMovimientoById(Long id);
	public Collection<Map<String, Object>>  getMovimientosByOperacionId(Long id);
	
	public Collection<Map<String, Object>> search(SearchMovimientosDTO searchDTO);
}
