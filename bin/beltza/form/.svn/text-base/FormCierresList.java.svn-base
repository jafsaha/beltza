package beltza.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Cierre;
import beltza.domain.Especie;
import beltza.util.JTextFieldLimit;

public class FormCierresList extends JInternalFrame implements BeltzaObserver {

	private JTable table;
	private JTextField textBuscEspecie;
	private JXDatePicker spinnerBuscFecha;
	private final JButton buscarButton; 
	
	private static String[] columnas = { "Fecha", "Especie", "Stock", "Posici\u00F3n", "Aforo", "Pos x Aforo", "Acumulado" };
	// Variable creada para demo
	private Object[][] datos = {};

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			FormCierresList frame = new FormCierresList();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public FormCierresList() {
		super();
		setTitle("Cierres");
		setClosable(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setMaximizable(true);
		setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setBounds(100, 100, 566, 511);

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

		final JLabel fechaLabel = new JLabel();
		fechaLabel.setText("Fecha");
		fechaLabel.setBounds(188, 25, 54, 14);
		panel.add(fechaLabel);

		spinnerBuscFecha = new JXDatePicker(new Date());
		spinnerBuscFecha.setBounds(258, 22, 115, 20);
		spinnerBuscFecha.setFormats("dd/MM/yyyy");

		panel.add(spinnerBuscFecha);

		final JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(0, 0));
		panel_1.setLayout(null);
		panel_1.setPreferredSize(new Dimension(500, 20));
		panel_1.setBackground(new Color(102, 205, 170));
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		getContentPane().add(panel_2, BorderLayout.CENTER);

		final JLabel listaDeEspeciesLabel = new JLabel();
		listaDeEspeciesLabel.setText("Lista de Especies por Cierre");
		panel_2.add(listaDeEspeciesLabel, BorderLayout.NORTH);

		final JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		table = new JTable(this.datos, columnas);
		scrollPane.setViewportView(table);
		table.setModel(new CierreTM());

		BeltzaApplication.getModel().addObserver(this, ChangeType.CIERRE);
		//
	}

	class CierreTM extends DefaultTableModel {
		private String[] columnNames = columnas;
		private Object[] data;

		public CierreTM() {
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
				return ((Cierre) data[row]).getDia().getFechaAsociada();
			case 1:
				return ((Cierre) data[row]).getEspecie().getCodigo();
			case 2:
				return ((Cierre) data[row]).getStock();
			case 3:
				return ((Cierre) data[row]).getPosicion();
			case 4:
				if (((Cierre) data[row]).getEspecie().getAforoInverso() && ((Cierre) data[row]).getAforo() != null
						&& ((Cierre) data[row]).getAforo().doubleValue() != 0)
					return new Double(1 / ((Cierre) data[row]).getAforo().doubleValue());
				else
					return ((Cierre) data[row]).getAforo();
			case 5:
				return (((Cierre) data[row]).getPosicion().doubleValue() * ((Cierre) data[row]).getAforo().doubleValue());
			case 6:
				double acumulado = 0.0;
				for(int i=0;i<=row;i++){
					acumulado = acumulado + (((Cierre) data[i]).getPosicion().doubleValue() * ((Cierre) data[i]).getAforo().doubleValue());					
				}
				return acumulado;
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
			if (column == 4)
				return true;
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			Cierre cierre = ((Cierre) data[row]);
			switch (col) {
			case 4:
				cierre.setAforo((Double) value);
				break;
			default:
				break;
			}
			updateCierre(cierre);
		}

	};

	private void actualizarLista() {
		Especie especie = null;
		if (this.textBuscEspecie.getText() != null && this.textBuscEspecie.getText().trim().length() > 0) {
			especie = new Especie();
			especie.setCodigo(this.textBuscEspecie.getText());
		}

		Collection result = BeltzaApplication.getModel().getCierresByFechaEspecie((Date) spinnerBuscFecha.getDate(), especie);

		final CierreTM dm = new CierreTM();
		dm.setData(result.toArray());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				table.setModel(dm);
			}
		});
		buscarButton.setText("Buscar");
	}

	private void updateCierre(Cierre cierre) {
		BeltzaApplication.getModel().saveCierre(cierre);
	}

	public void modelChanged(BeltzaEvent event) {
		if (this.isVisible() && this.isSelected()) {
			actualizarLista();
		} else {
			buscarButton.setText("Buscar *");
		}
	}
}
