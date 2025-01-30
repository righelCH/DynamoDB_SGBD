package Righel_Backend;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.util.HashMap;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class Consultas {
    private AmazonDynamoDB userBajoNivel;
    private DynamoDB dynamoDB;

    // Constructor
    public Consultas(AmazonDynamoDB userBajoNivel) {
        if (userBajoNivel == null) {
            throw new IllegalArgumentException("El cliente AmazonDynamoDB no puede ser null");
        }
        this.userBajoNivel = userBajoNivel;
        this.dynamoDB = new DynamoDB(userBajoNivel);  // Se usa DynamoDB constructor correctamente
    }

    public void consultarItems(String selectedTable, String partitionKeyName, String partitionKeyValue, JTextArea resultArea) {
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

}
