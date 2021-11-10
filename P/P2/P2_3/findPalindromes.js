findPalindromes = function () {
    var allNumbers = db.phones.find({},{"display": 1, "_id": 0}).toArray();

    var palindrome_numbers = []
    
    for (var i = 0 ; i<allNumbers.length ; i++){
        var palindrome_number = true

        // Get real number from the output of mongo
        var number = allNumbers[i].display
        number = number.split("-")[1]

        // Iterate over digits to check it its a palindrome
        for (var d = 0; d < 9; d++) {
            if (number[d] !== number[8 - d]) { 
                palindrome_number = false; 
            }
        }
        // If the number is palindrome we add it to the list
        if (palindrome_number){
            palindrome_numbers.push(allNumbers[i])
        }
        
    }

    return palindrome_numbers;
}
