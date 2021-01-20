import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Data
public class Agent {
    /**
     * Compteur d'agents
     */
    public static int nbTotAgent;
    /**
     * Identifiant unique de l'agent
     */
    private int idAgent;
    /**
     * Label de l'objet que l'agent porte
     */
    private String carriedObjectLabel;
    /**
     * Label de l'objet qui se trouve dans la même case que l'agent
     */
    private String onCaseObjectLabel;
    /**
     * Mémoire des derniers objets rencontrés par l'agent
     */
    private String memory;
    /**
     * Map permettant de stocker les objets qui se trouve autour de l'agent en fonctiuon de leur direction
     */
    private Map<Direction, String> directionCaseLabel;
    /**
     * Random commun à tous les agents (même seed)
     */
    private Random random=new Random();

    /**
     * Initialisation de l'agent
     */
    public Agent() {
        random.setSeed(1);
        this.idAgent=nbTotAgent++;
        this.carriedObjectLabel = "0";
        this.onCaseObjectLabel = "";
        this.memory = "";
        this.directionCaseLabel = new HashMap<>();
        for (Direction d : Direction.values()) {
            this.directionCaseLabel.put(d, "0");
        }
    }

    /**
     * Met à jour la mémoire l'agent en fonction du dernier objet rencontré (ou non)
     */
    private void memorize() {
        if (this.memory.length() == Constante.SIZE_MEMORY)
            this.memory = this.memory.substring(1);
        else
            this.memory = this.memory + this.onCaseObjectLabel;
    }

    /**
     * Permet à l'agent de percevoir les objets qui se trouvent autour de lui dans un environnement e dans chacune des directions
     * @param e environnement à percevoir
     */
    public void percept(Environnement e) {
        this.onCaseObjectLabel = e.percept(this, null);
        this.memorize();
        for (Direction d : Direction.values()) {
            this.directionCaseLabel.replace(d, e.percept(this, d));
        }
    }

    /**
     * Permet à l'agent de prendre une décision d'action dans un environnement e (déposer un objet, ramasser un objet, se déplacer)
     * @param e environnement dans lequel agir
     */
    public void action(Environnement e) {
        double f,proba;
        //Random r=new Random();
        double rand=random.nextDouble();

        //do drop?
        if(!this.carriedObjectLabel.equals("0") && this.onCaseObjectLabel.equals("0")){
            f=calculateObjectProp(true);
            proba=Math.pow(f/(Constante.K_MINUS+f),2);
            if(proba>rand){
                e.drop(this);
                this.onCaseObjectLabel=this.carriedObjectLabel;
                this.carriedObjectLabel="0";
            }
        }
        //do carry?
        else if(this.carriedObjectLabel.equals("0") && !this.onCaseObjectLabel.equals("0")){
            f=calculateObjectProp(false);
            proba=Math.pow(Constante.K_PLUS/(Constante.K_PLUS+f),2);
            if(proba>rand){
                e.carry(this);
                this.carriedObjectLabel=this.onCaseObjectLabel;
                this.onCaseObjectLabel="0";
            }
        }
        move(e);
    }

    /**
     * Permet à l'agent de se déplacer dans un environnemlent e
     * @param e environnement dans lequel se déplacer
     */
    public void move(Environnement e) {
        e.moveAgent(this);
    }

    /**
     * Calcul la proportion d'objets autour de l'agent en fonction de l'objet il porte (pour déposer) ou de l'objet sur lequel il se trouve (pour ramasser)
     * @param doDrop boolean sélectionnant la méthode de calcul pour un ramassage ou un dépôt d'objet
     * @return la proportion d'objet
     */
    private double calculateObjectProp(boolean doDrop) {
        double nbSameObject = 0;
        double nbTotal = 0;
        String object;
        if(doDrop)
            object=this.carriedObjectLabel;
        else
            object=onCaseObjectLabel;
        for (Direction d : this.directionCaseLabel.keySet()) {
            if (this.directionCaseLabel.get(d).equals(object)) {
                nbSameObject++;
            }
            nbTotal++;
        }
        return nbSameObject / nbTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return idAgent == agent.idAgent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAgent);
    }

    @Override
    public String toString(){
        return ((idAgent>9) ? "#" + idAgent : "#0" + idAgent);
    }
}
