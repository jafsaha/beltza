package beltza;

import junit.framework.Test;
import junit.framework.TestSuite;
import beltza.business.ClientesTest;
import beltza.business.ConfiguracionTest;
import beltza.business.EspeciesTest;
import beltza.business.OperacionesTest;
import beltza.db.ConnectionManagerTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for beltza");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConnectionManagerTest.class);
		suite.addTestSuite(BeltzaApplicationTest.class);
		suite.addTestSuite(ConfiguracionTest.class);
		suite.addTestSuite(EspeciesTest.class);
		suite.addTestSuite(ClientesTest.class);
		suite.addTestSuite(OperacionesTest.class);
		//$JUnit-END$
		return suite;
	}

}
