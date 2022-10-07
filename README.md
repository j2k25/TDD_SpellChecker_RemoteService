# TDD_SpellChecker_RemoteService
Test Driven Development using Design Principles #2


We will write a program that will spellcheck the words in plain text file, using a specific service. When the program starts, key in the path to the file. If the file does not exist or can't be read, display an appropriate error and exit gracefully. If the file's content can be read, then extract one word at a time, send it off to a remote service to spell check. If the service reports that the spelling is correct, display the word as is. If the spelling is reported as incorrect, then pad the word with __ on both sides. After the file's content is displayed, give a blank line and report the number of words, number of typos, and the % of errors to overall number of words.

For example, if the file contains

This si all queit interesting

The output will be

This __si__ all __queit__ interesting

Total words: 5
Typos: 2
Errors: 40%


Create the program to use a particular service, but design in a way that we can switch to other services in the future, if we desire, 
without having to change much of the existing code, but by writing new code only to integrate with the new service.

The service at the location http://agilec.cs.uh.edu/spell may be used for checking spelling. For example, 

http://agilec.cs.uh.edu/spell?check=right will return true
http://agilec.cs.uh.edu/spell?check=rihgt will return false

Please bring forward the practices, techniques, and tools you have learned so far. This includes: 
Good code quality
Lightweight design
Minimum code
Automated testing                                                         
Code coverage
Build files (I created them for you in assign1, but it's your turn now to practice and learn further)
Change build.sh in your repository to point to assign2 instead of assign1

Total [100]: 100
Program works as expected [20]: 
Programm gracefully handles network failure [10]: 
All tests pass [10]:
Test coverage [10]:
Test quality [10]:
Design quality, good separation of concerns [20]: 
Code quality [20]:
