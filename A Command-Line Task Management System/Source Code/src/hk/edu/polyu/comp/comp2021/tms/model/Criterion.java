package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.Serializable;

/**
 * a criterion that can tell if a task is desired according to its condition
 */
public abstract class Criterion implements Serializable {
    private final String name;

    /**
     * @param name name of the criterion
     */
    Criterion(String name) {
        this.name = name;
    }

    /**
     * @param task a task
     * @return true iff the task satisfies the condition
     */
    public abstract boolean evaluate(Task task);

    /**
     * @return a string representation of this criterion
     */
    public abstract String toString();
    private static final int NAME_MAX_LENGTH = 8;

    /**
     * @param s a string
     * @return true iff the string is a valid name for a criterion
     * special case when the name is "IsPrimitive", which violates the name rules. so it is specially treated.
     */
    public static boolean isValidName(String s) {
        if (s.equals("IsPrimitive"))    return true;
        if (startsWithDigit(s))
            return false;
        if (s.length() > NAME_MAX_LENGTH)
            return false;
        for (char c : s.toCharArray())
            if (!isLetterOrDigit(c))
                return false;
        return true;
    }
    private static boolean startsWithDigit(String s) {
        return s.charAt(0) >= '0' && s.charAt(0) <= '9';
    }
    /*
     * return true when the given character is an English letter or a digit.
     * otherwise, return false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    /**
     * @return name of the criterion
     */
    public String getName() {
        return name;
    }
}
