package test.zoo.zoo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Zoo {
    public ArrayList<Creature> creatures = new ArrayList<>();

    public void spawn(Class<? extends Creature> creatureClass) {
        try {
            creatures.add(getInstance(creatureClass));
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public <a> a getInstance(Class<? extends a> clazz) throws ReflectiveOperationException {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if(constructor.getParameterCount() == 0) {
                return (a)constructor.newInstance();
            }
        }
        return null;
    }
}
