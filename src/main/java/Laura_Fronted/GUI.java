package Laura_Fronted;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConsultas;
	private JPanel panelGestionTabla;
	private JPanel panelCRUD;
	private JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
	
		setTitle("DynamoDB");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/multimedia/Icono_dynamo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 886, 563);
		contentPane.add(tabbedPane);
		
	
		panelGestionTabla = new JPanel();
		panelGestionTabla.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("Gestión Tabla", null, panelGestionTabla, null);
		
		panelCRUD = new JPanel();
		panelCRUD.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("CRUD", null, panelCRUD, null);
		
		panelConsultas = new JPanel();
		panelConsultas.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("Consultas", null, panelConsultas, null);
	}
}
