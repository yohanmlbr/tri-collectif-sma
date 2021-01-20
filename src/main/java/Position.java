import lombok.Data;

@Data
public class Position {
    /**
     * Coordonnée x de position dans la grille
     */
    private int x;
    /**
     * Coordonnée y de position dans la grille
     */
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
}
