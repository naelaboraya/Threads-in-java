# <strong>Threads in java</strong>

This project is a collection of Java functions that demonstrate how to use threads to perform tasks concurrently and efficiently. 
<p>The project includes examples of using threads to count the number of lines in text files and using a thread pool to perform the same task.



# This project has two parts :

## Part I
### This part Deals with generating a random number of text files and applying functions on them. 

    In this part, you will find a Java class named "Ex2_1" that demonstrates different ways of counting the number of lines in a set of text files.
    
    These methods are:
    
    1. A normal, sequential method that reads each file one by one and counts the lines.
    2. A method that uses multiple threads, one per file, to count the lines concurrently.
    3. A method that uses a fixed-size thread pool to count the lines concurrently.

### Prerequisites

To run the code in this project, you will need :

 * Java 8 or higher

 * Any Java IDE (e.g. IntelliJ, Eclipse)



### Usage

To use the code, you can either import the project into your IDE and run the main method in the Test_Ex2_1 class, or you can compile and run the code using the javac and java command-line tools.

The main method in the Ex2_1 class demonstrates how to use the three methods for counting the lines in a set of text files. You can modify the main method to test different scenarios and input files.


## Code structure

 * The Ex2_1 class contains the method "createTextFiles" that creates text files and saves them to a folder and anothe three methods for counting the lines in the text   files , one method is normal , the second method uses Threads , and the third uses ThreadPool.

 * The NumOfLinesThreads class is an inner class that extends the Thread class and is used in the second method for counting the lines.

 * The NumOfLinesThreadPool class is an inner class that implements the Callable interface and is used in the third method for counting the lines.

### Break down into end to end tests

xxxx

```
yyyy
```


## Built With

* [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) - The Java integrated development environment used for coding and building the project.

## Contributing

If you have any suggestions or comments, feel free to create a pull request or open an issue. Your contributions are always welcome!


## Authors

*  [Nael Aboraya](https://github.com/naelaboraya)



