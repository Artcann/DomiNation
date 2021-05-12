package org.example.view.menu;

import de.saxsys.mvvmfx.ViewModel;
import org.example.core.GameEngine;

public class MenuViewModel implements ViewModel {

    public void newGame(int nbPlayers) {

        GameEngine gameEngine = new GameEngine();
        gameEngine.newGame(3);
    }
}
