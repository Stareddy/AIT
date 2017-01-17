package Testing;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.sikuli.script.*;
import Rueckmeldung_Wareneingang.RWGUIStarter;

public class TesteWareneingang_1 {

	
	@SuppressWarnings("static-access")
	@Test
	public void testGUI(){
		
		try{
			
			Screen sc = new Screen();
			
			RWGUIStarter rwg= new RWGUIStarter();
			
			rwg.starteGUI();

			sc.click("images/btn_entladung_begonnen.PNG");
			
			assertNotNull("Test ok", sc.exists("images/kein_lieferant_2.PNG"));
			
		}catch(Exception e){
			
		}
	}
}
