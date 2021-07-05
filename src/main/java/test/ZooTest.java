package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Mapping;
import org.plixo.jrcos.Serializer;
import org.plixo.jrcos.Util;
import test.zoo.zoo.Giraffe;
import test.zoo.zoo.Rhino;
import test.zoo.zoo.Zoo;

import java.io.File;

public class ZooTest {

    static File location = new File("zoo/original.json");

    public static void main(String[] args) {
        System.out.println("Serializer v1.2 Zoo Example");

        Zoo zoo = new Zoo();
        zoo.spawn(Giraffe.class);
        zoo.spawn(Rhino.class);
        Mapping.overwriteLists = true;
        Mapping.useDefaultCase = false;


        try {
           JsonObject object = Util.loadFromJson(location);
           Initializer.getObject(zoo,object);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

       // zoo.creatures.forEach(Creature::advanceAge);

        try {
            JsonElement element = Serializer.getJson(zoo);
            Util.saveJsonObj(location,element);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        //ArrayList
        //Enums
    }
}
