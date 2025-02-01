package Righel_Backend;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;

import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Consultas {
    private AmazonDynamoDB userBajoNivel;
    private DynamoDB dynamoDB;

    // Constructor
    public Consultas(AmazonDynamoDB userBajoNivel) {
        if (userBajoNivel == null) {
            throw new IllegalArgumentException("El cliente AmazonDynamoDB no puede ser null");
        }
        this.setUserBajoNivel(userBajoNivel);
        this.dynamoDB = new DynamoDB(userBajoNivel);  // Se usa DynamoDB constructor correctamente
    }

    public void consultarItems(String selectedTable, String partitionKeyName, String partitionKeyValue, JTextArea resultArea) throws AmazonDynamoDBException {
        try {
            // Validar que la clave de partición esté presente
            if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
                throw new IllegalArgumentException("La clave de partición y su valor son obligatorios para consultar.");
            }

            // Crear instancia de DynamoDB y seleccionar la tabla
            Table table = dynamoDB.getTable(selectedTable);

            // Configuración de la consulta utilizando solo la clave de partición
            HashMap<String, Object> valueMap = new HashMap<>();
            valueMap.put(":v_partitionKey", partitionKeyValue);

            // Definir el filtro para la clave de partición
            QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(partitionKeyName + " = :v_partitionKey")
                .withValueMap(valueMap);

            // Ejecutar la consulta
            ItemCollection<QueryOutcome> items = table.query(querySpec);

            // Mostrar los resultados de la consulta en el JTextArea
            StringBuilder resultString = new StringBuilder();
            for (Item item : items) {
                resultString.append(item.toJSONPretty()).append("\n");
            }
            
            if (resultString.length() > 0) {
                // Colocar los resultados en el JTextArea
                resultArea.setText(resultString.toString());
            } else {
                resultArea.setText("No se encontraron resultados.");
            }

        } catch (Exception ex) {
            resultArea.setText("Error al consultar los ítems: " + ex.getMessage());
            ex.printStackTrace();
        }
        
         
    }

    public void consultarItemsAmbosTextArea(String selectedTable, String partitionKeyName, String partitionKeyValue, JTextArea jsonResultArea, JTextArea cleanResultArea) {
        try {
            // Validar que la clave de partición esté presente
            if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
                throw new IllegalArgumentException("La clave de partición y su valor son obligatorios para consultar.");
            }

            // Crear instancia de DynamoDB y seleccionar la tabla
            Table table = dynamoDB.getTable(selectedTable);

            // Configuración de la consulta utilizando solo la clave de partición
            HashMap<String, Object> valueMap = new HashMap<>();
            valueMap.put(":v_partitionKey", partitionKeyValue);

            // Definir el filtro para la clave de partición
            QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(partitionKeyName + " = :v_partitionKey")
                .withValueMap(valueMap);

            // Ejecutar la consulta
            ItemCollection<QueryOutcome> items = table.query(querySpec);

            // Inicializar lista para JSON y StringBuilder para texto limpio
            List<Map<String, Object>> resultsList = new ArrayList<>();
            StringBuilder cleanResultString = new StringBuilder();

            // Procesar los resultados
            for (Item item : items) {
                // Convertir Item a Map<String, Object> para Gson
                Map<String, Object> itemMap = item.asMap();
                resultsList.add(itemMap);

                // Construir formato limpio
                cleanResultString.append("Registro:\n");
                for (Map.Entry<String, Object> entry : itemMap.entrySet()) {
                    cleanResultString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                cleanResultString.append("---------------\n"); // Separador entre registros
            }

            // Convertir lista a JSON bonito
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(resultsList);

            // Mostrar resultados en JTextAreas
            jsonResultArea.setText(jsonOutput);

            if (resultsList.isEmpty()) {
                cleanResultArea.setText("No se encontraron resultados.");
            } else {
                cleanResultArea.setText(cleanResultString.toString());
            }

        } catch (AmazonDynamoDBException e) {
            jsonResultArea.setText("Error de DynamoDB: " + e.getMessage());
            cleanResultArea.setText("Error de DynamoDB: " + e.getMessage());
        } catch (Exception ex) {
            jsonResultArea.setText("Error al consultar los ítems: " + ex.getMessage());
            cleanResultArea.setText("Error al consultar los ítems: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

	public AmazonDynamoDB getUserBajoNivel() {
		return userBajoNivel;
	}

	public void setUserBajoNivel(AmazonDynamoDB userBajoNivel) {
		this.userBajoNivel = userBajoNivel;
	}

}
