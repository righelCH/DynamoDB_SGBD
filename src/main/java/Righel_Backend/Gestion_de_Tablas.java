package Righel_Backend;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import javax.swing.*;

public class GestionDeTablas {

    private AmazonDynamoDB dynamoDB;
    private DynamoDB dynamoDBClient;

    public GestionDeTablas(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
        this.dynamoDBClient = new DynamoDB(dynamoDB);
    }

    // Método para listar las tablas
    public void listarTablas() {
        try {
            ListTablesRequest listTablesRequest = new ListTablesRequest();
            ListTablesResponse listTablesResponse = dynamoDB.listTables(listTablesRequest);
            List<String> tables = listTablesResponse.getTableNames();

            StringBuilder tableList = new StringBuilder("Tablas disponibles:\n");
            for (String tableName : tables) {
                tableList.append(tableName).append("\n");
            }
            JOptionPane.showMessageDialog(null, tableList.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar las tablas: " + e.getMessage());
        }
    }

    // Método para crear una tabla con parámetros proporcionados
    public void crearTabla(String tableName, String partitionKeyName, String partitionKeyType,
                           String sortKeyName, String sortKeyType, boolean provisioned,
                           Long readCapacityUnits, Long writeCapacityUnits) {
        try {
            // Definir las claves de la tabla
            KeySchemaElement partitionKey = new KeySchemaElement(partitionKeyName, KeyType.HASH);
            KeySchemaElement sortKey = null;
            if (sortKeyName != null && !sortKeyName.isEmpty()) {
                sortKey = new KeySchemaElement(sortKeyName, KeyType.RANGE);
            }

            // Definir las características de la capacidad (si la tabla es provisionada)
            ProvisionedThroughput provisionedThroughput = null;
            if (provisioned) {
                provisionedThroughput = new ProvisionedThroughput()
                        .withReadCapacityUnits(readCapacityUnits)
                        .withWriteCapacityUnits(writeCapacityUnits);
            }

            // Crear el objeto CreateTableRequest
            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName(tableName)
                    .withKeySchema(partitionKey, sortKey)
                    .withAttributeDefinitions(
                            new AttributeDefinition(partitionKeyName, ScalarAttributeType.S),
                            new AttributeDefinition(sortKeyName, ScalarAttributeType.S))
                    .withProvisionedThroughput(provisionedThroughput);

            // Crear la tabla
            Table table = dynamoDBClient.createTable(createTableRequest);
            table.waitForActive();
            JOptionPane.showMessageDialog(null, "Tabla " + tableName + " creada con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear la tabla: " + e.getMessage());
        }
    }

    // Método para agregar un índice global secundario (GSI) a una tabla
    public void definirIndice(String tableName, String indexName, String partitionKeyName, String sortKeyName,
                              Long readCapacityUnits, Long writeCapacityUnits) {
        try {
            // Definir el índice global secundario
            GlobalSecondaryIndex gsi = new GlobalSecondaryIndex()
                    .withIndexName(indexName)
                    .withKeySchema(
                            new KeySchemaElement(partitionKeyName, KeyType.HASH),
                            new KeySchemaElement(sortKeyName, KeyType.RANGE))
                    .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
                    .withProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits));

            // Crear la solicitud de actualización usando CreateGlobalSecondaryIndexAction
            CreateGlobalSecondaryIndexAction createIndexAction = new CreateGlobalSecondaryIndexAction()
                    .withIndexName(indexName)
                    .withKeySchema(
                            new KeySchemaElement(partitionKeyName, KeyType.HASH),
                            new KeySchemaElement(sortKeyName, KeyType.RANGE))
                    .withProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits))
                    .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

            // Crear la solicitud de actualización para la tabla
            UpdateTableRequest updateTableRequest = new UpdateTableRequest()
                    .withTableName(tableName)
                    .withGlobalSecondaryIndexUpdates(
                            new GlobalSecondaryIndexUpdate().withCreate(createIndexAction));

            // Realizar la actualización de la tabla
            dynamoDB.updateTable(updateTableRequest);
            JOptionPane.showMessageDialog(null, "Índice " + indexName + " añadido con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al definir el índice: " + e.getMessage());
        }
    }
}
