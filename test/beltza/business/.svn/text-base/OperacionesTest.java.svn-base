package beltza.business;

import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;
import beltza.BeltzaApplication;
import beltza.commons.Operator;
import beltza.domain.Cliente;
import beltza.domain.Especie;
import beltza.domain.Movimiento;
import beltza.domain.Operacion;
import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.EspecieDTO;
import beltza.dto.OperacionDTO;
import beltza.util.TestUtils;

public class OperacionesTest extends TestCase {

	public void ttest() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		EspecieDTO especieDTO1 = new EspecieDTO();
		especieDTO1.setCodigo(TestUtils.getRandomString("COD_", 4));
		especieDTO1.setAforo(TestUtils.getRandomDouble(1, 3));
		especieDTO1.setAforoInverso(false);
		model.addEspecie(especieDTO1);

		Especie especie1 = model.getEspecieByCodigo(especieDTO1.getCodigo());
		assertNotNull(especie1);

		EspecieDTO especieDTO2 = new EspecieDTO();
		especieDTO2.setCodigo(TestUtils.getRandomString("COD_", 4));
		especieDTO2.setAforo(TestUtils.getRandomDouble(1, 3));
		especieDTO2.setAforoInverso(false);
		model.addEspecie(especieDTO2);

		Especie especie2 = model.getEspecieByCodigo(especieDTO2.getCodigo());
		assertNotNull(especie2);

		String clienteCod = TestUtils.getRandomString("COD_", 4);

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		// operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.INGRESO);
		operacionDTO1.setSubtipo(OperacionSubType.NA);
		operacionDTO1.setEspecieEntraCod(especie1.getCodigo());
		// operacionDTO1.setClienteCod(clienteCod);
		// operacionDTO1.setValorizacion(TestUtils.getRandomDouble(2, 2));
		operacionDTO1.setCantidad(TestUtils.getRandomDouble(2, 2));
		operacionDTO1.setNotas("Notas op");
		operacionDTO1.setLiquidado(true);
		model.addOperacion(operacionDTO1);

		Operacion operacion1 = new Operacion();
		operacion1.setCantidad(operacionDTO1.getCantidad());
		Collection<Operacion> ops = model.searchOperaciones(operacion1);
		assertFalse(ops.isEmpty());
		assertFalse(model.searchOperaciones(operacion1).isEmpty());

		operacion1.setFechaLiquidacion(operacionDTO1.getFechaLiquidacion());
		assertFalse(model.searchOperaciones(operacion1).isEmpty());

		operacion1.setTipo(operacionDTO1.getTipo());
		assertFalse(model.searchOperaciones(operacion1).isEmpty());

		operacion1.setNotas(operacionDTO1.getNotas());
		assertFalse(model.searchOperaciones(operacion1).isEmpty());

		// Operacion de compra
		OperacionDTO operacionDTO2 = new OperacionDTO();
		operacionDTO2.setFechaLiquidacion(new Date());
		operacionDTO2.setTipo(OperacionType.COMPRA);
		operacionDTO2.setSubtipo(OperacionSubType.ACCION);
		operacionDTO2.setEspecieEntraCod(especie1.getCodigo());
		operacionDTO2.setEspecieSaleCod(especie2.getCodigo());
		operacionDTO2.setClienteCod(clienteCod);
		operacionDTO2.setValorizacion(TestUtils.getRandomDouble(2, 2));
		operacionDTO2.setCantidad(TestUtils.getRandomDouble(2, 2));
		operacionDTO2.setNotas("Notas op");
		operacionDTO2.setLiquidado(false);
		Long opId = model.addOperacion(operacionDTO2);

		Operacion operacion2 = model.getOperacionById(opId);
		assertNotNull(operacion2);

		Cliente cliente1 = model.getClienteByCodigo(clienteCod);
		assertNotNull(cliente1);

		model.liquidarOperacion(operacion2);

		Movimiento movimiento1 = new Movimiento();
		movimiento1.setOperacion(operacion2);

		assertFalse(model.searchMovimientosPositivos(movimiento1, Operator.MAYOR_IGUAL).isEmpty());
		assertFalse(model.searchMovimientosNegativos(movimiento1, Operator.MAYOR_IGUAL).isEmpty());

		model.cerrarDia();

		model.deleteOperacion(operacion2, true);

		// Operacion de canje
		model.reabrirUltimoDia();

