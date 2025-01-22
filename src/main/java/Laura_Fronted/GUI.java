package Laura_Fronted;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import javax.swing.JTabbedPane;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import Righel_Backend.DynamoApp;
import Righel_Backend.Gestion_de_Tablas;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame {
	private static DynamoApp dynamoApp;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConsultas;
	private JPanel panelGestionTabla;
	private JPanel panelCRUD;
	private JTabbedPane tabbedPane;
	private JButton btnMostrarTablas;
	private JList<String> listaTablas;
	private JScrollPane scrollPane;
	private JLabel lnombre;
	private JLabel lclaveparticion;
	private JLabel ltipoparticion;
	private JTextField tfnombretabla;
	private JTextField tfclaveparticion;
	private JTextField tfnombreordenacion;
	private JComboBox<String> combotipoparticion;
	private JButton btnCrearTabla;
	private JLabel lblNewLabel;
	private JLabel lresulgestion;
	private JLabel lblClaveDeOrdenacion;
	private JLabel lblProyecion;
	private JLabel ltitulo;
	private JLabel lnombreatributo;
	private JTextField tfnombreatributo;
	private JTextField tfvalor;
	private JComboBox<String> combotipo;
	private JButton btnModificarItem;
	private JTable tabla;
	private JButton btnBorrarItem;
	private JButton btnCrearItem;
	private JScrollPane scrollPane_1;
	private JLabel lresulcrud;
	private JComboBox<String> combotipoclaveordenacion;
	private JCheckBox cbprovision;
	private JSpinner spinnerlectura;
	private JSpinner spinnerescritura;
	private JLabel lblCapacidadDeLectura;
	private JLabel lblCapacidadDeEscritura;
	private static DefaultListModel<String> dlm;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// -
					// UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");

					// ----
					// UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					// --- UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
					GUI frame = new GUI(dynamoApp);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param dynamoApp
	 * @param dynamoApp
	 */
	public GUI(DynamoApp dynamoApp) {
		GUI.dynamoApp = dynamoApp; // Reutilizar la conexión existente

		setTitle("DynamoDB");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/multimedia/Icono_dynamo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(102, 255, 140));
		tabbedPane.setBounds(0, 0, 1190, 663);
		contentPane.add(tabbedPane);

		panelGestionTabla = new JPanel();
		panelGestionTabla.setBackground(new Color(215, 235, 255));
		tabbedPane.addTab("Gestión Tabla", null, panelGestionTabla, null);
		panelGestionTabla.setLayout(null);

		btnMostrarTablas = new JButton("Abrir Tabla");
		btnMostrarTablas.setBorder(new LineBorder(new Color(242, 165, 70)));
		btnMostrarTablas.setFont(new Font("Georgia", Font.BOLD, 14));
		btnMostrarTablas.setBackground(new Color(215, 215, 255));
		btnMostrarTablas.setBounds(858, 361, 230, 43);
		panelGestionTabla.add(btnMostrarTablas);

		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setBounds(871, 89, 217, 190);
		panelGestionTabla.add(scrollPane);

		listaTablas = new JList<String>();
		scrollPane.setViewportView(listaTablas);
		listaTablas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 160)), "<html><b>Tablas</html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		btnCrearTabla = new JButton(" Crear Tabla");
		btnCrearTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
				    AmazonDynamoDB dynamoDB = dynamoApp.getDynamoDB();
				    if (dynamoDB == null) {
				        throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
				    }

				    Gestion_de_Tablas tabla = new Gestion_de_Tablas(dynamoDB);
				    ListTablesResult result = dynamoDB.listTables();
				    System.out.println("Realizada la conexión, tablas existentes: "+result);
				    // Determinar el tipo de partición
				    String tipoparticion = "";
				    switch (combotipoparticion.getSelectedIndex()) {
				        case 0:
				            throw new IllegalArgumentException("Seleccione tipo de partición");
				        case 1:
				            tipoparticion = "S";
				            break;
				        case 2:
				            tipoparticion = "N";
				            break;
				        case 3:
				            tipoparticion = "B";
				            break;
				        default:
				            throw new IllegalArgumentException("Tipo de partición no válido");
				    }

				    // Determinar el tipo de ordenación
				    String tipoordenacion = "";
				    switch (combotipoclaveordenacion.getSelectedIndex()) {
				        case 0:
				            tipoordenacion = ""; // Sin ordenación
				            break;
				        case 1:
				            tipoordenacion = "S";
				            break;
				        case 2:
				            tipoordenacion = "N";
				            break;
				        case 3:
				            tipoordenacion = "B";
				            break;
				        default:
				            throw new IllegalArgumentException("Tipo de ordenación no válido");
				    }

				    // Validación de campos obligatorios
				    if (tfnombretabla.getText().isEmpty()) {
				        throw new IllegalArgumentException("El nombre de la tabla es obligatorio");
				    }

				    if (tfclaveparticion.getText().isEmpty()) {
				        throw new IllegalArgumentException("El nombre de la clave de partición es obligatorio");
				    }

				    if (tipoparticion.isEmpty()) {
				        throw new IllegalArgumentException("El tipo de partición es obligatorio");
				    }

				    // Variables para la capacidad y modo de facturación
				    Long caplectura = null;
				    Long cap1escritura = null;
				    boolean provision = cbprovision.isSelected();

				    // Validar capacidades si el modo es provisionado
				    if (provision) {
				        // Validación de capacidades de lectura y escritura
				        if (spinnerlectura.getValue().toString().isEmpty()
				                || spinnerescritura.getValue().toString().isEmpty()) {
				            throw new IllegalArgumentException("Debe ingresar las capacidades de lectura y escritura");
				        }

				        cap1escritura = Long.parseLong(spinnerescritura.getValue().toString());
				        caplectura = Long.parseLong(spinnerlectura.getValue().toString());

				        // Validar que las capacidades estén dentro del rango permitido
				        if (caplectura < 1 || caplectura > 40000) {
				            throw new IllegalArgumentException("Capacidad de lectura fuera de rango (1-40000)");
				        }

				        if (cap1escritura < 1 || cap1escritura > 40000) {
				            throw new IllegalArgumentException("Capacidad de escritura fuera de rango (1-40000)");
				        }
				    }

				    // Llamada al método para crear la tabla
				    if (tipoordenacion.isEmpty()) {
				        // Sin clave de ordenación
				        tabla.crearTabla(
				            tfnombretabla.getText(), 
				            tfclaveparticion.getText(), 
				            tipoparticion, 
				            null, // Sin clave de ordenación
				            tipoordenacion, 
				            provision, 
				            caplectura, 
				            cap1escritura
				        );
				    } else {
				        // Con clave de ordenación
				        tabla.crearTabla(
				            tfnombretabla.getText(), 
				            tfclaveparticion.getText(), 
				            tipoparticion, 
				            tfnombreordenacion.getText(), // Clave de ordenación
				            tipoordenacion, 
				            provision, 
				            caplectura, 
				            cap1escritura
				        );
				    }

				} catch (Exception ex) {
				    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		});
		btnCrearTabla.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/escribir.png")));
		btnCrearTabla.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearTabla.setBorder(new LineBorder(new Color(242, 165, 70)));
		btnCrearTabla.setBackground(new Color(215, 215, 255));
		btnCrearTabla.setBounds(463, 521, 217, 43);
		panelGestionTabla.add(btnCrearTabla);

		lblNewLabel = new JLabel("Administración de Tablas: Creación y Visualización");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 26));
		lblNewLabel.setBounds(259, 10, 729, 69);
		panelGestionTabla.add(lblNewLabel);

		lnombre = new JLabel("Nombre tabla");
		lnombre.setFont(new Font("Georgia", Font.BOLD, 14));
		lnombre.setBounds(92, 89, 235, 43);
		panelGestionTabla.add(lnombre);

		lclaveparticion = new JLabel("Nombre  de la clave de partición");
		lclaveparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		lclaveparticion.setBounds(92, 126, 248, 43);
		panelGestionTabla.add(lclaveparticion);

		ltipoparticion = new JLabel("Tipo de partición");
		ltipoparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		ltipoparticion.setBounds(92, 167, 235, 43);
		panelGestionTabla.add(ltipoparticion);

		tfnombretabla = new JTextField();
		tfnombretabla.setBounds(453, 93, 217, 30);
		panelGestionTabla.add(tfnombretabla);
		tfnombretabla.setColumns(10);

		tfclaveparticion = new JTextField();
		tfclaveparticion.setColumns(10);
		tfclaveparticion.setBounds(453, 134, 217, 30);
		panelGestionTabla.add(tfclaveparticion);

		combotipoparticion = new JComboBox<String>();
		combotipoparticion.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)" }));
		combotipoparticion.setFont(new Font("Georgia", Font.BOLD, 13));
		combotipoparticion.setBounds(453, 173, 217, 30);
		panelGestionTabla.add(combotipoparticion);

		lresulgestion = new JLabel("");
		lresulgestion.setHorizontalAlignment(SwingConstants.CENTER);
		lresulgestion.setForeground(new Color(255, 0, 0));
		lresulgestion.setFont(new Font("Georgia", Font.BOLD, 16));
		lresulgestion.setBounds(137, 574, 793, 51);
		panelGestionTabla.add(lresulgestion);

		tfnombreordenacion = new JTextField();
		tfnombreordenacion.setBounds(453, 213, 217, 27);
		panelGestionTabla.add(tfnombreordenacion);
		tfnombreordenacion.setColumns(10);

		lblClaveDeOrdenacion = new JLabel("Nombre de Clave de ordenación");
		lblClaveDeOrdenacion.setFont(new Font("Georgia", Font.BOLD, 14));
		lblClaveDeOrdenacion.setBounds(92, 205, 272, 43);
		panelGestionTabla.add(lblClaveDeOrdenacion);

		lblProyecion = new JLabel("Capacidad aprovisionada");
		lblProyecion.setFont(new Font("Georgia", Font.BOLD, 14));
		lblProyecion.setBounds(92, 286, 272, 43);
		panelGestionTabla.add(lblProyecion);

		JLabel lblTipoDeDato = new JLabel("Tipo de dato de la clave de ordenación");
		lblTipoDeDato.setFont(new Font("Georgia", Font.BOLD, 14));
		lblTipoDeDato.setBounds(92, 250, 309, 43);
		panelGestionTabla.add(lblTipoDeDato);

		combotipoclaveordenacion = new JComboBox<String>();
		combotipoclaveordenacion.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)", "Sin ordenación" }));
		combotipoclaveordenacion.setFont(new Font("Georgia", Font.BOLD, 13));
		combotipoclaveordenacion.setBounds(453, 252, 217, 30);
		panelGestionTabla.add(combotipoclaveordenacion);

		cbprovision = new JCheckBox("");
		cbprovision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbprovision.isSelected()) {
					lblCapacidadDeEscritura.setVisible(true);
					lblCapacidadDeLectura.setVisible(true);
					spinnerescritura.setVisible(true);
					spinnerlectura.setVisible(true);
				} else {
					lblCapacidadDeEscritura.setVisible(false);
					lblCapacidadDeLectura.setVisible(false);
					spinnerescritura.setVisible(false);
					spinnerlectura.setVisible(false);
				}
			}
		});
		cbprovision.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbprovision.setBackground(new Color(215, 235, 255));
		cbprovision.setBounds(453, 286, 53, 43);
		panelGestionTabla.add(cbprovision);

		lblCapacidadDeLectura = new JLabel("Capacidad de lectura");
		lblCapacidadDeLectura.setVisible(false);
		lblCapacidadDeLectura.setFont(new Font("Georgia", Font.BOLD, 14));
		lblCapacidadDeLectura.setBounds(92, 331, 309, 43);
		panelGestionTabla.add(lblCapacidadDeLectura);

		spinnerlectura = new JSpinner();
		spinnerlectura.setVisible(false);
		spinnerlectura.setModel(new SpinnerNumberModel(1, 1, 40000, 1));
		spinnerlectura.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinnerlectura.setBounds(453, 335, 53, 34);
		panelGestionTabla.add(spinnerlectura);

		lblCapacidadDeEscritura = new JLabel("Capacidad de escritura");
		lblCapacidadDeEscritura.setVisible(false);
		lblCapacidadDeEscritura.setFont(new Font("Georgia", Font.BOLD, 14));
		lblCapacidadDeEscritura.setBounds(92, 384, 309, 43);
		panelGestionTabla.add(lblCapacidadDeEscritura);

		spinnerescritura = new JSpinner();
		spinnerescritura.setVisible(false);
		spinnerescritura.setModel(new SpinnerNumberModel(1, 1, 40000, 1));
		spinnerescritura.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinnerescritura.setBounds(453, 388, 53, 34);
		panelGestionTabla.add(spinnerescritura);

		JButton btnNewButton = new JButton("Actualizar lista de tablas");
		btnNewButton.setBorder(new LineBorder(new Color(255, 128, 64)));
		btnNewButton.setBackground(new Color(213, 213, 255));
		btnNewButton.setFont(new Font("Georgia", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {

				try {
					// Obtener el cliente DynamoDB desde DynamoApp
					AmazonDynamoDB dynamoDB = dynamoApp.getDynamoDB();
					if (dynamoDB == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}

					// Instancia Gestion_de_Tablas con el cliente válido
					Gestion_de_Tablas gestionTablas = new Gestion_de_Tablas(dynamoDB);

					// Llama al método listarTablas
					List<String> tablas = gestionTablas.listarTablas();
					if(tablas.isEmpty()) {
						lresulgestion.setText("No hay tablas disponibles");
					}else {
						lresulgestion.setText("Tablas actualizadas");
					}
					// Actualiza el modelo de la JList
					dlm = new DefaultListModel<>();
					for (String tabla : tablas) {
						dlm.addElement(tabla);
					}
					listaTablas.setModel(dlm);

				} catch (Exception ex) {
					lresulgestion.setText("Error al listar las tablas ");
					ex.getMessage();
				}
			}

		});
		btnNewButton.setBounds(858, 308, 230, 43);
		panelGestionTabla.add(btnNewButton);

		panelCRUD = new JPanel();
		panelCRUD.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("CRUD", null, panelCRUD, null);
		panelCRUD.setLayout(null);

		ltitulo = new JLabel("Gestión de Datos con Operaciones CRUD");
		ltitulo.setHorizontalAlignment(SwingConstants.CENTER);
		ltitulo.setFont(new Font("Georgia", Font.BOLD, 26));
		ltitulo.setBounds(228, 21, 746, 76);
		panelCRUD.add(ltitulo);

		lnombreatributo = new JLabel("Nombre del atributo");
		lnombreatributo.setFont(new Font("Georgia", Font.BOLD, 15));
		lnombreatributo.setBounds(161, 141, 162, 25);
		panelCRUD.add(lnombreatributo);

		tfnombreatributo = new JTextField();
		tfnombreatributo.setFont(new Font("Georgia", Font.BOLD, 15));
		tfnombreatributo.setBounds(410, 141, 173, 25);
		panelCRUD.add(tfnombreatributo);
		tfnombreatributo.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Georgia", Font.BOLD, 15));
		lblTipo.setBounds(161, 193, 162, 25);
		panelCRUD.add(lblTipo);

		combotipo = new JComboBox<String>();
		combotipo.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)", "Boolean (BOOL)", "Null (NULL)",
						"List (L)", "Map (M)", "String Set (SS)", "Number Set (NS)", "Binary Set (BS)" }));
		combotipo.setFont(new Font("Georgia", Font.BOLD, 15));
		combotipo.setBounds(410, 196, 173, 25);
		panelCRUD.add(combotipo);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Georgia", Font.BOLD, 15));
		lblValor.setBounds(161, 238, 162, 25);
		panelCRUD.add(lblValor);

		tfvalor = new JTextField();
		tfvalor.setFont(new Font("Georgia", Font.BOLD, 15));
		tfvalor.setColumns(10);
		tfvalor.setBounds(410, 242, 173, 25);
		panelCRUD.add(tfvalor);

		btnCrearItem = new JButton("Crear Item");
		btnCrearItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearItem.setBounds(804, 134, 203, 39);
		panelCRUD.add(btnCrearItem);

		btnBorrarItem = new JButton("Borrar Item");
		btnBorrarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnBorrarItem.setBounds(804, 183, 203, 39);
		panelCRUD.add(btnBorrarItem);

		btnModificarItem = new JButton("Modificar Item");
		btnModificarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnModificarItem.setBounds(804, 228, 203, 39);
		panelCRUD.add(btnModificarItem);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(161, 322, 846, 239);
		panelCRUD.add(scrollPane_1);

		tabla = new JTable();
		scrollPane_1.setViewportView(tabla);

		lresulcrud = new JLabel("");
		lresulcrud.setForeground(new Color(255, 0, 0));
		lresulcrud.setFont(new Font("Georgia", Font.BOLD, 14));
		lresulcrud.setHorizontalAlignment(SwingConstants.CENTER);
		lresulcrud.setBounds(201, 571, 746, 55);
		panelCRUD.add(lresulcrud);

		panelConsultas = new JPanel();
		panelConsultas.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("Consultas", null, panelConsultas, null);
		panelConsultas.setLayout(null);
	}
}
