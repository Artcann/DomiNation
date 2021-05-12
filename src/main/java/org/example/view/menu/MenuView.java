package org.example.view.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuView implements FxmlView<MenuViewModel>, Initializable {

    @InjectViewModel
    private MenuViewModel viewModel;

    @FXML
    private Button startButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Integer> choices = new ArrayList<>();
        for(var i = 0; i<3; i++) choices.add(i+2);

        ChoiceDialog<Integer> choosePlayers = new ChoiceDialog<>(2, choices);

        choosePlayers.setTitle("Nombre de joueurs");
        choosePlayers.setContentText("Combien de joueurs humains ? ");

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Optional<Integer> result = choosePlayers.showAndWait();
                result.ifPresent(integer -> viewModel.newGame(integer));
            }
        });
    }
}
