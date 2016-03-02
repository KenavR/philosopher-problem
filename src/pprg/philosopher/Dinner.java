package pprg.philosopher;

import pprg.philosopher.test.ConsoleProgressBar;
import pprg.philosopher.test.TestSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Dinner {

    private final int maxThinkingTime;
    private final int maxEatingTime;
    private final int numberOfPhilsophers;
    private ArrayList<Philosopher> philosophers = new ArrayList<>();
    private final List<Fork> forks;
    private TestSettings testSettings = new TestSettings();

    public Dinner(int numberOfPhilsophers, int maxThinkTime, int maxEatingTime, long timeoutInMS) {
        this.maxThinkingTime = maxThinkTime;
        this.maxEatingTime = maxEatingTime;
        this.numberOfPhilsophers = numberOfPhilsophers;
        this.forks = getForks(this.numberOfPhilsophers);

        this.testSettings = new TestSettings(timeoutInMS);
    }

    public Dinner(int numberOfPhilsophers, int maxThinkTime, int maxEatingTime) {
        this(maxThinkTime, maxEatingTime, numberOfPhilsophers, 10000l);
    }

    public List<Fork> getForks(int numberOfForks) {
        List<Fork> forks = new ArrayList<>();
        IntStream.range(0, numberOfForks).forEach(idx -> {
            forks.add(new Fork(idx));
        });
        return forks;
    }

    public void startDinner() {
        startDinner(false);
    }

    public void startDinnerWithOneRighty() {
        startDinner(true);
    }

    private void startDinner(boolean fixed) {
        IntStream.range(0, this.numberOfPhilsophers).forEach(i -> {
            boolean lefty = !(fixed && i == 0);
            Philosopher philosopher = new Philosopher(i, this.forks.get(i), this.forks.get((i + 1) % this.numberOfPhilsophers), this.maxThinkingTime, this.maxEatingTime, lefty, testSettings);
            philosophers.add(i, philosopher);
        });

        ExecutorService executor = Executors.newFixedThreadPool(this.numberOfPhilsophers);
        try {
            System.out.println(String.format("Test for %s started with %d philosophers, %d ms thinking time, %d ms eating time and a %d ms timeout",(fixed) ? "'Fixed Dinner'" : "'Deadlock Dinner'",  this.numberOfPhilsophers, this.maxThinkingTime, this.maxEatingTime, testSettings.getTimeout()));
            Timer timer = new Timer();
            int steps = (int)testSettings.getTimeout()/3000;
            timer.schedule(new ConsoleProgressBar(steps), 1000, testSettings.getTimeout()/steps);
            executor.invokeAll(philosophers, testSettings.getTimeout(), TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Handled");
        } finally {
            finalOutput(fixed);
        }
    }

    private void finalOutput(boolean fixed) {
        long timeSum = 0;
        System.out.println();
        System.out.println("########################################################################");
        System.out.println(String.format("This test was executed with the following parameters: "));
        System.out.println(String.format("- Philosophers: %d ", this.numberOfPhilsophers));
        System.out.println(String.format("- Max thinking time: %d ms", this.maxThinkingTime));
        System.out.println(String.format("- Max eating time: %d ms", this.maxEatingTime));
        System.out.println(String.format("- timeout: %d ms", this.testSettings.getTimeout()));
        System.out.println();
        System.out.println("------------------------------ Result ----------------------------------");
        for (Philosopher phil : philosophers) {
            System.out.println(String.format("Philosopher %d ate %d times.", phil.getSeat(), phil.getBites()));
            timeSum += phil.getLastBite();
        }
        System.out.println();
        if (fixed)
            System.out.println("No deadlock occured!");
        else
            System.out.println(String.format("The deadlock occured after %d ms.", timeSum / philosophers.size()));

        System.out.println("########################################################################");
        System.out.println();
    }
}
