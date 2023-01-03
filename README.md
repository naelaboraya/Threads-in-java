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

### Tests & Results

We have tested the functions with different number of files and different values of "seed" and "bound"  to generate a different random number in each test , 
    we compared the results of each funtion and compared the time has taken by each funtion to complete its task. You can see and modify the tests in class "Test_Ex2_1" , you can change the values of "seed" and "bound" and the number of files as you want. 
    
   * Tests were conducted on an Asus laptop with an Intel Core i5 processor and 8GB of RAM running Windows 10.

  *  The results showed that the thread pool implementation was the fastest, followed by the threads implementation, and the regular implementation was the slowest.    

```
Results for testing the functions with number_of_files = 10 , seed = System.currentTimeMillis() to test a different seed each time , and bound = 100 :
    
    1. Normal function : 
       Total number of lines = 433
       Time for first function = 0.105 seconds
    
    2. Thread function : 
       Total number of lines = 433
       Time for second function = 0.039 seconds
    
    3. ThreadPool function :
       Total number of lines = 433
       Time for third function = 0.025 seconds
    
    
Results for testing the functions with number_of_files = 100 , seed = System.currentTimeMillis() to test a different seed each time , and bound = 1000 :
    
    1. Normal function : 
       Total number of lines = 53219
       Time for first function = 1.594 seconds
    
    2. Thread function : 
       Total number of lines = 53219
       Time for second function = 0.11 seconds
    
    3. ThreadPool function :
       Total number of lines = 53219
       Time for third function = 0.082 seconds    
   
    
Results for testing the functions with number_of_files = 1000 , seed = System.currentTimeMillis() to test a different seed each time , and bound = 10000 :
    
    1. Normal function : 
       Total number of lines = 4964953
       Time for first function = 4.538 seconds
    
    2. Thread function : 
       Total number of lines = 4964953
       Time for second function = 0.82 seconds
    
    3. ThreadPool function :
       Total number of lines = 4964953
       Time for third function = 1.195 seconds 
    

Results for testing the functions with number_of_files = 1000 , seed = System.currentTimeMillis() to test a different seed each time , and bound = 100000 :
    
    1. Normal function : 
       Total number of lines = 50584930
       Time for first function = 7.5 seconds
    
    2. Thread function : 
       Total number of lines = 50584930
       Time for second function = 3.463 seconds
    
    3. ThreadPool function :
       Total number of lines = 50584930
       Time for third function = 1.227 seconds 
    
    
Results for testing the functions with number_of_files = 10000 , seed = System.currentTimeMillis() to test a different seed each time , and bound = 100000 :
    
    1. Normal function : 
       Total number of lines = 502004362
       Time for first function = 86.497 seconds
    
    2. Thread function : 
       Total number of lines = 502004362
       Time for second function = 44.212 seconds
    
    3. ThreadPool function :
       Total number of lines = 502004362
       Time for third function = 16.121 seconds     
    
```

## Conclusion
    

In conclusion, the results of the tests show that the thread pool implementation is the most efficient approach for calculating the total number of lines in a group of text files. This is because the thread pool creates a fixed number of threads that can be reused for multiple tasks, rather than creating a new thread for each task as in the case of the thread implementation. This results in a reduction of overhead and improved performance. Additionally, the normal implementation, which does not use threads, must complete each task sequentially, leading to slower overall performance compared to the other two approaches.   
    

## Built With

* [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) - The Java integrated development environment used for coding and building the project.

## Contributing

If you have any suggestions or comments, feel free to create a pull request or open an issue. Your contributions are always welcome!


## Authors

*  [Nael Aboraya](https://github.com/naelaboraya)



