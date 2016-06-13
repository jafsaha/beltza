package beltza.form;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import beltza.business.BeltzaBusinessModel;
import beltza.util.UtilGUI;

public class FormLogin extends JDialog {

	private static final long serialVersionUID = 1L;

	private BeltzaBusinessModel model;

	private JPanel jContentPane = null;

	private JLabel jlblPass = null;

	private JButton jbtnAceptar = null;

	private JButton jbtnCancelar = null;

	private JLabel jlblTitulo = null;

	private JPasswordField jtxtPass = null;

	private JLabel jlblInfo = null;

	public FormLogin(BeltzaBusinessModel m) {
		super();
		model = m;
		initialize();
	}

	public FormLogin(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setSize(300, 200);
		this.setTitle("Acceso a Beltza");
		UtilGUI.center(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("Beltza v" + model.getVersion());
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		
		this.addWindowListener(new WindowListener() {

			public void windowClosing(WindowEvent e) {
				salir();
			}

			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlblTitulo = new JLabel("Ingrese la Contrase\u00F1a", JLabel.CENTER);
			jlblTitulo.setBounds(new Rectangle(60, 23, 189, 16));
			jlblPass = new JLabel();
			jlblPass.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jlblPass.setBounds(new Rectangle(10, 64, 112, 16));
			jlblPass.setText("Contrase\u00F1a:");
			jlblInfo = new JLabel("", JLabel.CENTER);
			jlblInfo.setForeground(Color.RED);
			jlblInfo.setBounds(new Rectangle(60, 95, 189, 16));

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jlblPass);
			jContentPane.add(getJbtnAceptar(), null);
			jContentPane.add(getJbtnCancelar(), null);
			jContentPane.add(jlblTitulo, null);
			jContentPane.add(getJtxtPass());
			jContentPane.add(jlblInfo, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jbtnAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbtnAceptar() {
		if (jbtnAceptar == null) {
			jbtnAceptar = new JButton();
			jbtnAceptar.setBounds(new Rectangle(91, 138, 86, 18));
			jbtnAceptar.setText("Ingresar");
			jbtnAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					validarIngresoSistema();
				}
			});
		}
		return jbtnAceptar;
	}

	/**
	 * This method initializes jbtnCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbtnCancelar() {
		if (jbtnCancelar == null) {
			jbtnCancelar = new JButton();
			jbtnCancelar.setBounds(new Rectangle(185, 138, 91, 18));
			jbtnCancelar.setText("Salir");
			jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return jbtnCancelar;
	}

	/**
	 * This method initializes jtxtPass
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getJtxtPass() {
		if (jtxtPass == null) {
			jtxtPass = new JPasswordField();
			jtxtPass.setBounds(new Rectangle(129, 62, 106, 20));
			jtxtPass.setToolTipText("Sensible a may\u00FAsculas");
			jtxtPass.addFocusListener(new java.awt.event.FocusListener() {
				public void focusGained(FocusEvent e) {
					jlblInfo.setText("");
					jtxtPass.selectAll();
				}

				public void focusLost(FocusEvent e) {

				}
			});
			jtxtPass.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					validarIngresoSistema();
				}
			});
		}
		return jtxtPass;
	}

	private void validarIngresoSistema() {
		if (model.verifyPassword(new String(this.jtxtPass.getPassword())))
			ingresoSistema();
		else
			jlblInfo.setText("Clave incorrecta");
	}

	private void ingresoSistema() {
		this.setVisible(false);
		FormPrincipal fmPrincipal = new FormPrincipal(this.model);
	}

	private void salir() {
		model.release();
		System.exit(0);
	}
}
