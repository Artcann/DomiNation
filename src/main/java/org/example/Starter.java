package org.example;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.view.menu.MenuView;
import org.example.view.menu.MenuViewModel;

public class Starter extends Application {
    public static void main(String...args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Domi-Nation");

        ViewTuple<MenuView, MenuViewModel> viewTuple = FluentViewLoader.fxmlView(MenuView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
