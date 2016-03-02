package pprg.philosopher;

import java.io.IOException;

public class Application {

    /**
     * The expects 0 to 5 arguments. The arguments are
     * Exercise number: 1 or 2; default=1
     * Number of philosophers: e.g. 5; default=3
     * Max thinking time in ms: e.g. 100; default=10
     * Max eating time in ms: e.g. 25; default=10
     * Timeout in ms: e.g. 30000; default=10000
     *
     * Example: java -jar philosopher-problem.jar 1 5 10 100 30000
     */

    public static void main(String[] args) {
        final Exercise exercise = contains(args, 0) ? Exercise.findByNumber(args[0]) : Exercise.DEADLOCK;
        final int philosophers = contains(args, 1) ? Integer.valueOf(args[1]) : 3;
        final int thinkingtime = contains(args, 2) ? Integer.valueOf(args[2]) : 10;
        final int eatingtime = contains(args, 3) ? Integer.valueOf(args[3]) : 10;
        final int timeoutInMS = contains(args, 4) ? Integer.valueOf(args[4]) : 10000;

        Dinner dinner = new Dinner(philosophers, thinkingtime, eatingtime, timeoutInMS);
        if (exercise == Exercise.FIXED) dinner.startDinnerWithOneRighty();
        else dinner.startDinner();

        exit();
    }

    public static boolean contains(String[] args, int index) {
        return (args.length > index && args[index] != null);
    }

    public static void exit() {
        System.out.println("Press enter to exit");
        try {
            int key = 0;
            do {
                key = System.in.read();
                if (key == 10) System.exit(0);
            } while (key != 10);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
