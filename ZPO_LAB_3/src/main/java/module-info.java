module pl.blazejdrozd.quiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json.chargebee;


    opens pl.blazejdrozd.quiz to javafx.fxml;
    exports pl.blazejdrozd.quiz;
}