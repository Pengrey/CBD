package jedis_chat.app; 
import redis.clients.jedis.Jedis; 

public class Server 
{
    private Jedis jedis;

    public Server(){
        this.jedis = new Jedis("localhost"); 
        System.out.println(jedis.info()); 
    }

    public static void main( String[] args )
    {
        new Server();
    }
}
