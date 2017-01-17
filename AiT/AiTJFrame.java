package Arrive_in_Time;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Das Frame von der GUI. 
 *
 */
public class AiTJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public JPanel contentPane;
	
	/**
	 * Das JTextField "txtArriveInTime" wird für den Namen der Anwendung verwendet. 
	 */
	private JTextField txtArriveInTime;
	
	/**
	 * Das JTable zeigt die Tabelle, wenn einer der Auswahl-Buttons ausgewählt wurde.
	 * Das JTable und das scrollPane wurden public gesetzt, damit von anderen Klassen aus, die benötigten Tabellen erstellt werden können.
	 */
	public JTable table;
	public JScrollPane scrollPane;
	
	/**
	 * Der JButton online_offline wird dazu verwendet, um anzuzeigen, ob die Anwendung aktiv ist.
	 * Hierfür wird der Button in der Klasse AiTHauptklasse aufgerufen, was jede 1,5 sec ein Lebenszeichen (durch ein Blicken) von sich gibt.
	 */
	public JButton online_offline;
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	/**
	 * Die Verbindung des AiTJFrame mit den Klassen, in denen eine Interaktion mit dem JFrame stattfinden soll.
	 */
	AiTFrameHandler aitFrameHandler;	
	PünktlichkeitAnzeige pünktlich;	
	EntladezeitAnzeige entlade;	
	AuswertungenAnzeige auswertung;	
	BewertungenAnzeige bewertung;

	/**
	 * Die Erstellung des JFrame.
	 */
	public AiTJFrame(String title) {
		super(title);
		setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Das setBounds() definiert die Größe des dargestellten GUI's. 
		 */
		setBounds(100, 100, 1500, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/**
		 * Die Überschrift der Anwendung. 
		 */
		txtArriveInTime = new JTextField();
		txtArriveInTime.setEditable(false);
		txtArriveInTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtArriveInTime.setText("Arrive in Time");
		contentPane.add(txtArriveInTime, BorderLayout.NORTH);
		txtArriveInTime.setColumns(10);
		txtArriveInTime.setFont(new Font("Serif", Font.BOLD, 25));
		
		/**
		 * Verlinkung der Klassen, mit denen das AiTJFrame kommunizieren soll.
		 */
		aitFrameHandler = new AiTFrameHandler(this);
		pünktlich = new PünktlichkeitAnzeige(this);
		entlade = new EntladezeitAnzeige(this);
		auswertung =  new AuswertungenAnzeige(this);
		bewertung = new BewertungenAnzeige(this);
		
		/**
		 * Das JPanel wird dazu verwendet, um Buttons für die Auswahl der jeweiligen Darstellung der Tabellen aufzunehmen.
		 */
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		/**
		 * Der Pünktlichkeit-Button verwendet die Klasse PünktlichkeitsAnzeige, um die Methode jobStarten() zu starten.
		 * Dadurch wird ein JTable generiert, das die Pünktlichkeit der Lieferanten darstellt.
		 */
		JButton pünktlichkeitBtn = new JButton("Pünktlichkeit");
		pünktlichkeitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pünktlich.jobStarten();
			}});
		pünktlichkeitBtn.setFont(new Font("Serif", Font.BOLD, 25));
		pünktlichkeitBtn.getPreferredSize();
		
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		panel.add(textField_4);
		textField_4.setColumns(1);
		panel.add(pünktlichkeitBtn);
		
		/**
		 * Der Entladezeit-Button verwendet die Klasse EntladezeitAnzeige, um die Methode jobStarten() zu starten.
		 * Dadurch wird ein JTable generiert, das die Entladezeit der Lieferanten darstellt.
		 */
		JButton entladezeitBtn = new JButton("Entladezeit");
		entladezeitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entlade.jobStarten();
			}
		});
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		panel.add(textField);
		textField.setColumns(1);
		entladezeitBtn.setFont(new Font("Serif", Font.BOLD, 25));
		panel.add(entladezeitBtn);
		
		/**
		 * Der Auswertungen-Button verwendet die Klasse AuswertungsAnzeige, um die Methode jobStarten() zu starten.
		 * Dadurch wird ein JTable generiert, um Auswertungen darzustellen.
		 */
		JButton auswertungBtn = new JButton("Auswertungen");
		auswertungBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				auswertung.jobStarten();
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setColumns(1);
		auswertungBtn.setFont(new Font("Serif", Font.BOLD, 25));
		panel.add(auswertungBtn);
		
		/**
		 * Der Bewertunngen-Button verwendet die Klasse BewertungenAnzeige, um die Methode jobStarten() zu starten.
		 * Dadurch wird ein JTable generiert, um Bewertungen darzustellen.
		 */
		JButton bewertungBtn = new JButton("Bewertung");
		bewertungBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bewertung.jobStarten();
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		panel.add(textField_2);
		textField_2.setColumns(1);
		bewertungBtn.setFont(new Font("Serif", Font.BOLD, 25));
		panel.add(bewertungBtn);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setEditable(false);
		panel.add(textField_3);
		textField_3.setColumns(1);
		
		/**
		 * Die untere Schaltfläche wird dazu verwendet, um drei Buttons aufzunehmen.
		 */
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		contentPane.add(splitPane, BorderLayout.SOUTH);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);
		
		/**
		 * Der erste Button Online/Offline wird für die Anzeige verwendet, ob die Anwendung aktiv ist.
		 * Dieser Button ist beim Start der Anwendung auf rot gesetzt.
		 */
		online_offline = new JButton("");
		online_offline.setEnabled(false);
		online_offline.setBackground(Color.red);
		splitPane_1.setLeftComponent(online_offline);
		
		/**
		 * Der zweite Button "Anwendung anhalten"-Button wird für das Anhalten der Anwendung verwendet.
		 * Wird die Anwendung angehalten, wird der Button "Anwendung starten" auf grün gesetzt und der 
		 * Button "Anwendung anhalten" und der Button "online_offline" auf rot gesetzt.
		 */
		JButton anhalteButton = new JButton("Anwendung anhalten");
		anhalteButton.setBackground(Color.red);
		anhalteButton.setFont(new Font("Serif", Font.BOLD, 25));
		splitPane_1.setRightComponent(anhalteButton);
		anhalteButton.addActionListener(aitFrameHandler);
		
		/**
		 * Der dritte Button "Anwendung starten"-Button wird für das Starten der Anwendung verwendet.
		 * Nach dem Start der Anwendung wird der Button von grün auf rot geschaltet. 
		 * Der Button "Anwendung anhalten" wird auf grün gesetzt und der Button "online_offline" fängt an die Farbe zu wechseln.
		 */
		JButton startButton = new JButton("Anwendung starten");
		startButton.setFont(new Font("Serif", Font.BOLD, 25));
		startButton.setBackground(Color.green);	
		splitPane.setLeftComponent(startButton);
		startButton.addActionListener(aitFrameHandler);
		
		/**
		 * Damit der AiTFrameHandler die Befehle der Buttons ausführen kann, muss die Methode actionPerformed() für die Buttons "Anwendung anhalten"
		 * und "Anwendung starten" implementiert werden.
		 */
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setBackground(Color.red);
				anhalteButton.setBackground(Color.green);
				startButton.setEnabled(false);
				startButton.setFocusable(false);
				anhalteButton.setFocusable(false);
			}
		});

		anhalteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anhalteButton.setBackground(Color.red);
				startButton.setBackground(Color.green);
				online_offline.setBackground(Color.red);
				startButton.setEnabled(true);
				startButton.setFocusable(false);
				anhalteButton.setFocusable(false);
			}
		});
	}
}
