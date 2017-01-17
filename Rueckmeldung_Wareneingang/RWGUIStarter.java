package Rueckmeldung_Wareneingang;

/**
 * Die Klasse RWGUIStarter ist für den Programmstart zuständig. Nachdem das JFrame erstellt wurde, 
 * wird die Klasse RefreshKlasse aufgerufen. 
 *
 */
public class RWGUIStarter {
	public static void main(String[]args){
		starteGUI();
		
	}
	
	public static void starteGUI(){
		RWJFrame frame = new RWJFrame("");
		frame.setVisible(true);
		
		RefreshKlasse refresh = new RefreshKlasse(frame);
		refresh.jobStarten();
	}
}
