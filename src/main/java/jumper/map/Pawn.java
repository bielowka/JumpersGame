package jumper.map;

public class Pawn {
    private boolean isWhite;
    private Vector2d currentPosition;

    public Pawn(boolean isWhite, Vector2d currentPosition){
        this.isWhite = isWhite;
        this.currentPosition = currentPosition;
    }

    public Vector2d getPosition() {
        return currentPosition;
    }

    public void setPosition(Vector2d currentPosition) {
        this.currentPosition = currentPosition;
    }
}
