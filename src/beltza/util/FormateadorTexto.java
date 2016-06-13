package beltza.util;

import java.awt.Toolkit;


public class FormateadorTexto{ 
int caracteresMaximos; 
String unString;

	public FormateadorTexto(int caracteresMaximos, String unString ) { 
		this.caracteresMaximos = caracteresMaximos; 
		this.unString = unString;
	} 

	public String formatear(){ 
		if(this.unString.length()>0){
			if (this.unString.length() <= caracteresMaximos){
				this.unString=unString.toUpperCase();
				return this.unString;
			} else{
				Toolkit.getDefaultToolkit().beep();
				return this.unString=this.unString.substring(0, this.caracteresMaximos).toUpperCase();
							
			}		
		}
		return this.unString;
 
	} 

} 
