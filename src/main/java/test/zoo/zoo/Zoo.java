package test.zoo.zoo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public ArrayList<Creature> creatures = new ArrayList<>();

    public void spawn(Class<? extends Creature> creatureClass) {
        try {
            creatures.add(getObject(creatureClass));
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public <a> a getObject(Class<? extends a> abstractClass) throws ReflectiveOperationException {
        for (Constructor<?> constructor : abstractClass.getConstructors()) {
            if(constructor.getParameterCount() == 0) {
                return (a)constructor.newInstance();
            }
        }
        return null;
    }
}
