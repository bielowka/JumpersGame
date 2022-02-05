package jumper.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jumper.map.BattleField;
import jumper.map.Pawn;
import jumper.map.Vector2d;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App extends Application{
    Stage window;
    Scene scene;
    GridPane gridpane = new GridPane();
    Button button = new Button("Make a move");

    Image whitePawn;
    {
        try {
            whitePawn = new Image(new FileInputStream("src/main/resources/white.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Image blackPawn;
    {
        try {
            blackPawn = new Image(new FileInputStream("src/main/resources/black.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void mapVisual(BattleField battleField, GridPane pane){
        pane.setGridLinesVisible(false);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        pane.getChildren().clear();
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < battleField.mapWeight; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(60);
            pane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < battleField.mapHeight; i++) {
            RowConstraints rowConstraints = new RowConstraints(60);
            pane.getRowConstraints().add(rowConstraints);
        }

        for (int y = 0; y < battleField.mapHeight; y++)
            for (int x = 0; x < battleField.mapWeight; x++) {
                if (battleField.getPawnsOfP1().get(new Vector2d(x, y)) != null){
                    GuiElementBox Box = new GuiElementBox(whitePawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);
                }
                if (battleField.getPawnsOfP2().get(new Vector2d(x, y)) != null){
                    GuiElementBox Box = new GuiElementBox(blackPawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);
                }
        }
    }

    public void moveVisual(BattleField battleField, GridPane pane,int which){
        pane.setGridLinesVisible(false);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        pane.getChildren().clear();
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < battleField.mapWeight; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(60);
            pane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < battleField.mapHeight; i++) {
            RowConstraints rowConstraints = new RowConstraints(60);
            pane.getRowConstraints().add(rowConstraints);
        }

        for (int y = 0; y < battleField.mapHeight; y++)
            for (int x = 0; x < battleField.mapWeight; x++) {
                if (battleField.getPawnsOfP1().get(new Vector2d(x, y)) != null){
                    GuiElementBox Box = new GuiElementBox(whitePawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);

                    if (which == 1) {
                        assert box != null;
                        VBox finalBox = box;
                        int finalX = x;
                        int finalY = y;
                        box.setOnMouseClicked((event) -> {
                            finalBox.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
                            battleField.setInMove(true);
                            battleField.setWhichPawn(new Vector2d(finalX, finalY));
                            battleField.setInMove(true);
                        });
                    }

                }
                if (battleField.getPawnsOfP2().get(new Vector2d(x, y)) != null){
                    GuiElementBox Box = new GuiElementBox(blackPawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);

                    if (which == 2) {
                        assert box != null;
                        VBox finalBox = box;
                        int finalX = x;
                        int finalY = y;
                        box.setOnMouseClicked((event) -> {
                            finalBox.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
                            battleField.setInMove(true);
                            battleField.setWhichPawn(new Vector2d(finalX, finalY));
                            battleField.setInMove(true);
                        });
                    }
                }
            }
    }

    public void inMoveVisual(BattleField battleField, GridPane pane,int which,Vector2d whichPawn){
        pane.setGridLinesVisible(false);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        pane.getChildren().clear();
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(10, 10, 10, 10));

        for (int i = 0; i < battleField.mapWeight; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(60);
            pane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < battleField.mapHeight; i++) {
            RowConstraints rowConstraints = new RowConstraints(60);
            pane.getRowConstraints().add(rowConstraints);
        }

        for (int y = 0; y < battleField.mapHeight; y++)
            for (int x = 0; x < battleField.mapWeight; x++) {
                if (battleField.getPawnsOfP1().get(new Vector2d(x, y)) != null) {
                    GuiElementBox Box = new GuiElementBox(whitePawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);
                    if (x == whichPawn.x && y == whichPawn.y) box.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else if (battleField.getPawnsOfP2().get(new Vector2d(x, y)) != null){
                    GuiElementBox Box = new GuiElementBox(blackPawn);
                    VBox box = null;
                    try {
                        box = Box.MakeBox();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);
                    if (x == whichPawn.x && y == whichPawn.y) box.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));

                }
                else{
                    VBox box = new VBox();
                    GridPane.setConstraints(box, x, y);
                    GridPane.setHalignment(box, HPos.CENTER);
                    pane.add(box, x, y);

                    if (which == 1) {
                        assert box != null;
                        VBox finalBox = box;
                        int finalX = x;
                        int finalY = y;
                        box.setOnMouseClicked((event) -> {
                            finalBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                            battleField.addPosition(new Vector2d(finalX,finalY));
                        });
                    }

                    if (which == 2) {
                        assert box != null;
                        VBox finalBox = box;
                        int finalX = x;
                        int finalY = y;
                        box.setOnMouseClicked((event) -> {
                            finalBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                            battleField.addPosition(new Vector2d(finalX,finalY));
                        });
                    }

                }
            }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        BattleField battleField = new BattleField(gridpane,this);
        mapVisual(battleField,gridpane);

        Thread thread = new Thread(battleField);
        thread.start();

        VBox layout = new VBox(gridpane,button);
        layout.setAlignment(Pos.CENTER);

        button.setOnAction((event) -> {
            battleField.setInMove(false);
            System.out.println(battleField.getPositions());
        });

        scene = new Scene(layout,500,550);
        primaryStage.setTitle("Jumpers");
        window.setScene(scene);
        window.show();
    }
}
