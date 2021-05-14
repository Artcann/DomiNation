package org.example.view.main;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.example.core.Tile;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private GridPane grid;

    @FXML
    private Button updateBoardButton;

    @FXML
    private Label currentPlayerLabel;

    private ObservableList<Tile> board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image red = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("test.png")));
        Image green = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("test2.png")));

//        for(int i=0; i<9; i++) {
//            for(int j=0; j<9; j++) {
//                if((i%2 == 0 && j%2==1) || (i%2 == 1 && j%2 == 0)) {
//                    ImageView image = new ImageView();
//                    image.setImage(red);
//                    grid.add(image, i, j);
//                } else {
//                    ImageView image = new ImageView();
//                    image.setImage(green);
//                    grid.add(image, i, j);
//                }
//
//            }
//        }
        viewModel.initBoard();

        currentPlayerLabel.textProperty().bind(viewModel.currentPlayerProperty());

        this.board = viewModel.getObservableBoard();
        this.board.addListener(new ListChangeListener<Tile>() {
            @Override
            public void onChanged(Change<? extends Tile> change) {
                updateBoard();
            }
        });

        updateBoard();

        this.updateBoardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewModel.nextPlayer();
                viewModel.initBoard();
            }
        });
    }

    public void updateBoard() {
        for(Tile tile : board) {
            if(tile != null && tile.getType().equals("Castle")) {
                ImageView castleImage = new ImageView();
                castleImage.setPreserveRatio(true);
                castleImage.setFitHeight(100);
                castleImage.setFitWidth(100);
                switch (viewModel.getColor()) {
                    case BLUE:
                        castleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("blueCastle.png"))));
                        break;
                    case PINK:
                        castleImage.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pinkCastle.png"))));
                        break;
                }
                grid.add(castleImage, tile.getPosition()[0], tile.getPosition()[1]);
            }
        }
    }


}
