package beltza.business;

import junit.framework.TestCase;
import beltza.BeltzaApplication;
import beltza.domain.Cliente;
import beltza.util.TestUtils;

public class ClientesTest extends TestCase {

	
	public void test() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);
		
		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);
		
		String codigo =TestUtils.getRandomString("COD_", 4);
		model.addCliente(codigo);
		Cliente cliente1 = model.getClienteByCodigo(codigo);
		assertNotNull(cliente1);

		assertTrue(model.getAllClientes().contains(cliente1));
		
		Cliente cliente2 = new Cliente();
		cliente2.setCodigo(cliente1.getCodigo());
		assertFalse(model.searchClientes(cliente2).isEmpty());
		
		Cliente cliente3 = model.getClienteById(cliente1.getId());
		assertTrue(cliente1.equals(cliente3));

		Cliente cliente4 = model.getClienteByCodigo(cliente1.getCodigo());
		assertTrue(cliente1.equals(cliente4));
		
	}
	
}
