package jumper.map;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;


    public MapDirection next(){
        switch (this)
        {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalStateException("Unexpected value");
        }
    }

    public Vector2d toUnitVector(){
        switch (this)
        {
            case EAST:
                return new Vector2d(1,0);

            case WEST:
                return new Vector2d(-1,0);

            case NORTH:
                return new Vector2d(0,1);

            case SOUTH:
                return new Vector2d(0,-1);

            default:
                return new Vector2d(0,0);

        }

    }

    public static MapDirection intToMapDirection(int that){
        switch (that){
            case 0: return NORTH;
            case 1: return EAST;
            case 2: return SOUTH;
            case 3: return WEST;
            default:
                throw new IllegalStateException("Unexpected value: " + that);
        }
    }

}
