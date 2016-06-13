package beltza.db;

import java.sql.Connection;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class ConnectionManagerTest extends TestCase {

	private static Logger logger = Logger.getLogger(ConnectionManagerTest.class);
	
	public void test() {
		HSQLDBConnectionManager hsqldbConnManager = new HSQLDBConnectionManager();
		assertNotNull(hsqldbConnManager);
		
		Connection con = hsqldbConnManager.getConnection();
		assertNotNull(con);
		
		
	}
	
}
