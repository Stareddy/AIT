package Rueckmeldung_Schalter;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatenpflegeKlasse {
	
	RSJFrame rsjFrame;
	
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
	private String tag;
	
	/**
	 * Sollten keine passenden Daten in der Tabelle vorhanden sein, wird die Anzahl von "durchlauf"
	 * nicht erhöht.
	 */
	private int durchlauf = 1;
	
	/**
	 * Konstruktor für die Kommunikation zwischen der Klasse DatenpflegeKlasse und RSJFrame.
	 * @param rsjframe 
	 */
	public DatenpflegeKlasse(RSJFrame rsjframe){
		this.rsjFrame = rsjframe;
	}
	
	RefreshKlasse refresh;
	
	Date dt2 = new Date();
	SimpleDateFormat df1 = new SimpleDateFormat( "dd.MM.yyyy HH:mm" );
	SimpleDateFormat df2 = new SimpleDateFormat( "HH:mm" );
	SimpleDateFormat df3 = new SimpleDateFormat( "dd.MM.yyyy" );
	
	/**
	 * In der Methode jobStarten() werden die einzelnen Methoden nacheinander aufgerufen.
	 * Durch die Trennung der Programmlogik wird bessere Wartbarkeit und Lesbarkeit erreicht.
	 */
	public void jobStarten(){
		
		datenausJTable();
		if(o1 == null){
			
		}
		else{
			dbZugriff();
		}
		
	}
	
	/**
	 * Die Methode datenausJTable() lädt die Daten aus dem JTable.
	 */
	private void datenausJTable(){
		try{
			o1 = rsjFrame.table.getValueAt(rsjFrame.table.getSelectedRow(), 0);
			o2 = rsjFrame.table.getValueAt(rsjFrame.table.getSelectedRow(), 1);
			o3 = rsjFrame.table.getValueAt(rsjFrame.table.getSelectedRow(), 2);
		}catch(Exception e){
			rsjFrame.txtLetzteAktualisierung.setText("Es wurde kein Lieferant ausgewählt oder es sind bereits alle Lieferanten rückgemeldet worden.");
			rsjFrame.txtLetzteAktualisierung.setForeground(Color.red);
			return;
		}
	}
	
	/**
	 * Die Methode dbZugriff() ist für den DB Zugriff zuständig.
	 * Hierbei wird unter anderem zuerst überprüft, ob in der Tabelle Archiv die einzutragenden Daten vorhanden sind.
	 * Falls dies der Fall ist wird die Zeile aus dem JTable gelöscht, ist dies nicht der Fall werden die Werte in die Tabelle
	 * Archiv eingetragen und anschließend aus dem JTable gelöscht.
	 */
	public void dbZugriff(){
		refresh = new RefreshKlasse(rsjFrame);
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
			      ank_min = resultSet.getString("ankunft_min");
			      ank_max = resultSet.getString("ankunft_max");
			      tag = resultSet.getString("tag");
			      
			      
			      
			      if(o1.equals(spediteur)){
			    	  if(o2.equals(ank_min)){
			    		  if(o3.equals(ank_max)){
			    			  if(df3.format(dt2).equals(tag)){
			    				  rsjFrame.removeRow(rsjFrame.table.getSelectedRow());
				    			  rsjFrame.setBounds(100, 100, 1001, 750);
				    			  rsjFrame.setBounds(100, 100, 1000, 750);
				    			  rsjFrame.table.getSelectionModel().clearSelection();
				    			  refresh.jobStarten();
				    			  durchlauf = durchlauf + 1;
				    			  
			    			  }
			    			  
			    		  }
			    	  }
			      }else{
			    	  
			      }
			    				      
			}
			
			/**
			 * Wenn keine Werte vorhanden sind, werden diese in die Tabelle eingetragen.
			 */
			 if(durchlauf == 1){
		    	  preparedStatement = conn.prepareStatement("insert into  arriveintime.archiv values (?, ?, ?, ?, ?, ?, ?, ?)");
			      preparedStatement.setString(1, (String) o1);
			      preparedStatement.setString(2, (String) o2);
			      preparedStatement.setString(3, (String) o3);
			      preparedStatement.setString(4, df2.format(dt2));
			      preparedStatement.setString(5, "");
			      preparedStatement.setString(6, "");
			      preparedStatement.setString(7, df3.format(dt2));
			      preparedStatement.setString(8, df1.format(dt2));
			      preparedStatement.executeUpdate();
			      rsjFrame.removeRow(rsjFrame.table.getSelectedRow());
    			  rsjFrame.setBounds(100, 100, 1001, 750);
    			  rsjFrame.setBounds(100, 100, 1000, 750);
    			  rsjFrame.table.getSelectionModel().clearSelection();
    			  refresh.jobStarten();
		      }
			
		} catch (Exception e) {
			rsjFrame.txtLetzteAktualisierung.setText("Auswahl in einem kurzen Zeitfenster nicht erlaubt.");
			rsjFrame.txtLetzteAktualisierung.setForeground(Color.red);
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
