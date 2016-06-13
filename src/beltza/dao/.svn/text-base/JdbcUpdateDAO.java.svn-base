package beltza.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;

public class JdbcUpdateDAO extends JdbcDAO implements UpdateDAO {

	private static Logger logger = Logger.getLogger(JdbcUpdateDAO.class);

	public JdbcUpdateDAO(ConnectionManager connectionManager) {
		super(connectionManager);
	}

	public void update(Integer currentVersion, Integer targetVersion) {
		if (currentVersion < targetVersion) {
			logger.info("Ejecutando actualizaciones de version " + currentVersion + " => " + targetVersion);
			
			for (int v = currentVersion + 1; v <= targetVersion; v++) {
				try {
					String methodName = "runUpdateV" + v;

					logger.info("Ejecutando actualizaciones de version " + v + "...");
					this.getClass().getMethod(methodName).invoke(this);
					logger.info("Actualizaciones de version " + v + " terminadas");
					
				} catch (NoSuchMethodException e) {
					logger.info("No hay actualizaciones para la version " + v);
				} catch (Exception e) {
					String msg = "Error intentando ejecutar actualizaciones de version " + v; 
					logger.error(msg);
					throw new RuntimeException(msg, e);
				}
			}
		}
	}

	public void runUpdateV21() {
		//Al final este indice no se usa pero dejo el código para tener de ejemplo para una futura actualizacion
//		Connection con = getConnection();
//		try {
//			String sql = "CREATE INDEX INX_MOVIMIENTOS_1 ON MOVIMIENTOS (operacionId)";
//			Statement pstmt = con.createStatement();
//			pstmt.execute(sql);
//			
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
	}
	
	
}
