import lombok.Getter;

@Getter
public enum Direction {
    N(0,-1),
    O(-1,0),
    S(0,1),
    E(1,0);

    /**
     * Coordonnée x de direction
     */
    private int x;
    /**
     * Coordonnée y de direction
     */
    private int y;

    Direction(int x, int y){
        this.x=x;
        this.y=y;
    }
}
