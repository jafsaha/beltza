package beltza.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class HSQLDBConnectionManager implements ConnectionManager {

	private static Logger logger = Logger.getLogger(HSQLDBConnectionManager.class);

	// Archivo properties de configuracion de la base de datos
	private final String CONFIG_FILE_NAME = "config";

	// Driver de la base de datos
	private final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";

	//Modos de conexion a la base de datos
	private final String DB_MODE_SERVER = "server";
	private final String DB_MODE_INPROCESS = "inprocess";

	// Propiedades leidas del archivo de configuracion
	protected String mode;
	protected String url;
	protected String user;
	protected String password;
	protected String ddlFilename;
	protected String dbFilename;
	
	protected Connection connection;
	
	public HSQLDBConnectionManager() {
		loadDBConnectionConfig();
		checkDatabase();
	}

	public Connection getConnection() {
		try {
			if ((connection == null) || (connection.isClosed())) {
				connection = DriverManager.getConnection(url, user, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BeltzaConfigurationException("Imposible crear conexion a la base de datos", e);
		}
		return connection;
	}
	
	public void release() {
		try {
			//Solo se cierra la bd en modo inprocess
			// Si se cierra en modo server finaliza la ejecucion del servidor
			if (mode.equals(DB_MODE_INPROCESS)) {
				this.executeSql("SHUTDOWN", getConnection());
			}
			if (connection != null) {
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BeltzaConfigurationException("Imposible liberar conexion a la base de datos", e);
		}
	}
	
	protected void loadDBConnectionConfig() {
		ResourceBundle bundle = ResourceBundle.getBundle(CONFIG_FILE_NAME);
		mode = bundle.getString("db.mode");
		if (mode.equals(DB_MODE_SERVER)) {
			url = bundle.getString("db.url.server");
		} else if (mode.equals(DB_MODE_INPROCESS)) {
			url = bundle.getString("db.url.inprocess");
		} else {
			throw new BeltzaConfigurationException("Imposible determinar el modo de conexion a la base de datos.");
		}
		user = bundle.getString("db.user"); 
		password = bundle.getString("db.password"); 

		logger.debug("Configuracion DB: " + mode + " " + url + " " + user + " " + password);

		ddlFilename = bundle.getString("db.ddl.filename"); 
		dbFilename = bundle.getString("db.filename"); 
		
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			throw new BeltzaConfigurationException("No se encuentra la clase " + DRIVER_CLASS_NAME, e);
		}
	}
	
	protected void checkDatabase() {
		File dbFile = new File(dbFilename);
		if (!dbFile.exists()) {
			File ddlFile = new File(ddlFilename);
			createDatabase(ddlFile);
		}
		
	}
	
	protected void createDatabase(File ddlFile) {
		logger.info("Inicializando base de datos...");
        FileReader fr;
		try {
			fr = new FileReader (ddlFile);
			BufferedReader br = new BufferedReader (fr);
			ArrayList<String> lines = new ArrayList<String> ();
			String line = null;
			while ((line = br.readLine()) != null) {
			    lines.add(line);
			}
			br.close();
			fr.close();
			
			ArrayList<String> sqls = new ArrayList<String> ();
			String sql = "";
			for (String aLine : lines) {
				while (aLine.length() > 0) {
					int i = aLine.indexOf(";");
					if (i == -1) {
						sql = sql + aLine;
						aLine = "";
					} else {
						String aSubLine = ""; 
						aSubLine = aLine.substring(0, i + 1);
						if ((i + 2) < aLine.length()) { 
							aLine = aLine.substring(i + 2);
						} else {
							aLine = "";
						}
						sql = sql +  aSubLine;
						sqls.add(sql);
						logger.debug("SQL:" + sql);
						sql = "";
					}
				}
			}
			
			logger.info("Creando esquema...");
			Connection con = getConnection();
			try {
				con.setAutoCommit(false);
				Statement stmt = con.createStatement();
				for (String sentence : sqls) {
					stmt.addBatch(sentence);
				}
				stmt.executeBatch();
				stmt.close();
				con.commit();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BeltzaConfigurationException("Imposible crear base de datos", e);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BeltzaConfigurationException("Imposible leer " + ddlFilename, e);
		}
		logger.info("Base de datos inicializada.");
	}
	
	protected void executeSql(String sql, Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		logger.debug("Ejecutando:" + sql);
		int i = stmt.executeUpdate(sql);
		if (i == -1) {
			throw new BeltzaConfigurationException("Fallo sentencia " + sql);
        }
		stmt.close();
	}
	
}
