package Arrive_in_Time;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * Die Klasse EntladezeitAnzeige wird beim Betätigen des Buttons Entladezeit aufgerufen.
 * Dabei wird das JTable mit den Werten aus der Tabelle "entladung" befüllt.
 *
 */
public class EntladezeitAnzeige {
	
	/**
	 * Die für die Datenbank notwendige Bibliotheken.
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	Vector<Vector<String>> data;
	Vector<String> row;
	Vector<String> column;
	
	/**
	 * Da für die Anzeige auf der GUI des Hauptprogramms der heutige Tag 
	 * benötigt wird. Muss dieser vorerst ermittelt werden.
	 */
	Date dt2 = new Date();
	SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy" );
	
	String heutigeTag = df.format(dt2);
	String tag;
	
	AiTJFrame aitjFrame;
	
	/**
	 * Mit dem Konstruktor wird eine Verbindung zu dem AiTJFrame hergestellt.
	 * @param aitjframe
	 */
	public EntladezeitAnzeige(AiTJFrame aitjframe){
		this.aitjFrame = aitjframe;
	}
	
	/**
	 * In der Methode jobStarten() wird erst die Verbindung zur Datenbank hergestellt und anschließend
	 * die Tabelle JTable mit den Werten aus der Tabelle "entladung" befüllt.
	 */
	public void jobStarten(){

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
		          .executeQuery("select * from arriveintime.entladung");

		       ResultSetMetaData rsmt = resultSet.getMetaData();
		       
		       /**
		        * In den Folgenden Codezeilen werden die Spaltennamen in den
		        * Vector<String> mit dem Namen "column" aufgenommen.
		        * Hierbei werden jedoch nur die ersten drei Spalten genommen, denn nur diese
		        * werden für die Anzeige benötigt.
		        */
		       int c = rsmt.getColumnCount();		       
		       Vector<String> column = new Vector<String>(c);		       
		       for(int i = 1; i <= c-2; i++){
		           column.add(rsmt.getColumnName(i));
		       }
		       
		       /**
		        * Als nächstes werden die Zeilen aus der Datenbank ausgelesen und in den
		       	* Vektor "data" reingeschrieben.
		        */
		       data = new Vector<Vector<String>>();
		       row = new Vector<String>();
		       
		       /**
		        * Dabei werden alle Zeilen durchgelaufen, bis keine Ergebnisse/Zeilen mit Werten mehr
		        * vorhanden sind.
		        */
		       while(resultSet.next()){
		    	   
		    	   tag = resultSet.getString("Tag");
		    	   
		    	   if(tag.equals(heutigeTag)){
		    	   
		    		   row = new Vector<String>(c);
		    		   
		    		   for(int i = 1; i <= c; i++){
		    			   row.add(resultSet.getString(i));
		    		   }
		    		   /**
		    		    * Als letztes werden die Werte in den Vektor "data" eingetragen.
		    		    */
		    		   data.add(row);
		    	   }
		       }
		       
		       /**
		        * Nachdem in der Datenbank die Werte gesammelt und in die Vektoren gespeichert wurden,
		        * wird die Tabelle erstellt. 
		        */
		       aitjFrame.table = new JTable(data,column);
		       aitjFrame.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		       aitjFrame.contentPane.add(aitjFrame.table, BorderLayout.CENTER);
		       aitjFrame.table.setFont(new Font("Serif", Font.BOLD, 15));
		       aitjFrame.table.setDefaultEditor(Object.class, null);
		       aitjFrame.table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20));
		       aitjFrame.table.setEnabled(false);
		       aitjFrame.table.getTableHeader().setEnabled(false);
		       
		       /**
		        * Diese drei Zeilen erstellen ein Scrollbalken. Durch diese Funktion werden für 
		        * den Benutzer der Anwendung die Namen der Spalten sichtbar.
		        */
		       aitjFrame.scrollPane = new JScrollPane(aitjFrame.table);
		       aitjFrame.scrollPane.setFont(new Font("Serif", Font.BOLD, 25));
		       aitjFrame.contentPane.add( aitjFrame.scrollPane, BorderLayout.CENTER );
		       aitjFrame.scrollPane.setEnabled(false);


		   /**
		    * Sollte ein Fehler auftreten wird der catch-Block ausgelöst. 
		    */
		} catch(Exception e1){
			
			   JOptionPane.showMessageDialog(null, "ERROR");
		       
		/**
		 * Als letzte Funktion muss die Datenbankverbindung beendet werden.
		 */
		} finally{
			   
			try{
			statement.close();
			resultSet.close();
			conn.close();
			       
			}catch(Exception e1){
		    	   
				JOptionPane.showMessageDialog(null, "ERROR CLOSE");
			}
		
		
	}
	
		
	
	}

}
