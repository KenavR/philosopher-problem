package pprg.philosopher;

import jdk.nashorn.internal.ir.Symbol;
import pprg.philosopher.logging.PhilosopherLogger;
import pprg.philosopher.test.TestSettings;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Philosopher implements Callable<Philosopher> {

    private final Logger log = PhilosopherLogger.getInstance();

    private final int seat;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int maxThinkingTime;
    private final int maxEatingTime;
    private final Random rdm = new Random();
    private final boolean lefty; // starts with the left fork

    private TestSettings testSettings = new TestSettings();

    private long lastBite = 0;
    private int bites = 0;

    public Philosopher(int seat, Fork leftFork, Fork rightFork, int maxThinkingTime, int maxEatingTime, boolean lefty) {
        this.seat = seat;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.maxEatingTime = maxEatingTime;
        this.maxThinkingTime = maxThinkingTime;
        this.lefty = lefty;
    }

    public Philosopher(int seat, Fork leftFork, Fork rightFork, int maxThinkingTime, int maxEatingTime, boolean lefty, TestSettings settings) {
        this.seat = seat;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.maxEatingTime = maxEatingTime;
        this.maxThinkingTime = maxThinkingTime;
        this.lefty = lefty;

        this.testSettings = settings;
    }

    public int getSeat() {
        return seat;
    }

    public boolean isLefty() {
        return lefty;
    }

    public int getBites() {
        return bites;
    }

    public long getLastBite() {
        return lastBite;
    }

    private void think() throws InterruptedException {
        log.fine("Philosopher " + this.seat + " is now thinking.");
        Thread.sleep(rdm.nextInt(this.maxThinkingTime));
    }

    private void takeForks() throws InterruptedException {
        if (this.isLefty()) {
            leftFork.take(this, Fork.ForkPosition.LEFT);
            rightFork.take(this, Fork.ForkPosition.RIGHT);
        } else {
            rightFork.take(this, Fork.ForkPosition.RIGHT);
            leftFork.take(this, Fork.ForkPosition.LEFT);
        }
    }

    private void putForksBack() {
        rightFork.putBack(this, Fork.ForkPosition.RIGHT);
        leftFork.putBack(this, Fork.ForkPosition.LEFT);
    }

    private void eat() throws InterruptedException {
        bites++;
        log.fine("Philosopher " + this.seat + " is now eating.");
        Thread.sleep(rdm.nextInt(this.maxEatingTime));
    }


    public void dine() {
        try {
            think();
            log.fine("Philosopher " + this.seat + " wants to eat now.");
            takeForks();
            eat();
            putForksBack();
            log.fine("Philosopher " + this.seat + " is done eating.");
        } catch (InterruptedException e) {
            log.fine(String.format("The thread for philsopher %d was canceled.", this.getSeat()));
        }
    }

    @Override
    public Philosopher call() throws Exception {
        Date start = new Date();
        while (true) {
            dine();
            long timeElapsed = new Date().getTime() - start.getTime();
            if(isElapsedTimeInTimeoutRange(timeElapsed, testSettings.getTimeout(), 10)) {
                lastBite = timeElapsed;
                log.fine("Time elapsed since start: " + (new Date().getTime() - start.getTime()));
            }
        }
    }

    private boolean isElapsedTimeInTimeoutRange(long elapsedTime, long timeout, int deviation) {
        return elapsedTime < timeout-deviation;
    }
}
