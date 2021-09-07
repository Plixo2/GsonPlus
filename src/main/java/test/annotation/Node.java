package test.annotation;

import org.plixo.gsonplus.Optional;
import org.plixo.gsonplus.Serialize;

public class Node {

    @Serialize
    @Optional
    Node next = null;

    public Node() {
    }


    public Node getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

