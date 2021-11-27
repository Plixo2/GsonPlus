package test.annotation;

import org.plixo.gsonplus.Optional;

public class Node {

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

