package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * a criterion that is the negation of another criterion
 */
public class NegatedCriterion extends Criterion {
    /**
     * @return string representation of this criterion
     */
    @Override
    public String toString() {
        return "!(" + negatedCriterion.toString() + ")";
    }
    private final Criterion negatedCriterion;

    /**
     * @param name name of the new criterion
     * @param negatedCriterion the negated criterion
     */
    public NegatedCriterion(String name, Criterion negatedCriterion) {
        super(name);
        this.negatedCriterion = negatedCriterion;
    }

    /**
     * @param task the task on which this criterion will apply
     * @return true if and only if the task satisfies this criterion
     */
    @Override
    public boolean evaluate(Task task) {
        return !negatedCriterion.evaluate(task);
    }


}
