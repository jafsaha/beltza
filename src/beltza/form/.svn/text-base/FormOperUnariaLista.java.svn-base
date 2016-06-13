package beltza.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.BeltzaBusinessException;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Especie;
import beltza.domain.Operacion;
import beltza.domain.OperacionSubType;
import beltza.domain.OperacionType;
import beltza.dto.OperacionDTO;
import beltza.dto.SearchDateType;
import beltza.dto.SearchOperacionesDTO;
import beltza.util.FocusTraversalOnArray;
import beltza.util.JTextFieldLimit;
import beltza.util.UtilGUI;

public class FormOperUnariaLista extends JInternalFrame implements BeltzaObserver {

	private JComboBox fechaLiqComboBox;
	private JXDatePicker spinnerBuscFLiqH;
	private JXDatePicker spinnerBuscFechaAltaH;
	private JComboBox fechaAltaComboBox;
	private static final long serialVersionUID = 1L;
	private JLabel egresosLabel;
	private JLabel ingresosLabel;
	private JRadioButton movimientosCtaCteRadioButton;
	private JRadioButton ingresosEgresosRadioButton;
	private static String TYPE_SELECTED_IE = "ie";
	private static String TYPE_SELECTED_MOVCTACTE = "movCtaCte";

	private ButtonGroup buttonGroupBusc = new ButtonGroup();
	private JLabel clienteLabel;
	private JTextField textCliente;
	private JLabel notasLabel;
	private JButton cancelarButton;
	private JLabel cantidadLabel;
	private JLabel especieLabel;
	private JXDatePicker spinnerBuscFechaAltaD;
	private JXDatePicker spinnerBuscFLiqD;
	private Operacion operacionSelectedToEdit;

	private JTextField textNotas;
	private JButton liquidarEgresoButton;
	private JButton liquidarIngresoButton;
	private JButton delEgresosButton;
	private JButton delIngresosButton;
	private JRadioButton egresoRadioButton;
	private JRadioButton ingresoRadioButton;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JFormattedTextField textCantidad;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JTable tableEgreso;
	private JTable tableIngreso;
	private JButton agregarButton;
	private JCheckBox liquidadoCheckBox;
	private JTextField textEspecie;
	private JTextField textBuscEspecie;
	private JTextField textBuscCliente;
	private JCheckBox mantenerDatosCheckBox;

	private final JButton buscarButton;

