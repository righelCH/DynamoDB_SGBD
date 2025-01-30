package Righel_Backend;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;


import Laura_Fronted.LoginTab;

import javax.swing.*;

public class DynamoApp {

	private AmazonDynamoDB userBajoNivel;
	private String accessKeyId;
	private String secretKey;
	private String region;
	private LoginTab frameGUI;

	// Clases auxiliares
	public final Gestion_de_Tablas gestorTablas;
	public final OperacionesCRUD operacion;
	public final Consultas consulta;

	// Singleton
	private static DynamoApp instance;

	public DynamoApp(String accessKeyId, String secretKey, String region, LoginTab frameGUI) {
		this.accessKeyId = accessKeyId;
		this.secretKey = secretKey;
		this.region = region;
		this.setFrameGUI(frameGUI);

		// Conectar a DynamoDB
		connectToDynamoDB();

		// Inicializar las clases auxiliares con la conexión establecida
		this.gestorTablas = new Gestion_de_Tablas(userBajoNivel);
		this.operacion = new OperacionesCRUD(userBajoNivel);
		this.consulta = new Consultas(userBajoNivel);
	}

	// Singleton: Inicializar solo una vez
	public static void initInstance(String accessKeyId, String secretKey, String region, LoginTab frameGUI) {
		if (instance == null) {
			instance = new DynamoApp(accessKeyId, secretKey, region, frameGUI);
		}
	}

	// Obtener la instancia única
	public static DynamoApp getInstance() {
		return instance;
	}

	// Conectar a DynamoDB
	private void connectToDynamoDB() {
		if (accessKeyId != null && secretKey != null && region != null) {
			try {
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
				this.userBajoNivel = AmazonDynamoDBClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
						.withRegion(region).build();

			   //Comprobar que tenemos conexion listando las tablas que existan
			    ListTablesResult result = userBajoNivel.listTables();
				System.out.println("Conexión exitosa. Tablas disponibles: " + result.getTableNames());
				
				JOptionPane.showMessageDialog(null, "Conexión exitosa.");
			

				// Abrir GUI y cerrar la ventana de inicio
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error al conectar con DynamoDB: " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Faltan credenciales para conectar.");
		}
	}

	// Método para obtener la conexión (opcional)
	public AmazonDynamoDB getDynamoDB() {
		return userBajoNivel;
	}

	public LoginTab getFrameGUI() {
		return frameGUI;
	}

	public void setFrameGUI(LoginTab frameGUI) {
		this.frameGUI = frameGUI;
	}
}
