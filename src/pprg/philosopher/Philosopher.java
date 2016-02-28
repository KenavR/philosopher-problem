package pprg.philosopher;

import jdk.nashorn.internal.ir.Symbol;
import pprg.philosopher.logging.PhilosopherLogger;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

public class Philosopher implements Runnable {

    private final Logger log = PhilosopherLogger.getInstance();

    private final int seat;
    private final Fork leftFork;
    private final Fork rightFork;
    private final int maxThinkingTime;
    private final int maxEatingTime;
    private final Random rdm = new Random();
    private final boolean lefty; // starts with the left fork

    public Philosopher(int seat, Fork leftFork, Fork rightFork, int maxThinkingTime, int maxEatingTime, boolean lefty) {
        this.seat = seat;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.maxEatingTime = maxEatingTime;
        this.maxThinkingTime = maxThinkingTime;
        this.lefty = lefty;

    }

    public int getSeat() {
        return seat;
    }

    public boolean isLefty() {
        return lefty;
    }

    public void think() throws InterruptedException {
        log.fine("Philosopher " + this.seat + " is now thinking.");
        Thread.sleep(rdm.nextInt(this.maxThinkingTime));
    }

    private void eat() throws InterruptedException {
        log.fine("Philosopher " + this.seat + " is now eating.");
        Thread.sleep(rdm.nextInt(this.maxEatingTime));
    }


    public void dine() {
        try {
            think();

            log.fine("Philosopher " + this.seat + " wants to eat now.");

            if(this.isLefty()) {
                System.out.println("left");
                leftFork.take(this, Fork.ForkPosition.LEFT);
                rightFork.take(this, Fork.ForkPosition.RIGHT);
            } else {
                rightFork.take(this, Fork.ForkPosition.RIGHT);
                leftFork.take(this, Fork.ForkPosition.LEFT);
            }

            eat();
            rightFork.putBack(this, Fork.ForkPosition.RIGHT);
            leftFork.putBack(this, Fork.ForkPosition.LEFT);

            log.fine("Philosopher " + this.seat + " is done eating.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Date start = new Date();
        while(true) {
            log.info("Time elapsed since start: "+( new Date().getTime() - start.getTime() ));
            dine();

        }
    }
}