	private static String[] columnas = { "Fecha", "Cliente", "Notas", "Especie", "Cantidad", "Liq." };
	// Variable creada para demo
	private Object[][] datos = {};

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormOperUnariaLista frame = new FormOperUnariaLista();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormOperUnariaLista() {
		super();
		setTitle("Alta Movimientos");
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				validar();
			}
		});
		setMaximizable(true);
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 766, 511);

		final JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(null);
		panelBusqueda.setPreferredSize(new Dimension(0, 100));
		panelBusqueda.setMinimumSize(new Dimension(0, 0));
		panelBusqueda.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelBusqueda, BorderLayout.NORTH);

		buscarButton = new JButton();
		buscarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarLista();
			}
		});
		buscarButton.setText("Buscar");
		buscarButton.setBounds(651, 10, 93, 23);
		panelBusqueda.add(buscarButton);

		final JLabel especieBuscLabel = new JLabel();
		especieBuscLabel.setText("Especie");
		especieBuscLabel.setBounds(5, 14, 54, 14);
		panelBusqueda.add(especieBuscLabel);

		textBuscEspecie = new JTextField();
		textBuscEspecie.setBounds(59, 11, 57, 20);
		textBuscEspecie.setDocument(new JTextFieldLimit(4, true));
		panelBusqueda.add(textBuscEspecie);

		final JLabel clienteBuscLabel = new JLabel();
		clienteBuscLabel.setText("Cliente");
		clienteBuscLabel.setBounds(5, 44, 54, 14);
		panelBusqueda.add(clienteBuscLabel);

		textBuscCliente = new JTextField();
		textBuscCliente.setBounds(59, 41, 57, 20);
		textBuscCliente.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				UtilGUI.setJTextFieldToUppercase(textBuscCliente);
			}
		});
		panelBusqueda.add(textBuscCliente);

		final JLabel fechaLabel = new JLabel();
		fechaLabel.setText("Fecha Alta");
		fechaLabel.setBounds(191, 14, 65, 14);
		panelBusqueda.add(fechaLabel);

		spinnerBuscFechaAltaD = new JXDatePicker(new Date());
		spinnerBuscFechaAltaD.setBounds(257, 11, 116, 20);
		spinnerBuscFechaAltaD.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFechaAltaD);

		final JLabel fechaLiqBuscLabel = new JLabel();
		fechaLiqBuscLabel.setText("Fecha Liq.");
		fechaLiqBuscLabel.setBounds(191, 44, 65, 14);
		panelBusqueda.add(fechaLiqBuscLabel);

		spinnerBuscFLiqD = new JXDatePicker(new Date());
		spinnerBuscFLiqD.setBounds(257, 41, 116, 20);
		spinnerBuscFLiqD.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFLiqD);

		ingresosEgresosRadioButton = new JRadioButton();
		buttonGroupBusc.add(ingresosEgresosRadioButton);
		ingresosEgresosRadioButton.setForeground(new Color(0, 0, 255));
		ingresosEgresosRadioButton.setFont(new Font("", Font.BOLD, 12));
		ingresosEgresosRadioButton.setOpaque(false);
		ingresosEgresosRadioButton.setText("Ingresos / Egresos");
		ingresosEgresosRadioButton.setBounds(375, 68, 179, 23);
		ingresosEgresosRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		ingresosEgresosRadioButton.setVisible(false);
		panelBusqueda.add(ingresosEgresosRadioButton);

		final JLabel verLabel = new JLabel();
		verLabel.setFont(new Font("", Font.BOLD, 12));
		verLabel.setText("Ver");
		verLabel.setBounds(22, 71, 54, 14);
		panelBusqueda.add(verLabel);

		movimientosCtaCteRadioButton = new JRadioButton();
		movimientosCtaCteRadioButton.setSelected(true);
		buttonGroupBusc.add(movimientosCtaCteRadioButton);
		movimientosCtaCteRadioButton.setForeground(new Color(0, 0, 255));
		movimientosCtaCteRadioButton.setFont(new Font("", Font.BOLD, 12));
		movimientosCtaCteRadioButton.setOpaque(false);
		movimientosCtaCteRadioButton.setText("Movimientos Cta. Cte.");
		movimientosCtaCteRadioButton.setBounds(80, 68, 221, 23);
		movimientosCtaCteRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		panelBusqueda.add(movimientosCtaCteRadioButton);

		fechaAltaComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaAltaComboBox.setSelectedIndex(1);
		fechaAltaComboBox.setBounds(379, 10, 93, 22);
		fechaAltaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaAltaChange();
			}
		});
		panelBusqueda.add(fechaAltaComboBox);

		spinnerBuscFechaAltaH = new JXDatePicker();
		spinnerBuscFechaAltaH.setBounds(477, 11, 116, 20);
		panelBusqueda.add(spinnerBuscFechaAltaH);

		spinnerBuscFLiqH = new JXDatePicker();
		spinnerBuscFLiqH.setBounds(477, 41, 116, 20);
		panelBusqueda.add(spinnerBuscFLiqH);

		fechaLiqComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaLiqComboBox.setSelectedIndex(0);
		fechaLiqComboBox.setBounds(379, 40, 93, 22);
		fechaLiqComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaLiqChange();
			}
		});
		panelBusqueda.add(fechaLiqComboBox);

		final JPanel panel_1 = new JPanel();
		panel_1.setFocusCycleRoot(true);
		panel_1.setMinimumSize(new Dimension(0, 0));
		panel_1.setLayout(null);
		panel_1.setPreferredSize(new Dimension(600, 100));
		panel_1.setBackground(new Color(102, 205, 170));
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		agregarButton = new JButton();
		agregarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOrUpdateOperacion();
			}
		});
		agregarButton.setText("Agregar");
		agregarButton.setBounds(572, 10, 93, 23);
		panel_1.add(agregarButton);

		mantenerDatosCheckBox = new JCheckBox("Mantener datos");
		mantenerDatosCheckBox.setSelected(true);
		mantenerDatosCheckBox.setBounds(672, 10, 120, 23);
		mantenerDatosCheckBox.setOpaque(false);
		mantenerDatosCheckBox.setVisible(false);
		panel_1.add(mantenerDatosCheckBox);

		textEspecie = new JTextField();
		textEspecie.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				UtilGUI.setJTextFieldToUppercase(textEspecie);
			}
		});
		textEspecie.setBounds(91, 35, 70, 20);
		textEspecie.setDocument(new JTextFieldLimit(4, true));
		panel_1.add(textEspecie);

		liquidadoCheckBox = new JCheckBox();
		liquidadoCheckBox.setOpaque(false);
		liquidadoCheckBox.setText("Liquidado");
		liquidadoCheckBox.setBounds(330, 63, 113, 23);
		panel_1.add(liquidadoCheckBox);

		especieLabel = new JLabel();
		especieLabel.setText("Especie");
		especieLabel.setBounds(17, 38, 54, 14);
		panel_1.add(especieLabel);

		cantidadLabel = new JLabel();
		cantidadLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cantidadLabel.setText("Cantidad");
		cantidadLabel.setBounds(167, 38, 82, 14);
		panel_1.add(cantidadLabel);

		textCantidad = new JFormattedTextField(UtilGUI.numberFormat());
		textCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		textCantidad.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
			}
		});
		textCantidad.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (textCantidad.getText().length() == 0)
					textCantidad.setText("0");
			}
		});
		textCantidad.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textCantidad.setBounds(255, 35, 132, 20);
		panel_1.add(textCantidad);

		ingresoRadioButton = new JRadioButton();
		ingresoRadioButton.setOpaque(false);
		ingresoRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				validar();
			}
		});
		buttonGroup.add(ingresoRadioButton);
		ingresoRadioButton.setText("Ingreso");
		ingresoRadioButton.setBounds(416, 10, 132, 23);
		panel_1.add(ingresoRadioButton);

		egresoRadioButton = new JRadioButton();
		egresoRadioButton.setOpaque(false);
		egresoRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				validar();
			}
		});
		buttonGroup.add(egresoRadioButton);
		egresoRadioButton.setText("Egreso");
		egresoRadioButton.setBounds(416, 35, 132, 23);
		panel_1.add(egresoRadioButton);

		cancelarButton = new JButton();
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initAdd();
			}
		});
		cancelarButton.setText("Cancelar");
		cancelarButton.setBounds(572, 35, 93, 23);
		panel_1.add(cancelarButton);

		notasLabel = new JLabel();
		notasLabel.setText("Notas");
		notasLabel.setBounds(17, 67, 54, 14);
		panel_1.add(notasLabel);

		textNotas = new JTextField();
		textNotas.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
			}
		});
		textNotas.setBounds(91, 64, 210, 20);
		panel_1.add(textNotas);

		clienteLabel = new JLabel();
		clienteLabel.setText("Cliente");
		clienteLabel.setBounds(17, 11, 63, 14);
		panel_1.add(clienteLabel);

		textCliente = new JTextField();
		textCliente.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				UtilGUI.setJTextFieldToUppercase(textCliente);
			}
		});
		textCliente.setBounds(91, 8, 169, 20);
		panel_1.add(textCliente);

		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textCliente, textEspecie, textCantidad, textNotas }));

		// panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new
		// Component[] { textCliente, textEspecie, textCantidad, textNotas,
		// liquidadoCheckBox,
		// ingresoRadioButton, agregarButton, especieLabel, cantidadLabel,
		// egresoRadioButton, cancelarButton, notasLabel, clienteLabel }));

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
		ingresosLabel.setText("Ingresos");
		panel_4.add(ingresosLabel, BorderLayout.NORTH);

		final JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(90, 0));
		panel_6.setMinimumSize(new Dimension(90, 0));
		panel_6.setLayout(null);
		panel_6.setBackground(Color.ORANGE);
		panel_4.add(panel_6, BorderLayout.WEST);

		delIngresosButton = new JButton();
		delIngresosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarOperacion((Operacion) ((IETableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		delIngresosButton.setBounds(5, 5, 75, 23);
		delIngresosButton.setText("Borrar");
		delIngresosButton.setEnabled(false);
		panel_6.add(delIngresosButton);

		liquidarIngresoButton = new JButton();
		liquidarIngresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((IETableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		liquidarIngresoButton.setText("Liquidar");
		liquidarIngresoButton.setBounds(5, 34, 75, 23);
		liquidarIngresoButton.setEnabled(false);
		panel_6.add(liquidarIngresoButton);

		final JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.GRAY);
		panel_4.add(panel_8, BorderLayout.EAST);

		scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);

		tableIngreso = new JTable();
		scrollPane.setViewportView(tableIngreso);
		tableIngreso.setModel(new IETableModel());
		tableIngreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableIngreso.getSelectedRow() < 0) {
						prepareAdd(OperacionType.INGRESO);
					} else {
						prepareUpdate((Operacion) ((IETableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
					}
			}
		});

		tableIngreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}

		});
		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		panel_3.add(panel_5);

		egresosLabel = new JLabel();
		egresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
		egresosLabel.setFont(new Font("", Font.BOLD, 12));
		egresosLabel.setText("Egresos");
		panel_5.add(egresosLabel, BorderLayout.NORTH);

		final JPanel panel_7 = new JPanel();
		panel_7.setMinimumSize(new Dimension(90, 0));
		panel_7.setPreferredSize(new Dimension(90, 0));
		panel_7.setLayout(null);
		panel_7.setBackground(Color.ORANGE);
		panel_5.add(panel_7, BorderLayout.EAST);

		delEgresosButton = new JButton();
		delEgresosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarOperacion((Operacion) ((IETableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		delEgresosButton.setBounds(5, 5, 75, 23);
		delEgresosButton.setText("Borrar");
		delEgresosButton.setEnabled(false);
		panel_7.add(delEgresosButton);

		liquidarEgresoButton = new JButton();
		liquidarEgresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((IETableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		liquidarEgresoButton.setText("Liquidar");
		liquidarEgresoButton.setBounds(5, 34, 75, 23);
		liquidarEgresoButton.setEnabled(false);
		panel_7.add(liquidarEgresoButton);

		final JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_5.add(panel_9, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);

		tableEgreso = new JTable();
		scrollPane_1.setViewportView(tableEgreso);
		tableEgreso.setModel(new IETableModel());
		tableEgreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableEgreso.getSelectedRow() < 0) {
						prepareAdd(OperacionType.EGRESO);
					} else {
						prepareUpdate((Operacion) ((IETableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
					}
			}

		});
		tableEgreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}

		});

		BeltzaApplication.getModel().addObserver(this, ChangeType.OPERACION);
		//

		onSelectComboFechaAltaChange();
		onSelectComboFechaLiqChange();

		initAdd();
	}

	class IETableModel extends DefaultTableModel {
		private String[] columnNames = columnas;
		private Object[] data;

		public IETableModel() {
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
				return ((Operacion) data[row]).getDia().getFechaAsociada();
			case 1:
				return ((Operacion) data[row]).getCliente() != null ? ((Operacion) data[row]).getCliente().getCodigo() : "";
			case 2:
				return ((Operacion) data[row]).getNotas();
			case 3:
				if (((Operacion) data[row]).getTipo().equals(OperacionType.INGRESO) || ((Operacion) data[row]).getTipo().equals(OperacionType.ALTA))
					return ((Operacion) data[row]).getEspecieEntra().getCodigo();
				else
					return ((Operacion) data[row]).getEspecieSale().getCodigo();
			case 4:
				return ((Operacion) data[row]).getCantidad();
			case 5:
				return ((Operacion) data[row]).isLiquidado();
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

	private void actualizarLista() {
		Collection result = null;

		SearchOperacionesDTO searchDTO = getSearchOperacionesDTO();

		String especieCod = textBuscEspecie.getText().trim().length() > 0 ? textBuscEspecie.getText() : null;
		// INGRESO o ALTA
		if (getTypeSelected().equals(this.TYPE_SELECTED_MOVCTACTE))
			searchDTO.setTipo(OperacionType.ALTA);
		else
			searchDTO.setTipo(OperacionType.INGRESO);
		searchDTO.setEspecieEntraCod(especieCod);
		searchDTO.setEspecieSaleCod(null);
		result = BeltzaApplication.getModel().searchOperaciones(searchDTO);

		final IETableModel dm = new IETableModel();
		dm.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableIngreso.setModel(dm);
			}
		});

		// EGRESO o BAJA
		if (getTypeSelected().equals(this.TYPE_SELECTED_MOVCTACTE))
			searchDTO.setTipo(OperacionType.BAJA);
		else
			searchDTO.setTipo(OperacionType.EGRESO);
		searchDTO.setEspecieEntraCod(null);
		searchDTO.setEspecieSaleCod(especieCod);
		result = BeltzaApplication.getModel().searchOperaciones(searchDTO);

		final IETableModel egresoTM = new IETableModel();
		egresoTM.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tableEgreso.setModel(egresoTM);
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				actualizarTitulos();
			}
		});
		buscarButton.setText("Buscar");
	}

	private void actualizarTitulos() {
		if (((IETableModel) tableIngreso.getModel()).getRowCount() == 200) {
			if (!ingresosLabel.getText().contains(UtilGUI.RECORD_LIMIT_ADD_TEXT)) {
				ingresosLabel.setText(ingresosLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				ingresosLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
			}
		} else {
			ingresosLabel.setText(ingresosLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			ingresosLabel.setToolTipText(null);
		}

		if (((IETableModel) tableEgreso.getModel()).getRowCount() == 200) {
			if (!egresosLabel.getText().contains(UtilGUI.RECORD_LIMIT_ADD_TEXT)) {
				egresosLabel.setText(egresosLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				egresosLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
			}
		} else {
			egresosLabel.setText(egresosLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			egresosLabel.setToolTipText(null);
		}
	}

	
	private void initAdd() {
		textCliente.setText("");
		textEspecie.setText("");
		textCantidad.setText("");
		textNotas.setText("");
		liquidadoCheckBox.setSelected(false);
		agregarButton.setEnabled(false);
		agregarButton.setText("Agregar");
		textEspecie.setEditable(true);
		textCliente.setEditable(true);
		liquidadoCheckBox.setEnabled(true);
		ingresoRadioButton.setEnabled(true);
		egresoRadioButton.setEnabled(true);
		ingresoRadioButton.setText("Ingreso");
		egresoRadioButton.setText("Egreso");
		liquidadoCheckBox.setVisible(true);
		ingresosLabel.setText("Ingresos");
		egresosLabel.setText("Egresos");
		operacionSelectedToEdit = null;
		liquidarIngresoButton.setVisible(true);
		liquidarEgresoButton.setVisible(true);

		if (getTypeSelected().equals(this.TYPE_SELECTED_MOVCTACTE)) {
			ingresoRadioButton.setText("Alta Deudor");
			egresoRadioButton.setText("Alta Acreedor");
			liquidadoCheckBox.setVisible(false);
			ingresosLabel.setText("Altas Deudores");
			egresosLabel.setText("Altas Acreedores");
			liquidarIngresoButton.setVisible(false);
			liquidarEgresoButton.setVisible(false);
		}
	}

	private void initUpdate() {
		initAdd();
		agregarButton.setEnabled(true);
		agregarButton.setText("Modificar");
		textEspecie.setEditable(false);
		textCliente.setEditable(false);
		liquidadoCheckBox.setEnabled(false);
		ingresoRadioButton.setEnabled(false);
		egresoRadioButton.setEnabled(false);
	}

	private void addOrUpdateOperacion() {

		boolean agregar = true;
		boolean limpiarDatos = false;

		// Es modificacion
		if (operacionSelectedToEdit != null && operacionSelectedToEdit.getId() != null) {

			if (operacionSelectedToEdit.getCliente().getCodigo().equals(textCliente.getText())
					&& ((operacionSelectedToEdit.getEspecieEntra() != null && operacionSelectedToEdit.getEspecieEntra().getCodigo()
							.equals(textEspecie.getText())) || (operacionSelectedToEdit.getEspecieSale() != null && operacionSelectedToEdit.getEspecieSale()
							.getCodigo().equals(textEspecie.getText())))) {
				// Modificacion sobre el mismo registro
				agregar = false;

				try {
					operacionSelectedToEdit.setCantidad(new Double(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				operacionSelectedToEdit.setNotas(textNotas.getText());
				BeltzaApplication.getModel().saveOperacion(operacionSelectedToEdit);
				initAdd();
			} else {
				// Elimmino y creo un movimiento nuevo
				BeltzaApplication.getModel().deleteOperacion(operacionSelectedToEdit, false);
				operacionSelectedToEdit = null;
				limpiarDatos = true;
			}
		}

		// Es Alta
		if (agregar) {
			OperacionDTO operacionDTO = new OperacionDTO();
			if (getTypeSelected().equals(this.TYPE_SELECTED_IE)) {
				if (this.ingresoRadioButton.isSelected()) {
					operacionDTO.setTipo(OperacionType.INGRESO);
					operacionDTO.setEspecieEntraCod(textEspecie.getText());
				} else {
					operacionDTO.setTipo(OperacionType.EGRESO);
					operacionDTO.setEspecieSaleCod(textEspecie.getText());
				}
			} else {
				if (this.ingresoRadioButton.isSelected()) {
					operacionDTO.setTipo(OperacionType.ALTA);
					operacionDTO.setEspecieEntraCod(textEspecie.getText());
				} else {
					operacionDTO.setTipo(OperacionType.BAJA);
					operacionDTO.setEspecieSaleCod(textEspecie.getText());
				}
			}
			operacionDTO.setSubtipo(OperacionSubType.NA);
			try {
				operacionDTO.setCantidad(new Double(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			operacionDTO.setLiquidado(liquidadoCheckBox.isSelected());
			operacionDTO.setNotas(textNotas.getText());
			if (textCliente.getText().trim().length() > 0)
				operacionDTO.setClienteCod(textCliente.getText());

			try {
				BeltzaApplication.getModel().addOperacion(operacionDTO);
				if (!mantenerDatosCheckBox.isSelected() || limpiarDatos)
					initAdd();
			} catch (BeltzaBusinessException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	private void prepareAdd(OperacionType operacionType) {
		initAdd();
		if (operacionType.equals(OperacionType.INGRESO))
			this.ingresoRadioButton.setSelected(true);
		else
			this.egresoRadioButton.setSelected(true);
	}

	private void prepareUpdate(Operacion operacion) {
		initUpdate();

		Boolean editSpecial = operacion.getDia().getId().equals(BeltzaApplication.getModel().getDiaAbierto().getId());
		textCliente.setEditable(editSpecial);
		textEspecie.setEditable(editSpecial);

		if (operacion.getTipo().equals(OperacionType.INGRESO) || operacion.getTipo().equals(OperacionType.ALTA)) {
			ingresoRadioButton.setSelected(true);
			textEspecie.setText(operacion.getEspecieEntra().getCodigo());
		} else {
			egresoRadioButton.setSelected(true);
			textEspecie.setText(operacion.getEspecieSale().getCodigo());
		}
		textCliente.setText(operacion.getCliente().getCodigo());
		textCantidad.setText(NumberFormat.getInstance().format(operacion.getCantidad()));
		liquidadoCheckBox.setSelected(operacion.getFechaLiquidacion() != null);
		textNotas.setText(operacion.getNotas());

		// Me guardo la operacion que uso al aceptar
		operacionSelectedToEdit = operacion;
	}

	public void modelChanged(BeltzaEvent event) {
		if (this.isVisible() && this.isSelected()) {
			actualizarLista();
		} else {
			buscarButton.setText("Buscar *");
			verifTableSelection();
		}
	}

	private void validar() {
		this.agregarButton.setEnabled(false);

		try {
			if (this.textCliente.getText().trim().length() == 0)
				return;
			if (this.textEspecie.getText().trim().length() == 0)
				return;
			if (this.textCantidad.getText().trim().length() == 0 || (UtilGUI.numberFormat().parse(textCantidad.getText()).doubleValue() == 0))
				return;
			if (!this.ingresoRadioButton.isSelected() && !this.egresoRadioButton.isSelected())
				return;

			this.agregarButton.setEnabled(true);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void liquidar(Operacion operacion) {
		if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Liquidar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Borrar", JOptionPane.OK_CANCEL_OPTION) == 0)
			BeltzaApplication.getModel().liquidarOperacion(operacion);
	}

	private void borrarOperacion(Operacion operacion) {
		if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Borrar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Borrar", JOptionPane.OK_CANCEL_OPTION) == 0) {
			BeltzaApplication.getModel().deleteOperacion(operacion, true);
			initAdd();
		}
	}

	private void verifTableSelection() {
		if (tableIngreso.getSelectedRow() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			delIngresosButton.setEnabled(false);
			liquidarIngresoButton.setEnabled(false);
		} else {
			delIngresosButton.setEnabled(true);

			liquidarIngresoButton.setEnabled(!((Operacion) ((IETableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow())).isLiquidado());
		}
		if (tableEgreso.getSelectedRow() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			delEgresosButton.setEnabled(false);
			liquidarEgresoButton.setEnabled(false);
		} else {
			delEgresosButton.setEnabled(true);
			liquidarEgresoButton.setEnabled(!((Operacion) ((IETableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow())).isLiquidado());
		}
	}

	private String getTypeSelected() {
		if (ingresosEgresosRadioButton.isSelected())
			return this.TYPE_SELECTED_IE;

		return this.TYPE_SELECTED_MOVCTACTE;
	}

	private void onTypeSelected() {
		initAdd();
		actualizarLista();
	}

	private void onSelectComboFechaAltaChange() {
		spinnerBuscFechaAltaD.setEnabled(fechaAltaComboBox.getSelectedIndex() > 0);
		spinnerBuscFechaAltaH.setEnabled(fechaAltaComboBox.getSelectedIndex() == 2);
	}

	private void onSelectComboFechaLiqChange() {
		spinnerBuscFLiqD.setEnabled(fechaLiqComboBox.getSelectedIndex() > 0);
		spinnerBuscFLiqH.setEnabled(fechaLiqComboBox.getSelectedIndex() == 2);
	}

	private SearchOperacionesDTO getSearchOperacionesDTO() {
		SearchOperacionesDTO searchDTO = new SearchOperacionesDTO();
		searchDTO.setSubtipo(OperacionSubType.NA);
		searchDTO.setClienteCod(textBuscCliente.getText().trim().length() > 0 ? textBuscCliente.getText() : null);
		searchDTO.setTipoFechaAlta(fechaAltaComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaAltaComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaAltaDesde(fechaAltaComboBox.getSelectedIndex() > 0 ? spinnerBuscFechaAltaD.getDate() : null);
		searchDTO.setFechaAltaHasta(fechaAltaComboBox.getSelectedIndex() == 2 ? spinnerBuscFechaAltaH.getDate() : null);

		searchDTO.setTipoFechaLiq(fechaLiqComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaLiqComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaLiqDesde(fechaLiqComboBox.getSelectedIndex() > 0 ? spinnerBuscFLiqD.getDate() : null);
		searchDTO.setFechaLiqHasta(fechaLiqComboBox.getSelectedIndex() == 2 ? spinnerBuscFLiqH.getDate() : null);

		return searchDTO;
	}
}
