package test;

import com.google.gson.*;
import org.plixo.gsonplus.GsonPlusBuilder;
import org.plixo.gsonplus.GsonPlus;
import org.plixo.gsonplus.GsonPlusConfig;
import org.plixo.gsonplus.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleTest {



    public static void main(String[] args) throws Exception {

        System.out.println("GsonPlus v1.6 2D Simple Example");

        File file = new File("file.json");

        GsonPlusConfig.setClassLoader(SimpleTest.class.getClassLoader());

        //create DataClass with values and save it to a file
        DataClass original = new DataClass();
        original.list.add(new Vector2D(1,2));
        original.list.add(new Vector2D(10,15));
        GsonPlus gsonPlus = new GsonPlus(); //instance for saving
        JsonElement toJson = gsonPlus.toJson(original);
        Util.saveJsonObj(file,toJson);

        //load data from file and load into an empty DataClass
        DataClass copy = new DataClass();
        JsonElement fromFile = Util.loadFromJson(file);
        GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder(); //instance for building
        copy = (DataClass) gsonPlusBuilder.create(copy,fromFile);

        System.out.println(copy.toString());//should have the same values

        boolean state = original.hashCode() == copy.hashCode();//should be true
        if(state)  {
            System.out.println("the copy is the same");
        } else {
            System.out.println("the copy is not the same, test failed");
        }



    }

    public static class DataClass {
        public List<Vector2D> list = new ArrayList<>();

        @Override
        public int hashCode() {
            return list.hashCode();
        }

        @Override
        public String toString() {
            return "DataClass{list=" + list + '}';
        }
    }

    public static class Vector2D {
        public double x = 0;
        public double y = 0;

        public Vector2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        //empty for instancing
        public Vector2D() {

        }

        @Override
        public String toString() {
            return "Vector2D{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
