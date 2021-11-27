package test.annotation;


import org.plixo.gsonplus.ExposeField;

public class Player {
    float hp = 0;


    @ExposeField
    Node linkedList;

    public Player() {
        linkedList = new Node();
        Node next = new Node();
        linkedList.setNext(next);
    }
}
