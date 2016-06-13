package beltza.business;

import junit.framework.TestCase;
import beltza.BeltzaApplication;
import beltza.domain.Especie;
import beltza.dto.EspecieDTO;
import beltza.util.TestUtils;

public class EspeciesTest extends TestCase {

	
	public void test() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);
		
		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);
		
		EspecieDTO especieDTO = new EspecieDTO();
		especieDTO.setCodigo(TestUtils.getRandomString("COD_", 4));
		especieDTO.setAforo(TestUtils.getRandomDouble(1, 3));
		especieDTO.setAforoInverso(false);
		model.addEspecie(especieDTO);
		
		Especie especie1 = model.getEspecieByCodigo(especieDTO.getCodigo());
		assertNotNull(especie1);
		
		assertTrue(model.getAllEspecies().contains(especie1));
		
		Especie especie2 = new Especie();
		especie2.setCodigo(especie1.getCodigo());
		assertFalse(model.searchEspecies(especie2).isEmpty());
		
		Especie especie3 = new Especie();
		especie3.setCodigo(especie1.getCodigo());
		especie3.setAforo(especie1.getAforo());
		assertFalse(model.searchEspecies(especie3).isEmpty());
		
		especie1.setAforo(TestUtils.getRandomDouble());
		model.saveEspecie(especie1);
		assertTrue(model.searchEspecies(especie3).isEmpty());

		Especie especie4 = model.getEspecieById(especie1.getId());
		assertTrue(especie1.equals(especie4));

		Especie especie5 = model.getEspecieByCodigo(especie1.getCodigo());
		assertTrue(especie1.equals(especie5));
		
		model.release();
	}
	
}
