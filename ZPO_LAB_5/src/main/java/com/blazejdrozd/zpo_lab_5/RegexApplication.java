package com.blazejdrozd.zpo_lab_5;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Field;

public class RegexApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserData user = new UserData();
        Field nameField = UserData.class.getDeclaredField("name");

        VinputText vinput = new VinputText(new MyPatternValidator(nameField));

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setDisable(true);

        vinput.textField.textProperty().addListener((obs, oldVal, newVal) -> {
            confirmBtn.setDisable(!vinput.isValid());
        });

        confirmBtn.setOnAction(e -> {
            user.setName(vinput.getText());
            System.out.println("Wprowadzone imię: " + user.getName());
        });

        VBox root = new VBox(10, vinput, confirmBtn);

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Walidator z Adnotacją");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}