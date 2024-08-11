module com.eshaan.dottest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.eshaan.dottest to javafx.fxml;

    exports com.eshaan.dottest;
}