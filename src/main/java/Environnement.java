import java.util.*;

public class Environnement {
    /**
     * Grille de l'environnement contenant les cases
     */
    private List<List<Case>> grille;
    /**
     * Map permettant de stocker les positions des agents dans l'environnement
     */
    private Map<Agent, Position> agentPositions;

    /**
     * Initialise l'environemment : la grille et ses composants (agents, objets A, objets B)
     */
    public Environnement(){
        this.grille=new ArrayList<>();
        this.agentPositions = new HashMap<>();

        for(int i = 0; i< Constante.NB_COLONNES; i++){
            List<Case> list=new ArrayList<>();
            for(int j=0;j<Constante.NB_LIGNES;j++){
                list.add(new Case());
            }
            this.grille.add(list);
        }

        for(int i=0;i<Constante.NB_AGENTS;i++){
            Random rand = new Random();
            boolean done=false;
            while(!done){
                int x = rand.nextInt(Constante.NB_COLONNES);
                int y = rand.nextInt(Constante.NB_LIGNES);
                if(this.grille.get(x).get(y).getObjectLabel().equals("0")){
                    Agent a=new Agent();
                    this.grille.get(x).get(y).setAgent(a);
                    this.agentPositions.put(a, new Position(x,y));
                    done=true;
                }
            }

        }

        for(int i=0;i<Constante.NB_A;i++){
            Random rand = new Random();
            boolean done=false;
            while(!done){
                int x = rand.nextInt(Constante.NB_COLONNES);
                int y = rand.nextInt(Constante.NB_LIGNES);
                if(this.grille.get(x).get(y).getObjectLabel().equals("0")){
                    this.grille.get(x).get(y).setObjectLabel("A");
                    done=true;
                }
            }

        }

        for(int i=0;i<Constante.NB_B;i++){
            Random rand = new Random();
            boolean done=false;
            while(!done){
                int x = rand.nextInt(Constante.NB_COLONNES);
                int y = rand.nextInt(Constante.NB_LIGNES);
                if(this.grille.get(x).get(y).getObjectLabel().equals("0")){
                    this.grille.get(x).get(y).setObjectLabel("B");
                    done=true;
                }
            }

        }
    }

    /**
     * Permet à un agent de percevoir le contenu d'une case dans une direction
     * @param a agent qui percoit l'environnement
     * @param d direction choisie
     * @return le contenu de la case dans la direction d de l'agent a
     */
    public String percept(Agent a, Direction d) {
        Position p = this.agentPositions.get(a);
        int x = p.getX();
        int y = p.getY();

        if (d != null) {
            x = x + d.getX();
            y = y + d.getY();
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            if (x >= Constante.NB_COLONNES) x = Constante.NB_COLONNES - 1;
            if (y >= Constante.NB_LIGNES) y = Constante.NB_LIGNES - 1;
        }

        return this.grille.get(x).get(y).getObjectLabel();
    }

    /**
     * Séléctionne aléatoirement un agent
     * @return
     */
    public Agent pickRandomAgent(){
        List<Agent>agents=new ArrayList<>(this.agentPositions.keySet());
        Random r=new Random();
        return agents.get(r.nextInt(agents.size()));
    }

    /**
     * Séléctionne un agent avec son id
     * @param id identifiant de l'agent
     * @return l'agent
     */
    public Agent pickAgent(int id){
        List<Agent>agents=new ArrayList<>(this.agentPositions.keySet());
        return agents.get(id);
    }

    /**
     * Déplace un agent a dans la grille d'une position à une autre de manière aléatoire
     * @param a agent à déplacer
     */
    public void moveAgent(Agent a){
        Position p = this.agentPositions.get(a);
        int x = p.getX();
        int y = p.getY();
        this.grille.get(x).get(y).setAgent(null);

        int range=Constante.RANGE_AGENT;
        List<Direction> directions= Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        Iterator<Direction> iteratorD = directions.iterator();

        boolean retry;
        int newX,newY;
        do {
            Direction d=null;
            try {
                d=iteratorD.next();
            }catch(java.util.NoSuchElementException e){
                newX=x;
                newY=y;
                break;
            }
            retry=false;

            newX = x + (d.getX() * range);
            if (newX >= Constante.NB_COLONNES)
                newX = 0;
            else if (newX < 0)
                newX = Constante.NB_COLONNES - 1;

            newY = y + (d.getY() * range);
            if (newY >= Constante.NB_LIGNES)
                newY = 0;
            else if (newY < 0)
                newY = Constante.NB_LIGNES - 1;

            if(this.grille.get(newX).get(newY).getAgent()!=null){
                retry=true;
            }
        }while(retry);

        p.setX(newX);
        p.setY(newY);
        this.agentPositions.replace(a,p);
        this.grille.get(p.getX()).get(p.getY()).setAgent(a);
    }

    /**
     * Dépose l'objet qu'un agent a est en train de porter dans la case où il se trouve
     * @param a agent qui dépose l'objet
     */
    public void drop(Agent a){
        Position p=this.agentPositions.get(a);
        this.grille.get(p.getX()).get(p.getY()).setObjectLabel(a.getCarriedObjectLabel());
    }

    /**
     * Ramasse l'objet qui se trouve sur la case d'un agent a
     * @param a agent qui ramasse l'objet
     */
    public void carry(Agent a){
        Position p=this.agentPositions.get(a);
        this.grille.get(p.getX()).get(p.getY()).setObjectLabel("0");
    }

    /**
     * Affiche la grille dans la console avec ses composants dans chaque case
     * @param iteration identifiant de l'itération de l'algorithme du tri séléctif pour apporter une précision à l'affichage
     */
    public void display(int iteration){
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < Constante.NB_COLONNES; i++) {
            separator.append("_____");
        }

        System.out.println("Itération #"+iteration);
        System.out.println();
        for(int i=0;i<Constante.NB_COLONNES;i++){
            StringBuilder line= new StringBuilder();
            for(int j=0;j<Constante.NB_LIGNES;j++){
                line.append("[").append(this.grille.get(i).get(j).toString()).append("]");
            }
            System.out.println(line);
        }
        System.out.println(separator);
    }
}
