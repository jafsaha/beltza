package beltza.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import beltza.business.BeltzaBusinessException;

public class TransactionAspect implements InvocationHandler {

	private static Logger logger = Logger.getLogger(TransactionAspect.class);
	
	protected Object target;
	protected ConnectionManager connectionMgr;

	public TransactionAspect(Object target, ConnectionManager connectionMgr) {
		this.target = target;
		this.connectionMgr = connectionMgr;
	}

	public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
		Object o = null;
		Boolean transactional = false;
		Boolean checkpoint = false;
		// Obtiene el metodo del target que por algun motivo no es el mismo que el recibido como parametro
		Method targetMethod = Class.forName(target.getClass().getName()).getMethod(method.getName(), method.getParameterTypes());
		if (targetMethod.isAnnotationPresent(Transactional.class)) {
			transactional = true;
			checkpoint = Boolean.valueOf(targetMethod.getAnnotation(Transactional.class).checkpoint());
		}
		
		Connection con = getConnection();
		if (con != null) {
			if (con.getAutoCommit()) {
				con.setAutoCommit(false);
			}
			try {
				o = method.invoke(target, parameters);
				if (transactional) {
					con.commit();
					logger.debug("Transaction commit en " + method.getName());
					if (checkpoint) {
						this.checkpoint();
						logger.debug("Transaction checkpoint en " + method.getName());
					}
				}
				if ("release".equals(method.getName())) {
					this.release();
				}
			} catch (InvocationTargetException e) {
				try {
					if (transactional) {
						con.rollback();
						logger.debug("Transaction rollback en " + method.getName());
					}
				} catch (SQLException sqle) {
					throw new BeltzaBusinessException(sqle);
				}
				throw e.getTargetException();
			}
		}
		return o;
	}
	
	protected Connection getConnection() {
		return connectionMgr.getConnection();
	}

	protected void checkpoint() throws SQLException {
		getConnection().createStatement().execute("CHECKPOINT");
	}
	
	protected void release() {
		logger.debug("Releasing");
		connectionMgr.release();
	}
	
}
