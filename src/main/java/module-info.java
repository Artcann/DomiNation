module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;
    requires javax.inject;
    requires org.apache.logging.log4j;
    requires org.jetbrains.annotations;

    opens org.example to javafx.fxml;
    opens org.example.helloWorld to javafx.fxml, de.saxsys.mvvmfx;
    exports org.example;
}