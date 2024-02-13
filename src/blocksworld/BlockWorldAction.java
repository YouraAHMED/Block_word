package blocksworld;

import modelling.BooleanVariable;
import modelling.Variable;
import planning.Action;
import planning.BasicAction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockWorldAction extends BlockWorld{
   private BlockWorldVariable blockWorldVariable;


    /**
     * Constructeur de la classe.
     *
     * @param nombreBlocks Le nombre de blocs dans le monde.
     * @param nombrePiles  Le nombre de piles dans le monde.
     */

    public BlockWorldAction(int nombreBlocks, int nombrePiles) {
        super(nombreBlocks, nombrePiles);
        this.blockWorldVariable = new BlockWorldVariable(nombreBlocks,nombrePiles);
    }
    /**
     * Génère un ensemble d'actions représentant le déplacement d'un bloc du dessus d'un bloc vers le dessus d'un autre bloc.
     * Les actions générées sont soumises à des préconditions spécifiques et ont un coût unitaire de 1.
     *
     * @return Un ensemble d'actions représentant le déplacement de blocs dans le monde des blocs.
     */
    private   Set<Action> moveBlockBtoBSecond(){
        Set<Variable> onVariables = this.blockWorldVariable.getOnVariables();
        Set<BooleanVariable> fixedVariables = this.blockWorldVariable.getFixedVariables();
        Set<Action> actions = new HashSet<>();
       for(Variable blockOnb : onVariables){
           for(Object valeurVariable : blockOnb.getDomain()){
               // on parcoure les blocks qui ne sont pas sur la table d'une pile(les blocks libre)
               if((int) valeurVariable >=0){
                   for (BooleanVariable fixed : fixedVariables){
                       //il faut exclure le block à deplacer
                       if(getIdFromOnVariable(blockOnb) != getIdFromFixedVariable(fixed) && (int) valeurVariable != getIdFromFixedVariable(fixed)){
                         //on initialise la liste des preconditions et des effets
                           Map<Variable,Object> precondiotons = new HashMap<>();
                           Map<Variable,Object> effets = new HashMap<>();
                           //preconditions
                           precondiotons.put(blockOnb,valeurVariable); // le block est posé sur le block numero valeurVariable(premiere condition)
                           //deuxieme precondition le bloc b doit être deplaçable(fixedb)
                           precondiotons.put(this.blockWorldVariable.getFixed(getIdFromOnVariable(blockOnb)),false);
                           precondiotons.put(fixed,false); // troisieme precondition la destination doit etre libre(fixed dans notre cas)
                           //effet
                           //la nouvelle valeur du block sera l'indice de fixed(onb = b'')
                           effets.put(blockOnb,getIdFromFixedVariable(fixed));
                           //la variable fixedvaleurVariable va passer à false
                           effets.put(this.blockWorldVariable.getFixed((int)valeurVariable),false);
                           // la valeur de la variable fixed va passer à true
                           effets.put(fixed,true);

                           //action avec un cout de 1
                           actions.add(new BasicAction(precondiotons,effets,1));

                       }
                   }
               }
           }
       }
       return actions;
    }

    /**
     * Génère un ensemble d'actions pour déplacer un bloc du dessus d'un autre bloc vers une pile libre.
     *
     * @return Un ensemble d'actions de déplacement vers une pile libre.
     */
    private Set<Action> moveBlockBtoPile() {
        // Récupération des variables "on" et "free" du monde des blocs
        Set<Variable> onVariables = this.blockWorldVariable.getOnVariables();
        Set<BooleanVariable> freeVariables = this.blockWorldVariable.getFreeVariables();

        // Ensemble d'actions à retourner
        Set<Action> actions = new HashSet<>();

        // Parcours des variables "on" pour chaque bloc
        for (Variable blockOnb : onVariables) {
            // Parcours du domaine de la variable "on" (positions des blocs)
            for (Object valeurVariable : blockOnb.getDomain()) {
                // Vérification que le bloc est sur un block (et non sur la table)
                if ((int) valeurVariable >= 0) {
                    // Parcours des variables "free" (piles)
                    for (BooleanVariable free : freeVariables) {
                        // Initialisation des préconditions et effets pour chaque action
                        Map<Variable, Object> preconditions = new HashMap<>();
                        Map<Variable, Object> effets = new HashMap<>();

                        // Définition des préconditions
                        preconditions.put(blockOnb, valeurVariable); // Le bloc est posé sur le bloc numéro valeurVariable (première condition)
                        preconditions.put(this.blockWorldVariable.getFixed(getIdFromOnVariable(blockOnb)), false); // Le bloc doit être déplaçable (fixedb)
                        preconditions.put(free, true); // La pile doit être libre (vide)

                        // Définition des effets
                        effets.put(blockOnb, getIdFromFreeVariable(free)); // La nouvelle valeur du bloc sera l'indice de la pile libre (onb = p)
                        effets.put(this.blockWorldVariable.getFixed((int) valeurVariable), false); // La variable fixedvaleurVariable va passer à false
                        effets.put(free, false); // La valeur de la variable free va passer à false

                        // Création d'une action avec un coût de 1
                        actions.add(new BasicAction(preconditions, effets, 1));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Génère un ensemble d'actions pour déplacer un bloc du dessous d'une pile vers le dessus d'un autre bloc ou vers une pile libre.
     *
     * @return Un ensemble d'actions de déplacement d'un block place sur une pile vers un autre bloc.
     */
    private Set<Action> moveBlockBPileToBlockOrPile() {
        Set<Variable> onVariables = this.blockWorldVariable.getOnVariables();
        Set<BooleanVariable> fixedVariables = this.blockWorldVariable.getFixedVariables();
        Set<BooleanVariable> freeVariables = this.blockWorldVariable.getFreeVariables();

        Set<Action> actions = new HashSet<>();

        for (Variable blockOnb : onVariables) {
            for (Object valeurVariable : blockOnb.getDomain()) {
                if ((int) valeurVariable < 0) {
                    actions.addAll(moveBlockToFixed(blockOnb, fixedVariables, valeurVariable));
                    actions.addAll(moveBlockToFree(blockOnb, freeVariables, valeurVariable));
                }
            }
        }

        return actions;
    }

    private Set<Action> moveBlockToFixed(Variable blockOnb, Set<BooleanVariable> fixedVariables, Object valeurVariable) {
        Set<Action> actions = new HashSet<>();

        for (BooleanVariable fixed : fixedVariables) {
            if (getIdFromOnVariable(blockOnb) != getIdFromFixedVariable(fixed)) {
                Map<Variable, Object> preconditions = new HashMap<>();
                Map<Variable, Object> effets = new HashMap<>();

                preconditions.put(blockOnb, valeurVariable);
                preconditions.put(this.blockWorldVariable.getFixed(getIdFromOnVariable(blockOnb)), false);
                preconditions.put(fixed, false);

                effets.put(blockOnb, getIdFromFixedVariable(fixed));
                effets.put(this.blockWorldVariable.getFree((int) valeurVariable), true);
                effets.put(fixed, true);

                actions.add(new BasicAction(preconditions, effets, 1));
            }
        }

        return actions;
    }

    private Set<Action> moveBlockToFree(Variable blockOnb, Set<BooleanVariable> freeVariables, Object valeurVariable) {
        Set<Action> actions = new HashSet<>();

        for (BooleanVariable free : freeVariables) {
            if (getIdFromFreeVariable(free) != (int) valeurVariable) {
                Map<Variable, Object> preconditions = new HashMap<>();
                Map<Variable, Object> effets = new HashMap<>();

                preconditions.put(blockOnb, valeurVariable);
                preconditions.put(this.blockWorldVariable.getFixed(getIdFromOnVariable(blockOnb)), false);
                preconditions.put(free, true);

                effets.put(blockOnb, getIdFromFreeVariable(free));
                effets.put(this.blockWorldVariable.getFree((int) valeurVariable), true);
                effets.put(free, false);

                actions.add(new BasicAction(preconditions, effets, 1));
            }
        }

        return actions;
    }
    /**
     * Cette méthode génère toutes les actions possibles dans le monde des blocs.
     *
     * @return Un ensemble d'actions représentant toutes les combinaisons de déplacements possibles.
     */
    public Set<Action> getAllActions(){
        Set<Action> actions =new HashSet<>();
        actions.addAll(this.moveBlockBtoBSecond());
        actions.addAll(moveBlockBtoPile());
        actions.addAll(moveBlockBPileToBlockOrPile());
        return actions;
    }


}
