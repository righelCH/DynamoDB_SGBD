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
	

	public DynamoApp() {
		
	}

	
	// Método para conectar usando las credenciales extraídas del archivo de
	
	public void connectToDynamoDB(String accessKeyId,String secretkey,String region) {
		if (accessKeyId != null && secretkey != null && region != null) {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretkey);
			dynamoDB = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.fromName(region))
					.build();
			JOptionPane.showMessageDialog(null, "Conectado a DynamoDB con las credenciales del archivo properties.");
		} else {
			JOptionPane.showMessageDialog(null, "Faltan credenciales para conectar.");
		}
	}

	
	

	// Método para obtener el cliente DynamoDB
	public AmazonDynamoDB getDynamoDB() {
		return dynamoDB;
	}

	
}
