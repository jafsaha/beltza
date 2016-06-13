package beltza.dao;

import java.util.Collection;
import java.util.Map;

import beltza.dto.SearchOperacionesDTO;


public interface OperacionDAO {

	public Long addOperacion(Map<String, Object> operacionMap);
	public Boolean saveOperacion(Map<String, Object> operacionMap);
	public Boolean deleteOperacion(Long operacionId);
	public Collection<Map<String, Object>> searchOperaciones(Map<String, Object> operacionMap);
	public Map<String, Object> getOperacionByNumero(Long numero);
	public Map<String, Object> getOperacionById(Long id);
	public Collection<Map<String, Object>> getOperacionByDia(Long diaId);
	public Collection<Map<String, Object>> getOperacionSubTypeListFromDiaAbierto();
	public Collection<Map<String, Object>> getOperacionSubTypeListByExample(SearchOperacionesDTO searchDTO);
	public Collection<Map<String, Object>> searchByIgual(SearchOperacionesDTO searchDTO);
	public Collection<Map<String, Object>> searchByRango(SearchOperacionesDTO searchDTO);
	public Collection<Map<String, Object>> search(SearchOperacionesDTO searchDTO);
}
