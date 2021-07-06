package test;

import com.google.gson.*;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;
import org.plixo.jrcos.Util;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleTest {

    static File file = new File("file.json");

    public static void main(String[] args) throws Exception {

        //create DataClass with values and save to file
        DataClass original = new DataClass();
        original.list.add(new Vector2D(1,2));
        original.list.add(new Vector2D(10,15));
        JsonElement toJson = Serializer.getJson(original);
        Util.saveJsonObj(file,toJson);

        //load data from file and load into an empty DataClass
        DataClass copy = new DataClass();
        JsonElement fromFile = Util.loadFromJson(file);
        copy = (DataClass) Initializer.getObject(copy,fromFile);

        System.out.println(copy.toString());
        //should have the same Vectors

        System.out.println(original.hashCode() == copy.hashCode());
        //should be true

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
