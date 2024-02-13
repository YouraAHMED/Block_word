package blocksworld;

import cp.ValueHeuristic;
import modelling.Variable;

import java.util.*;

public class HeuristicValueCP implements ValueHeuristic {

    private final Random random;



    public HeuristicValueCP(Random random) {
        this.random = random;
    }


    /**
     *Methode permettant d'ordoner les valeurs du domaine d'une variable ici specifiquement au variables "on_b"
     * @param variable
     * @param domaine
     * @return
     */
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> domaineList = new ArrayList<>(domaine);
        Collections.shuffle(domaineList, this.random);

        List<Object> valeur = new ArrayList<>();
        if (!(variable.getName().equals("on_b"))) {
            //si la variable n'est pas une variable "on_b" on retourne le domaine tel quel
            valeur.addAll(domaineList);
        } else {
            //si la variable est une variable "on_b" on retourne le domaine en mettant les valeurs negatives en premier
            for(Object o : domaineList){
                Integer i = (Integer) o;
                if (i < 0) {
                    valeur.add(0 , i);
                }else {
                    valeur.add(i);
                }
            }
        }
        return valeur;
    }

}
