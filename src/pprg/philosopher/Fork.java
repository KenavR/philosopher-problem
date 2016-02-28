package pprg.philosopher;

import pprg.philosopher.logging.PhilosopherLogger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Fork extends ReentrantLock {

    public enum ForkPosition {
        LEFT, RIGHT
    }

    private final Logger log = PhilosopherLogger.getInstance();
    private final int place;

    public Fork(int place) {
        this.place = place;
    }

    public void take(Philosopher owner, ForkPosition side) throws InterruptedException {
        while (!this.tryLock(10, TimeUnit.MILLISECONDS)) ;
        log.fine("Philosopher " + owner.getSeat() + " took his " + side + " fork.");
    }

    public void putBack(Philosopher owner, ForkPosition side) {
        this.unlock();
        log.fine("Philosopher " + owner.getSeat() + " put back his " + side + " fork.");
    }
}
