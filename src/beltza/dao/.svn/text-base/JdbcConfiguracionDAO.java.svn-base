package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;

public class JdbcConfiguracionDAO extends JdbcDAO implements ConfiguracionDAO {

	private static Logger logger = Logger.getLogger(JdbcConfiguracionDAO.class);

	protected final String TABLE_CONFIGURACION = "CONFIGURACION";
	
	protected final String NOMBRE = "NOMBRE";
	protected final String VALOR = "VALOR";
	
	protected final String PASSWORD = "password";
	protected final String VERSION = "version";

	public JdbcConfiguracionDAO(ConnectionManager connectionManager) {
		super(connectionManager);
	}

	public String getPassword() {
		return getValue(PASSWORD);
	}

	public void setPassword(String password) {
		setValue(PASSWORD, password);
	}

	public Integer getVersion() {
		String strVersion = getValue(VERSION); 
		if (strVersion == null) {
			setVersion(0);
			return 0;
		}
		return Integer.valueOf(strVersion);
	}

	public void setVersion(Integer version) {
		setValue(VERSION, version.toString());
	}
	
	protected final String SQL_SELECT = "select * from " + TABLE_CONFIGURACION + " where " + NOMBRE + " = ?";

	protected String getValue(String nombre) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_SELECT);
			pstmt.setString(1, nombre);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getString(2);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected final String SQL_UPDATE = "update " + TABLE_CONFIGURACION + " set " + VALOR + " = ? where " + NOMBRE + " = ?";
	protected final String SQL_INSERT = "insert into " + TABLE_CONFIGURACION +
	" (" + NOMBRE + ", " + VALOR + ") " +
	" values (?, ?)";

	protected  void setValue(String nombre, String valor) {
		Connection con = getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, valor);
			pstmt.setString(2, nombre);
			int rows = pstmt.executeUpdate();
			if (rows == 0) {
				pstmt = con.prepareStatement(SQL_INSERT);
				pstmt.setString(1, nombre);
				pstmt.setString(2, valor);
				rows = pstmt.executeUpdate();
				if (rows == 0) {
					throw new RuntimeException("Imposible actualizar la configuracion.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
