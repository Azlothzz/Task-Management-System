package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * a task
 */
public class Task implements Serializable {
    private float duration;
    private String name;
    private String description;
    private HashMap<String, Task> prerequisites;

    /**
     * @param name name of the task
     * @param description description of the task
     * @param duration duration of the task
     * @param prerequisites prerequisites of the task
     */
    public Task(String name, String description, float duration, HashMap<String, Task> prerequisites) {
        setName(name);
        setDescription(description);
        setDuration(duration);
        setPrerequisites(prerequisites);
    }

    /**
     * @param prerequisite a task that is a prerequisite of this task
     * @param newName the new name that the task is going to be
     */
    public void ChangePrerequisiteName(Task prerequisite, String newName){
        if (!prerequisites.containsValue(prerequisite)) return;
        prerequisites.remove(prerequisite.getName(), prerequisite);
        prerequisites.put(newName, prerequisite);
    }

    /**
     * @return name of this task
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new name of this task
     */
    // does not check if the new name is legal
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return duration of this task
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @param duration new duration of this task
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * @return description of this task
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description new description of this task
     */
    // does not check if the new description is legal
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return a collection of references to prerequisites of this task
     */
    public Collection<Task> getPrerequisites() {return prerequisites.values();}

    /**
     * @param prerequisites the new prerequisites of this task
     */
    public void setPrerequisites(HashMap<String, Task> prerequisites) {this.prerequisites = prerequisites;}

    /**
     * @return true if and only if this task is a primitive task
     */
    public boolean isPrimitive() {return duration > 0.0F;}
    /**
     * @return true if and only if this task is a composite task
     */
    public boolean isCompositeTask() {return duration == 0.0F;}

    /**
     * @param task a given task to be checked if is a prerequisite of this task
     * @return true if and only if the given task is a prerequisite of this task
     */
    public boolean hasPrerequisite(Task task) {
        return prerequisites.containsValue(task);
    }

    /**
     * @param taskName name of a task to be checked if is a prerequisite of this task
     * @return true if and only if the given task is a prerequisite of this task
     */
    public boolean hasPrerequisite(String taskName) {
        return prerequisites.containsKey(taskName);
    }

    /**
     * @return multiple lines of texts of this task's information
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Task Name: ").append(getName()).append("\n");
        builder.append("         ").append("Description: ").append(getDescription()).append("\n");
        builder.append("         ").append("Duration: ").append(getDuration()).append("\n");

        if (isPrimitive())
            builder.append("         ").append("Prerequisites: ");
        else
            builder.append("         ").append("Sub-tasks: ");

        for (Task prerequisite : getPrerequisites())
            builder.append(prerequisite.getName()).append(" ");
        builder.append("\n");
        return builder.toString();
    }

    /**
     * @param s a string
     * @return if and only if the given string is a valid task description
     */
    public static boolean isValidDescription(String s) {
        for (char c : s.toCharArray())
            if (!isLetterOrDigit(c) && c != '-')
                return false;
        return true;
    }

    /**
     * @param s a string
     * @return if and only if the given string represents a valid positive float number
     */
    public static boolean isValidDuration(String s) {
        float duration;
        try {
            duration = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return isValidDuration(duration);
    }

    /**
     * @param duration  a float
     * @return if and only if the float is positive
     */
    public static boolean isValidDuration(float duration) {
        return duration > 0;
    }
    private static final int NAME_MAX_LENGTH = 8;

    /**
     * @param s a string
     * @return if and only if the string is a valid task name
     * the string must not start with a digit
     * the string must not be longer than 8 characters
     * the string must only contain English letters and digits
     */
    public static boolean isValidName(String s) {
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

}
