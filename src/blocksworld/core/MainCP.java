package blocksworld.core;

import blocksworld.*;
import cp.*;
import modelling.Constraint;
import modelling.Variable;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainCP {

    public static void main(String[] args) {
        // Définir le nombre de blocs et de piles
        int nombreBlocks = 10;
        int nombrePiles = 5;

        // Créer une instance du monde des blocs avec des contraintes de régularité
        BlockWorldVariable variable = new BlockWorldVariable(nombreBlocks, nombrePiles);
        ConstraintesRegularity regularityConstraints = new ConstraintesRegularity(nombreBlocks, nombrePiles);
        Set<Constraint> regularityConstraintsSet = regularityConstraints.getRegularityConstraints();

        CroissanteConstraintes croissanteConstraintes = new CroissanteConstraintes(nombreBlocks, nombrePiles);
        Set<Constraint> croissanceConstraintsSet = croissanteConstraintes.getCroissanceConstraint();

        Set<Constraint> allConstraints = new HashSet<>();
        allConstraints.addAll(regularityConstraintsSet);
        allConstraints.addAll(croissanceConstraintsSet);

        // Afficher les contraintes
        System.out.println("Contraintes de régularité:");
        for (Constraint constraint : allConstraints) {
            System.out.println(constraint);
        }

        // Créer une instance de solveur par backtrack
        AbstractSolver backtrackSolver = new BacktrackSolver(variable.getVariables(), allConstraints);
        long startTimeBacktrack = System.currentTimeMillis();
        Map<Variable, Object> solutionBacktrack = backtrackSolver.solve();
        long endTimeBacktrack = System.currentTimeMillis();

        // Afficher le résultat du solveur par backtrack
        System.out.println("Résultat du solveur par backtrack:");
        displaySolution(solutionBacktrack);
        displayTime(startTimeBacktrack, endTimeBacktrack);

        // Créer une instance de solveur MAC
        AbstractSolver macSolver = new MACSolver(variable.getVariables(), allConstraints);
        long startTimeMAC = System.currentTimeMillis();
        Map<Variable, Object> solutionMAC = macSolver.solve();
        long endTimeMAC = System.currentTimeMillis();

        // Afficher le résultat du solveur MAC
        System.out.println("Résultat du solveur MAC:");
        displaySolution(solutionMAC);
        displayTime(startTimeMAC, endTimeMAC);

        // Créer une instance de solveur MAC avec heuristique
        HeuristicValueCP heuristicValueCP = new HeuristicValueCP(new Random());
        AbstractSolver macSolverHeuristicBW = new HeuristicMACSolver(variable.getVariables(), allConstraints, new DomainSizeVariableHeuristic(true), heuristicValueCP);
        long startTimeMACHeuristic = System.currentTimeMillis();
        Map<Variable, Object> solutionMACHeuristic = macSolverHeuristicBW.solve();
        long endTimeMACHeuristic = System.currentTimeMillis();

        // Afficher le résultat du solveur MAC
        System.out.println("Résultat du solveur MAC avec heuristique:");
        displaySolution(solutionMACHeuristic);
        displayTime(startTimeMACHeuristic, endTimeMACHeuristic);

        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        AbstractSolver macSolverHeuristic = new HeuristicMACSolver(variable.getVariables(), allConstraints, new DomainSizeVariableHeuristic(true), valueHeuristic);
        long startTimeMACHeuristic1 = System.currentTimeMillis();
        Map<Variable, Object> solutionMACHeuristic1 = macSolverHeuristic.solve();
        long endTimeMACHeuristic1 = System.currentTimeMillis();

        // Afficher le résultat du solveur MAC
        System.out.println("Résultat du solveur MAC le RandomValueHeuristic:");
        displaySolution(solutionMACHeuristic1);
        displayTime(startTimeMACHeuristic1, endTimeMACHeuristic1);


        ValueHeuristic valueHeuristic1 = new RandomValueHeuristic(new Random());
        AbstractSolver macSolverHeuristic1 = new HeuristicMACSolver(variable.getVariables(), allConstraints, new DomainSizeVariableHeuristic(false), valueHeuristic1);
        long startTimeMACHeuristic11 = System.currentTimeMillis();
        Map<Variable, Object> solutionMACHeuristic11 = macSolverHeuristic1.solve();
        long endTimeMACHeuristic11 = System.currentTimeMillis();

        // Afficher le résultat du solveur MAC
        System.out.println("Résultat du solveur MAC le RandomValueHeuristic:");
        displaySolution(solutionMACHeuristic11);
        displayTime(startTimeMACHeuristic11, endTimeMACHeuristic11);




    }

    // Méthode pour afficher la solution
    private static void displaySolution(Map<Variable, Object> solution) {
        if (solution != null) {
            System.out.println("Solution trouvée:");
            System.out.println(solution);
        } else {
            System.out.println("Aucune solution trouvée.");
        }
    }

    // Méthode pour afficher le temps d'exécution
    private static void displayTime(long startTime, long endTime) {
        System.out.println("Temps d'exécution : " + (endTime - startTime) + " millisecondes\n");
    }
}
