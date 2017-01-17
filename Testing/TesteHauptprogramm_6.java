package Testing;

import org.sikuli.script.*;
import Arrive_in_Time.AiTGUIStarter;
import org.junit.Test;
import static org.junit.Assert.*;

public class TesteHauptprogramm_6 {
	
@SuppressWarnings("static-access")
@Test
public void testGUI(){
		
		try{
			Screen sc = new Screen();
			AiTGUIStarter hp= new AiTGUIStarter();
			
			hp.starteGUI();
			
			sc.click("images/anwendung_starten.PNG");
			
			assertNotNull("Test ok", sc.exists("images/anwendung_anhalten.PNG"));
			
			assertNotNull("Test nicht", sc.exists("images/anwendung_starten_stop.PNG"));
			
			sc.click("images/anwendung_anhalten.PNG");
			
			Thread.sleep(1200);
			
			assertNotNull("Test ok", sc.exists("images/anwendung_anhalten_stop.PNG"));
			
			assertNotNull("Test nicht", sc.exists("images/anwendung_starten.PNG"));
			
		}catch(Exception e){
			
		}
	}
}
