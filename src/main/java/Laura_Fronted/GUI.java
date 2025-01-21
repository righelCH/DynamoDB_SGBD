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
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;

public class GUI extends JFrame {

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
	private JTextField tfnombre;
	private JTextField tfclaveparticion;
	private JTextField tfordenacion;
	private JComboBox<String> combotipoparticion;
	private JComboBox<String> comboindices;
	private JComboBox<String> comboproyeccion;
	private JButton btnCrearTabla;
	private JLabel lblNewLabel;
	private JLabel lresulgestion;
	private JLabel lblndices;
	private JLabel lblClaveDeOrdenacion;
	private JLabel lblProyecion;
	private JLabel ltitulo;
	private JLabel lnombreatributo;
	private JTextField tfnombreatributo;
	private JTextField tfvalor;
	private JComboBox combotipo;
	private JButton btnModificarItem;
	private JTable tabla;
	private JButton btnBorrarItem;
	private JButton btnCrearItem;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// -
					// UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");

					//---- UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					//--- UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
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
		panelGestionTabla.setVisible(false);
		panelGestionTabla.setBackground(new Color(213, 234, 255));
		tabbedPane.addTab("Gestión Tabla", null, panelGestionTabla, null);
		panelGestionTabla.setLayout(null);

		btnMostrarTablas = new JButton("Abrir Tabla");
		btnMostrarTablas.setBorder(new LineBorder(new Color(242, 165, 70)));
		btnMostrarTablas.setFont(new Font("Georgia", Font.BOLD, 14));
		btnMostrarTablas.setBackground(new Color(215, 215, 255));
		btnMostrarTablas.setBounds(667, 199, 193, 43);
		panelGestionTabla.add(btnMostrarTablas);

		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setBounds(667, 93, 186, 83);
		panelGestionTabla.add(scrollPane);

