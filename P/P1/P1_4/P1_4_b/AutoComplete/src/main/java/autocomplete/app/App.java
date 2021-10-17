package autocomplete.app;
import java.util.Scanner;
import java.util.Set; 
import redis.clients.jedis.Jedis; 
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;

public class App {
    private Jedis jedis;

    public App(){
        this.jedis = new Jedis("localhost");
    }

    public void saveNames(){

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File myObj = new File(classLoader.getResource("nomes-pt-2021.csv").getFile());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String[] name = myReader.nextLine().split(";");
                // System.out.println(name[0] + " : " + name[1]);
                jedis.zadd("names", Integer.parseInt(name[1]), name[0]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public Set<String> getNames(String name){
        String maxName = name.substring(0, name.length() - 1) + Character.toString((char) (((name.charAt(name.length() - 1) - 'a' + 1) % 26) + 'a'));
        return jedis.zrangeByLex("names", "[" + name, "(" + maxName);
    }

    public static String getInput(){
        Scanner scanner = new Scanner(System.in);
        // Print prompt
        System.out.print("Search for ('Enter' for quit): ");
        // Get parcial or total name
        String readString = scanner.nextLine();
        
        // Close scanner
        scanner.close();

        return readString;
    }

    public static void main( String[] args ) {
        App autoC = new App();
        autoC.saveNames();

        String name = getInput();

        autoC.getNames(name).stream().forEach(System.out::println); 
    }
}
