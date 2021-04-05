module com.wiljos {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;

    opens org.example to javafx.fxml;
    exports org.example;
}