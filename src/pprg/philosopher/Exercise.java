package pprg.philosopher;

public enum Exercise {
    DEADLOCK("1"), FIXED("2");

    private String number;

    Exercise(String number){
        this.number = number;
    }

    public static Exercise findByNumber(String ex){
        for(Exercise exercise : values()){
            if( exercise.number.equals(ex)){
                return exercise;
            }
        }
        return Exercise.DEADLOCK;
    }
}
