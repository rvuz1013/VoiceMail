
import java.util.ArrayList;

/**
 * @author Vuz, Ryan & Zhang, Yingfan
 *
 */
public class MessageQueue {

    private ArrayList<Message> queue;
    
    public MessageQueue() {
        queue = new ArrayList<>();
    }

    public Message remove() {
        return queue.remove(0);
    }
    
    public void add(Message newMessage) {
        queue.add(newMessage);
    }
    
    public int size() {
        return queue.size();
    }
    
    public Message recent() {
       if (queue.size() > 1) {
           return null;
       }else {
           return queue.get(0);
       } 
    }
}
