package org.example.view.main;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.Domino;
import org.example.core.Tile;
import org.example.util.Ressource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    private static final Logger logger = LogManager.getLogger(MainView.class);

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private GridPane gridBoard;

    @FXML
    private GridPane tableGrid;

    @FXML
    private Button updateBoardButton;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label selectedTileLabel;

    private Domino[] selectedTile;

    @FXML
    private Label currentPlayerScore;

    private ImageView[][] boardView;

    private ImageView[][] tableView;

    private Label[][] labelTable;

    private ChoiceDialog<String> chooseOrientation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.initBoard();

        initBoard();
        initTable();
        initChooseOrientation();

        currentPlayerLabel.textProperty().bind(viewModel.currentPlayerProperty());
        currentPlayerScore.textProperty().bind(viewModel.playerScoreProperty());

        viewModel.updateScore();

        viewModel.getObservableBoard().addListener((ListChangeListener<Tile>) change -> updateBoard());

        this.updateBoardButton.setOnAction(actionEvent -> nextPlayer());

        viewModel.getObservableTable().addListener((ListChangeListener<Domino[]>) change -> updateTable());
    }

    private void initBoard() {
        boardView = new ImageView[9][9];

        for (int i = 0; i<9; i++) {
            for (int j=0; j<9; j++) {
                ImageView image = new ImageView();

                image.setPreserveRatio(true);
                image.setFitWidth(100);
                image.setFitHeight(100);

                image.setOnMouseClicked(mouseEvent -> {

                    Integer[][] move = new Integer[2][2];

                    Node node = (Node) mouseEvent.getSource();
                    int columnIndex = GridPane.getColumnIndex(node);
                    int rowIndex = GridPane.getRowIndex(node);

                    final Integer[] secondTileCoordinate = new Integer[2];

                    logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);

                    if (selectedTile != null) {
                        Optional<String> result = chooseOrientation.showAndWait();
                        result.ifPresentOrElse(string -> {
                            switch (string) {
                                case "Up":
                                    secondTileCoordinate[0] = rowIndex - 1;
                                    secondTileCoordinate[1] = columnIndex;
                                    break;
                                case "Down":
                                    secondTileCoordinate[0] = rowIndex + 1;
                                    secondTileCoordinate[1] = columnIndex;
                                    break;
                                case "Left":
                                    secondTileCoordinate[0] = rowIndex;
                                    secondTileCoordinate[1] = columnIndex - 1;
                                    break;
                                case "Right":
                                    secondTileCoordinate[0] = rowIndex;
                                    secondTileCoordinate[1] = columnIndex + 1;
                                    break;
                            }
                        }, () -> {
                            throw new RuntimeException();
                        });
                    }

                    move[0] = new Integer[]{rowIndex, columnIndex};
                    move[1] = secondTileCoordinate;

                    if (!viewModel.makeMove(move, selectedTile)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Action");
                        alert.setContentText("This Move is not valid, please try again");
                        alert.show();
                    } else {
                        logger.debug("Domino Placed Successfully");
                        viewModel.updateScore();
                        viewModel.updateBoard();
                    }

                });

                boardView[i][j] = image;
                gridBoard.add(image, j, i);
            }
        }
        updateBoard();
    }

    private void initTable() {
        tableView = new ImageView[3][2];
        labelTable = new Label[3][2];

        for(int i=0; i<3; i++) {
            for(int j=0; j<2; j++) {
                ImageView image = new ImageView();

                image.setPreserveRatio(true);
                image.setFitWidth(200);
                image.setFitHeight(100);

                image.setOnMouseClicked(mouseEvent -> {
                    Node node = (Node) mouseEvent.getSource();
                    int columnIndex = GridPane.getColumnIndex(node);
                    int rowIndex = GridPane.getRowIndex(node);

                    logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);

                    Domino[] selectedDomino = viewModel.getObservableTable().get(rowIndex + (3 * columnIndex));

                    String selectedTileText = "Tile Number : " + selectedDomino[0].getNumber();

                    selectedTileLabel.setText(selectedTileText);
                    selectedTile = selectedDomino;
                });

                tableView[i][j] = image;
                tableGrid.add(image, j, i);

                Label label = new Label();

                GridPane.setValignment(label, VPos.BOTTOM);
                label.setPrefHeight(30);
                label.setPrefWidth(30);
                label.setAlignment(Pos.CENTER);
                label.setFont(new Font(18));
                label.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                label.setTextFill(Color.WHITE);

                label.setOnMouseClicked(mouseEvent -> {
                    Node node = (Node) mouseEvent.getSource();
                    int columnIndex = GridPane.getColumnIndex(node);
                    int rowIndex = GridPane.getRowIndex(node);

                    logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);
                });

                labelTable[i][j] = label;
                tableGrid.add(label, j, i);
            }
        }
        updateTable();
    }

    private void updateBoard() {
        ObservableList<Tile> board = viewModel.getObservableBoard();

        for (int i = 0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.get(j+(9*i)) != null && board.get(j+(9*i)).getType().equals("Castle")) {
                    boardView[i][j].setImage(Ressource.getCastleImage(viewModel.getColor()));
                } else if (board.get(j+(9*i)) != null) {
                    int index = board.get(j+(9*i)).getIndex();
                    boardView[i][j].setImage(Ressource.getRecto(board.get(j+9*i).getNumber(), index));
                } else {
                    boardView[i][j].setImage(Ressource.getWhite());
                }
            }
        }
    }

    private void updateTable() {

        for(int j=0; j<2; j++) {
            for(int i=0; i<3; i++) {
                tableView[i][j].setImage(Ressource.getWhite());
            }
        }

        int i = 0;
        int j = 0;
        for(Domino[] domino : viewModel.getObservableTable().get()) {
            tableView[i][j].setImage(Ressource.getRecto(domino[0].getNumber()));
            tableView[i][j].toBack();

            labelTable[i][j].setText(String.valueOf(domino[0].getNumber()));

            i++;
            if(i == 3) {
                j++;
                i = 0;
            }
        }
    }

    private void nextPlayer() {
        viewModel.nextPlayer();
        viewModel.updateBoard();
        viewModel.updateScore();
    }

    private void initChooseOrientation() {
        List<String> choices = new ArrayList<>();
        choices.add("Up");
        choices.add("Down");
        choices.add("Right");
        choices.add("Left");

        chooseOrientation = new ChoiceDialog<>("Up", choices);
        chooseOrientation.setContentText("Choose an Orientation for the Domino");
    }

}
