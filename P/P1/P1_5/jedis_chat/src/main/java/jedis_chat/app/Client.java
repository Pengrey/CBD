package jedis_chat.app;
import java.util.Set;
import java.util.Scanner;
import redis.clients.jedis.Jedis;

public class Client
{
    private Jedis jedis;
    private String username;

    public Client() {
		this.jedis = new Jedis("localhost");
        this.username = login();
	}

    public String login(){
        System.out.println("usage: command [arguments]\ncommands:                \nlogin                   start login process;\ncreate                  create new user process\nquit                    quit program");
        String input = getInput();
        String arr[] = input.split(" ", 2);
        String command = arr[0];   // command
        String username;
        String password;

        switch(command) {
            case "login":
                // Get Credentials
                System.out.println("Username:");
                username = getInput();
                System.out.println("Password:");
                password = getInput();
                
                // Verification
                if(!jedis.sismember(username + "/password", password)){
                    System.out.println("Bad credentials. Try again.");
                    return login();
                }

                // Set as online
                jedis.sadd("isOnline", username);

                System.out.println("Welcome back " + username + "!");
                return username;

            case "create":
                // Get Credentials
                System.out.println("Username:");
                username = getInput();

                // Check if username exists already
                if(jedis.scard(username + "/isUser") == 1){
                    System.out.println("User already exists!");
                    return login();
                }

                System.out.println("Password:");
                password = getInput();

                // Check if password is well given
                System.out.println("Please retype the password:");
                if(!password.equals(getInput())){
                    System.out.println("Passord missmatch.");
                    return login();
                }

                // Build user
                jedis.sadd(username + "/isUser", "0");        // set as an user
                jedis.sadd(username + "/password", password); // set credentials to user

                // Set as online
                jedis.sadd("isOnline", username);

                System.out.println("Welcome " + username + "!");
                return username;
            
            case "quit":
                System.exit(0);
                break;
                
            default:
                System.out.println( "Command doesnt exist" );
                login();
        }
        return null;
    }

    public void getOnlineUsers(){
        System.out.println("Online users:");
        jedis.smembers("isOnline").stream().forEach(System.out::println);
    }

    public void follow(String userToFollow){
        // Check if userToFollow exists 
        if(!(jedis.scard(userToFollow + "/isUser") == 1)){
            System.out.println("User doesnt exist!");
        }else{
            // Add follower to the followed
            jedis.sadd(userToFollow + "/followers", username);
            // Add following to the follower
            jedis.sadd(username + "/following", userToFollow);
            System.out.println("You are now following " + userToFollow);
        }
    }

    public void rmfollow(String userToFollow){
        // Check if userToFollow exists 
        if(!(jedis.scard(userToFollow + "/isUser") == 1)){
            System.out.println("User doesnt exist!");
        // Check if user follows username
        }else if(!(jedis.sismember(username + "/following", userToFollow))){
            System.out.println("You dont follow that user!");
        }else{
            // Add follower to the followed
            jedis.srem(userToFollow + "/followers", username);
            // Add following to the follower
            jedis.srem(username + "/following", userToFollow);
            System.out.println("You are now not following " + userToFollow);
        }
    }

    public void following(){
        System.out.println("You are following:");
        jedis.smembers(username + "/following").stream().forEach(System.out::println);
    }

    public void followers(){
        System.out.println("Your followers:");
        jedis.smembers(username + "/followers").stream().forEach(System.out::println);
    }

    public void menu(){
        String input = getInput();
        String arr[] = input.split(" ", 2);
        String command = arr[0];                       // command
        String args = arr.length == 2 ? arr[1] : null; // arguments

        switch(command) {
            case "quit":
                // Set as offline
                jedis.srem("isOnline", username); 
                break;
            case "online":
                getOnlineUsers();
                menu();
                break;
            case "follow":
                if(args == null){
                    System.out.println("Please add username to follow");
                }else{
                    follow(args);
                }
                menu();
                break;
            case "rmfollow":
                if(args == null){
                    System.out.println("Please add username to stop following");
                }else{
                    rmfollow(args);
                }
                menu();
                break;
            case "following":
                following();
                menu();
                break;
            case "followers":
                followers();
                menu();
                break;
            case "send":
                menu();
                break;
            case "notifications":
                menu();
                break;
            case "msgs":
                menu();
                break;
            case "help":
                System.out.println("usage: command [arguments]\nOptions:                \nsend  <message>       send message to system\nquit                    quit program\nhelp                    display usage prompt");
                menu();
                break;
            default:
                System.out.println( "Command doesnt exist" );
                menu();
        }

    }

    public static String getInput(){
        Scanner sc= new Scanner(System.in);  
        System.out.print("> ");  
        String str= sc.nextLine();              
        return str;  
    }

    public static void main( String[] args )
    {
        Client c = new Client();
        System.out.println("usage: command [arguments]\nOptions:                \nsend  <message>       send message to system\nquit                    quit program\nonline                   get online users\nhelp                    display usage prompt");
        c.menu();
    }
}
