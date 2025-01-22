package Righel_Backend;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Gestion_de_Tablas {

	private AmazonDynamoDB dynamoDB;
	private DynamoDB dynamoDBClient;
	public static GlobalSecondaryIndex getGsi() {
		return gsi;
	}

	public static void setGsi(GlobalSecondaryIndex gsi) {
		Gestion_de_Tablas.gsi = gsi;
	}


	String tablechoose = null;
	private static GlobalSecondaryIndex gsi;
	
	public Gestion_de_Tablas(AmazonDynamoDB dynamoDB) {
	    if (dynamoDB == null) {
	        throw new IllegalArgumentException("El cliente AmazonDynamoDB no puede ser null");
	    }
	    this.dynamoDB = dynamoDB;
	    this.dynamoDBClient = new DynamoDB(dynamoDB);
	}
//	// Método para listar las tablas
//	public StringBuilder listarTablas() {
//		StringBuilder tableList = null;
//		try {
//			ListTablesRequest listTablesRequest = new ListTablesRequest();
//			ListTablesResult listTablesResponse = dynamoDB.listTables(listTablesRequest);
//			List<String> tables = listTablesResponse.getTableNames();
//
//			tableList = new StringBuilder("Tablas disponibles:\n");
//			for (String tableName : tables) {
//				tableList.append(tableName).append("\n");
//			}
//			JOptionPane.showMessageDialog(null, tableList.toString());
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "Error al listar las tablas: " + e.getMessage());
//		}
//
//		return tableList;
//	}

	// Método para listar tablas y devolverlas como una lista
	public List<String> listarTablas() {
	    try {
	        // Solicitar las tablas
	        ListTablesRequest listTablesRequest = new ListTablesRequest();
	        ListTablesResult listTablesResponse = dynamoDB.listTables(listTablesRequest);
	        List<String> tables = listTablesResponse.getTableNames();

//	        // Mostrar un mensaje al usuario
//	        if (tables.isEmpty()) {
//	            JOptionPane.showMessageDialog(null, "No hay tablas disponibles.");
//	        } else {
//	            JOptionPane.showMessageDialog(null, "Tablas actualizadas.");
//	        }

	        return tables; // Devuelve la lista directamente
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al listar las tablas: " + e.getMessage());
	        return new ArrayList<>(); // Devuelve una lista vacía en caso de error
	    }
	}
	public String seleccionarTabla(JList<String> listaTabla) {

		listaTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaTabla.addListSelectionListener(e -> {
			tablechoose = (String) listaTabla.getSelectedValue();
		});
		return tablechoose;
	}

