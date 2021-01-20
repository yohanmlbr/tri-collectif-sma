public class Main {
    public static void main(String[] args){
        Environnement e = new Environnement();
        Agent a;
        int iteration=0;
        e.display(iteration); //affichage initial
        /**
         * Pour chaque itération, on séléctionne tout les agents un par un, et on les fait percevoir et agir dans l'environnement
         */
        while(iteration < Constante.NB_ITER){
            for(int i=0;i<Agent.nbTotAgent;i++){
                a=e.pickAgent(i);
                a.percept(e);
                a.action(e);
            }
            iteration++;
        }
        e.display(iteration); //affichage final
    }
}
