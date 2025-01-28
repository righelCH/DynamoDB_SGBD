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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import Righel_Backend.DynamoApp;
import Righel_Backend.Gestion_de_Tablas;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;

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
	private JTextField tfnombreatributocrud;
	private JTextField tfvaloratributocrud;
	private JComboBox<String> combotipocrud;
	private JButton btnModificarItem;
	private JTable tablaDatos;
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
	private JLabel lblTipoDeDato;
	private JButton btnActualizar;
	private JLabel lblTipo;
	private JLabel lblValor;
	private Gestion_de_Tablas gestionTablas; // Declarar la variable
	private JTextField tfnombreclaveParticioncrud;
	private JTextField tfValorUnicoParticioncrud;
	private JTextField tfvalorclaveparticioncrud;
	private JLabel lvalorclaveparticion;
	private JLabel lnombreclaveparticion;
	private JTextField tfvalorclaveordenacioncrud;
	private JLabel ltipoparticion_1;
	private JLabel ltipoparticion_1_1;
	private JButton btnLeerItem;

	private DefaultTableModel dtm;
	private JButton btnActualizarTabla;
	private JTextField tnombreTabla;
	private JTextField tfiltroregex;
	private JButton baplicarfiltro;
	private JTextArea textArea;

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
		String[] columnas = {};
		dtm = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override // Método para hacer las celdas no editables con FALSE
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override // Método indica que los datos han cambiado
			public void fireTableDataChanged() {
				super.fireTableDataChanged();
			}

			@Override // Método que devuelve el no filas
			public int getRowCount() {
				return super.getRowCount();
			}

			@Override // Método que elimina fila
			public void removeRow(int row) {
				super.removeRow(row);
			}

			@Override // Método indica estructura tabla ha cambiado
			public void fireTableStructureChanged() {
				super.fireTableStructureChanged();
			}
		};
		GUI.dynamoApp = dynamoApp; // Reutilizar la conexión existente
		AmazonDynamoDB client = dynamoApp.getDynamoDB();
		setGestionTablas(new Gestion_de_Tablas(client)); // Inicializar Gestion_de_Tablas

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
		btnMostrarTablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTable = listaTablas.getSelectedValue();
		        if (selectedTable != null) {
		            cargarDatosTabla(selectedTable); // Cargar los datos de la tabla seleccionada
		            tabbedPane.setSelectedComponent(panelCRUD); // Cambiar a la pestaña CRUD
		        } else {
		            JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnMostrarTablas.setBorder(new LineBorder(new Color(242, 165, 70)));
		btnMostrarTablas.setFont(new Font("Georgia", Font.BOLD, 14));
		btnMostrarTablas.setBackground(new Color(215, 215, 255));
		btnMostrarTablas.setBounds(858, 412, 230, 43);
		panelGestionTabla.add(btnMostrarTablas);

		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setBounds(858, 117, 230, 193);
		panelGestionTabla.add(scrollPane);

		listaTablas = new JList<String>();
		scrollPane.setViewportView(listaTablas);
		listaTablas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 160)), "<html><b>Tablas</html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listaTablas.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Asegúrate de que la selección no se esté ajustando
					String selectedTable = listaTablas.getSelectedValue();
					if (selectedTable != null) {
						cargarDatosTabla(selectedTable);
					}
				}
			}

		});
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
					System.out.println("Realizada la conexión, tablas existentes: " + result);
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
						tabla.crearTabla(tfnombretabla.getText(), tfclaveparticion.getText(), tipoparticion, null, // Sin
																													// clave
																													// de
																													// ordenación
								tipoordenacion, provision, caplectura, cap1escritura);
					} else {
						// Con clave de ordenación
						tabla.crearTabla(tfnombretabla.getText(), tfclaveparticion.getText(), tipoparticion,
								tfnombreordenacion.getText(), // Clave de ordenación
								tipoordenacion, provision, caplectura, cap1escritura);
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
		lnombre.setBounds(82, 117, 235, 43);
		panelGestionTabla.add(lnombre);

		lclaveparticion = new JLabel("Nombre  de la clave de partición (Clave Primaria)");
		lclaveparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		lclaveparticion.setBounds(82, 154, 366, 43);
		panelGestionTabla.add(lclaveparticion);

		ltipoparticion = new JLabel("Tipo de partición");
		ltipoparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		ltipoparticion.setBounds(82, 195, 235, 43);
		panelGestionTabla.add(ltipoparticion);

		tfnombretabla = new JTextField();
		tfnombretabla.setBounds(473, 121, 217, 30);
		panelGestionTabla.add(tfnombretabla);
		tfnombretabla.setColumns(10);

		tfclaveparticion = new JTextField();
		tfclaveparticion.setColumns(10);
		tfclaveparticion.setBounds(473, 162, 217, 30);
		panelGestionTabla.add(tfclaveparticion);

		combotipoparticion = new JComboBox<String>();
		combotipoparticion.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)" }));
		combotipoparticion.setFont(new Font("Georgia", Font.BOLD, 13));
		combotipoparticion.setBounds(473, 201, 217, 30);
		panelGestionTabla.add(combotipoparticion);

		lresulgestion = new JLabel("");
		lresulgestion.setHorizontalAlignment(SwingConstants.CENTER);
		lresulgestion.setForeground(new Color(255, 0, 0));
		lresulgestion.setFont(new Font("Georgia", Font.BOLD, 16));
		lresulgestion.setBounds(137, 574, 793, 51);
		panelGestionTabla.add(lresulgestion);

		tfnombreordenacion = new JTextField();
		tfnombreordenacion.setBounds(473, 241, 217, 27);
		panelGestionTabla.add(tfnombreordenacion);
		tfnombreordenacion.setColumns(10);

		lblClaveDeOrdenacion = new JLabel("Nombre de Clave de ordenación");
		lblClaveDeOrdenacion.setFont(new Font("Georgia", Font.BOLD, 14));
		lblClaveDeOrdenacion.setBounds(82, 233, 272, 43);
		panelGestionTabla.add(lblClaveDeOrdenacion);

		lblProyecion = new JLabel("Capacidad aprovisionada");
		lblProyecion.setFont(new Font("Georgia", Font.BOLD, 14));
		lblProyecion.setBounds(82, 314, 272, 43);
		panelGestionTabla.add(lblProyecion);

		lblTipoDeDato = new JLabel("Tipo de dato de la clave de ordenación");
		lblTipoDeDato.setFont(new Font("Georgia", Font.BOLD, 14));
		lblTipoDeDato.setBounds(82, 278, 309, 43);
		panelGestionTabla.add(lblTipoDeDato);

		combotipoclaveordenacion = new JComboBox<String>();
		combotipoclaveordenacion.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)" }));
		combotipoclaveordenacion.setFont(new Font("Georgia", Font.BOLD, 13));
		combotipoclaveordenacion.setBounds(473, 280, 217, 30);
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
		cbprovision.setBounds(473, 314, 53, 43);
		panelGestionTabla.add(cbprovision);

		lblCapacidadDeLectura = new JLabel("Capacidad de lectura");
		lblCapacidadDeLectura.setVisible(false);
		lblCapacidadDeLectura.setFont(new Font("Georgia", Font.BOLD, 14));
		lblCapacidadDeLectura.setBounds(82, 359, 309, 43);
		panelGestionTabla.add(lblCapacidadDeLectura);

		spinnerlectura = new JSpinner();
		spinnerlectura.setVisible(false);
		spinnerlectura.setModel(new SpinnerNumberModel(1, 1, 40000, 1));
		spinnerlectura.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinnerlectura.setBounds(473, 363, 53, 34);
		panelGestionTabla.add(spinnerlectura);

		lblCapacidadDeEscritura = new JLabel("Capacidad de escritura");
		lblCapacidadDeEscritura.setVisible(false);
		lblCapacidadDeEscritura.setFont(new Font("Georgia", Font.BOLD, 14));
		lblCapacidadDeEscritura.setBounds(82, 412, 309, 43);
		panelGestionTabla.add(lblCapacidadDeEscritura);

		spinnerescritura = new JSpinner();
		spinnerescritura.setVisible(false);
		spinnerescritura.setModel(new SpinnerNumberModel(1, 1, 40000, 1));
		spinnerescritura.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinnerescritura.setBounds(473, 416, 53, 34);
		panelGestionTabla.add(spinnerescritura);

		btnActualizar = new JButton("Mostrar lista de tablas");
		btnActualizar.setBorder(new LineBorder(new Color(255, 128, 64)));
		btnActualizar.setBackground(new Color(213, 213, 255));
		btnActualizar.setFont(new Font("Georgia", Font.BOLD, 14));
		btnActualizar.addActionListener(new ActionListener() {

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
					if (tablas.isEmpty()) {
						lresulgestion.setText("No hay tablas disponibles");
					} else {
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
		btnActualizar.setBounds(858, 359, 230, 43);
		panelGestionTabla.add(btnActualizar);

		panelCRUD = new JPanel();
		panelCRUD.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("CRUD", null, panelCRUD, null);
		panelCRUD.setLayout(null);

		ltitulo = new JLabel("Gestión de Datos con Operaciones CRUD");
		ltitulo.setHorizontalAlignment(SwingConstants.CENTER);
		ltitulo.setFont(new Font("Georgia", Font.BOLD, 26));
		ltitulo.setBounds(216, 10, 746, 76);
		panelCRUD.add(ltitulo);

		lnombreatributo = new JLabel("Nombre del atributo Nuevo");
		lnombreatributo.setFont(new Font("Georgia", Font.BOLD, 15));
		lnombreatributo.setBounds(103, 277, 274, 25);
		panelCRUD.add(lnombreatributo);

		tfnombreatributocrud = new JTextField();
		tfnombreatributocrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfnombreatributocrud.setBounds(426, 277, 217, 25);
		panelCRUD.add(tfnombreatributocrud);
		tfnombreatributocrud.setColumns(10);

		lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Georgia", Font.BOLD, 15));
		lblTipo.setBounds(103, 320, 274, 25);
		panelCRUD.add(lblTipo);

		combotipocrud = new JComboBox<String>();
		combotipocrud.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)", "Boolean (BOOL)", "Null (NULL)",
						"List (L)", "Map (M)", "String Set (SS)", "Number Set (NS)", "Binary Set (BS)" }));
		combotipocrud.setFont(new Font("Georgia", Font.BOLD, 14));
		combotipocrud.setBounds(426, 320, 217, 25);
		panelCRUD.add(combotipocrud);

		lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Georgia", Font.BOLD, 15));
		lblValor.setBounds(103, 365, 274, 25);
		panelCRUD.add(lblValor);

		tfvaloratributocrud = new JTextField();
		tfvaloratributocrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfvaloratributocrud.setColumns(10);
		tfvaloratributocrud.setBounds(426, 365, 217, 25);
		panelCRUD.add(tfvaloratributocrud);

		btnCrearItem = new JButton("Crear Item");
		btnCrearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCrearItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							// Obtener el nombre de la tabla seleccionada desde el JList
							String selectedTable = listaTablas.getSelectedValue(); // listaTablas es tu JList
							if (selectedTable == null || selectedTable.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Capturar los valores ingresados por el usuario
							String partitionKeyName = tfnombreclaveParticioncrud.getText().trim(); // Nombre de la clave
																									// de partición
							String partitionKeyValue = tfvalorclaveparticioncrud.getText().trim(); // Valor de la clave
																									// de
																									// partición
							String sortKeyName = tfValorUnicoParticioncrud.getText().trim(); // Nombre de la clave de
																								// ordenación
							String sortKeyValue = tfvalorclaveordenacioncrud.getText().trim(); // Valor de la clave de
																								// ordenación
							String attributeName = tfnombreatributocrud.getText().trim(); // Nombre del atributo
																							// adicional
							String attributeType = combotipocrud.getSelectedItem().toString(); // Tipo del atributo
																								// adicional
							String attributeValue = tfvaloratributocrud.getText().trim(); // Valor del atributo
																							// adicional

							// Validar campos obligatorios
							if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"La clave de partición y su valor son obligatorios.", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							if (attributeName.isEmpty() || attributeValue.isEmpty()
									|| attributeType.equals("Seleccione")) {
								JOptionPane.showMessageDialog(null,
										"Debe proporcionar un atributo, su tipo y su valor.", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Configurar la conexión a DynamoDB
							AmazonDynamoDB client = dynamoApp.getDynamoDB();
							if (client == null) {
								throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
							}

							// Crear instancia de DynamoDB y seleccionar la tabla
							DynamoDB dynamoDB = new DynamoDB(client);
							Table table = dynamoDB.getTable(selectedTable);

							// Crear el ítem
							Item item;

							// Verificar si la clave de ordenación es necesaria
							if (!sortKeyName.isEmpty() && !sortKeyValue.isEmpty()) {
								item = new Item().withPrimaryKey(partitionKeyName, partitionKeyValue, sortKeyName,
										sortKeyValue);
							} else {
								item = new Item().withPrimaryKey(partitionKeyName, partitionKeyValue);
							}

							// Procesar el tipo del atributo y agregarlo al ítem
							switch (attributeType) {
							case "String (S)":
								item.withString(attributeName, attributeValue);
								break;
							case "Number (N)":
								item.withNumber(attributeName, Double.parseDouble(attributeValue));
								break;
							case "Boolean (BOOL)":
								item.withBoolean(attributeName, Boolean.parseBoolean(attributeValue));
								break;
							case "Binary (B)":
								item.withBinary(attributeName, attributeValue.getBytes());
								break;
							case "Null (NULL)":
								item.withNull(attributeName);
								break;
							case "List (L)":
								List<String> list = Arrays.asList(attributeValue.split(","));
								item.withList(attributeName, list);
								break;
							case "Map (M)":
								Map<String, String> map = Arrays.stream(attributeValue.split(","))
										.map(s -> s.split("="))
										.collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));
								item.withMap(attributeName, map);
								break;
							case "String Set (SS)":
								Set<String> stringSet = new HashSet<>(Arrays.asList(attributeValue.split(",")));
								item.withStringSet(attributeName, stringSet);
								break;
							case "Number Set (NS)":
								Set<Number> numberSet = Arrays.stream(attributeValue.split(","))
										.map(Double::parseDouble).collect(Collectors.toSet());
								item.withNumberSet(attributeName, numberSet);
								break;
							case "Binary Set (BS)":
								Set<byte[]> binarySet = Arrays.stream(attributeValue.split(",")).map(String::getBytes)
										.collect(Collectors.toSet());
								item.withBinarySet(attributeName, binarySet);
								break;
							default:
								JOptionPane.showMessageDialog(null, "Tipo de atributo no soportado.", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Insertar el ítem en la tabla
							table.putItem(item);

							// Mensaje de éxito
							JOptionPane.showMessageDialog(null,
									"Ítem creado exitosamente en la tabla: " + selectedTable, "Éxito",
									JOptionPane.INFORMATION_MESSAGE);

						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,
									"El valor ingresado no es válido para el tipo seleccionado (ejemplo: Number).",
									"Error", JOptionPane.ERROR_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Error al crear el ítem: " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
					}
				});

			}

		});
		btnCrearItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearItem.setBounds(804, 134, 203, 39);
		panelCRUD.add(btnCrearItem);

		btnBorrarItem = new JButton("Borrar Item");
		btnBorrarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBorrarItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							// Obtener el nombre de la tabla seleccionada
							String selectedTable = listaTablas.getSelectedValue();
							if (selectedTable == null || selectedTable.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Capturar las claves
							String partitionKeyName = tfnombreclaveParticioncrud.getText().trim();
							String partitionKeyValue = tfvalorclaveparticioncrud.getText().trim();
							String sortKeyName = tfValorUnicoParticioncrud.getText().trim();
							String sortKeyValue = tfvalorclaveordenacioncrud.getText().trim();

							// Validar claves
							if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"La clave de partición y su valor son obligatorios para borrar un ítem.",
										"Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							// Configurar la conexión a DynamoDB
							AmazonDynamoDB client = dynamoApp.getDynamoDB();
							if (client == null) {
								throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
							}

							// Crear instancia de DynamoDB y seleccionar la tabla
							DynamoDB dynamoDB = new DynamoDB(client);
							Table table = dynamoDB.getTable(selectedTable);

							// Borrar el ítem
							if (!sortKeyName.isEmpty() && !sortKeyValue.isEmpty()) {
								table.deleteItem(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue);
							} else {
								table.deleteItem(partitionKeyName, partitionKeyValue);
							}

							// Mensaje de éxito
							JOptionPane.showMessageDialog(null,
									"Ítem borrado exitosamente de la tabla: " + selectedTable, "Éxito",
									JOptionPane.INFORMATION_MESSAGE);

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Error al borrar el ítem: " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
					}
				});

			}
		});
		btnBorrarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnBorrarItem.setBounds(804, 183, 203, 39);
		panelCRUD.add(btnBorrarItem);

		btnModificarItem = new JButton("Modificar Item");
		btnModificarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener el nombre de la tabla seleccionada
					String selectedTable = listaTablas.getSelectedValue();
					if (selectedTable == null || selectedTable.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Capturar claves y datos a modificar

					String partitionKeyName = tfnombreclaveParticioncrud.getText().trim(); // Nombre de la clave de
																							// partición
					String partitionKeyValue = tfvalorclaveparticioncrud.getText().trim(); // Valor de la clave de
																							// partición
					String sortKeyName = tfValorUnicoParticioncrud.getText().trim(); // Nombre de la clave de ordenación
					String sortKeyValue = tfvalorclaveordenacioncrud.getText().trim(); // Valor de la clave de
																						// ordenación
					String attributeName = tfnombreatributocrud.getText().trim(); // Nombre del atributo adicional
					String attributeType = combotipocrud.getSelectedItem().toString(); // Tipo del atributo adicional
					String attributeValue = tfvaloratributocrud.getText().trim(); // Valor del atributo adicional

					// Validar campos
					if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"La clave de partición y su valor son obligatorios para modificar un ítem.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (attributeName.isEmpty() || attributeValue.isEmpty() || attributeType.equals("Seleccione")) {
						JOptionPane.showMessageDialog(null,
								"Debe proporcionar el nombre, tipo y valor del atributo a modificar.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Configurar la conexión a DynamoDB
					AmazonDynamoDB client = dynamoApp.getDynamoDB();
					if (client == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}
/*
 *AmazonDynamoDB client = dynamoApp.getDynamoDB(); 
 *DynamoDB dynamoDB = new DynamoDB(client);*/
					// Crear instancia de DynamoDB y seleccionar la tabla
					DynamoDB dynamoDB = new DynamoDB(client);
					Table table = dynamoDB.getTable(selectedTable);

					// Crear la especificación para la actualización
					UpdateItemSpec updateItemSpec = new UpdateItemSpec()
							.withPrimaryKey(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue)
							.withUpdateExpression("set #attr = :val")
							.withNameMap(new NameMap().with("#attr", attributeName)).withValueMap(
									new ValueMap().with(":val", processAttributeValue(attributeType, attributeValue)));

					// Ejecutar la actualización
					table.updateItem(updateItemSpec);

					// Mensaje de éxito
					JOptionPane.showMessageDialog(null, "Ítem modificado exitosamente en la tabla: " + selectedTable,
							"Éxito", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al modificar el ítem: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

			}
		});
		btnModificarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnModificarItem.setBounds(804, 228, 203, 39);
		panelCRUD.add(btnModificarItem);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(103, 418, 904, 143);
		panelCRUD.add(scrollPane_1);

		tablaDatos = new JTable(dtm);
		tablaDatos.setForeground(Color.DARK_GRAY);
		tablaDatos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tablaDatos.setShowVerticalLines(true);
		tablaDatos.setShowHorizontalLines(false);
		tablaDatos.setRowSelectionAllowed(true);
		tablaDatos.setColumnSelectionAllowed(false);
		tablaDatos.setSelectionForeground(Color.white);
		tablaDatos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablaDatos.setVisible(true);
		tablaDatos.getTableHeader().setReorderingAllowed(false);
		tablaDatos.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(tablaDatos);

		lresulcrud = new JLabel("");
		lresulcrud.setForeground(new Color(255, 0, 0));
		lresulcrud.setFont(new Font("Georgia", Font.BOLD, 14));
		lresulcrud.setHorizontalAlignment(SwingConstants.CENTER);
		lresulcrud.setBounds(201, 571, 746, 55);
		panelCRUD.add(lresulcrud);

		lnombreclaveparticion = new JLabel("Nombre  de la clave de partición");
		lnombreclaveparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		lnombreclaveparticion.setBounds(103, 95, 276, 43);
		panelCRUD.add(lnombreclaveparticion);

		ltipoparticion_1 = new JLabel("Nombre de la clave de ordenación");
		ltipoparticion_1.setFont(new Font("Georgia", Font.BOLD, 14));
		ltipoparticion_1.setBounds(103, 183, 276, 43);
		panelCRUD.add(ltipoparticion_1);

		tfnombreclaveParticioncrud = new JTextField();
		tfnombreclaveParticioncrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfnombreclaveParticioncrud.setColumns(10);
		tfnombreclaveParticioncrud.setBounds(426, 102, 217, 30);
		panelCRUD.add(tfnombreclaveParticioncrud);

		tfValorUnicoParticioncrud = new JTextField();
		tfValorUnicoParticioncrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfValorUnicoParticioncrud.setColumns(10);
		tfValorUnicoParticioncrud.setBounds(426, 186, 217, 30);
		panelCRUD.add(tfValorUnicoParticioncrud);

		lvalorclaveparticion = new JLabel("Valor de la clave de partición");
		lvalorclaveparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		lvalorclaveparticion.setBounds(103, 134, 276, 43);
		panelCRUD.add(lvalorclaveparticion);

		tfvalorclaveparticioncrud = new JTextField();
		tfvalorclaveparticioncrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfvalorclaveparticioncrud.setColumns(10);
		tfvalorclaveparticioncrud.setBounds(426, 141, 217, 30);
		panelCRUD.add(tfvalorclaveparticioncrud);

		ltipoparticion_1_1 = new JLabel("Valor de la clave de ordenación");
		ltipoparticion_1_1.setFont(new Font("Georgia", Font.BOLD, 14));
		ltipoparticion_1_1.setBounds(103, 229, 276, 43);
		panelCRUD.add(ltipoparticion_1_1);

		tfvalorclaveordenacioncrud = new JTextField();
		tfvalorclaveordenacioncrud.setFont(new Font("Georgia", Font.BOLD, 14));
		tfvalorclaveordenacioncrud.setColumns(10);
		tfvalorclaveordenacioncrud.setBounds(426, 232, 217, 30);
		panelCRUD.add(tfvalorclaveordenacioncrud);

		btnLeerItem = new JButton("Leer item");
		btnLeerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLeerItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnLeerItem.setBounds(804, 280, 203, 39);
		panelCRUD.add(btnLeerItem);
		
		btnActualizarTabla = new JButton("Actualizar Tabla");
		btnActualizarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTable = listaTablas.getSelectedValue();
		        if (selectedTable != null) {
		            cargarDatosTabla(selectedTable); // Cargar los datos de la tabla seleccionada
		            dtm.fireTableDataChanged();
					dtm.fireTableStructureChanged();
		        } else {
		            JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
		        }
				
			}
		});
		btnActualizarTabla.setFont(new Font("Georgia", Font.BOLD, 14));
		btnActualizarTabla.setBounds(804, 351, 203, 39);
		panelCRUD.add(btnActualizarTabla);

		panelConsultas = new JPanel();
		panelConsultas.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("Consultas", null, panelConsultas, null);
		panelConsultas.setLayout(null);
		
		tnombreTabla = new JTextField();
		tnombreTabla.setBounds(244, 38, 86, 20);
		panelConsultas.add(tnombreTabla);
		tnombreTabla.setColumns(10);
		
		tfiltroregex = new JTextField();
		tfiltroregex.setBounds(435, 41, 86, 20);
		panelConsultas.add(tfiltroregex);
		tfiltroregex.setColumns(10);
		
		baplicarfiltro = new JButton("Aplicar filtro");
		baplicarfiltro.setBounds(293, 101, 89, 23);
		panelConsultas.add(baplicarfiltro);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de tabla");
		lblNewLabel_1.setBounds(137, 41, 97, 14);
		panelConsultas.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Filtro");
		lblNewLabel_3.setBounds(379, 41, 46, 14);
		panelConsultas.add(lblNewLabel_3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(148, 135, 444, 260);
		panelConsultas.add(scrollPane_2);
		
		textArea = new JTextArea();
		scrollPane_2.setViewportView(textArea);
		baplicarfiltro.addActionListener(e->{
			consultarElementosConFiltro();
		});
	}

	
	private Object processAttributeValue(String type, String value) {
		switch (type) {
		case "String (S)":
			return value;
		case "Number (N)":
			return Double.parseDouble(value);
		case "Boolean (BOOL)":
			return Boolean.parseBoolean(value);
		default:
			throw new IllegalArgumentException("Tipo de atributo no soportado para la actualización.");
		}
	}

	public void consultarElementosConFiltro() {
    String tableName = "TEST2";  // Nombre de la tabla
    String partitionKeyName = "clave_participacion";  // Nombre de la clave de partición
    String partitionKeyValue = "101";  // Valor de la clave de partición
    String filterKeyName = "campo1";  // Campo a filtrar
    String filterValue = "7";  // Valor del filtro
    AmazonDynamoDB client = dynamoApp.getDynamoDB(); 
    DynamoDB dynamoDB = new DynamoDB(client);

    // Establecer el filtro con la expresión
    String keyConditionExpression = partitionKeyName + " = :partitionKeyValue";
    String filterExpression = filterKeyName + " = :filterValue";
    
    Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
    expressionAttributeValues.put(":partitionKeyValue", new AttributeValue().withS(partitionKeyValue));  // Usar .withS() para String
    expressionAttributeValues.put(":filterValue", new AttributeValue().withN(filterValue));  // Usar .withN() para número

    try {
        // Crear la solicitud de consulta con la expresión de condición y filtro
        QueryRequest queryRequest = new QueryRequest()
            .withTableName(tableName)
            .withKeyConditionExpression(keyConditionExpression)
            .withFilterExpression(filterExpression)
            .withExpressionAttributeValues(expressionAttributeValues);

        // Ejecutar la consulta
        QueryResult result = client.query(queryRequest);

        // Verificar si hay elementos en la respuesta
        if (result.getItems().isEmpty()) {
            textArea.append("No se encontraron resultados.\n");
        } else {
            // Procesar los resultados
            for (Map<String, AttributeValue> item : result.getItems()) {
                // Mostrar el resultado en el TextArea
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
                    sb.append(entry.getKey())
                      .append(": ")
                      .append(entry.getValue().getS())  // Asumiendo que es un valor String, cambia si es diferente
                      .append("\n");
                }
                textArea.append(sb.toString() + "\n");
                System.out.println("Resultado: " + sb.toString());
            }
        }
    } catch (AmazonDynamoDBException e) {
        System.err.println("Error de consulta: " + e.getMessage());
    }
}

