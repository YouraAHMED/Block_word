package core;

import blocksworld.BlockWorldAction;
import blocksworld.BlockWorldVariable;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import modelling.Variable;
import planning.*;
import planning.Action;

import javax.swing.*;
import java.util.*;

public class MainBWAction {
    public static void main(String[] args) {
        final int NOMBREBLOCKS = 6;
        final int NOMBREPILES = 3;
        // Exemple d'état initial
        List<ArrayList<Integer>> pilesInitiales = new ArrayList<>();
        pilesInitiales.add(new ArrayList<>(List.of(0, 1, 2)));  // Pile 1
        pilesInitiales.add(new ArrayList<>(List.of(3, 4)));   // Pile 2
        pilesInitiales.add(new ArrayList<>(List.of(5)));    // Pile 3
        //creation d'un etat initial
        BlockWorldVariable variable = new BlockWorldVariable(NOMBREBLOCKS, NOMBREPILES);
        Map<Variable, Object> etatInitial = instancierEtat(pilesInitiales, NOMBREBLOCKS, NOMBREPILES);
        BlockWorldAction blockWorldAction = new BlockWorldAction(NOMBREBLOCKS,NOMBREPILES);
        Set<Action> actions = blockWorldAction.getAllActions();
        //creation d'un etat partiel
        Map<Variable, Object> instatiationPartiel = new HashMap<>();
        instatiationPartiel.put(variable.getOnVariable(2), 0);
        instatiationPartiel.put(variable.getOnVariable(1), 2);
        instatiationPartiel.put(variable.getOnVariable(4), 5);
        instatiationPartiel.put(variable.getOnVariable(5), 3);
        instatiationPartiel.put(variable.getOnVariable(0), -1);
        instatiationPartiel.put(variable.getOnVariable(3), -2);


        /*instatiationPartiel.put(variable.getOnVariable(1), 0);S
        instatiationPartiel.put(variable.getOnVariable(2), 1);
        instatiationPartiel.put(variable.getOnVariable(3), 4);
        instatiationPartiel.put(variable.getOnVariable(4),5);
        instatiationPartiel.put(variable.getOnVariable(5), -3);*/

        //afficher l'etat initial
        System.out.println("etat initial : "+etatInitial);






        //creation de planer
        //temps d'execution
        Goal etatFinal = new BasicGoal(instatiationPartiel);
        BFSPlanner bfs = new BFSPlanner(etatInitial,actions,etatFinal);
        DFSPlanner dfs = new DFSPlanner(etatInitial,actions,etatFinal);
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInitial,actions,etatFinal);
        //creer une heuristique
        //Heuristic h1 = new HeuristicPlanner(variable,etatInitial);
        //AStarPlanner aStarPlanner = new AStarPlanner(etatInitial,actions,etatFinal,h1);
        //temps d'execution
        // Mesurer le temps avant l'exécution du plan
        //activation de la sonde

        long startTimeDij = System.currentTimeMillis();
        List<Action> dij = dijkstraPlanner.plan();
        long endTimeDij = System.currentTimeMillis();
        long startTimeBfs = System.currentTimeMillis();
        List<Action> bfsAction = bfs.plan();
        long endTimeBfs = System.currentTimeMillis();
        long startTimeDfs = System.currentTimeMillis();
        List<Action> dfsAction = dfs.plan();
        long endTimeDfs = System.currentTimeMillis();
        long startTimeAstar = System.currentTimeMillis();
        //List<Action> astar = aStarPlanner.plan();
        long endTimeAstar = System.currentTimeMillis();
        // Mesurer le temps après l'exécution du plan
        long endTime = System.currentTimeMillis();
        // Calculer la durée totale de l'exécution
        long durationBfs = endTimeBfs - startTimeBfs;
        long durationDfs = endTimeDfs - startTimeDfs;
        long durationDij = endTimeDij - startTimeDij;
        long durationAstar = endTimeAstar - startTimeAstar;
        //affichage du nombre de seconde ecoulé
        System.out.println("Temps écoulé pour l'exécution du plan bfs : " + durationBfs + " millisecondes");
        System.out.println("Temps écoulé pour l'exécution du plan dfs : " + durationDfs + " millisecondes");
        System.out.println("Temps écoulé pour l'exécution du plan dijktra : " + durationDij + " millisecondes");
        System.out.println("Temps écoulé pour l'exécution du plan Astar : " + durationAstar + " millisecondes");

