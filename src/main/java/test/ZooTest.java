package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.gsonplus.GsonPlusBuilder;
import org.plixo.gsonplus.GsonPlusConfig;
import org.plixo.gsonplus.GsonPlus;
import org.plixo.gsonplus.Util;
import test.zoo.zoo.Creature;
import test.zoo.zoo.Giraffe;
import test.zoo.zoo.Rhino;
import test.zoo.zoo.Zoo;

import java.io.File;

public class ZooTest {

    static File location = new File("zoo/original.json");

    public static void main(String[] args) {

        System.out.println("GsonPlus v1.5 2D Zoo Example");

        Zoo zoo = new Zoo();
        zoo.spawn(Giraffe.class);
        zoo.spawn(Rhino.class);
        GsonPlusConfig.setOverwriteLists(true);
        GsonPlusConfig.setUseDefaultCase(false);


        GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder();
        try {
           JsonObject object = Util.loadFromJson(location);
            gsonPlusBuilder.create(zoo,object);
        } catch (Exception e) {
            e.printStackTrace();
        }

            zoo.creatures.forEach(Creature::advanceAge);
            zoo.creatures.forEach(System.out::println);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        GsonPlus gsonPlus = new GsonPlus();
        try {
            JsonElement element = gsonPlus.toJson(zoo);
            Util.saveJsonObj(location,element);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        //Enums support-
        //non JsonPrimitives for defaults-
        //null values still crash or not? (by casting to primitive?)-
        //not found / empty body / not a primitive / (wrong value to cast)-
        //files can extend file.class, but are not the same class -> extends check-
        //size check in arrays or lists-
        //other exception in getObject and getJson-
        //parse field object with getObject, so it can be created if the obj is null-
        //file support
        //interface check^^
        //default enum does not work -> fall back on normal field value, but why? ?
        //load with array instead of override
    }
}
