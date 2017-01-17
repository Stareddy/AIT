package Rueckmeldung_Wareneingang;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntladungsKlasse1 {
	
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
	private String entl_beg;
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
	public EntladungsKlasse1(RWJFrame rwjframe){
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
		
		if(o1 == null){
			
		}
		else{
			datenbankEintrag();
		}
		
	
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
			rwjFrame.textField.setText("Es wurde kein Lieferant ausgewählt oder es sind bereits alle Lieferanten entladen worden.");
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
			      entl_beg = resultSet.getString("entladung_start");
			      entl_end = resultSet.getString("entladung_ende");
			      tag_zeit = resultSet.getString("tag");
			      
			      if(o1.equals(spediteur)){
			    	  if(o2.equals(ank_min)){
			    		  if(o3.equals(ank_max)){
			    			  if(df2.format(dt1).equals(tag_zeit)){
			    				  
			    				  /**
			    				   * Wenn in der Tabelle alle vier Werte vorhanden sind wird die Variable durchlauf auf 4 gesetzt.
			    				   */
			    				  durchlauf = 2+2;
			    				  
			    				  /**
			    				   * Wenn in der Spalte "entladung_start" kein Wert in der Zeile, in der die vier oberen Variablen vorhanden sind, eingetragen ist, wird die Variable entl_beg in 
			    				   * diesen leeren Platz eingetragen. 
			    				   */
			    				  if(entl_beg.isEmpty()){
			    					  
			    					  /**
			    					   * Der SQL-Statement.
			    					   * Jedes Fragezeihen steht für je ein Wert.
			    					   */
			    					  preparedStatement = conn.prepareStatement("update arriveintime.archiv set entladung_start=? where Lieferant=? and ankunft_min=? and ankunft_max=? and tag=?");
			    					  
			    					  /**
			    					   * In das erste Fragezeichen wird die Entladezeit zu Beginn der Entladung eingetragen.
			    					   * Die nächsten Fragen sind Where-Abfragen.
			    					   */
			    				      preparedStatement.setString(1, df1.format(dt1));
			    				      preparedStatement.setString(2, spediteur);
			    				      preparedStatement.setString(3, ank_min);
			    				      preparedStatement.setString(4, ank_max);
			    				      preparedStatement.setString(5, tag_zeit);
			    				      
			    				      /**
			    				       * Das Update-Statement führt den SQL-Befehl aus.
			    				       */
			    				      preparedStatement.executeUpdate();
			    				      
			    				      /**
			    				       * Nachdem die Übertragung abgeschlossen wurde, werden die Farben der Buttons verändert.
			    				       */
			    				      rwjFrame.btnNewButton.setBackground(Color.red);
			    				      rwjFrame.button.setBackground(Color.green);
			    				      
			    				      /**
			    				       * Als nächstes wird das JTable und der "Entladung begonnen"-Button gesperrt und der "Entladung beendet"-Button entsperrt.
			    				       */
			    				      rwjFrame.table.setEnabled(false);
			    				      rwjFrame.btnNewButton.setEnabled(false);
			    				      rwjFrame.button.setEnabled(true);
					    			  refresh.jobStarten();
					    			  return;
					    			  
					    		/**
					    		 * Wurde das Programm geschlossen, und der Spediteur, der bereits Entladen hat wird erneut aufgerufen, wird dem Benutzer eine Mitteilung ausgegeben
					    		 * und der Spediteur aus der Tabelle entfernt.
					    		 */
			    				  }else if(entl_beg.isEmpty() == false && entl_end.isEmpty() == false){			    					  
			    					  rwjFrame.removeRow(rwjFrame.table.getSelectedRow());
					    			  rwjFrame.setBounds(100, 100, 1001, 750);
					    			  rwjFrame.setBounds(100, 100, 1000, 750);
					    			  rwjFrame.table.getSelectionModel().clearSelection();
					    			  rwjFrame.textField.setText("Dieser Lieferant wurde bereits entladen.");
					    			  rwjFrame.textField.setForeground(Color.black);
					    			  return;
					    		/**
					    		 * Hat der Spediteur bereits mit der Entladung begonnen, und wurde das Programm geschlossen, so wird beim erneuten
					    		 * Betätigen des "Entladung begonnen"-Buttons der Button und die Tabelle gesperrt und der "Entladung beendet"-Button entsperrt.
					    		 */
			    				  }if(entl_beg.isEmpty() == false && entl_end.isEmpty() == true){
			    					  rwjFrame.btnNewButton.setBackground(Color.red);
			    				      rwjFrame.button.setBackground(Color.green);
			    				      rwjFrame.table.setEnabled(false);
			    				      rwjFrame.btnNewButton.setEnabled(false);
			    				      rwjFrame.button.setEnabled(true);
			    				      refresh.jobStarten();
			    				  /**
			    				   * Wurde jedoch ein Lieferant ausgewählt der noch entladen wird, wird der Benutzer Informiert.s
			    				   */
			    				  }else{			    				  
			    					  	rwjFrame.textField.setText("Dieser Lieferant wurde noch nicht entladen!!!");
			  							rwjFrame.textField.setForeground(Color.red);
			  							return;
			    				  }
			    			  }			    			  
			    		  }
			    	  }
		    	  /**
				   * Wurde jedoch ein Lieferant ausgewählt der noch nicht Rückgemeldet wurde, wird der Benutzer dementsprechend informiert.
				   */
			      }else if(durchlauf == 1){
			    	  	rwjFrame.textField.setText("Dieser Lieferant wurde noch nicht Rückgemeldet.");
						rwjFrame.textField.setForeground(Color.red);
			      }	      
			}			
			
		} catch (Exception e) {
			rwjFrame.textField.setText("Auswahl in einem kurzen Zeitfenster nicht erlaubt.");
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
