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

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;
import beltza.domain.OperacionProp;
import beltza.dto.SearchDateType;
import beltza.dto.SearchOperacionesDTO;

public class JdbcOperacionDAO extends JdbcDAO implements OperacionDAO {

	private static Logger logger = Logger.getLogger(JdbcOperacionDAO.class);

	protected final String TABLE_OPERACIONES = "OPERACIONES";

	protected final String SEQ_OPERACIONES = "SEQ_OPERACIONES";
	protected final String SEQ_OPERACION_NUMERO = "SEQ_OPERACION_NUMERO";

	// Nombres de campos en la DB (case-sensitive)
	protected final String ID = "ID";
	protected final String NUMERO = "NUMERO";
	protected final String FECHA_ALTA = "FECHAALTA";
	protected final String FECHA_LIQUIDACION = "FECHALIQUIDACION";
	protected final String TIPO = "TIPO";
	protected final String SUBTIPO = "SUBTIPO";
	protected final String CLIENTE_ID = "CLIENTEID";
	protected final String ESPECIE_ENTRA_ID = "ESPECIEENTRAID";
	protected final String ESPECIE_SALE_ID = "ESPECIESALEID";
	protected final String DIA_ID = "DIAID";
	protected final String VALORIZACION = "VALORIZACION";
	protected final String CANTIDAD = "CANTIDAD";
	protected final String CANTIDAD_SALE = "CANTIDADSALE";
	protected final String NOTAS = "NOTAS";

	protected Map<String, String> fieldToProp;
	protected Map<String, String> propToField;

	public JdbcOperacionDAO(ConnectionManager connectionManager) {
		super(connectionManager);
		buildFieldMapping();
	}

	protected void buildFieldMapping() {
		// (campo, propiedad)
		fieldToProp = new HashMap<String, String>();
		fieldToProp.put(ID, OperacionProp.ID);
		fieldToProp.put(NUMERO, OperacionProp.NUMERO);
		fieldToProp.put(FECHA_ALTA, OperacionProp.FECHA_ALTA);
		fieldToProp.put(FECHA_LIQUIDACION, OperacionProp.FECHA_LIQUIDACION);
		fieldToProp.put(TIPO, OperacionProp.TIPO);
		fieldToProp.put(SUBTIPO, OperacionProp.SUBTIPO);
		fieldToProp.put(CLIENTE_ID, OperacionProp.CLIENTE);
		fieldToProp.put(ESPECIE_ENTRA_ID, OperacionProp.ESPECIE_ENTRA);
		fieldToProp.put(ESPECIE_SALE_ID, OperacionProp.ESPECIE_SALE);
		fieldToProp.put(DIA_ID, OperacionProp.DIA);
		fieldToProp.put(VALORIZACION, OperacionProp.VALORIZACION);
		fieldToProp.put(CANTIDAD, OperacionProp.CANTIDAD);
		fieldToProp.put(CANTIDAD_SALE, OperacionProp.CANTIDAD_SALE);
		fieldToProp.put(NOTAS, OperacionProp.NOTAS);

		// (propiedad, campo)
		propToField = new HashMap<String, String>();
		for (String key : fieldToProp.keySet()) {
			propToField.put(fieldToProp.get(key), key);
		}
	}

