### Connection and status

To get a connection to the redis database and get status info we can execute the [Forum.java](http://Forum.java) by doing the following:

```bash
mvn exec:java -Dexec.mainClass="com.jedis_work.app.Forum"
```

### "Forum" with different approaches

To run the program that writes and reads to the database by using a set we run the following: 

```bash
mvn exec:java -Dexec.mainClass="redis.SimplePost"
```

To run the program that writes and reads to the database by using a list we run the following: 

```bash
mvn exec:java -Dexec.mainClass="redis.SimplePostList"
```

To run the program that writes and reads to the database by using a hashmap we run the following: 

```bash
mvn exec:java -Dexec.mainClass="redis.SimplePostHash"
```

### Clean Redis

```bash
redis-cli flushall
```

> Note: It is neeeded to run this command every time we want to change the forum backend aproach
>