		listaTablas = new JList<String>();
		scrollPane.setViewportView(listaTablas);
		listaTablas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 160)), "<html><b>Tablas</html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		btnCrearTabla = new JButton(" Crear Tabla");
		btnCrearTabla.setIcon(new ImageIcon(GUI.class.getResource("/multimedia/escribir.png")));
		btnCrearTabla.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearTabla.setBorder(new LineBorder(new Color(242, 165, 70)));
		btnCrearTabla.setBackground(new Color(215, 215, 255));
		btnCrearTabla.setBounds(338, 409, 223, 43);
		panelGestionTabla.add(btnCrearTabla);

		lblNewLabel = new JLabel("Administración de Tablas: Creación y Visualización");
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 26));
		lblNewLabel.setBounds(71, 10, 729, 69);
		panelGestionTabla.add(lblNewLabel);

		lnombre = new JLabel("Nombre");
		lnombre.setFont(new Font("Georgia", Font.BOLD, 14));
		lnombre.setBounds(99, 80, 83, 43);
		panelGestionTabla.add(lnombre);

		lclaveparticion = new JLabel("Nombre clave de partición");
		lclaveparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		lclaveparticion.setBounds(97, 133, 208, 43);
		panelGestionTabla.add(lclaveparticion);

		ltipoparticion = new JLabel("Tipo de partición");
		ltipoparticion.setFont(new Font("Georgia", Font.BOLD, 14));
		ltipoparticion.setBounds(97, 186, 143, 43);
		panelGestionTabla.add(ltipoparticion);

		tfnombre = new JTextField();
		tfnombre.setBounds(338, 88, 217, 30);
		panelGestionTabla.add(tfnombre);
		tfnombre.setColumns(10);

		tfclaveparticion = new JTextField();
		tfclaveparticion.setColumns(10);
		tfclaveparticion.setBounds(338, 133, 217, 30);
		panelGestionTabla.add(tfclaveparticion);

		combotipoparticion = new JComboBox<String>();
		combotipoparticion.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Seleccione", "String (S)", "Number (N)", "Binary (B)" }));
		combotipoparticion.setFont(new Font("Georgia", Font.BOLD, 13));
		combotipoparticion.setBounds(338, 186, 217, 30);
		panelGestionTabla.add(combotipoparticion);

		lresulgestion = new JLabel("");
		lresulgestion.setHorizontalAlignment(SwingConstants.CENTER);
		lresulgestion.setForeground(new Color(255, 0, 0));
		lresulgestion.setFont(new Font("Georgia", Font.BOLD, 14));
		lresulgestion.setBounds(37, 475, 793, 51);
		panelGestionTabla.add(lresulgestion);

		lblndices = new JLabel("Índices");
		lblndices.setFont(new Font("Georgia", Font.BOLD, 14));
		lblndices.setBounds(99, 235, 70, 43);
		panelGestionTabla.add(lblndices);

		comboindices = new JComboBox<String>();
		comboindices.setFont(new Font("Georgia", Font.BOLD, 13));
		comboindices.setModel(new DefaultComboBoxModel<String>(new String[] { "Seleccione", "Sin índice", "índice Primario",
				"Índice Secundario Global (GSI)", "Índice Secundario Local(LSI)" }));
		comboindices.setBounds(338, 247, 217, 30);
		panelGestionTabla.add(comboindices);

		tfordenacion = new JTextField();
		tfordenacion.setVisible(false);
		tfordenacion.setBounds(499, 316, 139, 27);
		panelGestionTabla.add(tfordenacion);
		tfordenacion.setColumns(10);

		comboproyeccion = new JComboBox<String>();
		comboproyeccion.setVisible(false);
		comboproyeccion
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Seleccione", "ALL", "KEYS_ONLY", "INCLUDE" }));
		comboproyeccion.setFont(new Font("Georgia", Font.BOLD, 13));
		comboproyeccion.setBounds(499, 372, 139, 28);
		panelGestionTabla.add(comboproyeccion);

		lblClaveDeOrdenacion = new JLabel("Clave de ordenación");
		lblClaveDeOrdenacion.setVisible(false);
		lblClaveDeOrdenacion.setFont(new Font("Georgia", Font.BOLD, 12));
		lblClaveDeOrdenacion.setBounds(338, 305, 151, 43);
		panelGestionTabla.add(lblClaveDeOrdenacion);

		lblProyecion = new JLabel("Proyección");
		lblProyecion.setVisible(false);
		lblProyecion.setFont(new Font("Georgia", Font.BOLD, 12));
		lblProyecion.setBounds(338, 357, 151, 43);
		panelGestionTabla.add(lblProyecion);

		panelCRUD = new JPanel();
		panelCRUD.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("CRUD", null, panelCRUD, null);
		panelCRUD.setLayout(null);
		
		ltitulo = new JLabel("Gestión de Datos con Operaciones CRUD");
		ltitulo.setHorizontalAlignment(SwingConstants.CENTER);
		ltitulo.setFont(new Font("Georgia", Font.BOLD, 26));
		ltitulo.setBounds(60, 10, 746, 76);
		panelCRUD.add(ltitulo);
		
		lnombreatributo = new JLabel("Nombre del atributo");
		lnombreatributo.setFont(new Font("Georgia", Font.BOLD, 13));
		lnombreatributo.setBounds(44, 107, 162, 25);
		panelCRUD.add(lnombreatributo);
		
		tfnombreatributo = new JTextField();
		tfnombreatributo.setFont(new Font("Georgia", Font.BOLD, 12));
		tfnombreatributo.setBounds(228, 107, 173, 25);
		panelCRUD.add(tfnombreatributo);
		tfnombreatributo.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Georgia", Font.BOLD, 13));
		lblTipo.setBounds(44, 159, 162, 25);
		panelCRUD.add(lblTipo);
		
		combotipo = new JComboBox();
		combotipo.setModel(new DefaultComboBoxModel(new String[] {"Seleccione", "String (S)", "Number (N)", "Binary (B)", "Boolean (BOOL)", "Null (NULL)", "List (L)", "Map (M)", "String Set (SS)", "Number Set (NS)", "Binary Set (BS)"}));
		combotipo.setFont(new Font("Georgia", Font.BOLD, 12));
		combotipo.setBounds(228, 162, 173, 25);
		panelCRUD.add(combotipo);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Georgia", Font.BOLD, 13));
		lblValor.setBounds(44, 204, 162, 25);
		panelCRUD.add(lblValor);
		
		tfvalor = new JTextField();
		tfvalor.setFont(new Font("Georgia", Font.BOLD, 12));
		tfvalor.setColumns(10);
		tfvalor.setBounds(228, 208, 173, 25);
		panelCRUD.add(tfvalor);
		
		btnCrearItem = new JButton("Crear Item");
		btnCrearItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnCrearItem.setBounds(605, 96, 176, 39);
		panelCRUD.add(btnCrearItem);
		
		btnBorrarItem = new JButton("Borrar Item");
		btnBorrarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnBorrarItem.setBounds(605, 145, 176, 39);
		panelCRUD.add(btnBorrarItem);
		
		btnModificarItem = new JButton("Modificar Item");
		btnModificarItem.setFont(new Font("Georgia", Font.BOLD, 14));
		btnModificarItem.setBounds(605, 190, 176, 39);
		panelCRUD.add(btnModificarItem);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(60, 269, 746, 192);
		panelCRUD.add(scrollPane_1);
		
		tabla = new JTable();
		scrollPane_1.setViewportView(tabla);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Georgia", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(60, 471, 746, 55);
		panelCRUD.add(lblNewLabel_1);

		panelConsultas = new JPanel();
		panelConsultas.setBackground(new Color(217, 236, 255));
		tabbedPane.addTab("Consultas", null, panelConsultas, null);
		panelConsultas.setLayout(null);
	}
}
