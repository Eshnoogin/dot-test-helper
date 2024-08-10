module com.eshaan.dottest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eshaan.dottest to javafx.fxml;
    exports com.eshaan.dottest;
}