	protected final String SQL_INSERT = "insert into " + TABLE_OPERACIONES + " (" + ID + "," + NUMERO + "," + FECHA_ALTA + "," + FECHA_LIQUIDACION + "," + TIPO
			+ "," + SUBTIPO + "," + CLIENTE_ID + "," + ESPECIE_ENTRA_ID + "," + ESPECIE_SALE_ID + "," + DIA_ID + "," + VALORIZACION + "," + CANTIDAD + ","
			+ CANTIDAD_SALE + "," + NOTAS + ") " + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public Long addOperacion(Map<String, Object> operacionMap) {
		Connection con = getConnection();
		try {
			Long nextId = getNextSequenceValue(SEQ_OPERACIONES);
			Long nextOpNumero = getNextSequenceValue(SEQ_OPERACION_NUMERO);
			PreparedStatement pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setObject(1, nextId);
			pstmt.setObject(2, nextOpNumero);

			// De esta forma solo guarda la fecha, dejando HH MM y SS en cero
			pstmt.setDate(3, new java.sql.Date(((java.util.Date) operacionMap.get(fieldToProp.get(FECHA_ALTA))).getTime()));
			java.util.Date fechaLiquidacion = (java.util.Date) operacionMap.get(fieldToProp.get(FECHA_LIQUIDACION));
			if (fechaLiquidacion != null) {
				pstmt.setDate(4, new java.sql.Date(fechaLiquidacion.getTime()));
			}
			/*
			 * //De esta forma guarda la fecha HH MM y SS exactas
			 * pstmt.setTimestamp(2, new
			 * java.sql.Timestamp(operacion.getFechaNegociacion().getTime()));
			 */
			pstmt.setObject(5, operacionMap.get(fieldToProp.get(TIPO)));
			pstmt.setObject(6, operacionMap.get(fieldToProp.get(SUBTIPO)));
			pstmt.setObject(7, operacionMap.get(fieldToProp.get(CLIENTE_ID)));
			pstmt.setObject(8, operacionMap.get(fieldToProp.get(ESPECIE_ENTRA_ID)));
			pstmt.setObject(9, operacionMap.get(fieldToProp.get(ESPECIE_SALE_ID)));
			pstmt.setObject(10, operacionMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(11, operacionMap.get(fieldToProp.get(VALORIZACION)));
			pstmt.setObject(12, operacionMap.get(fieldToProp.get(CANTIDAD)));
			pstmt.setObject(13, operacionMap.get(fieldToProp.get(CANTIDAD_SALE)));
			pstmt.setObject(14, operacionMap.get(fieldToProp.get(NOTAS)));
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

	protected final String SQL_UPDATE = "update " + TABLE_OPERACIONES + " " + "set " + NUMERO + " = (?)," + FECHA_ALTA + " = (?)," + FECHA_LIQUIDACION
			+ " = (?)," + TIPO + " = (?)," + SUBTIPO + " = (?)," + CLIENTE_ID + " = (?)," + ESPECIE_ENTRA_ID + " = (?)," + ESPECIE_SALE_ID + " = (?)," + DIA_ID
			+ " = (?)," + VALORIZACION + " = (?)," + CANTIDAD + " = (?)," + CANTIDAD_SALE + " = (?)," + NOTAS + " = (?)" + " " + "where " + ID + " = (?)";

	public Boolean saveOperacion(Map<String, Object> operacionMap) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setObject(1, operacionMap.get(fieldToProp.get(NUMERO)));
			pstmt.setDate(2, new java.sql.Date(((java.util.Date) operacionMap.get(fieldToProp.get(FECHA_ALTA))).getTime()));
			java.util.Date fechaLiquidacion = (java.util.Date) operacionMap.get(fieldToProp.get(FECHA_LIQUIDACION));
			if (fechaLiquidacion != null) {
				pstmt.setDate(3, new java.sql.Date(fechaLiquidacion.getTime()));
			}
			pstmt.setObject(4, operacionMap.get(fieldToProp.get(TIPO)));
			pstmt.setObject(5, operacionMap.get(fieldToProp.get(SUBTIPO)));
			pstmt.setObject(6, operacionMap.get(fieldToProp.get(CLIENTE_ID)));
			pstmt.setObject(7, operacionMap.get(fieldToProp.get(ESPECIE_ENTRA_ID)));
			pstmt.setObject(8, operacionMap.get(fieldToProp.get(ESPECIE_SALE_ID)));
			pstmt.setObject(9, operacionMap.get(fieldToProp.get(DIA_ID)));
			pstmt.setObject(10, operacionMap.get(fieldToProp.get(VALORIZACION)));
			pstmt.setObject(11, operacionMap.get(fieldToProp.get(CANTIDAD)));
			pstmt.setObject(12, operacionMap.get(fieldToProp.get(CANTIDAD_SALE)));
			pstmt.setObject(13, operacionMap.get(fieldToProp.get(NOTAS)));
			pstmt.setObject(14, operacionMap.get(fieldToProp.get(ID)));
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected final String SQL_DELETE = "delete from " + TABLE_OPERACIONES + " where " + ID + " = ?";

	public Boolean deleteOperacion(Long operacionId) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setObject(1, operacionId);
			int rows = pstmt.executeUpdate();
			return (rows > 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Map<String, Object>> searchOperaciones(Map<String, Object> operacionMap) {
		return simpleSearchTable(TABLE_OPERACIONES, operacionMap, propToField, fieldToProp);
	}

	public Map<String, Object> getOperacionByNumero(Long numero) {
		if (numero == null)
			return null;
		Map<String, Object> operacionMap = new HashMap<String, Object>();
		operacionMap.put(OperacionProp.NUMERO, numero);
		Collection<Map<String, Object>> operaciones = this.searchOperaciones(operacionMap);
		if (operaciones.size() > 0) {
			return operaciones.iterator().next();
		} else {
			return null;
		}
	}

	public Map<String, Object> getOperacionById(Long id) {
		if (id == null)
			return null;
		Map<String, Object> operacionMap = new HashMap<String, Object>();
		operacionMap.put(OperacionProp.ID, id);
		Collection<Map<String, Object>> operaciones = this.searchOperaciones(operacionMap);
		if (operaciones.size() > 0) {
			return operaciones.iterator().next();
		} else {
			return null;
		}
	}

	public Collection<Map<String, Object>> getOperacionByDia(Long diaId) {
		Map<String, Object> operacionMap = new HashMap<String, Object>();
		operacionMap.put(OperacionProp.DIA, diaId);
		return this.searchOperaciones(operacionMap);
	}

	public Collection<Map<String, Object>> getOperacionSubTypeListFromDiaAbierto() {
		Connection con = getConnection();
		try {
			String sql = "SELECT DISTINCT o.SUBTIPO as SUBTIPO FROM operaciones o JOIN dias d ON d.ID = o.DIAID WHERE d.ABIERTO";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			return DAOUtils.getMaps(rset, fieldToProp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Map<String, Object>> getOperacionSubTypeListByExample(SearchOperacionesDTO searchDTO) {
		Connection con = getConnection();
		try {
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT DISTINCT o.SUBTIPO as SUBTIPO FROM operaciones o";
			sql += " JOIN dias d ON d.ID = o.DIAID";
			sql += " LEFT JOIN especies e1 ON e1.ID = o.ESPECIEENTRAID";
			sql += " LEFT JOIN especies e2 ON e2.ID = o.ESPECIESALEID";
			sql += " LEFT JOIN clientes c ON c.ID = o.CLIENTEID";
			sql += " WHERE 1=1";
			if (searchDTO.getEspecieEntraCod() != null && searchDTO.getEspecieEntraCod().trim().length() > 0)
				sql += " AND (e1.CODIGO = '" + searchDTO.getEspecieEntraCod() + "' OR e2.CODIGO = '" + searchDTO.getEspecieEntraCod() + "')";
			if (searchDTO.getClienteCod() != null) {
				sql += " AND (c.CODIGO = '" + searchDTO.getClienteCod() + "')";
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

	public Collection<Map<String, Object>> searchByIgual(SearchOperacionesDTO searchDTO) {
		Connection con = getConnection();
		try {
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT * FROM operaciones o" 
						+ " LEFT JOIN especies ee ON ee.ID = o.ESPECIEENTRAID"
						+ " LEFT JOIN especies es ON es.ID = o.ESPECIESALEID" 
						+ " LEFT JOIN clientes c ON c.ID = o.CLIENTEID";
			sql += " WHERE 1=1";
			if (searchDTO.getTipo() != null) {
				sql += " AND (o.TIPO = '" + searchDTO.getTipo() + "')";
			}
			if (searchDTO.getSubtipo() != null) {
				sql += " AND (o.SUBTIPO = '" + searchDTO.getSubtipo() + "')";
			}
			if (searchDTO.getEspecieEntraCod() != null) {
				sql += " AND (ee.CODIGO = '" + searchDTO.getEspecieEntraCod() + "')";
			}
			if (searchDTO.getEspecieSaleCod() != null) {
				sql += " AND (es.CODIGO = '" + searchDTO.getEspecieSaleCod() + "')";
			}
			if (searchDTO.getClienteCod() != null) {
				sql += " AND (c.CODIGO = '" + searchDTO.getClienteCod() + "')";
			}
			if (searchDTO.getFechaAltaDesde() != null) {
				sql += " AND (o.FECHAALTA = ?)";
				params.add(searchDTO.getFechaAltaDesde());
			}
			if (searchDTO.getFechaLiqDesde() != null) {
				sql += " AND (o.FECHALIQUIDACION = ?)";
				params.add(searchDTO.getFechaLiqDesde());
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

	public Collection<Map<String, Object>> searchByRango(SearchOperacionesDTO searchDTO) {
		Connection con = getConnection();
		try {
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT * FROM operaciones o" 
							+ " LEFT JOIN especies ee ON ee.ID = o.ESPECIEENTRAID"
							+ " LEFT JOIN especies es ON es.ID = o.ESPECIESALEID" 
							+ " LEFT JOIN clientes c ON c.ID = o.CLIENTEID";
			sql += " WHERE 1=1";
			if (searchDTO.getTipo() != null) {
				sql += " AND (o.TIPO = '" + searchDTO.getTipo() + "')";
			}
			if (searchDTO.getSubtipo() != null) {
				sql += " AND (o.SUBTIPO = '" + searchDTO.getSubtipo() + "')";
			}
			if (searchDTO.getEspecieEntraCod() != null) {
				sql += " AND (ee.CODIGO = '" + searchDTO.getEspecieEntraCod() + "')";
			}
			if (searchDTO.getEspecieSaleCod() != null) {
				sql += " AND (es.CODIGO = '" + searchDTO.getEspecieSaleCod() + "')";
			}
			if (searchDTO.getClienteCod() != null) {
				sql += " AND (c.CODIGO = '" + searchDTO.getClienteCod() + "')";
			}
			if (searchDTO.getFechaAltaDesde() != null) {
				sql += " AND (o.FECHAALTA >= ?)";
				params.add(searchDTO.getFechaAltaDesde());
			}
			if (searchDTO.getFechaAltaHasta() != null) {
				sql += " AND (o.FECHAALTA <= ?)";
				params.add(searchDTO.getFechaAltaHasta());
			}
			if (searchDTO.getFechaLiqDesde() != null) {
				sql += " AND (o.FECHALIQUIDACION >= ?)";
				params.add(searchDTO.getFechaLiqDesde());
			}
			if (searchDTO.getFechaLiqHasta() != null) {
				sql += " AND (o.FECHALIQUIDACION <= ?)";
				params.add(searchDTO.getFechaLiqHasta());
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

	public Collection<Map<String, Object>> search(SearchOperacionesDTO searchDTO) {
		Connection con = getConnection();
		try {
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT TOP 200 o.ID, o.NUMERO, o.FECHAALTA, o.FECHALIQUIDACION, o.TIPO, o.SUBTIPO, o.CLIENTEID, o.ESPECIEENTRAID, o.ESPECIESALEID, o.DIAID, o.VALORIZACION, o.CANTIDAD, o.CANTIDADSALE, o.NOTAS" 
						+ " FROM operaciones o" 
						+ " JOIN dias d ON d.ID = o.DIAID"
						+ " LEFT JOIN especies ee ON ee.ID = o.ESPECIEENTRAID"
						+ " LEFT JOIN especies es ON es.ID = o.ESPECIESALEID" 
						+ " LEFT JOIN clientes c ON c.ID = o.CLIENTEID";
			sql += " WHERE 1=1";
			if (searchDTO.getTipo() != null) {
				sql += " AND (o.TIPO = '" + searchDTO.getTipo() + "')";
			}
			if (searchDTO.getSubtipo() != null) {
				sql += " AND (o.SUBTIPO = '" + searchDTO.getSubtipo() + "')";
			}
			if (searchDTO.getEspecieEntraCod() != null) {
				sql += " AND (ee.CODIGO = '" + searchDTO.getEspecieEntraCod() + "')";
			}
			if (searchDTO.getEspecieSaleCod() != null) {
				sql += " AND (es.CODIGO = '" + searchDTO.getEspecieSaleCod() + "')";
			}
			if (searchDTO.getClienteCod() != null) {
				sql += " AND (c.CODIGO = '" + searchDTO.getClienteCod() + "')";
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
