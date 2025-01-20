package Laura_Fronted;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfusuario;
	private JPasswordField passwordField;
	private JLabel ltitulo;
	private JLabel lclave;
	private JLabel lcontrasena;
	private JLabel lregion;
	private JComboBox<String> comboRegion;
	private JButton btnEntrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setTitle("DynamoDB");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/multimedia/Icono_dynamo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(198, 226, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ltitulo = new JLabel("Acceso a DynamoDb");
		ltitulo.setHorizontalAlignment(SwingConstants.CENTER);
		ltitulo.setFont(new Font("Georgia", Font.BOLD, 38));
		ltitulo.setBounds(229, 26, 483, 105);
		contentPane.add(ltitulo);
		
		JLabel limagen = new JLabel((String) null);
	
		Image dynamo = new ImageIcon(Principal.class.getResource("/multimedia/dynamo_sinbg.png")).getImage().getScaledInstance(100,80,Image.SCALE_SMOOTH);
		limagen.setIcon(new ImageIcon(dynamo));
		limagen.setBounds(688, 39, 112, 80);
		contentPane.add(limagen);
		
		tfusuario = new JTextField();
		tfusuario.setFont(new Font("Georgia", Font.PLAIN, 18));
		tfusuario.setBounds(655, 206, 223, 30);
		contentPane.add(tfusuario);
		tfusuario.setColumns(10);
		
		lclave = new JLabel("Clave de acceso AWS ");
		lclave.setFont(new Font("Georgia", Font.PLAIN, 26));
		lclave.setBounds(252, 203, 282, 39);
		contentPane.add(lclave);
		
		lcontrasena = new JLabel("clave de acceso secreta");
		lcontrasena.setFont(new Font("Georgia", Font.PLAIN, 26));
		lcontrasena.setBounds(252, 287, 323, 39);
		contentPane.add(lcontrasena);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Georgia", Font.PLAIN, 18));
		passwordField.setBounds(655, 292, 219, 30);
		contentPane.add(passwordField);
		
		lregion = new JLabel("Región ");
		lregion.setFont(new Font("Georgia", Font.PLAIN, 26));
		lregion.setBounds(252, 374, 323, 39);
		contentPane.add(lregion);
		
		comboRegion = new JComboBox<String>();
		comboRegion.setFont(new Font("Georgia", Font.PLAIN, 18));
		comboRegion.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione", "us-east-1", "eu-central-1", "eu-west-3"}));
		comboRegion.setBounds(655, 383, 219, 30);
		contentPane.add(comboRegion);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEntrar.setBackground(new Color(204, 204, 255));
		btnEntrar.setFont(new Font("Georgia", Font.BOLD, 30));
		btnEntrar.setBounds(461, 484, 202, 64);
		contentPane.add(btnEntrar);
		
	}
}
