package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * binary criterion that takes the disjunction or conjunction of two criteria
 */
public class BinaryCriterion extends Criterion {
    /**
     * @return a string representation of this
     */
    @Override
    public String toString() {
        return "(" + criterion1.toString() + ") " + logicOp + " (" + criterion2.toString() + ")";
    }
    private final String logicOp;
    private final Criterion criterion1, criterion2;

    /**
     * @param name name of this criterion
     * @param criterion1 reference of one of the two criteria
     * @param logicOp logical operation
     * @param criterion2 reference of the other of the two criteria
     */
    /*
     * validity of these arguments have been checked.
     */
    public BinaryCriterion(String name, Criterion criterion1, String logicOp, Criterion criterion2) {
        super(name);
        this.criterion1 = criterion1;
        this.criterion2 = criterion2;
        this.logicOp = logicOp;
    }

    /**
     * @param task the task to be tested on this criterion
     * @return true if and only if the task tested true on this criterion
     */
    @Override
    public boolean evaluate(Task task) {
        switch (logicOp) {
            case "&&":
                return criterion1.evaluate(task) && criterion2.evaluate(task);
            case "||":
                return criterion1.evaluate(task) || criterion2.evaluate(task);
            default:
                return false;
        }
    }


    /**
     * @param s a string
     * @return true if and only if the string is either "&&" or "||"
     */
    public static boolean isValidLogicOp(String s) {
        return s.equals("&&") || s.equals("||");
    }
}
