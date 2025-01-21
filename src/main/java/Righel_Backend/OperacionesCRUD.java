package Righel_Backend;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class OperacionesCRUD {

	private final DynamoDB dynamoDB;
	private String tableName;

	public OperacionesCRUD(DynamoDB dynamoDB, String tableName) {
		this.dynamoDB = dynamoDB;
		this.tableName = tableName;
	}

	// Crear un ítem
	public void createItem(String key, String keyValue, String attribute, String attributeValue) {
		Table table = dynamoDB.getTable(tableName);

		try {
			Item item = new Item().withPrimaryKey(key, keyValue).withString(attribute, attributeValue);
			table.putItem(item);
			System.out.println("Ítem creado exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al crear el ítem: " + e.getMessage());
		}
	}

	// Leer un ítem
	public void readItem(String key, String keyValue) {
		Table table = dynamoDB.getTable(tableName);

		try {
			Item item = table.getItem(key, keyValue);
			if (item != null) {
				System.out.println("Ítem encontrado: " + item.toJSONPretty());
			} else {
				System.out.println("Ítem no encontrado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al leer el ítem: " + e.getMessage());
		}
	}

	public void updateItem(String primaryKey, String primaryKeyValue, String attributeName, String newValue) {
		try {
			Table table = dynamoDB.getTable(tableName);

// Crear la especificación de actualización
			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(primaryKey, primaryKeyValue)
					.withUpdateExpression("set #attr = :val").withNameMap(new NameMap().with("#attr", attributeName))
					.withValueMap(new ValueMap().withString(":val", newValue))
					.withReturnValues(ReturnValue.UPDATED_NEW);

// Ejecutar la actualización
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			System.out.println("Ítem actualizado con éxito: " + outcome.getItem());
		} catch (Exception e) {
			System.err.println("Error al actualizar el ítem: " + e.getMessage());
		}
	}

	// Eliminar un ítem
	public void deleteItem(String key, String keyValue) {
		Table table = dynamoDB.getTable(tableName);

		try {
			table.deleteItem(key, keyValue);
			System.out.println("Ítem eliminado exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al eliminar el ítem: " + e.getMessage());
		}
	}
}
