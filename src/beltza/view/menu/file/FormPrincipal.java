package beltza.view.menu.file;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import beltza.BeltzaApplication;
import beltza.business.BeltzaBusinessModel;
import beltza.business.event.BeltzaEvent;
import beltza.business.event.BeltzaObserver;
import beltza.business.event.ChangeType;
import beltza.commons.BookBuilder;
import beltza.domain.Dia;
import beltza.domain.Especie;
import beltza.dto.OperacionDTO;
import beltza.form.FormCierresList;
import beltza.form.FormCompactData;
import beltza.form.FormCtaCteClientesLista;
import beltza.form.FormEspeciesList;
import beltza.form.FormMovimientosLista;
import beltza.form.FormOpenDay;
import beltza.form.FormOperBinariaLista;
import beltza.form.FormOperUnariaLista;
import beltza.form.FormReportsSelect;
import beltza.form.FormUpdatePsw;
import beltza.report.BeltzaReportManager;
import beltza.util.UtilGUI;

/**
 * @author Ale
 */
public class FormPrincipal extends JFrame implements BeltzaObserver {

	/**
	 * Atributos de Clase
	 */
	private static final long serialVersionUID = 1L;
	private static FormPrincipal myInstance;
	private JLabel infoLabel;
	private BeltzaBusinessModel model;
	/**
	 * Atributos
	 */
	private JPanel jContentPanelPrincipal = null;
	private javax.swing.JMenuBar jJMenuBar = null;
	private JMenu jMenuArchivo = null;
	private JMenu jMenuUtilidades = null;
	private JMenu jMenuOperaciones = null;
	private JMenu jMenuListados = null;
	private JMenu jMenuAyuda = null;
	private JMenuItem jMenuItemAbrirDia = null;
	private JMenuItem jMenuItemCerrarDia = null;
	private JMenuItem jMenuItemReabrirDia = null;
	private JMenuItem jMenuItemBorrarDia = null;
	private JMenuItem jMenuItemCompactar = null;
	private JMenuItem jMenuItemSalir = null;
	private JMenuItem jMenuItemAforo = null;
	private JMenuItem jMenuItemCierres = null;
	private JMenuItem jMenuItemClaves = null;
	private JMenuItem jMenuItemBulkUpload = null;
	private JMenuItem jMenuItemLista = null;
	private JMenuItem jMenuItemCuentas = null;
	private JMenuItem jMenuItemIngrEgr = null;
	private JMenuItem jMenuItemCtaCteClientes = null;
	private JMenuItem jMenuItemReportes = null;
	private JMenuItem jMenuItemReporteDirecto = null;
	private JMenuItem jMenuItemAcerca = null;

	private FormUpdatePsw frmUpdatePsw = null;
	private FormEspeciesList frmEspeciesList = null;
	private FormCierresList frmCierresList = null;
	private FormOperUnariaLista frmOperUnariaList = null;
	private FormOperBinariaLista frmOperBinariaList = null;
	private FormMovimientosLista frmMovimientosList = null;
	private FormCtaCteClientesLista frmCtaCteClientesList = null;
	private FormReportsSelect frmReportes = null;
	private FormOpenDay frmOpenDay = null;
	private FormCompactData frmCompactData = null;

	private JDesktopPane jDesktopPane = null;
	private JPanel jPanelEstadoFinanciera = null;
	private JLabel jlblBillete = null;
	private JTextField jtxtbillete = null;
	private JLabel jlblPesos = null;
	private JTextField jtxtPesos = null;

	/**
	 * This is the default constructor
	 */
	public FormPrincipal(BeltzaBusinessModel m) {
		super();
		this.model = m;
		this.myInstance = this;
		initialize();
	}

	public FormPrincipal() {
		super();
		initialize();
	}

	// Singleton
	public static FormPrincipal getInstance() {
		if (myInstance == null) {
			myInstance = new FormPrincipal();
			return myInstance;
		} else {
			return myInstance;
		}
	}

