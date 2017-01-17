package Rueckmeldung_Wareneingang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Die Klasse RWFrameHandler fängt die Befehle des GUI ab.
 *
 */
public class RWFrameHandler implements ActionListener   {

	RWJFrame rwjFrame;
	String befehlvomFrame;
	
	public RWFrameHandler(RWJFrame rwjframe){
		this.rwjFrame = rwjframe;
	}

	/**
	 * Die Methode actionPerformed() fängt die Events ab. Wurde ein Button betätigt,
	 * so wird der jeweilige Befehl die entsprechende Methode aufrufen.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		befehlvomFrame = ae.getActionCommand();
	
		if(befehlvomFrame.equals("Entladung begonnen")){
			endladungsBeginn();
		}
		else 
		
		if(befehlvomFrame.equals("Entladung beendet")){
			entladungsende();
		}
		else
			System.out.println("Fehler aufgetreten!!!");
	}
	
	/**
	 * Wurde der Button "Entladung gebonnen" betätigt,
	 * so wird von der Klasse EntladungsKlasse1 ein Objekt erstellt und die Methode jobStarten() ausgeführt.
	 */
	public void endladungsBeginn(){
		EntladungsKlasse1 beginn = new EntladungsKlasse1(rwjFrame);
		beginn.jobStarten();
	}
	
	/**
	 * Wurde der Button "Entladung beendet" betätigt,
	 * so wird von der Klasse EntladungsKlasse2 ein Objekt erstellt und die Methode jobStarten() ausgeführt.
	 */
	public void entladungsende(){
		EntladungsKlasse2 ende = new EntladungsKlasse2(rwjFrame);
		ende.jobStarten();
	}
}
