package beltza.business;

import junit.framework.TestCase;
import beltza.BeltzaApplication;

public class ConfiguracionTest extends TestCase {

	
	public void test() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);
		
		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);
		
		String password = "admin";
		model.setPassword(password);
		
		assertTrue(model.verifyPassword(password));
		model.setPassword(password + password);
		assertFalse(model.verifyPassword(password));
		
		model.setPassword(password);
	}
	
	
}
