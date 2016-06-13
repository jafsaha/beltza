package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;
import beltza.domain.DiaProp;

public class JdbcDiaDAO extends JdbcDAO implements DiaDAO {

	private static Logger logger = Logger.getLogger(JdbcDiaDAO.class);

	protected final String TABLE_DIAS = "DIAS";

	protected final String SEQ_DIAS = "SEQ_DIAS";
	
	//Nombres de campos en la DB (case-sensitive)
	protected final String ID = "ID";
	protected final String FECHA_ASOCIADA = "FECHAASOCIADA";
	protected final String ABIERTO = "ABIERTO";
	protected final String DESCRIPCION = "DESCRIPCION";
	
	protected Map<String, String> fieldToProp;
	protected Map<String, String> propToField;
	
	private Map<Long, Map<String, Object>> cache = new HashMap<Long, Map<String, Object>>();
	
	public JdbcDiaDAO(ConnectionManager connectionManager) {
		super(connectionManager);
		buildFieldMapping();
	}
	
	protected void buildFieldMapping() {
		// (campo, propiedad)
		fieldToProp = new HashMap<String, String>();
		fieldToProp.put(ID, DiaProp.ID);
		fieldToProp.put(FECHA_ASOCIADA, DiaProp.FECHA_ASOCIADA);
		fieldToProp.put(ABIERTO, DiaProp.ABIERTO);
		fieldToProp.put(DESCRIPCION, DiaProp.DESCRIPCION);
		
		// (propiedad, campo)
		propToField = new HashMap<String, String>();
		for(String key: fieldToProp.keySet()) {
			propToField.put(fieldToProp.get(key), key);
		}
	}
	
	protected final String SQL_INSERT = "insert into " + TABLE_DIAS +
	" (" + ID + "," + FECHA_ASOCIADA + "," + ABIERTO + "," + DESCRIPCION + ") " +
	" values (?,?,?,?)";

	public Long addDia(Map<String, Object> diaMap) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_DIAS);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId); 
			pstmt.setDate(2, new java.sql.Date(((java.util.Date)diaMap.get(fieldToProp.get(FECHA_ASOCIADA))).getTime()));  
			pstmt.setObject(3, diaMap.get(fieldToProp.get(ABIERTO)));
			pstmt.setObject(4, diaMap.get(fieldToProp.get(DESCRIPCION)));
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				return nextId;
			} else {
				return -1L;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected final String SQL_UPDATE = "update " + TABLE_DIAS + " " +
	"set " + 
	FECHA_ASOCIADA + " = (?)," + ABIERTO + " = (?)," + DESCRIPCION + " = (?)" + " " +
	"where " + ID + " = (?)";
	
	public Boolean saveDia(Map<String, Object> diaMap) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setObject(1, diaMap.get(fieldToProp.get(FECHA_ASOCIADA)));
			pstmt.setObject(2, diaMap.get(fieldToProp.get(ABIERTO)));
			pstmt.setObject(3, diaMap.get(fieldToProp.get(DESCRIPCION)));
			pstmt.setObject(4, diaMap.get(fieldToProp.get(ID)));
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Map<String, Object>> searchDias(Map<String, Object> diaMap) {
		return simpleSearchTable(TABLE_DIAS, diaMap, propToField, fieldToProp);
	}

	public Collection<Map<String, Object>> getDiaByAbierto(Boolean abierto) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.ABIERTO, abierto);
		return this.searchDias(diaMap);
	}

	public Map<String, Object> getDiaByFechaAsociada(Date fechaAsociada) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.FECHA_ASOCIADA, fechaAsociada);
		Collection<Map<String, Object>> dias = this.searchDias(diaMap);
		if (dias.size() > 0) {
			return dias.iterator().next();
		} else {
			return null;
		}
	}

	public Map<String, Object> getFirstDiaGreaterThanFechaAsociada(Date fechaAsociada) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		Map<String, String> operatorMap = new HashMap<String, String>();
		diaMap.put(DiaProp.FECHA_ASOCIADA, fechaAsociada);
		operatorMap.put(DiaProp.FECHA_ASOCIADA, ">=");
		Collection<Map<String, Object>> dias =  searchTable(TABLE_DIAS, diaMap, operatorMap, FECHA_ASOCIADA, Order.ASC, propToField, fieldToProp);
		if (dias.size() > 0) {
			return dias.iterator().next();
		} else {
			return null;
		}
	}

	public Map<String, Object> getFirstDiaLessThanFechaAsociada(Date fechaAsociada) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		Map<String, String> operatorMap = new HashMap<String, String>();
		diaMap.put(DiaProp.FECHA_ASOCIADA, fechaAsociada);
		operatorMap.put(DiaProp.FECHA_ASOCIADA, "<=");
		Collection<Map<String, Object>> dias =  searchTable(TABLE_DIAS, diaMap, operatorMap, FECHA_ASOCIADA, Order.DESC, propToField, fieldToProp);
		if (dias.size() > 0) {
			return dias.iterator().next();
		} else {
			return null;
		}
	}
	
	public Map<String, Object> getDiaById(Long id) {
		if (id == null) return null;
		
		Map<String, Object> dia = cache.get(id); 
		if( dia != null) return dia;
		
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.ID, id);
		Collection<Map<String, Object>> dias = this.searchDias(diaMap);
		if (dias.size() > 0) {
			dia = dias.iterator().next();
			cache.put(id, dia);
			return dia;
		} else {
			return null;
		}
	}

	public Collection<Map<String, Object>> searchDiasDesde(Long diaId) {
		Map<String, Object> diaMap = new HashMap<String, Object>();
		diaMap.put(DiaProp.ID, diaId);
		diaMap.put(DiaProp.ABIERTO, false);
		Map<String, String> operatorMap = new HashMap<String, String>();
		operatorMap.put(DiaProp.ID, ">=");
		return searchTable(TABLE_DIAS, diaMap, operatorMap, propToField, fieldToProp);
	}
	
	protected final String SELECT_LAST = "select * from " + TABLE_DIAS + " where " + 
	ID + " = (select max(" + ID + ") from " + TABLE_DIAS + ")" + " AND " +
	ABIERTO + " = (?)";
	public Map<String, Object> getUltimoDiaCerrado() {
		Connection con = getConnection();
		try {
			String sql = SELECT_LAST;
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			ResultSet rset = pstmt.executeQuery();
			Collection<Map<String, Object>> result = DAOUtils.getMaps(rset, fieldToProp);
			if (result.size() > 0) {
				return result.iterator().next();
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	protected final String DELETE_DIA = "delete  from " + TABLE_DIAS + " where " + 
	ID + " = (?)";
	public boolean deleteDiaById(Long id) {
		Connection con = getConnection();
		try {
			String sql = DELETE_DIA;
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, id);
			boolean res = pstmt.execute();
			cache.remove(id);
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
}
