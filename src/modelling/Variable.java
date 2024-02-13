package modelling;

/**
 * Classe permettant de representer une variable
 *
 * @autor : <NumEtu 22209828> SOW Mariama Saoudatou
 * @autor : <NumEtu 22108455> AHMED youra
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Variable {

    /**
     * nom de la variable.
     */

    private final String nom;
    /**
     * Domaine de la variable.
     */
    private final Set<Object> domaine;

    /**
     * Constructeur de la classe Variable.
     *
     * @param nom     nom de la variable
     * @param domaine le domaine de la variable
     */
    public Variable(String nom, Set<Object> domaine) {
        this.nom = nom;
        this.domaine = new HashSet<>(domaine);
    }

    /**
     * on redefine la methode equals pour pouvoir comparer deux variables.
     *
     * @param o objet a comparer
     * @return true si les deux variables sont egales, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Variable) ){
            return false;
        }
        Variable v = (Variable) o;
        return v.nom.equals(this.nom);
    }

    @Override
    public int hashCode() {
        return this.nom.hashCode();
    }

    /**
     * Methode qui permet de recuperer le nom
     *
     * @return le nom de la variable
     */
    public String getName() {
        return this.nom;
    }

    /**
     * Methode qui permet de recuperer le domaine
     *
     * @return le domaine de la variable
     */
    public Set<Object> getDomain() {
        return this.domaine;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "nom='" + nom + '\'' +
                ", domaine=" + domaine +
                '}';
    }

    public void setDomain(List<Boolean> domain) {
        this.domaine.clear();
        this.domaine.addAll(domain);
    }
}
