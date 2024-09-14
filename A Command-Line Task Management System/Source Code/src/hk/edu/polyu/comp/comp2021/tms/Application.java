package hk.edu.polyu.comp.comp2021.tms;
import hk.edu.polyu.comp.comp2021.tms.model.TMS;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
/**
 * a GUI that takes commands from the input text box and output the message given by the TMS
 */
public class Application extends Frame {
    private final TextField commandField;
    private final TextArea chatArea;
    private final List<String> chatHistory;
    private final Button submitButton;
    private final TMS tms;
    private class MyComponentListener implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
            updateWindowSize();
        }
        @Override
        public void componentMoved(ComponentEvent e) {
            // Leave this method empty if it is not needed
        }
        @Override
        public void componentShown(ComponentEvent e) {
            // Leave this method empty if it is not needed
        }
        @Override
        public void componentHidden(ComponentEvent e) {
            // Leave this method empty if it is not needed
        }
    }
    private static final int GAP = 10;
    private static final int WIDTH_FACTOR = 2;
    private static final int HEIGHT_FACTOR = 2;
    private static final int MIN_WIDTH_FACTOR = 3;
    private static final int MIN_HEIGHT_FACTOR = 3;
    private static final int FONT_SIZE = 16;
    private static final int COMMAND_FIELD_HEIGHT = 30;
    private static final int SUBMIT_BUTTON_WIDTH = 90;
    private static final int SUBMIT_BUTTON_HEIGHT = COMMAND_FIELD_HEIGHT;
    private static final int CHAT_AREA_X = GAP;
    private static final int CHAT_AREA_Y = 4 * GAP;
    private static final int CHAT_AREA_WIDTH_OFFSET = 2 * GAP;
    private static final int CHAT_AREA_HEIGHT_OFFSET = 8 * GAP + COMMAND_FIELD_HEIGHT;
    private static final int COMMAND_FIELD_X = CHAT_AREA_X;
    private static final int COMMAND_FIELD_WIDTH_OFFSET = 2 * GAP + SUBMIT_BUTTON_WIDTH;
    private static final int COMMAND_FIELD_Y_OFFSET = 3 * GAP + COMMAND_FIELD_HEIGHT;
    private static final int SUBMIT_BUTTON_X_OFFSET = SUBMIT_BUTTON_WIDTH + GAP;
    private static final int SUBMIT_BUTTON_Y_OFFSET = SUBMIT_BUTTON_HEIGHT + 3 * GAP;
    /**
     * default constructor where the GUI is implemented
     */
    public Application() {
        tms = new TMS();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int WIDTH = screen.width / WIDTH_FACTOR;
        int HEIGHT = screen.height / HEIGHT_FACTOR;
        setTitle("Task Management System");
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setMinimumSize(new Dimension(screen.width / MIN_WIDTH_FACTOR, screen.height / MIN_HEIGHT_FACTOR));
        setMaximumSize(new Dimension(screen.width, screen.height));
        centerWindow();
        Font font = new Font("Times New Roman", Font.PLAIN, FONT_SIZE);
        setFont(font);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                tms.validate_executeCommand("Quit");
            }
        });
        chatArea = new TextArea();
        chatArea.setBounds(CHAT_AREA_X, CHAT_AREA_Y, getWidth() - CHAT_AREA_WIDTH_OFFSET, getHeight() - CHAT_AREA_HEIGHT_OFFSET);
        chatArea.setEditable(false);
        add(chatArea);
        commandField = new TextField();
        commandField.setBounds(COMMAND_FIELD_X, getHeight() - COMMAND_FIELD_Y_OFFSET, getWidth() - COMMAND_FIELD_WIDTH_OFFSET, COMMAND_FIELD_HEIGHT);
        commandField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    communicate();
            }
        });
        add(commandField);
        submitButton = new Button("Send");
        submitButton.setBounds(getWidth() - SUBMIT_BUTTON_X_OFFSET, getHeight() - SUBMIT_BUTTON_Y_OFFSET, SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT);
        chatHistory = new ArrayList<>();
        chatHistory.add(" * Welcome to Task Management System designed by Team_014.\n");
        chatHistory.add(" * Team_014 members are LIN Zhanzhi, KONG Zirui, Raynell Lu Soon Hong, ZHEN ZHANG Alex.\n");
        chatHistory.add(" * Please input commands one by one and the TMS will display the result automatically.\n");
        chatHistory.add("**************************************************************************************\n");
        chatArea.setText(chatHistory.toString().replaceAll("\n, ", "\n").substring(1,
                chatHistory.toString().replaceAll("\n, ", "\n").length() - 1));
        submitButton.addActionListener(e -> communicate());
        add(submitButton);
        setVisible(true);
        addComponentListener(new MyComponentListener());
        commandField.requestFocus();
        commandField.setCaretPosition(commandField.getText().length());
    }
    private void updateWindowSize() {
        chatArea.setBounds(CHAT_AREA_X, CHAT_AREA_Y, getWidth() - CHAT_AREA_WIDTH_OFFSET, getHeight() - CHAT_AREA_HEIGHT_OFFSET);
        commandField.setBounds(COMMAND_FIELD_X, getHeight() - COMMAND_FIELD_Y_OFFSET, getWidth() - COMMAND_FIELD_WIDTH_OFFSET, COMMAND_FIELD_HEIGHT);
        submitButton.setBounds(getWidth() - SUBMIT_BUTTON_X_OFFSET, getHeight() - SUBMIT_BUTTON_Y_OFFSET, SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT);
    }
    private void communicate() {
        String command = commandField.getText();
        commandField.setText("");
        String message = tms.validate_executeCommand(command);
        chatHistory.add("Command: " + command + '\n');
        chatHistory.add(message + "\n");
        chatArea.setText(chatHistory.toString().replaceAll("\n, ", "\n").substring(1,
                chatHistory.toString().replaceAll("\n, ", "\n").length() - 1));
        chatArea.setCaretPosition(chatArea.getText().length());
    }
    private void centerWindow() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screen.width;
        int screenHeight = screen.height;
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);
    }
    /**
     * @param args is not used but kept
     * call the constructor to run the GUI
     */
    public static void main(String[] args) {
        new Application();
    }
}