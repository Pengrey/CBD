## Redis based forum

This project is a redis DB based chat forum written in java with the help of the redis client jedis.

### Features

```bash
+-----------------------------------------------------------------------------+
| Commands:                                                                   |
+--------------------------------------+--------------------------------------+
| login                                | Start login progress                 |
| create                               | Start account creation process       |
| send [message]                       | Send message to system               |
| notifs                               | Give number of new messages unread   |
| msgs                                 | Display unread messages              |
| online                               | Display online users                 |
| follow [username]                    | Follow user                          |
| following                            | Display followed users               |
| unfollow [username]                  | Unfollow user                        |
| followers                            | Display followers                    |
| help                                 | Display this prompt                  |
| quit                                 | Exit program                         |
+--------------------------------------+--------------------------------------+
```

### Usage

Start DB:

```bash
redis-server
```

Create Forum Server and get its status:

```bash
mvn exec:java -Dexec.mainClass="jedis_chat.app.Server"
```

Start Client:

```bash
mvn exec:java -Dexec.mainClass="jedis_chat.app.Client"
```

Start Client with prompt on:

```bash
mvn exec:java -Dexec.mainClass="jedis_chat.app.Client" -Dexec.args="-p"
```

output:

```bash
	    _____          _ _       ______                         
           |  __ \        | (_)     |  ____|                        
           | |__) |___  __| |_ ___  | |__ ___  _ __ _   _ _ __ ___  
           |  _  // _ \/ _` | / __| |  __/ _ \| '__| | | | '_ ` _ \ 
           | | \ \  __/ (_| | \__ \ | | | (_) | |  | |_| | | | | | |
           |_|  \_\___|\__,_|_|___/ |_|  \___/|_|   \__,_|_| |_| |_|

+-----------------------------------------------------------------------------+
|####################################Login####################################|
+-----------------------------------------------------------------------------+
| Commands:                                                                   |
+--------------------------------------+--------------------------------------+
| login                                | Start login progress                 |
| create                               | Start account creation process       |
| quit                                 | Exit program                         |
+--------------------------------------+--------------------------------------+
>
```

clean database:

```bash
redis-cli flushall
```

### Backend:

For the data base we use Redis as the name indicates, more precisely we use sets.

The sets that are used follow a very specific notation:

```bash
username ---- password
	    |
	    -- followers
  	    |
	    -- following
	    |
	    -- messages
	    |
	    -- isUser

# Special set used for online tracking:
IsOnline ---- username
```

This notation makes it easier for us to have different sets for different users, we do this by following the notation and divide each path edge with an "/", for example if we wanted to get the followers of the user we just need to check the set with the key "username/followers" or if we want to see if the user exists we just need to check if "username/isUser" has one value (used to represent if an user exists or not).
