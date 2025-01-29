package Righel_Backend;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import Laura_Fronted.GUI;
import Laura_Fronted.Principal;

import javax.swing.*;

public class DynamoApp {

	private AmazonDynamoDB userAltoNivel;
	private String accessKeyId;
	private String secretKey;
	private String region;
	private Principal frameGUI;
private OperacionesCRUD operacion;
private Gestion_de_Tablas gestorTabla;

	public DynamoApp(String accessKeyId,String secretKey,String region,Principal frameGUI) {
		this.accessKeyId=accessKeyId;
		this.secretKey=secretKey;
		this.region=region;
		this.frameGUI=frameGUI;
		connectToDynamoDB(accessKeyId, secretKey, region, frameGUI);
	gestorTabla=new Gestion_de_Tablas(userAltoNivel);
	
	DynamoApp aa=new DynamoApp(accessKeyId, secretKey, region, frameGUI);
	aa.gestorTabla.crearTabla(region, region, accessKeyId, secretKey, region, false, null, null);
		//operacion=new OperacionesCRUD(dynamoDB, "")
	}

	private static DynamoApp instance;

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	// Método para obtener la instancia única
	public static DynamoApp getInstance() {
		if (instance == null) {
	//		instance = new DynamoApp();
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void connectToDynamoDB(String accessKeyId, String secretKey, String region, Principal principalFrame) {
		if (accessKeyId != null && secretKey != null && region != null) {
			try {
				this.accessKeyId = accessKeyId;
				this.secretKey = secretKey;
				this.region = region;

				// Configuración de credenciales
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
				this.dynamoDB = AmazonDynamoDBClientBuilder.standard()
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
						.withRegion(Regions.fromName(region)).build();

				// Operación para verificar conexión
				ListTablesResult result = dynamoDB.listTables();
				JOptionPane.showMessageDialog(null, "Conexión exitosa.");
				System.out.println("Conexión exitosa. Tablas disponibles: " + result.getTableNames());

				// Abre la nueva ventana (GUI)s
				GUI gui = new GUI(this); // Pasa esta instancia
				gui.setVisible(true);
				principalFrame.dispose();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error al conectar con DynamoDB: " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Faltan credenciales para conectar.");
		}
	}

	// Método para obtener el cliente DynamoDB
	public AmazonDynamoDB getDynamoDB() {
		return dynamoDB;
	}

}
