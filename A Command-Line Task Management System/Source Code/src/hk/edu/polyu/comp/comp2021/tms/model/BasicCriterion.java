package hk.edu.polyu.comp.comp2021.tms.model;

/**
 * basic criterion which examines a certain property of a task
 */
public class BasicCriterion extends Criterion {
    /**
     * @return a string representation of this criterion
     */
    @Override
    public String toString() {
        if (getName().equals("IsPrimitive"))
            return "IsPrimitive";
        return property + " " + op + " " + getValue();
    }
    private final String property;
    private final String op;
    private final String value;// used for task name and description
    private float realValue;// used for task duration
    private String[] taskNames;// used for task prerequisites or subtasks
    /*
     * validity of these arguments have been checked.
     */

    /**
     * @param name name of the criterion
     * @param property tested property
     * @param op test relationship
     * @param value test value
     */
    public BasicCriterion(String name, String property, String op, String value) {
        super(name);
        this.property = property;
        this.op = op;
        this.value = value;
        realValue = 0;
        switch (property) {
            case "duration":
                realValue = Float.parseFloat(value);
                break;
            case "name":
            case "description":
                //noinspection UnusedAssignment
                value = value.substring(1, value.length() - 1);
                break;
            case "prerequisites":
            case "subtasks":
                taskNames = trim_unique(value).split(",");
                break;
        }
    }

    private String getValue() {
        if (property.equals("name") || property.equals("description"))
            return '"' + value + '"';
        return value;
    }

    /**
     * @param property a valid property
     * @param op a op to verify
     * @return true if and only if the op and property fit
     */
    public static boolean isValidOp(String property, String op) {
        if (property.equals("name") || property.equals("description") || property.equals("prerequisites")
                || property.equals("subtasks"))
            return op.equals("contains");
        if (property.equals("duration"))
            return op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=") || op.equals("==")
                    || op.equals("!=");
        return false;
    }

    /**
     * @param property a valid property
     * @param value a value to verify
     * @return true iff they fit
     */
    public static boolean isValidValue(String property, String value) {
        if (property.equals("name") || property.equals("description")) {
            if (value.length() <= 2)
                return false;
            if (value.charAt(0) != '\"')
                return false;
            if (value.charAt(value.length() - 1) != '\"')
                return false;
            value = value.substring(1, value.length() - 1);
            return property.equals("name") ? Task.isValidName(value) : Task.isValidDescription(value);
        }
        if (property.equals("duration")) {
            try {
                Float.parseFloat(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        if (property.equals("prerequisites") || property.equals("subtasks")) {
            return isValidPrerequisite(value);
        }
        return false;
    }
    /*
     * this is called for criterion on the property prerequisite or subtasks
     */
    private static boolean isValidPrerequisite(String s) {
        if (s.equals(","))
            return true;
        s = trim_unique(s);
        if (s.isEmpty())
            return false;
        String[] names = s.split(",");
        for (String name : names)
            if (!Task.isValidName(name))
                return false;
        return true;
    }

    /**
     * @param task a task
     * @return true iff the task fulfills this criterion
     */
    @Override
    public boolean evaluate(Task task) {
        if (property.equals("duration")) {
            int cmp = Float.compare(task.getDuration(), realValue);
            switch (op) {
                case ">":
                    return cmp > 0;
                case "<":
                    return cmp < 0;
                case "<=":
                    return cmp <= 0;
                case ">=":
                    return cmp >= 0;
                case "==":
                    return cmp == 0;
                case "!=":
                    return cmp != 0;
                default:
                    return false;
            }
        }
        if (property.equals("name"))
            return task.getName().contains(value);
        if (property.equals("description"))
            return task.getDescription().contains(value);
        if (property.equals("prerequisites") || property.equals("subtasks")) {
            for (String taskName : taskNames)
                if (!task.hasPrerequisite(taskName))
                    return false;
            return true;
        }
        return false;
    }



    /**
     * @param s a property to verify
     * @return true iff it is valid
     */
    public static boolean isValidProperty(String s) {
        return s.equals("name") || s.equals("description") || s.equals("duration") || s.equals("prerequisites")
                || s.equals("subtasks");
    }
    /*
     * remove the given character at both end of the given string and replace
     * consecutive duplicates of the character by a single one of it
     */
    private static String trim_unique(String s) {
        int l = 0;
        while (l < s.length() && s.charAt(l) == ',')
            ++l;
        if (l == s.length())
            return "";
        int r = s.length() - 1;
        while (s.charAt(r) == ',')
            --r;
        s = s.substring(l, r + 1);
        while (s.contains("" + ',' + ','))
            s = s.replace("" + ',' + ',', "" + ',');
        return s;
    }
}
