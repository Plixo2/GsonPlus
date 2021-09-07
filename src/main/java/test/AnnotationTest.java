package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plixo.gsonplus.GsonPlus;
import org.plixo.gsonplus.GsonPlusBuilder;
import org.plixo.gsonplus.GsonPlusConfig;
import org.plixo.gsonplus.Util;
import test.annotation.Player;
import test.zoo.zoo.Creature;
import test.zoo.zoo.Giraffe;
import test.zoo.zoo.Rhino;
import test.zoo.zoo.Zoo;

import java.io.File;

public class AnnotationTest {

    static File location = new File("annotation/original.json");

    public static void main(String[] args) {

        System.out.println("GsonPlus v1.6 2D Zoo Example");

        Player player = new Player();

        GsonPlusConfig.setOverwriteLists(true);
        GsonPlusConfig.setUseDefaultCase(false);
        GsonPlusConfig.setClassLoader(AnnotationTest.class.getClassLoader());

        GsonPlusConfig.setAnnotationsUse(true); //for use with Annotations


        GsonPlusBuilder gsonPlusBuilder = new GsonPlusBuilder();
        try {
           JsonObject object = Util.loadFromJson(location);
           gsonPlusBuilder.create(player,object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonPlus gsonPlus = new GsonPlus();
        try {
            JsonElement element = gsonPlus.toJson(player);
            System.out.println("Saved: " + element);
            Util.saveJsonObj(location,element);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }
}
