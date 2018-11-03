package Arrive_in_Time;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Die Klasse P�nktlichkeitsKlasse() hat die Aufgabe die P�nktlichkeit der Lieferanten zu berechnen.
 * Dabei wird eine Verbindung zu der Datenbank hergestellt und mithilfe der Werte 
 * aus der Tabelle "archiv" die P�nktlichkeit berechnet.
 *
 */
public class P�nktlichkeitsKlasse {
	
	/**
     * F�r die Datenbankverbindung notwendige Parameter.
	 * 
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	private static Statement statement2 = null;
	private static ResultSet resultSet2 = null;
	
	/**
     * Wichtige Daten f�r die Datenbankverbindung.
	 * In dem Fall der Username und das Passwort.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	/**
     * Die unten aufgef�hrten Strings sind f�r die Durchsuchung in der DB wichtig.
     * Sie nehmen den jeweiligen in der Tabelle ausgew�hlten Wert auf und werden f�r die
     * Schleifendurchl�ufe ben�tigt.
	 */
	private String spediteur;
	private String ank_min;
	private String ank_max;
	private String ank_ist;
	private String tag;
	private String tag_zeit;
	
	private String spediteur2;
	private String ank_min2;
	private String ank_max2;
	private String ank_ist2;
    private String p�nktlichkeit;
	private String tag2;
	
	/**
     * Der int durchlauf wird dann verwendet, wenn 1. keine P�nktlichkeit berechnet wurde,
     * 2. wenn die P�nktlichkeit berechnet wurde, jedoch nicht f�r den ausgew�hlten Lieferanten.
	 */
	static int durchlauf = 1;
	
	/**
	 * Der SimpleDateFormat wird dazu verwendet, um die aus der Tabelle "archiv" entnommenen 
	 * Zeiten (die davor als String gespeichert wurden) wieder in das Zeitformat umzuwandeln.
	 */
	SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
	
	public void jobStarten(){
		datenbankBearbeitung();
	}
	
