package beltza.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface DiaDAO {

	public Long addDia(Map<String, Object> diaMap);
	public Boolean saveDia(Map<String, Object> diaMap);
	public Collection<Map<String, Object>> searchDias(Map<String, Object> diaMap);
	public Collection<Map<String, Object>> getDiaByAbierto(Boolean abierto);
	public Map<String, Object> getDiaByFechaAsociada(Date fechaAsociada);
	public Map<String, Object> getFirstDiaGreaterThanFechaAsociada(Date fechaAsociada);
	public Map<String, Object> getFirstDiaLessThanFechaAsociada(Date fechaAsociada);
	
	public Map<String, Object> getDiaById(Long id);
	public Collection<Map<String, Object>> searchDiasDesde(Long diaId);
	public Map<String, Object> getUltimoDiaCerrado();
	
	public boolean deleteDiaById(Long id);

	
}
