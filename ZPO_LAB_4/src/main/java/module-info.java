module pl.blazejdrozd.zpo_lab_4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.blazejdrozd.zpo_lab_4 to javafx.fxml;
    exports pl.blazejdrozd.zpo_lab_4;
}