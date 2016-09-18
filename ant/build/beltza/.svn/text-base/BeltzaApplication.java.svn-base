package beltza;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import beltza.business.BeltzaBusinessModel;
import beltza.business.BeltzaBusinessModelImpl;
import beltza.commons.LoggingAspect;
import beltza.dao.CierreDAO;
import beltza.dao.ClienteDAO;
import beltza.dao.DiaDAO;
import beltza.dao.EspecieDAO;
import beltza.dao.JdbcCierreDAO;
import beltza.dao.JdbcClienteDAO;
import beltza.dao.JdbcConfiguracionDAO;
import beltza.dao.JdbcDiaDAO;
import beltza.dao.JdbcEspecieDAO;
import beltza.dao.JdbcMapDAO;
import beltza.dao.JdbcMovimientoDAO;
import beltza.dao.JdbcOperacionDAO;
import beltza.dao.JdbcUpdateDAO;
import beltza.dao.MapDAO;
import beltza.dao.MovimientoDAO;
import beltza.dao.OperacionDAO;
import beltza.db.BeltzaConfigurationException;
import beltza.db.ConnectionManager;
import beltza.db.HSQLDBConnectionManager;
import beltza.db.TransactionAspect;
import beltza.form.FormLogin;
import beltza.report.BeltzaReportManager;

public class BeltzaApplication {

	private static Logger logger = Logger.getLogger(BeltzaApplication.class);

	// Archivo properties de configuracion de la aplicacion
	public static final String APPLICATION_FILE_NAME = "application";

	protected static BeltzaBusinessModel model;
	protected ConnectionManager connManager;

	protected Integer appVersion;
	protected String codigoEspecieDeReferencia = "";
	protected String codigosEspeciesMonitoreo = "";

	public BeltzaApplication() {
		this(false);
	}

