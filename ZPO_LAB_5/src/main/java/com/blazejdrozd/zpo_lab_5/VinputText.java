package com.blazejdrozd.zpo_lab_5;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class VinputText extends HBox {
    public TextField textField;
    private ImageView icon;
    private Validator validator;
    private Label label;
    private Tooltip tooltip;

    public VinputText(Validator v) {
        validator = v;
        tooltip = new Tooltip();
        label = new Label();
        label.setText("<- my validated field");
        textField = new TextField();
        icon = new ImageView(new Image("wrong.png"));
        icon.setFitHeight(20);
        icon.setFitWidth(20);

        this.getChildren().addAll(icon, textField, label);

        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (validator != null) {
                validator.validate(newVal);
                boolean isValid = validator.isValid();
                icon.setImage(new Image(isValid ? "ok.png" : "wrong.png"));
                tooltip.setText(isValid ? "OK" : validator.getMessage());
                Tooltip.install(icon, tooltip);
            }
        });
    }

    public String getText() {
        return textField.getText();
    }

    public boolean isValid() {
        return validator != null && validator.isValid();
    }
}