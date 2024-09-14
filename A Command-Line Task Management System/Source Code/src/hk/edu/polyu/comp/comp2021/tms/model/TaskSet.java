package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.Serializable;
import java.util.*;

/**
 * contain, create, delete, modify tasks
 */
public class TaskSet implements Serializable {
    private final HashMap<String, Task> taskSet;

    /**
     * instantiate a taskSet
     */
    public TaskSet() {taskSet = new HashMap<>();}

    /**
     * @param taskName a task name
     * @return true if and only if there is a task this taskSet with this name
     */
    private boolean containsTask(String taskName) {return taskSet.containsKey(taskName);}

    /**
     * @param task a task
     * @return true if and only if this taskSet contains this task
     */
    private boolean containsTask(Task task) {return taskSet.containsValue(task);}
    private void addTask(Task task) {taskSet.put(task.getName(), task);}
    private Task getTask(String taskName) {return taskSet.get(taskName);}
    private Collection<Task> getTaskCollection(){return taskSet.values();}

    /**
     * @return number of tasks stored in this taskSet
     */
    public int size(){return taskSet.size();}

    /**
     * @param taskName name of the new primitive task
     * @param description description of it
     * @param duration duration of it
     * @param prerequisites prerequisite of it
     * @return execution message
     * all arguments are not verified
     */
    public String createPrimitiveTask(String taskName, String description, String duration, String prerequisites) {
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (containsTask(taskName))                feedback.append("Error. ").append("Task name \"").append(taskName).append("\" has been taken.\n");
        else if (!Task.isValidDescription(description)) feedback.append("Error. ").append("Invalid characters in description.\n");
        else if (!Task.isValidDuration(duration))       feedback.append("Error. ").append("Invalid duration.\n");
        else if (!isValidPrerequisites(prerequisites))  feedback.append("Error. ").append("Invalid prerequisites.\n");
        else{
            Task newTask = new Task(taskName, description, Float.parseFloat(duration), getPrerequisites(prerequisites));
            addTask(newTask);
            feedback.append("Done. ").append("Primitive Task \"").append(taskName).append("\" created.\n").append(newTask);
        }
        return feedback.toString();
    }

    /**
     * @param taskName name of the new task
     * @param description description of the new task
     * @param prerequisites subtasks of the new task, in string form
     * @return execution message
     */
    public String createCompositeTask(String taskName, String description, String prerequisites) {
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (containsTask(taskName))                feedback.append("Error. ").append("Task name \"").append(taskName).append("\" has been taken.\n");
        else if (!Task.isValidDescription(description)) feedback.append("Error. ").append("Invalid characters in description.\n");
        else if (!isValidPrerequisites(prerequisites))  feedback.append("Error. ").append("Invalid prerequisites.\n");
        else
        {
            Task newTask = new Task(taskName, description, 0.0F, getPrerequisites(prerequisites));
            addTask(newTask);
            feedback.append("Done. ").append("Composite Task \"").append(taskName).append("\" created.\n").append(newTask);
        }
        return feedback.toString();
    }

