import lombok.Data;

@Data
public class Case {
    /**
     * Agent qui se trouve dans la case (ou null)
     */
    private Agent agent;
    /**
     * Label de l'objet qui se trouve dans la case (ou null)
     */
    private String objectLabel;

    /**
     * Initialisation d'une case vide
     */
    public Case(){
        this.objectLabel="0";
    }

    @Override
    public String toString() {
        String label;
        if(objectLabel.equals("0"))
            label="   ";
        else
            label=" "+objectLabel+" ";
        //return (this.agent != null) ? label+".": label+" ";
        return (this.agent != null) ? this.agent.toString() : label;
    }
}
