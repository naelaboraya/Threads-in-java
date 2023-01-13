# <strong>Threads in java</strong>
![threads5](https://user-images.githubusercontent.com/94143804/212205758-d9708725-68bb-4fe9-98dd-f8fcd7c62291.jpg)


 This project is a collection of Java classes and functions that demonstrate how to use threads to perform tasks concurrently and efficiently. 
 <p>The project includes examples of using threads to count the number of lines in text files and using a thread pool to perform the same task.
 <p>It also includes an implementation of ThreadPoolExecutor with priorities for the tasks that it takes.



# This project has two parts :

# Part I
### This part deals with generating a random number of text files and applying functions on them. 

    In this part, you will find a Java class named "Ex2_1" that demonstrates different ways of counting the number of lines in a set of text files.
    
    These methods are:
    
    1. A normal, sequential method that reads each file one by one and counts the lines.
    2. A method that uses multiple threads, one per file, to count the lines concurrently.
    3. A method that uses a fixed-size thread pool to count the lines concurrently.



The main method in the Ex2_1 class demonstrates how to use the three methods for counting the lines in a set of text files. You can modify the main method to test different scenarios and input files.


## Code structure

 * The Ex2_1 class contains the method "createTextFiles" that creates text files and saves them to a folder called "OutputFiles" and another three methods for counting the lines in the text files , one is a normal method , the second uses Threads , and the third uses ThreadPool.

 * The NumOfLinesThreads class is an inner class that extends the Thread class and is used in the second method for counting the lines.

 * The NumOfLinesThreadPool class is an inner class that implements the Callable interface and is used in the third method for counting the lines.

## UML Diagram for part I

![UML_Ex2_1](https://user-images.githubusercontent.com/94143804/210369991-1ba8c858-9d73-418b-9b67-c15d0e44f784.png)


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
    
    
Results for testing the functions with number_of_files = 100 , seed = 1 , and bound = 100000 :
    
    1. Normal function : 
       Total number of lines = 5686229
       Time for first function = 0.5783178 seconds
    
    2. Thread function : 
       Total number of lines = 5686229
       Time for second function = 0.1834518 seconds
    
    3. ThreadPool function :
       Total number of lines = 5686229
       Time for third function = 0.162134 seconds    
   
     
    

Results for testing the functions with number_of_files = 1000 , seed = 12 , and bound = 100000 :
    
    1. Normal function : 
       Total number of lines = 50782123
       Time for first function = 4.186 seconds
    
    2. Thread function : 
       Total number of lines = 50782123
       Time for second function = 1.687 seconds
    
    3. ThreadPool function :
       Total number of lines = 50782123
       Time for third function = 1.847 seconds 
    
    
Results for testing the functions with number_of_files = 10000 , seed = 10 , and bound = 100000 :
    
    1. Normal function : 
       Total number of lines = 501295148
       Time for first function = 49.188 seconds
    
    2. Thread function : 
       Total number of lines = 501295148
       Time for second function = 16.143 seconds
    
    3. ThreadPool function :
       Total number of lines = 501295148
       Time for third function = 20.377 seconds    


*For screenshots of the results please visit our wiki!
```


## Conclusion
    

In conclusion, the results of the tests show that the thread pool implementation is the most efficient approach for calculating the total number of lines in a group of text files. This is because the thread pool creates a fixed number of threads that can be reused for multiple tasks, rather than creating a new thread for each task as in the case of the thread implementation. This results in a reduction of overhead and improved performance. Additionally, the normal implementation, which does not use threads, must complete each task sequentially, leading to slower overall performance compared to the other two approaches.   
    
# Part II
### In this part we have implemented a ThreadPool executor that deals with tasks that have a priority , it holds a priority queue for that , and executes the tasks according to the their priorities. 
    
    In this part, we have implemented Java class named "Task" that represents a callable task (it implements Callable interface),
    and has a Type ("COMPUTATIONAL" , "IO" or "OTHER") , each type of these three types has an integer value (1,2,3 in order), 
    1 is the highest priority and 10 is the lowest.   
    
    We also implemented "CustomExecutor" class that represents a ThreadPoolExecutor that deals with tasks of type "Task", 
    it stores the tasks in a HashMap and executes them according to their priorites (by using PriorityQueue in the executor's constructor),
    that means that   task with a higher priority executed first.

## Code structure
    
 * The "Task" class - represents a task with callable operation and a type , it implements "Callable" interface. 
    It has a private constructor, so it can not be initialized using the keyword "new", a task can be constructed 
    using one of its factory methods. 
    * There are three methods that do this work by calling the private constructor :  
       1. "of" - takes a Callable and creates a new task from it, sets the type to be "OTHER" as default.
       2. "of" - takes a TaskType and a Callable , creates a task from this Callable and sets the task's type to be as the given TaskType.
       3. "createTask" - does the same work as the previous function , takes a Callable and a TaskType as input , creates a task from this Callable and sets the task's type to be as the given TaskType.
    
 * The "ComparableTask" class - a custom implementation of the FutureTask class that implements the Comparable interface. 
    It provides a way to compare tasks based on their priorities. 
    The ComparableTask class is used as an adapter, it adapts the FutureTask class to make it comparable based on the priorities of the tasks. The main purpose of this class is to allow tasks to be prioritized in a queue,
 so that the tasks with higher priorities are executed first.
    * Methods and properties of class ComparableTask : 
      1. It provides a constructor that takes a Callable task and creates a FutureTask and a Task object from it. 
      2. It also provides a getTask method that returns the Task object.
      3. The compareTo method compares the priorities of two tasks and returns a negative integer, zero, or a positive integer as this task is less than,
 equal to, or greater than the specified task.
    
 * The "CustomExecutor" class - a custom implementation of the ThreadPoolExecutor.
    It provides a fixed number of threads and uses a PriorityBlockingQueue to hold the tasks.
    The tasks are also stored in a HashMap where the key is the task's priority and the value is the task itself.
    The thread pool is initialized with a minimum pool size of {@code MinPoolSize} (which is the number of the cores in the computer divided by 2),
 a maximum pool size of {@code MaxPoolSize} (which is the number of the cores in the computer minus 1),
 a keep-alive time of 300 ms.
    * Methods and properties of class CustomExecutor : 
      1. This class provides methods for getting the tasks, setting max priority and submitting tasks to the queue.
      2. The class also overrides the "newTaskFor" method, in order to provide a custom RunnableFuture implementation, which allows the task
 to be prioritized based on its natural ordering or a comparator provided at construction time.
      3. The class provides a "submit" method which adds the submitted task to the HashMap and updates the max priority if necessary.
      4. The class has a function "gracefullyTerminate" that shuts down the executor.
     
## UML Diagram for part II
     
![Package Ex2_2 (1)](https://user-images.githubusercontent.com/94143804/212206548-1a8ad370-7260-4093-b2b5-1414635d2c89.png)

    
## Prerequisites 

To run the code in this project, you will need :

 * Java 8 or higher

 * Any Java IDE (e.g. IntelliJ, Eclipse)



## Usage

To use the code, you can either import the project into your IDE and run the main method in the Ex2_1.Test_Ex2_1 class, or you can compile and run the code using the javac and java command-line tools.
To use part 2 , you can see the test class "Tests" in package "Ex2_2" , you can modify the tasks as you want and do your own tests. 
You can also import the package into your project and use the classes to prioritize and execute your own tasks.
     
## Built With

* [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) - The Java integrated development environment used for coding and building the project.

## Contributing

If you have any suggestions or comments, feel free to create a pull request or open an issue. Your contributions are always welcome!


## Authors

*  [Nael Aboraya](https://github.com/naelaboraya)
*  [Marwan Hresh](https://github.com/marwanhresh)


