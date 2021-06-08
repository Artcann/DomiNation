package org.example.view.menu;

import de.saxsys.mvvmfx.ViewModel;
import org.example.core.GameEngine;

import javax.inject.Inject;


public class MenuViewModel implements ViewModel {

    @Inject
    private final GameEngine gameEngine;

    public MenuViewModel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void newGame() {
        gameEngine.newGame(3);
    }
}
