package pprg.philosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Diner {

    private final int maxThinkingTime;
    private final int maxEatingTime;
    private final int numberOfPhilsophers;
    private ArrayList<Philosopher> philosophers = new ArrayList<>();
    private final List<Fork> forks;

    public Diner(int maxThinkTime, int maxEatingTime, int numberOfPhilsophers) {
        this.maxThinkingTime = maxThinkTime;
        this.maxEatingTime = maxEatingTime;
        this.numberOfPhilsophers = numberOfPhilsophers;
        this.forks = getForks(this.numberOfPhilsophers);
    }

    public List<Fork> getForks(int numberOfForks) {
        List<Fork> forks = new ArrayList<>();
        IntStream.range(0, numberOfForks).forEach(idx -> {
            forks.add(new Fork(idx));
        });
        return forks;
    }

    public void startDinner() {
        IntStream.range(0, this.numberOfPhilsophers).forEach(i -> {
            Philosopher philosopher = new Philosopher(i, this.forks.get(i), this.forks.get((i+1)% this.numberOfPhilsophers), this.maxThinkingTime, this.maxEatingTime, true);
            philosophers.add(i, philosopher);

            Thread thread = new Thread(philosopher);
            thread.start();
        });
    }

    public void startDinnerWithOneRighty() {
        IntStream.range(0, this.numberOfPhilsophers).forEach(i -> {
            Philosopher philosopher = new Philosopher(i, this.forks.get(i), this.forks.get((i+1)% this.numberOfPhilsophers), this.maxThinkingTime, this.maxEatingTime, i != 0);
            philosophers.add(i, philosopher);

            Thread thread = new Thread(philosopher);
            thread.start();
        });
    }
}
