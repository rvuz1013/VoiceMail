/**
 * @author Vuz, Ryan & Zhang, Yingfan
 * 
 */

public class Connection {
    
    private MailSystem mailSystem;
    private Mailbox mailbox;
    private String recording;
    private String pressedKeys;
    private Phone phone;
    private int state;
    
    private static final int DISCONNECTED =     0;
    private static final int CONNECTED =        1;
    private static final int RECORDING =        2;
    private static final int MAILBOX_MENU =      3;
    private static final int MESSAGE_MENU =      4;
    private static final int CHANGE_PASSCODE =   5;
    private static final int CHANGE_GREETING =   6;
    
    private static final String INITIAL_PROMPT =
      "Enter mailbox number followed by #";
    private static final String MAILBOX_MENU_TEXT = 
            "Enter 1 to listen to your messages\n"
          + "Enter 2 to change your passcode\n"
          + "Enter 3 to change your greeting";
    private static final String MESSAGE_MENU_TEXT =
       "Enter 1 to listen to the current message\n"
          + "Enter 2 to save the current message\n"
          + "Enter 3 to delete the current message\n"
          + "Enter 4 to return to the main menu";
    
    public Connection(MailSystem s, Phone p) {
        mailSystem = s;
        phone = p;
        resetConnection();
    }
    
    private void resetConnection() {
        recording = "";
        pressedKeys = "";
        state = CONNECTED;
        phone.speakOut(INITIAL_PROMPT);
    }
    
    public void dial(String key) {
        switch (state) {
            case CONNECTED -> connect(key);
            case RECORDING -> logIn(key);
            case CHANGE_GREETING -> changeGreeting(key);
            case CHANGE_PASSCODE -> changePasscode(key);
            case MAILBOX_MENU -> mailboxMenu(key);
            case MESSAGE_MENU -> messageMenu(key);
        }
    }
    
    private void connect(String key) {
        if(key.equals("#")){
            mailbox = mailSystem.findMailbox(pressedKeys);
            if(mailbox != null){
                state = RECORDING;
                phone.speakOut(mailbox.getGreeting());
                pressedKeys = "";
            }else{
                phone.speakOut("Incorrect mailbox extention. Try again!");
                pressedKeys = "";
            }
        }else{
            pressedKeys += key;
        }
    }
    
    public void record(String voice){
        if(state == RECORDING || state == CHANGE_GREETING){
            recording += voice;
        }
    }
    
    private void logIn(String key) {
        if (key.equals("#")){
            if(mailbox.checkPasscode(pressedKeys)){
                state = MAILBOX_MENU;
                phone.speakOut(MAILBOX_MENU_TEXT);
            }else{
                phone.speakOut("Incorrect passcode. Try again!");
                pressedKeys = "";
            }
        }else{
            pressedKeys += key;
        }
    }
    
    private void changePasscode(String key) {
        if(key.equals("#")){
            mailbox.setPasscode(pressedKeys);
            state = MAILBOX_MENU;
            phone.speakOut(MAILBOX_MENU_TEXT);
            pressedKeys = "";
        }else{
            pressedKeys += key;
        }
    }
    
    private void changeGreeting(String key) {
        if(key.equals("#")){
            mailbox.setGreeting(recording);
            state = MAILBOX_MENU;
            phone.speakOut(MAILBOX_MENU_TEXT);
            recording = "";
        }
    }
    
    private void mailboxMenu(String key) {
        switch (key) {
            case "1" -> {
                state = MESSAGE_MENU;
                phone.speakOut(MESSAGE_MENU_TEXT);
            }
            case "2" -> {
                state = CHANGE_PASSCODE;
                phone.speakOut("Enter new passcode followed by the # key");
            }
            case "3" -> {
                state = CHANGE_GREETING;
                phone.speakOut("Record your greeting, then press the # key");
            }
        }
    }
    
    private void messageMenu(String key) {
        switch (key) {
            case "1" -> {
                String output = "";
                Message m = mailbox.getCurrentMessage();
                if(m == null){
                    output += "No messages.\n";
                }else{
                    output += m.getText() + "\n";
                }
                output += MESSAGE_MENU_TEXT;
                phone.speakOut(output);
            }
            case "2" -> {
                mailbox.saveCurrentMessage();
                phone.speakOut(MESSAGE_MENU_TEXT);
            }
            case "3" -> {
                mailbox.removeCurrentMessage();
                phone.speakOut(MESSAGE_MENU_TEXT);
            }
            case "4" -> {
                state = MAILBOX_MENU;
                phone.speakOut(MAILBOX_MENU_TEXT);
            }
        }
    }
    
    public void hangUp() {
        if(state == RECORDING){
            mailbox.addMessage(new Message(recording));
        }
        resetConnection();
    }
}
