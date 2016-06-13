package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beltza.commons.Operator;
import beltza.db.ConnectionManager;
import beltza.domain.MovimientoProp;
import beltza.domain.MovimientoType;
import beltza.dto.SearchDateType;
import beltza.dto.SearchMovimientosDTO;
import beltza.dto.SearchOperacionesDTO;
import beltza.util.OperatorConverter;

public class JdbcMovimientoDAO extends JdbcDAO implements MovimientoDAO {

	protected final String TABLE_MOVIMIENTOS = "MOVIMIENTOS";

	protected final String SEQ_MOVIMIENTOS = "SEQ_MOVIMIENTOS";

	protected final String ID = "ID";
	protected final String OPERACION_ID = "OPERACIONID";
	protected final String ESPECIE_ID = "ESPECIEID";
	protected final String CLIENTE_ID = "CLIENTEID";
	protected final String DIA_ID = "DIAID";
	protected final String CANTIDAD = "CANTIDAD";
	protected final String VALORIZACION = "VALORIZACION";
	protected final String TIPO = "TIPO";
	protected final String LIQUIDADO = "LIQUIDADO";
	protected final String FECHA_ALTA = "FECHAALTA";
	protected final String FECHA_CONCRECION = "FECHACONCRECION";
	protected final String DIA_CONCRECION = "DIACONCRECIONID";
	
	protected Map<String, String> fieldToProp;
	protected Map<String, String> propToField;
	
	public JdbcMovimientoDAO(ConnectionManager connectionManager) {
		super(connectionManager);
		this.buildFieldMapping();
	}

	protected void buildFieldMapping() {
		// (campo, propiedad)
		fieldToProp = new HashMap<String, String>();
		fieldToProp.put(ID, MovimientoProp.ID);
		fieldToProp.put(OPERACION_ID, MovimientoProp.OPERACION);
		fieldToProp.put(ESPECIE_ID, MovimientoProp.ESPECIE);
		fieldToProp.put(CLIENTE_ID, MovimientoProp.CLIENTE);
		fieldToProp.put(DIA_ID, MovimientoProp.DIA);
		fieldToProp.put(CANTIDAD, MovimientoProp.CANTIDAD);
		fieldToProp.put(VALORIZACION, MovimientoProp.VALORIZACION);
		fieldToProp.put(TIPO, MovimientoProp.TIPO);
		fieldToProp.put(LIQUIDADO, MovimientoProp.LIQUIDADO);
		fieldToProp.put(FECHA_ALTA, MovimientoProp.FECHA_ALTA);
		fieldToProp.put(FECHA_CONCRECION, MovimientoProp.FECHA_CONCRECION);
		fieldToProp.put(DIA_CONCRECION, MovimientoProp.DIA_CONCRECION);
		
		// (propiedad, campo)
		propToField = new HashMap<String, String>();
		for(String key: fieldToProp.keySet()) {
			propToField.put(fieldToProp.get(key), key);
		}
	}
	
	protected final String SQL_INSERT = "insert into " + TABLE_MOVIMIENTOS + 
	" (" + ID + "," + OPERACION_ID + "," + ESPECIE_ID + "," + CLIENTE_ID + "," + DIA_ID + "," + CANTIDAD + "," + VALORIZACION + "," + 
	TIPO + "," + LIQUIDADO + "," + FECHA_ALTA + "," + FECHA_CONCRECION + "," + DIA_CONCRECION + ") " +
	" values (?,?,?,?,?,?,?,?,?,?,?,?)";

