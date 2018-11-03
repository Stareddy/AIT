package Arrive_in_Time;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * In der EntladezeitKlasse () wird die Entladezeit berechnet. Dabei wird aus der Tabelle "archiv"
 * der Start und das Ende der Entladung genommen und die Differenz davon als Entladezeit berechnet.
 *
 */
public class EntladezeitKlasse {
	
	/**
	 * Für die Datenbankverbindung notwendige Bibliotheken.
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static Statement statement2 = null;
	private static ResultSet resultSet2 = null;
	
	/**
	 * Der Benutzer und das Passwort, um auf die Datenbank zugreifen zu können.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	AiTJFrame aitjFrame;
	
	/**
	 * Für die Datenbanksuche benötigten Attribute.
	 */
	private String spediteur;
	private String entl_start;
	private String entl_ende;
	private String tag;
	private String tag_zeit;
	
	private String spediteur2;
	private String entl_start2;
	private String entl_ende2;
	private String entl_ges;
	private String tag2;
	
	static int durchlauf = 1;
	
	/**
	 * Der int diffMinutes wird für die benötigte Entladezeit verwendet.
	 */
	int diffMinutes;
	
	public void jobStarten(){
		datenbankBearbeitung();
	}
	
	/**
	 * Die Methode datenbankBearbeitung() berechnet die Entladezeit.
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
					.executeQuery("select * from arriveintime.archiv");
			
			/**
			 * Untersuchung, ob Werte in der Tabelle Archiv vorhanden sind.
			 */
			while (resultSet.next()) {
					
			      spediteur = resultSet.getString("Lieferant");
			      entl_start = resultSet.getString("entladung_start");
			      entl_ende = resultSet.getString("entladung_ende");
			      tag = resultSet.getString("tag");
			      tag_zeit = resultSet.getString("tag_zeit");

			      durchlauf = 1;
			      if(spediteur.isEmpty() == false){
			    	  if(entl_start.isEmpty() == false){
			    		  if(entl_ende.isEmpty() == false){
			    			  
			    			  /**
			    			   * Erst wenn der Lieferant die Entladung begonnen und beendet hat, kann die Selektion 
			    			   * aus der Tabelle "entladung" stattfinden.
			    			   */
		    				  statement2 = conn.createStatement();
		    				  
		    				  resultSet2 = statement2
		    							.executeQuery("select * from arriveintime.entladung");
		    				  
		    				  while (resultSet2.next()){
		    					  
		    				      spediteur2 = resultSet2.getString("Lieferant");
		    				      entl_start2 = resultSet2.getString("Start_Entladung");
		    				      entl_ende2 = resultSet2.getString("Ende_Entladung");
		    					  entl_ges = resultSet2.getString("Gesamte_Entladungszeit_min");
		    					  tag2 = resultSet2.getString("Tag");
		    					  
		    					  if(spediteur2.equals(spediteur)){
		    						  if(entl_start2.equals(entl_start)){
		    							  if(entl_ende2.equals(entl_ende)){
		    								  if(tag.equals(tag2)){
		    								  
			    								  /**
												   * Wenn die Entladungszeit bereits berechnet wurde, wird der durchlauf um 1 erhöht.
			    								   */
		    									  if(entl_ges.isEmpty() == false){	
		    										 durchlauf++;
		    										 
		    										 /**
		    										  * Wenn die Entladezeit leer ist, wird sie berechnet und bei dem jeweiligen
		    										  * Lieferanten eingetragen.
		    										  */
		    									  }else if(entl_ges.isEmpty() == true){
		    											SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
		    											try {			    											
		    												Date ankmax = df2.parse(entl_ende);
		    												Date ankist = df2.parse(entl_start);
		    												
		    												long diff = ankmax.getTime() - ankist.getTime();
		    												
		    												diffMinutes = (int) (diff / (60 * 1000)) ;
		    													    									
		    											} catch (ParseException e) {
		    												// TODO Auto-generated catch block
		    												e.printStackTrace();
		    											}
		    										
		    											/**
		    											 * Eintragen der Daten in die Tabelle "entladung".
		    											 */
		    										  preparedStatement = conn.prepareStatement("insert into  arriveintime.entladung values (?, ?, ?, ?, ?, ?)");
		    									      preparedStatement.setString(1, spediteur);
		    									      preparedStatement.setString(2, entl_start);
		    									      preparedStatement.setString(3, entl_ende);
		    									      preparedStatement.setInt(4, diffMinutes);
		    									      preparedStatement.setString(5, tag);
		    									      preparedStatement.setString(6, tag_zeit);
		    									      preparedStatement.executeUpdate();
		    									      durchlauf = durchlauf + 1;
		    									      
		    									  }
		    								  }
		    							  }
		    						  }
		    					  }if(spediteur2.isEmpty()){
		    						  durchlauf = 1;
		    					  }
		    					  
		    					  /**
								   * Wenn der ausgewählte Lieferant in der Tabelle "entladung" nicht vorhanden ist,
		    					   * wird er in dieser if-Schleife eingetragen.
		    					   */
		    				  }if(durchlauf == 1){
	    						  SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
									try {			    											
										Date ankmax = df2.parse(entl_ende);
										Date ankist = df2.parse(entl_start);
										
										long diff = ankmax.getTime() - ankist.getTime();
										
										diffMinutes = (int) (diff / (60 * 1000)) ;
										
									} catch (ParseException e) {
										e.printStackTrace();
									}
								
								  preparedStatement = conn.prepareStatement("insert into  arriveintime.entladung values (?, ?, ?, ?, ?, ?)");
							      preparedStatement.setString(1, spediteur);
							      preparedStatement.setString(2, entl_start);
							      preparedStatement.setString(3, entl_ende);
							      preparedStatement.setInt(4, diffMinutes);
							      preparedStatement.setString(5, tag);
							      preparedStatement.setString(6, tag_zeit);
							      preparedStatement.executeUpdate();
	    						  
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
