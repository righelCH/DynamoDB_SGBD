package Righel_Backend;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DynamoApp {

	private AmazonDynamoDB dynamoDB;
	private String accessKeyId;
	private String secretKey;
	private String region;
	private Properties properties;

	public DynamoApp() {
		properties = new Properties();
	}

	// Método para abrir el archivo de propiedades y extraer las credenciales
	public void openPropertiesFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecciona el archivo de propiedades");
		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try (FileInputStream input = new FileInputStream(fileChooser.getSelectedFile())) {
				properties.load(input);
				accessKeyId = properties.getProperty("aws.accessKeyId");
				secretKey = properties.getProperty("aws.secretKey");
				region = properties.getProperty("aws.region");
				JOptionPane.showMessageDialog(null, "Archivo de propiedades cargado exitosamente.");
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al cargar el archivo de propiedades.");
			}
		}
	}

	// Método para conectar usando las credenciales extraídas del archivo de
	// propiedades
	public void connectToDynamoDB() {
		if (accessKeyId != null && secretKey != null && region != null) {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
			dynamoDB = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.fromName(region))
					.build();
			JOptionPane.showMessageDialog(null, "Conectado a DynamoDB con las credenciales del archivo properties.");
		} else {
			JOptionPane.showMessageDialog(null, "Faltan credenciales para conectar.");
		}
	}

	// Método para seleccionar el perfil y conectarse a DynamoDB
	public void selectProfileAndConnect() {
		// Usar ProfileCredentialsProvider para obtener las credenciales del perfil
		// seleccionado
		String profileName = JOptionPane.showInputDialog("Introduce el nombre del perfil de AWS:");
		if (profileName != null && !profileName.trim().isEmpty()) {
			ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider(profileName);
			try {
				accessKeyId = profileCredentialsProvider.getCredentials().getAWSAccessKeyId();
				secretKey = profileCredentialsProvider.getCredentials().getAWSSecretKey();
				region = "us-west-2"; // Ajusta la región según sea necesario

				connectToDynamoDB(); // Conectar con DynamoDB usando el perfil
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error al obtener las credenciales del perfil.");
				e.printStackTrace();
			}
		}
	}

	// Método para obtener el cliente DynamoDB
	public AmazonDynamoDB getDynamoDB() {
		return dynamoDB;
	}

	// eEJMPLO De como lo implementarias
	// DynamoDBApp app = new DynamoDBApp();
	// btnOpenProperties.addActionListener(e -> app.openPropertiesFile());

	// btnSelectProfile.addActionListener(e -> app.selectProfileAndConnect());

	/*
	 * JButton btnConnect = new JButton("Conectar"); btnConnect.addActionListener(e
	 * -> { if (app.accessKeyId != null && app.secretKey != null && app.region !=
	 * null) { app.connectToDynamoDB(); } else {
	 * JOptionPane.showMessageDialog(frame,
	 * "Por favor, cargue el archivo de propiedades o seleccione un perfil."); } });
	 */

}
