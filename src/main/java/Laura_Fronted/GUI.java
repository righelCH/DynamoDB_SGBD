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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.formdev.flatlaf.FlatLightLaf;

import Righel_Backend.DynamoApp;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

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
	private JComboBox<String> cbnombretabla;
	private JButton bbuscar;
	private JTextArea textArea;
	private JTextField tvalorclav;
	private JLabel lnombreclaveconsulta;
	private JTextField tnombreclavep;
	private AmazonDynamoDB userBajoNivel;
	private JLabel lnombretablaconsulta;
	private JScrollPane scrollPane_2;
	private JLabel lvalorclaveparticionconsulta;
	private JLabel lconsultasid;
	private static JTextArea textAreadatos;
	private JScrollPane scrollPane_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatLightLaf());
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
		GUI.dynamoApp = dynamoApp;

		userBajoNivel = dynamoApp.getDynamoDB();
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

		setTitle("DynamoDB");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/multimedia/Icono_dynamo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.decode("#FFE700"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setBackground(Color.decode("#FFE700"));
		tabbedPane.setBounds(0, 0, 1190, 663);
		contentPane.add(tabbedPane);

		panelGestionTabla = new JPanel();
		panelGestionTabla.setBackground(Color.decode("#BBE9FF"));
		tabbedPane.addTab("Gestión Tabla", new ImageIcon(GUI.class.getResource("/multimedia/gestion-de-proyectos.png")),
				panelGestionTabla, null);
		panelGestionTabla.setLayout(null);

		btnMostrarTablas = new JButton("Abrir Tabla");
		btnMostrarTablas.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/abrir-correo-electronico.png")));
		btnMostrarTablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTable = listaTablas.getSelectedValue();
				if (selectedTable != null) {
					dynamoApp.gestorTablas.cargarDatosTabla(selectedTable, tablaDatos); // Cargar los datos de la tabla
																						// seleccionada
					tabbedPane.setSelectedComponent(panelCRUD); // Cambiar a la pestaña CRUD
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnMostrarTablas.setBorder(new LineBorder(new Color(255, 255, 255)));
		btnMostrarTablas.setFont(new Font("Georgia", Font.BOLD, 14));
		btnMostrarTablas.setBackground(Color.decode("#00FF9C"));
		btnMostrarTablas.setBounds(858, 412, 230, 43);
		panelGestionTabla.add(btnMostrarTablas);

		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setBounds(858, 117, 230, 193);
		panelGestionTabla.add(scrollPane);

		listaTablas = new JList<String>();
		listaTablas.setBackground(new Color(255, 255, 215));
		scrollPane.setViewportView(listaTablas);
		listaTablas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 160)), "<html><b>Tablas</html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listaTablas.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Asegurar que la selección no se esté ajustando
					String selectedTable = listaTablas.getSelectedValue();
					if (selectedTable != null) {
						dynamoApp.gestorTablas.cargarDatosTabla(selectedTable, tablaDatos);
					}
				}
			}

		});

		btnCrearTabla = new JButton(" Crear Tabla");
		btnCrearTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (userBajoNivel == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}
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
						dynamoApp.gestorTablas.crearTabla(tfnombretabla.getText(), tfclaveparticion.getText(),
								tipoparticion, null, tipoordenacion, provision, caplectura, cap1escritura);

					} else {
						// Con clave de ordenación
						dynamoApp.gestorTablas.crearTabla(tfnombretabla.getText(), tfclaveparticion.getText(),
								tipoparticion, tfnombreordenacion.getText(), tipoordenacion, provision, caplectura,
								cap1escritura);
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}
		}); // 00FF9C
		btnCrearTabla.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/escribir.png")));
		btnCrearTabla.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearTabla.setBorder(new LineBorder(new Color(255, 255, 255)));
		btnCrearTabla.setBackground(Color.decode("#00FF9C"));
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
		cbprovision.setBackground(Color.decode("#BBE9FF"));
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

		btnActualizar = new JButton(
				"<html></Center>Mostrar lista de tablas/<br>actualizar lista de tablas</Center></html>");
		btnActualizar.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/mostrar-contrasena.png")));

		btnActualizar.setBorder(new LineBorder(new Color(255, 255, 255)));
		btnActualizar.setBackground(Color.decode("#00FF9C"));
		btnActualizar.setFont(new Font("Georgia", Font.BOLD, 14));
		btnActualizar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					if (userBajoNivel == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}

					List<String> tablas = dynamoApp.gestorTablas.listarTablas();

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
		btnActualizar.setBounds(858, 341, 230, 61);
		panelGestionTabla.add(btnActualizar);

		panelCRUD = new JPanel();
		panelCRUD.setBackground(Color.decode("#BBE9FF"));
		tabbedPane.addTab("CRUD", new ImageIcon(GUI.class.getResource("/multimedia/datos-desestructurados.png")),
				panelCRUD, null);
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
		btnCrearItem.setBackground(Color.decode("#00FF9C"));
		btnCrearItem.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/varita-magica.png")));
		btnCrearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener el nombre de la tabla seleccionada desde el JList
					String selectedTable = listaTablas.getSelectedValue();
					if (selectedTable == null || selectedTable.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Capturar los valores ingresados por el usuario
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

					// Validar campos obligatorios
					if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
						JOptionPane.showMessageDialog(null, "La clave de partición y su valor son obligatorios.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (attributeName.isEmpty() || attributeValue.isEmpty() || attributeType.equals("Seleccione")) {
						JOptionPane.showMessageDialog(null, "Debe proporcionar un atributo, su tipo y su valor.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					dynamoApp.operacion.crearItem(selectedTable, partitionKeyName, partitionKeyValue, sortKeyName,
							sortKeyValue, attributeName, attributeType, attributeValue);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnCrearItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearItem.setBounds(804, 134, 203, 39);
		panelCRUD.add(btnCrearItem);

		btnBorrarItem = new JButton("Borrar Item");
		btnBorrarItem.setBackground(Color.decode("#00FF9C"));
		btnBorrarItem.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/goma-de-borrar.png")));
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

					if (userBajoNivel == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}

					dynamoApp.operacion.borrarItem(selectedTable, partitionKeyName, partitionKeyValue, sortKeyName,
							sortKeyValue, userBajoNivel);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al borrar el ítem: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		btnBorrarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnBorrarItem.setBounds(804, 183, 203, 39);
		panelCRUD.add(btnBorrarItem);

		btnModificarItem = new JButton("Modificar Item");
		btnModificarItem.setBackground(Color.decode("#00FF9C"));
		btnModificarItem.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/editar.png")));
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

					if (userBajoNivel == null) {
						throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
					}

					// Llamar a la operación para modificar el ítem utilizando la estructura de
					// dynamoApp.operacion
					dynamoApp.operacion.modificarItem(selectedTable, partitionKeyName, partitionKeyValue, sortKeyName,
							sortKeyValue, attributeName, attributeType, attributeValue, userBajoNivel);

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
		tablaDatos.setShowHorizontalLines(true);
		tablaDatos.setRowSelectionAllowed(true);
		tablaDatos.setColumnSelectionAllowed(false);
		tablaDatos.setSelectionForeground(Color.white);
		tablaDatos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablaDatos.setVisible(true);
		tablaDatos.getTableHeader().setReorderingAllowed(false);
		tablaDatos.setAutoCreateRowSorter(true);

		tablaDatos.setSelectionForeground(Color.black);
		tablaDatos.setSelectionBackground(Color.cyan);
		tablaDatos.setGridColor(Color.red); // Establece el color de la cuadrícula
		tablaDatos.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablaDatos.getTableHeader().setBackground(Color.MAGENTA); // Cambia el color de fondo del encabezado
		tablaDatos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Cambia fuente del encabezado
		tablaDatos.getTableHeader().setForeground(new Color(255, 255, 255)); // Cambia color de la fuente del encabezado
		tablaDatos.setVisible(true);

		// Cambiar la alineación del texto del encabezado
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tablaDatos.getTableHeader()
				.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tablaDatos.getTableHeader().repaint();

		// Cambiar la alineación del texto en las celdas
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Aplicar el renderizador a todas las columnas
		for (int i = 0; i < tablaDatos.getColumnCount(); i++) {
			tablaDatos.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}

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
		btnLeerItem.setBackground(Color.decode("#00FF9C"));
		btnLeerItem.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/leer.png")));
		btnLeerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener el nombre de la tabla seleccionada desde el JList
					String selectedTable = listaTablas.getSelectedValue();
					if (selectedTable == null || selectedTable.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Capturar las claves
					String partitionKeyName = tfnombreclaveParticioncrud.getText().trim(); // Nombre de la clave de
																							// partición
					String partitionKeyValue = tfvalorclaveparticioncrud.getText().trim(); // Valor de la clave de
																							// partición
					String sortKeyName = tfValorUnicoParticioncrud.getText().trim(); // Nombre de la clave de ordenación
					String sortKeyValue = tfvalorclaveordenacioncrud.getText().trim(); // Valor de la clave de
																						// ordenación

					// Validar campos obligatorios
					if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"La clave de partición y su valor son obligatorios para leer un ítem.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Llamar al método para leer el ítem
					dynamoApp.operacion.leerItem(selectedTable, partitionKeyName, partitionKeyValue, sortKeyName,
							sortKeyValue, userBajoNivel);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLeerItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnLeerItem.setBounds(804, 280, 203, 39);
		panelCRUD.add(btnLeerItem);

		btnActualizarTabla = new JButton("Actualizar Tabla");
		btnActualizarTabla.setBackground(Color.decode("#00FF9C"));
		btnActualizarTabla.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/actualizar-base-de-datos.png")));
		btnActualizarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTable = listaTablas.getSelectedValue();

				if (selectedTable != null) {

					if (userBajoNivel == null) {
						JOptionPane.showMessageDialog(null, "El cliente DynamoDB no está inicializado.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					dynamoApp.gestorTablas.cargarDatosTabla(selectedTable, tablaDatos);

//		            // Notificar que los datos han sido actualizados (aunque esto ya lo maneja el método cargarDatosTabla)
//		            DefaultTableModel dtm = (DefaultTableModel) tablaDatos.getModel();
//		            dtm.fireTableDataChanged();
//		            dtm.fireTableStructureChanged();
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
		panelConsultas.setBackground(Color.decode("#BBE9FF"));
		tabbedPane.addTab("Consultas", new ImageIcon(GUI.class.getResource("/multimedia/busqueda-de-datos.png")),
				panelConsultas, null);
		panelConsultas.setLayout(null);

		cbnombretabla = new JComboBox<String>();
		cbnombretabla.setFont(new Font("Georgia", Font.BOLD, 16));
		cbnombretabla.setBounds(346, 149, 178, 32);
		cargarTablasEnComboBox(cbnombretabla);
		panelConsultas.add(cbnombretabla);

		bbuscar = new JButton("Buscar");
		bbuscar.setBackground(Color.decode("#00FF9C"));
		bbuscar.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/lupa.png")));
		bbuscar.setFont(new Font("Georgia", Font.BOLD, 18));
		bbuscar.setBounds(346, 386, 178, 44);
		panelConsultas.add(bbuscar);

		lnombretablaconsulta = new JLabel("Nombre de tabla");
		lnombretablaconsulta.setFont(new Font("Georgia", Font.BOLD, 18));
		lnombretablaconsulta.setBounds(68, 158, 192, 23);
		panelConsultas.add(lnombretablaconsulta);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBackground(new Color(255, 255, 255));
		scrollPane_2.setForeground(new Color(0, 128, 64));
		scrollPane_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane_2.setBounds(677, 146, 459, 183);
		panelConsultas.add(scrollPane_2);

		textArea = new JTextArea();
		textArea.setForeground(new Color(0, 128, 0));
		textArea.setFont(new Font("Tahoma", Font.BOLD, 13));
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setEditable(false);
		textArea.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"<html><b>Resultados de b\u00FAsqueda en formato JSON</html>", TitledBorder.CENTER, TitledBorder.TOP,
				null, new Color(0, 0, 160)));
		scrollPane_2.setViewportView(textArea);

		lvalorclaveparticionconsulta = new JLabel("Valor de clave de partición");
		lvalorclaveparticionconsulta.setFont(new Font("Georgia", Font.BOLD, 18));
		lvalorclaveparticionconsulta.setBounds(68, 281, 269, 32);
		panelConsultas.add(lvalorclaveparticionconsulta);

		tvalorclav = new JTextField();
		tvalorclav.setFont(new Font("Georgia", Font.BOLD, 16));
		tvalorclav.setColumns(10);
		tvalorclav.setBounds(346, 277, 178, 32);
		panelConsultas.add(tvalorclav);

		lnombreclaveconsulta = new JLabel(" Nombre clave de partición");
		lnombreclaveconsulta.setFont(new Font("Georgia", Font.BOLD, 18));
		lnombreclaveconsulta.setBounds(69, 223, 254, 25);
		panelConsultas.add(lnombreclaveconsulta);

		tnombreclavep = new JTextField();
		tnombreclavep.setFont(new Font("Georgia", Font.BOLD, 16));
		tnombreclavep.setColumns(10);
		tnombreclavep.setBounds(346, 216, 178, 32);
		panelConsultas.add(tnombreclavep);

		lconsultasid = new JLabel("Consultas por Clave de partición");
		lconsultasid.setHorizontalAlignment(SwingConstants.CENTER);
		lconsultasid.setFont(new Font("Georgia", Font.BOLD, 34));
		lconsultasid.setBounds(249, 10, 579, 88);
		panelConsultas.add(lconsultasid);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBackground(new Color(255, 255, 255));
		scrollPane_3.setBounds(677, 386, 459, 160);
		panelConsultas.add(scrollPane_3);

		textAreadatos = new JTextArea();
		textAreadatos.setForeground(new Color(0, 0, 0));
		textAreadatos.setFont(new Font("Tahoma", Font.BOLD, 13));
		textAreadatos.setBackground(new Color(255, 255, 255));
		textAreadatos.setEditable(false);
		textAreadatos.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"<html><b>Resultados de b\u00FAsqueda</html>", TitledBorder.CENTER, TitledBorder.TOP, null,
				new Color(0, 0, 160)));
		scrollPane_3.setViewportView(textAreadatos);
		bbuscar.addActionListener(e -> {
			consultarElementosConFiltro();
		});

		bbuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					// Obtener el nombre de la tabla seleccionada desde el JList
					String selectedTable = cbnombretabla.getSelectedItem().toString();
					if (selectedTable == null || selectedTable.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Seleccione una tabla de la lista.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Capturar las claves
					String partitionKeyName = tnombreclavep.getText().trim(); // Nombre de la clave de partición
					String partitionKeyValue = tvalorclav.getText().trim(); // Valor de la clave de partición

					// Validar campos obligatorios
					if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"La clave de partición y su valor son obligatorios para realizar una consulta.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Llamar al método para consultar los ítems
					// dynamoApp.consulta.consultarItems(selectedTable, partitionKeyName,
					// partitionKeyValue, textArea);
					dynamoApp.consulta.consultarItemsAmbosTextArea(selectedTable, partitionKeyName, partitionKeyValue,
							textArea, textAreadatos);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// FILTROS CON REGEX
	private String generateFilterExpression(String filterKeyName, String filterKeyType, String regexPattern) {
		if (filterKeyType.equals("String")) {
			// Si es tipo String, usamos expresiones regulares
			if (regexPattern.startsWith("s/")) {
				// El formato es "s/regex/", ejemplo: "s/a.*b/" para cadenas que contengan "a"
				// seguido de "b"
				// String regex = regexPattern.substring(2); // Obtener el patrón de la
				// expresión regular
				return "contains(" + filterKeyName + ", :filterValue)"; // Usamos "contains" para regex
			} else {
				// Si no es un regex, usamos contains por defecto
				return "contains(" + filterKeyName + ", :filterValue)";
			}
		} else if (filterKeyType.equals("Number")) {
			// Si es tipo Number, procesamos como comparación numérica
			if (regexPattern.contains(">")) {
				// Para comparaciones mayores que el valor
				String[] parts = regexPattern.split(">");
				if (parts.length == 2) {
					try {
						// int threshold = Integer.parseInt(parts[1].trim());
						return filterKeyName + " > :filterValue"; // Compara si el valor del campo es mayor
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Valor de comparación inválido para tipo Number", "Error",
								JOptionPane.ERROR_MESSAGE);
						return "";
					}
				}
			} else if (regexPattern.contains("<")) {
				// Para comparaciones menores que el valor
				String[] parts = regexPattern.split("<");
				if (parts.length == 2) {
					try {
						// int threshold = Integer.parseInt(parts[1].trim());
						return filterKeyName + " < :filterValue"; // Compara si el valor del campo es menor
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Valor de comparación inválido para tipo Number", "Error",
								JOptionPane.ERROR_MESSAGE);
						return "";
					}
				}
			} else if (regexPattern.contains("=")) {
				// Para comparaciones de igualdad
				String[] parts = regexPattern.split("=");
				if (parts.length == 2) {
					try {
						// int threshold = Integer.parseInt(parts[1].trim());
						return filterKeyName + " = :filterValue"; // Compara si el valor del campo es igual
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Valor de comparación inválido para tipo Number", "Error",
								JOptionPane.ERROR_MESSAGE);
						return "";
					}
				}
			}
		}

		return ""; // Si no se aplica ningún filtro, devolvemos vacío
	}

	private void consultarElementosConFiltro() {
		// Definir el nombre de la tabla
		String tableName = "test3"; // Asegurarse de que la tabla esté correctamente indicada
		String filterKeyName = "cadena"; // El campo en el que aplicar el filtro
		String regexPattern = "s/Dual.*"; // El patrón de filtrado (en este caso estamos buscando valores mayores a 6)
		String filterKeyType = "String"; // Tipo de filtro (en este caso estamos trabajando con números)

		// Variable que almacena el nombre de la clave de partición
		String nombreClaveParticion = "clave"; // Este es el nombre de la clave de partición (puede ser otro campo si es
												// diferente)

		AmazonDynamoDB client = dynamoApp.getDynamoDB();
		// DynamoDB dynamoDB = new DynamoDB(client);

		// Generar la expresión de filtro
		String filterExpression = generateFilterExpression(filterKeyName, filterKeyType, regexPattern);

		// Mapa para los valores de las expresiones
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":clave", new AttributeValue().withS("uno")); // Asigna un valor válido para
																					// :clave

		// Para números, aseguramos que el valor para :filterValue sea un número
		expressionAttributeValues.put(":filterValue", new AttributeValue().withN("6")); // Aquí pasamos el número 6 como
																						// valor para la comparación

		try {
			// Crear la solicitud de consulta
			QueryRequest queryRequest = new QueryRequest().withTableName(tableName)
					.withKeyConditionExpression(nombreClaveParticion + " = :clave") // Usamos la variable
																					// nombreClaveParticion aquí
					.withFilterExpression(filterExpression) // Aplicamos la expresión con regex aquí
					.withExpressionAttributeValues(expressionAttributeValues);

			// Ejecutar la consulta
			QueryResult result = client.query(queryRequest);

			// Procesar los resultados
			if (result.getItems().isEmpty()) {
				textArea.append("No se encontraron resultados.\n");
			} else {
				for (Map<String, AttributeValue> item : result.getItems()) {
					StringBuilder sb = new StringBuilder();
					for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
						if (entry.getValue().getS() != null) {
							sb.append(entry.getKey()).append(": ").append(entry.getValue().getS()).append("\n");
						} else if (entry.getValue().getN() != null) {
							sb.append(entry.getKey()).append(": ").append(entry.getValue().getN()).append("\n");
						}
					}
					textArea.append(sb.toString() + "\n");
				}
			}
		} catch (AmazonDynamoDBException e) {
//			JOptionPane.showMessageDialog(null, "Error de consulta: " + e.getMessage(), "Error",
//					JOptionPane.ERROR_MESSAGE);
//			System.out.println(e.getMessage());
		}
	}

	public void cargarTablasEnComboBox(JComboBox<String> comboBoxTablas) {
		comboBoxTablas.removeAllItems(); // Limpiar el JComboBox antes de agregar nuevos elementos
		dynamoApp.gestorTablas.listarTablas().forEach(comboBoxTablas::addItem); // Agregar los nombres de las tablas
	}

}
