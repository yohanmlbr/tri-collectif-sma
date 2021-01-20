public class Constante {

    //General
    public final static int NB_ITER=1000000; //nombre d'itérations de l'algorithme de tri séléctif

    //Environnement
    public final static int NB_COLONNES=50; //nombre de colonnes de l'environnement
    public final static int NB_LIGNES=50; //nombre de lignes de l'environnement
    public final static int NB_A=200; //nombre d'objets A dans l'environnement
    public final static int NB_B=200; //nombre d'objets B dans l'environnement
    public final static int NB_AGENTS=20; //nombre d'agents dans l'environnement

    //Agent
    public final static int RANGE_AGENT=1; //longueur des deplacement des agents
    public final static int SIZE_MEMORY=10; //taille de la mémoire des agents
    public final static double K_PLUS=0.1; //constante du calcul de la probabilité de ramassage
    public final static double K_MINUS=0.3; //constante du calcul de la probabilité de dépôt

    //2eme implémentation avec taux d'erreur et utilisation de la mémoire des agents
    public final static boolean UEAM =true; //Using Error And Memory, utilisation du mode utilisant la mémoire et le taux d'erreur de reconnaisance d'objets
    public final static double ERROR_RATE=0.15; //variable du taux d'erreur de reconnaissance des objets dans la mémoire
}
