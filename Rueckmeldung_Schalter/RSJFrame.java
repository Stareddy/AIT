package Rueckmeldung_Schalter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class RSJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Wichtige Parameter für die Erstellung des JFrames.
	 */
	public JPanel contentPane;
	public JTextField txtRckmeldungSchalter;
	public JTable table;
	public JTextField txtLetzteAktualisierung;
	public JButton btnRckmelden;
	
	/**
	 * Während im String "markierung" den in der DB gesetzten Fleck aufnimmt,
	 * wird in dem String "Tag" der momentane Tag aufgenommen (Montag - Sonntag).
	 */
	private String markierung;
	private String Tag;
	
	/**
	 * Wichtige Daten für die Datenbankverbindung.
	 * In dem Fall der Username und das Passwort.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
	
	/**
	 * Für die Datenbankverbindung notwendige Parameter. 
	 * 
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	
	Vector<Vector<String>> data;
	Vector<String> row;
	/**
	 * Die Verlinkung auf die Klassen, damit die Kommunikation zwischen diesen 
	 * stattfinden kann. 
	 */
	public RSFrameHandler rsframeHandler;
	public RefreshKlasse refresh;
	
	
	/**
	 * Die Erzeugung des JFrames.
	 */
	public RSJFrame() {
		
		setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Die Größe des zu erstellenden Fensters.
		 */
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		/**
		 * Die Verweisung auf die Klasse mit denen kommuniziert wird.
		 */
		rsframeHandler = new RSFrameHandler(this);
		refresh = new RefreshKlasse(this);
		
		/**
		 * Da für das Eintragen von Lieferanten der heutige Tag benötigt wird,
		 * muss die Methode Tag() aus der Klasse RefreshKlasse aufgerufen werden.
		 * Hierbei wird der heutige Tag ausgewählt und in die globale Variable
		 * "Tag" reingeschrieben.
		 */
		refresh.Tag();
		
		/**
		 * Hier findet die Verbindung mit der Datenbank statt.
		 */
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
		          .executeQuery("select * from arriveintime.entladezeitfenster order by ankunft_min");

		       ResultSetMetaData rsmt = resultSet.getMetaData();
		       
		       /**
		        * In den Folgenden Codezeilen werden die Spaltennamen in den
		        * Vector<String> mit dem Namen "column" aufgenommen.
		        * Hierbei werden jedoch nur die ersten drei Spalten genommen, denn nur diese
		        * werden für die Anzeige benötigt.
		        */
		       int c = rsmt.getColumnCount();		       
		       Vector<String> column = new Vector<String>(c);		       
		       for(int i = 1; i <= 3; i++){
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
		    	   
		    	   /**
		    	    * Pro Zeile wird jeweils der heutige Tag gesucht.
		    	    */
		    	   markierung = resultSet.getString(getTag());
		    	   
		    	   /**
		    	    * Dabei wird immer überprüft, ob bei der Spalte mit dem heutigen Tag
		    	    * die Bemerkung "ja" gesetzt ist.
		    	    * Ist dies der Fall, werden die Werte in den Vektor "row" gespeichert.
		    	    */
		    	   if(markierung.equals("ja")){
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
		       table = new JTable(data,column);
		       table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		       contentPane.add(table, BorderLayout.WEST);
		       table.setFont(new Font("Serif", Font.BOLD, 15));
		       table.setDefaultEditor(Object.class, null);
		       
		       /**
		        * Diese drei Zeilen erstellen ein Scrollbalken. Durch diese Funktion werden für 
		        * den Benutzer der Anwendung die Namen der Spalten sichtbar.
		        */
		       JScrollPane scrollPane = new JScrollPane( table );
		       scrollPane.setFont(new Font("Serif", Font.BOLD, 30));
		       contentPane.add(scrollPane, BorderLayout.CENTER );


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
		
		/**
		 * In diesem Bereich wird das Testfeld erstellt, der den Namen der Anwendung in sich trägt.
		 */
		txtRckmeldungSchalter = new JTextField();
		txtRckmeldungSchalter.setEditable(false);
		txtRckmeldungSchalter.setHorizontalAlignment(SwingConstants.CENTER);
		txtRckmeldungSchalter.setText("R\u00FCckmeldung Schalter");
		contentPane.add(txtRckmeldungSchalter, BorderLayout.NORTH);
		txtRckmeldungSchalter.setColumns(10);
		txtRckmeldungSchalter.setFont(new Font("Serif", Font.BOLD, 40));
		
		/**
		 * In diesem Abschnitt wird der Button für das Rückmelden der Lieferanten generiert.
		 */
		btnRckmelden = new JButton("Rückmelden");
		contentPane.add(btnRckmelden, BorderLayout.EAST);
		btnRckmelden.setFont(new Font("Serif", Font.BOLD, 20));
		btnRckmelden.addActionListener(rsframeHandler);
		/**
		 * Mit dieser Methode wird dem Button die Funktion gegeben, bei dem Betätigen
		 * ein Event auszulösen, der in der Klasse RSFrameHandler abgefangen und bearbeitet wird.
		 */
		btnRckmelden.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				}
			});
				
		/**
		 * Als letztes wird das Textfeld für die letzte Aktualisierung erstellt.
		 * Nach dem Start der Anwendung und bei jedem Rückmelden der Lieferanten wird
		 * das Feld aktualisiert.
		 */
		txtLetzteAktualisierung = new JTextField();
		txtLetzteAktualisierung.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtLetzteAktualisierung.setEditable(false);
		txtLetzteAktualisierung.setHorizontalAlignment(SwingConstants.CENTER);
		txtLetzteAktualisierung.setText("");		
		txtLetzteAktualisierung.setColumns(67);
		contentPane.add(txtLetzteAktualisierung, BorderLayout.SOUTH);
		
		
				
	}
	
	/**
	 * Nachdem in der Klasse Refresh der heutige Tag ermittelt wurde, wird
	 * dieser mit dem Aufruf der Methode setTag in das JFrame geladen.
	 * @param tag wird in der Klasse Refresh aktualisiert.
	 */
	public void setTag(String tag){
		this.Tag = tag;
	}
	
	/**
	 * Mit der Methode getTag() wird der Tag, nachdem dieser in der oberen Methode
	 * erstellt wurde, ausgelesen und für die Erstellung der Tabelle verwendet.
	 * @return Tag
	 */
	public String getTag(){
		return Tag;
	}
	
	/**
	 * Mit Hilfe von der Methode removeRow wird nachdem der ausgewählte Spediteur
	 * in die DB aufgenommen wurde bzw. falls er bereits in der Archiv Tabelle aufgenommen wurde,
	 * wird dieser aus der JTable Tabelle entfernt.
	 * @param row
	 */
	public void removeRow(int row) {
        data.removeElementAt(row);
    }

}
