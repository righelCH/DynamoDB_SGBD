package Righel_Backend;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class OperacionesCRUD {

	private AmazonDynamoDB userBajoNivel;

	public OperacionesCRUD(AmazonDynamoDB userBajoNivel) {
		this.userBajoNivel = userBajoNivel;

	}

	//-------------------------  CREATE/CREAR ITEM ------------------------------------------------------------------
	public void crearItem(String selectedTable, String partitionKeyName, String partitionKeyValue, String sortKeyName,
			String sortKeyValue, String attributeName, String attributeType, String attributeValue) {
		try {
			if (userBajoNivel == null) {
				throw new IllegalStateException("El cliente DynamoDB no está inicializado.");
			}

// Crear instancia de DynamoDB y seleccionar la tabla
			DynamoDB dynamoDB = new DynamoDB(userBajoNivel);
			Table table = dynamoDB.getTable(selectedTable);

// Crear el ítem
			Item item;

// Verificar si la clave de ordenación es necesaria
			if (!sortKeyName.isEmpty() && !sortKeyValue.isEmpty()) {
				item = new Item().withPrimaryKey(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue);
			} else {
				item = new Item().withPrimaryKey(partitionKeyName, partitionKeyValue);
			}

// Procesar el tipo del atributo y agregarlo al ítem
			switch (attributeType) {
			case "String (S)":
				item.withString(attributeName, attributeValue);
				break;
			case "Number (N)":
				item.withNumber(attributeName, Double.parseDouble(attributeValue));
				break;
			case "Boolean (BOOL)":
				item.withBoolean(attributeName, Boolean.parseBoolean(attributeValue));
				break;
			case "Binary (B)":
				item.withBinary(attributeName, attributeValue.getBytes());
				break;
			case "Null (NULL)":
				item.withNull(attributeName);
				break;
			case "List (L)":
				List<String> list = Arrays.asList(attributeValue.split(","));
				item.withList(attributeName, list);
				break;
			case "Map (M)":
				Map<String, String> map = Arrays.stream(attributeValue.split(",")).map(s -> s.split("="))
						.collect(Collectors.toMap(a -> a[0].trim(), a -> a[1].trim()));
				item.withMap(attributeName, map);
				break;
			case "String Set (SS)":
				Set<String> stringSet = new HashSet<>(Arrays.asList(attributeValue.split(",")));
				item.withStringSet(attributeName, stringSet);
				break;
			case "Number Set (NS)":
				Set<Number> numberSet = Arrays.stream(attributeValue.split(",")).map(Double::parseDouble)
						.collect(Collectors.toSet());
				item.withNumberSet(attributeName, numberSet);
				break;
			case "Binary Set (BS)":
				Set<byte[]> binarySet = Arrays.stream(attributeValue.split(",")).map(String::getBytes)
						.collect(Collectors.toSet());
				item.withBinarySet(attributeName, binarySet);
				break;
			default:
				throw new IllegalArgumentException("Tipo de atributo no soportado.");
			}

// Insertar el ítem en la tabla
			table.putItem(item);

// Mensaje de éxito
			JOptionPane.showMessageDialog(null, "Ítem creado exitosamente en la tabla: " + selectedTable, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"El valor ingresado no es válido para el tipo seleccionado (ejemplo: Number).", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al crear el ítem: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}

	}

	//------------------------- READ/LEER ITEM ------------------------------------------------------------------
	public void leerItem(String selectedTable, String partitionKeyName, String partitionKeyValue, String sortKeyName,
			String sortKeyValue, AmazonDynamoDB client) {
		try {
// Validar que la clave de partición esté presente
			if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
				throw new IllegalArgumentException(
						"La clave de partición y su valor son obligatorios para leer un ítem.");
			}

// Crear instancia de DynamoDB y seleccionar la tabla
			DynamoDB dynamoDB = new DynamoDB(client);
			Table table = dynamoDB.getTable(selectedTable);

// Leer el ítem, verificando si hay una clave de ordenación
			Item item;
			if (!sortKeyName.isEmpty() && !sortKeyValue.isEmpty()) {
				item = table.getItem(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue);
			} else {
				item = table.getItem(partitionKeyName, partitionKeyValue);
			}

// Verificar si el ítem existe
			if (item != null) {
				// Mostrar el ítem encontrado
				JOptionPane.showMessageDialog(null, "Ítem encontrado:\n" + item.toJSONPretty(), "Ítem Encontrado",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró el ítem con la clave proporcionada.",
						"Ítem No Encontrado", JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al leer el ítem: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	//------------------------- DELETE/BORRAR ITEM ------------------------------------------------------------------
	public void borrarItem(String selectedTable, String partitionKeyName, String partitionKeyValue, String sortKeyName,
			String sortKeyValue, AmazonDynamoDB client) {
		try {
			// Validar que la clave de partición esté presente
			if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
				throw new IllegalArgumentException(
						"La clave de partición y su valor son obligatorios para borrar un ítem.");
			}

			// Crear instancia de DynamoDB y seleccionar la tabla
			DynamoDB dynamoDB = new DynamoDB(client);
			Table table = dynamoDB.getTable(selectedTable);

			// Borrar el ítem
			if (!sortKeyName.isEmpty() && !sortKeyValue.isEmpty()) {
				table.deleteItem(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue);
			} else {
				table.deleteItem(partitionKeyName, partitionKeyValue);
			}

			// Mensaje de éxito
			JOptionPane.showMessageDialog(null, "Ítem borrado exitosamente de la tabla: " + selectedTable, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al borrar el ítem: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	//------------------------- UPDATE/MODIFICAR ITEM ------------------------------------------------------------------
	public void modificarItem(String selectedTable, String partitionKeyName, String partitionKeyValue,
			String sortKeyName, String sortKeyValue, String attributeName, String attributeType, String attributeValue,
			AmazonDynamoDB client) {
		try {
// Validar que la clave de partición esté presente
			if (partitionKeyName.isEmpty() || partitionKeyValue.isEmpty()) {
				throw new IllegalArgumentException(
						"La clave de partición y su valor son obligatorios para modificar un ítem.");
			}

// Validar que los atributos adicionales estén presentes
			if (attributeName.isEmpty() || attributeValue.isEmpty() || attributeType.equals("Seleccione")) {
				throw new IllegalArgumentException(
						"Debe proporcionar el nombre, tipo y valor del atributo a modificar.");
			}

// Crear instancia de DynamoDB y seleccionar la tabla
			DynamoDB dynamoDB = new DynamoDB(client);
			Table table = dynamoDB.getTable(selectedTable);

// Crear la especificación para la actualización
			UpdateItemSpec updateItemSpec = new UpdateItemSpec()
					.withPrimaryKey(partitionKeyName, partitionKeyValue, sortKeyName, sortKeyValue)
					.withUpdateExpression("set #attr = :val").withNameMap(new NameMap().with("#attr", attributeName))
					.withValueMap(new ValueMap().with(":val", processAttributeValue(attributeType, attributeValue)));

// Ejecutar la actualización
			table.updateItem(updateItemSpec);

// Mensaje de éxito
			JOptionPane.showMessageDialog(null, "Ítem modificado exitosamente en la tabla: " + selectedTable, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error al modificar el ítem: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	
// Método auxiliar para procesar el valor del atributo según su tipo
	private static Object processAttributeValue(String attributeType, String attributeValue) {
		switch (attributeType) {
		case "String":
			return attributeValue;
		case "Number":
			return Double.valueOf(attributeValue);
		case "Boolean":
			return Boolean.valueOf(attributeValue);
		default:
			return attributeValue; // Por defecto, se asume como String
		}
	}
}