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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.GameEngine;
import org.example.util.Ressource;
import org.example.view.menu.MenuView;
import org.example.view.menu.MenuViewModel;

import java.io.IOException;

public class Starter extends Application {

    private static final Logger logger = LogManager.getLogger(Starter.class);

    private static Stage primaryStage;

    public static void main(String...args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            e.printStackTrace();
        });

        EasyDI easyDI = new EasyDI();
        easyDI.bindProvider(HostServices.class, this::getHostServices);

        easyDI.markAsSingleton(GameEngine.class);

        MvvmFX.setCustomDependencyInjector(easyDI::getInstance);

        primaryStage = stage;

        Ressource.loadRessources();

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
