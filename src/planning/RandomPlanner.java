package planning;

/**
 * Cette classe permet de generer un probleme de planification aleatoire
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import modelling.BooleanVariable;
import modelling.Variable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class RandomPlanner {
    /**
     * un generateur de nombre aleatoire
     */
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    /**
     * l'etat initial
     */
    private final Map<Variable, Object> initialState;

    /**
     * l'ensemble des actions possibles
     */
    private final Set<Action> actions;

    /**
     * le but à atteindre
     */
    private final Goal goal;

    /**
     * Constructeur de la classe
     *
     * @param initialState etat initial
     * @param actions      ensemble des actions possibles
     * @param goal         but à atteindre
     */
    public RandomPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
    }

    /**
     * Methode qui permet de generer un probleme de planification aleatoire avec des variables booleennes
     *
     * @param nbVariables
     * @param numberOfActions
     * @return un probleme de planification aleatoire
     */
    public static RandomPlanner generateRandomPlanningProblemBoolean(int nbVariables, int numberOfActions) {
        Set<Variable> variables = generateRandomVariableBoolean(nbVariables);
        Map<Variable, Object> initialState = generateRandomInitialState(variables);
        List<Variable> variableList = new ArrayList<>(variables);
        Set<Action> actions = new HashSet<>();
        Map<Variable, Object> goalState = new HashMap<>(initialState);

        for (int i = 0; i < numberOfActions; i++) {
            Action action = generateRandomAction(goalState, variableList);
            actions.add(action);

            if (random.nextDouble() < 0.33) {
                goalState = action.successor(goalState);

            }
        }

        return new RandomPlanner(initialState, actions, new BasicGoal(goalState));
    }

    /**
     * Methode qui permet de recuperer un element aleatoire dans une liste
     *
     * @param listeElement
     * @param <T>
     * @return
     */
    private static <T> T getRandomElement(List<T> listeElement) {
        if (listeElement == null || listeElement.isEmpty()) {
            throw new IllegalArgumentException("la liste ne doit pas etre vide");
        }
        Collections.shuffle(listeElement);
        return listeElement.get(0);

    }

    /**
     * Methode qui permet de generer un domaine
     *
     * @param domainSize
     * @return
     */
    private static Set<Object> generateDomain(int domainSize) {
        if (domainSize < 0) {
            throw new IllegalArgumentException("la taille du domaine doit etre positive");
        }
        Set<Object> domain = new HashSet<>();
        for (int value = 0; value < domainSize; value++) {
            domain.add(value);
        }
        return domain;
    }

    /**
     * Methode qui permet de generer des variables aleatoires
     *
     * @param Variables
     * @return un ensemble de variables
     */
    public static Set<Variable> generateRandomVariable(int Variables) {
        if (Variables < 0) {
            throw new IllegalArgumentException("le nombre de variables doit etre positif");
        }
        Set<Variable> variables = new HashSet<>();
        for (int i = 0; i < Variables; i++) {
            variables.add(new Variable("V" + i, generateDomain(Variables)));
        }
        return variables;
    }

    /**
     * Methode qui permet de generer des variables booleennes aleatoires
     *
     * @param Variables
     * @return un ensemble de variables booleennes
     */
    public static Set<Variable> generateRandomVariableBoolean(int Variables) {
        if (Variables < 0) {
            throw new IllegalArgumentException("le nombre de variables doit etre positif");
        }
        Set<Variable> variables = new HashSet<>();
        for (int i = 0; i < Variables; i++) {
            variables.add(new BooleanVariable("V" + i));
        }
        return variables;
    }

    /**
     * Methode qui permet de generer des actions aleatoires
     *
     * @param etat
     * @return un ensemble d'actions
     */
    public static BasicAction generateRandomAction(Map<Variable, Object> etat, List<Variable> variables) {
        if (etat == null) {
            throw new IllegalArgumentException("l'etat ne doit pas etre null");
        }

        Map<Variable, Object> preconditions = new HashMap<>();
        Map<Variable, Object> effects = new HashMap<>();

        for (Variable variable : etat.keySet()) {
            if (random.nextBoolean()) {
                preconditions.put(variable, etat.get(variable));
            }
            if (random.nextBoolean()) {
                effects.put(variable, getRandomElement(new ArrayList<>(variable.getDomain())));
            }
            if (random.nextDouble() < 0.2) {
                Variable variable1 = getRandomElement(variables);
                effects.put(variable1, getRandomElement(new ArrayList<>(variable1.getDomain())));

            }
        }

        return new BasicAction(preconditions, effects, random.nextInt(10));
    }

    /**
     * Methode qui permet de generer un etat initial aleatoire
     *
     * @param variables
     * @return un etat initial
     */
    private static Map<Variable, Object> generateRandomInitialState(Set<Variable> variables) {
        Map<Variable, Object> initialState = new HashMap<>();
        for (Variable variable : variables) {
            if (random.nextBoolean()) {
                initialState.put(variable, random.nextBoolean());
            }
        }
        return initialState;
    }

    /**
     * Methode qui permet de generer un etat initial aleatoire avec comme domaine des entiers
     *
     * @param variables
     * @return un etat initial
     */
    private static Map<Variable, Object> generateRandomInitialStateInt(Set<Variable> variables) {
        Map<Variable, Object> initialState = new HashMap<>();
        for (Variable variable : variables) {
            if (random.nextBoolean()) {
                initialState.put(variable, random.nextInt(10));
            }
        }
        return initialState;
    }

    @Override
    public String toString() {
        return "RandomPlanner{" +
                "initialState=" + initialState +
                ", actions=" + actions +
                ", goal=" + goal +
                '}';
    }

    /**
     * Methode qui permet de recuperer l'etat initial
     *
     * @return l'etat initial
     */

    public Map<Variable, Object> getInitialState() {
        return initialState;
    }

    /**
     * Methode qui permet de recuperer les actions
     *
     * @return les actions
     */

    public Set<Action> getActions() {
        return actions;
    }

    /**
     * Methode qui permet de recuperer le but
     *
     * @return le but
     */

    public Goal getGoal() {
        return goal;
    }
}

