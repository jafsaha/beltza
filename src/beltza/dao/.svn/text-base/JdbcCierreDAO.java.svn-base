package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;
import beltza.domain.CierreProp;

public class JdbcCierreDAO extends JdbcDAO implements CierreDAO {

	private static Logger logger = Logger.getLogger(JdbcCierreDAO.class);

	protected final String TABLE_CIERRES = "CIERRES";

	protected final String SEQ_CIERRES = "SEQ_DIAS";
	
	//Nombres de campos en la DB (case-sensitive)
	protected final String ID = "ID";
	protected final String DIA_ID = "DIAID";
	protected final String ESPECIE_ID = "ESPECIEID";
	protected final String AFORO = "AFORO";
	protected final String STOCK = "STOCK";
	protected final String POSICION = "POSICION";
	
	protected Map<String, String> fieldToProp;
	protected Map<String, String> propToField;
	
	public JdbcCierreDAO(ConnectionManager connectionManager) {
		super(connectionManager);
		buildFieldMapping();
	}
	
	protected void buildFieldMapping() {
		// (campo, propiedad)
		fieldToProp = new HashMap<String, String>();
		fieldToProp.put(ID, CierreProp.ID);
		fieldToProp.put(DIA_ID, CierreProp.DIA);
		fieldToProp.put(ESPECIE_ID, CierreProp.ESPECIE);
		fieldToProp.put(AFORO, CierreProp.AFORO);
		fieldToProp.put(STOCK, CierreProp.STOCK);
		fieldToProp.put(POSICION, CierreProp.POSICION);
		
		// (propiedad, campo)
		propToField = new HashMap<String, String>();
		for(String key: fieldToProp.keySet()) {
			propToField.put(fieldToProp.get(key), key);
		}
	}
	
	protected final String SQL_INSERT = "insert into " + TABLE_CIERRES +
	" (" + ID + "," + DIA_ID + "," + ESPECIE_ID + "," + AFORO + "," + STOCK + "," + POSICION + ") " +
	" values (?,?,?,?,?,?)";

	public Long addCierre(Map<String, Object> cierreMap) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_CIERRES);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId); 
			pstmt.setObject(2, cierreMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(3, cierreMap.get(fieldToProp.get(ESPECIE_ID)));
			pstmt.setObject(4, cierreMap.get(fieldToProp.get(AFORO)));
			pstmt.setObject(5, cierreMap.get(fieldToProp.get(STOCK)));
			pstmt.setObject(6, cierreMap.get(fieldToProp.get(POSICION)));
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

	protected final String SQL_UPDATE = "update " + TABLE_CIERRES + " " +
	"set " + 
	DIA_ID + " = (?)," + ESPECIE_ID + " = (?)," + AFORO + " = (?)," + STOCK + " = (?)," + POSICION + " = (?)" + " " +
	"where " + ID + " = (?)";

	public Boolean saveCierre(Map<String, Object> cierreMap) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setObject(1, cierreMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(2, cierreMap.get(fieldToProp.get(ESPECIE_ID)));
			pstmt.setObject(3, cierreMap.get(fieldToProp.get(AFORO)));
			pstmt.setObject(4, cierreMap.get(fieldToProp.get(STOCK)));
			pstmt.setObject(5, cierreMap.get(fieldToProp.get(POSICION)));
			pstmt.setObject(6, cierreMap.get(fieldToProp.get(ID)));
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected final String SQL_DELETE = "delete from " + TABLE_CIERRES + " where " + ID + " = ?";
	
	public Boolean deleteCierre(Long cierreId) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setObject(1, cierreId); 
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Map<String, Object>> searchCierres(Map<String, Object> cierreMap) {
		return simpleSearchTable(TABLE_CIERRES, cierreMap, propToField, fieldToProp);
	}

	public Map<String, Object> getCierreById(Long id) {
		if (id == null) return null;
		Map<String, Object> cierreMap = new HashMap<String, Object>();
		cierreMap.put(CierreProp.ID, id);
		Collection<Map<String, Object>> cierres = this.searchCierres(cierreMap);
		if (cierres.size() > 0) {
			return cierres.iterator().next();
		} else {
			return null;
		}
	}

	protected final String DELETE_CIERRE = "delete  from " + TABLE_CIERRES + " where " + 
	DIA_ID + " = (?)";
	public boolean deleteCierreByDiaId(Long diaId) {
		Connection con = getConnection();
		try {
			String sql = DELETE_CIERRE;
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, diaId);
			return pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
