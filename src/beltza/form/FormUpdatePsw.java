package beltza.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import beltza.BeltzaApplication;

public class FormUpdatePsw extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static FormUpdatePsw myInstance;
	private JPanel jContentPane = null;
	private JLabel jLabelOldPsw = null;
	private JLabel jLabelNewPsw = null;
	private JLabel jLabelValidateNewPws = null;
	private JPasswordField jtxtOldPsw = null;
	private JPasswordField jtxtNewPsw = null;
	private JPasswordField jtxtValidateNewPsw = null;
	private JButton jbtnAceptar = null;
	private JButton jbtnCancelar = null;
	private JLabel jlblInfo = null;

	public FormUpdatePsw() {
		super();
		initialize();
	}

	// Singleton
	public static FormUpdatePsw getInstance() {
		if (myInstance == null) {
			myInstance = new FormUpdatePsw();
			return myInstance;
		} else {
			return myInstance;
		}
	}

	private void initialize() {
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setSize(300, 200);
		this.setTitle("Actualizaci\u00F3n Clave");
		this.setClosable(true);
		this.setPreferredSize(new Dimension(300, 200));
		this.setLocation(200, 200);
		// UtilGUI.center(this);

		this.setContentPane(getJContentPane());

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelOldPsw = new JLabel();
			jLabelOldPsw.setBounds(new Rectangle(30, 25, 100, 15));
			jLabelOldPsw.setText("Calve actual:");
			jLabelNewPsw = new JLabel();
			jLabelNewPsw.setBounds(new Rectangle(30, 55, 100, 15));
			jLabelNewPsw.setText("Calve nueva:");
			jLabelValidateNewPws = new JLabel();
			jLabelValidateNewPws.setBounds(new Rectangle(30, 85, 100, 13));
			jLabelValidateNewPws.setText("Reingrese clave:");
			jlblInfo = new JLabel("", JLabel.CENTER);
			jlblInfo.setForeground(Color.RED);
			jlblInfo.setBounds(new Rectangle(0, 110, 300, 16));

			jContentPane = new JPanel();
			jContentPane.setLayout(null);

			jContentPane.add(jLabelOldPsw, null);
			jContentPane.add(jLabelNewPsw, null);
			jContentPane.add(jLabelValidateNewPws, null);
			jContentPane.add(jlblInfo, null);

			jContentPane.add(getJtxtOldPsw(), null);
			jContentPane.add(getJtxtNewPsw(), null);
			jContentPane.add(getJtxtValidateNewPsw(), null);

			jContentPane.add(getJbtnAceptar(), null);
			jContentPane.add(getJbtnCancelar(), null);
		}
		return jContentPane;
	}

	protected JTextField getJtxtOldPsw() {
		if (jtxtOldPsw == null) {
			jtxtOldPsw = new JPasswordField();
			jtxtOldPsw.setBounds(new Rectangle(140, 23, 94, 20));
		}
		return jtxtOldPsw;
	}

	protected JTextField getJtxtNewPsw() {
		if (jtxtNewPsw == null) {
			jtxtNewPsw = new JPasswordField();
			jtxtNewPsw.setBounds(new Rectangle(140, 53, 94, 20));
		}
		return jtxtNewPsw;
	}

	protected JTextField getJtxtValidateNewPsw() {
		if (jtxtValidateNewPsw == null) {
			jtxtValidateNewPsw = new JPasswordField();
			jtxtValidateNewPsw.setBounds(new Rectangle(140, 83, 94, 20));
		}
		return jtxtValidateNewPsw;
	}

	protected JButton getJbtnAceptar() {
		if (jbtnAceptar == null) {
			jbtnAceptar = new JButton();
			jbtnAceptar.setBounds(new Rectangle(80, 135, 90, 22));
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
			jbtnCancelar.setBounds(new Rectangle(180, 135, 90, 22));
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
			cambiarClave();
			this.setVisible(false);
		}
	}

	private boolean validar() {

		if (BeltzaApplication.getModel().verifyPassword(
				new String(this.jtxtOldPsw.getPassword())))
			if (new String(this.jtxtNewPsw.getPassword()).equals(new String(
					this.jtxtValidateNewPsw.getPassword())))
				return true;
			else
				jlblInfo.setText("La clave nueva no coincide");
		else
			jlblInfo.setText("Clave actual incorrecta");

		return false;
	}

	private void cambiarClave() {
		BeltzaApplication.getModel().setPassword(
				new String(this.jtxtNewPsw.getPassword()));
	}

	public void setVisible(boolean v) {
		super.setVisible(v);
		if (this.jtxtOldPsw != null)
			this.jtxtOldPsw.setText("");
		if (this.jtxtNewPsw != null)
			this.jtxtNewPsw.setText("");
		if (this.jtxtValidateNewPsw != null)
			this.jtxtValidateNewPsw.setText("");
	}
}
