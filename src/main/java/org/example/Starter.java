package org.example;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.core.GameEngine;
import org.example.view.main.MainViewModel;
import org.example.view.menu.MenuView;
import org.example.view.menu.MenuViewModel;

public class Starter extends Application {

    private static Stage primaryStage;

    public static void main(String...args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EasyDI easyDI = new EasyDI();
        easyDI.bindProvider(HostServices.class, this::getHostServices);

        easyDI.markAsSingleton(GameEngine.class);

        MvvmFX.setCustomDependencyInjector(easyDI::getInstance);

        primaryStage = stage;

        stage.setTitle("Domi-Nation");

        ViewTuple<MenuView, MenuViewModel> viewTuple = FluentViewLoader.fxmlView(MenuView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
