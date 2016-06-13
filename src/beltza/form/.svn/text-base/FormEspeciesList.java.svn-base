package beltza.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Dia;
import beltza.domain.Especie;
import beltza.dto.EspecieDTO;
import beltza.report.BeltzaReportManager;
import beltza.util.JTextFieldLimit;
import beltza.util.UtilGUI;

public class FormEspeciesList extends JInternalFrame implements BeltzaObserver {

	private JCheckBox mostrarTodosCheckBox;
	private JTable table;
	private JButton agregarButton;
	private JCheckBox aforoInversoCheckBox;
	private JTextField textAforo;
	private JTextField textEspecie;
	private JXDatePicker spinnerFechaCierre;
	private JComboBox comboBox;
	private JTextField textBuscEspecie;
	private final JButton buscarButton; 
	
	private static String[] columnas = { "Especie", "Stock", "Posici\u00F3n", "Aforo", "Aforo Inverso" };
	// Variable creada para demo
	private Object[][] datos = {};

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormEspeciesList frame = new FormEspeciesList();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormEspeciesList() {
		super();
		setTitle("Especies");
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
		setBounds(100, 100, 600, 511);

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
		buscarButton.setBounds(447, 21, 93, 23);
		panel.add(buscarButton);

		final JLabel especieBuscLabel = new JLabel();
		especieBuscLabel.setText("Especie");
		especieBuscLabel.setBounds(22, 25, 54, 14);
		panel.add(especieBuscLabel);

		textBuscEspecie = new JTextField();
		textBuscEspecie.setBounds(93, 22, 79, 20);
		textBuscEspecie.setDocument(new JTextFieldLimit(4, true));
		panel.add(textBuscEspecie);

		final JLabel aforoInvLabel = new JLabel();
		aforoInvLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		aforoInvLabel.setText("Aforo Inverso");
		aforoInvLabel.setBounds(178, 24, 128, 14);
		panel.add(aforoInvLabel);

		comboBox = new JComboBox(new String[] { "", "Si", "No" });
		comboBox.setBounds(312, 21, 79, 20);
		panel.add(comboBox);
		
		final JLabel fechaLabel = new JLabel();
		fechaLabel.setText("Fecha de corte informe:");
		fechaLabel.setBounds(292, 54, 148, 14);
		panel.add(fechaLabel);
		
		spinnerFechaCierre = new JXDatePicker(new Date());
		spinnerFechaCierre.setBounds(446, 51, 115, 20);
		spinnerFechaCierre.setFormats("dd/MM/yyyy");

		panel.add(spinnerFechaCierre);

		mostrarTodosCheckBox = new JCheckBox();
		mostrarTodosCheckBox.setOpaque(false);
		mostrarTodosCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mostrarTodosCheckBox.setText("Mostrar todos");
		mostrarTodosCheckBox.setBounds(93, 48, 167, 23);
		panel.add(mostrarTodosCheckBox);

		final JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(0, 0));
		panel_1.setLayout(null);
		panel_1.setPreferredSize(new Dimension(500, 100));
		panel_1.setBackground(new Color(102, 205, 170));
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		agregarButton = new JButton();
		agregarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEspecie();
			}
		});
		agregarButton.setText("Agregar");
		agregarButton.setBounds(447, 21, 93, 23);
		panel_1.add(agregarButton);

		textEspecie = new JTextField();
		textEspecie.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				UtilGUI.setJTextFieldToUppercase(textEspecie);
			}
		});
		textEspecie.setBounds(89, 22, 70, 20);
		textEspecie.setDocument(new JTextFieldLimit(4, true));
		panel_1.add(textEspecie);

		textAforo = new JTextField();
		textAforo.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				validar();
			}
		});
		textAforo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textAforo.setBounds(89, 48, 70, 20);
		panel_1.add(textAforo);

		aforoInversoCheckBox = new JCheckBox();
		aforoInversoCheckBox.setOpaque(false);
		aforoInversoCheckBox.setText("Aforo Inverso");
		aforoInversoCheckBox.setBounds(195, 47, 140, 23);
		panel_1.add(aforoInversoCheckBox);

		final JLabel especieLabel = new JLabel();
		especieLabel.setText("Especie");
		especieLabel.setBounds(29, 25, 54, 14);
		panel_1.add(especieLabel);

		final JLabel aforoLabel = new JLabel();
		aforoLabel.setText("Aforo");
		aforoLabel.setBounds(29, 51, 55, 14);
		panel_1.add(aforoLabel);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		getContentPane().add(panel_2, BorderLayout.CENTER);

		final JLabel listaDeEspeciesLabel = new JLabel();
		listaDeEspeciesLabel.setText("Lista de Especies");
		panel_2.add(listaDeEspeciesLabel, BorderLayout.NORTH);

		final JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable(this.datos, columnas);
		// TODO: ver como implementar esto del editor
		// table.setDefaultEditor(Double.class, editor);
		scrollPane.setViewportView(table);
		table.setModel(new EspecieTM());
		table.setDefaultRenderer(Double.class, new DecimalRenderer(""));
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (table.getSelectedRow() >= 0) {//((JTable)e.getSource()).getSelectedRow()
						try {
							Dia dia = BeltzaApplication.getModel().getDiaAbierto();
							if (dia != null){
								String especieCod = ((Especie)((EspecieTM)table.getModel()).getDataRow(table.getSelectedRow())).getCodigo();
								BeltzaReportManager.getInstance().generarReporteMovsEspeciePorCliente(dia, especieCod, spinnerFechaCierre.getDate());
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
			}
		});
		BeltzaApplication.getModel().addObserver(this, ChangeType.ESPECIE);
		//
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

	class EspecieTM extends DefaultTableModel {
		private String[] columnNames = columnas;
		private Object[] data;

		public EspecieTM() {
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
		
		public Object getDataRow(int row) {
			return data[row];
		}

		public Object getValueAt(int row, int col) {
			switch (col) {
			case 0:
				return ((Especie) data[row]).getCodigo();
			case 1:
				return ((Especie) data[row]).getStock();
			case 2:
				return ((Especie) data[row]).getPosicion();
			case 3:
				if (((Especie) data[row]).getAforoInverso() && ((Especie) data[row]).getAforo() != null && ((Especie) data[row]).getAforo().doubleValue() != 0)
					return new Double(1 / ((Especie) data[row]).getAforo().doubleValue());
				else
					return ((Especie) data[row]).getAforo();
			case 4:
				return ((Especie) data[row]).getAforoInverso();
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

		public boolean isCellEditable(int row, int column) {
			// Aqui devolvemos true o false segun queramos que una celda
			// identificada por fila,columna (row,column), sea o no editable
			if ((column == 3 || column == 4) && UtilGUI.BUTTON_BUSCAR.equals(buscarButton.getText()))
				return true;
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			Especie especie = ((Especie) data[row]);
			switch (col) {
			case 3:
				if (especie.getAforoInverso() && ((Double) value).doubleValue() != 0)
					especie.setAforo(1 / ((Double) value).doubleValue());
				else
					especie.setAforo((Double) value);
				break;
			case 4:
				especie.setAforoInverso((Boolean) value);
				break;
			default:
				break;
			}
			updateEspecie(especie);
		}

	};

	private void actualizarLista() {
		Especie especie = new Especie();
		especie.setCodigo(this.textBuscEspecie.getText());
		if (comboBox.getSelectedItem().equals("Si"))
			especie.setAforoInverso(Boolean.TRUE);
		else if (comboBox.getSelectedItem().equals("No"))
			especie.setAforoInverso(Boolean.FALSE);

		Collection result = BeltzaApplication.getModel().searchEspecies(especie);

		if (!this.mostrarTodosCheckBox.isSelected()) {
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Especie esp = (Especie) it.next();
				if (Math.abs(esp.getPosicion().doubleValue()) < 0.01 && Math.abs(esp.getStock().doubleValue()) < 0.01)
					it.remove();
				else {
					if (Math.abs(esp.getPosicion().doubleValue()) < 0.01)
						esp.setPosicion(0.0);
					if (Math.abs(esp.getStock().doubleValue()) < 0.01)
						esp.setStock(0.0);
				}
			}
		}

		final EspecieTM dm = new EspecieTM();
		dm.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				table.setModel(dm);
			}
		});
		buscarButton.setText("Buscar");
	}

	private void initAgregar() {
		textEspecie.setText("");
		textAforo.setText("");
		aforoInversoCheckBox.setSelected(false);
		agregarButton.setEnabled(false);
	}

	private void validar() {
		this.agregarButton.setEnabled(false);

		if (this.textEspecie.getText().trim().length() == 0)
			return;
		if (this.textAforo.getText().trim().length() == 0)
			return;

		this.agregarButton.setEnabled(true);
	}

	private void addEspecie() {

		if (textEspecie.isEditable()) {
			EspecieDTO especie = new EspecieDTO();
			especie.setCodigo(textEspecie.getText());
			try {
				especie.setAforo(new Double(NumberFormat.getInstance().parse(textAforo.getText()).doubleValue()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			especie.setAforoInverso(aforoInversoCheckBox.isSelected());
			BeltzaApplication.getModel().addEspecie(especie);
		} else {
			Especie especie = new Especie();
			especie.setCodigo(textEspecie.getText());
			try {
				especie.setAforo(new Double(NumberFormat.getInstance().parse(textAforo.getText()).doubleValue()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			especie.setAforoInverso(aforoInversoCheckBox.isSelected());
			BeltzaApplication.getModel().saveEspecie(especie);
		}
		initAgregar();
	}

	private void updateEspecie(Especie especie) {
		BeltzaApplication.getModel().saveEspecie(especie);
	}

	public void modelChanged(BeltzaEvent event) {
		if (this.isVisible() && this.isSelected()) {
			actualizarLista();
		} else {
			buscarButton.setText("Buscar *");
		}
	}
}
