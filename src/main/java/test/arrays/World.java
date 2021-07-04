package test.arrays;

public class World {

    public Cube[][] list = new Cube[4][4];

    public World() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
               // list[x][y] = new Cube();
            }
        }
    }

    public void fill() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                list[x][y] = new GreenCube();
            }
        }
    }

    @Override
    public String toString() {
       StringBuilder s = new StringBuilder("World{");
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                s.append(list[x][y]);
            }
        }
        return s+"}";
    }
}