    /**
     * @param taskName name of the task to be deleted
     * @return execution message
     */
    /*
     * Deleting a primitive task is just deleting it.
     * Deleting a composite task is to delete all its direct sub-tasks and itself. (Be careful if it has a composite sub-task, then we'll try to delete the sub-sub-tasks.)
     * A task is deletable if and only if deleting it would not result in any incompletable remaining task.
     * failure cases:
     * deleting a task that is not in the task list
     * deleting an undeletable task
     * deleting a task that is a prerequisite of another task
     * deleting a composite task having a sub-task that is a prerequisite of another
     * task
     */
    public String deleteTask(String taskName) {
        assert taskName != null;
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (!containsTask(taskName))               feedback.append("Error. ").append("Task \"").append(taskName).append("\" is not defined.");
        else                                            feedback.append(deleteTask(getTask(taskName)));
        return feedback.toString();
    }
    private String deleteTask(Task task) {
        assert task != null;
        assert containsTask(task);
        StringBuilder feedback = new StringBuilder();
        ArrayList<Task> deletedTasks = new ArrayList<>();
        getDeletedTasks(task, deletedTasks);
        for (Task other : getTaskCollection()){
            if (!deletedTasks.contains(other)){
                for (Task otherSub : other.getPrerequisites()){
                    if (deletedTasks.contains(otherSub)) {
                        feedback.append("Error. ").append("Task \"").append(task.getName()).append("\" is not deletable.");
                        return feedback.toString();
                    }
                }
            }
        }
        feedback.append("Done. \n");
        for (Task deletedTask : deletedTasks){
            taskSet.remove(deletedTask.getName(), deletedTask);
            feedback.append("Task \"").append(deletedTask.getName()).append("\" is deleted.\n");
        }
        return feedback.toString();
    }
    private void getDeletedTasks(Task task, Collection<Task> deletedTasks){
        deletedTasks.add(task);
        if (task.isPrimitive()) return;
        for (Task sub : task.getPrerequisites())
            if (!deletedTasks.contains(sub)){
                getDeletedTasks(sub, deletedTasks);
            }
    }

    /**
     * @param taskName name of the task to have its property changed
     * @param property the property to change
     * @param newValue new value for the property
     * @return execution message
     */
    /*
     * when a task get its name changed, the task is deemed unchanged, and all
     * relationships related to it should remain
     */
    public String ChangeTask(String taskName, String property, String newValue){
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                    feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (!containsTask(taskName))                   feedback.append("Error. ").append("Task \"").append(taskName).append("\" is not defined.");
        else if (!isValidTaskProperty(taskName, property))  feedback.append("Error. ").append("Invalid property: \"").append(property).append("\"\n");
        else if (!isValidNewValue(property, newValue))      feedback.append("Error. ").append("Invalid newValue: ").append(newValue).append("\n");
        else                                                feedback.append(ChangeTask(getTask(taskName), property, newValue));
        return feedback.toString();
    }
    private String ChangeTask(Task task, String property, String newValue){
        StringBuilder feedback = new StringBuilder();
        if (property.equals("name")) {
            if (newValue.equals(task.getName()))    feedback.append("Error. ").append("New name is the same as current name.\n");
            else if (containsTask(newValue))        feedback.append("Error. ").append("New task name \"").append(newValue).append("\" has been taken.\n");
            else feedback.append(ChangeTaskName(task, newValue));
            return feedback.toString();
        }
        if (property.equals("description")) {
            task.setDescription(newValue);
            feedback.append("Done. ").append("Task \"").append(task.getName()).append("\" changed description into \"").append(newValue).append("\"\n");
            return feedback.toString();
        }
        if (property.equals("duration")) {
            float duration = Float.parseFloat(newValue);
            task.setDuration(duration);
            feedback.append("Done. ").append("Task \"").append(task.getName()).append("\" changed duration into ").append(duration).append("\n");
            return feedback.toString();
        }
        if (property.equals("prerequisites") || property.equals("subtasks")) {
            if (property.equals("subtasks") && newValue.equals(","))    feedback.append("Error. ").append("A composite task without subtasks is not allowed.\n");
            else feedback.append(ChangeTaskPrerequisites(task, newValue));
            return feedback.toString();
        }
        feedback.append("Error. ").append("Unknown error in ChangeTask.\n");
        return feedback.toString();
    }
    private String ChangeTaskName(Task task, String newName){
        StringBuilder feedback = new StringBuilder();
        String oldName = task.getName();
        taskSet.remove(oldName, task);
        for (Task otherTask : getTaskCollection())
            if (otherTask.hasPrerequisite(task))
                otherTask.ChangePrerequisiteName(task, newName);
        task.setName(newName);
        taskSet.put(newName, task);
        feedback.append("Done. ").append("Task \"").append(oldName).append("\" changes name into \"").append(newName).append("\"\n");
        return feedback.toString();
    }
    private String ChangeTaskPrerequisites(Task task, String newPrerequisites){
        StringBuilder feedback = new StringBuilder();
        if (loopCreated(task, Objects.requireNonNull(getPrerequisites(newPrerequisites)).values())) feedback.append("Error. ").append("Change causing incompletable tasks is not allowed.\n");
        else {task.setPrerequisites(getPrerequisites(newPrerequisites));    feedback.append("Done. ");}
        return feedback.toString();
    }
    private boolean loopCreated(Task startTask, Collection<Task> queue) {
        if (queue.contains(startTask))  return true;
        ArrayList<Task> inQueue = new ArrayList<>();
        inQueue.add(startTask);
        inQueue.addAll(queue);
        while (!queue.isEmpty()) {
            //there is an unchecked cast here, but it still works
            Task now = (Task) (queue.toArray())[0];
            queue.remove(now);
            for (Task next : now.getPrerequisites()) {
                if (next == startTask)  return true;
                if (inQueue.contains(next))continue;
                inQueue.add(next);
                queue.add(next);
            }
        }
        return false;
    }

