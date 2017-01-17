package Rueckmeldung_Wareneingang;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntladungsKlasse2 {
	
	/**
	 * Für die Datenbankverbindung notwendige Parameter. 
	 * 
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	
	/**
	 * Wichtige Daten für die Datenbankverbindung.
	 * In dem Fall der Username und das Passwort.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	/**
	 * In den Objects werden die Werte aus der JTable übernommen. 
	 */
	Object o1;
	Object o2;
	Object o3;
	
	/**
	 * Die unten aufgeführten Strings sind für die Durchsuchung in der DB wichtig.
	 * Sie nehmen den jeweiligen in der Tabelle ausgewählten Wert auf und werden für die 
	 * Schleifendurchläufe benötigt.
	 */
	private String spediteur;
	private String ank_min;
	private String ank_max;
	private String entl_end;
	private String tag_zeit;
	
	/**
	 * Sollten keine passenden Daten in der Tabelle vorhanden sein, wird die Anzahl von "durchlauf"
	 * nicht erhöht.
	 */
	private int durchlauf = 1;
	
	/**
	 *  Verlinkung mit dem JFrame.
	 */
	RWJFrame rwjFrame;
	
	/**
	 * In dem Konstruktor wird der Frame eingebunden. Dadurch werden die Befehle und VEränderungen, die 
	 * in der Klasse gemacht werden, in dem JFrame sichtbar bzw. möglich.
	 * @param rwjframe
	 */
	public EntladungsKlasse2(RWJFrame rwjframe){
		this.rwjFrame = rwjframe;
	}
	
	/**
	 * Verlinkung der RefreshKlasse, damit die Methoden aus der Klasse aufgerufen werden können.
	 */
	RefreshKlasse refresh;
	
	/**
	 * Die nächsten drei Code-Zeilen ermitteln das Datum und die Uhrzeit.
	 * Diese werden für die Datenbankeinträge benötigt.
	 */
	Date dt1 = new Date();
	SimpleDateFormat df1 = new SimpleDateFormat( "HH:mm" );
	SimpleDateFormat df2 = new SimpleDateFormat( "dd.MM.yyyy" );
	
	/**
	 * Die Methode jobStarten() wird im RWFrameHandler aufgerufen. Dabei werden zwei Methoden aufgerufen, die die
	 * Datenbank mit Werten fühlen.
	 */
	public void jobStarten(){
		datenausJTable();
		datenbankEintrag();
	}

	/**
	 * In der Methode datenausJTable() wird zuerst ausgelesen, welche Zeile ausgewählt wurde.
	 * Danach werden die einzelnen Werte in Object aufgenommen. Dies ist für die Methode datenbankEintrag()
	 * notwendig.
	 */
	private void datenausJTable() {
		try{
			o1 = rwjFrame.table.getValueAt(rwjFrame.table.getSelectedRow(), 0);
			o2 = rwjFrame.table.getValueAt(rwjFrame.table.getSelectedRow(), 1);
			o3 = rwjFrame.table.getValueAt(rwjFrame.table.getSelectedRow(), 2);
		}catch(Exception e){
			rwjFrame.textField.setText("Es wurde kein Lieferant ausgewählt oder alle Spediteure sind bereits rückgemeldet.");
			rwjFrame.textField.setForeground(Color.red);
			return;
		}
		
	}

	/**
	 * In der Methode datenbankEintrag() wird die Verbindung mit der DB hergestellt und die Daten in die Tablelle Archiv
	 * aufgenommen.
	 */
	private void datenbankEintrag() {
		refresh = new RefreshKlasse(rwjFrame);
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
			 * Untersuchung ob Werte in der Tabelle Archiv vorhanden sind.
			 */
			while (resultSet.next()) {
		    	  
			      spediteur = resultSet.getString("Lieferant");
			      ank_min = resultSet.getString("ankunft_min");
			      ank_max = resultSet.getString("ankunft_max");
			      entl_end = resultSet.getString("entladung_ende");
			      tag_zeit = resultSet.getString("tag");
			      
			      if(o1.equals(spediteur)){
			    	  if(o2.equals(ank_min)){
			    		  if(o3.equals(ank_max)){
			    			  if(df2.format(dt1).equals(tag_zeit)){
			    				  
			    				  /**
			    				   * Wenn in der Tabelle alle vier Werte vorhanden sind wird die Variable durchlauf auf 2 gesetzt.
			    				   */
			    				  durchlauf = durchlauf + 1;
			    				  
			    				  /**
			    				   * Als nächstes wird der letzte noch leere Wert (Entladung-ende) eingetragen. 
			    				   */
			    				  if(entl_end.isEmpty()){
			    					  preparedStatement = conn.prepareStatement("update arriveintime.archiv set entladung_ende=? where Lieferant=? and ankunft_min=? and ankunft_max=? and tag=?");
			    				      preparedStatement.setString(1, df1.format(dt1));
			    				      preparedStatement.setString(2, spediteur);
			    				      preparedStatement.setString(3, ank_min);
			    				      preparedStatement.setString(4, ank_max);
			    				      preparedStatement.setString(5, tag_zeit);
			    				      preparedStatement.executeUpdate();
			    				      
			    				      /**
			    				       * Nach dem Eintragen werden die Buttons wieder in die Ursprüngliche Farbe gewechselt.
			    				       */
			    				      rwjFrame.btnNewButton.setBackground(Color.green);
			    				      rwjFrame.button.setBackground(Color.red);
			    				      rwjFrame.table.setEnabled(true);
			    				      
			    				      /**
			    				       * Während der Button "Entladung begonnen" und die JTable wieder freigegeben werden, wird der Button "Entladung beendet" wieder deaktiviert.
			    				       */
			    				      rwjFrame.btnNewButton.setEnabled(true);
			    				      rwjFrame.button.setEnabled(false);
			    				      rwjFrame.removeRow(rwjFrame.table.getSelectedRow());
					    			  rwjFrame.setBounds(100, 100, 1001, 750);
					    			  rwjFrame.setBounds(100, 100, 1000, 750);
					    			  rwjFrame.table.getSelectionModel().clearSelection();
					    			  refresh.jobStarten();
					    			  durchlauf = durchlauf + 1;
					    			  return;
			    				  }
			    			  }  
			    		  }
			    	  }
			      }else if(durchlauf == 1){
			    	  	rwjFrame.textField.setText("Dieser Lieferant wurde weder Rückgemeldet noch hat die Entladung angefangen!!!");
						rwjFrame.textField.setForeground(Color.red);
			      }	  			      
			}
			
			/**
			 * Wenn keine Werte vorhanden sind, werden diese in die Tabelle eingetragen.
			 */
			 if(durchlauf != 1){
			      rwjFrame.removeRow(rwjFrame.table.getSelectedRow());
    			  rwjFrame.setBounds(100, 100, 1001, 750);
    			  rwjFrame.setBounds(100, 100, 1000, 750);
    			  rwjFrame.table.getSelectionModel().clearSelection();
    			  refresh.jobStarten();
		      }
			
		} catch (Exception e) {
			rwjFrame.textField.setText("Auswahl in einem kurzen Zeitfenster nicht erlaubt!!!");
			rwjFrame.textField.setForeground(Color.red);
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
