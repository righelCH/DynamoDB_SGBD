package Righel_Backend;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

public class OperacionesCRUD {

	private final DynamoDB dynamoDB;
	private String tableName;

	public OperacionesCRUD(DynamoDB dynamoDB, String tableName) {
		this.dynamoDB = dynamoDB;
		this.tableName = tableName;
	}

//	// Crear un ítem
//	public void createItem(String key, String keyValue, String attribute, String attributeValue) {
//		Table table = dynamoDB.getTable(tableName);
//
//		try {
//			Item item = new Item().withPrimaryKey(key, keyValue).withString(attribute, attributeValue);
//			table.putItem(item);
//			System.out.println("Ítem creado exitosamente.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Error al crear el ítem: " + e.getMessage());
//		}
//	}

//	public void createItem(String key, String keyValue, String attribute, String attributeValue, String attributeType) {
//	    Table table = dynamoDB.getTable(tableName); // Obtener la tabla seleccionada
//
//	    try {
//	        // Crear un ítem y establecer la clave primaria
//	        Item item = new Item().withPrimaryKey(key, keyValue);
//
//	        // Añadir el atributo dinámicamente según su tipo
//	        switch (attributeType) {
//	            case "String (S)":
//	                item.withString(attribute, attributeValue);
//	                break;
//	            case "Number (N)":
//	                item.withNumber(attribute, Double.parseDouble(attributeValue));
//	                break;
//	            case "Boolean (BOOL)":
//	                item.withBoolean(attribute, Boolean.parseBoolean(attributeValue));
//	                break;
//	            case "Binary (B)":
//	                item.withBinary(attribute, attributeValue.getBytes());
//	                break;
//	            case "Null (NULL)":
//	                item.withNull(attribute);
//	                break;
//	            default:
//	                throw new IllegalArgumentException("Tipo de atributo no soportado: " + attributeType);
//	        }
//
//	        // Insertar el ítem en la tabla
//	        table.putItem(item);
//	        System.out.println("Ítem creado exitosamente.");
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        System.out.println("Error al crear el ítem: " + e.getMessage());
//	    }
//	}

	public void createItem(String partitionKey, String partitionKeyValue, String sortKey, String sortKeyValue,
			String attribute1, String attribute1Value, String attribute2, String attribute2Value) {
		Table table = dynamoDB.getTable(tableName);

		try {
// Crear un ítem
			Item item;

// Verificar si se proporciona clave de ordenación
			if (sortKey != null && !sortKey.isEmpty()) {
				// Si hay clave de ordenación, usar ambos valores
				item = new Item().withPrimaryKey(partitionKey, partitionKeyValue, sortKey, sortKeyValue);
			} else {
				// Si no hay clave de ordenación, solo usar la clave de partición
				item = new Item().withPrimaryKey(partitionKey, partitionKeyValue);
			}

// Añadir atributos adicionales
			if (attribute1 != null && !attribute1.isEmpty() && attribute1Value != null) {
				item.withString(attribute1, attribute1Value);
			}
			if (attribute2 != null && !attribute2.isEmpty() && attribute2Value != null) {
				item.withString(attribute2, attribute2Value);
			}

// Insertar el ítem en la tabla
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

	// Método para leer keyValue y attributeValue y devolverlos como lista
	public List<String[]> getKeyAndAttributeValues(String keyName, String attributeName) {
		List<String[]> results = new ArrayList<>();
		try {
			Table table = dynamoDB.getTable(tableName);

			// Escanear toda la tabla
			for (Item item : table.scan(new ScanSpec())) {
				// Obtener valores específicos
				String keyValue = item.getString(keyName); // Valor de la clave primaria
				String attributeValue = item.getString(attributeName); // Valor del atributo

				// Guardar en la lista como un arreglo de dos elementos
				results.add(new String[] { keyValue, attributeValue });
			}
		} catch (Exception e) {
			System.err.println("Error al obtener valores: " + e.getMessage());
		}
		return results; // Devolver la lista con los valores
	}
}