	/**
     * In der Methode datenbankBearbeitung() wird die P�nktlichkeit berechnet.
	 */
	public void datenbankBearbeitung(){
		try{
            // Mit diesem Befehl, wird der Treiber f�r die Datenbank geladen.
			Class.forName("com.mysql.jdbc.Driver");

            // Als n�chstes erfolgt die Verbindung der Datenbank.
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/arriveintime?",user, password);

            // Durch die Statements werden die SQL-Abfragen erm�glicht.
			statement = conn.createStatement();

            // Als n�chsts werden die Ergebnisse der SQL-Abfrage erzeugt.
			resultSet = statement
					.executeQuery("select * from arriveintime.archiv");
			
			/**
			 * Untersuchung, ob Werte in der Tabelle Archiv vorhanden sind.
			 */
			while (resultSet.next()) {
					
			      spediteur = resultSet.getString("Lieferant");
			      ank_min = resultSet.getString("ankunft_min");
			      ank_max = resultSet.getString("ankunft_max");
			      ank_ist = resultSet.getString("ankunft_ist");
			      tag = resultSet.getString("tag");
			      tag_zeit = resultSet.getString("tag_zeit");
			      
			      /**
			       * Wurde ein Lieferant in der Tabelle "archiv" gefunden, wird der Wert des 
			       * int durchlaufs auf 1 gesetzt.
			       */
			      durchlauf = 1;
			      
			      /**
                   * Als n�chstes wird �berpr�ft, ob die Werte null/leer sind.
			       */
			      if(spediteur.isEmpty() == false){
			    	  if(ank_min.isEmpty() == false){
			    		  if(ank_max.isEmpty() == false){
			    			  if(ank_ist.isEmpty() == false){
			    				  
			    				  /**
                                   * Sind die Werte nicht leer wird die Tabelle P�nktlichkeit ausgew�hlt.
			    				   */
			    				  statement2 = conn.createStatement();
			    				  
			    				  resultSet2 = statement2
			    							.executeQuery("select * from arriveintime.puenktlichkeit");
			    				  
			    				  /**
                                   * Als n�chstes werden die Werte aus der Tabelle P�nktlichkeit selektiert.
			    				   */
			    				  while ( resultSet2.next()){
			    					  
			    				      spediteur2 = resultSet2.getString("Lieferant");
			    				      ank_min2 = resultSet2.getString("Min_Ankunftszeit");
			    				      ank_max2 = resultSet2.getString("Max_Ankunftszeit");
			    				      ank_ist2 = resultSet2.getString("Ist_Ankunftszeit");
                                      p�nktlichkeit = resultSet2.getString("P�nktlichkeit");
			    					  tag2 = resultSet2.getString("Tag");
			    					  
			    					  /**
                                       * Als n�chstes wird �berpr�ft, ob der Lieferant in der Tabelle
                                       * "archiv" gleich dem Lieferanten in der Tabelle P�nktlichkeit entspricht.
			    					   */
			    					  if(spediteur2.equals(spediteur)){
			    						  if(ank_min2.equals(ank_min)){
			    							  if(ank_max2.equals(ank_max)){
			    								  if(ank_ist2.equals(ank_ist)){
			    									  if(tag.equals(tag2)){			    										    												    									  
				    									  /**
                                                           * Wenn der Lieferant vorhanden und die P�nktlichkeit vorhanden ist,
                                                           * soll der durchlauf um 1 erh�ht werden.
				    									   */
                                                          if (p�nktlichkeit.isEmpty() == false) {
				    										 durchlauf++;
				    										 
				    										 /**
                                                              * Wenn der Lieferant vorhanden, aber die P�nktlichkeit leer ist,
				    										  * wird sie in dieser if-Schleife berechnet und in die Tabelle
				    										  * "puenktlichkeit" eingetragen.
				    										  */
                                                          } else if (p�nktlichkeit.isEmpty() == true) {
				    											SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
				    											
				    											try {
	
					    											/**
                                                                     * �bersetzen des Strings in das Format Date.
					    											 */
				    												Date ankmax = df2.parse(ank_max);
				    												Date ankist = df2.parse(ank_ist);
				    												
				    												long diff = ankmax.getTime() - ankist.getTime();
				    												
				    												/**
                                                                     * Berechnung der P�nktlichkeit.
				    												 */
				    												int diffMinutes = (int) (diff / (60 * 1000)) ;
				    												
				    												if(diffMinutes>=60){
                                                                        p�nktlichkeit = "100 %";
				    													
				    												}
				    												if(diffMinutes<=60){
				    													if(diffMinutes>=30){
                                                                            p�nktlichkeit = "100 %";
				    													}
				    													
				    												}if(diffMinutes<=29){
				    													if(diffMinutes>=23){
                                                                            p�nktlichkeit = "75 %";
				    													}
				    												}if(diffMinutes<=22){
				    													if(diffMinutes>=15){
                                                                            p�nktlichkeit = "50 %";
				    													}
				    													
				    												}if(diffMinutes<=14){
				    													if(diffMinutes>=1){
                                                                            p�nktlichkeit = "25 %";
				    													}
				    														
				    												}if(diffMinutes<=0){
                                                                        p�nktlichkeit = "0 %";
				    												}
				    												
				    											} catch (ParseException e) {
				    												e.printStackTrace();
				    											}
				    										
				    											
				    											/**
				    											 * Eintragen der Daten in die Tabelle "puenktlichkeit".
				    											 */
				    										  preparedStatement = conn.prepareStatement("insert into  arriveintime.puenktlichkeit values (?, ?, ?, ?, ?, ?, ? ))");
				    									      preparedStatement.setString(1, spediteur);
				    									      preparedStatement.setString(2, ank_min);
				    									      preparedStatement.setString(3, ank_max);
				    									      preparedStatement.setString(4, ank_ist);
                                                              preparedStatement.setString(5, p�nktlichkeit);
				    									      preparedStatement.setString(6, tag);
				    									      preparedStatement.setString(7, tag_zeit);
				    									      preparedStatement.executeUpdate();
				    									      durchlauf = durchlauf + 1;
				    									      
				    									  }
			    									  }
			    								  }
			    							  }
			    						  }
			    						  
			    						  /**
			    						   * Wenn der Lieferant nicht gefunden wurde, wird durchlauf auf 1 gesetzt.
			    						   */
			    					  }if(spediteur2.isEmpty()){
			    						  durchlauf = 1;
			    					  }
			    					  
			    					  /**
                                       * Wenn der durchlauf dem Wert 1 entspricht, wird f�r den momentan ausgew�hlten Lieferant
                                       * die P�nktlichkeit berechnet.
			    					   */
			    				  }if(durchlauf == 1){
		    						  SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
										try {			    											
											Date ankmax = df2.parse(ank_max);
											Date ankist = df2.parse(ank_ist);
											
											long diff = ankmax.getTime() - ankist.getTime();
											
											int diffMinutes = (int) (diff / (60 * 1000)) ;
											if(diffMinutes>=60){
                                                p�nktlichkeit = "100 %";
												
											}
											if(diffMinutes<=60){
												if(diffMinutes>=30){
                                                    p�nktlichkeit = "100 %";
												}
												
											}if(diffMinutes<=29){
												if(diffMinutes>=23){
                                                    p�nktlichkeit = "75 %";
												}
											}if(diffMinutes<=22){
												if(diffMinutes>=15){
                                                    p�nktlichkeit = "50 %";
												}
												
											}if(diffMinutes<=14){
												if(diffMinutes>=1){
                                                    p�nktlichkeit = "25 %";
												}
													
											}if(diffMinutes<=0){
                                                p�nktlichkeit = "0 %";
											}
											
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
									  preparedStatement = conn.prepareStatement("insert into  arriveintime.puenktlichkeit values (?, ?, ?, ?, ?, ?, ?)");
								      preparedStatement.setString(1, spediteur);
								      preparedStatement.setString(2, ank_min);
								      preparedStatement.setString(3, ank_max);
								      preparedStatement.setString(4, ank_ist);
                                      preparedStatement.setString(5, p�nktlichkeit);
								      preparedStatement.setString(6, tag);
								      preparedStatement.setString(7, tag_zeit);
								      preparedStatement.executeUpdate();
		    						  
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
