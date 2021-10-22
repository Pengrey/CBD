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
        System.out.println("+-----------------------------------------------------------------------------+\n" +
                           "|####################################Login####################################|\n" +
                           "+-----------------------------------------------------------------------------+\n" + 
                           "| Commands:                                                                   |\n" +
                           "+--------------------------------------+--------------------------------------+\n" +
                           "| login                                | Start login progress                 |\n" +  
                           "| create                               | Start account creation process       |\n" +
                           "| quit                                 | Exit program                         |\n" +
                           "+--------------------------------------+--------------------------------------+");


        String input = getInput();
        String arr[] = input.split(" ", 2);
        String command = arr[0];   // command
        String username;
        String password;

        switch(command) {
            case "login":
                // Get Credentials
                System.out.println("[?] Username:");
                username = getInput();
                System.out.println("[?] Password:");
                password = getInput();
                
                // Verification
                if(!jedis.sismember(username + "/password", password)){
                    System.out.println("[!] Bad credentials. Try again.");
                    return login();
                }

                // Set as online
                jedis.sadd("isOnline", username);

                System.out.println("[I] Welcome back " + username + "!");
                return username;

            case "create":
                // Get Credentials
                System.out.println("[?] Username:");
                username = getInput();

                // Check if username exists already
                if(jedis.scard(username + "/isUser") == 1){
                    System.out.println("[!] User already exists!");
                    return login();
                }

                System.out.println("[?] Password:");
                password = getInput();

                // Check if password is well given
                System.out.println("[?] Please retype the password:");
                if(!password.equals(getInput())){
                    System.out.println("[!] Passord missmatch.");
                    return login();
                }

                // Build user
                jedis.sadd(username + "/isUser", "0");        // set as an user
                jedis.sadd(username + "/password", password); // set credentials to user

                // Set as online
                jedis.sadd("isOnline", username);

                System.out.println("[I] Welcome " + username + "!");
                return username;
            
            case "quit":
                System.exit(0);
                break;
                
            default:
                System.out.println("[!] Command doesnt exist");
                login();
        }
        return null;
    }

    public void getOnlineUsers(){
        System.out.println("[I] Online users:");
        for(String user:jedis.smembers("isOnline")){
            System.out.println(" -> " + user);
        }
    }

    public void follow(String userToFollow){
        // Check if userToFollow exists 
        if(!(jedis.scard(userToFollow + "/isUser") == 1)){
            System.out.println("[!] User doesnt exist!");
        }else{
            // Add follower to the followed
            jedis.sadd(userToFollow + "/followers", username);
            // Add following to the follower
            jedis.sadd(username + "/following", userToFollow);
            System.out.println("[I] You are now following " + userToFollow);
        }
    }

    public void rmfollow(String userToFollow){
        // Check if user follows user to unfollow 
        if(!(jedis.sismember(username + "/following", userToFollow))){
            System.out.println("[!] You dont follow that user!");
        }else{
            // Add follower to the followed
            jedis.srem(userToFollow + "/followers", username);
            // Add following to the follower
            jedis.srem(username + "/following", userToFollow);
            System.out.println("[I] You are now not following " + userToFollow);
        }
    }

    public void following(){
        System.out.println("[I] You are following:");
        for(String following:jedis.smembers(username + "/following")){
            System.out.println(" -> " + following);
        }
    }

    public void followers(){
        System.out.println("[I] Your followers:");
        for(String follower:jedis.smembers(username + "/followers")){
            System.out.println(" -> " + follower);
        }
    }

    public void send(String message){
        for(String follower:jedis.smembers(username + "/followers")){
            jedis.sadd(follower + "/messages", username + " > " + message);
        }
        System.out.println("[I] Message sent successfully.");
    }

    public void notifications(){
        System.out.println("[I] Your have " + jedis.scard(username + "/messages") + " new unread messages.");
    }

    public void getMsgs(){
        System.out.println("[I] Messages unread:");
        for(String message:jedis.smembers(username + "/messages")){
            jedis.srem(username + "/messages", message); 
            System.out.println(" -> " + message);
        }
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
                    System.out.println("[!] Please add username to follow");
                }else{
                    follow(args);
                }
                menu();
                break;
            case "unfollow":
                if(args == null){
                    System.out.println("[!] Please add username to unfollow");
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
                if(args == null){
                    System.out.println("[!] Please add a message to send");
                }else{
                    send(args);
                }
                menu();
                break;
            case "notifs":
                notifications();
                menu();
                break;
            case "msgs":
                getMsgs();
                menu();
                break;
            case "help":
                System.out.println("+-----------------------------------------------------------------------------+\n" +
                                   "|####################################Usage####################################|\n" +
                                   "+-----------------------------------------------------------------------------+\n" + 
                                   "| Commands:                                                                   |\n" +
                                   "+--------------------------------------+--------------------------------------+\n" +
                                   "| send [message]                       | Send message to system               |\n" +
                                   "| notifs                               | Give number of new messages unread   |\n" +  
                                   "| msgs                                 | Display unread messages              |\n" +
                                   "| online                               | Display online users                 |\n" +
                                   "| follow [username]                    | Follow user                          |\n" +
                                   "| following                            | Display followed users               |\n" +
                                   "| unfollow [username]                  | Unfollow user                        |\n" +
                                   "| followers                            | Display followers                    |\n" +
                                   "| help                                 | Display this prompt                  |\n" +
                                   "| quit                                 | Exit program                         |\n" +
                                   "+--------------------------------------+--------------------------------------+");
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
        if (args.length == 1 && args[0].equals("-p")){
            System.out.println( "            _____          _ _       ______                         \n" +   
                                "           |  __ \\        | (_)     |  ____|                        \n" +
                                "           | |__) |___  __| |_ ___  | |__ ___  _ __ _   _ _ __ ___  \n" + 
                                "           |  _  // _ \\/ _` | / __| |  __/ _ \\| '__| | | | '_ ` _ \\ \n" + 
                                "           | | \\ \\  __/ (_| | \\__ \\ | | | (_) | |  | |_| | | | | | |\n" + 
                                "           |_|  \\_\\___|\\__,_|_|___/ |_|  \\___/|_|   \\__,_|_| |_| |_|\n");
        }
        Client c = new Client();
        System.out.println("+-----------------------------------------------------------------------------+\n" +
                           "|####################################Usage####################################|\n" +
                           "+-----------------------------------------------------------------------------+\n" + 
                           "| Commands:                                                                   |\n" +
                           "+--------------------------------------+--------------------------------------+\n" +
                           "| send [message]                       | Send message to system               |\n" +
                           "| notifs                               | Give number of new messages unread   |\n" +  
                           "| msgs                                 | Display unread messages              |\n" +
                           "| online                               | Display online users                 |\n" +
                           "| follow [username]                    | Follow user                          |\n" +
                           "| following                            | Display followed users               |\n" +
                           "| unfollow [username]                  | Unfollow user                        |\n" +
                           "| followers                            | Display followers                    |\n" +
                           "| help                                 | Display this prompt                  |\n" +
                           "| quit                                 | Exit program                         |\n" +
                           "+--------------------------------------+--------------------------------------+");
        c.menu();                       
    }
}
