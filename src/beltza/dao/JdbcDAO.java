package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;

public class JdbcDAO {

	private static Logger logger = Logger.getLogger(JdbcDAO.class);

	protected ConnectionManager connectionManager;
	protected String SEQUENCES_TABLE = "DUAL";

	public JdbcDAO(ConnectionManager connectionManager) {
		setConnectionManager(connectionManager);
	}
	
	protected Connection getConnection() {
		if (connectionManager != null) {
			return connectionManager.getConnection();
		} else {
			throw new RuntimeException("Imposible obtener una conexion");
		}
	}
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	} 
	

	public Long getNextSequenceValue(String sequence) {
		Connection con = getConnection();
		try {
			// En HSQLDB las secuencias estan asociadas a tablas
			String sql = "select next value for " + sequence + " FROM " + SEQUENCES_TABLE;
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getLong(1);
			} else {
				throw new RuntimeException("Imposible obtener siguiente valor de la sequencia " + sequence);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection<Map<String, Object>> simpleSearchTable(String tableName, Map<String, Object> conditionMap, Map<String, String> propToFieldMap, Map<String, String> fieldToPropMap) {
		return this.searchTable(tableName, conditionMap, new HashMap<String, String>(), propToFieldMap, fieldToPropMap);
	}
	
	public Collection<Map<String, Object>> searchTable(String tableName, Map<String, Object> conditionMap, Map<String, String> operatorMap, Map<String, 
														String> propToFieldMap, Map<String, String> fieldToPropMap) {
		return searchTable(tableName, conditionMap, operatorMap, null, null, propToFieldMap, fieldToPropMap);
	}

	public Collection<Map<String, Object>> searchTable(String tableName, Map<String, Object> conditionMap, Map<String, String> operatorMap,  
														String orderColumn, Order order,
														Map<String, String> propToFieldMap, Map<String, String> fieldToPropMap) {
		Connection con = getConnection();
		try {
			String sql = "select * from " + tableName;
			Map<String, Object> fieldConditionMap = new HashMap<String, Object>();
			Map<String, String> fieldOperatorMap = new HashMap<String, String>();
			
			for (String propKey : conditionMap.keySet()) {
				String field = propToFieldMap.get(propKey);
				Object value = conditionMap.get(propKey); 
				if ((value != null) && (!value.equals(""))) {
					fieldConditionMap.put(field, value);
					String operator = operatorMap.get(propKey);
					if (operator == null) {
						operator = "=";
					}
					fieldOperatorMap.put(field, operator);
				}
				
			}
			String orderBy = "";
			if (orderColumn != null) {
				orderBy = "ORDER BY " + orderColumn;
				if (order != null) {
					orderBy = orderBy + " " + order.name();
				}
			}
				
			
			String where = DAOUtils.buildWhereClause(fieldOperatorMap, DAOUtils.AND);
			sql = sql + " " + where + " " + orderBy;
			logger.debug(sql);
			PreparedStatement pstmt = con.prepareStatement(sql);
			int i = 1;
			for (String fieldKey : fieldConditionMap.keySet()) {
				Object value = fieldConditionMap.get(fieldKey);
				if (value instanceof java.util.Date) {
					java.util.Date dateValue = (java.util.Date) value;
					java.sql.Date sqlDateValue = new java.sql.Date(dateValue.getTime());  
					pstmt.setDate(i, sqlDateValue);
				} else {
					pstmt.setObject(i, value);
				}
				i++;
			}
			
			ResultSet rset = pstmt.executeQuery();
			return DAOUtils.getMaps(rset, fieldToPropMap);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
