package hk.edu.polyu.comp.comp2021.tms.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Task Management System where required functions are implemented
 * can only communicate with it by sending an outside String command, and get its output messages as command result
 */
public class TMS implements Serializable {
    private TaskSet taskSet;
    private CriterionSet criterionSet;
    private StringBuilder feedback;

    /**
     * default constructor of TMS that can be called to create a TMS object in the outside environment
     */
    public TMS() {
        taskSet = new TaskSet();
        criterionSet = new CriterionSet();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        storeState();
    }

    /**
     * @param cmd is the command sent in from outside
     * @return is the messages regarding the executing of the command, it may contain multiple lines of texts
     */
    public String validate_executeCommand(String cmd) {
        feedback = new StringBuilder();// everytime a new command is fed, clear the message buffer
        cmd = trim_unique(cmd);
        String[] temp = cmd.split(" ");
        switch (temp[0]) {
            case "CreatePrimitiveTask":
                if (temp.length != 5)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.createPrimitiveTask(temp[1], temp[2], temp[3], temp[4]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "CreateCompositeTask":
                if (temp.length != 4)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.createCompositeTask(temp[1], temp[2], temp[3]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "DeleteTask":
                if (temp.length != 2)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.deleteTask(temp[1]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "ChangeTask":
                if (temp.length != 4)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.ChangeTask(temp[1], temp[2], temp[3]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "PrintTask":
                if (temp.length != 2)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.printTask(temp[1]));
                break;
            case "PrintAllTasks":
                if (temp.length != 1)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.printAllTasks());
                break;
            case "ReportDuration":
                if (temp.length != 2)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.reportDuration(temp[1]));
                break;
            case "ReportEarliestFinishTime":
                if (temp.length != 2)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(taskSet.reportEarliestFinishTime(temp[1]));
                break;
            case "DefineBasicCriterion":
                if (temp.length != 5)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(criterionSet.defineBasicCriterion(temp[1], temp[2], temp[3], temp[4]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "DefineNegatedCriterion":
                if (temp.length != 3)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(criterionSet.defineNegatedCriterion(temp[1], temp[2]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "DefineBinaryCriterion":
                if (temp.length != 5)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(criterionSet.defineBinaryCriterion(temp[1], temp[2], temp[3], temp[4]));
                if (feedback.toString().startsWith("Done. "))   storeState();
                break;
            case "PrintAllCriteria":
                if (temp.length != 1)   feedback.append("Error. ").append("Invalid command format.\n");
                else                    feedback.append(criterionSet.printAllCriteria());
                break;
            case "Search":
                if (temp.length != 2)                           feedback.append("Error. ").append("Invalid command format.\n");
                else if (!Criterion.isValidName(temp[1]))       feedback.append("Error. ").append("Invalid criterion name: \"").append(temp[1]).append("\"\n");
                else if (!criterionSet.contains(temp[1]))       feedback.append("Error. ").append("Criterion \"").append(temp[1]).append("\" is not defined.\n");
                else                                            feedback.append(taskSet.search(criterionSet.getCriterion(temp[1])));
                break;
            case "Store":
                if (temp.length != 2)                           feedback.append("Error. ").append("Invalid command format.\n");
                Store(temp[1]);
                break;
            case "Load":
                if (temp.length != 2)                           feedback.append("Error. ").append("Invalid command format.\n");
                boolean flag = Load(temp[1]);
                if (flag) {
                    clearState();
                    storeState();
                }
                break;
            case "undo":
                if (temp.length != 1)                           feedback.append("Error. ").append("Invalid command format.\n");
                undo();
                break;
            case "redo":
                if (temp.length != 1)                           feedback.append("Error. ").append("Invalid command format.\n");
                redo();
                break;
            case "Quit":
                if (temp.length != 1)                           feedback.append("Error. ").append("Invalid command format.\n");
                deleteTemporaryFiles();
                System.exit(0);
                break;
            default:
                feedback.append("Error. ").append("Invalid command format.\n");
                break;
        }
        return feedback.toString();
    }
    private static final String TEMP_DIR = "./temp/";
    private static final String STATE_FILE_EXTENSION = ".data";
    private static final String STATE_FILE_PREFIX = "state_";
    private final Stack<String> undoStack;
    private final Stack<String> redoStack;
    private void clearState() {
        undoStack.clear();
        redoStack.clear();
    }
    /*
     * store the current program state into a
     */
    private void storeState() {
        try {
            // Create the temporary directory if it doesn't exist
            File tempDir = new File(TEMP_DIR);
            if (!tempDir.exists()) {
                boolean makeDirectory = tempDir.mkdir();
                if (!makeDirectory) {
                    feedback.append("Error storing program state.\n");
                    return;
                }
            }
            // Generate a unique state file name
            String stateFileName = STATE_FILE_PREFIX + System.currentTimeMillis() + STATE_FILE_EXTENSION;
            String stateFilePath = TEMP_DIR + stateFileName;

            // Serialize the current state to the state file
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(stateFilePath)));
            outputStream.writeObject(taskSet);
            outputStream.writeObject(criterionSet);
            outputStream.flush();
            outputStream.close();

            // Add the state file to the undo stack
            undoStack.push(stateFilePath);
            redoStack.clear();
        } catch (IOException e) {
            feedback.append("Error storing program state.\n");
        }
    }

    private void undo() {
        // undoStack is guaranteed to have size at least 1. and it equals one before the
        // execution of the first command.
        if (undoStack.size() <= 1) {
            feedback.append("Cannot undo no more.\n");
            return;
        }
        String currentStateFile = undoStack.pop();
        redoStack.push(currentStateFile);

        // Restore the previous state
        String previousStateFile = undoStack.peek();
        if (restoreState(previousStateFile))    feedback.append("Undo successful.\n");
    }

    private void redo() {
        if (redoStack.isEmpty()) {
            feedback.append("Cannot redo no more.\n");
            return;
        }
        String nextStateFile = redoStack.pop();
        undoStack.push(nextStateFile);

        // Restore the next state
        if (restoreState(nextStateFile))    feedback.append("Redo successful.\n");
    }

    private boolean restoreState(String stateFile) {
        try {
            // Deserialize the state from the state file
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(stateFile)));
            taskSet = (TaskSet) inputStream.readObject();
            criterionSet = (CriterionSet) inputStream.readObject();
            inputStream.close();
//            feedback.append("State restored from: ").append(stateFile).append("\n");
        } catch (IOException | ClassNotFoundException e) {
            feedback.append("Error loading program state.\n");
            return false;
        }
        return true;
    }

    /*
     * if the file specified by the path exist, it will be deleted, created empty,
     * and written into the current state of the TMS
     * if the file does not exist,
     */
    private void Store(String path) {
        try {
//            feedback.append("The path becomes \"").append(path).append("\"\n");
            File file = new File(path);

            // delete the original file
            if (file.exists()) {
                boolean delete = file.delete();
                if (!delete) {
                    feedback.append("Store failed. Unable to empty the file.");
                    return;
                }
            }
            // Create a new empty file to write into the current state
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    feedback.append("Store failed. Unable to create empty file.");
                    return;
                }
            }
            // Serialize the taskSet and criterionSet objects to the file
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file.toPath()));
            outputStream.writeObject(taskSet);
            outputStream.writeObject(criterionSet);
            outputStream.flush();
            outputStream.close();
            feedback.append("Current state stored: ").append(taskSet.size()).append(" tasks and ")
                    .append(criterionSet.size()).append(" criteria successfully!\n");
        } catch (Exception e) {
            feedback.append("Error storing program state.\n");
        }
    }

    private boolean Load(String path) {
        TaskSet readTaskSet;
        CriterionSet readCriterionSet;
        try {
            File file = new File(path);
            // Check if the file exists
            if (!file.exists()) {
                feedback.append("Loaded failed. File does not exist.\n");
                return false;
            }

            // Deserialize the taskSet and criterionSet objects from the file
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file.toPath()));
            readTaskSet = (TaskSet) inputStream.readObject();
            readCriterionSet = (CriterionSet) inputStream.readObject();
            inputStream.close();

            taskSet = readTaskSet;
            criterionSet = readCriterionSet;
            feedback.append("Loaded ").append(taskSet.size()).append(" Tasks and ")
                    .append(criterionSet.size()).append(" criteria successfully!\n");

        } catch (Exception e) {
            feedback.append("Error loading program state.\n");
            return false;
        }
        return true;
    }


    /*
     * remove the given character at both end of the given string and replace
     * consecutive duplicates of the character by a single one of it
     */
    private static String trim_unique(String s) {
        int l = 0;
        while (l < s.length() && s.charAt(l) == ' ')
            ++l;
        if (l == s.length())
            return "";
        int r = s.length() - 1;
        while (s.charAt(r) == ' ')
            --r;
        s = s.substring(l, r + 1);
        while (s.contains("" + ' ' + ' '))
            s = s.replace("" + ' ' + ' ', "" + ' ');
        return s;
    }
    private void deleteTemporaryFiles() {
        File directory = new File(TEMP_DIR);
        if (!directory.exists())    return;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory())file.delete();
            }
        }
        directory.delete();
    }
}
