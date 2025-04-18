package pl.blazejdrozd.zpo_lab_4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectController {
    private Object currentObject = null;
    private final Map<String, TextInputControl> fieldInputs = new HashMap<>();

    @FXML
    private VBox fields;

    @FXML
    private TextArea log;

    @FXML
    private TextField className;

    @FXML
    private void createObject() {
        fields.getChildren().clear();
        fieldInputs.clear();
        log.clear();
        currentObject = null;

        String classNameText = this.className.getText();
        if (classNameText == null || classNameText.trim().isEmpty()) {
            log("Error: Class name required.");
            return;
        }

        log("Attempting to create object...");

        try {
            Class<?> clazz = Class.forName(classNameText);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            currentObject = constructor.newInstance();
            log("Object created successfully.");

            for (Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                HBox container = new HBox(10);

                Label label = new Label(fieldName + " (" + fieldType.getSimpleName() + "):");
                label.setMinWidth(150);

                TextInputControl input;

                if (fieldName.toLowerCase().contains("text")) {
                    input = new TextArea();
                    ((TextArea) input).setPrefRowCount(3);
                    ((TextArea) input).setWrapText(true);
                } else {
                    input = new TextField();
                }
                input.setMinWidth(200);

                try {
                    String getterName = determineGetterName(fieldName, fieldType);
                    Method getter = clazz.getMethod(getterName);
                    Object value = getter.invoke(currentObject);
                    input.setText(value != null ? String.valueOf(value) : "");
                } catch (NoSuchMethodException e) {
                    log("Info: Could not get initial value for a field.");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log("Error: Failed to get initial value for a field.");
                    e.printStackTrace(System.err);
                }

                container.getChildren().addAll(label, input);
                fields.getChildren().add(container);
                fieldInputs.put(fieldName, input);
            }
            log("Fields displayed.");

        } catch (ClassNotFoundException e) {
            log("Error: Class not found.");
            e.printStackTrace(System.err);
        } catch (NoSuchMethodException e) {
            log("Error: Constructor issue.");
            e.printStackTrace(System.err);
        } catch (InvocationTargetException e) {
            log("Error: Constructor failed.");
            e.getTargetException().printStackTrace(System.err);
        } catch (InstantiationException e) {
            log("Error: Cannot create instance.");
            e.printStackTrace(System.err);
        } catch (IllegalAccessException e) {
            log("Error: Cannot access constructor.");
            e.printStackTrace(System.err);
        } catch (Exception e) {
            log("Error: Object creation failed.");
            e.printStackTrace(System.err);
        }
    }

    @FXML
    private void saveChanges() {
        if (currentObject == null) {
            log("Error: No object created.");
            return;
        }

        log("Attempting to save changes...");
        Class<?> clazz = currentObject.getClass();
        boolean changesMade = false;
        boolean errorOccurred = false;

        for (Map.Entry<String, TextInputControl> entry : fieldInputs.entrySet()) {
            String fieldName = entry.getKey();
            TextInputControl inputControl = entry.getValue();
            String valueStr = inputControl.getText();

            try {
                Field field = clazz.getDeclaredField(fieldName);
                Class<?> fieldType = field.getType();
                String setterName = "set" + capitalize(fieldName);
                Method setter = clazz.getMethod(setterName, fieldType);
                Object parsedValue = parseValue(fieldType, valueStr);
                setter.invoke(currentObject, parsedValue);

                changesMade = true;

            } catch (NoSuchFieldException e) {
                log("Error: Field not found during save.");
                errorOccurred = true;
                e.printStackTrace(System.err);
            } catch (NoSuchMethodException e) {
                log("Info: Cannot save a field (no setter).");
            } catch (IllegalAccessException e) {
                log("Error: Cannot access setter for a field.");
                errorOccurred = true;
                e.printStackTrace(System.err);
            } catch (InvocationTargetException e) {
                log("Error: Setting field failed.");
                errorOccurred = true;
                e.getTargetException().printStackTrace(System.err);
            } catch (NumberFormatException e) {
                log("Error: Invalid number format.");
                errorOccurred = true;
            } catch (IllegalArgumentException e) {
                log("Error: Type mismatch.");
                errorOccurred = true;
            } catch (RuntimeException e) {
                log("Error: Could not parse value.");
                errorOccurred = true;
            } catch (Exception e) {
                log("Error: Field saving failed.");
                errorOccurred = true;
                e.printStackTrace(System.err);
            }
        }

        if (changesMade && !errorOccurred) {
            log("Changes saved.");
        } else if (errorOccurred) {
            log("Errors occurred during save operation. Some changes might not be saved.");
        } else {
            log("No changes applied (possibly no editable fields with setters).");
        }


        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                log(field.getName() + "=" + field.get(currentObject));
            } catch (IllegalAccessException e) {
                log("Error: Cannot access field: " + field.getName());
            }
        }
    }

    private Object parseValue(Class<?> type, String value) {
        if (value == null || (value.isEmpty() && !type.isPrimitive())) {
            if (!type.isPrimitive()) return null;
        }

        try {
            if (type == String.class) return value;
            if (type == int.class || type == Integer.class) return Integer.parseInt(value);
            if (type == double.class || type == Double.class) return Double.parseDouble(value);
            if (type == float.class || type == Float.class) return Float.parseFloat(value);
            if (type == boolean.class || type == Boolean.class) {
                return "true".equalsIgnoreCase(value) || "1".equals(value) || "yes".equalsIgnoreCase(value);
            }
            if (type == long.class || type == Long.class) return Long.parseLong(value);
            if (type == short.class || type == Short.class) return Short.parseShort(value);
            if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
            if (type == char.class || type == Character.class) return value.charAt(0);
            log("Warning: Unsupported type encountered.");
            return value;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number format for type " + type.getSimpleName() + ": " + value);
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private String determineGetterName(String fieldName, Class<?> fieldType) {
        String capitalizedFieldName = capitalize(fieldName);
        if (fieldType == boolean.class || fieldType == Boolean.class) {
            try {
                Class<?> targetClass = (currentObject != null) ? currentObject.getClass() : Class.forName(className.getText());
                targetClass.getMethod("is" + capitalizedFieldName);
                return "is" + capitalizedFieldName;
            } catch (NoSuchMethodException e) {
                return "get" + capitalizedFieldName;
            } catch (Exception e) {
                System.err.println("Could not determine getter type for boolean, defaulting to get" + capitalizedFieldName);
                return "get" + capitalizedFieldName;
            }
        } else {
            return "get" + capitalizedFieldName;
        }
    }

    private void log(String message) {
        log.appendText(message + "\n");
    }
}