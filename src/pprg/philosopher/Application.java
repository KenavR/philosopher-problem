package pprg.philosopher;

public class Application {
    public static void main(String[] args) {
        Dinner dinner = new Dinner(1, 1, 2);
        dinner.startDinner();
    }
}
