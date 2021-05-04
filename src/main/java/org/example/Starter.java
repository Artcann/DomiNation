package org.example;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.core.GameEngine;

public class Starter extends Application {
    public static void main(String...args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World Application");

//        ViewTuple<TestView, HelloWorldViewModel> viewTuple = FluentViewLoader.fxmlView(TestView.class).load();
//
//        Parent root = viewTuple.getView();
//        stage.setScene(new Scene(root));
//        stage.show();

        GameEngine gameEngine = new GameEngine();
        gameEngine.newGame(3);
    }
}
