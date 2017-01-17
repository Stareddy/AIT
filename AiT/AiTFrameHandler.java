package Arrive_in_Time;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse AiTFrameHandler fängt die Befehle von den Buttons der Klasse AiTJFrame ab.
 */
public class AiTFrameHandler implements ActionListener {
	
	/**
	 * Die Verlinkung zum Frame.
	 */
	AiTJFrame aitjFrame;
	
	/**
	 * Die Aufnahme der Befehle vom Frame.
	 */
	private String befehlvomFrame ="";
	
	/**
	 * Die unten aufgeführten Integer werden dazu verwendet, um die Anwendung beim Betätigen des "Anwendung anhalten"-Buttons anzuhalten.
	 */
	static int ausgangsWert = 3;
	static int neuerWert = 2;
	
	/**
	 * Der Konstruktor wird hierzu verwendet, um vom Frame die Daten zu erhalten.
	 * @param aitjframe
	 */
	public AiTFrameHandler(AiTJFrame aitjframe){
		this.aitjFrame = aitjframe;
	}
	
	/**
	 * Abfangen der Befehle vom Frame. Dabei wird der Befehl in den String gespeichert und in if-Schleifen abgearbeitet.
	 */
	public void actionPerformed(ActionEvent ae) {
		befehlvomFrame = ae.getActionCommand();
		
		if (befehlvomFrame.equals("Anwendung starten")) {
			AnwendungStarten();
		} else

		if (befehlvomFrame.equals("Anwendung anhalten")) {
			AnwendungAnhalten();
		
		}
	}
	
	/**
	 * Die Methode AnwendungStarten() erstellt von der Hauptklasse ein Objekt.
	 * Als nächstes wird die setMethode setWert2() aufgerufen und der Integerwert in der Klasse AitHauptklasse auf 2 gesetzt.
	 * Als nächstes wird ein Threat erstellt von dem Objekt der AiTHauptklasse erstellt und gestartet.
	 */
	private void AnwendungStarten() {
		if(ausgangsWert>neuerWert){
			AiTHauptklasse startJob = new AiTHauptklasse(aitjFrame);
			startJob.setWert2(2);
			Thread serverThread = new Thread(startJob);
			serverThread.setDaemon(true);
			serverThread.start();
		}	
		
	}
	
	/**
	 * Die Methode AnwendungAnhalten() soll beim Betätigen des "Anwendung anhalten"-Button anhalten.
	 * Dabei wird von der AiTHauptklasse ein Objekt erstellt und die Methode setWert() aufgerufen und der Wert auf 7 gesetzt.
	 * Dadurch ist die while-Schleife in der AiTHauptklasse nicht mehr erfüllt und die Anwendung wird angehalten.
	 * Schließlich wird die innere Methode setWert() auf 2 gesetzt.
	 */
	private void AnwendungAnhalten() {
		AiTHauptklasse startJob = new AiTHauptklasse(aitjFrame);
		startJob.setWert(7);
		setWert(2);
			try {
				Thread.sleep(1000);
				aitjFrame.online_offline.setBackground(Color.red);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}

	public void setWert(int x){
		neuerWert=x;
	}
}