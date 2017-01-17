package Rueckmeldung_Schalter;
/**
 * Start der Anwendung, indem die GUI gestartet wird.
 * Nachdem die GUI erstellt wurde, wird die Methode jobStarten() von der Klasse Refresh ausgeführt.
 * 
 * 
 */
public class RSGUIStarter {

	public static void main(String[] args) {
		starteGUI();
	}
	
	public static void starteGUI(){
		RSJFrame frame = new RSJFrame();
		frame.setVisible(true);
		
		RefreshKlasse refresh = new RefreshKlasse(frame);
		refresh.jobStarten();
	}

}
