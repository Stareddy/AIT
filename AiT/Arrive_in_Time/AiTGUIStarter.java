package Arrive_in_Time;
/**
 * Hier wird ein Objekt von der Klasse AiTJFrame erstellt.
 * Dabei wird eine GUI, die für den Benutzer sichtbar wird, erstellt.
 *
 */
public class AiTGUIStarter {

	public static void main(String[] args) {
		starteGUI();
	}
	
	public static void starteGUI(){
		Arrive_in_Time.AiTJFrame frame = new Arrive_in_Time.AiTJFrame("");
		frame.setVisible(true);
	}

}