    /**
     * @param taskName name of the task to be printed
     * @return execution message
     * the name is not verified
     */
    public String printTask(String taskName){
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                    feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (!containsTask(taskName))                   feedback.append("Error. ").append("Task \"").append(taskName).append("\" is not defined.");
        else feedback.append(getTask(taskName));
        return feedback.toString();
    }

    /**
     * @return execution message
     */
    public String printAllTasks() {
        StringBuilder feedback = new StringBuilder();
        Collection<Task> results = getTaskCollection();
        feedback.append(results.size()).append(" defined tasks.\n");
        for (Task task : results)
            feedback.append(task);
        return feedback.toString();
    }

    /**
     * @param taskName name of the task to be reported its duration
     * @return execution message
     */
    public String reportDuration(String taskName){
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                    feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (!containsTask(taskName))                   feedback.append("Error. ").append("Task \"").append(taskName).append("\" is not defined.");
        else if (getTask(taskName).isPrimitive())           feedback.append("The duration of task \"").append(taskName).append("\" is ").append(getTask(taskName).getDuration()).append(" hours.\n");
        else feedback.append("The duration of task \"").append(taskName).append("\" is ").append(calculateFinishTime(getTask(taskName))).append(" hours.\n");
        return feedback.toString();
    }

    /**
     * @param taskName name of the task to be reported its minimum finish time
     * @return execution message
     */
    public String reportEarliestFinishTime(String taskName){
        StringBuilder feedback = new StringBuilder();
        if (!Task.isValidName(taskName))                    feedback.append("Error. ").append("Invalid task name: \"").append(taskName).append("\".\n");
        else if (!containsTask(taskName))                   feedback.append("Error. ").append("Task \"").append(taskName).append("\" is not defined.");
        else feedback.append("Starting at 0.0 hour, the earliest finish time of task \"").append(taskName)
                    .append("\" is ").append(calculateFinishTime(getTask(taskName))).append(" hour.\n");
        return feedback.toString();
    }
    private HashMap<Task, Node> taskToNode;
    private PriorityQueue<FinishTime> doingTask;
    private float currentTime;
    private class FinishTime implements Comparable<FinishTime> {
        final Float finishTime;
        final Node node;

        public FinishTime(Float finishTime, Node node) {
            this.finishTime = finishTime;
            this.node = node;
        }

