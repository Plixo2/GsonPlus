package test.zoo.zoo;

import test.zoo.math.Vector2D;

public abstract class Creature {
    public int age = 0;
    int maxAge;
    public boolean dead = false;
    public Type type = Type.LAND;

    public Creature(int maxAge) {
        this.maxAge = maxAge;
    }


    public void advanceAge() {
        age += 1;
        if(age >= maxAge) {
            kill();
            age = maxAge;
        } else {
            dead = false;
        }
    }

    public void kill() {
        this.dead = true;
        onDeath();
    }

    @Override
    public String toString() {
        return "Creature{" +
                "age=" + age +
                ", maxAge=" + maxAge +
                ", dead=" + dead +
                ", type=" + type +
                '}';
    }

    public abstract void onDeath();

    public enum Type {
        WATER,LAND,AIR
    }
}
