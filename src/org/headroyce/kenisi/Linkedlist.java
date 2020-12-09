package org.headroyce.kenisi;

import java.util.Iterator;

public class Linkedlist<T> implements Iterable<T> {
    // TODO: Add appropriate attributes

    /**
     * Initializes an empty list
     */
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;
    public Linkedlist(){
        this.head = null;
        this.tail = head;
    }

    /**
     * Gets the size of the linked list.  Size is a read-only attribute.
     * @return the number of elements in the list
     */
    public int size(){ // Time complexity O(1)

        // TODO: Complete Me
        return size;
    }

    /**
     * Add to the end of the linked list
     * @param data the data to add to the list
     */
    public void add( T data ){ // Time complexity O(1)
        // TODO: Complete Me

        Node<T> add = new Node(data);

        if (this.size == 0){
            head = add;
            tail = add;
        }else{
            tail.next = add;
            tail = add;
            tail.next = null;
        }
        this.size++;
    }

    /**
     * Inserts data into the list after the index provided.
     * @param data the data to insert into the linked list
     * @param place the index to insert after. -1 indicates before head, > size indicates at the end of the list
     */
    public void insert( T data, int place ){ // Time complexity O(n)
        // TODO: Complete Me
        this.size++;
        Node<T> add = new Node(data);
        if(place == -1){
            add.next = head;
            this.head = add;
        }else if (place > this.size()){
            Node<T> curr = head;
            while (curr.next != tail){
                curr = curr.next;
            }
            tail.next = add;
            tail = add;
            tail.next = null;


        } else{
            Node<T> curr = head;
            for (int i = 0; i < place; i++){
                curr = curr.next;
            }
            Node<T> inbetweencurr = curr.next;
            curr.next = add;
            add.next = inbetweencurr;

        }

    }

    /**
     * Removes an element from the list at the index provided.
     * @param place index to remove; <= 0 indicates removal of first element; > size indicates removal of last element
     * @return the data that was removed
     */
    public T remove( int place ){ // Time complexity O(n)
        // TODO: Complete Me
        size--;
        if(place <= 0){
            T data = head.data;
            head = head.next;
            return data;
        }else if (place >= this.size()){
            Node<T> curr = head;

            while (curr.next != tail){
                curr = curr.next;
            }
            System.out.println("hi");
            tail = curr;
            T data = curr.data;
            tail.next = null;
            return data;
        } else{
            Node<T> curr = head;
            for (int i = 0; i < place-1; i++){
                curr = curr.next;
            }

            Node<T> inbetweencurr = curr.next;
            T data = inbetweencurr.data;
            curr.next = inbetweencurr.next;
            return data;

        }



    }

    /**
     * Gets the data from a provided index (stating at index zero)
     * @param place the index to retreive data from
     * @return the data at index place
     * @throws ArrayIndexOutOfBoundsException if place is outside the list
     */
    public T get( int place ){ // Time complexity O(n)
        // TODO: Complete Me


        if(place >= this.size() || place < 0){
            throw new ArrayIndexOutOfBoundsException();
        }

        Node<T> curr = head;

        for (int i = 0; i < place; i++){
            curr = curr.next;
        }
        return curr.data;

    }

    /**
     * Convert the LList into a String
     * @return a String in format [E0, E1, E2, ...]
     */
    public String toString(){ //O(n^2) = for loop (O(n)) * toString (O(n))
        // TODO: Complete Me


        Node<T> current = this.head;
        int place = 0;
        String linkedlist = "[";
        while( current != null ){
            linkedlist += ", " + current.data.toString();
            current = current.next;
        }

        return linkedlist + "]";
    }

    /**
     * Provides an iterator for the list
     * @return a new iterator starting at the first element of the list
     */
    @Override
    public Iterator<T> iterator() {
        return new LListIterator<T>();
    }

    /**
     * Prints the linked list to the console
     */
    public void print(){
        //TODO: Uncomment this before submission
        Node<T> current = this.head;
        int spot = 0;
        while( current != null ){
            System.out.println(spot+": " + current.data.toString());
            spot = spot + 1;
            current = current.next;
        }
    }

    /**
     * Nodes that specific to the linked list
     * @param <E> the type of the Node. It must by T or extend it.
     */
    private class Node<E extends T>{
        // TODO: Add appropriate attributes
        E data;
        Node<E> next;

        public Node( E data ){
            this.data = data;
            this.next = null;
        }
    }

    /**
     * The iterator that is used for our list.
     */
    private class LListIterator<E extends T> implements Iterator<E> {

        // TODO: Add appropriate attributes


        private Node<T> curr;

        public LListIterator(){
            curr = head;
        }

        @Override
        public boolean hasNext() { // Time complexity O(1)
            // TODO: Complete Me checks if null or nah\
            if (curr == null){
                return false;
            }
            return true;
        }

        @Override
        public E next() { // Time complexity O(1)
            E data = (E) curr.data;
            curr = curr.next;
            return data;

        }
    }
}
