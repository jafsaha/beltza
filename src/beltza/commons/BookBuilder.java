package beltza.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.OperacionDTO;

public class BookBuilder {

	public static final String MONEDAS = "Monedas";
	public static final String BILLETES = "Billetes";
	public static final String ACCIONES = "Acciones";
	public static final String BONOS = "Bonos";
	public static final String CANJES = "Canjes";
	public static final String INGRESO_EGRESO = "Ingreso_Egreso";
	private final Set<String> sheetNames = new HashSet<String>(Arrays.asList(MONEDAS, BILLETES, ACCIONES, BONOS, CANJES, INGRESO_EGRESO));

	public List<OperacionDTO> build(FileInputStream fis) {

		List<OperacionDTO> operaciones = new ArrayList<OperacionDTO>();

		HSSFWorkbook workbook = newWorkbook(fis);

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

			if (unknownSheetName(workbook.getSheetName(i))) {
				continue;
			}

			HSSFSheet sheet = workbook.getSheetAt(i);
			BookConfig config = this.buildBookConfigFor(workbook.getSheetName(i), sheet.getRow(0));

			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				HSSFRow row = sheet.getRow(j);

				if (isEmpty(row)) {
					continue;
				}

				operaciones.add(operationOf(row, config));

			}
		}
		return operaciones;
	}

	private boolean isEmpty(HSSFRow row) {
		if (row == null){
			return true;
		}
		
		for (Iterator<HSSFCell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
			HSSFCell cell = cellIterator.next();
			if (cell != null && HSSFCell.CELL_TYPE_BLANK != cell.getCellType()) {
				return false;
			}
		}
		return true;
	}

	private OperacionDTO operationOf(HSSFRow row, BookConfig config) {
		OperacionDTO operacion = new OperacionDTO();
		operacion.setTipo(config.type(row)); // tipo OK
		operacion.setSubtipo(config.subType()); // subtipo OK
		operacion.setFechaLiquidacion(config.fechaLiquidacion(row)); // fecha de liquidacion OK
		operacion.setClienteCod(config.cliente(row)); // cliente OK
		operacion.setEspecieEntraCod(config.cuenta(row)); // especie entra OK
		operacion.setEspecieSaleCod(config.especie(row)); // especie sale OK
		operacion.setValorizacion(config.valorizacion(row)); // precio OK
		operacion.setLiquidado(config.liquidado(row)); // liquidado OK
		operacion.setCantidad(config.cantidad(row)); // cantidad OK
		operacion.setNotas(config.notas(row));
		return operacion;
	}

	private Set<String> sheetNames() {
		return sheetNames;
	}

	private boolean unknownSheetName(String sheetName) {
		return !sheetNames().contains(sheetName);
	}

	private HSSFWorkbook newWorkbook(FileInputStream fis) {
		try {
			return new HSSFWorkbook(fis);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	private BookConfig buildBookConfigFor(String sheetName, HSSFRow row) {
		Map<String, Short> columns = new HashMap<String, Short>();
		for (short i = 0; i < row.getLastCellNum(); i++) {
			columns.put(row.getCell(i).getRichStringCellValue().getString(), i);
		}
		return new BookConfig(sheetName, columns);
	}

	public static class BookConfig {

		private static final String ALTA = "Alta";
		private static final String BAJA = "Baja";
		private static final String COMPRA = "Compra";
		private static final String VENTA = "Venta";
		private static final String CANJE = "Canje";
		private static final String INGRESO = "Ingreso";
		private static final String EGRESO = "Egreso";
		private final String sheetName;
		private final Map<String, Short> columns;

		public BookConfig(String sheetName, Map<String, Short> aColumns) {
			this.sheetName = sheetName;
			this.columns = aColumns;
		}

		private short getIndexOf(String aCellLabel) {
			return columns.get(aCellLabel);
		}

		private OperacionSubType subType() {
			final String operationSubtype = this.sheetName;
			if (MONEDAS.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.MONEDA;
			else if (BILLETES.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.BILLETE;
			else if (ACCIONES.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.ACCION;
			else if (BONOS.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.BONO;
			else if (CANJES.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.CANJE;
			else if (INGRESO_EGRESO.equalsIgnoreCase(operationSubtype))
				return OperacionSubType.NA;
			else
				throw new RuntimeException("Operation subtype not supported");
		}

		private String especie(HSSFRow row) {
			if (BILLETES.equalsIgnoreCase(sheetName)) {
				return "BB";
			}
			HSSFCell cell = row.getCell(getIndexOf("ESPECIE"));
			return cell.getRichStringCellValue().getString();
		}

		public String cuenta(HSSFRow row) {
			if (BILLETES.equalsIgnoreCase(sheetName)) {
				return "$";
			}
			HSSFCell cell = row.getCell(getIndexOf("CUENTA"));
			Long cuenta = null;
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				cuenta = Long.parseLong(cell.getRichStringCellValue().toString());
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				cuenta = new Long((long) cell.getNumericCellValue());
			} else {
				throw new RuntimeException("Cell type not supported " + cell.getCellType());
			}
			return String.valueOf(cuenta);
		}

		private String cliente(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("CLIENTE"));
			String clientCod = "";
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				clientCod = cell.getRichStringCellValue().getString();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				clientCod = String.valueOf(cell.getNumericCellValue());
			}
			return clientCod;
		}

		private Date fechaLiquidacion(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("FECHA_LIQUIDACION"));
			Date fechaLiquidacion = null;
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				try {
					fechaLiquidacion = df.parse(cell.getRichStringCellValue().toString());
				} catch (ParseException e) {
					throw new RuntimeException("Cell type date " + cell.getCellType(), e);
				}
			} else {
				throw new RuntimeException("Cell type not supported " + cell.getCellType());
			}
			return fechaLiquidacion;
		}

		private Double valorizacion(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("PRECIO"));
			Double valorizacion = null;
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
				try {
					valorizacion = nf.parse(cell.getRichStringCellValue().toString()).doubleValue();
				} catch (ParseException e) {
					throw new RuntimeException("Trying to read double ", e);
				}
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				valorizacion = cell.getNumericCellValue();
			} else {
				throw new RuntimeException("Cell type not supported " + cell.getCellType());
			}
			return valorizacion;
		}

		public Double cantidad(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("CANTIDAD"));
			Double cantidad = null;
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
				try {
					cantidad = nf.parse(cell.getRichStringCellValue().toString()).doubleValue();
				} catch (ParseException e) {
					throw new RuntimeException("Trying to read double ", e);
				}
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				cantidad = cell.getNumericCellValue();
			} else {
				throw new RuntimeException("Cell type not supported " + cell.getCellType());
			}
			return cantidad;
		}

		public Boolean liquidado(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("LIQUIDADO"));
			return cell.getRichStringCellValue().getString().equalsIgnoreCase("S") ? true : false;
		}

		public OperacionType type(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("TIPO (COMPRA / VENTA)"));
			final String type = cell.getRichStringCellValue().getString();
			if (ALTA.equalsIgnoreCase(type))
				return OperacionType.ALTA;

			else if (BAJA.equalsIgnoreCase(type))
				return OperacionType.BAJA;

			else if (COMPRA.equalsIgnoreCase(type))
				return OperacionType.COMPRA;

			else if (VENTA.equalsIgnoreCase(type))
				return OperacionType.VENTA;

			else if (CANJE.equalsIgnoreCase(type))
				return OperacionType.CANJE;

			else if (INGRESO.equalsIgnoreCase(type))
				return OperacionType.INGRESO;

			else if (EGRESO.equalsIgnoreCase(type))
				return OperacionType.EGRESO;

			else
				throw new RuntimeException("Operation subtype not supported");
		}

		public String notas(HSSFRow row) {
			HSSFCell cell = row.getCell(getIndexOf("NOTA"));
			String notas = "";
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				notas = cell.getRichStringCellValue().getString();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				notas = String.valueOf(cell.getNumericCellValue());
			}
			return notas;
		}
	}
}
