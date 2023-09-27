/**
 * @author Vuz, Ryan & Zhang, Yingfan
 * 
 */

public class Mailbox {
    
    private MessageQueue newMessages;
    private MessageQueue savedMessages;
    private String passcode;
    private String greeting;
    
    public Mailbox(String passcode, String greeting) {
        this.passcode = passcode;
        this.greeting = greeting;
        newMessages = new MessageQueue();
        savedMessages = new MessageQueue();
    }
    
    public boolean checkPasscode(String enteredPasscode) {
        return enteredPasscode.equals(passcode);
    }
    
    public String getGreeting() {
        return greeting;
    }
    
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
    
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
    
    public void addMessage(Message newMessage) {
        newMessages.add(newMessage);
    }
    
    public Message getCurrentMessage() {
        if(newMessages.size()> 0){
            return newMessages.recent();
        }else if(savedMessages.size() > 0){
            return savedMessages.recent();
        }else{
            return null;
        }
    }

    public Message removeCurrentMessage() {
        if(newMessages.size()> 0){
            return newMessages.remove();
        }else if(savedMessages.size() > 0){
            return savedMessages.remove();
        }else{
            return null;
        }
    }
    
    public void saveCurrentMessage() {
        Message currentMessage = getCurrentMessage();
        if(currentMessage != null){
            savedMessages.add(currentMessage);
            newMessages.remove();
        }
    }   
}