		OperacionDTO operacionDTO3 = new OperacionDTO();
		operacionDTO3.setFechaLiquidacion(new Date());
		operacionDTO3.setTipo(OperacionType.CANJE);
		operacionDTO3.setSubtipo(OperacionSubType.CANJE);
		operacionDTO3.setEspecieEntraCod(especie1.getCodigo());
		operacionDTO3.setEspecieSaleCod(especie2.getCodigo());
		operacionDTO3.setClienteCod(clienteCod);
		operacionDTO3.setCantidad(TestUtils.getRandomDouble(2, 2));
		operacionDTO3.setCantidadSale(TestUtils.getRandomDouble(2, 2));
		operacionDTO3.setNotas("Notas op");
		operacionDTO3.setLiquidado(false);
		Long op3Id = model.addOperacion(operacionDTO3);

		Operacion operacion3 = model.getOperacionById(op3Id);
		assertNotNull(operacion3);

		model.searchMovimientosNoLiqByCliente(operacion3.getCliente().getCodigo(), operacion3.getEspecieEntra().getCodigo());

		model.liquidarOperacion(operacion3);

	}

	public void ttestModificarIngreso() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.INGRESO);
		operacionDTO1.setSubtipo(OperacionSubType.NA);
		operacionDTO1.setEspecieEntraCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);

		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		model.saveOperacion(operacion1);
	}
	
	public void ttestModificarEgreso() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.EGRESO);
		operacionDTO1.setSubtipo(OperacionSubType.NA);
		operacionDTO1.setEspecieSaleCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);

		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		model.saveOperacion(operacion1);
	}

	public void ttestModificarCanje() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.CANJE);
		operacionDTO1.setSubtipo(OperacionSubType.CANJE);
		operacionDTO1.setEspecieEntraCod(TestUtils.getRandomString("ENTRA_", 4));
		operacionDTO1.setEspecieSaleCod(TestUtils.getRandomString("SALE_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		operacionDTO1.setCantidadSale(5D);
		operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);

		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		operacion1.setCantidadSale(9D);
		model.saveOperacion(operacion1);
	}

	public void ttestModificarCompra() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.COMPRA);
		operacionDTO1.setSubtipo(OperacionSubType.ACCION);
		operacionDTO1.setEspecieEntraCod(TestUtils.getRandomString("ENTRA_", 4));
		operacionDTO1.setEspecieSaleCod(TestUtils.getRandomString("SALE_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		operacionDTO1.setValorizacion(1.5);
		operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);

		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		operacion1.setValorizacion(2.0);
		model.saveOperacion(operacion1);
	}

	public void ttestModificarVenta() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de ingreso
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.VENTA);
		operacionDTO1.setSubtipo(OperacionSubType.ACCION);
		operacionDTO1.setEspecieEntraCod(TestUtils.getRandomString("ENTRA_", 4));
		operacionDTO1.setEspecieSaleCod(TestUtils.getRandomString("SALE_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		operacionDTO1.setValorizacion(1.5);
		operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);

		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		operacion1.setValorizacion(2.0);
		model.saveOperacion(operacion1);
	}

	public void ttestAlta() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);

		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);

		if (model.getDiaAbierto() == null) {
			model.abrirDia();
		}

		// Operacion de alta
		OperacionDTO operacionDTO1 = new OperacionDTO();
		operacionDTO1.setFechaLiquidacion(new Date());
		operacionDTO1.setTipo(OperacionType.ALTA);
		operacionDTO1.setSubtipo(OperacionSubType.NA);
		operacionDTO1.setEspecieEntraCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setClienteCod(TestUtils.getRandomString("COD_", 4));
		operacionDTO1.setCantidad(4D);
		//operacionDTO1.setLiquidado(true);
		Long opId = model.addOperacion(operacionDTO1);
/*
		Operacion operacion1 = model.getOperacionById(opId);
		operacion1.setCantidad(10D);
		operacion1.setValorizacion(2.0);
		model.saveOperacion(operacion1);
*/		
	}
	
	public void testDesliquidar() {
		BeltzaApplication app = new BeltzaApplication(true);
		assertNotNull(app);
		BeltzaBusinessModel model = app.getModel();
		assertNotNull(model);
		
		
		Operacion operacion = model.getOperacionById(1L);
		
		model.desliquidarOperacion(operacion);
		
	}
	
}
