package redis;

import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
 
public class SimplePostHash {
 
	private Jedis jedis;
	public static String USERS = "users"; // Key set for users' name
	
	public SimplePostHash() {
		this.jedis = new Jedis("localhost");
	}
 
	public void saveUser(String username) {
		jedis.hset(USERS, username, "username");
	}

	public Map<String, String> getUser() {
		return jedis.hgetAll(USERS);
	}
	
	public Set<String> getAllKeys() {
		return jedis.keys("*");
	}
 
	public static void main(String[] args) {
		SimplePostHash board = new SimplePostHash();
		
        // set some users
		String[] users = { "Ana", "Pedro", "Maria", "Luis" };
		
        // Add users
        for (String user: users) board.saveUser(user);
		
        board.getAllKeys().stream().forEach(System.out::println);

        for (Map.Entry<String,String> entry : board.getUser().entrySet())
            System.out.println(entry.getKey());
		
	}
}