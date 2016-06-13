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
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
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
import beltza.form.FormEspeciesList.DecimalRenderer;
import beltza.util.FocusTraversalOnArray;
import beltza.util.JTextFieldLimit;
import beltza.util.UtilGUI;

public class FormOperBinariaLista extends JInternalFrame implements BeltzaObserver {

	private JTextField textBuscEspecieSale;
	private JComboBox fechaLiqComboBox;
	private JComboBox fechaAltaComboBox;
	private JXDatePicker spinnerBuscFLiqH;
	private JXDatePicker spinnerBuscFechaAltaH;
	private JTextField textBuscCliente;
	private JLabel ventasLabel;
	private JLabel comprasLabel;
	private JPanel panelEntrada;
	private static final long serialVersionUID = 1L;
	private JButton desliqEgresoButton;
	private JButton desliqIngresoButton;
	private JPanel panelVentas;
	private JRadioButton canjeRadioButton;
	private JRadioButton ingrEgreRadioButton;
	private JLabel fechaLiqLabel;
	private JLabel clienteLabel;
	private JLabel especieSLabel;
	private JButton cancelarButton;
	private JLabel notaLabel;
	private JLabel precioLabel;
	private JLabel cantidadLabel;
	private JLabel especieLabel;
	private JLabel igualLabel;
	private JTextField textCliente;
	private JTextField textEspeciSale;
	private JXDatePicker spinnerBuscFechaAltaD;
	private JXDatePicker spinnerBuscFLiqD;
	private JXDatePicker spinnerFechaLiq;
	private JTextField textNota;
	private Operacion operacionSelectedToEdit;

	private JButton delEgresosButton;
	private JButton delIngresosButton;
	private JButton liquidarEgresoButton;
	private JButton liquidarIngresoButton;
	private ButtonGroup buttonGroupSubTipo = new ButtonGroup();
	private JFormattedTextField textPrecio;
	private JRadioButton accionRadioButton;
	private JRadioButton bonoRadioButton;
	private JRadioButton billeteRadioButton;
	private JRadioButton monedaRadioButton;
	private JRadioButton ventaRadioButton;
	private JRadioButton compraRadioButton;
	private ButtonGroup buttonGroupTipo = new ButtonGroup();
	private JFormattedTextField textCantidad;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JTable tableEgreso;
	private JTable tableIngreso;
	private JButton agregarButton;
	private JCheckBox liquidadoCheckBox;
	private JTextField textEspecieEntra;
	private JTextField textBuscEspecie;
	private JCheckBox mantenerDatosCheckBox;
	private JPanel panel_3;

	private final JButton buscarButton;
	private static String[] columnas = { "Fecha", "Cliente", "Especie", "Cantidad", "Precio", "Cant*Precio", "Liq." };

	private static String[] columnasCanje = { "Fecha", "Cliente", "Esp. entra", "Cant. entra", "Esp. sale", "Cant. sale", "Liq.", "Fecha Liq.", "Notas" };

	private static String[] columnasIE = { "Fecha", "Cliente", "Notas", "Especie", "Cantidad", "Liq." };

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormOperBinariaLista frame = new FormOperBinariaLista();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormOperBinariaLista() {
		super();
		setTitle("Operaciones Compra-Venta");
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				validar();
			}
		});
