package Testing;

import org.sikuli.script.*;
import org.junit.Test;
import static org.junit.Assert.*;
import Rueckmeldung_Schalter.RSGUIStarter;

public class TesteSchalter_2 {
	
	@SuppressWarnings("static-access")
	@Test
	public void testGUI(){
		
		
		try{
			Screen sc = new Screen();
			RSGUIStarter rsg= new RSGUIStarter();
			
			rsg.starteGUI();
						
			sc.click("images/btn_rueckmelden.PNG");
			
			assertNotNull("Test ok", sc.exists("images/kein_lieferant.PNG"));
			
			
		}catch(Exception e){
			
		}
	}
}
