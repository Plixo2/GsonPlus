package test.zoo.zoo;

import test.zoo.math.Vector2D;

public abstract class Creature {
    int age = 0;
    int maxAge;
    boolean dead = false;

    public transient DataStruct struct = new DataStruct();

    public Creature(int maxAge) {
        this.maxAge = maxAge;
    }

    public static class DataStruct {
        public int age = 0;
        public boolean dead = false;
        public Vector2D size = new Vector2D(0,0);
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

    public abstract void onDeath();
}
