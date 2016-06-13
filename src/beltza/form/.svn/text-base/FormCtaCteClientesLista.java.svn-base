package beltza.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Cliente;
import beltza.domain.Dia;
import beltza.domain.Especie;
import beltza.domain.Movimiento;
import beltza.report.BeltzaReportManager;
import beltza.util.JTextFieldLimit;
import beltza.util.UtilGUI;

public class FormCtaCteClientesLista extends JInternalFrame implements BeltzaObserver {

	private static final long serialVersionUID = 1L;
	
	private JCheckBox checkBoxFiltrarCeros;
	private JRadioButton rbFecha;
	private JRadioButton rbFuturo;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel ingresosLabel;
	private JLabel egresosLabel;
	private JTextField textBuscCliente;
	private JXDatePicker spinnerFechaCorteInfo;
	private JXDatePicker spinnerFechaBusq;

	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JTable tableEgreso;
	private JTable tableIngreso;
	private JTextField textBuscEspecie;

	private final JButton buscarButton;
	
	private static String[] columnas = { "Cliente", "Especie", "Cantidad" };
	private static String[] columnasMovi = { "Fecha", "Cliente", "Especie", "Cantidad", "Precio", "Liq." };

	private static final String TITULO_ACREEDOR = "Saldo Acreedor";
	private static final String TITULO_DEUDOR = "Saldo Deudor";

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormCtaCteClientesLista frame = new FormCtaCteClientesLista();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormCtaCteClientesLista() {
		super();
		setTitle("Cta.Cte. Clientes");
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setMaximizable(true);
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 700, 511);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(0, 100));
		panel.setMinimumSize(new Dimension(0, 0));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);

		buscarButton = new JButton();
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarLista();
			}
		});
		buscarButton.setText("Buscar");
		buscarButton.setBounds(502, 52, 93, 23);
		panel.add(buscarButton);

		final JLabel especieBuscLabel = new JLabel();
		especieBuscLabel.setText("Especie");
		especieBuscLabel.setBounds(22, 23, 54, 14);
		panel.add(especieBuscLabel);

		textBuscEspecie = new JTextField();
		textBuscEspecie.setBounds(93, 20, 79, 20);
		textBuscEspecie.setDocument(new JTextFieldLimit(4, true));
		panel.add(textBuscEspecie);

		final JLabel clienteLabel = new JLabel();
		clienteLabel.setText("Cliente");
		clienteLabel.setBounds(22, 56, 57, 15);
		panel.add(clienteLabel);

		textBuscCliente = new JTextField();
		textBuscCliente.setBounds(93, 53, 80, 21);
		textBuscCliente.setDocument(new JTextFieldLimit(30, true));
		panel.add(textBuscCliente);

		final JLabel fechaCorteInfoLabel = new JLabel();
		fechaCorteInfoLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		fechaCorteInfoLabel.setText("Fecha corte info");
		fechaCorteInfoLabel.setBounds(198, 22, 136, 14);
		panel.add(fechaCorteInfoLabel);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -15);
		spinnerFechaCorteInfo = new JXDatePicker(new Date());
		spinnerFechaCorteInfo.setBounds(340, 19, 120, 20);
		spinnerFechaCorteInfo.setFormats("dd/MM/yyyy");
		spinnerFechaCorteInfo.setDate(cal.getTime());
		panel.add(spinnerFechaCorteInfo);

		spinnerFechaBusq = new JXDatePicker(new Date());
		spinnerFechaBusq.setBounds(340, 53, 120, 20);
		spinnerFechaBusq.setFormats("dd/MM/yyyy");
		spinnerFechaBusq.setEnabled(false);
		panel.add(spinnerFechaBusq);

		rbFuturo = new JRadioButton();
		rbFuturo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spinnerFechaBusq.setEnabled(false);
			}
		});
		buttonGroup.add(rbFuturo);
		rbFuturo.setOpaque(false);
		rbFuturo.setSelected(true);
		rbFuturo.setText("Futuro");
		rbFuturo.setBounds(179, 52, 85, 23);
		panel.add(rbFuturo);

		rbFecha = new JRadioButton();
		rbFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spinnerFechaBusq.setEnabled(true);
			}
		});
		buttonGroup.add(rbFecha);
		rbFecha.setOpaque(false);
		rbFecha.setText("Fecha");
		rbFecha.setBounds(264, 52, 72, 23);
		panel.add(rbFecha);

		checkBoxFiltrarCeros = new JCheckBox();
		checkBoxFiltrarCeros.setSelected(true);
		checkBoxFiltrarCeros.setOpaque(false);
		checkBoxFiltrarCeros.setText("Filtrar ceros");
		checkBoxFiltrarCeros.setBounds(502, 19, 182, 23);
		panel.add(checkBoxFiltrarCeros);

		final JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(0, 0));
		panel_1.setLayout(null);
		panel_1.setPreferredSize(new Dimension(600, 20));
		panel_1.setBackground(new Color(102, 205, 170));
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		panel_2.setBackground(Color.PINK);
		getContentPane().add(panel_2, BorderLayout.CENTER);

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(1, 2));
		panel_2.add(panel_3, BorderLayout.CENTER);

		final JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BorderLayout());
		panel_3.add(panel_4);

		ingresosLabel = new JLabel();
		ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ingresosLabel.setFont(new Font("", Font.BOLD, 12));
		ingresosLabel.setText(TITULO_DEUDOR);
		panel_4.add(ingresosLabel, BorderLayout.NORTH);

		final JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(10, 0));
		panel_6.setMinimumSize(new Dimension(10, 0));
		panel_6.setLayout(null);
		panel_6.setBackground(Color.ORANGE);
		panel_4.add(panel_6, BorderLayout.WEST);

		final JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.GRAY);
		panel_4.add(panel_8, BorderLayout.EAST);

		scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);

		tableEgreso = new JTable();
		tableEgreso.setDefaultRenderer(Double.class, new DecimalRenderer("###,##0.00"));
		tableEgreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableEgreso.getSelectedRow() >= 0) {
						if (tableEgreso.getModel() instanceof CtaCteTableModel)
							showMovimientosNoLiq(tableEgreso, e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK);
						else
							actualizarLista();
					}
			}
		});
		scrollPane.setViewportView(tableEgreso);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		panel_3.add(panel_5);

		egresosLabel = new JLabel();
		egresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
		egresosLabel.setFont(new Font("", Font.BOLD, 12));
		egresosLabel.setText(TITULO_ACREEDOR);
		panel_5.add(egresosLabel, BorderLayout.NORTH);

		final JPanel panel_7 = new JPanel();
		panel_7.setMinimumSize(new Dimension(10, 0));
		panel_7.setPreferredSize(new Dimension(10, 0));
		panel_7.setLayout(null);
		panel_7.setBackground(Color.ORANGE);
		panel_5.add(panel_7, BorderLayout.EAST);

		final JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_5.add(panel_9, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);

		tableIngreso = new JTable();
		tableIngreso.setDefaultRenderer(Double.class, new DecimalRenderer("###,##0.00"));

		tableIngreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableIngreso.getSelectedRow() >= 0) {
						if (tableIngreso.getModel() instanceof CtaCteTableModel)
							showMovimientosNoLiq(tableIngreso, e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK);
						else
							actualizarLista();
					}
			}
		});
		scrollPane_1.setViewportView(tableIngreso);

		BeltzaApplication.getModel().addObserver(this, ChangeType.MOVIMIENTO);
	}

	class DecimalRenderer extends DefaultTableCellRenderer {
		private final NumberFormat formatter;

		public DecimalRenderer() {
			this.formatter = NumberFormat.getNumberInstance();
			setHorizontalAlignment(JLabel.RIGHT);
		}

		public DecimalRenderer(String format) {
			if (format == null) {
				throw new IllegalArgumentException("format cannot be null");
			}
			this.formatter = new DecimalFormat(format);
			setHorizontalAlignment(JLabel.RIGHT);
		}

		protected void setValue(Object value) {
			if (value instanceof Number) {
				value = formatter.format(value);
			}
			super.setValue(value);
		}
	}

	class CtaCteTableModel extends DefaultTableModel {
		private String[] columnNames = columnas;
		private Object[] data;

		public CtaCteTableModel() {
			super();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			if (data == null)
				return 0;
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			switch (col) {
			case 0:
				return ((Map) data[row]).get("COD_CLIENTE");
			case 1:
				return ((Map) data[row]).get("COD_ESPECIE");
			case 2:
				return Math.abs((Double) ((Map) data[row]).get("CANTIDAD"));
			default:
				return "";
			}
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public void setData(Object[] v) {
			data = v;
		}

		public Object getDataRow(int row) {
			return data[row];
		}

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	class MovTableModel extends DefaultTableModel {
		private String[] columnNames = columnasMovi;
		private Object[] data;

		public MovTableModel() {
			super();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			if (data == null)
				return 0;
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			switch (col) {
			case 0:
				return ((Movimiento) data[row]).getDia().getFechaAsociada();
			case 1:
				if (((Movimiento) data[row]).getOperacion() != null && ((Movimiento) data[row]).getOperacion().getCliente() != null)
					return ((Movimiento) data[row]).getOperacion().getCliente().getCodigo();
				return "";
			case 2:
				if (((Movimiento) data[row]).getEspecie() != null)
					return ((Movimiento) data[row]).getEspecie().getCodigo();
				return "";
			case 3:
				return Math.abs(((Movimiento) data[row]).getCantidad());
			case 4:
				return ((Movimiento) data[row]).getValorizacion();
			case 5:
				return ((Movimiento) data[row]).getLiquidado();
			default:
				return "";
			}
		}

		public Class getColumnClass(int c) {
			if (getValueAt(0, c) == null)
				return String.class;
			return getValueAt(0, c).getClass();
		}

		public void setData(Object[] v) {
			data = v;
		}

		public Object getDataRow(int row) {
			return data[row];
		}

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private void actualizarLista() {
		egresosLabel.setText(TITULO_ACREEDOR);
		ingresosLabel.setText(TITULO_DEUDOR);

		Especie especie = BeltzaApplication.getModel().getEspecieByCodigo(textBuscEspecie.getText().trim());
		Cliente cliente = BeltzaApplication.getModel().getClienteByCodigo(textBuscCliente.getText().trim());

		Collection result = null;

		// ACREEDOR
		if (rbFuturo.isSelected())
			result = BeltzaApplication.getModel().searchMovimientosConsolidados(especie, cliente, -1, checkBoxFiltrarCeros.isSelected());
		else
			result = BeltzaApplication.getModel().searchMovimientosConsolidadosByFechaLiq(especie, cliente, -1, spinnerFechaBusq.getDate(), checkBoxFiltrarCeros.isSelected());

		final CtaCteTableModel dm = new CtaCteTableModel();
		dm.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableIngreso.setModel(dm);
			}
		});

		// DEUDOR
		if (rbFuturo.isSelected())
			result = BeltzaApplication.getModel().searchMovimientosConsolidados(especie, cliente, 1, checkBoxFiltrarCeros.isSelected());
		else
			result = BeltzaApplication.getModel().searchMovimientosConsolidadosByFechaLiq(especie, cliente, 1, spinnerFechaBusq.getDate(), checkBoxFiltrarCeros.isSelected());

		final CtaCteTableModel egresoTM = new CtaCteTableModel();
		egresoTM.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableEgreso.setModel(egresoTM);
			}
		});
		buscarButton.setText("Buscar");
	}

	public void modelChanged(BeltzaEvent event) {
		if (this.isVisible() && this.isSelected()) {
			actualizarLista();
		} else {
			buscarButton.setText("Buscar *");
		}
	}

	private void showMovimientosNoLiq(JTable table, Boolean alt) {
		String clienteCod = (String) ((Map) ((CtaCteTableModel) table.getModel()).getDataRow(table.getSelectedRow())).get("COD_CLIENTE");
		String especieCod = (String) ((Map) ((CtaCteTableModel) table.getModel()).getDataRow(table.getSelectedRow())).get("COD_ESPECIE");
		Double saldo = (Double) ((Map) ((CtaCteTableModel) table.getModel()).getDataRow(table.getSelectedRow())).get("CANTIDAD");
		Date fechaCorte = spinnerFechaCorteInfo != null ? spinnerFechaCorteInfo.getDate() : null;

		try {
			Dia dia = BeltzaApplication.getModel().getDiaAbierto();
			if (dia != null)
				if (alt)
					BeltzaReportManager.getInstance().generarReporteMovsClienteDobleClickAlt(dia, clienteCod, especieCod, fechaCorte);
				else
					BeltzaReportManager.getInstance().generarReporteMovsClienteDobleClick(dia, clienteCod, especieCod, fechaCorte);
			else
				JOptionPane.showMessageDialog(null, "No hay ning\u00FAn d\u00EDa abierto.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Antes se mostraba en la misma grilla
		if (false) {
			String desc = "Movimientos " + especieCod + " de " + clienteCod + " (Saldo = " + UtilGUI.numberFormat().format(saldo.doubleValue()) + ")";
			if (table.equals(tableIngreso))
				egresosLabel.setText(desc);
			else
				ingresosLabel.setText(desc);

			Collection result = BeltzaApplication.getModel().searchMovimientosNoLiqByCliente(clienteCod, especieCod);

			MovTableModel tm = new MovTableModel();
			tm.setData(result.toArray());

			table.setModel(tm);
		}

	}
}