//		addInternalFrameListener(new InternalFrameAdapter() {
//			@Override
//			public void internalFrameActivated(InternalFrameEvent arg0) {
//			}
//		});

		setMaximizable(true);
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 760, 538);

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

		final JLabel fechaLabel = new JLabel();
		fechaLabel.setText("Fecha Alta");
		fechaLabel.setBounds(191, 14, 65, 14);
		panelBusqueda.add(fechaLabel);

		spinnerBuscFechaAltaD = new JXDatePicker(new Date());
		spinnerBuscFechaAltaD.setBounds(257, 11, 116, 20);
		spinnerBuscFechaAltaD.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFechaAltaD);

		spinnerBuscFLiqD = new JXDatePicker(new Date());
		spinnerBuscFLiqD.setBounds(257, 41, 116, 20);
		spinnerBuscFLiqD.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFLiqD);

		monedaRadioButton = new JRadioButton();
		monedaRadioButton.setForeground(new Color(0, 0, 255));
		monedaRadioButton.setFont(new Font("", Font.BOLD, 12));
		monedaRadioButton.setOpaque(false);
		monedaRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		monedaRadioButton.setBounds(77, 67, 93, 23);
		panelBusqueda.add(monedaRadioButton);
		buttonGroupSubTipo.add(monedaRadioButton);
		monedaRadioButton.setText("Monedas");
		monedaRadioButton.setMnemonic('D');

		billeteRadioButton = new JRadioButton();
		billeteRadioButton.setForeground(new Color(0, 0, 255));
		billeteRadioButton.setFont(new Font("", Font.BOLD, 12));
		billeteRadioButton.setOpaque(false);
		billeteRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		billeteRadioButton.setBounds(176, 67, 93, 23);
		panelBusqueda.add(billeteRadioButton);
		buttonGroupSubTipo.add(billeteRadioButton);
		billeteRadioButton.setText("Billetes");
		billeteRadioButton.setMnemonic('B');

		class AcceleratorAction extends AbstractAction {
			public AcceleratorAction() {
				super();
			}

			public void actionPerformed(ActionEvent e) {

			}
		}

		bonoRadioButton = new JRadioButton();
		bonoRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		bonoRadioButton.setForeground(new Color(0, 0, 255));
		bonoRadioButton.setFont(new Font("", Font.BOLD, 12));
		bonoRadioButton.setOpaque(false);
		bonoRadioButton.setBounds(275, 67, 93, 23);
		panelBusqueda.add(bonoRadioButton);
		buttonGroupSubTipo.add(bonoRadioButton);
		bonoRadioButton.setText("Bonos");
		bonoRadioButton.setMnemonic('N');

		accionRadioButton = new JRadioButton();
		accionRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		accionRadioButton.setForeground(new Color(0, 0, 255));
		accionRadioButton.setFont(new Font("", Font.BOLD, 12));
		accionRadioButton.setOpaque(false);
		accionRadioButton.setBounds(374, 67, 93, 23);
		panelBusqueda.add(accionRadioButton);
		buttonGroupSubTipo.add(accionRadioButton);
		accionRadioButton.setText("Acciones");
		accionRadioButton.setMnemonic('A');

		final JLabel verLabel = new JLabel();
		verLabel.setFont(new Font("", Font.BOLD, 12));
		verLabel.setText("Ver");
		// Pedido modificacion para que no muestre nada
		verLabel.setVisible(false);
		verLabel.setBounds(17, 71, 57, 15);
		panelBusqueda.add(verLabel);

		canjeRadioButton = new JRadioButton();
		canjeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		canjeRadioButton.setOpaque(false);
		canjeRadioButton.setMnemonic('J');
		canjeRadioButton.setForeground(new Color(0, 0, 255));
		canjeRadioButton.setFont(new Font("Dialog", Font.BOLD, 12));
		canjeRadioButton.setText("Canjes");
		canjeRadioButton.setBounds(484, 68, 93, 23);
		panelBusqueda.add(canjeRadioButton);
		buttonGroupSubTipo.add(canjeRadioButton);

		ingrEgreRadioButton = new JRadioButton();
		ingrEgreRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTypeSelected();
			}
		});
		ingrEgreRadioButton.setOpaque(false);
		ingrEgreRadioButton.setMnemonic('G');
		ingrEgreRadioButton.setForeground(new Color(0, 0, 255));
		ingrEgreRadioButton.setFont(new Font("Dialog", Font.BOLD, 12));
		ingrEgreRadioButton.setText("Ingreso/Egreso");
		ingrEgreRadioButton.setBounds(584, 68, 160, 23);
		panelBusqueda.add(ingrEgreRadioButton);
		buttonGroupSubTipo.add(ingrEgreRadioButton);

		final JLabel fechaLiqBuscLabel = new JLabel();
		fechaLiqBuscLabel.setText("Fecha Liq.");
		fechaLiqBuscLabel.setBounds(191, 44, 65, 14);
		panelBusqueda.add(fechaLiqBuscLabel);

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

		spinnerBuscFechaAltaH = new JXDatePicker();
		spinnerBuscFechaAltaH.setBounds(483, 11, 116, 20);
		spinnerBuscFechaAltaH.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFechaAltaH);

		spinnerBuscFLiqH = new JXDatePicker();
		spinnerBuscFLiqH.setBounds(483, 41, 116, 20);
		spinnerBuscFLiqH.setFormats("dd/MM/yyyy");
		panelBusqueda.add(spinnerBuscFLiqH);

		fechaAltaComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaAltaComboBox.setSelectedIndex(1);
		fechaAltaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaAltaChange();
			}
		});
		fechaAltaComboBox.setBounds(385, 10, 93, 22);
		panelBusqueda.add(fechaAltaComboBox);

		fechaLiqComboBox = new JComboBox(new String[] { "-Seleccionar-", "Igual", "Hasta" });
		fechaLiqComboBox.setSelectedIndex(0);
		fechaLiqComboBox.setBounds(385, 40, 93, 22);
		fechaLiqComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onSelectComboFechaLiqChange();
			}
		});
		panelBusqueda.add(fechaLiqComboBox);

		textBuscEspecieSale = new JTextField();
		textBuscEspecieSale.setBounds(122, 11, 57, 20);
		textBuscEspecieSale.setDocument(new JTextFieldLimit(4, true));
		textBuscEspecieSale.setVisible(false);
		panelBusqueda.add(textBuscEspecieSale);

		panelEntrada = new JPanel();
		panelEntrada.setFocusCycleRoot(true);
		panelEntrada.setMinimumSize(new Dimension(0, 0));
		panelEntrada.setLayout(null);
		panelEntrada.setPreferredSize(new Dimension(500, 100));
		panelEntrada.setBackground(new Color(102, 205, 170));
		getContentPane().add(panelEntrada, BorderLayout.SOUTH);

		agregarButton = new JButton();
		agregarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOrUpdateOperacion();
			}
		});
		agregarButton.setText("Agregar");
		agregarButton.setBounds(649, 42, 93, 23);
		panelEntrada.add(agregarButton);

		textEspecieEntra = new JTextField();
		textEspecieEntra.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				UtilGUI.setJTextFieldToUppercase(textEspecieEntra);
			}
		});
		textEspecieEntra.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				actualizarSubtotal();
			}
		});

		textEspecieEntra.setBounds(75, 43, 70, 20);
		textEspecieEntra.setDocument(new JTextFieldLimit(4, true));

		panelEntrada.add(textEspecieEntra);

		liquidadoCheckBox = new JCheckBox();
		liquidadoCheckBox.setOpaque(false);
		liquidadoCheckBox.setText("Liquidado");
		liquidadoCheckBox.setBounds(413, 68, 93, 23);
		panelEntrada.add(liquidadoCheckBox);

		especieLabel = new JLabel();
		especieLabel.setText("Especie");
		especieLabel.setBounds(8, 46, 66, 14);
		panelEntrada.add(especieLabel);

		cantidadLabel = new JLabel();
		cantidadLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cantidadLabel.setText("Cantidad");
		cantidadLabel.setBounds(205, 18, 82, 14);
		panelEntrada.add(cantidadLabel);

		textCantidad = new JFormattedTextField(UtilGUI.numberFormat());
		textCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		textCantidad.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (textCantidad.getText().length() == 0)
					textCantidad.setText("0");
				actualizarSubtotal();
			}
		});
		textCantidad.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				if ("0123456789".indexOf(e.getKeyChar()) > -1)
					actualizarSubtotal();
			}
		});
		textCantidad.setBounds(290, 14, 101, 20);
		panelEntrada.add(textCantidad);

		compraRadioButton = new JRadioButton();
		compraRadioButton.setOpaque(false);
		compraRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				validar();
			}
		});
		buttonGroupTipo.add(compraRadioButton);
		compraRadioButton.setText("Compra");
		compraRadioButton.setBounds(538, 43, 93, 23);
		panelEntrada.add(compraRadioButton);

		ventaRadioButton = new JRadioButton();
		ventaRadioButton.setOpaque(false);
		ventaRadioButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				validar();
			}
		});
		buttonGroupTipo.add(ventaRadioButton);
		ventaRadioButton.setText("Venta");
		ventaRadioButton.setBounds(538, 69, 93, 23);
		panelEntrada.add(ventaRadioButton);

		precioLabel = new JLabel();
		precioLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		precioLabel.setText("Precio");
		precioLabel.setBounds(397, 17, 82, 14);
		panelEntrada.add(precioLabel);

		textPrecio = new JFormattedTextField(UtilGUI.numberFormat());
		textPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		textPrecio.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				if (textPrecio.getText().length() == 0)
					textPrecio.setText("0");
				actualizarSubtotal();
			}
		});
		textPrecio.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				if ("0123456789".indexOf(e.getKeyChar()) > -1)
					actualizarSubtotal();
			}
		});
		textPrecio.setBounds(485, 13, 94, 20);
		panelEntrada.add(textPrecio);

		notaLabel = new JLabel();
		notaLabel.setText("Nota");
		notaLabel.setBounds(8, 73, 66, 15);
		panelEntrada.add(notaLabel);

		textNota = new JTextField();
		textNota.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
			}
		});
		textNota.setBounds(75, 69, 299, 21);
		panelEntrada.add(textNota);

		cancelarButton = new JButton();
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initAdd();
			}
		});
		cancelarButton.setText("Cancelar");
		cancelarButton.setBounds(649, 70, 93, 23);
		panelEntrada.add(cancelarButton);

		especieSLabel = new JLabel();
		especieSLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		especieSLabel.setText("Cuenta");
		especieSLabel.setBounds(146, 46, 77, 15);
		panelEntrada.add(especieSLabel);

		textEspeciSale = new JTextField();
		textEspeciSale.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				UtilGUI.setJTextFieldToUppercase(textEspeciSale);
			}
		});
		textEspeciSale.setBounds(229, 43, 70, 20);
		textEspeciSale.setDocument(new JTextFieldLimit(4, true));
		panelEntrada.add(textEspeciSale);

		clienteLabel = new JLabel();
		clienteLabel.setText("Cliente");
		clienteLabel.setBounds(8, 18, 65, 15);
		panelEntrada.add(clienteLabel);

		textCliente = new JTextField();
		textCliente.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
				UtilGUI.setJTextFieldToUppercase(textCliente);
			}
		});
		textCliente.setBounds(75, 15, 124, 20);
		textCliente.setDocument(new JTextFieldLimit(20, false));
		panelEntrada.add(textCliente);

		igualLabel = new JLabel();
		igualLabel.setBounds(585, 16, 157, 14);
		panelEntrada.add(igualLabel);

		fechaLiqLabel = new JLabel();
		fechaLiqLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		fechaLiqLabel.setText("Fecha Liq.");
		fechaLiqLabel.setBounds(300, 46, 86, 14);
		panelEntrada.add(fechaLiqLabel);

		spinnerFechaLiq = new JXDatePicker();
		spinnerFechaLiq.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				validar();
			}
		});
		spinnerFechaLiq.setBounds(390, 43, 116, 20);
		spinnerFechaLiq.setFormats("dd/MM/yyyy");

		panelEntrada.add(spinnerFechaLiq);

		panelEntrada.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textCliente, textCantidad, textPrecio, agregarButton,
				textEspecieEntra, textEspeciSale, spinnerFechaLiq, textNota, liquidadoCheckBox, especieLabel, cantidadLabel, precioLabel, notaLabel,
				ventaRadioButton, cancelarButton, especieSLabel, clienteLabel, igualLabel, fechaLiqLabel }));

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		panel_2.setBackground(Color.PINK);
		getContentPane().add(panel_2, BorderLayout.CENTER);

		panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(1, 2));
		panel_2.add(panel_3, BorderLayout.CENTER);

		final JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BorderLayout());
		panel_3.add(panel_4);

		comprasLabel = new JLabel();
		comprasLabel.setHorizontalAlignment(SwingConstants.CENTER);
		comprasLabel.setFont(new Font("", Font.BOLD, 12));
		comprasLabel.setText("Compras");
		panel_4.add(comprasLabel, BorderLayout.NORTH);

		final JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(90, 0));
		panel_6.setMinimumSize(new Dimension(90, 0));
		panel_6.setMaximumSize(new Dimension(90, 0));
		panel_6.setLayout(null);
		panel_6.setBackground(Color.ORANGE);
		panel_4.add(panel_6, BorderLayout.WEST);

		delIngresosButton = new JButton();
		delIngresosButton.setBounds(5, 5, 75, 23);
		delIngresosButton.setText("Borrar");
		delIngresosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarOperacion((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		delIngresosButton.setEnabled(false);
		panel_6.add(delIngresosButton);

		liquidarIngresoButton = new JButton();
		liquidarIngresoButton.setText("Liquidar");
		liquidarIngresoButton.setToolTipText(liquidarIngresoButton.getText());
		liquidarIngresoButton.setBounds(5, 34, 75, 23);
		liquidarIngresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
			}
		});
		liquidarIngresoButton.setEnabled(false);
		panel_6.add(liquidarIngresoButton);

		desliqIngresoButton = new JButton();
		desliqIngresoButton.setText("Desliquidar");
		desliqIngresoButton.setToolTipText(desliqIngresoButton.getText());
		desliqIngresoButton.setBounds(5, 63, 75, 23);
		desliqIngresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
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
		tableIngreso.setModel(new CVTableModel());
		tableIngreso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableIngreso.setDefaultRenderer(Double.class, new DecimalRenderer(""));
		tableIngreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableIngreso.getSelectedRow() < 0) {
						prepareAdd(OperacionType.COMPRA);
					} else {
						if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE))
							prepareUpdate((Operacion) ((CVCanjeTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
						else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA))
							prepareUpdate((Operacion) ((IETableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
						else
							prepareUpdate((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow()));
					}
			}
		});
		tableIngreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}

		});

		panelVentas = new JPanel();
		panelVentas.setLayout(new BorderLayout());
		panel_3.add(panelVentas);

		ventasLabel = new JLabel();
		ventasLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ventasLabel.setFont(new Font("", Font.BOLD, 12));
		ventasLabel.setText("Ventas");
		panelVentas.add(ventasLabel, BorderLayout.NORTH);

		final JPanel panel_7 = new JPanel();
		panel_7.setMinimumSize(new Dimension(90, 0));
		panel_7.setPreferredSize(new Dimension(90, 0));
		panel_7.setLayout(null);
		panel_7.setBackground(Color.ORANGE);
		panelVentas.add(panel_7, BorderLayout.EAST);

		delEgresosButton = new JButton();
		delEgresosButton.setBounds(5, 5, 75, 23);
		delEgresosButton.setText("Borrar");
		delEgresosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarOperacion((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		delEgresosButton.setEnabled(false);
		panel_7.add(delEgresosButton);

		liquidarEgresoButton = new JButton();
		liquidarEgresoButton.setText("Liquidar");
		liquidarEgresoButton.setToolTipText(liquidarEgresoButton.getText());
		liquidarEgresoButton.setBounds(5, 34, 75, 23);
		liquidarEgresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		liquidarEgresoButton.setEnabled(false);
		panel_7.add(liquidarEgresoButton);

		desliqEgresoButton = new JButton();
		desliqEgresoButton.setText("Desliquidar");
		desliqEgresoButton.setToolTipText(desliqEgresoButton.getText());
		desliqEgresoButton.setBounds(5, 63, 75, 23);
		desliqEgresoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liquidar((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
			}
		});
		desliqEgresoButton.setEnabled(false);
		panel_7.add(desliqEgresoButton);

		final JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panelVentas.add(panel_9, BorderLayout.WEST);

		scrollPane_1 = new JScrollPane();
		panelVentas.add(scrollPane_1, BorderLayout.CENTER);

		
		 
			
		tableEgreso = new JTable();
		scrollPane_1.setViewportView(tableEgreso);
		tableEgreso.setModel(new CVTableModel());
		tableEgreso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEgreso.setDefaultRenderer(Double.class, new DecimalRenderer(""));
		tableEgreso.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tableEgreso.getSelectedRow() < 0) {
						prepareAdd(OperacionType.VENTA);
					} else {
						if (getOperacionSubTypeSelected().equals(OperacionSubType.NA))
							prepareUpdate((Operacion) ((IETableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
						else
							prepareUpdate((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow()));
					}
			}

		});
		tableEgreso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				verifTableSelection();
			}

		});

		mantenerDatosCheckBox = new JCheckBox("Mantener datos");
		mantenerDatosCheckBox.setSelected(true);
		mantenerDatosCheckBox.setBounds(749, 42, 120, 23);
		mantenerDatosCheckBox.setOpaque(false);
		mantenerDatosCheckBox.setVisible(false);
		panelEntrada.add(mantenerDatosCheckBox);

		BeltzaApplication.getModel().addObserver(this, ChangeType.OPERACION);

		actualizarRadios();

		getInputMap().put(KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK), "monedaAction");
		getActionMap().put("monedaAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				monedaRadioButton.setSelected(true);
				onTypeSelected();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK), "billeteAction");
		getActionMap().put("billeteAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				billeteRadioButton.setSelected(true);
				onTypeSelected();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK), "bonoAction");
		getActionMap().put("bonoAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				bonoRadioButton.setSelected(true);
				onTypeSelected();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK), "accionAction");
		getActionMap().put("accionAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				accionRadioButton.setSelected(true);
				onTypeSelected();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke('J', InputEvent.CTRL_MASK), "canjeAction");
		getActionMap().put("canjeAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				canjeRadioButton.setSelected(true);
				onTypeSelected();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK), "ingresoAction");
		getActionMap().put("ingresoAction", new AcceleratorAction() {
			public void actionPerformed(ActionEvent e) {
				ingrEgreRadioButton.setSelected(true);
				onTypeSelected();
			}
		});

		onSelectComboFechaAltaChange();
		onSelectComboFechaLiqChange();
	}

	class CVTableModel extends DefaultTableModel {
		private String[] columnNames = columnas;
		private Object[] data;

		public CVTableModel() {
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
				return ((Operacion) data[row]).getCliente().getCodigo();
			case 2:
				if (((Operacion) data[row]).getTipo().equals(OperacionType.COMPRA))
					return ((Operacion) data[row]).getEspecieEntra().getCodigo();
				else
					return ((Operacion) data[row]).getEspecieSale().getCodigo();
			case 3:
				return new BigDecimal(((Operacion) data[row]).getCantidad()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			case 4:
				if (((Operacion) data[row]).getSubtipo().equals(OperacionSubType.BONO))
					return new BigDecimal(((Operacion) data[row]).getValorizacion().doubleValue() * 100).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				else
					return new BigDecimal(((Operacion) data[row]).getValorizacion()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			case 5:
				if ((((Operacion) data[row]).getTipo().equals(OperacionType.VENTA) && ((Operacion) data[row]).getEspecieSale().getAforoInverso())
						|| (((Operacion) data[row]).getTipo().equals(OperacionType.COMPRA) && ((Operacion) data[row]).getEspecieEntra().getAforoInverso())
						&& !((Operacion) data[row]).getSubtipo().equals(OperacionSubType.BILLETE))
					return new BigDecimal(((Operacion) data[row]).getCantidad() / ((Operacion) data[row]).getValorizacion()).setScale(2,
							BigDecimal.ROUND_HALF_UP).doubleValue();
				else
					return new BigDecimal(((Operacion) data[row]).getCantidad() * ((Operacion) data[row]).getValorizacion()).setScale(2,
							BigDecimal.ROUND_HALF_UP).doubleValue();
			case 6:
				return ((Operacion) data[row]).isLiquidado();
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

	class CVCanjeTableModel extends CVTableModel {
		private String[] columnNames = columnasCanje;
		private Object[] data;

		public CVCanjeTableModel() {
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
				return ((Operacion) data[row]).getCliente().getCodigo();
			case 2:
				if (((Operacion) data[row]).getTipo().equals(OperacionType.COMPRA))
					return ((Operacion) data[row]).getEspecieSale().getCodigo();
				else
					return ((Operacion) data[row]).getEspecieEntra().getCodigo();
			case 3:
				return new BigDecimal(((Operacion) data[row]).getCantidad()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			case 4:
				if (((Operacion) data[row]).getTipo().equals(OperacionType.COMPRA))
					return ((Operacion) data[row]).getEspecieEntra().getCodigo();
				else
					return ((Operacion) data[row]).getEspecieSale().getCodigo();
			case 5:
				return new BigDecimal(((Operacion) data[row]).getCantidadSale()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			case 6:
				return ((Operacion) data[row]).isLiquidado();
			case 7:
				return UtilGUI.formatFecha(((Operacion) data[row]).getFechaLiquidacion());
			case 8:
				return ((Operacion) data[row]).getNotas();
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

	class IETableModel extends CVTableModel {
		private String[] columnNames = columnasIE;
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
				return new BigDecimal(((Operacion) data[row]).getCantidad()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
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
	private void actualizarLista() {
		// COMPRA NEW
		SearchOperacionesDTO searchDTO = getSearchOperacionesDTO();

		Collection<Operacion> result = BeltzaApplication.getModel().searchOperaciones(searchDTO);

		if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
			final CVCanjeTableModel dm = new CVCanjeTableModel();
			dm.setData(result.toArray());
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					tableIngreso.setModel(dm);
				}
			});
		} else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA)) {
			final IETableModel dm = new IETableModel();
			dm.setData(result.toArray());
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					tableIngreso.setModel(dm);
				}
			});
		} else {
			final CVTableModel dm = new CVTableModel();
			dm.setData(result.toArray());
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					tableIngreso.setModel(dm);
				}
			});
		}

		// EGRESO (no hace falta buscar para Canje porque no hay nada)
		if (!getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
			if (getOperacionSubTypeSelected().equals(OperacionSubType.NA))
				searchDTO.setTipo(OperacionType.EGRESO);
			else
				searchDTO.setTipo(OperacionType.VENTA);
			searchDTO.setSubtipo(getOperacionSubTypeSelected());
			searchDTO.setEspecieSaleCod(searchDTO.getEspecieEntraCod());
			searchDTO.setEspecieEntraCod(null);
			result = BeltzaApplication.getModel().searchOperaciones(searchDTO);

			if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
				final CVCanjeTableModel dm = new CVCanjeTableModel();
				dm.setData(result.toArray());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						tableEgreso.setModel(dm);
					}
				});
			} else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA)) {
				final IETableModel dm = new IETableModel();
				dm.setData(result.toArray());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						tableEgreso.setModel(dm);
					}
				});
			} else {
				final CVTableModel dm = new CVTableModel();
				dm.setData(result.toArray());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						tableEgreso.setModel(dm);
					}
				});
			}
		}

		actualizarRadios();
		buscarButton.setText("Buscar");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				actualizarTitulos();
			}
		});

	}

	private void actualizarTitulos() {
		if (((CVTableModel) tableIngreso.getModel()).getRowCount() == 200) {
			if (!comprasLabel.getText().contains(UtilGUI.RECORD_LIMIT_ADD_TEXT)) {
				comprasLabel.setText(comprasLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				comprasLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
			}
		} else {
			comprasLabel.setText(comprasLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			comprasLabel.setToolTipText(null);
		}

		if (((CVTableModel) tableEgreso.getModel()).getRowCount() == 200) {
			if (!ventasLabel.getText().contains(UtilGUI.RECORD_LIMIT_ADD_TEXT)) {
				ventasLabel.setText(ventasLabel.getText() + UtilGUI.RECORD_LIMIT_ADD_TEXT);
				ventasLabel.setToolTipText(UtilGUI.RECORD_LIMIT_TOOLTIP);
			}
		} else {
			ventasLabel.setText(ventasLabel.getText().replace(UtilGUI.RECORD_LIMIT_ADD_TEXT, ""));
			ventasLabel.setToolTipText(null);
		}
	}

	private void initAdd() {
		comprasLabel.setText("Compras");
		ventasLabel.setText("Ventas");
		cantidadLabel.setText("Cantidad");
		precioLabel.setText("Precio");
		especieLabel.setText("Especie");
		especieSLabel.setText("Cuenta");
		textEspecieEntra.setText("");
		textEspeciSale.setText("");
		textCantidad.setText("");
		textPrecio.setText("");
		textNota.setText("");
		textCliente.setText("");
		igualLabel.setText("");
		spinnerFechaLiq.setDate(null);
		liquidadoCheckBox.setEnabled(true);
		liquidadoCheckBox.setSelected(false);
		agregarButton.setEnabled(false);
		agregarButton.setText("Agregar");
		textEspecieEntra.setEditable(true);
		textEspeciSale.setEditable(true);
		textCliente.setEditable(true);
		compraRadioButton.setEnabled(true);
		ventaRadioButton.setEnabled(true);
		compraRadioButton.setVisible(true);
		ventaRadioButton.setVisible(true);
		panelVentas.setVisible(true);

		compraRadioButton.setText("Compra");
		ventaRadioButton.setText("Venta");

		textEspeciSale.setVisible(true);
		especieSLabel.setVisible(true);
		textPrecio.setVisible(true);
		precioLabel.setVisible(true);
		spinnerFechaLiq.setVisible(true);
		fechaLiqLabel.setVisible(true);

		operacionSelectedToEdit = null;

		// Si es BILLETE
		if (getOperacionSubTypeSelected().equals(OperacionSubType.BILLETE)) {
			textEspecieEntra.setText("BB");
			textEspeciSale.setText("$");
			textEspecieEntra.setEditable(false);
			textEspeciSale.setEditable(false);
		} else if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
			cantidadLabel.setText("Cant. Entra");
			precioLabel.setText("Cant. Sale");
			especieLabel.setText("Esp. Entra");
			especieSLabel.setText("Esp. Sale");
			compraRadioButton.setVisible(false);
			ventaRadioButton.setVisible(false);
			panelVentas.setVisible(false);
		} else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA)) {
			compraRadioButton.setText("Ingreso");
			ventaRadioButton.setText("Egreso");
			textEspeciSale.setVisible(false);
			especieSLabel.setVisible(false);
			textPrecio.setVisible(false);
			precioLabel.setVisible(false);
			spinnerFechaLiq.setVisible(false);
			fechaLiqLabel.setVisible(false);
		}
	}

	private void initUpdate() {
		initAdd();
		agregarButton.setEnabled(false);
		agregarButton.setText("Modificar");
		textEspecieEntra.setEditable(false);
		textEspeciSale.setEditable(false);
		textCliente.setEditable(false);
		compraRadioButton.setEnabled(false);
		ventaRadioButton.setEnabled(false);
		liquidadoCheckBox.setEnabled(false);
	}

	private void addOrUpdateOperacion() {
		boolean agregar = true;
		boolean limpiarDatos = false;
		Long diaId = null;
		boolean liquidado = false;
		// Es modificacion
		if (operacionSelectedToEdit != null && operacionSelectedToEdit.getId() != null) {

			if (operacionSelectedToEdit.getCliente().getCodigo().equals(textCliente.getText())
					&& (operacionSelectedToEdit.getEspecieEntra().getCodigo().equals(textEspecieEntra.getText()) && operacionSelectedToEdit.getEspecieSale()
							.getCodigo().equals(textEspeciSale.getText()))
					|| (operacionSelectedToEdit.getTipo().equals(OperacionType.VENTA)
							&& operacionSelectedToEdit.getEspecieEntra().getCodigo().equals(textEspeciSale.getText()) && operacionSelectedToEdit
							.getEspecieSale().getCodigo().equals(textEspecieEntra.getText()))) {
				// Modificacion sobre el mismo registro
				agregar = false;

				if (operacionSelectedToEdit.getSubtipo().equals(OperacionSubType.NA)) {
					try {
						operacionSelectedToEdit.setCantidad(new Double(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					try {
						operacionSelectedToEdit.setCantidad(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue());
						if (operacionSelectedToEdit.getSubtipo().equals(OperacionSubType.BONO))
							operacionSelectedToEdit.setValorizacion(NumberFormat.getInstance().parse(this.textPrecio.getText()).doubleValue() / 100);
						else if (operacionSelectedToEdit.getSubtipo().equals(OperacionSubType.CANJE))
							operacionSelectedToEdit.setCantidadSale(NumberFormat.getInstance().parse(textPrecio.getText()).doubleValue());
						else
							operacionSelectedToEdit.setValorizacion(NumberFormat.getInstance().parse(textPrecio.getText()).doubleValue());

					} catch (ParseException e) {
						e.printStackTrace();
					}
					operacionSelectedToEdit.setFechaLiquidacion(spinnerFechaLiq.getDate());
				}

				operacionSelectedToEdit.setNotas(textNota.getText());

				BeltzaApplication.getModel().saveOperacion(operacionSelectedToEdit);
				initAdd();

			} else {
				// Elimmino y creo un movimiento nuevo
				BeltzaApplication.getModel().deleteOperacion(operacionSelectedToEdit, false);
				diaId = operacionSelectedToEdit.getDia().getId();
				liquidado = operacionSelectedToEdit.isLiquidado();
				operacionSelectedToEdit = null;
				limpiarDatos = true;
			}
		}

		// Es Alta
		if (agregar) {
			OperacionDTO operacionDTO = new OperacionDTO();
			operacionDTO.setSubtipo(getOperacionSubTypeSelected());
			operacionDTO.setClienteCod(textCliente.getText());
			operacionDTO.setDiaId(diaId);
			operacionDTO.setLiquidado(liquidadoCheckBox.isSelected() || liquidado);

			// INGRESO - EGRESO
			if (getOperacionSubTypeSelected().equals(OperacionSubType.NA)) {
				if (this.compraRadioButton.isSelected()) {
					operacionDTO.setTipo(OperacionType.INGRESO);
					operacionDTO.setEspecieEntraCod(textEspecieEntra.getText());
				} else {
					operacionDTO.setTipo(OperacionType.EGRESO);
					operacionDTO.setEspecieSaleCod(textEspecieEntra.getText());
				}
				try {
					operacionDTO.setCantidad(new Double(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue()));
				} catch (ParseException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			} else {// COMPRA - VENTA
				if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
					operacionDTO.setTipo(OperacionType.CANJE);
					operacionDTO.setEspecieEntraCod(textEspecieEntra.getText());
					operacionDTO.setEspecieSaleCod(textEspeciSale.getText());
				} else if (this.compraRadioButton.isSelected()) {
					operacionDTO.setTipo(OperacionType.COMPRA);
					operacionDTO.setEspecieEntraCod(textEspecieEntra.getText());
					operacionDTO.setEspecieSaleCod(textEspeciSale.getText());
				} else {
					operacionDTO.setTipo(OperacionType.VENTA);
					operacionDTO.setEspecieSaleCod(textEspecieEntra.getText());
					operacionDTO.setEspecieEntraCod(textEspeciSale.getText());
				}
				operacionDTO.setFechaLiquidacion((Date) spinnerFechaLiq.getDate());

				try {
					operacionDTO.setCantidad(NumberFormat.getInstance().parse(textCantidad.getText()).doubleValue());
					if (operacionDTO.getSubtipo().equals(OperacionSubType.BONO))
						operacionDTO.setValorizacion(NumberFormat.getInstance().parse(this.textPrecio.getText()).doubleValue() / 100);
					else if (operacionDTO.getSubtipo().equals(OperacionSubType.CANJE))
						operacionDTO.setCantidadSale(NumberFormat.getInstance().parse(textPrecio.getText()).doubleValue());
					else
						operacionDTO.setValorizacion(NumberFormat.getInstance().parse(textPrecio.getText()).doubleValue());
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			operacionDTO.setNotas(textNota.getText());

			try {
				BeltzaApplication.getModel().addOperacion(operacionDTO);
				if (!mantenerDatosCheckBox.isSelected() || limpiarDatos)
					initAdd();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, UtilGUI.getMessageFromException(e));
			}
		}

	}

	private void prepareAdd(OperacionType operacionType) {
		initAdd();
		if (operacionType.equals(OperacionType.COMPRA) || operacionType.equals(OperacionType.INGRESO))
			this.compraRadioButton.setSelected(true);
		else
			this.ventaRadioButton.setSelected(true);
	}

	private void prepareUpdate(Operacion operacion) {
		initUpdate();

		Boolean editSpecial = true;
		textCliente.setEditable(editSpecial);
		textEspecieEntra.setEditable(editSpecial);
		textEspeciSale.setEditable(editSpecial);

		if (operacion.getSubtipo().equals(OperacionSubType.NA)) {
			if (operacion.getTipo().equals(OperacionType.INGRESO)) {
				compraRadioButton.setSelected(true);
				textEspecieEntra.setText(operacion.getEspecieEntra().getCodigo());
			} else {
				ventaRadioButton.setSelected(true);
				textEspecieEntra.setText(operacion.getEspecieSale().getCodigo());
			}
		} else {
			if (operacion.getTipo().equals(OperacionType.CANJE)) {
				textEspecieEntra.setText(operacion.getEspecieEntra().getCodigo());
				textEspeciSale.setText(operacion.getEspecieSale().getCodigo());
			} else if (operacion.getTipo().equals(OperacionType.COMPRA)) {
				compraRadioButton.setSelected(true);
				textEspecieEntra.setText(operacion.getEspecieEntra().getCodigo());
				textEspeciSale.setText(operacion.getEspecieSale().getCodigo());
			} else {
				ventaRadioButton.setSelected(true);
				textEspecieEntra.setText(operacion.getEspecieSale().getCodigo());
				textEspeciSale.setText(operacion.getEspecieEntra().getCodigo());
			}

			if (operacion.getSubtipo().equals(OperacionSubType.CANJE))
				textPrecio.setText(UtilGUI.numberFormat().format(operacion.getCantidadSale().doubleValue()));
			else if (operacion.getSubtipo().equals(OperacionSubType.BONO))
				textPrecio.setText(UtilGUI.numberFormat().format(
						new BigDecimal(operacion.getValorizacion()).multiply(new BigDecimal(100), new MathContext(13)).doubleValue()));
			else
				textPrecio.setText(UtilGUI.numberFormat().format(operacion.getValorizacion().doubleValue()));
			spinnerFechaLiq.setDate(operacion.getFechaLiquidacion());

			actualizarSubtotal();
		}
		textCantidad.setText(NumberFormat.getInstance().format(operacion.getCantidad()));
		textCliente.setText(operacion.getCliente().getCodigo());
		textNota.setText(operacion.getNotas());
		liquidadoCheckBox.setSelected(operacion.getFechaLiquidacion() != null);

		actualizarSubtotal();

		// Me guardo la operacion que uso al aceptar
		operacionSelectedToEdit = operacion;
	}

	public void modelChanged(BeltzaEvent event) {
		if (ChangeType.OPERACION.equals(event.getChangeType())) {
			if (this.isVisible() && this.isSelected()) {
				actualizarLista();
			} else {
				buscarButton.setText("Buscar *");
				verifTableSelection();
			}
		}
	}

	private void validar() {
		this.agregarButton.setEnabled(false);

		try {
			if (this.textEspecieEntra.getText().trim().length() == 0)
				return;
			if (!getOperacionSubTypeSelected().equals(OperacionSubType.NA) && this.textEspeciSale.getText().trim().length() == 0)
				return;
			if (this.textCantidad.getText().trim().length() == 0 || (UtilGUI.numberFormat().parse(textCantidad.getText()).doubleValue() == 0))
				return;
			if (!getOperacionSubTypeSelected().equals(OperacionSubType.NA) && this.textPrecio.getText().trim().length() == 0)
				return;
			if (this.textCliente.getText().trim().length() == 0)
				return;
			if (!getOperacionSubTypeSelected().equals(OperacionSubType.CANJE) && !getOperacionSubTypeSelected().equals(OperacionSubType.NA)
					&& !this.compraRadioButton.isSelected() && !this.ventaRadioButton.isSelected())
				return;
			if (!monedaRadioButton.isSelected() && !billeteRadioButton.isSelected() && !bonoRadioButton.isSelected() && !accionRadioButton.isSelected()
					&& !canjeRadioButton.isSelected() && !ingrEgreRadioButton.isSelected())
				return;
			this.agregarButton.setEnabled(true);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void liquidar(Operacion operacion) {
		if (operacion.isLiquidado()) {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Des-Liquidar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Desliquidar",
					JOptionPane.OK_CANCEL_OPTION) == 0)
				BeltzaApplication.getModel().desliquidarOperacion(operacion);
		} else {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Liquidar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Liquidar",
					JOptionPane.OK_CANCEL_OPTION) == 0)
				BeltzaApplication.getModel().liquidarOperacion(operacion);
		}
	}

	private void borrarOperacion(Operacion operacion) {
		if (!operacion.getDia().isAbierto()){
			if (JOptionPane.showConfirmDialog(null, "Esta eliminando una operaci\u00F3n de dia anterior", "Confirmaci\u00F3n Borrar", JOptionPane.OK_CANCEL_OPTION) == 0) {
				if (JOptionPane.showConfirmDialog(null, "Confirma que desea Borrar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Borrar", JOptionPane.OK_CANCEL_OPTION) == 0) {
					BeltzaApplication.getModel().deleteOperacion(operacion, true);
					initAdd();
				}
			}
		}else{
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Borrar la Operaci\u00F3n seleccionada?", "Confirmaci\u00F3n Borrar", JOptionPane.OK_CANCEL_OPTION) == 0) {
				BeltzaApplication.getModel().deleteOperacion(operacion, true);
				initAdd();
			}
		}
	}

	private void verifTableSelection() {
		if (tableIngreso.getSelectedRow() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			delIngresosButton.setEnabled(false);
			liquidarIngresoButton.setEnabled(false);
			desliqIngresoButton.setEnabled(false);
		} else {
			delIngresosButton.setEnabled(true);

			liquidarIngresoButton.setEnabled(!((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow())).isLiquidado());
			desliqIngresoButton.setEnabled(((Operacion) ((CVTableModel) tableIngreso.getModel()).getDataRow(tableIngreso.getSelectedRow())).isLiquidado());
		}
		if (tableEgreso.getSelectedRow() < 0 || !UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText())) {
			delEgresosButton.setEnabled(false);
			liquidarEgresoButton.setEnabled(false);
			desliqEgresoButton.setEnabled(false);
		} else {
			delEgresosButton.setEnabled(true);

			liquidarEgresoButton.setEnabled(!((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow())).isLiquidado());
			desliqEgresoButton.setEnabled(((Operacion) ((CVTableModel) tableEgreso.getModel()).getDataRow(tableEgreso.getSelectedRow())).isLiquidado());
		}
	}

	private OperacionSubType getOperacionSubTypeSelected() {
		if (monedaRadioButton.isSelected())
			return OperacionSubType.MONEDA;
		else if (billeteRadioButton.isSelected())
			return OperacionSubType.BILLETE;
		else if (accionRadioButton.isSelected())
			return OperacionSubType.ACCION;
		else if (bonoRadioButton.isSelected())
			return OperacionSubType.BONO;
		else if (canjeRadioButton.isSelected())
			return OperacionSubType.CANJE;
		else if (ingrEgreRadioButton.isSelected())
			return OperacionSubType.NA;

		monedaRadioButton.setSelected(true);
		return OperacionSubType.MONEDA;
	}

	private void onTypeSelected() {
		initAdd();
		setPanel();
		actualizarLista();
		setEntryOrder();
	}

	private void actualizarSubtotal() {
		String text = "";

		if (!getOperacionSubTypeSelected().equals(OperacionSubType.CANJE))
			if (textCantidad.getText().trim().length() > 0 && textPrecio.getText().trim().length() > 0) {
				double subT;
				try {
					boolean inverso = false;
					if (textEspecieEntra.getText().trim().length() > 0) {
						Especie esp = BeltzaApplication.getModel().getEspecieByCodigo(textEspecieEntra.getText());
						if (esp != null && esp.getAforoInverso())
							inverso = true;
					}
					subT = 0;
					if (inverso) {
						subT = UtilGUI.numberFormat().parse(textCantidad.getText()).doubleValue()
								/ UtilGUI.numberFormat().parse(textPrecio.getText()).doubleValue();
					} else {
						subT = UtilGUI.numberFormat().parse(textCantidad.getText()).doubleValue()
								* UtilGUI.numberFormat().parse(textPrecio.getText()).doubleValue();
					}
					if (getOperacionSubTypeSelected().equals(OperacionSubType.BONO))
						subT = subT / 100.0;
					text = UtilGUI.numberFormat().format(new BigDecimal(subT).setScale(2, BigDecimal.ROUND_HALF_UP));
				} catch (ParseException e) {

				}

			}

		if (text.trim().length() > 0)
			text = "= " + text;
		igualLabel.setText(text);
	}

	private void setPanel() {
		if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE)) {
			textBuscEspecieSale.setVisible(true);
			comprasLabel.setText("Canjes");
			panel_3.remove(panelVentas);
		} else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA)) {
			textBuscEspecieSale.setVisible(false);
			comprasLabel.setText("Ingreso");
			ventasLabel.setText("Egreso");
			panel_3.add(panelVentas);
		} else {
			panel_3.add(panelVentas);
			textBuscEspecieSale.setVisible(false);
		}

	}

	private void setEntryOrder() {
		if (canjeRadioButton.isSelected()) {
			panelEntrada.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textCliente, textEspecieEntra, textCantidad, textEspeciSale,
					textPrecio, textNota, liquidadoCheckBox, spinnerFechaLiq }));

		} else if (ingrEgreRadioButton.isSelected()) {
			panelEntrada.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textCliente, textEspecieEntra, textCantidad, textNota,
					liquidadoCheckBox, compraRadioButton, ventaRadioButton }));
		} else {
			panelEntrada.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { textCliente, textCantidad, textPrecio, agregarButton,
					textEspecieEntra, textEspeciSale, textNota, liquidadoCheckBox, spinnerFechaLiq }));
		}
	}

	private void actualizarRadios() {

		SearchOperacionesDTO searchDTO = getSearchOperacionesDTO();
		final ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) BeltzaApplication.getModel().getOperacionSubTypeListByExample(searchDTO);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				doActualizarRadios(list);
			}
		});
	}

	private void doActualizarRadios(ArrayList<Map<String, Object>> list) {

		monedaRadioButton.setForeground(new Color(0, 0, 255));
		billeteRadioButton.setForeground(new Color(0, 0, 255));
		bonoRadioButton.setForeground(new Color(0, 0, 255));
		ingrEgreRadioButton.setForeground(new Color(0, 0, 255));
		canjeRadioButton.setForeground(new Color(0, 0, 255));
		accionRadioButton.setForeground(new Color(0, 0, 255));

		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			Map<java.lang.String, java.lang.Object> m = (Map<java.lang.String, java.lang.Object>) it.next();
			if (m.get("subtipo") != null) {
				if (m.get("subtipo").equals(OperacionSubType.MONEDA.toString()))
					monedaRadioButton.setForeground(new Color(255, 0, 0));
				else if (m.get("subtipo").equals(OperacionSubType.BILLETE.toString()))
					billeteRadioButton.setForeground(new Color(255, 0, 0));
				else if (m.get("subtipo").equals(OperacionSubType.BONO.toString()))
					bonoRadioButton.setForeground(new Color(255, 0, 0));
				else if (m.get("subtipo").equals(OperacionSubType.NA.toString()))
					ingrEgreRadioButton.setForeground(new Color(255, 0, 0));
				else if (m.get("subtipo").equals(OperacionSubType.CANJE.toString()))
					canjeRadioButton.setForeground(new Color(255, 0, 0));
				else if (m.get("subtipo").equals(OperacionSubType.ACCION.toString()))
					accionRadioButton.setForeground(new Color(255, 0, 0));
			}
		}
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
		if (getOperacionSubTypeSelected().equals(OperacionSubType.CANJE))
			searchDTO.setTipo(OperacionType.CANJE);
		else if (getOperacionSubTypeSelected().equals(OperacionSubType.NA))
			searchDTO.setTipo(OperacionType.INGRESO);
		else
			searchDTO.setTipo(OperacionType.COMPRA);
		searchDTO.setSubtipo(getOperacionSubTypeSelected());
		searchDTO.setClienteCod(textBuscCliente.getText().trim().length() > 0 ? textBuscCliente.getText() : null);
		searchDTO.setEspecieEntraCod(textBuscEspecie.getText().trim().length() > 0 ? textBuscEspecie.getText() : null);
		searchDTO
				.setEspecieSaleCod(getOperacionSubTypeSelected().equals(OperacionSubType.CANJE) && textBuscEspecieSale.getText().trim().length() > 0 ? textBuscEspecieSale
						.getText() : null);
		searchDTO.setTipoFechaAlta(fechaAltaComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaAltaComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaAltaDesde(fechaAltaComboBox.getSelectedIndex() > 0 ? spinnerBuscFechaAltaD.getDate() : null);
		searchDTO.setFechaAltaHasta(fechaAltaComboBox.getSelectedIndex() == 2 ? spinnerBuscFechaAltaH.getDate() : null);

		searchDTO.setTipoFechaLiq(fechaLiqComboBox.getSelectedIndex() == 0 ? null : SearchDateType.values()[fechaLiqComboBox.getSelectedIndex() - 1]);
		searchDTO.setFechaLiqDesde(fechaLiqComboBox.getSelectedIndex() > 0 ? spinnerBuscFLiqD.getDate() : null);
		searchDTO.setFechaLiqHasta(fechaLiqComboBox.getSelectedIndex() == 2 ? spinnerBuscFLiqH.getDate() : null);

		return searchDTO;
	}
}
