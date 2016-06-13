package beltza.commons;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import beltza.business.BeltzaBusinessModel;
import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.OperacionDTO;

public class BookBuilder {

	BookConfig config = new BookConfig();
	OperationBuilder operationBuilder = new OperationBuilder(); 
	List<HSSFRow> rows = new ArrayList<HSSFRow>();
	private BeltzaBusinessModel model;

	public BookBuilder(BeltzaBusinessModel model) {
		this.model = model;
	}

	public List<OperacionDTO> buildOperations() {
		List<OperacionDTO> operations = new ArrayList<OperacionDTO>();
		for (HSSFRow row : rows) {
			operations.add(buildOperation(row, config));
		}
		return operations;
	}
	
	private OperacionDTO buildOperation(HSSFRow row, BookConfig config) {
		OperacionDTO operacion= new OperacionDTO();
		operacion.setClienteCod(getClientCod(row.getCell(config.getClientIdIdx())));
		operacion.setEspecieEntraCod(getCodigoEspecie(row.getCell(config.getEspecieIdx())));
		operacion.setValorizacion(getValorizacion(row.getCell(config.getValorizacionIdx())));
		operacion.setFechaLiquidacion(getFechaLiquidacion(row.getCell(config.getFechaLiquidacionIdx())));
		operacion.setCantidad(getValorizacion(row.getCell(config.getMontoIdx())));
		operacion.setTipo(OperacionType.ALTA);
		operacion.setSubtipo(OperacionSubType.MONEDA);
		
		return operacion;
	}

	private String getClientCod(HSSFCell cell) {
		String clientCod = "";
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			clientCod = cell.getRichStringCellValue().getString();
		}else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			clientCod = String.valueOf(cell.getNumericCellValue());
		}
		return clientCod;
	}

	private Date getFechaLiquidacion(HSSFCell cell) {
		Date fechaLiquidacion = null;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try {
				fechaLiquidacion= df.parse(cell.getRichStringCellValue().toString());
			} catch (ParseException e) {
				throw new RuntimeException("Cell type date "+cell.getCellType(),e);
			}  
		}else{
			throw new RuntimeException("Cell type not supported "+cell.getCellType());
		}
		return fechaLiquidacion;
	}

	private Double getValorizacion(HSSFCell cell) {
		Double valorizacion = null;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
			try {
				valorizacion = nf.parse(cell.getRichStringCellValue().toString()).doubleValue();
			} catch (ParseException e) {
				throw new RuntimeException("Trying to read double ", e);
			}
		}else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			valorizacion = cell.getNumericCellValue();
		}else{
			throw new RuntimeException("Cell type not supported "+cell.getCellType());
		}
		return valorizacion;
	}

	private String getCodigoEspecie(HSSFCell cell) {
		return cell.getRichStringCellValue().getString();
	}

	private Long getClientId(HSSFCell cell) {
		Long clientId = null;
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
			clientId = Long.parseLong(cell.getRichStringCellValue().toString());
		}else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			clientId = new Long((long) cell.getNumericCellValue());
		}else{
			throw new RuntimeException("Cell type not supported "+cell.getCellType());
		}
		return clientId;
	}

	public void with(HSSFRow row){
		if(!isEmpty(row)){
			rows.add(row);
		}
	}

	private boolean isEmpty(HSSFRow row) {
		for (Iterator<HSSFCell> cellIterator = row.cellIterator(); cellIterator
				.hasNext();) {
			HSSFCell cell = cellIterator.next();
			if( HSSFCell.CELL_TYPE_BLANK != cell.getCellType()){
				return false;
			}
		}
		return true;
	}

	class BookConfig {
		Map<String, Integer> titleByPosition = new HashMap<String, Integer>();

		public short getClientIdIdx() {
			return 0;
		}

		public short getFechaLiquidacionIdx() {
			return 5;
		}

		public short getValorizacionIdx() {
			return 2;
		}

		public short getEspecieIdx() {
			return 1;
		}
		
		public short getMontoIdx() {
			return 3;
		}
		
		public short getMonedaIdx() {
			return 4;
		}
		
	}
	class OperationBuilder{
		
	}
}
