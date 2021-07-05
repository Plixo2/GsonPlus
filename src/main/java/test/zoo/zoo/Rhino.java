package test.zoo.zoo;

public class Rhino extends Animal{

    public Rhino() {
        super(4);
    }

    @Override
    public void onDeath() {
        System.out.println("Nilpferd ist tot :(");
    }
}
