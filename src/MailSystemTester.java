import java.util.Scanner;
/**
 * @author Vuz, Ryan & Zhang, Yingfan
 */
public class MailSystemTester {

    private static final int COUNT = 15;
    
    public static void main(String[] args) {
        MailSystem mailSystem = new MailSystem(COUNT);
        Scanner input = new Scanner(System.in);
        Phone  p = new Phone(input);
        Connection c = new Connection(mailSystem, p);
        p.run(c);
    }
}
