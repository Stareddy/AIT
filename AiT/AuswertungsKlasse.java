package Arrive_in_Time;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Die AuswertungsKlasse() hat die Aufgabe Werte aus zwei Tabellen zu holen,
 * diese zu vergleichen und in eine dritte Tabelle einzutragen.
 *
 */
public class AuswertungsKlasse {
	
	/**
	 * Für die Datenbankverbindung notwendige Parameter. 
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static Statement statement2 = null;
	private static ResultSet resultSet2 = null;

	/**
	 * Wichtige Daten für die Datenbankverbindung.
	 * In dem Fall der Username und das Passwort.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	/**
	 * Die unten aufgeführten Strings sind für die Durchsuchung in der DB wichtig.
	 * Sie nehmen den jeweiligen in der Tabelle ausgewählten Wert auf und werden für die 
	 * Schleifendurchläufe benötigt.
	 */
	private String spediteur;
	private String ank_min;
	private String ank_max;
	private String ank_ist;
	private String tag;
	private String tag_zeit;
	
	private String spediteur2;
	private String tag2;
	private String tag_zeit2;
	private int entl_ges2;
	
	private String auswertung;
	
	public void jobStarten(){
		datenbankBearbeitung();
	}
	
	/**
	 * Die Methode datenbankBearbeitung() stellt die Verbindung zur Datenbank her und 
	 * trägt die Daten in die Tabelle auswertungen ein. 
	 */
	public void datenbankBearbeitung(){
		try{
			// Mit diesem Befehl, wird der Treiber für die Datenbank geladen.
			Class.forName("com.mysql.jdbc.Driver");
			
			// Als nächstes erfolgt die Verbindung der Datenbank.
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/arriveintime?",user, password);
			
			// Durch die Statements werden die SQL-Abfragen ermöglicht.
			statement = conn.createStatement();
			  
			// Als nächsts werden die Ergebnisse der SQL-Abfrage erzeugt.
			resultSet = statement
					.executeQuery("select * from arriveintime.puenktlichkeit");
			
			/**
			 * Untersuchung ob Werte in der Tabelle "puenktlichkeit" vorhanden sind.
			 */
			while (resultSet.next()) {
		    	  
			      spediteur = resultSet.getString("Lieferant");
			      ank_min = resultSet.getString("Min_Ankunftszeit");
			      ank_max = resultSet.getString("Max_Ankunftszeit");
			      ank_ist = resultSet.getString("Ist_Ankunftszeit");
			      tag  = resultSet.getString("Tag");
			      tag_zeit  = resultSet.getString("tag_zeit");
			      
			      /**
			       * Nachdem die Werte aus der Tabelle "puenktlichkeit" entnommen wurden,
			       * wird die Verbindung zur der Tabelle "entladung" hergestellt.
			       */
			      	// Durch die Statements werden die SQL-Abfragen ermöglicht.
					statement2 = conn.createStatement();
					  
					// Als nächsts werden die Ergebnisse der SQL-Abfrage erzeugt.
					resultSet2 = statement2
							.executeQuery("select * from arriveintime.entladung");
					
					while(resultSet2.next()){
					      spediteur2 = resultSet2.getString("Lieferant");
					      entl_ges2 = resultSet2.getInt("Gesamte_Entladungszeit_min");
					      tag2  = resultSet2.getString("Tag");
					      tag_zeit2  = resultSet2.getString("tag_zeit");
					      
					      /**
					       * Nachdem die Werte entnommen wurden, werden die Werte aus der Tabelle
					       * "puenktlichkeit" und der Tabelle "entladung" miteinander verglichen.
					       */
					      if(spediteur.equals(spediteur2)){
					    	  if(tag.equals(tag2)){
					    		  if(tag_zeit.equals(tag_zeit2)){
					    			  SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
					    			  
					    			  /**
					    			   * Wenn die Daten gleich sind, wird die Pünktlichkeit berechnet.
					    			   * Dabei wird erst die Differenz zwischen der maximalen Ankunftszeit und
					    			   * Ist-Ankunftszeit berechnet. 
					    			   * Als nächstes wird die Differenz minus die Entladezeit_gesamt berechnet.
					    			   * Das Ergebnis wird in den unteren if-Schleifen ausgewertet.
					    			   */
										try {			    											
											Date ankmax = df2.parse(ank_max);
											Date ankist = df2.parse(ank_ist);
											
											long diff = ankmax.getTime() - ankist.getTime();
											
											int diffMinutes = (int) (diff / (60 * 1000)) ;
											
											int diffende = diffMinutes - entl_ges2;
											
											if(diffende>=0){
												auswertung = "100 %";
											}
											if(diffende == -1){
												auswertung = "90 %";
											}
											if(diffende == -2){
												auswertung = "80 %";
											}
											if(diffende == -3){
												auswertung = "70 %";
											}
											if(diffende == -4){
												auswertung = "60 %";
											}
											if(diffende == -5){
												auswertung = "50 %";
											}
											if(diffende == -6){
												auswertung = "40 %";
											}
											if(diffende == -7){
												auswertung = "30 %";
											}	
											if(diffende == -8){
												auswertung = "20 %";
											}
											if(diffende == -9){
												auswertung = "10 %";
											}
											if(diffende<=-10){
												auswertung = "0 %";
											}
											
											/**
											 * Das Ergebnis wird in die Tabelle "auswertungen" eingetragen.
											 */
											preparedStatement = conn.prepareStatement("insert ignore into arriveintime.auswertungen values (?, ?, ?, ?, ?, ?, ?, ?)");
  									      	preparedStatement.setString(1, spediteur);
  									      	preparedStatement.setString(2, ank_min);
  									      	preparedStatement.setString(3, ank_max);
  									      	preparedStatement.setString(4, ank_ist);
  									      	preparedStatement.setInt(5, entl_ges2);
  									      	preparedStatement.setString(6, auswertung);
  									      	preparedStatement.setString(7, tag);
  									      	preparedStatement.setString(8, tag_zeit);
  									      	preparedStatement.executeUpdate();
											
										} catch (ParseException e) {
											e.printStackTrace();
										}
					    		  }
					    		  
					    		  
					    	  }
					      }
					      
					}
			      
			      
			      
			      
			}
			
		} catch (Exception e) {
			e.printStackTrace();
            return;
	    } finally {
	      close();
	      
	    }
	}
	
	/**
	 * Mit der Methode close() werden die noch offenen Datenbankverbindungen geschlossen.
	 */
	private void close() {
		try {
		      if (resultSet != null) {
		        resultSet.close();
		      }

		      if (statement != null) {
		        statement.close();
		      }

		      if (conn != null) {
		        conn.close();
		      }
		    } catch (Exception e) {

		    }
	}

}
