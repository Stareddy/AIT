package Arrive_in_Time;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * Die Klasse BewertungenAnzeige() 
 *
 */
public class BewertungenAnzeige {
	
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
	String auswertung;
	
	AiTJFrame aitjFrame;
	
	/**
	 * Mit dem Konstruktor wird eine Verbindung zu dem AiTJFrame hergestellt.
	 * @param aitjframe
	 */
	public BewertungenAnzeige(AiTJFrame aitjframe){
		this.aitjFrame = aitjframe;
	}
	
	/**
	 * Die Methode jobStarten() erstellt das JTable und bewertet die Lieferanten 
	 * nach dem Ampelsystem.
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
		          .executeQuery("select * from arriveintime.bewertung");

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
		    	   auswertung = resultSet.getString("Bewertung");
		    	   
		    	   
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
		       aitjFrame.table = new JTable(data,column){
				private static final long serialVersionUID = 1L;

				/**
				 * In diesem Bereich wird die Auswertung mittels Ampelsystem erstellt. Dabei wird in 
				 * jeder erstellten Zeile, in der zweiten Spalte(binär=erste Spalte) nach dem Wert gesucht
				 * und bei jeweiligen Treffer die Farbe dementsprechend angepasst.
				 */
		    	    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		    	        Component comp = super.prepareRenderer(renderer, row, col);
		    	        Object value = getModel().getValueAt(row, col);{
		    	            if (value.equals("100 %")) {
		    	                comp.setBackground(Color.green);
		    	                comp.setForeground(Color.green);
		    	            } else if (value.equals("90 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("80 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("70 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("60 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("50 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("40 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("30 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("20 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("10 %")) {
		    	                comp.setBackground(Color.yellow);
		    	                comp.setForeground(Color.yellow);
		    	            }else if (value.equals("0 %")) {
		    	                comp.setBackground(Color.red);
		    	                comp.setForeground(Color.red);
		    	            }else{
		    	                comp.setBackground(Color.white);
		    	                comp.setForeground(Color.black);
		    	            }
		    	        }
		    	        return comp;
		    	    }
		    	};
		       aitjFrame.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		       aitjFrame.contentPane.add(aitjFrame.table, BorderLayout.CENTER);
		       aitjFrame.table.setFont(new Font("Serif", Font.BOLD, 20));
		       aitjFrame.table.setRowHeight(30);
		       aitjFrame.table.setDefaultEditor(Object.class, null);
		       aitjFrame.table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20));
		       aitjFrame.table.setEnabled(false);
		       aitjFrame.table.getTableHeader().setEnabled(false);
		       
		       /**
		        * Diese drei Zeilen erstellen ein Scrollbalken. Durch diese Funktion werden für 
		        * den Benutzer der Anwendung die Namen der Spalten sichtbar.
		        */
		       aitjFrame.scrollPane = new JScrollPane(aitjFrame.table);
		       aitjFrame.contentPane.add( aitjFrame.scrollPane, BorderLayout.CENTER );
		       aitjFrame.scrollPane.setEnabled(false);
		       aitjFrame.scrollPane.setBorder(new EmptyBorder(0, 0, 0, 60));
		       
		       
		   /**
		    * Sollte ein Fehler auftreten wird der catch-Block ausgelöst. 
		    */
		} catch(Exception e){
			
			   JOptionPane.showMessageDialog(null, "ERROR");
		       
		/**
		 * Als letzte Funktion muss die Datenbankverbindung beendet werden.
		 */
		} finally{
			   
			try{
			statement.close();
			resultSet.close();
			conn.close();
			       
			}catch(Exception e){
		    	   
				JOptionPane.showMessageDialog(null, "ERROR CLOSE");
			}
		}
	}
}