package Rueckmeldung_Wareneingang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Die Klasse RWFrameHandler f�ngt die Befehle des GUI ab.
 *
 */
public class RWFrameHandler implements ActionListener   {

	RWJFrame rwjFrame;
	String befehlvomFrame;
	
	public RWFrameHandler(RWJFrame rwjframe){
		this.rwjFrame = rwjframe;
	}

	/**
	 * Die Methode actionPerformed() f�ngt die Events ab. Wurde ein Button bet�tigt,
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
	 * Wurde der Button "Entladung gebonnen" bet�tigt,
	 * so wird von der Klasse EntladungsKlasse1 ein Objekt erstellt und die Methode jobStarten() ausgef�hrt.
	 */
	public void endladungsBeginn(){
		EntladungsKlasse1 beginn = new EntladungsKlasse1(rwjFrame);
		beginn.jobStarten();
	}
	
	/**
	 * Wurde der Button "Entladung beendet" bet�tigt,
	 * so wird von der Klasse EntladungsKlasse2 ein Objekt erstellt und die Methode jobStarten() ausgef�hrt.
	 */
	public void entladungsende(){
		EntladungsKlasse2 ende = new EntladungsKlasse2(rwjFrame);
		ende.jobStarten();
	}
}
