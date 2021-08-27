package test.car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car {

    public int age;
    public float speed;
    public String name;
    public Vector2D size;
    public boolean isRed;
    public long aLong = (long) (Math.random() * 100000);
    public byte aByte = (byte) (Math.random() * 200);
    public char aChar = (char) (Math.random() * 100);
    public short aShort = (short) (Math.random() * 1000);
    public Runnable runnableTest = () -> System.out.println("Hello World!");

    public List<String> names;

    public Car(int age, String name, float speed, Vector2D size, boolean isRed, String... names) {
        this.age = age;
        this.name = name;
        this.speed = speed;
        this.size = size;
        this.isRed = isRed;
        this.names = new ArrayList<>(Arrays.asList(names));
    }

    @Override
    public String toString() {
        return "Car{" +
                "age=" + age +
                ", speed=" + speed +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", isRed=" + isRed +
                ", aLong=" + aLong +
                ", aByte=" + aByte +
                ", aChar=" + aChar +
                ", aShort=" + aShort +
                ", names=" + names +
                '}';
    }
}
