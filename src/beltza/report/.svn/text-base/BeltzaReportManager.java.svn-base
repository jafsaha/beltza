package beltza.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import beltza.BeltzaApplication;
import beltza.db.ConnectionManager;
import beltza.domain.Dia;

public class BeltzaReportManager {

	private final String REPORTS_FILE_NAME = "reports";

	private static BeltzaReportManager instance;

	private List<String> reportList = new ArrayList<String>();

	private HashMap reports;

	private String nameReporteMovsClientes;

	private String nameReporteMovsClientesAlt;

	protected ConnectionManager connManager;

	private String nameReporteMovsCtasCtesEspeciePorCliente;

	private BeltzaReportManager() {
		super();
		reports = new HashMap();
	}

	public static synchronized BeltzaReportManager getInstance() {
		if (instance == null) {
			instance = new BeltzaReportManager();
		}
		return instance;
	}

	public synchronized void registerReport(String name, String urlTemplate) throws Exception {
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(urlTemplate);
		reports.put(name, jasperReport);
	}

	public synchronized void registerReport(String name, InputStream urlTemplate) throws Exception {
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(urlTemplate);
		reports.put(name, jasperReport);
	}

	public synchronized void registerReports(Properties prop) throws Exception {
		Enumeration e = prop.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			registerReport(key, value);
		}
	}

	private void generatePdf(String name, Map parameters, OutputStream out, JasperPrint jp) throws Exception {

		JasperExportManager.exportReportToPdfStream(jp, out);
	}

	private void generateHtml(String name, Map parameters, OutputStream out, JasperPrint jp) throws Exception {

		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
	}

	private void generateCsv(String name, Map parameters, OutputStream out, JasperPrint jp) throws Exception {

		JRCsvExporter exporter = new JRCsvExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
	}

	public void generatePdf(String name, Map parameters, JRDataSource ds, OutputStream out) throws Exception {

		generatePdf(name, parameters, out, fillReport(name, parameters, ds));
	}

	public void generateHtml(String name, Map parameters, JRDataSource ds, OutputStream out) throws Exception {

		generateHtml(name, parameters, out, fillReport(name, parameters, ds));
	}

	public void generateCsv(String name, Map parameters, JRDataSource ds, OutputStream out) throws Exception {

		generateCsv(name, parameters, out, fillReport(name, parameters, ds));
	}

	public void generatePdf(String name, Map parameters, OutputStream out) throws Exception {

		generatePdf(name, parameters, out, fillReport(name, parameters));
	}

	public void generateWithPreview(String name, Map parameters) throws Exception {

		JasperPrint print = fillReport(name, parameters);
		JasperViewer.viewReport(print, false);
		JasperExportManager.exportReportToPdf(print);
	}

	public void generateHtml(String name, Map parameters, OutputStream out) throws Exception {

		generateHtml(name, parameters, out, fillReport(name, parameters));
	}

	public void generateCsv(String name, Map parameters, OutputStream out) throws Exception {

		generateCsv(name, parameters, out, fillReport(name, parameters));
	}

	private JasperPrint fillReport(String name, Map parameters) throws Exception {
		return JasperFillManager.fillReport((JasperReport) reports.get(name), parameters, connManager.getConnection());
	}

	private JasperPrint fillReport(String name, Map parameters, JRDataSource data) throws Exception {
		return JasperFillManager.fillReport((JasperReport) reports.get(name), parameters, data);
	}

	public void initialize(ConnectionManager conManager) {
		this.connManager = conManager;
		try {
			ResourceBundle prop = ResourceBundle.getBundle(REPORTS_FILE_NAME);
			Enumeration keys = prop.getKeys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String report = prop.getString(key);
				InputStream is = getClass().getResourceAsStream(report);
				this.registerReport(key, is);
				reportList.add(key);
			}
			Collections.sort(reportList);

			// Agrego el reporte particular MovsCtasCtesDClick
			ResourceBundle bundle = ResourceBundle.getBundle(BeltzaApplication.APPLICATION_FILE_NAME);
			String key = bundle.getString("movsCtasCtesDClick.name");
			String report = bundle.getString("movsCtasCtesDClick.path");
			nameReporteMovsClientes = key;
			InputStream is = getClass().getResourceAsStream(report);
			this.registerReport(key, is);

			// Agrego el reporte particular MovsCtasCtesDClickTodos
			bundle = ResourceBundle.getBundle(BeltzaApplication.APPLICATION_FILE_NAME);
			key = bundle.getString("movsCtasCtesDClickAlt.name");
			report = bundle.getString("movsCtasCtesDClickAlt.path");
			nameReporteMovsClientesAlt = key;
			is = getClass().getResourceAsStream(report);
			this.registerReport(key, is);
			
			// Agrego el reporte particular MovsCtasCtesEspeciePorCliente
			bundle = ResourceBundle.getBundle(BeltzaApplication.APPLICATION_FILE_NAME);
			key = bundle.getString("movsCtasCtesEspeciePorCliente.name");
			report = bundle.getString("movsCtasCtesEspeciePorCliente.path");
			nameReporteMovsCtasCtesEspeciePorCliente = key;
			is = getClass().getResourceAsStream(report);
			this.registerReport(key, is);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarReportesBeltza(String name, Dia dia) throws Exception {
		HashMap param = new HashMap();
		param.put("DiaId", dia.getId());
		param.put("FechaOperacion", dia.getFechaAsociada());
		param.put("DiaAbierto", dia.isAbierto());

		generateWithPreview(name, param);
	}

	public void generarReporteMovsClienteDobleClick(Dia dia, String cliente, String especie, Date fechaCorte) throws Exception {
		HashMap param = new HashMap();
		param.put("DiaId", dia.getId());
		param.put("FechaOperacion", dia.getFechaAsociada());
		param.put("DiaAbierto", dia.isAbierto());
		param.put("elCliente", cliente);
		param.put("laEspecie", especie);
		param.put("fechaCorte", fechaCorte);
		param.put("SUBREPORT_DIR", "beltza/report/resources/");

		generateWithPreview(nameReporteMovsClientes, param);
	}

	public void generarReporteMovsClienteDobleClickAlt(Dia dia, String cliente, String especie, Date fechaCorte) throws Exception {
		HashMap param = new HashMap();
		param.put("DiaId", dia.getId());
		param.put("FechaOperacion", dia.getFechaAsociada());
		param.put("DiaAbierto", dia.isAbierto());
		param.put("elCliente", cliente);
		param.put("laEspecie", especie);
		param.put("fechaCorte", fechaCorte);
		param.put("SUBREPORT_DIR", "beltza/report/resources/");
		
		generateWithPreview(nameReporteMovsClientesAlt, param);
	}

	public void generarReporteMovsEspeciePorCliente(Dia dia, String especie, Date fechaCorte) throws Exception {
		HashMap param = new HashMap();
		param.put("DiaId", dia.getId());
		param.put("FechaOperacion", dia.getFechaAsociada());
		param.put("DiaAbierto", dia.isAbierto());
		param.put("laEspecie", especie);
		param.put("fechaCorte", fechaCorte);
		param.put("SUBREPORT_DIR", "beltza/report/resources/");
		
		generateWithPreview(nameReporteMovsCtasCtesEspeciePorCliente, param);
	}
	
	public Object[] getReportNameList() {
		if (reportList == null)
			return null;

		return reportList.toArray();
	}
}
