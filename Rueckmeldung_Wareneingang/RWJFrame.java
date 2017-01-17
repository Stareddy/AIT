package Rueckmeldung_Wareneingang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

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
import javax.swing.JSplitPane;

/**
 * In der Klasse RWJFrame wird die GUI für den Wareneingang erzeug.
 *
 */
public class RWJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Die Verlinkung der benötigten Klassen findet unterhalb statt.
	 * Sie ist notwendig, damit Änderungen auf dem Frame auch von anderen Klassen vorgenommen werden kann.
	 */
	RefreshKlasse refresh;
	RWFrameHandler rwframeHandler;
	EntladungsKlasse1 entladung1;
	EntladungsKlasse2 entladung2;

	/**
	 * Damit die Datenbankverbindung ermöglicht werden kann, werden Benutzername und das Passwort benötigt.
	 */
	private static String user = "AiT";
	private static String password = "Arrive in Time";
		
	/**
	 * Für Datenbankverbindung notwendigen Klassen.
	 */
	private static Connection conn = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	/**
	 * Für die Erzeugung notwendigen Klassen.
	 */
	public JTable table;
	public JSplitPane splitPane;
	public JButton btnNewButton;
	public JButton button;
	public JSplitPane splitPane_1;
	public JSplitPane splitPane_2;
	public JTextField textField;
	public JPanel panel;	
	public JButton btnRefresh;
	public String ankunftstag;
	public JPanel contentPane;
	public JTextField txtRckmeldungSchalter;
	
	Vector<Vector<String>> data;
	Vector<String> row;
	
	private String Tag;
	
	/**
	 * Hier findet die Erzeugung des Frames statt.
	 */
	public RWJFrame(String title) {
		
		super(title);
		setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		rwframeHandler = new RWFrameHandler(this);
		refresh = new RefreshKlasse(this);
		entladung1 = new EntladungsKlasse1(this);
		entladung2 = new EntladungsKlasse2(this);
		refresh.Tag();
		
		/**
		 * Erstellung des JTable.
		 * Hierbei wird zuerst die Datenbankverbindung erstellt und danach die Daten in die Tabelle geladen.
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
			 * Auslesen der Spaltenbezeichnungen und eintragen in den Vektor "column".
			 */
			int c = rsmt.getColumnCount();
			Vector<String> column = new Vector<String>(c);
			for(int i = 1; i <= 3; i++)
			{
				column.add(rsmt.getColumnName(i));
			}
			
			/**
			 * Auslesen der Zeilendaten und eintragen in den Vektor "data".
			 */
			data = new Vector<Vector<String>>();
			row = new Vector<String>();
			
			/**
			 * Der Schleifendurchgang ermöglicht das Finden der Spediteure, die an dem heutigen Tag kommen.
			 * Dazu wird nach der Spaltenbezeichnung mit dem heutigen Tag gesucht. Ist die Spalte gefunden,
			 * wird kontrolliert ob an dem heutigen Tag der Lieferant auch kommt.
			 */
			while(resultSet.next()){
				ankunftstag = resultSet.getString(getTag());
				if(ankunftstag.equals("ja")){
					row = new Vector<String>(c);
					for(int i = 1; i <= c; i++){
						row.add(resultSet.getString(i));
					}
					data.add(row);
				}	           
			}
			
			/**
			 * Im letzten Schritt wird dann noch die JTable erstellt und mit Daten gefüllt.
			 */
			table = new JTable(data,column);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contentPane.add(table, BorderLayout.WEST);
			table.setFont(new Font("Serif", Font.BOLD, 15));
			table.setDefaultEditor(Object.class, null);
			JScrollPane scrollPane = new JScrollPane( table );
			scrollPane.setFont(new Font("Serif", Font.BOLD, 25));
			contentPane.add(scrollPane, BorderLayout.WEST );
			
		   	}catch(Exception e){
		   		JOptionPane.showMessageDialog(null, "ERROR");
		   	
		   	/**
		   	 * Im letzten Schritt wird die Datenbankverbindung geschlossen.
		   	 */
		   	}finally{
		   		try{
		   			statement.close();
		   			resultSet.close();
		   			conn.close();
		   		}catch(Exception e){
		   			JOptionPane.showMessageDialog(null, "ERROR CLOSE");
		   		}
		   	}
		
		/**
		 * Die unteren Zeilen erstellen ein Textfeld der für die Übershrift der Anwendung darstellt.
		 */
		txtRckmeldungSchalter = new JTextField();
		txtRckmeldungSchalter.setEditable(false);
		txtRckmeldungSchalter.setHorizontalAlignment(SwingConstants.CENTER);
		txtRckmeldungSchalter.setText("R\u00FCckmeldung Wareneingang");
		contentPane.add(txtRckmeldungSchalter, BorderLayout.NORTH);
		txtRckmeldungSchalter.setColumns(10);
		txtRckmeldungSchalter.setFont(new Font("Serif", Font.BOLD, 40));
		
		/**
		 * Als nächstes werden die Buttons für den Wareneingang erstellt.
		 * Dabei wird zuerst ein JSplitPane eingebunden, woduch zwei Buttons nebeneinander platziert werden können.
		 */
		splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.EAST);
		
		/**
		 * Der erste Button "Entladung begonnen" wird aktiviert, wenn die Entladung beginnt.
		 * WICHTIG: Der Button muss Global gesetzt sein, da sonst kein Zugriff von außerhalb der Klasse möglich ist.
		 * 
		 * FEHLER WAR: Den Button Lokal zu definieren --> final JButton ....
		 */
		btnNewButton = new JButton("Entladung begonnen");
		btnNewButton.addActionListener(rwframeHandler);
		btnNewButton.setBackground(Color.green);		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});		
		splitPane.setLeftComponent(btnNewButton);
		
		/**
		 * Der zweite Button "Entladung beendet" wird aktiviert, wenn die Entladung beendet worden ist.
		 */
		button = new JButton("Entladung beendet");
		button.addActionListener(rwframeHandler);
		button.setBackground(Color.red);
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		splitPane.setRightComponent(button);
		
		/**
		 * Für Aktualisierungen oder Fehlermeldungen wird unten am Fenster ein Textfeld eingesetzt,
		 * der den Benutzer bei Änderungen/Fehlern informiert.
		 */
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("");
		textField.setColumns(67);
		contentPane.add(textField, BorderLayout.SOUTH);
		
		
	}
	
	/**
	 * Damit das JTable mit dem richtigen Spediteuren befüllt wird, muss in der While-Schleife 
	 * der heutige Tag bekannt sein. In der Klasse RefreshKlasse wird der heutige Tag ermittelt und mit der
	 * Methode setTag() an das Frame geliefert.
	 * @param tag
	 */
	public void setTag(String tag){
		this.Tag = tag;
	}
	
	/**
	 * Mit der Methode getTag() wird der heutige Tag entnommen und in der While-Schleife verwendet.
	 * @return
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
