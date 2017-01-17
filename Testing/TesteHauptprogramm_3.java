package Testing;

import org.sikuli.script.*;
import Arrive_in_Time.AiTGUIStarter;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesteHauptprogramm_3 {
	
@SuppressWarnings("static-access")
@Test
public void testGUI(){
		
		try{
			Screen sc = new Screen();
			AiTGUIStarter hp= new AiTGUIStarter();
			
			hp.starteGUI();
			
			sc.click("images/auswertungen.PNG");
			
			assertNotNull("Test ok", sc.exists("images/auswertungen_tabelle.PNG"));
			
		}catch(Exception e){
			
		}
	}
}
