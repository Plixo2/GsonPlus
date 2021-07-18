package test.zoo.zoo;

public class Giraffe extends Animal {

    public Giraffe() {
        super(6);
    }

    @Override
    public void onDeath() {
        System.out.println("Giraffe is dead :(");
    }
}
