package Rueckmeldung_Schalter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RSFrameHandler implements ActionListener {
	/**
	 * Verweis auf das JFrame. Dadurch wird die Kommunikation und Interaktion
	 * zwischen der Klasse RSFrameHandler und RSJFrame ermöglicht.
	 */
	RSJFrame rsJFrame;
	
	/**
	 * Der Befehl der beim Betätigen des Buttons gesendet wird, wird in den String
	 * "befehlvomFrame" abgelegt.
	 */
	String befehlvomFrame = "";
	
	/**
	 * Durch den Konstruktor wird der RSJFrame in die Klasse integriert.
	 */
	public RSFrameHandler(RSJFrame rsJFrame){
		this.rsJFrame = rsJFrame;
	}
	
	/**
	 * Wurde ein Button an der GUI betätigt, wird der Befehl in der Methode
	 * actionPerformed() abgefangen und abgearbeitet. 
	 */
	public void actionPerformed(ActionEvent ae) {
		befehlvomFrame = ae.getActionCommand();
			
		/**
		 * Wurde "Rückmelden" ausgewählt, wird die Methode datenpflegeMethode() ausgeführt.
		 */
		if(befehlvomFrame.equals("Rückmelden")){
			datenpflegeMethode();
			
		} else
			
			System.out.println("Fehler ist aufgetreten!!!");
	}
	
	/**
	 * In der Methode datenpflegeMethode() wird von der Klasse DatenpflegeKlasse
	 * ein Objekt erstellt und die Methode jobStarten() gestartet.
	 */
	public void datenpflegeMethode(){
		DatenpflegeKlasse datenpflege = new DatenpflegeKlasse(rsJFrame);
		datenpflege.jobStarten();
	}
}