        // Afficher le nombre de nœuds explorés pour chaque algorithme
        System.out.println("Nombre de nœuds explorés par BFS : " + bfs.getNodeCount());
        System.out.println("Nombre de nœuds explorés par DFS : " + dfs.getNodeCount());
        System.out.println("Nombre de nœuds explorés par Dijkstra : " + dijkstraPlanner.getNodeCount());
        //System.out.println("Nombre de nœuds explorés par A* : " + aStarPlanner.getNodeCount());

        BWIntegerGUI gui = new BWIntegerGUI(NOMBREBLOCKS);
        JFrame frame = new JFrame(String.valueOf("Blocksworld"));
        frame.setSize(900,900);
        BWState<Integer> bwState = makeBWState(etatInitial,variable,NOMBREBLOCKS);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Playing plan

        for (Action a: bfsAction) {
            try { Thread.sleep(1_000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            etatInitial=a.successor(etatInitial);
            component.setState(makeBWState(etatInitial,variable,NOMBREBLOCKS));
        }
        System.out.println("Simulation of plan: done.");




    }

    /*public static Map<Variable,Object> creerEtatInitial(List<ArrayList<Integer>> listPiles,int nbreBlocs,int  nbrePiles){
        //initialisation de l'etat initial
        Map<Variable,Object> etatInitial = new HashMap<Variable, Object>();
        BlockWorldVariable blockWorldVariable = new BlockWorldVariable(nbreBlocs,nbrePiles);
        //on parcoure la liste des piles
        int i = -1;
        for(ArrayList<Integer> pile : listPiles){
            //pour chaque pile on verifie si elle est libre
             // toutes les piles prennent des valeurs négatives
            System.out.println("la pile : "+pile);
            if(pile.isEmpty()){
                //si la pile est vide sa variable free est à true
                etatInitial.put(blockWorldVariable.getFree(i),true);

            }else{
                //la pile contient des blocks on recupere les blocks
                etatInitial.put(blockWorldVariable.getFree(i),false) ;//la pile n'est pas libre
                int premierBlock = pile.get(0);//le premier element de chaque pile est sur elle
                etatInitial.put(blockWorldVariable.getOnVariable(premierBlock ),i);
                Collections.reverse(pile);//
                //la variable fixed du dernier element de chaque pile est à false
                etatInitial.put(blockWorldVariable.getFixed(premierBlock),false);


                //traitement des autres elements de la pile
               for(int j=0;j<pile.size()-1;j++){
                    etatInitial.put(blockWorldVariable.getOnVariable(pile.get(j)),pile.get(j+1));
                    etatInitial.put(blockWorldVariable.getFixed(pile.get(j+1)),true);

                }
            }
            i--;

        }
        return etatInitial;
    }*/

    private static Map<Variable, Object> instancierEtat(List<ArrayList<Integer>> piles, int b , int p){
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

    private static BWState<Integer> makeBWState(Map<Variable, Object> etatInitial, BlockWorldVariable variable, int n) {
        // Building state
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Variable onB = variable.getOnVariable(b);
            /*Integer underValue = (Integer) etatInitial.get(onB);
            int under = (underValue != null) ? underValue.intValue() : -1;*/
            int under = (int) etatInitial.get(onB);
            //System.out.println("la variable "+under);
            if (under >= 0) { // if the value is a block (as opposed to a stack)

                builder.setOn(b, under);

            }

        }
        BWState<Integer> state = builder.getState();
        return state;
    }



}