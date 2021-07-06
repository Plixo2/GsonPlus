package test.zoo.zoo;

import test.zoo.math.Vector2D;

public abstract class Creature {
    public int age = 0;
    int maxAge;
    public boolean dead = false;

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

    public abstract void onDeath();
}
