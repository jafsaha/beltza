package beltza.db;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import beltza.BeltzaApplication;
import beltza.business.BeltzaBusinessModel;
import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.EspecieDTO;
import beltza.dto.OperacionDTO;

public class PopulationTest extends TestCase {

	
	
	static private int NRO_DIAS = 200; 

	static Calendar c = Calendar.getInstance();
	{
		c.add(Calendar.YEAR, -2);
	}
	
	static private String[] especies = {"USD","ARS","AWG","AZN","CLP","CNY","GYD","HKD","HUF","IMP","IRR","ISK","JMD","JOD","JMF","JPW","TMT","TND","UAH","VND"};
	static private String[] clientes = {"CLI1","CLI2","CLI3","CLI4","CLI5","CLI6","CLI7","CLI8","CLI9","CLI10","CLI11","CLI12","CLI13","CLI14","CLI15","CLI16",};

	static private OperacionType[] operacionTypes= {OperacionType.ALTA, OperacionType.BAJA, OperacionType.CANJE, OperacionType.COMPRA, OperacionType.EGRESO, OperacionType.INGRESO, OperacionType.VENTA};
	
	public void test() {
		BeltzaApplication app = new BeltzaApplication(true);
		BeltzaBusinessModel model = app.getModel();
		
		setUp(model);

		for (int d = 0; d < NRO_DIAS; d++) {
			
			Date dia = siguienteDia();
			System.out.println(dia + " Dia numero:" + d);
			
			model.abrirDia(dia);
			
			int movs = getRandomInt(90, 110);
			
			long movsDiaStart = System.currentTimeMillis();
			for (int  	o = 0; o < movs; o++) {
				OperacionDTO operacion = new OperacionDTO();
				operacion.setEspecieEntraCod(getRandomEspecie());
				operacion.setEspecieSaleCod(getRandomEspecie());
				
				operacion.setCantidad(1.0 * getRandomInt(1, 100));
				operacion.setCantidadSale(1.0 * getRandomInt(1, 100));
	
				operacion.setClienteCod(getRandomCliente());
				operacion.setDiaId(model.getDiaAbierto().getId());
				
				operacion.setFechaLiquidacion(dia);
				operacion.setLiquidado(true);
				
				operacion.setNotas("Op." + o);
				OperacionType tipo = getRandomOpType();
				operacion.setTipo(tipo);
				operacion.setSubtipo(getRandomOpSubType(tipo));
	
				operacion.setValorizacion(getRandomDouble(1, 10));

				model.addOperacion(operacion);
				System.out.print(".");
			}
			System.out.println(movs);
			
			long movsDiaEnd = System.currentTimeMillis();

			System.out.println("Dia :" + d + " Movs:" + movs + " Total:"  + (movsDiaEnd -movsDiaStart) + " ms. Promedio x mov.: "  + (movsDiaEnd -movsDiaStart)/movs + " ms.");
			
			
			model.cerrarDia();
			
		}
		
	}

	private void setUp(BeltzaBusinessModel model) {
		System.out.println("Set up");
		System.out.println("Cargando especies...");
		for (String cod : especies) {
			EspecieDTO especie = new EspecieDTO();
			especie.setCodigo(cod);
			especie.setAforo(getRandomAforo());
			especie.setAforoInverso(getRandomAforoInverso());
			model.addEspecie(especie);
		}
		
	}
	

	private Date siguienteDia() {
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	
	private String getRandomEspecie() {
		return especies[getRandomInt(0, especies.length-1)];
	}

	private String getRandomCliente() {
		return clientes[getRandomInt(0, clientes.length-1)];
	}

	private OperacionType getRandomOpType() {
		return operacionTypes[getRandomInt(0, operacionTypes.length-1)];
	}

	private OperacionSubType getRandomOpSubType(OperacionType tipo) {
		if (tipo.equals(OperacionType.ALTA) || tipo.equals(OperacionType.BAJA) || tipo.equals(OperacionType.INGRESO) || tipo.equals(OperacionType.EGRESO) ) {
			return OperacionSubType.NA;
		}
		//if (tipo.equals(OperacionType.COMPRA) || tipo.equals(OperacionType.VENTA) || tipo.equals(OperacionType.CANJE)) {
			return OperacionSubType.BILLETE;
		//}
	}
	
	
	private int getRandomInt(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	private double getRandomDouble(int min, int max) {
		return min + (Math.random() * ((max - min) + 1));
	}
	
	private double getRandomAforo() {
		Integer dec = getRandomInt(0, 99);
		Integer ent = getRandomInt(0, 10);
		Double res =Double.valueOf(ent.toString() + "." + dec.toString());
		return  res;
	}
	
	private boolean getRandomAforoInverso() {
		int bit = getRandomInt(0, 1);
		return (bit == 0);
	}	
	
	
}
