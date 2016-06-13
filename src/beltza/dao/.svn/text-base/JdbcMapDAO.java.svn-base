package beltza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import beltza.db.ConnectionManager;

public class JdbcMapDAO extends JdbcDAO implements MapDAO {

	private static Logger logger = Logger.getLogger(JdbcMapDAO.class);

	public JdbcMapDAO(ConnectionManager connectionManager) {
		super(connectionManager);
	}

	protected final String SQL_MOVIMIENTOS_POSITIVOS_CONSOLIDADOS = "select c.CODIGO as COD_CLIENTE, e.CODIGO as COD_ESPECIE, sum(m.CANTIDAD) as CANTIDAD"
			+ " from movimientos as m " + "left join especies e on m.ESPECIEID = e.ID " + "left join clientes c on m.CLIENTEID = c.ID "
			+ "where m.CLIENTEID is not NULL";

	/*
	 * + "having sum(m.CANTIDAD) > 0";
	 */
	public Collection<Map<String, Object>> searchMovimientosConsolidados(Long especieId, Long clienteId, int signo, boolean filtrarCeros) {
		Connection con = getConnection();
		try {
			int numParam = 1;
			String sql = SQL_MOVIMIENTOS_POSITIVOS_CONSOLIDADOS;
			if (clienteId != null)
				sql += " and c.ID = (?)";
			if (especieId != null)
				sql += " and e.ID = (?)";
			sql += " group by c.CODIGO, e.CODIGO";

			if (signo > 0){
				sql += " having sum(m.CANTIDAD) >= 0";
			}else if (signo < 0){
				sql += " having sum(m.CANTIDAD) < 0";
			}
			
			if (filtrarCeros){
				sql += " and round(abs(sum(m.CANTIDAD)), 2) > 0.01";
			}
			
			sql += " ORDER BY c.CODIGO";

			PreparedStatement pstmt = con.prepareStatement(sql);
			if (clienteId != null) {
				pstmt.setDouble(1, clienteId);
				numParam++;
			}
			if (especieId != null)
				pstmt.setDouble(numParam, especieId);
			ResultSet rset = pstmt.executeQuery();
			return DAOUtils.getMaps(rset);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// Consolidacion de movimientos concretados con fecha de concrecion anterior
	// o igual a FF
	// o movimientos no concretados con fecha de liquidacion anterior o igual a
	// FF
	protected final String SQL_MOVIMIENTOS_POSITIVOS_CONSOLIDADOS_BY_FECHALIQ = "select c.CODIGO as COD_CLIENTE, e.CODIGO as COD_ESPECIE, sum(m.CANTIDAD) as CANTIDAD"
			+ " from movimientos as m "
			+ "left join especies e on m.ESPECIEID = e.ID "
			+ "left join clientes c on m.CLIENTEID = c.ID "
			+ "left join dias d on m.DIAID = d.ID "
			+ "left join operaciones o on m.OPERACIONID = o.ID "
			+ "where "
			+ "m.CLIENTEID is not NULL "
			+ "and "
			+ "( "
			+ "( "
			+ "o.FECHALIQUIDACION is NULL "
			+ "and "
			+ "d.FECHAASOCIADA <= (?)  "
			+ ") "
			+ "or "
			+ "( "
			+ "o.FECHALIQUIDACION is not NULL  "
			+ "and " + "o.FECHALIQUIDACION <= (?)  " + ") " + ") ";

	public Collection<Map<String, Object>> searchMovimientosConsolidadosByFechaLiq(Long especieId, Long clienteId, int signo, Date fechaLiquidacion,
			boolean filtrarCeros) {
		Connection con = getConnection();
		try {
			int numParam = 1;
			String sql = SQL_MOVIMIENTOS_POSITIVOS_CONSOLIDADOS_BY_FECHALIQ;
			if (clienteId != null)
				sql += " and c.ID = (?)";
			if (especieId != null)
				sql += " and e.ID = (?)";
			sql += " group by c.CODIGO, e.CODIGO";

			if (signo > 0){
				sql += " having sum(m.CANTIDAD) >= 0";
			}else if (signo < 0){
				sql += " having sum(m.CANTIDAD) < 0";
			}
			
			if (filtrarCeros){
				sql += " and round(abs(sum(m.CANTIDAD)), 2) > 0.01";
			}			

			sql += " ORDER BY c.CODIGO";

			logger.debug(sql);

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDate(numParam, new java.sql.Date(fechaLiquidacion.getTime()));
			numParam++;
			pstmt.setDate(numParam, new java.sql.Date(fechaLiquidacion.getTime()));
			numParam++;
			if (clienteId != null) {
				pstmt.setDouble(numParam, clienteId);
				numParam++;
			}
			if (especieId != null)
				pstmt.setDouble(numParam, especieId);
			ResultSet rset = pstmt.executeQuery();
			return DAOUtils.getMaps(rset);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
