package beltza.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.jdesktop.swingx.JXDatePicker;

import beltza.BeltzaApplication;
import beltza.business.BeltzaBusinessException;

public class FormCompactData extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FormCompactData myInstance;
	private JPanel jContentPane = null;
	private JLabel fechaLabel = null;
	private JButton jbtnAceptar = null;
	private JButton jbtnCancelar = null;
	private JLabel jlblInfo = null;

	private JXDatePicker datePicker;

	public FormCompactData() {
		super();
		initialize();
	}

	// Singleton
	public static FormCompactData getInstance() {
		if (myInstance == null) {
			myInstance = new FormCompactData();
			return myInstance;
		} else {
			return myInstance;
		}
	}

	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Compactar Datos");
		this.setClosable(true);
		this.setPreferredSize(new Dimension(300, 200));
		this.setLocation(200, 200);
		// UtilGUI.center(this);

		this.setContentPane(getJContentPane());

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			fechaLabel = new JLabel();
			fechaLabel.setBounds(new Rectangle(30, 25, 82, 15));
			fechaLabel.setText("Fecha:");
			jlblInfo = new JLabel("", JLabel.CENTER);
			jlblInfo.setForeground(Color.RED);
			jlblInfo.setBounds(new Rectangle(0, 110, 300, 16));

			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			jContentPane.add(fechaLabel);
			jContentPane.add(jlblInfo, null);

			jContentPane.add(getJbtnAceptar());
			jContentPane.add(getJbtnCancelar());

			final JSeparator separator = new JSeparator();
			separator.setBounds(6, 123, 284, 2);
			jContentPane.add(separator);

			datePicker = new JXDatePicker(new Date());
			datePicker.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validar();
				}
			});
			datePicker.setBounds(110, 22, 123, 20);
			datePicker.setFormats("dd/MM/yyyy");

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, -12);
			datePicker.setDate(cal.getTime());

			jContentPane.add(datePicker);

		}
		return jContentPane;
	}

	protected JButton getJbtnAceptar() {
		if (jbtnAceptar == null) {
			jbtnAceptar = new JButton();
			jbtnAceptar.setBounds(new Rectangle(90, 137, 90, 22));
			jbtnAceptar.setText("Aceptar");
			jbtnAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aceptar();
				}
			});
		}
		return jbtnAceptar;
	}

	protected JButton getJbtnCancelar() {
		if (jbtnCancelar == null) {
			jbtnCancelar = new JButton();
			jbtnCancelar.setBounds(new Rectangle(190, 137, 90, 22));
			jbtnCancelar.setText("Cancelar");
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

	private void aceptar() {
		if (validar()) {
			if (compactarDatos(this.datePicker.getDate()))
				this.setVisible(false);
		}
	}

	private boolean validar() {
		return true;
	}

	private boolean compactarDatos(Date date) {
		try {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Compactar los Datos?", "Confirmaci\u00F3n Compactar Datos", JOptionPane.OK_CANCEL_OPTION) == 0) {
				BeltzaApplication.getModel().compact(date);
				return true;
			}

		} catch (BeltzaBusinessException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	public void setVisible(boolean v) {
		super.setVisible(v);
	}

}