public Gestion_de_Tablas getGestionTablas() {
		return gestionTablas;
	}

	public void setGestionTablas(Gestion_de_Tablas gestionTablas) {
		this.gestionTablas = gestionTablas;
	}

	private void cargarDatosTabla(String tableName) {
	    try {
	        // Configurar la conexión a DynamoDB
	        AmazonDynamoDB client = dynamoApp.getDynamoDB();
	        if (client == null) {
	            throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
	        }

	        // Crear una instancia de DynamoDB
	        DynamoDB dynamoDB = new DynamoDB(client);
	        Table table = dynamoDB.getTable(tableName);

	        // Escanear la tabla
	        ItemCollection<ScanOutcome> items = table.scan();

	        // Verificar si se obtuvieron datos
	        if (!items.iterator().hasNext()) {
	            System.out.println("No se encontraron datos en la tabla: " + tableName);
	            JOptionPane.showMessageDialog(null, "La tabla está vacía.", "Información",
	                    JOptionPane.INFORMATION_MESSAGE);
	            dtm.fireTableDataChanged();
				dtm.fireTableStructureChanged();
	        }

	        // Imprimir los datos obtenidos
	        System.out.println("Datos obtenidos de la tabla " + tableName + ":");
	        for (Item item : items) {
	            System.out.println(item.toJSONPretty());
	        }

	        // Obtener los nombres de las columnas dinámicamente
	        Set<String> columnNamesSet = new HashSet<>();
	        for (Item item : items) {
	            columnNamesSet.addAll(item.asMap().keySet());
	        }

	        // Convertir a una lista para definir las columnas en el modelo
	        List<String> columnNames = new ArrayList<>(columnNamesSet);

	        // Crear un modelo para la tabla
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames.toArray(), 0);

	        // Llenar el modelo con los datos
	        for (Item item : items) {
	            Object[] rowData = new Object[columnNames.size()];
	            for (int i = 0; i < columnNames.size(); i++) {
	                String columnName = columnNames.get(i);
	                rowData[i] = item.get(columnName); // Obtener el valor del atributo
	            }
	            tableModel.addRow(rowData);
	        }

	        // Asignar el modelo al JTable
	        tablaDatos.setModel(tableModel);

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, "Error al cargar datos de la tabla: " + ex.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	        ex.printStackTrace();
	    }
	}
}
