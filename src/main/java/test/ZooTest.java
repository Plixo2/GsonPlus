package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Mapping;
import org.plixo.jrcos.Serializer;
import org.plixo.jrcos.Util;
import test.zoo.zoo.Creature;
import test.zoo.zoo.Giraffe;
import test.zoo.zoo.Rhino;
import test.zoo.zoo.Zoo;

import java.io.File;

public class ZooTest {

    static File location = new File("zoo/original.json");

    public static void main(String[] args) {

      //  System.out.println(System.getProperty("java.awt.headless"));
        System.setProperty("java.awt.headless", "false");

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
            e.printStackTrace();
        }

        while (true) {
            zoo.creatures.forEach(Creature::advanceAge);
            zoo.creatures.forEach(System.out::println);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(Math.random() < -0.1) {
                break;
            }
        }

        try {
            JsonElement element = Serializer.getJson(zoo);
            Util.saveJsonObj(location,element);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        //Enums finished
        //non JsonPrimitives for defaults
        //null values still crash or not? (by casting to primitive?)
        //not found / empty body / not a primitive / (wrong value to cast)
        //files can extend file.class, but are not the same class -> extends check
        //file support
        //size check in arrays or lists
        //load with array instead of override
        //other exception in getObject and getJson
        //parse field object with getObject, so it can be created if the obj is null
        //default enum does not work -> fall back on normal field value, but why?
        //interface check^^
    }
}
