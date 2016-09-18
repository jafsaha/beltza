package beltza.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DAOUtils {

	public static String AND = "AND";
	public static String OR = "OR";
	
	/**
	 * @deprecated
	 */
	public static String buildWhere(Map<String, Object> map, String operator) {
		String where = "";
		for (String key : map.keySet()) {
			String condition = "";
			Object value = map.get(key);
			if ((value != null) && (!value.equals(""))) {
				if (value instanceof String) {
					if (!"".equals(value)) {
						condition = key + " = " + "'" + value.toString() + "'";
					}
				} else {
					condition = key + " = " + value.toString();
				}

				if (!"".equals(condition)) {
					if (!where.equals("")) {
						where = where + " " + operator + " " + condition; 
					} else {
						where = where + condition;
					}
				}
			}
		}
		if (!"".equals(where)) {
			where = "where " + where;
		}
		return where;
	}

	public static String buildWhereClause(Map<String, String> fieldOperatorMap, String operator) {
		String where = "";
		for (String key : fieldOperatorMap.keySet()) {
			String condition = "";
			String condOperator = fieldOperatorMap.get(key);
			condition = key + " " + condOperator + " " + " (?) ";
			if (!"".equals(condition)) {
				if (!where.equals("")) {
					where = where + " " + operator + " " + condition; 
				} else {
					where = where + condition;
				}
			}
		}
		if (!"".equals(where)) {
			where = "where " + where;
		}
		return where;
	}
	
    /**
     * Returns next record of result set as a Map.
     * The keys of the map are the column names,
     * as returned by the metadata.
     * The values are the columns as Objects.
     *
     * @param resultSet The ResultSet to process.
     * @exception SQLException if an error occurs.
     */
    public static Map getMap(ResultSet resultSet)
       throws SQLException {

           // Acquire resultSet MetaData
       ResultSetMetaData metaData = resultSet.getMetaData();
       int cols = metaData.getColumnCount();

           // Create hashmap, sized to number of columns
       HashMap row = new HashMap(cols,1);

           // Transfer record into hashmap
       if (resultSet.next()) {
           for (int i=1; i<=cols ; i++) {
               row.put(metaData.getColumnName(i),
                   resultSet.getObject(i));
           }
       } // end while

       return ((Map) row);

    } // end getMap


    public static Collection<Map<String, Object>> getMaps(ResultSet resultSet, Map<String, String> fieldMapping)
       throws SQLException {

           // Acquire resultSet MetaData
    	ResultSetMetaData metaData = resultSet.getMetaData();
       int cols = metaData.getColumnCount();

           // Use ArrayList to maintain ResultSet sequence
       ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

           // Scroll to each record, make map of row, add to list
       while (resultSet.next()) {
           //Map row = new HashMap(cols,1);
           Map<String, Object> row = new HashMap<String, Object>();
           for (int i=1; i<=cols ; i++) {
        	   String propName = fieldMapping.get(metaData.getColumnName(i));
               Object object = resultSet.getObject(i);
               row.put(propName, object);
           }
           list.add(row);
       } 
       return list;
    } 
	
    public static Collection<Map<String, Object>> getMaps(ResultSet resultSet) throws SQLException {
	    ResultSetMetaData metaData = resultSet.getMetaData();
	    int cols = metaData.getColumnCount();
	    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    while (resultSet.next()) {
	        Map<String, Object> row = new HashMap<String, Object>();
	        for (int i=1; i<=cols ; i++) {
	        	String fieldName = metaData.getColumnName(i);
	        	row.put(fieldName, resultSet.getObject(i));
	        }
	        list.add(row);
	    } 
	    return list;
    } 
	
}
