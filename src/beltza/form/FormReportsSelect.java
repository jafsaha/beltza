package beltza.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.domain.Dia;
import beltza.report.BeltzaReportManager;

public class FormReportsSelect extends JInternalFrame implements BeltzaObserver {

	private JList reportsList;
	private static final long serialVersionUID = 1L;

	private JXDatePicker spinnerFecha;

	private static FormReportsSelect myInstance;
	private JPanel jContentPane = null;
	private JButton jbtnAceptar = null;
	private JButton jbtnCancelar = null;
	private JLabel jlblInfo = null;

	public FormReportsSelect() {
		super();

		initialize();

		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				validar();
			}
		});
	}

	// Singleton
	public static FormReportsSelect getInstance() {
		if (myInstance == null) {
			myInstance = new FormReportsSelect();
			return myInstance;
		} else {
			return myInstance;
		}
	}

	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setSize(349, 292);
		this.setTitle("Generaci\u00F3n de Reportes");
		this.setClosable(true);
		this.setPreferredSize(new Dimension(350, 300));
		this.setLocation(200, 200);
		this.setContentPane(getJContentPane());

		BeltzaApplication.getModel().addObserver(this, ChangeType.DIA);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlblInfo = new JLabel("", JLabel.CENTER);
			jlblInfo.setForeground(Color.RED);
			jlblInfo.setBounds(new Rectangle(0, 110, 300, 16));

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jlblInfo, null);

			jContentPane.add(getJbtnAceptar());
			jContentPane.add(getJbtnCancelar());

			final JLabel fechaLabel = new JLabel();
			fechaLabel.setText("Fecha");
			fechaLabel.setBounds(31, 24, 73, 14);
			jContentPane.add(fechaLabel);

			spinnerFecha = new JXDatePicker(new Date());
			spinnerFecha.setBounds(110, 21, 120, 20);
			spinnerFecha.setFormats("dd/MM/yyyy");
			spinnerFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validar();
				}
			});
			jContentPane.add(spinnerFecha);

			final JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(31, 91, 155, 124);
			jContentPane.add(scrollPane);

			reportsList = new JList(getListaReportes());
			scrollPane.setViewportView(reportsList);
			reportsList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					validar();
				}
			});

			final JLabel seleccionLabel = new JLabel();
			seleccionLabel.setText("Seleccione los Reportes");
			seleccionLabel.setBounds(31, 71, 249, 15);
			jContentPane.add(seleccionLabel);

			final JSeparator separator = new JSeparator();
			separator.setBounds(0, 230, 344, 14);
			jContentPane.add(separator);

		}
		return jContentPane;
	}

	protected JButton getJbtnAceptar() {
		if (jbtnAceptar == null) {
			jbtnAceptar = new JButton();
			jbtnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generar();
				}
			});
			jbtnAceptar.setBounds(new Rectangle(140, 242, 90, 22));
			jbtnAceptar.setText("Generar");
		}
		return jbtnAceptar;
	}

	protected JButton getJbtnCancelar() {
		if (jbtnCancelar == null) {
			jbtnCancelar = new JButton();
			jbtnCancelar.setBounds(new Rectangle(240, 242, 90, 22));
			jbtnCancelar.setText("Cerrar");
			jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelar();
				}
			});
		}
		return jbtnCancelar;
	}

	private void cancelar() {
		this.setVisible(false);
	}

	public void setVisible(boolean v) {
		super.setVisible(v);
	}

	private void validar() {
		Dia dia = BeltzaApplication.getModel().getDiaByFechaAsociada((Date) spinnerFecha.getDate());
		final Boolean enabled = (dia != null && reportsList.getSelectedValues().length > 0);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jbtnAceptar.setEnabled(enabled);
			}
		});
	}

	private void generar() {
		Object[] values = reportsList.getSelectedValues();

		Dia dia = BeltzaApplication.getModel().getDiaByFechaAsociada((Date) spinnerFecha.getDate());

		if (dia != null)
			for (int i = 0; i < values.length; i++) {
				try {
					BeltzaReportManager.getInstance().generarReportesBeltza(values[i].toString(), dia);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	private Object[] getListaReportes() {
		Object[] listRep = BeltzaReportManager.getInstance().getReportNameList();

		return listRep;
	}

	public void modelChanged(BeltzaEvent event) {
		if (ChangeType.DIA.equals(event.getChangeType())) {
			validar();
		}
	}
}
