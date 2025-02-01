package Righel_Backend;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Gestion_de_Tablas {

	private AmazonDynamoDB userBajoNivel;
	private DynamoDB userMedioNivel;
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
	    this.userBajoNivel = dynamoDB;
	    this.userMedioNivel = new DynamoDB(dynamoDB);
	}
	
	// Método para cargar los datos de la tabla en un JTable
    public  void cargarDatosTabla(String tableName, JTable tablaDatos) {
        try {
            // Validar que el cliente DynamoDB esté inicializado
            if (userBajoNivel == null) {
                throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
            }

            // Crear una instancia de DynamoDB
            DynamoDB dynamoDB = new DynamoDB(userBajoNivel);
            Table table = dynamoDB.getTable(tableName);

            // Escanear la tabla
            ItemCollection<ScanOutcome> items = table.scan();

            // Verificar si se obtuvieron datos
            if (!items.iterator().hasNext()) {
                System.out.println("No se encontraron datos en la tabla: " + tableName);
                DefaultTableModel dtm = (DefaultTableModel) tablaDatos.getModel();
                dtm.setRowCount(0); // Limpiar la tabla
                return;
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
	public List<String> mostrarTablas(){
		return  this.listarTablas();
	}
	// Método para listar tablas y devolverlas como una lista
	public List<String> listarTablas() {
	    try {
	        // Solicitar las tablas
	        ListTablesRequest listTablesRequest = new ListTablesRequest();
	        ListTablesResult listTablesResponse = userBajoNivel.listTables(listTablesRequest);
	        List<String> tables = listTablesResponse.getTableNames();

	        return tables; 
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
	        Table table = userMedioNivel.createTable(createTableRequest);
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
			userBajoNivel.updateTable(updateTableRequest);
			JOptionPane.showMessageDialog(null, "Índice " + indexName + " añadido con éxito.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al definir el índice: " + e.getMessage());
		}
	}
}
