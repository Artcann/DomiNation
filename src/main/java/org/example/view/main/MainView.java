package org.example.view.main;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.Domino;
import org.example.core.GameEngine;
import org.example.core.Tile;
import org.example.util.Ressource;

import java.net.URL;
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

    private ImageView[][] boardView;

    private ImageView[][] tableView;

    private Label[][] labelTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.initBoard();

        initBoard();
        initTable();

        logger.debug("test");

        currentPlayerLabel.textProperty().bind(viewModel.currentPlayerProperty());

        viewModel.getObservableBoard().addListener(new ListChangeListener<Tile>() {
            @Override
            public void onChanged(Change<? extends Tile> change) {
                updateBoard();
            }
        });

        this.updateBoardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewModel.nextPlayer();
                viewModel.updateBoard();
            }
        });

        viewModel.getObservableTable().addListener(new ListChangeListener<Domino[]>() {
            @Override
            public void onChanged(Change<? extends Domino[]> change) {
                updateTable();
            }
        });
    }

    private void initBoard() {
        boardView = new ImageView[9][9];

        for (int i = 0; i<9; i++) {
            for (int j=0; j<9; j++) {
                ImageView image = new ImageView();

                image.setPreserveRatio(true);
                image.setFitWidth(100);
                image.setFitHeight(100);

                image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Node node = (Node)mouseEvent.getSource();
                        int columnIndex = GridPane.getColumnIndex(node);
                        int rowIndex = GridPane.getRowIndex(node);

                        logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);
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

                image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Node node = (Node)mouseEvent.getSource();
                        int columnIndex = GridPane.getColumnIndex(node);
                        int rowIndex = GridPane.getRowIndex(node);

                        logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);
                    }
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

                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Node node = (Node)mouseEvent.getSource();
                        int columnIndex = GridPane.getColumnIndex(node);
                        int rowIndex = GridPane.getRowIndex(node);

                        logger.debug("Node Row :" + rowIndex + "; Node Column :" + columnIndex);
                    }
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
                    boardView[i][j].setImage(null);
                }
            }
        }
    }

    private void updateTable() {
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

//    private void updateTable2() {
//        int i = 0;
//        int j = 0;
//        for(Domino[] domino : viewModel.getObservableTable().get()) {
//            tableView[i][j].setImage(Ressource.getVerso(2));
//            tableView[i][j].toBack();
//            i++;
//            if(i == 3) {
//                j++;
//                i = 0;
//            }
//        }
//    }

}
