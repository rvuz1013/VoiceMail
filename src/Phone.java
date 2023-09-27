import java.util.Scanner;
/**
 * @author Vuz, Ryan & Zhang, Yingfan
 * 
 */

public class Phone {
    
    private Scanner input;
    
    public Phone(Scanner input) {
        this.input = input;
    }
    
    public void speakOut(String output) {
        System.out.println(output);
    }
    
    public void run(Connection c) {
        boolean more = true;
        while(more){
            String keys = input.nextLine();
            if(keys == null){
                return;
            }
            if(keys.equalsIgnoreCase("H")){
                c.hangUp();
            }else if(keys.equalsIgnoreCase("Q")){
                more = false;
            }else if(keys.length() == 1 && "1234567890#".indexOf(keys) >= 0){
                c.dial(keys);
            }else{
                c.record(keys);
            }
        }
    }
}
