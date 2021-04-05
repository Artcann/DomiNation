package org.example.helloWorld;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.helloWorld.HelloWorldViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TestView implements FxmlView<HelloWorldViewModel>, Initializable {
        
    @FXML
    private Label helloLabel;

    @InjectViewModel
    private HelloWorldViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessageProperty());
    }
}
