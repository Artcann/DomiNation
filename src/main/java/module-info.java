module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;
    requires javax.inject;
    requires org.apache.logging.log4j;
    requires org.jetbrains.annotations;
    requires easy.di;

    opens org.example.view.menu to javafx.fxml, de.saxsys.mvvmfx;
    opens org.example.util to javafx.fxml;
    exports org.example;
    exports org.example.core to easy.di;
}