//	// Método para crear una tabla con parámetros proporcionados
//	public void crearTabla(String tableName, String partitionKeyName, String partitionKeyType, String sortKeyName,
//			String sortKeyType, boolean provisioned, Long readCapacityUnits, Long writeCapacityUnits) {
//		try {
//			// Definir las claves de la tabla
//			KeySchemaElement partitionKey = new KeySchemaElement(partitionKeyName, KeyType.HASH);
//			KeySchemaElement sortKey = null;
//			if (sortKeyName != null && !sortKeyName.isEmpty()) {
//				sortKey = new KeySchemaElement(sortKeyName, KeyType.RANGE);
//			}
//
//			// Definir las características de la capacidad (si la tabla es provisionada)
//			ProvisionedThroughput provisionedThroughput = null;
//			if (provisioned) {
//				provisionedThroughput = new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
//						.withWriteCapacityUnits(writeCapacityUnits);
//			}
//
//			// Crear el objeto CreateTableRequest
//			CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
//					.withKeySchema(partitionKey, sortKey)
//					.withAttributeDefinitions(new AttributeDefinition(partitionKeyName, ScalarAttributeType.S),
//							new AttributeDefinition(sortKeyName, ScalarAttributeType.S))
//					.withProvisionedThroughput(provisionedThroughput);
//
//			// Crear la tabla
//			Table table = dynamoDBClient.createTable(createTableRequest);
//			table.waitForActive();
//			JOptionPane.showMessageDialog(null, "Tabla " + tableName + " creada con éxito.");
//			
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "Error al crear la tabla: " + e.getMessage());
//		}
//	}
	
	// Método para crear una tabla con parámetros proporcionados
	public void crearTabla(String tableName, String partitionKeyName, String partitionKeyType, 
	                       String sortKeyName, String sortKeyType, boolean provisioned, 
	                       Long readCapacityUnits, Long writeCapacityUnits) {
	    try {
	        // Crear la clave de partición
	        KeySchemaElement partitionKey = new KeySchemaElement(partitionKeyName, KeyType.HASH);

	        // Crear la clave de ordenación (si aplica)
	        KeySchemaElement sortKey = null;
	        if (sortKeyName != null && !sortKeyName.isEmpty()) {
	            sortKey = new KeySchemaElement(sortKeyName, KeyType.RANGE);
	        }

	        // Crear la lista de definiciones de atributos
	        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
	        attributeDefinitions.add(new AttributeDefinition(partitionKeyName, ScalarAttributeType.valueOf(partitionKeyType)));

	        if (sortKey != null) {
	            attributeDefinitions.add(new AttributeDefinition(sortKeyName, ScalarAttributeType.valueOf(sortKeyType)));
	        }

	        // Configurar el modo de facturación y la capacidad provisionada
	        BillingMode billingMode = provisioned ? BillingMode.PROVISIONED : BillingMode.PAY_PER_REQUEST;
	        ProvisionedThroughput provisionedThroughput = null;

	        if (provisioned) {
	            provisionedThroughput = new ProvisionedThroughput()
	                    .withReadCapacityUnits(readCapacityUnits)
	                    .withWriteCapacityUnits(writeCapacityUnits);
	        }

	        // Crear el objeto CreateTableRequest
	        CreateTableRequest createTableRequest = new CreateTableRequest()
	                .withTableName(tableName)
	                .withKeySchema(partitionKey, sortKey != null ? sortKey : null)
	                .withAttributeDefinitions(attributeDefinitions)
	                .withBillingMode(billingMode);

	        // Agregar capacidad provisionada si el modo es PROVISIONED
	        if (provisionedThroughput != null) {
	            createTableRequest.withProvisionedThroughput(provisionedThroughput);
	        }

	        // Crear la tabla en DynamoDB
	        Table table = dynamoDBClient.createTable(createTableRequest);
	        table.waitForActive(); // Esperar a que la tabla esté activa
	        JOptionPane.showMessageDialog(null, "Tabla " + tableName + " creada con éxito.");

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al crear la tabla: " + e.getMessage());
	    }
	}


	// Método para agregar un índice global secundario (GSI) a una tabla
	public void definirIndice(String tableName, String indexName, String partitionKeyName, String sortKeyName,
			Long readCapacityUnits, Long writeCapacityUnits) {
		try {
			gsi = new GlobalSecondaryIndex().withIndexName(indexName)
					.withKeySchema(new KeySchemaElement(partitionKeyName, KeyType.HASH),
							new KeySchemaElement(sortKeyName, KeyType.RANGE))
					.withProjection(new Projection().withProjectionType(ProjectionType.ALL))
					.withProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits));

			// Crear la solicitud de actualización usando CreateGlobalSecondaryIndexAction
			CreateGlobalSecondaryIndexAction createIndexAction = new CreateGlobalSecondaryIndexAction()
					.withIndexName(indexName)
					.withKeySchema(new KeySchemaElement(partitionKeyName, KeyType.HASH),
							new KeySchemaElement(sortKeyName, KeyType.RANGE))
					.withProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits))
					.withProjection(new Projection().withProjectionType(ProjectionType.ALL));

			// Crear la solicitud de actualización para la tabla
			UpdateTableRequest updateTableRequest = new UpdateTableRequest().withTableName(tableName)
					.withGlobalSecondaryIndexUpdates(new GlobalSecondaryIndexUpdate().withCreate(createIndexAction));

			// Realizar la actualización de la tabla
			dynamoDB.updateTable(updateTableRequest);
			JOptionPane.showMessageDialog(null, "Índice " + indexName + " añadido con éxito.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al definir el índice: " + e.getMessage());
		}
	}
}