	public BeltzaApplication(Boolean modelMode) {
		try {
			HSQLDBConnectionManager hsqldbConnManager = new HSQLDBConnectionManager();
			connManager = hsqldbConnManager;
			
			loadApplicationConfig();
			
			JdbcEspecieDAO jdbcEspecieDAO = new JdbcEspecieDAO(connManager); 
			LoggingAspect jdbcEspecieDAOAspect = new LoggingAspect(jdbcEspecieDAO, "EspecieDAO",100);
			EspecieDAO jdbcEspecieDAOProxy = (EspecieDAO) Proxy
					.newProxyInstance(jdbcEspecieDAO.getClass().getClassLoader(),
							new Class[] { EspecieDAO.class }, jdbcEspecieDAOAspect);
			
			JdbcOperacionDAO jdbcOperacionDAO = new JdbcOperacionDAO(connManager); 
			LoggingAspect jdbcOperacionDAOAspect = new LoggingAspect(jdbcOperacionDAO, "OperacionDAO",100);
			OperacionDAO jdbcOperacionDAOProxy = (OperacionDAO) Proxy
					.newProxyInstance(jdbcOperacionDAO.getClass().getClassLoader(),
							new Class[] { OperacionDAO.class }, jdbcOperacionDAOAspect);

			JdbcMovimientoDAO jdbcMovimientoDAO = new JdbcMovimientoDAO(connManager); 
			LoggingAspect jdbcMovimientoDAOAspect = new LoggingAspect(jdbcMovimientoDAO, "MovimientoDAO",100);
			MovimientoDAO jdbcMovimientoDAOProxy = (MovimientoDAO) Proxy
					.newProxyInstance(jdbcMovimientoDAO.getClass().getClassLoader(),
							new Class[] { MovimientoDAO.class }, jdbcMovimientoDAOAspect);
			
			JdbcClienteDAO jdbcClienteDAO = new JdbcClienteDAO(connManager); 
			LoggingAspect jdbcClienteDAOAspect = new LoggingAspect(jdbcClienteDAO, "ClienteDAO",100);
			ClienteDAO jdbcClienteDAOProxy = (ClienteDAO) Proxy
					.newProxyInstance(jdbcClienteDAO.getClass().getClassLoader(),
							new Class[] { ClienteDAO.class }, jdbcClienteDAOAspect);
			
			JdbcDiaDAO jdbcDiaDAO = new JdbcDiaDAO(connManager); 
			LoggingAspect jdbcDiaDAOAspect = new LoggingAspect(jdbcDiaDAO, "DiaDAO",100);
			DiaDAO jdbcDiaDAOProxy = (DiaDAO) Proxy
					.newProxyInstance(jdbcDiaDAO.getClass().getClassLoader(),
							new Class[] { DiaDAO.class }, jdbcDiaDAOAspect);
			
			JdbcCierreDAO jdbcCierreDAO = new JdbcCierreDAO(connManager); 
			LoggingAspect jdbcCierreDAOAspect = new LoggingAspect(jdbcCierreDAO, "CierreDAO",100);
			CierreDAO jdbcCierreDAOProxy = (CierreDAO) Proxy
					.newProxyInstance(jdbcCierreDAO.getClass().getClassLoader(),
							new Class[] { CierreDAO.class }, jdbcCierreDAOAspect);

			JdbcMapDAO jdbcMapDAO = new JdbcMapDAO(connManager); 
			LoggingAspect jdbcMapDAOAspect = new LoggingAspect(jdbcMapDAO, "MapDAO",100);
			MapDAO jdbcMapDAOProxy = (MapDAO) Proxy
					.newProxyInstance(jdbcMapDAO.getClass().getClassLoader(),
							new Class[] { MapDAO.class }, jdbcMapDAOAspect);
			
			
			BeltzaBusinessModelImpl modelImpl = new BeltzaBusinessModelImpl(
					new JdbcConfiguracionDAO(connManager), 
					jdbcEspecieDAOProxy,
					jdbcOperacionDAOProxy,
					jdbcMovimientoDAOProxy,
					jdbcClienteDAOProxy,
					jdbcDiaDAOProxy,
					jdbcMapDAOProxy,
					jdbcCierreDAOProxy,
					new JdbcUpdateDAO(connManager)
					);
	
			modelImpl.setCodigoEspecieDeReferencia(codigoEspecieDeReferencia);
			modelImpl.setCodigosEspeciesMonitoreo(codigosEspeciesMonitoreo);
			
			TransactionAspect txAspect = new TransactionAspect(modelImpl, connManager);
			BeltzaBusinessModel txProxy = (BeltzaBusinessModel) Proxy
					.newProxyInstance(modelImpl.getClass().getClassLoader(),
							new Class[] { BeltzaBusinessModel.class }, txAspect);
			//model = txProxy;

			LoggingAspect loggingAspect = new LoggingAspect(txProxy, "model",100);
			BeltzaBusinessModel loggingProxy = (BeltzaBusinessModel) Proxy
			.newProxyInstance(modelImpl.getClass().getClassLoader(),
					new Class[] { BeltzaBusinessModel.class }, loggingAspect);

			model = loggingProxy;

			BeltzaReportManager.getInstance().initialize(connManager);
			
			// run updates
			modelImpl.runUpdates(appVersion);
			
			if (!modelMode) {
				FormLogin fmLogin = new FormLogin(model);
			}
			
			
		} catch (BeltzaConfigurationException e) {
			logger.error("Error al iniciar la aplicacion");
			e.printStackTrace();
		}
	}

	public static BeltzaBusinessModel getModel() {
		return model;
	}

	protected void loadApplicationConfig() {
		ResourceBundle bundle = ResourceBundle.getBundle(APPLICATION_FILE_NAME);
		appVersion = Integer.valueOf(bundle.getString("application.version"));
		codigoEspecieDeReferencia = bundle.getString("especie.referencia.codigo");
		codigosEspeciesMonitoreo = bundle.getString("especies.monitoreo.codigos");
		logger.debug("Configuracion App: " + codigoEspecieDeReferencia + " " + codigosEspeciesMonitoreo);
	}

	public static void main(String[] args) {
		BeltzaApplication app = new BeltzaApplication();
	}

}
