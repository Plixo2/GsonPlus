package test.annotation;


import org.plixo.gsonplus.Serialize;

public class Player {
    @Serialize
    float hp = 0;

    @Serialize
    Node linkedList;

    public Player() {
        linkedList = new Node();
        Node next = new Node();
        linkedList.setNext(next);
    }
}
