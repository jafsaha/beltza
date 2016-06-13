package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;
import beltza.domain.Especie;
import beltza.domain.EspecieProp;

public class JdbcEspecieDAO extends JdbcDAO implements EspecieDAO {

	private static Logger logger = Logger.getLogger(JdbcEspecieDAO.class);

	protected final String TABLE_ESPECIES = "ESPECIES";

	protected final String SEQ_ESPECIES = "SEQ_ESPECIES";
	
	protected final String ID = "ID";
	protected final String CODIGO = "CODIGO";
	protected final String AFORO = "AFORO";
	protected final String STOCK = "STOCK";
	protected final String POSICION = "POSICION";
	protected final String AFORO_INVERSO = "AFOROINVERSO";
	
	protected Map<String, String> fieldToProp;
	protected Map<String, String> propToField;
	
	public JdbcEspecieDAO(ConnectionManager connectionManager) {
		super(connectionManager);
		buildFieldMapping();
	}
	
	protected void buildFieldMapping() {
		// (campo, propiedad)
		fieldToProp = new HashMap<String, String>();
		fieldToProp.put(ID, EspecieProp.ID);
		fieldToProp.put(CODIGO, EspecieProp.CODIGO);
		fieldToProp.put(AFORO, EspecieProp.AFORO);
		fieldToProp.put(STOCK, EspecieProp.STOCK);
		fieldToProp.put(POSICION, EspecieProp.POSICION);
		fieldToProp.put(AFORO_INVERSO, EspecieProp.AFORO_INVERSO);
		
		// (propiedad, campo)
		propToField = new HashMap<String, String>();
		for(String key: fieldToProp.keySet()) {
			propToField.put(fieldToProp.get(key), key);
		}
	}

	protected final String SQL_INSERT = "insert into " + TABLE_ESPECIES +
	" (" + ID + "," + CODIGO + "," + AFORO + "," + STOCK + "," + POSICION + "," + AFORO_INVERSO + ")" + 
	" values (?,?,?,?,?,?)";

	public Long addEspecie(Map<String, Object> especieMap) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_ESPECIES);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId);
			pstmt.setObject(2, especieMap.get(fieldToProp.get(CODIGO)));
			pstmt.setObject(3, especieMap.get(fieldToProp.get(AFORO)));
			pstmt.setObject(4, especieMap.get(fieldToProp.get(STOCK)));
			pstmt.setObject(5, especieMap.get(fieldToProp.get(POSICION)));
			pstmt.setObject(6, especieMap.get(fieldToProp.get(AFORO_INVERSO)));
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

	protected final String SQL_UPDATE = "update " + TABLE_ESPECIES + " " +
	"set " + AFORO + " = ?, " + STOCK + " = ?, " + POSICION + " = ?, " + AFORO_INVERSO + " = ? " + 
	"where " + CODIGO + " = ?";

	public Boolean saveEspecie(Especie especie) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setDouble(1, especie.getAforo());
			pstmt.setDouble(2, especie.getStock());
			pstmt.setDouble(3, especie.getPosicion());
			pstmt.setBoolean(4, especie.getAforoInverso());
			pstmt.setString(5, especie.getCodigo());
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Boolean existEspecie(String codigo) {
		return (getEspecieByCodigo(codigo) != null);
	}

	protected final String SQL_SELECT = "select * from " + TABLE_ESPECIES;

	public Collection<Especie> searchEspecies(Especie especie) {
		Connection con = getConnection();
		try {
			String sql = SQL_SELECT;
			if (especie != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (especie.getCodigo() != null && especie.getCodigo().trim().length() > 0) 
					map.put(CODIGO, especie.getCodigo());
				map.put(AFORO, especie.getAforo());
				map.put(POSICION, especie.getPosicion());
				map.put(STOCK, especie.getStock());
				map.put(AFORO_INVERSO, especie.getAforoInverso());

				String where = DAOUtils.buildWhere(map, DAOUtils.AND);
				sql = sql + " " + where;
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			Collection<Especie> especies = new ArrayList<Especie>();
			while (rset.next()) {
				Especie especieResultado = buildEspecie(rset);
				especies.add(especieResultado);
			}
			return especies;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public Especie getEspecieByCodigo(String codigo) {
		if ((codigo == null) || ("".equals(codigo))) return null;
		return getEspecieBy(CODIGO, codigo);
	}

	public Especie getEspecieById(Long id) {
		if (id == null) return null;
		return getEspecieBy(ID, id);
	}

	protected final String SQL_GETBY = "select * from " + TABLE_ESPECIES;

	protected Especie getEspecieBy(String field, Object value) {
		Connection con = getConnection();
		try {
			String sql = SQL_GETBY;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(field, value);
			String where = DAOUtils.buildWhere(map, DAOUtils.AND);
			sql = sql + " " + where;

			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			Especie especie = null;
			if (rset.next()) {
				especie = buildEspecie(rset);
			}
			return especie;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected Especie buildEspecie(ResultSet rset) throws SQLException {
		Especie especie = new Especie();
		especie.setId(rset.getLong(ID));
		especie.setCodigo(rset.getString(CODIGO));
		especie.setAforo(rset.getDouble(AFORO));
		especie.setStock(rset.getDouble(STOCK));
		especie.setPosicion(rset.getDouble(POSICION));
		especie.setAforoInverso(rset.getBoolean(AFORO_INVERSO));
		return especie;
	}
	
}
