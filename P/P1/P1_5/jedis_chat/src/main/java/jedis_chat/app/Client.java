package jedis_chat.app;
import java.util.Set;
import java.util.Scanner;
import redis.clients.jedis.Jedis;

public class Client
{
    private Jedis jedis;
    public Client() {
		this.jedis = new Jedis("localhost");
        this.username = login(t);
	}

    public static String login(j){
        String input = getInput();
        String arr[] = input.split(" ", 2);
        String command = arr[0];   // command

        switch(command) {
            case "login":
                // code block
                break;
            case "create":
                System.out.println("Username:");
                String username = getInput();
                System.out.println("Password:");
                String password = getInput();
                break;
            default:
                System.out.println( "Command doesnt exist" );
                login();
        }
    }

    public static String getInput(){
        Scanner sc= new Scanner(System.in);  
        System.out.print("> ");  
        String str= sc.nextLine();              
        return str;  
    }

    public static void menu(){
        String input = getInput();
        String arr[] = input.split(" ", 2);
        String command = arr[0];   // command
        String args = arr.length == 2 ? arr[1] : null; // arguments

        switch(command) {
            case "quit":
                // code block
                break;
            case "send":
                // code block
                menu();
                break;
            default:
                System.out.println( "Command doesnt exist" );
                menu();
        }

    }

    public static void main( String[] args )
    {
        menu();
    }
}
