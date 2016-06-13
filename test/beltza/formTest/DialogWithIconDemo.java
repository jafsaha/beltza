package beltza.formTest;

/*
 * DialogWithIconDemo.java
 */
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class DialogWithIconDemo extends JFrame {
	private Image icon;
	final private JButton btReal;
	final private JButton btSimulate;
	final private JToolBar toolbar;
	final private Frame frame;
	private MyDialog dialog;

	public DialogWithIconDemo() {
		super("DialogWithIconDemo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		try {
			URL url = new URL("http://www.rgagnon.com/images/gumbyblu.gif");
			icon = javax.imageio.ImageIO.read(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		setIconImage(icon);
		frame = this;
		toolbar = new JToolBar();
		btReal = new JButton();
		btSimulate = new JButton();
		btReal.setText("Real modal");
		btReal.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				btRealActionPerformed(evt);
			}
		});
		toolbar.add(btReal);
		btSimulate.setText("Simulate modal");
		btSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				btSimulateActionPerformed(evt);
			}
		});
		toolbar.add(btSimulate);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	private void btSimulateActionPerformed(final ActionEvent evt) {
		simulate();
	}

	private void btRealActionPerformed(final ActionEvent evt) {
		real();
	}

	public static void main(final String args[]) {
		new DialogWithIconDemo().setVisible(true);
	}

	private void simulate() {
		new Thread(new Runnable() {
			public void run() {
				setEnabled(false);
				new Thread(new Runnable() {
					public void run() {
						dialog = new MyDialog(frame, icon);
					}
				}).start();
				do {
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
					}
				} while (dialog.isVisible());
				System.out.println("simulate OK");
			}
		}).start();
	}

	private void real() {
		final JDialog dia = new JDialog(this, true);
		dia.setSize(200, 200);
		dia.setLocationRelativeTo(this);
		dia.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dia.setSize(200, 200);
			}
		});
		dia.setVisible(true);
		System.out.println("real OK");
	}

	class MyDialog extends JFrame {
		final private Frame parent;

		public MyDialog(final Frame parent, final Image icon) {
			this.parent = parent;
			setIconImage(icon);
			setSize(200, 200);
			setLocationRelativeTo(parent);
			setResizable(false);
			setVisible(true);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(final WindowEvent e) {
					parent.setEnabled(true);
				}

				public void windowIconified(final WindowEvent e) {
					parent.setVisible(true);
				}
			});
		}
	}
}
