package main;

public class Lion extends Warrior{
    private int loyalty;

    public Lion(String name, int blood, int bloodHeadquarter) {
        super(name, blood);
        this.loyalty = bloodHeadquarter;
    }

    public String bornMessage() {
        return "It's loyalty is " + loyalty;
    }
}
