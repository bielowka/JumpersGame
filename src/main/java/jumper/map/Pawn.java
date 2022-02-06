package jumper.map;

import java.lang.ref.PhantomReference;

public class Pawn {
    private boolean isWhite;
    private Vector2d currentPosition;

    public Pawn(boolean isWhite, Vector2d currentPosition){
        this.isWhite = isWhite;
        this.currentPosition = currentPosition;
    }
}
