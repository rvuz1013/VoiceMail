import java.util.ArrayList;
/**
 * @author Vuz, Ryan & Zhang, Yingfan
 * 
 */

public class MailSystem {
    
    private ArrayList<Mailbox> mailboxes;
    
    public MailSystem(int count) {
        mailboxes = new ArrayList<>();
        
        for(int i = 0; i < count; i++){
            String passcode = "" + (i + 1);
            String greeting = "Hello! You have reached mailbox #" + (i+1)
                    + "! \n\nPlease leave your message now, thank you.";
            mailboxes.add(new Mailbox(passcode, greeting));
        }
    }
    
    public Mailbox findMailbox(String ext) {
        int n = Integer.parseInt(ext);
        if(1 <= n && n <= mailboxes.size()){
            n = n - 1;
            return mailboxes.get(n);
        }else{
            return null;
        }
    }
}
