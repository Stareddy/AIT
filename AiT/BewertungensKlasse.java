package Arrive_in_Time;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Die Klasse BewertungensKlasse() berechnet die Bewertung für 
 * den jeweiligen Lieferanten.
 *
 */
public class BewertungensKlasse {
	
	/**
	 * Für die Datenbank notwendigen Bibliotheken.
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	AiTJFrame aitjFrame;
	
	/**
	 * Für die Datenbank notwendige Attribute.
	 */
	private String spediteur;
	private String auswertung;
	private String tag;
	private String tag_zeit;
	
	static int durchlauf = 1;
	
	public void jobStarten(){
		datenbankBearbeitung();
	}
	
	/**
	 * In der unten aufgeführten Methode wird die Bewertung für den jeweiligen 
	 * Lieferanten erstellt.
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
					.executeQuery("select * from arriveintime.auswertungen");
			
			/**
			 * Untersuchung, ob Werte in der Tabelle "auswertungen" vorhanden sind.
			 */
			while (resultSet.next()) {
					
			      spediteur = resultSet.getString("Lieferant");
			      auswertung = resultSet.getString("Auswertung");
			      tag = resultSet.getString("Tag");
			      tag_zeit = resultSet.getString("tag_zeit");
			      
			      /**
			       * Sind in der Tabelle "auswertungen" Lieferanten mit oben aufgeführten Attributen 
			       * vorhanden, diese sind aber in der Tabelle "bewertung" nicht eingepflegt, werden 
			       * Lieferanten in der Tabelle "bewertung" mit der entsprechenden Bewertung eingetragen.
			       */
			      preparedStatement = conn.prepareStatement("insert ignore into arriveintime.bewertung values (?, ?, ?, ?)");
			      preparedStatement.setString(1, spediteur);
			      preparedStatement.setString(2, auswertung);
			      preparedStatement.setString(3, tag);
			      preparedStatement.setString(4, tag_zeit);;
			      preparedStatement.executeUpdate();
			
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
