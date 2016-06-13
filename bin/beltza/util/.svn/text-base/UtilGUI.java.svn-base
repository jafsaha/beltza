package beltza.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class UtilGUI {

	public static final String BUTTON_BUSCAR = "Buscar";
	public static final String RECORD_LIMIT_ADD_TEXT = " (*)";
	public static final String RECORD_LIMIT_TOOLTIP = "Se muestran solo los 200 primeros registros, por favor especifique su b\u00FAsqueda";

	/** Centre a Window, Frame, JFrame, Dialog, etc. */
	public static void centre(Window w) {
		// After packing a Frame or Dialog, centre it on the screen.
		Dimension us = w.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
		int newX = (them.width - us.width) / 2;
		int newY = (them.height - us.height) / 2;
		w.setLocation(newX, newY);
	}

	/**
	 * Center a Window, Frame, JFrame, Dialog, etc., but do it the American
	 * Spelling Way :-)
	 */
	public static void center(Window w) {
		UtilGUI.centre(w);
	}

	public static void center(JInternalFrame j) {
		// After packing a Frame or Dialog, centre it on the screen.
		Dimension us = j.getSize(), them = j.getParent().getSize();
		int newX = (them.width - us.width) / 2;
		int newY = (them.height - us.height) / 2;
		j.setLocation(newX, newY);
	}

	/** Maximize a window, the hard way. */
	public static void maximize(Window w) {
		Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
		w.setBounds(0, 0, them.width, them.height);
	}

	public static String formatFecha(Date fecha) {
		if (fecha == null)
			return "";
		return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	}

	public static Date parseFecha(String source) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		return sdf.parse(source);
	}

	public static NumberFormat numberFormat() {
		NumberFormat format2;
		format2 = NumberFormat.getInstance();
		format2.setMinimumFractionDigits(0);
		format2.setMaximumFractionDigits(20);
		return format2;
	}

	public static NumberFormat numberFormat(int cantFractionDigits) {
		NumberFormat format2;
		format2 = NumberFormat.getInstance();
		format2.setMinimumFractionDigits(0);
		format2.setMaximumFractionDigits(cantFractionDigits);
		return format2;
	}

	public static String formatNumero(double numero) {
		return new DecimalFormat("###,##0.###").format(numero);
	}

	public static MaskFormatter getEspecieTextMaskFormatter() {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("UUUU");
		} catch (ParseException e1) {

		}
		return formatter;
	}

	public static void setJTextFieldToUppercase(JTextField field) {
		field.setText(field.getText().toUpperCase());
	}

	public static String getMessageFromException(Throwable e) {
		int i = 0;
		while (e != null && i < 100) {
			if (e.getMessage() != null) {
				return e.getMessage().replaceAll("beltza.business.BeltzaBusinessException", "");
			} else {
				e = e.getCause();
			}
			i++;
		}
		return "Error no detallado";
	}
}
