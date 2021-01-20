public class Main {
    public static void main(String[] args){
        Environnement e = new Environnement();
        Agent a;
        int iteration=0;
        e.display(iteration);
        while(iteration < Constante.NB_ITER){
            for(int i=0;i<Agent.nbTotAgent;i++){
                a=e.pickAgent(i);
                a.percept(e);
                a.action(e);
            }
            iteration++;
        }
        e.display(iteration);
    }
}
