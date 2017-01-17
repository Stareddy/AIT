package Rueckmeldung_Wareneingang;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RefreshKlasse {
	
	/**
	 * Die Verlinkung des JFrames in der Klasse RefreshKlasse ermöglicht Einträge in dem JFrame. 
	 */
	RWJFrame rwjFrame;
	
	/**
	 * Der String tag speichert den heutigen Tag.
	 */
	public String tag;
	
	/**
	 * Die Erstellung des Konstruktors mit den Parametern des RSJFrames ermöglicht Einträge in dem JFrame. 
	 */
	public RefreshKlasse(RWJFrame rwjframe){
		this.rwjFrame = rwjframe;
	}

	/**
	 * Die Methode jobStarten() wird in der Klasse RSGUIStarter und DatenpflegeKlasse ausgeführt.
	 * Sie aktualisiert in dem TextField "txtLetzteAktualisierung" die Beschriftung.
	 */
	public void jobStarten(){
		
		Date dt2 = new Date();
		SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy HH:mm" );
		
		rwjFrame.textField.setText("Letzte Aktualisierung " + df.format(dt2));
		rwjFrame.textField.setForeground(Color.black);
		
	  }
	
	/**
	 * Die Methode Tag() ist für die Ermittlung des heutigen Tags zuständig. 
	 * Dabei wird nach dem heutigen Tag gesucht, hierbei wird jedoch die Nummer des heutigen Tages ausgegeben.
	 * Diese wird übersetzt und als heutiger Tag gespeichert. Anschließend wird mit der Methode setTag() der
	 * entsprechende Tag an das JFrame gesendet.
	 */
	@SuppressWarnings("deprecation")
	public void Tag(){
		Date dt = new Date();
		dt.getDay();
		switch(dt.getDay()){
		case 1:  tag = "Montag";
			break;
		case 2:  tag = "Dienstag";
		    break;
		case 3:  tag = "Mittwoch";
		    break;
		case 4:  tag = "Donnerstag";
		    break;
		case 5:  tag = "Freitag";
		    break;
		case 6:  tag = "Samstag";
		    break;
		case 7:  tag = "Sonntag"; 
		}
		
		rwjFrame.setTag(tag);
	}
}