        @Override
        public int compareTo(FinishTime o) {
            return this.finishTime.compareTo(o.finishTime);
        }
    }
    private class Node implements Serializable {
        final Task task;
        final ArrayList<Node> parents;
        final ArrayList<Node> children;
        public Node(Task task) {
            this.task = task;
            parents = new ArrayList<>();
            children = new ArrayList<>();
        }
        public void addParent(Node node) {
            this.parents.add(node);
        }
        public void addChild(Node node) {
            this.children.add(node);
        }
        public void deleteChild(Node node) {
            this.children.remove(node);
        }
        public void tryStart() {
            if (!this.children.isEmpty())
                return;
            doingTask.add(new FinishTime(currentTime + this.task.getDuration(), this));
        }
        /*
         * a node notifies its parents when and only when the task it represents is done
         * notified parents shall first delete this child, and then try if they can
         * start doing.
         */
        public void notifyParent() {
            for (Node p : parents) {
                p.deleteChild(this);
                p.tryStart();
            }
        }
    }
    private float calculateFinishTime(Task task) {
        currentTime = 0.0F;
        taskToNode = new HashMap<>();
        doingTask = new PriorityQueue<>();
        Node root = getNode(task);
        build(root);
        for (Node node : taskToNode.values())
            node.tryStart();
        while (!doingTask.isEmpty()) {
            FinishTime now = doingTask.poll();
            currentTime = now.finishTime;
            now.node.notifyParent();
        }
        return currentTime;
    }
    private Node getNode(Task task) {
        if (!taskToNode.containsKey(task))
            taskToNode.put(task, new Node(task));
        return taskToNode.get(task);
    }
    private void build(Node now) {
        Task task = now.task;
        for (Task subtask : task.getPrerequisites()) {
            Node next = getNode(subtask);
            next.addParent(now);
            now.addChild(next);
            build(next);
        }
    }
    private boolean isValidNewValue(String property, String newValue) {
        switch (property) {
            case "name":
                return Task.isValidName(newValue);
            case "description":
                return Task.isValidDescription(newValue);
            case "duration":
                return Task.isValidDuration(newValue);
            case "prerequisites":
            case "subtasks":
                return isValidPrerequisites(newValue);
            default:
                return false;
        }
    }
    private HashMap<String, Task> getPrerequisites(String s) {
        if (!isValidPrerequisites(s))
            return null;
        HashMap<String, Task> res = new HashMap<>();
        if (s.equals(","))
            return res;
        s = trim_unique(s);
        String[] names = s.split(",");
        for (String name : names)
            if (!res.containsKey(name))
                res.put(name, getTask(name));
        return res;
    }

    /**
     * @param criterion the criterion with which we filter the tasks
     * @return execution message
     */
    public String search(Criterion criterion) {
        StringBuilder feedback = new StringBuilder();
        ArrayList<Task> desiredTask = new ArrayList<>();
        for (Task task : getTaskCollection())
            if (criterion.evaluate(task))
                desiredTask.add(task);
        feedback.append("Search result: ").append(desiredTask.size()).append(" tasks:\n");
        for (Task task : desiredTask)
            feedback.append(task);
        return feedback.toString();
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
    /*
     * tells if a given string represents a valid prerequisite with respect to the
     * current existing tasks
     * the string can only contain task names and commas
     * duplicate commas will be ignored
     * empty list must be denoted by ","
     * list with only one name can have no commas
     * duplicate task names are allowed in the string, and they will count as only
     * one occurrence, so that the task will have only one of it as its prerequisite
     */
    private boolean isValidPrerequisites(String s) {
        if (s.equals(","))
            return true;
        s = trim_unique(s);
        if (s.isEmpty())
            return false;
        for (String name : s.split(","))
            if (!Task.isValidName(name) || !containsTask(name))
                return false;
        return true;
    }

    /*
     * this is called when changing task property. primitive task and composite task
     * have different property. so the task is passed to the method.
     */
    private boolean isValidTaskProperty(String taskName, String property) {
        Task task = getTask(taskName);
        assert task != null;
        if (property.equals("name"))
            return true;
        if (property.equals("description"))
            return true;
        if (task.isCompositeTask())
            return property.equals("subtasks");
        return property.equals("duration") || property.equals("prerequisites");
    }

}