	public Long addMovimiento(Map<String, Object> movimientoMap) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_MOVIMIENTOS);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId);
			pstmt.setObject(2, movimientoMap.get(fieldToProp.get(OPERACION_ID)));
			pstmt.setObject(3, movimientoMap.get(fieldToProp.get(ESPECIE_ID)));
			pstmt.setObject(4, movimientoMap.get(fieldToProp.get(CLIENTE_ID)));
			pstmt.setObject(5, movimientoMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(6, movimientoMap.get(fieldToProp.get(CANTIDAD)));
			pstmt.setObject(7, movimientoMap.get(fieldToProp.get(VALORIZACION)));
			pstmt.setObject(8, movimientoMap.get(fieldToProp.get(TIPO)));
			pstmt.setObject(9, movimientoMap.get(fieldToProp.get(LIQUIDADO)));
			pstmt.setDate(10, new java.sql.Date(((java.util.Date)movimientoMap.get(fieldToProp.get(FECHA_ALTA))).getTime()));  
			if (movimientoMap.get(fieldToProp.get(FECHA_CONCRECION)) != null) {
				pstmt.setDate(11, new java.sql.Date(((java.util.Date)movimientoMap.get(fieldToProp.get(FECHA_CONCRECION))).getTime()));
			}
			pstmt.setObject(12, movimientoMap.get(fieldToProp.get(DIA_CONCRECION)));
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

	protected final String SQL_UPDATE = "update " + TABLE_MOVIMIENTOS + " " +
	"set " + 
	OPERACION_ID + " = (?)," + ESPECIE_ID + " = (?)," + CLIENTE_ID + " = (?)," + DIA_ID + " = (?)," + CANTIDAD + " = (?)," + VALORIZACION + " = (?)," + 
	TIPO + " = (?)," + LIQUIDADO + " = (?)," + FECHA_ALTA + " = (?)," + 
	FECHA_CONCRECION + " = (?)," + DIA_CONCRECION + " = (?)" + " " +
	"where " + ID + " = (?)";

	public Boolean saveMovimiento(Map<String, Object> movimientoMap) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setObject(1, movimientoMap.get(fieldToProp.get(OPERACION_ID)));
			pstmt.setObject(2, movimientoMap.get(fieldToProp.get(ESPECIE_ID)));
			pstmt.setObject(3, movimientoMap.get(fieldToProp.get(CLIENTE_ID)));
			pstmt.setObject(4, movimientoMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(5, movimientoMap.get(fieldToProp.get(CANTIDAD)));
			pstmt.setObject(6, movimientoMap.get(fieldToProp.get(VALORIZACION)));
			pstmt.setObject(7, movimientoMap.get(fieldToProp.get(TIPO)));
			pstmt.setObject(8, movimientoMap.get(fieldToProp.get(LIQUIDADO)));
			pstmt.setDate(9, new java.sql.Date(((java.util.Date)movimientoMap.get(fieldToProp.get(FECHA_ALTA))).getTime()));  
			if (movimientoMap.get(fieldToProp.get(FECHA_CONCRECION)) != null) {
				pstmt.setDate(10, new java.sql.Date(((java.util.Date)movimientoMap.get(fieldToProp.get(FECHA_CONCRECION))).getTime()));
			}
			pstmt.setObject(11, movimientoMap.get(fieldToProp.get(DIA_CONCRECION)));
			pstmt.setObject(12, movimientoMap.get(fieldToProp.get(ID)));
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
	
	
	protected final String SQL_DELETE = "delete from " + TABLE_MOVIMIENTOS + " where " + ID + " = ?";
	
	public Boolean deleteMovimiento(Long movimientoId) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setObject(1, movimientoId); 
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<Map<String, Object>> searchMovimientos(Map<String, Object> movimientoMap) {
		return simpleSearchTable(TABLE_MOVIMIENTOS, movimientoMap, propToField, fieldToProp);
	}

	public Collection<Map<String, Object>> searchPositiveMovimientos(Map<String, Object> movimientoMap, Operator operator) {
		Map<String, String> operatorMap = new HashMap<String, String>();
		movimientoMap.put(MovimientoProp.CANTIDAD, 0.0);
		movimientoMap.put(MovimientoProp.TIPO, MovimientoType.CANCELACION);
		operatorMap.put(MovimientoProp.CANTIDAD, ">=");
		operatorMap.put(MovimientoProp.TIPO, "<>");
		operatorMap.put(MovimientoProp.DIA, OperatorConverter.convert(operator));
		return searchTable(TABLE_MOVIMIENTOS, movimientoMap, operatorMap, propToField, fieldToProp);
	}

	public Collection<Map<String, Object>> searchNegativeMovimientos(Map<String, Object> movimientoMap, Operator operator) {
		Map<String, String> operatorMap = new HashMap<String, String>();
		movimientoMap.put(MovimientoProp.CANTIDAD, 0.0);
		movimientoMap.put(MovimientoProp.TIPO, MovimientoType.CANCELACION);
		operatorMap.put(MovimientoProp.CANTIDAD, "<");
		operatorMap.put(MovimientoProp.TIPO, "<>");
		operatorMap.put(MovimientoProp.DIA, OperatorConverter.convert(operator));
		return searchTable(TABLE_MOVIMIENTOS, movimientoMap, operatorMap, propToField, fieldToProp);
	}

	public Map<String, Object> getMovimientoById(Long id) {
		if (id == null) return null;
		Map<String, Object> movimientoMap = new HashMap<String, Object>();
		movimientoMap.put(MovimientoProp.ID, id);
		Collection<Map<String, Object>> movimientos = this.searchMovimientos(movimientoMap);
		if (movimientos.size() > 0) {
			return movimientos.iterator().next();
		} else {
			return null;
		}
	}

	public Collection<Map<String, Object>>  getMovimientosByOperacionId(Long id) {
		Map<String, Object> movimientoMap = new HashMap<String, Object>();
		movimientoMap.put(MovimientoProp.OPERACION, id);
		Collection<Map<String, Object>> movimientos = this.searchMovimientos(movimientoMap);
		return movimientos;
	}

/*	
	public Collection<Map<String, Object>> searchMovimientosCtaCteByCliente(Long clienteId, Long especieId) {
		Map<String, Object> movimientoLiqMap = new HashMap<String, Object>();
		movimientoLiqMap.put(MovimientoProp.CLIENTE, clienteId);
		movimientoLiqMap.put(MovimientoProp.ESPECIE, especieId);
		movimientoLiqMap.put(MovimientoProp.LIQUIDADO, false);
		Collection<Map<String, Object>> movimientos = this.searchMovimientos(movimientoLiqMap);
		return movimientos;
	}
*/	
	
	protected final String SQL_MOVIMIENTOS_CTA_CTE = "select *"
		+ " from movimientos as m" 
		+ " where "
		+ " m.CLIENTEID = (?) AND "
		+ " m.ESPECIEID = (?) AND "
		+ "(" + "m.LIQUIDADO = false OR m.OPERACIONID in (SELECT o.ID from operaciones o where o.TIPO = 'ALTA' OR o.TIPO = 'BAJA')" + ")";

	public Collection<Map<String, Object>> searchMovimientosCtaCteByCliente(Long clienteId, Long especieId) {
	Connection con = getConnection();
	try {
		String sql = SQL_MOVIMIENTOS_CTA_CTE;
		PreparedStatement pstmt = con.prepareStatement(sql);
		if (clienteId != null)
			pstmt.setDouble(1, clienteId);
		if (especieId != null)
			pstmt.setDouble(2, especieId);
		ResultSet rset = pstmt.executeQuery();
		return DAOUtils.getMaps(rset, fieldToProp);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

	}
	
	public Collection<Map<String, Object>> search(SearchMovimientosDTO searchDTO) {
		Connection con = getConnection();
		try {
			fieldToProp.put(ID, MovimientoProp.ID);
			fieldToProp.put(OPERACION_ID, MovimientoProp.OPERACION);
			fieldToProp.put(ESPECIE_ID, MovimientoProp.ESPECIE);
			fieldToProp.put(CLIENTE_ID, MovimientoProp.CLIENTE);
			fieldToProp.put(DIA_ID, MovimientoProp.DIA);
			fieldToProp.put(CANTIDAD, MovimientoProp.CANTIDAD);
			fieldToProp.put(VALORIZACION, MovimientoProp.VALORIZACION);
			fieldToProp.put(TIPO, MovimientoProp.TIPO);
			fieldToProp.put(LIQUIDADO, MovimientoProp.LIQUIDADO);
			fieldToProp.put(FECHA_ALTA, MovimientoProp.FECHA_ALTA);
			fieldToProp.put(FECHA_CONCRECION, MovimientoProp.FECHA_CONCRECION);
			fieldToProp.put(DIA_CONCRECION, MovimientoProp.DIA_CONCRECION);
			
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT top 400 m.ID, m.OPERACIONID, m.ESPECIEID, m.CLIENTEID, m.DIAID, m.CANTIDAD, m.VALORIZACION, m.TIPO, m.LIQUIDADO, m.FECHAALTA, m.FECHACONCRECION, m.DIACONCRECIONID" 
						+ " FROM movimientos m" 
						+ " JOIN operaciones o ON o.ID = m.OPERACIONID"
						+ " JOIN dias d ON d.ID = m.DIAID"
						+ " LEFT JOIN especies e ON e.ID = m.ESPECIEID"
						+ " LEFT JOIN clientes c ON c.ID = m.CLIENTEID";
			sql += " WHERE m.TIPO <> '" + MovimientoType.CANCELACION.name() +"'";
			if (searchDTO.getEspecieCod() != null) {
				sql += " AND (e.CODIGO = '" + searchDTO.getEspecieCod() + "')";
			}
			if (searchDTO.getClienteCod() != null) {
				sql += " AND (c.CODIGO = '" + searchDTO.getClienteCod() + "')";
			}
			if (searchDTO.getLiquidado() != null) {
				sql += " AND m.LIQUIDADO = " + (searchDTO.getLiquidado() ? "true" : "false");
			}
			if (SearchDateType.IGUAL.equals(searchDTO.getTipoFechaAlta())) {
				if (searchDTO.getFechaAltaDesde() != null) {
					sql += " AND (d.FECHAASOCIADA = ?)";
					params.add(searchDTO.getFechaAltaDesde());
				}
			} else {
				if (searchDTO.getFechaAltaDesde() != null) {
					sql += " AND (d.FECHAASOCIADA >= ?)";
					params.add(searchDTO.getFechaAltaDesde());
				}
				if (searchDTO.getFechaAltaHasta() != null) {
					sql += " AND (d.FECHAASOCIADA <= ?)";
					params.add(searchDTO.getFechaAltaHasta());
				}
			}
			if (SearchDateType.IGUAL.equals(searchDTO.getTipoFechaLiq())) {
				if (searchDTO.getFechaLiqDesde() != null) {
					sql += " AND (CASE WHEN o.FECHALIQUIDACION is null THEN d.FECHAASOCIADA ELSE o.FECHALIQUIDACION END = ?)";
					params.add(searchDTO.getFechaLiqDesde());
				}
			} else {
				if (searchDTO.getFechaLiqDesde() != null) {
					sql += " AND (CASE WHEN o.FECHALIQUIDACION is null THEN d.FECHAASOCIADA ELSE o.FECHALIQUIDACION END >= ?)";
					params.add(searchDTO.getFechaLiqDesde());
				}
				if (searchDTO.getFechaLiqHasta() != null) {
					sql += " AND (CASE WHEN o.FECHALIQUIDACION is null THEN d.FECHAASOCIADA ELSE o.FECHALIQUIDACION END <= ?)";
					params.add(searchDTO.getFechaLiqHasta());
				}
			}
			PreparedStatement stmt = con.prepareStatement(sql);

			for (int i = 0; i < params.size(); i++) {
				Object param = params.get(i);
				int index = i + 1;
				if (param instanceof java.util.Date) {
					java.util.Date dateParam = (java.util.Date) param;
					java.sql.Date sqlDateParam = new java.sql.Date(dateParam.getTime());
					stmt.setDate(index, sqlDateParam);
				} else {
					stmt.setObject(index, param);
				}
			}

			ResultSet rset = stmt.executeQuery();
			return DAOUtils.getMaps(rset, fieldToProp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
