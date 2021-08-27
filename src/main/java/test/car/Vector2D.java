package test.car;

import org.plixo.gsonplus.GsonPlusConfig;

import java.util.Arrays;
import java.util.Random;

public class Vector2D {
    public double x;
    public double y;


    //Random stuff to save and load (to show of)
    public String[] letters;
    public Vector2D parent = null;
    String abc = "abcdefghiklmnopqrstuvwxyz";

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;

        int random = new Random().nextInt(24);
        letters = new String[]{String.valueOf(abc.charAt(random)), String.valueOf(abc.charAt(random + 1))};
    }

    /**
     * empty constructor for new object creation
     * make sure that after that all fields (except primitives, arrays or other classes with an empty constructor) are not null
     * you also could make an Adapter in {@link GsonPlusConfig} with {@link GsonPlusConfig.IDefaultObject}
     */
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                ", letters=" + Arrays.toString(letters) +
                ", parent=" + parent +
                '}';
    }
}
