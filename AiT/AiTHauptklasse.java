package Arrive_in_Time;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.IOException;
/**
 * Die AiTHauptklasse ist f�r die Erstellung der Tabellen puenktlichkeit, entladung, auswertungen und bewertung zust�ndig.
 * Au�erdem gibt die Anwendung von hier aus das Signal in Form von Farbwechsel des Buttons "online/offline" zur�ck.  
 *
 */
public class AiTHauptklasse implements Runnable{
	
	/**
	 * Verlinkung zum Frame.
	 */
	AiTJFrame aitjFrame;
	
	/**
	 *  Die unten aufgef�hrten Integer werden dazu verwendet, um die Anwendung beim Bet�tigen des "Anwendung anhalten"-Buttons anzuhalten.
	 */
	static int ausgangsWert = 3;
	static int neuerWert = 2;

	/**
	 *  Der Konstruktor wird hierzu verwendet, um vom Frame die Daten zu erhalten.
	 * @param aitjframe
	 */
	public AiTHauptklasse(AiTJFrame aitjframe) {
		this.aitjFrame = aitjframe;
	}
	
	/**
	 * Die run() Methode ruft die Methode anwendungJobStarten() auf.
	 * Sollte ein Fehler auftreten, wird dieser Fehler vom catch-Block abgefangen.
	 */
	public void run() {

		try {
			anwendungJobStarten();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hier findet die eigentliche Programmlogik statt. Solange die Bedingungen in den while-Schleifen erf�llt sind, laufen die while-Schleifen durch.
	 * Wird der Anhalte-Button gedr�ckt, werden die Werte in so fern ver�ndert, dass die while-Schleifen nicht mehr erf�llt sind und die Anwendung dadurch 
	 * unterbrochen wird.
	 * @throws IOException
	 */
	private void anwendungJobStarten() throws IOException {

		/**
		 * Die �u�ere while-Schleife ist hierf�r zust�ndig, dass die inneren while-Schleifen immer wieder aufgerufen werden.
		 */
		while(ausgangsWert>neuerWert){
			
			/**
			 * Die erste while-Schleife ruft zuerst die Methode lebensZeichen() auf, um anzuzeigen, dass die Anwendung aktiv ist.
			 * Als n�chstes wird ein Objekt von der Klasse P�nktlichkeitsKlasse() erstellt und die Methode jobStarten() aufgerufen.
			 */
			while(ausgangsWert>neuerWert){
				lebensZeichen();
				
				P�nktlichkeitsKlasse p�nktlich = new P�nktlichkeitsKlasse();
				p�nktlich.jobStarten();
				break;
			}
			/**
			 * Die erste while-Schleife ruft zuerst die Methode lebensZeichen() auf, um anzuzeigen, dass die Anwendung aktiv ist.
			 * Als n�chstes wird ein Objekt von der Klasse EntladezeitKlasse() erstellt und die Methode jobStarten() aufgerufen.
			 */
			while(ausgangsWert>neuerWert){
				lebensZeichen();
				
				EntladezeitKlasse entlade = new EntladezeitKlasse();
				entlade.jobStarten();
				break;
			}
			
			/**
			 * Die erste while-Schleife ruft zuerst die Methode lebensZeichen() auf, um anzuzeigen, dass die Anwendung aktiv ist.
			 * Als n�chstes wird ein Objekt von der Klasse AuswertungsKlasse() erstellt und die Methode jobStarten() aufgerufen.
			 */
			while(ausgangsWert>neuerWert){
				lebensZeichen();
				
				AuswertungsKlasse auswerte = new AuswertungsKlasse();
				auswerte.jobStarten();
				break;
			}
			
			/**
			 * Die erste while-Schleife ruft zuerst die Methode lebensZeichen() auf, um anzuzeigen, dass die Anwendung aktiv ist.
			 * Als n�chstes wird ein Objekt von der Klasse BewertungensKlasse() erstellt und die Methode jobStarten() aufgerufen.
			 */
			while(ausgangsWert>neuerWert){
				lebensZeichen();
				
				BewertungensKlasse bewerte = new BewertungensKlasse();
				bewerte.jobStarten();
				break;
			}
		}

	
	}
	
	/**
	 * Die Methode lebensZeichen() verwendet die Methode Thread.sleep(), um die Methode f�r 1,5 sec anzuhalten.
	 * Davor wird jedoch die Farbe des Buttons gewechselt(auf die Farbe SystemColor.menu). Nach erneutem Aufruf der Methode Thread.sleep() wird die Farbe erneut gewechselt(auf gr�n).
	 */
	private void lebensZeichen(){
		try {
			aitjFrame.online_offline.setBackground(SystemColor.menu);
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			aitjFrame.online_offline.setBackground(Color.green);
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Die Methode setWert2() wird in der AiTFrameHandler-Klasse dazu verwendet, um beim Bet�tigen des "Anwendung starten"-Buttons die Anwendung starten zu k�nnen.
	 * Dabei wird der Integer Wert von neuerWert auf einen niedrigeren Wert gesetzt, als der Integer ausgangsWert. Dadurch ist die while-Schleife wieder erf�llt.
	 * @param x
	 */
	public void setWert2(int x){
		neuerWert=x;
	}

	/**
	 * Die Methode setWert() wird in der AiTFrameHandler-Klasse dazu verwendet, um beim Bet�tigen des "Anwendung anhalten"-Buttons die Anwendung anhalten zu k�nnen.
	 * Dabei wird der Integer Wert von neuerWert auf einen h�heren Wert gesetzt, als der Integer ausgangsWert. Dadurch ist die while-Schleife nicht mehr erf�llt.
	 * @param x
	 */
	public void setWert(int x){
		neuerWert=x;
	}
	
	

}
