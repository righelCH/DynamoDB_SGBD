package Laura_Fronted;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Righel_Backend.DynamoApp;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginTab extends JFrame {

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
	private JLabel limagen; JLabel lresul;
	DynamoApp dynamoApp;
	
	
	public JLabel getLresul() {
		return lresul;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginTab frame = new LoginTab();
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
	public LoginTab() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lresul.setText("Bienvenido a la aplicación DynamoDB, por favor ingrese sus credenciales");
			}
		});
		System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
		setTitle("DynamoDB");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginTab.class.getResource("/multimedia/Icono_dynamo.png")));
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

		limagen = new JLabel((String) null);

		Image dynamo = new ImageIcon(LoginTab.class.getResource("/multimedia/dynamo_sinbg.png")).getImage()
				.getScaledInstance(100, 80, Image.SCALE_SMOOTH);
		limagen.setIcon(new ImageIcon(dynamo));
		limagen.setBounds(688, 39, 112, 80);
		contentPane.add(limagen);

		tfusuario = new JTextField();
		tfusuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 tfusuario.selectAll();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				 // Copiar texto seleccionado al portapapeles
                String selectedText = tfusuario.getSelectedText();
                if (selectedText != null) {
                    copyToClipboard(selectedText);
                }
			}
		});
		tfusuario.setFont(new Font("Georgia", Font.PLAIN, 16));
		tfusuario.setBounds(655, 206, 335, 30);
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
		passwordField.setFont(new Font("Georgia", Font.PLAIN, 16));
		passwordField.setBounds(655, 292, 335, 30);
		contentPane.add(passwordField);

		lregion = new JLabel("Región ");
		lregion.setFont(new Font("Georgia", Font.PLAIN, 26));
		lregion.setBounds(252, 374, 323, 39);
		contentPane.add(lregion);

		comboRegion = new JComboBox<String>();
		comboRegion.setFont(new Font("Georgia", Font.PLAIN, 18));
		comboRegion.setModel(
				new DefaultComboBoxModel<String>(new String[] {"Seleccione", "eu-central-1", "eu-west-1", "eu-west-2", "eu-west-3", "eu-north-1", "us-east-1", "us-east-2", "us-west-1", "us-west-2", "ap-northeast-3", "ap-northeast-2", "ap-southeast-1", "ap-southeast-2", "ap-northeast-1", "ap-south-1", "eu-central-1"}));
		comboRegion.setBounds(655, 383, 219, 30);
		contentPane.add(comboRegion);

		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tfusuario.getText().isBlank() && passwordField.getPassword().length == 0
						&& comboRegion.getSelectedIndex() == 0) {
					lresul.setText("Por favor ingrese todos los campos");

					tfusuario.setBorder(new LineBorder(new Color(255, 0, 0)));
					passwordField.setBorder(new LineBorder(new Color(255, 0, 0)));
					comboRegion.setBorder(new LineBorder(new Color(255, 0, 0)));
				} else if (tfusuario.getText().isBlank()) {
					lresul.setText("Por favor ingrese el usuario");
					tfusuario.setBorder(new LineBorder(new Color(255, 0, 0)));
					passwordField.setBorder(null);
					comboRegion.setBorder(null);
				} else if (passwordField.getPassword().length == 0) {
					lresul.setText("Por favor ingrese la contraseña");
					passwordField.setBorder(new LineBorder(new Color(255, 0, 0)));
					tfusuario.setBorder(null);
					comboRegion.setBorder(null);
				} else if (comboRegion.getSelectedIndex() == 0) {
					lresul.setText("Por favor seleccione una región");
					comboRegion.setBorder(new LineBorder(new Color(255, 0, 0)));
					tfusuario.setBorder(null);
					passwordField.setBorder(null);
				} else {
					tfusuario.setBorder(null);
					passwordField.setBorder(null);
					comboRegion.setBorder(null);
				 dynamoApp=	 new DynamoApp(" AKIA3C6FL6TAJVER4NWP", "Ao0igG5BTy4JjYBk7uwtipaInf1KsPBdkGs/2uu5", comboRegion.getSelectedItem().toString(), LoginTab.this);
				 GUI gui = new GUI(dynamoApp);
					gui.setVisible(true);
				//	frame.dispose();	
				 //user.connectToDynamoDB(tfusuario.getText(), String.valueOf(passwordField.getPassword()), comboRegion.getSelectedItem().toString(), Principal.this);

	
				}
			}
		});
		btnEntrar.setBackground(new Color(204, 204, 255));
		btnEntrar.setFont(new Font("Georgia", Font.BOLD, 30));
		btnEntrar.setBounds(461, 484, 202, 64);
		contentPane.add(btnEntrar);

		lresul = new JLabel("");
		lresul.setHorizontalAlignment(SwingConstants.CENTER);
		lresul.setForeground(new Color(0, 0, 255));
		lresul.setFont(new Font("Georgia", Font.BOLD, 20));
		lresul.setBounds(185, 568, 825, 72);
		contentPane.add(lresul);

	}
	public DynamoApp getDynamoApp() {
		return dynamoApp;
	}
	// Método para copiar el texto al portapapeles  
    private static void copyToClipboard(String text) { 
        StringSelection selection = new StringSelection(text); 
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); 
        clipboard.setContents(selection, null); 
    } 
}
