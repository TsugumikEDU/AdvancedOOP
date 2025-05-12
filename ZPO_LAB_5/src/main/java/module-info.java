module com.blazejdrozd.zpo_lab_5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.blazejdrozd.zpo_lab_5 to javafx.fxml;
    exports com.blazejdrozd.zpo_lab_5;
}