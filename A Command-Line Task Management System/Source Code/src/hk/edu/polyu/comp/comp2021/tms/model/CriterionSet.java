package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * contains and manages the criteria
 */
public class CriterionSet implements Serializable {
    private final HashMap<String, Criterion> criterionSet;

    /**
     * any task with duration > 0 fulfills IsPrimitive
     */
    public CriterionSet() {
        criterionSet = new HashMap<>();
        addCriterion(new BasicCriterion("IsPrimitive", "duration", ">", "0.0"));

    }

    /**
     * @param criterionName is the given criterion name, in work flow it is verified, but this method may be called maliciously
     * @return the reference to the criterion with the given name
     */
    public Criterion getCriterion(String criterionName) {
        if (!Criterion.isValidName(criterionName))  return null;
        else if (!contains(criterionName))          return null;
        return criterionSet.get(criterionName);
    }

    /**
     * @param criterionName is the given criterion name, it may not be a valid name, but this code will work still
     * @return true if and only if there exists a criterion with the given name
     */
    public boolean contains(String criterionName) {
        return criterionSet.containsKey(criterionName);
    }

    /**
     * @return the number of criteria in the set
     */
    public int size(){return criterionSet.size();}

    /**
     * @param criterionName is the given name, to be verified
     * @param property is the given property, to be verified
     * @param op is the given op, not verified
     * @param value is the given value, not verified
     * @return message regarding the execution
     */
    public String defineBasicCriterion(String criterionName, String property, String op, String value) {
        StringBuilder feedback = new StringBuilder();
        if (!Criterion.isValidName(criterionName))              feedback.append("Error. ").append("Invalid criterion name: \"").append(criterionName).append("\"\n");
        else if (contains(criterionName))                       feedback.append("Error. ").append("Criterion name \"").append(criterionName).append("\" has been taken.\n");
        else if (!BasicCriterion.isValidProperty(property))     feedback.append("Error. ").append("Invalid property: \"").append(property).append("\"\n");
        else if (!BasicCriterion.isValidOp(property, op))       feedback.append("Error. ").append("Invalid op: \"").append(op).append("\"\n");
        else if (!BasicCriterion.isValidValue(property, value)) feedback.append("Error. ").append("Invalid value: ").append(value).append("\n");
        else {
            BasicCriterion newCriterion = new BasicCriterion(criterionName, property, op, value);
            addCriterion(newCriterion);
            feedback.append("Done. ").append("Basic Criterion \"").append(criterionName).append("\" created: ")
                    .append(newCriterion).append("\n");
        }
        return feedback.toString();
    }

    /**
     * @param criterionName is the given name for this new criterion
     * @param negatedCriterionName is the name of the negated criterion
     * @return message regarding the execution
     */
    public String defineNegatedCriterion(String criterionName, String negatedCriterionName) {
        StringBuilder feedback = new StringBuilder();
        if (!Criterion.isValidName(criterionName))              feedback.append("Error. ").append("Invalid criterion name: \"").append(criterionName).append("\"\n");
        else if (!Criterion.isValidName(negatedCriterionName))  feedback.append("Error. ").append("Invalid criterion name: \"").append(negatedCriterionName).append("\"\n");
        else if (contains(criterionName))                       feedback.append("Error. ").append("Criterion name \"").append(criterionName).append("\" has been taken.\n");
        else if (!contains(negatedCriterionName))               feedback.append("Error. ").append("Criterion \"").append(negatedCriterionName).append("\" is not defined.\n");
        else {
            NegatedCriterion negatedCriterion = new NegatedCriterion(criterionName, getCriterion(negatedCriterionName));
            addCriterion(negatedCriterion);
            feedback.append("Done. ").append("Negated Criterion \"").append(criterionName).append("\" defined: ")
                    .append(negatedCriterion).append("\n");
        }
        return feedback.toString();
    }


    /**
     * @param criterionName name for the new criterion
     * @param criterionName1 name of one of the two criteria
     * @param logicOp logical operation
     * @param criterionName2 name of the other of the two criteria
     * @return message regarding the execution
     */
    public String defineBinaryCriterion(String criterionName, String criterionName1, String logicOp, String criterionName2) {
        StringBuilder feedback = new StringBuilder();
        if (!Criterion.isValidName(criterionName))          feedback.append("Error. ").append("Invalid criterion name: \"").append(criterionName).append("\"\n");
        else if (!Criterion.isValidName(criterionName1))    feedback.append("Error. ").append("Invalid criterion name: \"").append(criterionName1).append("\"\n");
        else if (!Criterion.isValidName(criterionName2))    feedback.append("Error. ").append("Invalid criterion name: \"").append(criterionName2).append("\"\n");
        else if (contains(criterionName))                   feedback.append("Error. ").append("Criterion name \"").append(criterionName).append("\" has been taken.\n");
        else if (!contains(criterionName1))                 feedback.append("Error. ").append("Criterion \"").append(criterionName1).append("\" is not defined.\n");
        else if (!contains(criterionName2))                 feedback.append("Error. ").append("Criterion \"").append(criterionName2).append("\" is not defined.\n");
        else if (!BinaryCriterion.isValidLogicOp(logicOp))  feedback.append("Error. ").append("Invalid logicOp: ").append(logicOp).append(".\n");
        else{
            BinaryCriterion newCriterion = new BinaryCriterion(criterionName, getCriterion(criterionName1), logicOp, getCriterion(criterionName2));
            addCriterion(newCriterion);
            feedback.append("Done. ").append("Binary Criterion \"").append(criterionName).append("\" created: ").append(newCriterion).append("\n");
        }
        return feedback.toString();
    }

    /**
     * @return all printing information
     */
    public String printAllCriteria() {
        StringBuilder feedback = new StringBuilder();
        Collection<Criterion> result = getCriteria();
        feedback.append(result.size()).append(" defined criteria.\n");
        for (Criterion criterion : result)
            feedback.append(criterion).append("\n");
        return feedback.toString();
    }
    private void addCriterion(Criterion criterion) {
        criterionSet.put(criterion.getName(), criterion);
    }
    private Collection<Criterion> getCriteria(){return criterionSet.values();}

}
