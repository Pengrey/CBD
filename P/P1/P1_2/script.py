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