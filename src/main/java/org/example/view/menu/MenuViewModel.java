package org.example.view.menu;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import org.example.core.GameEngine;
import org.example.view.main.MainView;

import javax.inject.Inject;


public class MenuViewModel implements ViewModel {

    @Inject
    private GameEngine gameEngine;

    public MenuViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void newGame(int nbPlayers) {
        gameEngine.newGame(3);
    }
}
