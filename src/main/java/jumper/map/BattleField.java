package jumper.map;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import jumper.gui.App;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static jumper.map.MapDirection.intToMapDirection;

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
        String winner = "";
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
                    for (Vector2d i : positions){
                        this.pawnsOfP1.remove(whichPawn);
                        this.pawnsOfP1.put(i,new Pawn(true,i));
                        this.whichPawn = i;
                        Platform.runLater(() -> {
                            observer.mapVisual(this,gridPane);
                        });
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}

                    }

                    Platform.runLater(() -> {
                        observer.setLabel("Black's move...");
                    });
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
                    for (Vector2d i : positions){
                        this.pawnsOfP2.remove(whichPawn);
                        this.pawnsOfP2.put(i,new Pawn(true,i));
                        this.whichPawn = i;
                        Platform.runLater(() -> {
                            observer.mapVisual(this,gridPane);
                        });
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();}

                    }

                    Platform.runLater(() -> {
                        observer.setLabel("White's move...");
                    });
                    whichPlayer = 1;
                    break;

            }
            winner = checkIfSomeoneWon();
        }

        String finalWinner = winner;
        Platform.runLater(() -> {
            observer.setLabel("The winner is " + finalWinner);
        });
        int winnerInt = 1;
        if (winner.equals("black")) winnerInt = 2;
        this.winningAnimation(winnerInt);

    }

    public boolean checkField(Vector2d newPosition){
        if (newPosition.precedes(new Vector2d(0,0)) || newPosition.follows(new Vector2d(7,7))) {
            Platform.runLater(() -> {
                observer.setLabel("Invalid position...");
            });
            return false;
        }

        if (positions.size() == 0) {
            for (int i = 0; i < 4; i++) {
                if (whichPawn.add(intToMapDirection(i).toUnitVector()).equals(newPosition)) {
                    if (pawnsOfP1.get(newPosition) != null || pawnsOfP2.get(newPosition) != null) {
                        Platform.runLater(() -> {
                            observer.setLabel("Invalid position...");
                        });
                        return false;
                    }

                    Platform.runLater(() -> {
                        observer.setLabel("In move...");
                    });
                    this.addPosition(newPosition);
                    return true;
                }
            }


            for (int i = 0; i < 4; i++) {
                if (pawnsOfP1.get(whichPawn.add(intToMapDirection(i).toUnitVector())) != null
                        || pawnsOfP2.get(whichPawn.add(intToMapDirection(i).toUnitVector())) != null) {
                    if (whichPawn.add(intToMapDirection(i).toUnitVector()).add(intToMapDirection(i).toUnitVector()).equals(newPosition)) {
                        this.addPosition(newPosition);
                        Platform.runLater(() -> {
                            observer.setLabel("In move...");
                        });
                        return true;
                    }
                }
            }
        }

        else {
            for (int i = 0; i < 4; i++) {
                if (pawnsOfP1.get(positions.get(positions.size()-1).add(intToMapDirection(i).toUnitVector())) != null
                        || pawnsOfP2.get(positions.get(positions.size()-1).add(intToMapDirection(i).toUnitVector())) != null) {

                    for (int j = 0; j < 4; j++) {
                        if (positions.get(positions.size()-1).add(intToMapDirection(j).toUnitVector()).add(intToMapDirection(j).toUnitVector()).equals(newPosition)) {
                            this.addPosition(newPosition);
                            Platform.runLater(() -> {
                                observer.setLabel("In move...");
                            });
                            return true;
                        }
                    }
                }
            }
        }




        Platform.runLater(() -> {
            observer.setLabel("Invalid position...");
        });
        return false;

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

    public String checkIfSomeoneWon() {
        String winner = "white";
        for (int i=0; i<8; i++){
            if (pawnsOfP1.get(new Vector2d(7,i)) == null) winner = "";
        }
        if (!winner.equals("")){
            this.someoneWon = true;
            return winner;
        }

        winner = "black";
        for (int i=0; i<8; i++){
            if (pawnsOfP2.get(new Vector2d(0,i)) == null) winner = "";
        }
        if (!winner.equals("")){
            this.someoneWon = true;
            return winner;
        }

        return "";
    }

    public void winningAnimation(int winner){
        for (int i = 0; i < 16; i=i+2){
            int finalI = i;
            Platform.runLater(() -> {
                observer.winningVisual(this,gridPane, finalI,winner);
            });
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();}
        }
    }
}
