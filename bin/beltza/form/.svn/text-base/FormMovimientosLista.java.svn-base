package beltza.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Movimiento;
import beltza.domain.OperacionSubType;
import beltza.dto.SearchDateType;
import beltza.dto.SearchMovimientosDTO;
import beltza.util.JTextFieldLimit;
import beltza.util.UtilGUI;

public class FormMovimientosLista extends JInternalFrame implements BeltzaObserver {

	private JComboBox buscLiquidadoComboBox;
	private JXDatePicker spinnerBuscFechaLiqH;
	private JXDatePicker spinnerBuscFechaAltaH;
	private JComboBox fechaLiqComboBox;
	private JXDatePicker spinnerBuscFechaLiqD;
	private JButton desliqEgresoButton;
	private JButton desliqIngresoButton;
	private JComboBox fechaComboBox;
	private JTextField textBuscCliente;
	private JXDatePicker spinnerBuscFechaAltaD;

	private JButton liquidarEgresoButton;
	private JButton liquidarIngresoButton;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JTable tableEgreso;
	private JTable tableIngreso;
	private JTextField textBuscEspecie;
	private JLabel entradaLabel;
	private JLabel salidaLabel;

	private final JButton buscarButton;
	private static String[] columnas = { "Fecha", "Cliente", "Especie", "Cantidad", "Precio", "Liq." };

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormMovimientosLista frame = new FormMovimientosLista();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormMovimientosLista() {
		super();

		setTitle("Liquidacion"); // Ex-Movimientos
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setMaximizable(true);
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 766, 511);

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
		buscarButton.setBounds(661, 19, 93, 23);
		panel.add(buscarButton);

		final JLabel especieBuscLabel = new JLabel();
		especieBuscLabel.setText("Especie");
		especieBuscLabel.setBounds(22, 23, 54, 14);
		panel.add(especieBuscLabel);

		textBuscEspecie = new JTextField();
		textBuscEspecie.setBounds(93, 20, 79, 20);
		textBuscEspecie.setDocument(new JTextFieldLimit(4, true));
		panel.add(textBuscEspecie);

		final JLabel fechaLabel = new JLabel();
		fechaLabel.setText("Fecha Alta");
		fechaLabel.setBounds(192, 23, 79, 14);
		panel.add(fechaLabel);

		spinnerBuscFechaAltaD = new JXDatePicker(new Date());
		spinnerBuscFechaAltaD.setBounds(277, 20, 116, 20);
		spinnerBuscFechaAltaD.setFormats("dd/MM/yyyy");
		panel.add(spinnerBuscFechaAltaD);

		final JLabel clienteLabel = new JLabel();
		clienteLabel.setText("Cliente");
		clienteLabel.setBounds(22, 56, 57, 15);
		panel.add(clienteLabel);

		textBuscCliente = new JTextField();
		textBuscCliente.setBounds(93, 53, 80, 21);
		textBuscCliente.setDocument(new JTextFieldLimit(30, true));
		panel.add(textBuscCliente);

		fechaComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaComboBox.setSelectedIndex(1);
		fechaComboBox.setBounds(408, 19, 100, 22);
		fechaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaAltaChange();
			}
		});
		panel.add(fechaComboBox);

		final JLabel fechaLiqLabel = new JLabel();
		fechaLiqLabel.setText("Fecha Liq.");
		fechaLiqLabel.setBounds(192, 56, 79, 14);
		panel.add(fechaLiqLabel);

		spinnerBuscFechaLiqD = new JXDatePicker(new Date());
		spinnerBuscFechaLiqD.setBounds(277, 53, 116, 20);
		spinnerBuscFechaLiqD.setFormats("dd/MM/yyyy");
		panel.add(spinnerBuscFechaLiqD);

		fechaLiqComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaLiqComboBox.setSelectedIndex(0);
		fechaLiqComboBox.setBounds(408, 52, 100, 22);
		fechaLiqComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaLiqChange();
			}
		});
		panel.add(fechaLiqComboBox);

		spinnerBuscFechaAltaH = new JXDatePicker();
		spinnerBuscFechaAltaH.setBounds(521, 20, 116, 20);
		spinnerBuscFechaAltaH.setFormats("dd/MM/yyyy");
		panel.add(spinnerBuscFechaAltaH);

		spinnerBuscFechaLiqH = new JXDatePicker();
		spinnerBuscFechaLiqH.setBounds(521, 53, 116, 20);
		spinnerBuscFechaLiqH.setFormats("dd/MM/yyyy");
		panel.add(spinnerBuscFechaLiqH);

		buscLiquidadoComboBox = new JComboBox(new String[] { "Todos", "Liquidados", "Sin liquidar" });
		buscLiquidadoComboBox.setSelectedIndex(0);
		buscLiquidadoComboBox.setBounds(654, 52, 100, 22);
		panel.add(buscLiquidadoComboBox);

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

		entradaLabel = new JLabel();
		entradaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		entradaLabel.setFont(new Font("", Font.BOLD, 12));
		entradaLabel.setText("Entrada");
		panel_4.add(entradaLabel, BorderLayout.NORTH);

		final JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(90, 0));
		panel_6.setMinimumSize(new Dimension(90, 0));
		panel_6.setLayout(null);
		panel_6.setBackground(Color.ORANGE);
		panel_4.add(panel_6, BorderLayout.WEST);

		liquidarIngresoButton = new JButton();
		liquidarIngresoButton.setText("Liquidar");
		liquidarIngresoButton.setToolTipText(liquidarIngresoButton.getText());
		liquidarIngresoButton.setBounds(5, 10, 75, 23);
		liquidarIngresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidarMultiple(tableIngreso.getSelectedRows(), (MovTableModel) tableIngreso.getModel());
				// liquidar((Movimiento) ((MovTableModel)
				// tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		liquidarIngresoButton.setEnabled(false);
		panel_6.add(liquidarIngresoButton);

		desliqIngresoButton = new JButton();
		desliqIngresoButton.setText("Desliquidar");
		desliqIngresoButton.setToolTipText(desliqIngresoButton.getText());
		desliqIngresoButton.setBounds(5, 39, 75, 23);
		desliqIngresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidarMultiple(tableIngreso.getSelectedRows(), (MovTableModel) tableIngreso.getModel());
				// liquidar((Movimiento) ((MovTableModel)
				// tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		desliqIngresoButton.setEnabled(false);
		panel_6.add(desliqIngresoButton);

		final JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.GRAY);
		panel_4.add(panel_8, BorderLayout.EAST);

		scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);

		tableIngreso = new JTable();
		scrollPane.setViewportView(tableIngreso);
		tableIngreso.setModel(new MovTableModel());
		tableIngreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}

		});
		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		panel_3.add(panel_5);

		salidaLabel = new JLabel();
		salidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		salidaLabel.setFont(new Font("", Font.BOLD, 12));
		salidaLabel.setText("Salida");
		panel_5.add(salidaLabel, BorderLayout.NORTH);

		final JPanel panel_7 = new JPanel();
		panel_7.setMinimumSize(new Dimension(90, 0));
		panel_7.setPreferredSize(new Dimension(90, 0));
		panel_7.setLayout(null);
		panel_7.setBackground(Color.ORANGE);
		panel_5.add(panel_7, BorderLayout.EAST);

		liquidarEgresoButton = new JButton();
		liquidarEgresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidarMultiple(tableEgreso.getSelectedRows(), (MovTableModel) tableEgreso.getModel());
				// liquidar((Movimiento) ((MovTableModel)
				// tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		liquidarEgresoButton.setText("Liquidar");
		liquidarEgresoButton.setToolTipText(liquidarEgresoButton.getText());
		liquidarEgresoButton.setBounds(5, 10, 75, 23);
		liquidarEgresoButton.setEnabled(false);
		panel_7.add(liquidarEgresoButton);

		desliqEgresoButton = new JButton();
		desliqEgresoButton.setText("Desliquidar");
		desliqEgresoButton.setToolTipText(desliqEgresoButton.getText());
		desliqEgresoButton.setBounds(5, 39, 75, 23);
		desliqEgresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidarMultiple(tableEgreso.getSelectedRows(), (MovTableModel) tableEgreso.getModel());
				// liquidar((Movimiento) ((MovTableModel)
				// tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		desliqEgresoButton.setEnabled(false);
		panel_7.add(desliqEgresoButton);

		final JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_5.add(panel_9, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);

		tableEgreso = new JTable();
		scrollPane_1.setViewportView(tableEgreso);
		tableEgreso.setModel(new MovTableModel());
		tableEgreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}
		});
		// tableEgreso.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		onSelectComboFechaAltaChange();
		onSelectComboFechaLiqChange();

		BeltzaApplication.getModel().addObserver(this, ChangeType.MOVIMIENTO);
		//
	}

	class MovTableModel extends DefaultTableModel {
		private String[] columnNames = columnas;
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
				return ((Movimiento) data[row]).getCantidad();
			case 4:
				if (((Movimiento) data[row]).getOperacion().getSubtipo().equals(OperacionSubType.BONO))
					return ((Movimiento) data[row]).getValorizacion() * 100.0;
				else
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

		ArrayList<Movimiento> movPositivos = new ArrayList<Movimiento>();
		ArrayList<Movimiento> movNegativos = new ArrayList<Movimiento>();

		SearchMovimientosDTO searchDTO = new SearchMovimientosDTO();
		searchDTO.setClienteCod(textBuscCliente.getText().trim().length() > 0 ? textBuscCliente.getText() : null);
		searchDTO.setEspecieCod(textBuscEspecie.getText().trim().length() > 0 ? textBuscEspecie.getText() : null);
		searchDTO.setTipoFechaAlta(fechaComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaAltaDesde(fechaComboBox.getSelectedIndex() > 0 ? spinnerBuscFechaAltaD.getDate() : null);
		searchDTO.setFechaAltaHasta(fechaComboBox.getSelectedIndex() == 2 ? spinnerBuscFechaAltaH.getDate() : null);
		searchDTO.setTipoFechaLiq(fechaLiqComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaLiqComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaLiqDesde(fechaLiqComboBox.getSelectedIndex() > 0 ? spinnerBuscFechaLiqD.getDate() : null);
		searchDTO.setFechaLiqHasta(fechaLiqComboBox.getSelectedIndex() == 2 ? spinnerBuscFechaLiqH.getDate() : null);
		searchDTO.setLiquidado(buscLiquidadoComboBox.getSelectedIndex() > 0 ? buscLiquidadoComboBox.getSelectedIndex() == 1 : null);
		Iterator<Movimiento> it = BeltzaApplication.getModel().searchMovimientos(searchDTO).iterator();
		while (it.hasNext()) {
			Movimiento mov = (Movimiento) it.next();
			if (mov.getCantidad().doubleValue() < 0)
				movNegativos.add(mov);
			else
				movPositivos.add(mov);

		}

		// INGRESO
		final MovTableModel dm = new MovTableModel();
		dm.setData(movPositivos.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableIngreso.setModel(dm);
			}
		});

		// EGRESO
		final MovTableModel egresoTM = new MovTableModel();
		egresoTM.setData(movNegativos.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableEgreso.setModel(egresoTM);
			}
		});
		buscarButton.setText("Buscar");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				actualizarTitulos();
			}
		});

	}

	private void actualizarTitulos() {
		if ((((MovTableModel) tableIngreso.getModel()).getRowCount() + ((MovTableModel) tableEgreso.getModel()).getRowCount()) == 400) {
			if (!entradaLabel.getText().contains(UtilGUI.RECORD_LIMIT_ADD_TEXT)) {
				entradaLabel.setText(entradaLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				entradaLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
				salidaLabel.setText(salidaLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				salidaLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
			}
		} else {
			entradaLabel.setText(entradaLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			entradaLabel.setToolTipText(null);
			salidaLabel.setText(salidaLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			salidaLabel.setToolTipText(null);
		}
	}

	public void modelChanged(BeltzaEvent event) {
		if (ChangeType.MOVIMIENTO.equals(event.getChangeType())) {
			if (this.isVisible() && this.isSelected()) {
				actualizarLista();
			} else {
				buscarButton.setText("Buscar *");
				verifTableSelection();
			}
		}
	}

	private void liquidarMultiple(int[] array, MovTableModel movs) {
		List<Movimiento> movimientos = new ArrayList<Movimiento>();
		for (int i = 0; i < array.length; i++) {
			movimientos.add((Movimiento) ((MovTableModel) movs).getDataRow(array[i]));
		}

		BeltzaApplication.getModel().liquidarDesliquidarMovimientos(movimientos);
	}

	private void verifTableSelection() {
		if (tableIngreso.getSelectedRowCount() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			liquidarIngresoButton.setEnabled(false);
			desliqIngresoButton.setEnabled(false);
		} else {
			Boolean liq = null;
			int pos = 0;
			for (int i = 0; i < tableIngreso.getSelectedRowCount(); i++) {
				pos = tableIngreso.getSelectedRows()[i];
				Movimiento m = ((Movimiento) ((MovTableModel) tableIngreso.getModel()).getDataRow(pos));
				if (liq == null) {
					liq = m.getLiquidado();
				} else {
					if (!(liq.booleanValue() == m.getLiquidado().booleanValue())) {
						liq = null;
						break;
					}
				}
			}
			liquidarIngresoButton.setEnabled(liq != null && !liq);
			desliqIngresoButton.setEnabled(liq != null && liq);
		}
		if (tableEgreso.getSelectedRowCount() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			liquidarEgresoButton.setEnabled(false);
			desliqEgresoButton.setEnabled(false);
		} else {
			Boolean liq = null;
			int pos = 0;
			for (int i = 0; i < tableEgreso.getSelectedRowCount(); i++) {
				pos = tableEgreso.getSelectedRows()[i];
				Movimiento m = ((Movimiento) ((MovTableModel) tableEgreso.getModel()).getDataRow(pos));
				if (liq == null) {
					liq = m.getLiquidado();
				} else {
					if (!(liq.booleanValue() == m.getLiquidado().booleanValue())) {
						liq = null;
						break;
					}
				}
			}
			liquidarEgresoButton.setEnabled(liq != null && !liq);
			desliqEgresoButton.setEnabled(liq != null && liq);
		}
	}

	private void onSelectComboFechaAltaChange() {
		spinnerBuscFechaAltaD.setEnabled(fechaComboBox.getSelectedIndex() > 0);
		spinnerBuscFechaAltaH.setEnabled(fechaComboBox.getSelectedIndex() == 2);
	}

	private void onSelectComboFechaLiqChange() {
		spinnerBuscFechaLiqD.setEnabled(fechaLiqComboBox.getSelectedIndex() > 0);
		spinnerBuscFechaLiqH.setEnabled(fechaLiqComboBox.getSelectedIndex() == 2);
	}

}
