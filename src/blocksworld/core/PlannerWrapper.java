package blocksworld.core;

import blocksworld.BlockWorldVariable;
import bwmodel.BWState;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import modelling.Variable;
import planning.*;
import planning.Action;

import javax.swing.*;
import java.util.*;

import static blocksworld.core.MainPlanning.makeBWState;


/**
 * Classe proposant des methodes utilitaires pour lancer un planificateur, afficher les informations et jouer le plan.
 * On utilise cette classe pour lancer les planificateurs dans le main
 *
 * @author :
 * @version 1.0
 */

public class PlannerWrapper {
    private String name;
    private AbstractPlanner planner;
    private Map<Variable, Object> initialState;
    private BlockWorldVariable variable;
    private int n;


    /**
     * Constructeur de la classe PlannerWrapper.
     *
     * @param name          Nom du planificateur.
     * @param initialState  État initial du problème.
     * @param planner       Planificateur à utiliser.
     * @param variable      Variable du monde des blocs utilisée pour la planification.
     * @param n             Nombre de blocs dans le monde des blocs.
     */
    public PlannerWrapper(String name, Map<Variable, Object> initialState , AbstractPlanner planner , BlockWorldVariable variable, int n) {
        this.name = name;
        this.initialState = initialState;
        this.planner = planner;
        this.variable = variable;
        this.n = n;

    }

    /**
     * Lance l'interface graphique, exécute le planificateur, affiche les informations et joue le plan.
     */
    public void run() {
        // Afficher l'interface graphique
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(name);
        frame.setSize(900, 900);
        BWState<Integer> bwState = makeBWState(initialState, variable, n);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);

        // Exécuter le planificateur
        System.out.println("\nPlanificateur: " + name);
        long startTime = System.currentTimeMillis();
        List<Action> plan = planner.plan();
        long endTime = System.currentTimeMillis();



        if(plan == null){
            System.out.println("Pas de plan trouvé");
            return;
        }else {
            // Jouer le plan
            playPlan(plan, initialState, variable, n, name);
            System.out.println("Plan trouvé : " + plan);
            System.out.println("Nombre de noeuds explorés : " + planner.getNodeCount());
            System.out.println("Temps écoulé pour l'exécution du plan : " + (endTime - startTime) + " millisecondes");
        }



    }

    /**
     * Instancie l'état initial à partir de piles de blocs.
     *
     * @param piles Liste des piles de blocs.
     * @param b     Nombre de blocs.
     * @param p     Nombre de piles.
     * @return Map représentant l'état initial.
     */
    public static Map<Variable, Object> instancierEtat(List<ArrayList<Integer>> piles, int b , int p){
        BlockWorldVariable variable = new BlockWorldVariable(b, p);
        Map<Variable, Object> instatiation = new HashMap<Variable, Object>();
        int i = -1;

        for(ArrayList<Integer> block : piles){
            if(block.isEmpty()){
                instatiation.put(variable.getFree(i), true);
            }
            else{
                instatiation.put(variable.getFree(i), false);
                instatiation.put(variable.getOnVariable(block.get(0)), i);
                Collections.reverse(block);
                instatiation.put(variable.getFixed(block.get(0)), false);
                for(int j = 0; j < block.size()-1; j++){
                    instatiation.put(variable.getOnVariable(block.get(j)), block.get(j+1));
                    instatiation.put(variable.getFixed(block.get(j+1)), true);
                }
            }
            i--;
        }
        return instatiation;
    }


    /**
     * Joue un plan en simulant l'évolution de l'état initial dans l'interface graphique.
     *
     * @param plan          Liste d'actions représentant le plan.
     * @param etatInitial   État initial du problème.
     * @param variable      Variable du monde des blocs utilisée pour la planification.
     * @param n             Nombre de blocs dans le monde des blocs.
     * @param title         Titre de l'interface graphique.
     */
    public static void playPlan(List<Action> plan, Map<Variable, Object> etatInitial, BlockWorldVariable variable, int n, String title) {
        Map<Variable, Object> etatInitial1 = new HashMap<>(etatInitial);
        BWIntegerGUI gui1 = new BWIntegerGUI(n);
        JFrame frame1 = new JFrame(title);
        frame1.setSize(700, 700);
        BWState<Integer> bwState = MainPlanning.makeBWState(etatInitial, variable, n);
        BWComponent<Integer> component = gui1.getComponent(bwState);
        frame1.add(component);
        frame1.pack();
        frame1.setVisible(true);

        // Playing plan
        for (Action a: plan) {
            try { Thread.sleep(1_000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            etatInitial1=a.successor(etatInitial1);
            component.setState(MainPlanning.makeBWState(etatInitial1, variable, n));
        }
        System.out.println("Simulation of plan: done.");
    }

    /**
     * Génère des piles de blocs initiales de manière aléatoire.
     *
     * @param nbBlocks Nombre de blocs.
     * @param nbPiles  Nombre de piles.
     * @return Liste des piles de blocs générées aléatoirement.
     */
    public static List<ArrayList<Integer>> generateRandomInitialPiles(int nbBlocks, int nbPiles) {
        List<ArrayList<Integer>> pilesInitiales = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < nbPiles; i++) {
            pilesInitiales.add(new ArrayList<>(nbBlocks));
        }

        for (int i = 0; i < nbBlocks; i++) {
            int pile = random.nextInt(nbPiles);
            pilesInitiales.get(pile).add(i);
        }
        return pilesInitiales;
    }


}