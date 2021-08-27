package test.world;

public class World {

    public Cube[][] list = new Cube[4][4];

    public World() {

    }

    public void fill() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                list[x][y] = new GreenCube();
            }
        }
        list[3][3] = new RedCube();
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
