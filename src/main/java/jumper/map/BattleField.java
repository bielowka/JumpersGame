package jumper.map;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import jumper.gui.App;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BattleField implements Runnable{
    //mechanizmy ruchów
    //sprawdzanie czy ruch jest legalny tutaj - try cathc
    public int mapHeight = 8;
    public int mapWeight = 8;
    public boolean someoneWon = false;
    public GridPane gridPane;

    protected Map<Vector2d, Pawn> pawnsOfP1 = new LinkedHashMap<>();
    protected Map<Vector2d, Pawn> pawnsOfP2 = new LinkedHashMap<>();

    protected App observer;
    private int whichPlayer;

    private Vector2d whichPawn;
    private ArrayList<Vector2d> positions = new ArrayList<>();
    private boolean inMove = false;

    public BattleField(GridPane gridpane, App app){
        this.gridPane = gridpane;
        this.observer = app;
        this.whichPlayer = 1;
        for (int i = 0; i < 8; i++){
            Vector2d position1 = new Vector2d(0,i);
            pawnsOfP1.put(position1,new Pawn(true,position1));

            Vector2d position2 = new Vector2d(7,i);
            pawnsOfP2.put(position2,new Pawn(true,position2));
        }
    }


    @Override
    public synchronized void run() {
        while(!someoneWon){
            switch (whichPlayer){
                case 1:
                    Platform.runLater(() -> {
                        observer.moveVisual(this,gridPane,1);
                    });

                    while (!inMove){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}
                    }

                    positions.clear();
                    Platform.runLater(() -> {
                        observer.inMoveVisual(this,gridPane,1, whichPawn);
                    });
                    while (inMove){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}
                    }

                    whichPlayer = 2;
                    break;

                default:
                    Platform.runLater(() -> {
                        observer.moveVisual(this,gridPane,2);
                    });

                    while (!inMove){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}
                    }

                    positions.clear();
                    Platform.runLater(() -> {
                        observer.inMoveVisual(this,gridPane,2, whichPawn);
                    });
                    while (inMove){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}
                    }

                    whichPlayer = 1;
                    break;

            }
        }
    }

    public void setWhichPawn(Vector2d whichPawn) {
        this.whichPawn = whichPawn;
    }

    public void setPositions(ArrayList<Vector2d> positions) {
        this.positions = positions;
    }

    public ArrayList<Vector2d> getPositions() {
        return positions;
    }

    public void addPosition(Vector2d pos) {
        this.positions.add(pos);
    }

    public void setInMove(boolean inMove) {
        this.inMove = inMove;
    }

    public void setSomeoneWon() {
        this.someoneWon = true;
    }

    public Map<Vector2d, Pawn> getPawnsOfP1() {
        return pawnsOfP1;
    }

    public Map<Vector2d, Pawn> getPawnsOfP2() {
        return pawnsOfP2;
    }

}
