# Dining Philosopher Problem
An example implementation of the [dining philosopher problem](https://en.wikipedia.org/wiki/Dining_philosophers_problem) written in Java to measure the correlation between the number of philosophers, their thinking- and eating time and the elapsed time before a deadlock occurs.

## Execute
The project includes a executable JAR file. To start it run 

``` java -jar philosopher-problem.jar ```

or

``` java -jar philosopher-problem.jar 1 5 10 100 30000```

to pass paramaters to the JAR file. The parameters are 

* Exercise number: 1 (deadlock) or 2 (fixed); default is 1
* Number of philosophers (e.g. 5); default is 3
* Max thinking time in ms (e.g. 10); default is 10
* Max eating time in ms (e.g. 100); default is 10
* Timeout in ms (e.g. 30000)
