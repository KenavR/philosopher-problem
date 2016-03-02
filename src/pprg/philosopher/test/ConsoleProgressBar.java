package pprg.philosopher.test;

import java.util.TimerTask;
import java.util.stream.IntStream;

public class ConsoleProgressBar extends TimerTask {

    private int steps;
    private int counter = 0;

    public ConsoleProgressBar(int steps) {
        this.steps = steps;
    }

    @Override
    public void run() {
        counter++;
        //IntStream.range(0, 10).forEach((i) -> System.out.println());
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        IntStream.range(0, counter).forEach((i) -> sb.append("===="));
        IntStream.range(counter, steps).forEach((i) -> sb.append("    "));
        sb.append("|");
        System.out.println(sb.toString());
        if (counter >= steps)
            this.cancel();

    }
}
