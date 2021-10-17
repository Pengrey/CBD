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
            File myObj = new File(classLoader.getResource("names.txt").getFile());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String name = myReader.nextLine();
                // System.out.println(name);
                jedis.sadd(name, "0");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public Set<String> getNames(String name){
        return jedis.keys(name + "*");
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
