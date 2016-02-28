package pprg.philosopher;

public class Application {
    public static void main(String[] args) {
        Diner diner = new Diner(1, 1, 2);
        diner.startDinner();
    }
}
