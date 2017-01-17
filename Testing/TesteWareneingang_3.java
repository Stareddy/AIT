package Testing;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.sikuli.script.*;
import Rueckmeldung_Wareneingang.RWGUIStarter;

public class TesteWareneingang_3 {

	
	@SuppressWarnings("static-access")
	@Test
	public void testGUI(){
		
		try{
			
			Screen sc = new Screen();
			
			RWGUIStarter rwg= new RWGUIStarter();
			
			rwg.starteGUI();
			
			sc.click("images/auswahl_lieferant.png");

			sc.click("images/btn_entladung_begonnen.PNG");
			
			assertNotNull("Test ok", sc.exists("images/auswahl_lieferant_leer.PNG"));
			
			assertNotNull("Test ok", sc.exists("images/lieferant_bereits_entladen.PNG"));
			
		}catch(Exception e){
			
		}
	}
}

