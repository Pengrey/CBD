### Script:

```python
#!/usr/bin/python

def read_file(readFileName):
    f = open(readFileName,"r")
    return f.readlines()

def count_words(array, word_counts):
    for word in array:
        letter = word[0].upper()
        if letter in word_counts:
            word_counts[letter] = word_counts[letter] + 1
        else:
            word_counts[letter] = 1

def write_file(writeFileName, word_counts):
    f = open(writeFileName, "w")
    for letter in word_counts:
        f.write("SET " + letter + " " + str(word_counts[letter]) + "\n")
    f.close()

def main():
    word_counts = {}
    readFileName = input("Enter file name to count: ")
    array = read_file(readFileName)
    count_words(array,word_counts)
    writeFileName = input("Enter file name to write: ")
    write_file(writeFileName, word_counts)
    print("DONE")

if __name__=="__main__":
    main()
```

### Running:

```bash
pengrey@pengrey-sy > ./script.py
Enter file name to count: names.txt
Enter file name to write: names_counting.txt
DONE
pengrey@pengrey-sy > cat names_counting.txt 
SET A 463
SET B 257
SET C 463
SET D 315
SET E 248
SET F 135
SET G 210
SET H 121
SET I 74
SET J 290
SET K 268
SET L 335
SET M 471
SET N 152
SET O 63
SET P 118
SET Q 8
SET R 243
SET S 295
SET T 193
SET U 15
SET V 104
SET W 57
SET X 5
SET Y 21
SET Z 31
pengrey@pengrey-sy >
```

### Redis Mass Insertion

Sometimes Redis instances need to be loaded with a big amount of preexisting or user generated data in a short amount of time, so that millions of keys will be created as fast as possible.

This is called a *mass insertion*, and the goal of this document is to provide information about how to feed Redis with data as fast as possible.

One naive way to acomplish mass insertion is by using the tool netcat:

```bash
(cat data.txt; sleep 10) | nc localhost 6379 > /dev/null
```

However this is not a very reliable way to perform mass import because netcat does not really know when all the data was transferred and can't check for errors. In 2.6 or later versions of Redis the `redis-cli` utility supports a new mode called **pipe mode** that was designed in order to perform mass insertion.

Using the pipe mode the command to run looks like the following:

```bash
cat data.txt | redis-cli --pipe
```

The redis-cli utility will also make sure to only redirect errors received from the Redis instance to the standard output.

### In our specific example we use

After using the script to prepare the file to use it for mass insertion we just need to run the following command:

```bash
cat names_counting.txt | redis-cli --pipe
```

Output:

```bash
pengrey@pengrey-sy > cat names_counting.txt | redis-cli --pipe
All data transferred. Waiting for the last reply...
Last reply received from server.
errors: 0, replies: 26
pengrey@pengrey-sy >
```