	private void initialize() {
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//		try {
		//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		//				if ("Nimbus".equals(info.getName())) {
		//					UIManager.setLookAndFeel(info.getClassName());
		//					break;
		//				}
		//			}
		//		} catch (UnsupportedLookAndFeelException e) {
		//			//logger.debug(e);
		//		} catch (ClassNotFoundException e) {
		//			//logger.debug(e);
		//		} catch (InstantiationException e) {
		//			//logger.debug(e);
		//		} catch (IllegalAccessException e) {
		//			//logger.debug(e);
		//		}

		this.setContentPane(getJContentPanelPrincipal());
		this.setJMenuBar(getJJMenuBar());
		this.setBounds(0, 0, 1024, 768);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMaximumSize(new Dimension(1024, 768));
		this.setMinimumSize(new Dimension(1024, 768));
		this.setName("framePrincipal");
		this.setPreferredSize(new Dimension(1024, 768));
		this.setResizable(true);

		// UtilGUI.maximize(this);

		// TODO: obtener versión de otro lado (Properties?)
		this.setTitle("Beltza v" + model.getVersion());
		this.setVisible(true);

		this.frmUpdatePsw = FormUpdatePsw.getInstance();
		this.frmUpdatePsw.pack();

		this.frmEspeciesList = new FormEspeciesList();
		// this.frmEspeciesList.pack();
		this.frmEspeciesList.setLocation(60, 20);

		this.frmCierresList = new FormCierresList();
		// this.frmCierresList.pack();
		this.frmCierresList.setLocation(60, 20);

		this.frmOperUnariaList = new FormOperUnariaLista();
		// this.frmOperUnariaList.pack();
		this.frmOperUnariaList.setLocation(40, 20);

		this.frmOperBinariaList = new FormOperBinariaLista();
		// this.frmOperBinariaList.pack();
		this.frmOperBinariaList.setLocation(40, 20);

		this.frmMovimientosList = new FormMovimientosLista();
		// this.frmMovimientosList.pack();
		this.frmMovimientosList.setLocation(40, 20);

		this.frmCtaCteClientesList = new FormCtaCteClientesLista();
		// this.frmCtaCteClientesList.pack();
		this.frmCtaCteClientesList.setLocation(50, 20);

		this.frmReportes = FormReportsSelect.getInstance();
		this.frmReportes.pack();
		this.frmReportes.setLocation(50, 50);

		this.frmOpenDay = FormOpenDay.getInstance();
		this.frmOpenDay.pack();

		this.frmCompactData = FormCompactData.getInstance();
		this.frmCompactData.pack();

		this.frmUpdatePsw.setVisible(false);
		this.frmEspeciesList.setVisible(false);
		this.frmCierresList.setVisible(false);
		this.frmOperUnariaList.setVisible(false);
		this.frmOperBinariaList.setVisible(false);
		this.frmMovimientosList.setVisible(false);
		this.frmCtaCteClientesList.setVisible(false);
		this.frmReportes.setVisible(false);
		this.frmOpenDay.setVisible(false);
		this.frmCompactData.setVisible(false);

		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				jJMenuBar.setBounds(0, 0, jContentPanelPrincipal.getWidth(), 23);
				jPanelEstadoFinanciera.setBounds(1, 1, jContentPanelPrincipal.getWidth(), 34);
				jDesktopPane.setBounds(0, 35, jContentPanelPrincipal.getWidth(), jContentPanelPrincipal.getHeight() - 23);
			}
		});

		this.jDesktopPane.add(this.frmUpdatePsw);
		this.jDesktopPane.add(this.frmEspeciesList);
		this.jDesktopPane.add(this.frmCierresList);
		this.jDesktopPane.add(this.frmOperUnariaList);
		this.jDesktopPane.add(this.frmOperBinariaList);
		this.jDesktopPane.add(this.frmMovimientosList);
		this.jDesktopPane.add(this.frmCtaCteClientesList);
		this.jDesktopPane.add(this.frmReportes);
		this.jDesktopPane.add(this.frmOpenDay);
		this.jDesktopPane.add(this.frmCompactData);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});

		BeltzaApplication.getModel().addObserver(this, ChangeType.ESPECIE);
		BeltzaApplication.getModel().addObserver(this, ChangeType.DIA);

		actualizarPanelStock();
		actualizarPanelInfo();
		actualizarMenuesCierres();
	}

	/**
	 * This method initializes jContentPanelPrincipal
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPanelPrincipal() {
		if (jContentPanelPrincipal == null) {
			jContentPanelPrincipal = new JPanel();
			jContentPanelPrincipal.setLayout(null);
			jContentPanelPrincipal.setName("jpanelPrincipal");
			jContentPanelPrincipal.setBackground(new Color(153, 153, 153));
			jContentPanelPrincipal.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jContentPanelPrincipal.add(getJDesktopPane(), null);
			jContentPanelPrincipal.add(getJPanelEstadoFinanciera(), null);
		}

		return jContentPanelPrincipal;
	}

	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	private javax.swing.JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new javax.swing.JMenuBar();
			jJMenuBar.add(getJMenuArchivo());
			jJMenuBar.add(getJMenuUtilidades());
			jJMenuBar.add(getJMenuOperaciones());
			jJMenuBar.add(getJMenuListados());
			jJMenuBar.add(getJMenuAyuda());
			jJMenuBar.setPreferredSize(new Dimension(793, 23));
			jJMenuBar.setBackground(SystemColor.control);
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenuArchivo
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuArchivo() {
		if (jMenuArchivo == null) {
			jMenuArchivo = new JMenu();
			jMenuArchivo.add(getJMenuItemAbrirDia());
			jMenuArchivo.add(getJMenuItemCerrarDia());
			jMenuArchivo.addSeparator();
			jMenuArchivo.add(getJMenuItemReabrirDia());
			jMenuArchivo.add(getJMenuItemBorrarDia());
			jMenuArchivo.addSeparator();
			jMenuArchivo.add(getJMenuItemCompactar());
			jMenuArchivo.addSeparator();
			jMenuArchivo.add(getJMenuItemSalir());
			jMenuArchivo.setText("Archivo");
			jMenuArchivo.setFont(new Font("Dialog", Font.BOLD, 12));
			jMenuArchivo.setName("jMenuArchivo");
			jMenuArchivo.setBackground(SystemColor.control);
		}
		return jMenuArchivo;
	}

	private JMenu getJMenuUtilidades() {
		if (jMenuUtilidades == null) {
			jMenuUtilidades = new JMenu();
			jMenuUtilidades.add(getJMenuItemAforo());
			jMenuUtilidades.add(getJMenuItemCierres());
			jMenuUtilidades.add(getjMenuItemBulkUpload());
			jMenuUtilidades.addSeparator();
			jMenuUtilidades.add(getJMenuItemClaves());
			jMenuUtilidades.setText("Utilidades");
			jMenuUtilidades.setBackground(SystemColor.control);
		}
		return jMenuUtilidades;
	}

	/**
	 * This method initializes jMenuOperaciones
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuOperaciones() {
		if (jMenuOperaciones == null) {
			jMenuOperaciones = new JMenu();
			jMenuOperaciones.setText("Operaciones");
			jMenuOperaciones.setBackground(SystemColor.control);
			jMenuOperaciones.add(getJMenuItemIngrEgr());
			jMenuOperaciones.add(getJMenuItemLista());
			jMenuOperaciones.addSeparator();
			jMenuOperaciones.add(getJMenuItemMovimientos());
			jMenuOperaciones.add(getJMenuItemCtaCteCliente());

		}
		return jMenuOperaciones;
	}

	private JMenu getJMenuListados() {
		if (jMenuListados == null) {
			jMenuListados = new JMenu();
			jMenuListados.add(getJMenuItemReportes());
			jMenuListados.setText("Listados");
			jMenuListados.setBackground(SystemColor.control);
			jMenuListados.addSeparator();
			jMenuListados.add(getJMenuReporteDirecto());
			// JMenu pp = new JMenu("Reports");
			// pp.add(getJMenuItemReporteDirecto());
			// jMenuListados.add(pp);
		}
		return jMenuListados;
	}

	/**
	 * This method initializes jMenuAyuda
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuAyuda() {
		if (jMenuAyuda == null) {
			jMenuAyuda = new JMenu();
			jMenuAyuda.add(getJMenuItemAcerca());
			jMenuAyuda.setText("Ayuda");
			jMenuAyuda.setBackground(SystemColor.control);
		}
		return jMenuAyuda;
	}

	private JMenuItem getJMenuItemAbrirDia() {
		if (jMenuItemAbrirDia == null) {
			jMenuItemAbrirDia = new JMenuItem();
			jMenuItemAbrirDia.setText("Abrir D\u00EDa");
			jMenuItemAbrirDia.setBackground(SystemColor.control);
			jMenuItemAbrirDia.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					openDay();
				}
			});
		}
		return jMenuItemAbrirDia;
	}

	private JMenuItem getJMenuItemReabrirDia() {
		if (jMenuItemReabrirDia == null) {
			jMenuItemReabrirDia = new JMenuItem();
			jMenuItemReabrirDia.setText("Re-abrir D\u00EDa");
			jMenuItemReabrirDia.setBackground(SystemColor.control);
			jMenuItemReabrirDia.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					reopenDay();
				}
			});
		}
		return jMenuItemReabrirDia;
	}

	private JMenuItem getJMenuItemBorrarDia() {
		if (jMenuItemBorrarDia == null) {
			jMenuItemBorrarDia = new JMenuItem();
			jMenuItemBorrarDia.setText("Borrar \u00FAltimo d\u00EDa");
			jMenuItemBorrarDia.setBackground(SystemColor.control);
			jMenuItemBorrarDia.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteDay();
				}
			});
		}
		return jMenuItemBorrarDia;
	}

	private JMenuItem getJMenuItemCompactar() {
		if (jMenuItemCompactar == null) {
			jMenuItemCompactar = new JMenuItem();
			jMenuItemCompactar.setText("Compactar Datos");
			jMenuItemCompactar.setBackground(SystemColor.control);
			jMenuItemCompactar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					compactData();
				}
			});
		}
		return jMenuItemCompactar;
	}

	private JMenuItem getJMenuItemCerrarDia() {
		if (jMenuItemCerrarDia == null) {
			jMenuItemCerrarDia = new JMenuItem();
			jMenuItemCerrarDia.setText("Cerrar D\u00EDa");
			jMenuItemCerrarDia.setBackground(SystemColor.control);
			jMenuItemCerrarDia.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					closeDay();
				}
			});
		}
		return jMenuItemCerrarDia;
	}

	/**
	 * This method initializes jMenuItemSalir
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSalir() {
		if (jMenuItemSalir == null) {
			jMenuItemSalir = new JMenuItem();
			jMenuItemSalir.setText("Salir");
			jMenuItemSalir.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));
			jMenuItemSalir.setBackground(SystemColor.control);
			jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					salir();
				}
			});
		}
		return jMenuItemSalir;
	}

	private JMenuItem getJMenuItemAforo() {
		if (jMenuItemAforo == null) {
			jMenuItemAforo = new JMenuItem();
			jMenuItemAforo.setText("Aforo");
			jMenuItemAforo.setBackground(SystemColor.control);
			jMenuItemAforo.setAccelerator(KeyStroke.getKeyStroke('1', InputEvent.CTRL_MASK));
			jMenuItemAforo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmEspeciesList.setVisible(true);
					frmEspeciesList.toFront();
					try {
						frmEspeciesList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}

		return jMenuItemAforo;
	}

	private JMenuItem getJMenuItemCierres() {
		if (jMenuItemCierres == null) {
			jMenuItemCierres = new JMenuItem();
			jMenuItemCierres.setText("Cierres");
			jMenuItemCierres.setBackground(SystemColor.control);
			jMenuItemCierres.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmCierresList.setVisible(true);
					frmCierresList.toFront();
					try {
						frmCierresList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}

		return jMenuItemCierres;
	}

	private JMenuItem getJMenuItemClaves() {
		if (jMenuItemClaves == null) {
			jMenuItemClaves = new JMenuItem();
			jMenuItemClaves.setText("Clave");
			jMenuItemClaves.setBackground(SystemColor.control);
			jMenuItemClaves.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmUpdatePsw.setVisible(true);
					frmUpdatePsw.toFront();
					frmUpdatePsw.setFocusable(true);
				}
			});
		}
		return jMenuItemClaves;
	}

	private JMenuItem getjMenuItemBulkUpload() {
		if (jMenuItemBulkUpload == null) {
			jMenuItemBulkUpload = new JMenuItem();
			jMenuItemBulkUpload.setText("Upload");
			jMenuItemBulkUpload.setBackground(SystemColor.control);
			jMenuItemBulkUpload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();

					if (actionCancelled(fileChooser)) {
						return;
					}

					FileInputStream fis = readFile(fileChooser);

					List<OperacionDTO> operaciones = new BookBuilder().build(fis);
					for (OperacionDTO op : operaciones) {
						model.addOperacion(op);
					}
				}
			});
		}
		return jMenuItemBulkUpload;
	}

	private boolean actionCancelled(JFileChooser fileChooser) {
		return fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION;
	}

	private FileInputStream readFile(JFileChooser fileChooser) {
		try {
			return new FileInputStream(fileChooser.getSelectedFile());
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException("Fail reading file." + fileChooser.getName(), fnfe);
		} catch (IOException ioe) {
			throw new RuntimeException("Fail reading file." + fileChooser.getName(), ioe);
		}
	}

	private JMenuItem getJMenuItemMovimientos() {
		if (jMenuItemCuentas == null) {
			jMenuItemCuentas = new JMenuItem();
			jMenuItemCuentas.setText("Liquidacion");
			jMenuItemCuentas.setBackground(SystemColor.control);
			jMenuItemCuentas.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK));
			jMenuItemCuentas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmMovimientosList.setVisible(true);
					frmMovimientosList.toFront();
					try {
						frmMovimientosList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}
		return jMenuItemCuentas;
	}

	private JMenuItem getJMenuItemIngrEgr() {
		if (jMenuItemIngrEgr == null) {
			jMenuItemIngrEgr = new JMenuItem();
			jMenuItemIngrEgr.setText("Alta Movimientos");
			jMenuItemIngrEgr.setAccelerator(KeyStroke.getKeyStroke('M', InputEvent.CTRL_MASK));
			jMenuItemIngrEgr.setBackground(SystemColor.control);
			jMenuItemIngrEgr.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmOperUnariaList.setVisible(true);
					frmOperUnariaList.toFront();
					try {
						frmOperUnariaList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}
		return jMenuItemIngrEgr;
	}

	private JMenuItem getJMenuItemCtaCteCliente() {
		if (jMenuItemCtaCteClientes == null) {
			jMenuItemCtaCteClientes = new JMenuItem();
			jMenuItemCtaCteClientes.setText("Cta.Cte. Clientes");
			jMenuItemCtaCteClientes.setBackground(SystemColor.control);
			jMenuItemCtaCteClientes.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
			jMenuItemCtaCteClientes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmCtaCteClientesList.setVisible(true);
					frmCtaCteClientesList.toFront();
					try {
						frmCtaCteClientesList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}
		return jMenuItemCtaCteClientes;
	}

	private JMenuItem getJMenuItemReportes() {
		if (jMenuItemReportes == null) {
			jMenuItemReportes = new JMenuItem();
			jMenuItemReportes.setText("Generaci\u00F3n Reportes");
			jMenuItemReportes.setBackground(SystemColor.control);
			jMenuItemReportes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmReportes.setVisible(true);
					frmReportes.toFront();
					try {
						frmReportes.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}
		return jMenuItemReportes;
	}

	private JMenuItem getJMenuItemReporteDirecto() {
		if (jMenuItemReporteDirecto == null) {
			jMenuItemReporteDirecto = new JMenuItem();
			jMenuItemReporteDirecto.setText("Reporte R\u00E1pido");
			jMenuItemReporteDirecto.setBackground(SystemColor.control);
			jMenuItemReporteDirecto.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Dia dia = BeltzaApplication.getModel().getDiaAbierto();
					if (dia == null)
						JOptionPane.showMessageDialog(null, "No hay un d\u00EDa Abierto");
					else {
						ResourceBundle bundle = ResourceBundle.getBundle(BeltzaApplication.APPLICATION_FILE_NAME);
						String repo = bundle.getString("quickReport.codigo");

						try {
							BeltzaReportManager.getInstance().generarReportesBeltza(repo, dia);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			});
		}
		return jMenuItemReporteDirecto;
	}

	private JMenuItem getJMenuReporteDirecto() {
		JMenu jMenuReporteDirecto = new JMenu("Reportes Rapidos");

		ResourceBundle bundle = ResourceBundle.getBundle(BeltzaApplication.APPLICATION_FILE_NAME);

		try {
			String reso = "";
			int i = 1;
			while (bundle.getString("quickReport.codigo." + new Integer(i).toString()) != null) {
				reso = bundle.getString("quickReport.codigo." + new Integer(i).toString());
				String shortcut = null;
				try {
					shortcut = bundle.getString("quickReport.codigo." + new Integer(i).toString() + ".shortcut");
				} catch (Exception e) {
				}
				JMenuItem jmi = new JMenuItem();
				jmi.setText(reso);
				jmi.setBackground(SystemColor.control);
				if (shortcut != null && shortcut.trim().length() > 0)
					jmi.setAccelerator(KeyStroke.getKeyStroke(shortcut.toCharArray()[0], InputEvent.CTRL_MASK));
				jmi.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						String report = e.getActionCommand();
						Dia dia = BeltzaApplication.getModel().getDiaAbierto();
						if (dia == null)
							JOptionPane.showMessageDialog(null, "No hay d\u00EDa Abierto");
						else {
							try {
								BeltzaReportManager.getInstance().generarReportesBeltza(report, dia);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					}
				});

				jMenuReporteDirecto.add(jmi);
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return jMenuReporteDirecto;
	}

	private JMenuItem getJMenuItemAcerca() {
		if (jMenuItemAcerca == null) {
			jMenuItemAcerca = new JMenuItem();
			jMenuItemAcerca.setText("Acerca de...");
			jMenuItemAcerca.setBackground(SystemColor.control);
		}
		return jMenuItemAcerca;
	}

	/**
	 * This method initializes jDesktopPane
	 *
	 * @return javax.swing.JDesktopPane
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
			jDesktopPane.setBackground(SystemColor.activeCaptionBorder);
			jDesktopPane.setSize(new Dimension(1006, 647));
			jDesktopPane.setPreferredSize(new Dimension(1006, 647));
			jDesktopPane.setLocation(new Point(0, 38));
		}
		return jDesktopPane;
	}

	/**
	 * This method initializes jPanelEstadoFinanciera
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEstadoFinanciera() {
		if (jPanelEstadoFinanciera == null) {
			jlblPesos = new JLabel();
			jlblPesos.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jlblPesos.setBounds(new Rectangle(197, 9, 62, 18));
			jlblPesos.setText("Pesos:");
			jlblBillete = new JLabel();
			jlblBillete.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jlblBillete.setBounds(new Rectangle(15, 10, 54, 16));
			jlblBillete.setText("Billete:");
			jPanelEstadoFinanciera = new JPanel();
			jPanelEstadoFinanciera.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			jPanelEstadoFinanciera.setLayout(null);
			jPanelEstadoFinanciera.setLocation(new Point(0, 0));
			jPanelEstadoFinanciera.setSize(new Dimension(1006, 38));
			jPanelEstadoFinanciera.setPreferredSize(new Dimension(1006, 38));
			jPanelEstadoFinanciera.add(jlblBillete);
			jPanelEstadoFinanciera.add(getJtxtbillete());
			jPanelEstadoFinanciera.add(jlblPesos);
			jPanelEstadoFinanciera.add(getJtxtPesos());

			infoLabel = new JLabel();
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setForeground(new Color(255, 0, 0));
			infoLabel.setFont(new Font("", Font.BOLD, 12));
			infoLabel.setBounds(406, 11, 345, 14);
			jPanelEstadoFinanciera.add(infoLabel);
		}
		return jPanelEstadoFinanciera;
	}

	/**
	 * This method initializes jtxtbillete
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtxtbillete() {
		if (jtxtbillete == null) {
			jtxtbillete = new JTextField();
			jtxtbillete.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jtxtbillete.setBounds(new Rectangle(75, 8, 116, 19));
			jtxtbillete.setEditable(false);
			jtxtbillete.setForeground(new Color(0, 153, 51));
			jtxtbillete.setFont(new Font("Dialog", Font.BOLD, 12));
			jtxtbillete.setText("0");

		}
		return jtxtbillete;
	}

	/**
	 * This method initializes jtxtPesos
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtxtPesos() {
		if (jtxtPesos == null) {
			jtxtPesos = new JTextField();
			jtxtPesos.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jtxtPesos.setBounds(new Rectangle(268, 8, 116, 20));
			jtxtPesos.setText("0");
			jtxtPesos.setFont(new Font("Dialog", Font.BOLD, 12));
			jtxtPesos.setForeground(new Color(0, 0, 153));
			jtxtPesos.setEditable(false);
		}
		return jtxtPesos;
	}

	private JMenuItem getJMenuItemLista() {
		if (jMenuItemLista == null) {
			jMenuItemLista = new JMenuItem();
			jMenuItemLista.setText("Alta Operaciones");
			jMenuItemLista.setBackground(SystemColor.control);
			jMenuItemLista.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
			jMenuItemLista.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					frmOperBinariaList.setVisible(true);
					frmOperBinariaList.toFront();
					try {
						frmOperBinariaList.setSelected(true);
					} catch (PropertyVetoException e1) {

					}
				}
			});
		}
		return jMenuItemLista;
	}

	public BeltzaBusinessModel getModel() {
		return model;
	}

	public void setModel(BeltzaBusinessModel model) {
		this.model = model;
	}

	private void openDay() {
		Dia dia = BeltzaApplication.getModel().getDiaAbierto();
		if (dia != null)
			JOptionPane.showMessageDialog(null,
					"Ya hay un d\u00EDa Abierto (" + UtilGUI.formatFecha(dia.getFechaAsociada()) + "). Debe cerrarlo antes de abrir otro.");
		else {
			this.frmOpenDay.setVisible(true);

			// if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea
			// Abrir el d\u00EDa?", "Confirmaci Abrir",
			// JOptionPane.OK_CANCEL_OPTION) == 0) {
			// BeltzaApplication.getModel().abrirDia();
			// dia = BeltzaApplication.getModel().getDiaAbierto();
			// JOptionPane.showMessageDialog(null, "D\u00EDa Abierto correctamente ("
			// + UtilGUI.format(dia.getFechaAsociada()) + ")");
			// }
		}
	}

	private void reopenDay() {
		Dia dia = BeltzaApplication.getModel().getDiaAbierto();
		if (dia != null)
			JOptionPane.showMessageDialog(null,
					"Ya hay un d\u00EDa Abierto (" + UtilGUI.formatFecha(dia.getFechaAsociada()) + "). Debe cerrarlo antes de abrir otro.");
		else {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Re-abrir el \u00FAltimo d\u00EDa cerrado?", "Confirmaci\u00F3n Abrir",
					JOptionPane.OK_CANCEL_OPTION) == 0) {
				BeltzaApplication.getModel().reabrirUltimoDia();
				dia = BeltzaApplication.getModel().getDiaAbierto();
				JOptionPane.showMessageDialog(null, "D\u00EDa Abierto correctamente (" + UtilGUI.formatFecha(dia.getFechaAsociada()) + ")");
			}
		}
	}

	private void deleteDay() {
		try {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Borrar el \u00FAltimo d\u00EDa cerrado?", "Confirmaci\u00F3n Borrar",
					JOptionPane.OK_CANCEL_OPTION) == 0) {
				boolean pudoBorrar = BeltzaApplication.getModel().borrarUltimoDiaCerrado();
				if (!pudoBorrar)
					JOptionPane.showMessageDialog(null, "No se pudo borrar el d\u00EDa.");
				else {
					JOptionPane.showMessageDialog(null, "D\u00EDa borrado correctamente.");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void closeDay() {
		Dia dia = BeltzaApplication.getModel().getDiaAbierto();
		if (dia == null)
			JOptionPane.showMessageDialog(null, "No hay ning\u00FAn d\u00EDa Abierto. Debe abrir el d\u00EDa.");
		else {
			if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Cerrar el d\u00EDa " + UtilGUI.formatFecha(dia.getFechaAsociada()) + "?",
					"Confirmaci\u00F3n Cerrar", JOptionPane.OK_CANCEL_OPTION) == 0) {
				BeltzaApplication.getModel().cerrarDia();
				JOptionPane.showMessageDialog(null, "D\u00EDa Cerrado correctamente.");
			}
		}
	}

	private void compactData() {
		this.frmCompactData.setVisible(true);
	}

	public void modelChanged(BeltzaEvent event) {
		if (this.isVisible()) {
			if (ChangeType.DIA.equals(event.getChangeType())) {
				actualizarMenuesCierres();
				actualizarPanelInfo();
			} else if (ChangeType.ESPECIE.equals(event.getChangeType())) {
				actualizarPanelStock();
			}
		}
	}

	private void actualizarPanelStock() {
		// Especie esp = model.getEspecieByCodigo("BB");
		final Especie esp = model.getEspecieDeReferencia();
		if (esp != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					jtxtbillete.setText(UtilGUI.numberFormat(2).format(esp.getStock()));
				}
			});
		}
		if (model.getEspeciesMonitoreo() != null && model.getEspeciesMonitoreo().size() > 0) {
			final Especie espMon = model.getEspeciesMonitoreo().iterator().next();
			if (esp != null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						jtxtPesos.setText(UtilGUI.numberFormat(2).format(espMon.getStock()));
					}
				});
			}
		}
	}

	private void actualizarMenuesCierres() {
		final Boolean isDiaAbierto = (BeltzaApplication.getModel().getDiaAbierto() != null);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jMenuItemAbrirDia.setEnabled(!isDiaAbierto);
				jMenuItemReabrirDia.setEnabled(!isDiaAbierto);
				jMenuItemCerrarDia.setEnabled(isDiaAbierto);
				jMenuItemBorrarDia.setEnabled(!isDiaAbierto);
			}
		});
	}

	private void actualizarPanelInfo() {
		Dia dia = BeltzaApplication.getModel().getDiaAbierto();
		String t = null;
		if (dia == null)
			t = "No hay ning\u00FAn d\u00EDa Abierto";
		else
			t = "D\u00EDa actualmente abierto: " + UtilGUI.formatFecha(dia.getFechaAsociada());

		final String text = t;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				infoLabel.setText(text);
			}
		});

	}

	private void salir() {
		if (JOptionPane.showConfirmDialog(null, "¿Confirma que desea Salir?", "Salir", JOptionPane.OK_CANCEL_OPTION) == 0) {
			try {
				model.release();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.exit(0);
			}
		}
	}
}
