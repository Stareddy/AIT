package Testing;

import org.sikuli.script.*;
import org.junit.Test;
import static org.junit.Assert.*;
import Rueckmeldung_Schalter.RSGUIStarter;

public class TesteSchalter_3 {

	@SuppressWarnings("static-access")
	@Test
	public void testGUI(){
		
		try{
			Screen sc = new Screen();
			RSGUIStarter rsg= new RSGUIStarter();
			
			rsg.starteGUI();
			
			sc.click("images/auswahl_lieferant.png");
			
			sc.click("images/btn_rueckmelden.PNG");
			
			sc.click("images/auswahl_lieferant_2.png");
			
			sc.click("images/btn_rueckmelden.PNG");
			
			assertNotNull("Test ok", sc.exists("images/auswahl_lieferant_2.png"));
			
			assertNotNull("Test ok", sc.exists("images/lieferant_zeitfenster.PNG"));
			
		}catch(Exception e){
			
		}
	}
}

