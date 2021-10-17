### Run the programm:

```bash
mvn exec:java -Dexec.mainClass="autocomplete.app.App"
```

### Run redis server:

```bash
redis-server
```

### Clean redis db:

```bash
redis-cli flushall
```

> Note: Apoach a) uses names.txt and finds the names by using a set where the names are the keys and the value is 0 due to memory, aproach b) uses nomes-pt-2021.csv and saves the names with their respective weight on a sorted set, to get the values we use zrangeByLex by giving the name we have and the name thats is next in line (name that we have with his final char being the next char from the last char of name we have)
>
