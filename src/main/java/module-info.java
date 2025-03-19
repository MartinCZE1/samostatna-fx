module com.example.maturita {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maturita to javafx.fxml;
    exports com.example.maturita;
}