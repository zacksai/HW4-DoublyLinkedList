import java.util.*;

/**
 * DoubleLinkedList data structure
 *
 * @param <E> generic type of list
 * @author Zack Sai
 */
public class DoubleLinkedList<E> extends AbstractSequentialList<E> {

    // Head, tail, size fields
    private Node<E> zHead = null;
    private Node<E> zTail = null;
    private int zSize = 0;


    /**
     * Add method uses list iterator to add node at specified index
     *
     * @param index position to add at
     * @param obj   object being added
     */
    public void add(int index, E obj) {

        listIterator(index).add(obj);
    }

    /**
     * addFirst uses listIterator to add node at beginning of list
     *
     * @param obj object being added
     */
    public void addFirst(E obj) {

        // Create iterator object at position 0 and set
        zListIterator zIterator = new zListIterator(0);
        zIterator.set(obj);

        // Increment size
        zSize++;
    }

    /**
     * addLast method adds at the tail of the list
     *
     * @param obj object being added
     */
    public void addLast(E obj) {

        // Create new node, link it to the tail in appropriate order, increment size
        Node<E> addNode = new Node<E>(obj);
        addNode.zPrev = zTail;
        zTail.zNext = addNode;
        addNode = zTail;
        addNode.zNext = null;
        zSize++;
    }

    /**
     * getNode returns node at position passed
     *
     * @param index position to retrieve at
     * @return node at position index
     */
    private Node<E> getNode(int index) {

        // Begin at head, loop through list until index is reached
        Node<E> currentNode = zHead;
        for (int i = 0; i < index && currentNode != null; i++) {
            currentNode = currentNode.zNext;
        }

        // Return node at this position
        return currentNode;
    }

    /**
     * get returns data at position passed
     *
     * @param index position to retrieve at
     * @return node at position index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public E get(int index) {

        // throw exception if index is out of bounds
        if (index < 0 || index >= zSize) {
            throw new IndexOutOfBoundsException("Index must be between 0 and" + size());
        }
        Node<E> returnNode = getNode(index);
        return returnNode.zData;
    }

    /**
     * getFirst returns data at head of list
     *
     * @return data at head of list
     */
    public E getFirst() {
        return zHead.zData;
    }

    /**
     * getLast returns data at tail of list
     *
     * @return data at tail of list
     */
    public E getLast() {
        return zTail.zData;
    }

    /**
     * size returns size of list
     *
     * @return size of list
     */
    public int size() {
        return zSize;
    }

    /**
     * remove method removes node at index passed
     *
     * @param index position to remove node at
     * @return the node removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public E remove(int index) {

        // Create returnValue to store node and iterator to loop through
        E returnValue = null;
        ListIterator<E> zIterator = listIterator(index);

        // Remove item using iterator or throw exception for invalid index
        if (zIterator.hasNext()) {
            returnValue = zIterator.next();
            zIterator.remove();
        } else {
            throw new IndexOutOfBoundsException();
        }
        return returnValue;
    }

    /**
     * clear method resets to an empty list
     */
    public void clear() {
        zHead = null;
        zTail = null;
        zSize = 0;
    }

    // Constructors for iterator
    public ListIterator<E> iterator() {
        return new zListIterator(0);
    }

    public ListIterator<E> listIterator() {
        return new zListIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new zListIterator(index);
    }

    public ListIterator<E> listIterator(ListIterator<E> zIter) {
        return new zListIterator((zListIterator) zIter);
    }

    /**
     * Node inner class
     *
     * @param <E> generic data type
     */
    private static class Node<E> {

        // Variable containing data, reference to next and previous
        private E zData;
        private Node<E> zNext = null;
        private Node<E> zPrev = null;

        // Constructor
        private Node(E dataItem) {
            zData = dataItem;
        }

    }

    /**
     * zListIter class manages iterating through the linkedList
     */
    public class zListIterator implements ListIterator<E> {

        // Reference to next node, last node returned, and current index
        private Node<E> zNextItem;
        // the previous node
        private Node<E> zLastReturnedItem;
        private int index = 0;

        /**
         * zListIter constructor initializes iterator at given position
         *
         * @param i position to initialize at
         * @throws IndexOutOfBoundsException if index is invalid
         */
        public zListIterator(int i) {

            // Throw exception for invalid index
            if (i < 0 || i > zSize) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }

            // Set lastReturnedItem to null
            zLastReturnedItem = null;

            // If end of list, update index and next item appropriately
            if (i == zSize) {
                index = zSize;
                zNextItem = null;
            } else {
                // Otherwise, increment nextItem till position has been reached
                zNextItem = zHead;
                for (index = 0; index < i; index++)
                    zNextItem = zNextItem.zNext;
            }

        }

        /**
         * copy constructor initializes iterator to copied iterator
         *
         * @param other copied iterator
         */
        public zListIterator(zListIterator other) {
            zNextItem = other.zNextItem;
            zLastReturnedItem = other.zLastReturnedItem;
            index = other.index;
        }

        /**
         * hasNext determines if next item is null
         *
         * @return true if next item is not null
         */
        public boolean hasNext() {
            return zNextItem != null;
        }

        /**
         * hasPrevious determines if previous item is null
         *
         * @return
         */
        public boolean hasPrevious() {

            // Check head first
            if (zSize == 0)
                return false;

            // Ensure node is nonnull and in an existing list
            return (zNextItem == null && zSize != 0)
                    || zNextItem.zPrev != null;
        }

        /**
         * previousIndex returns previous index
         *
         * @return decremented index
         */
        public int previousIndex() {
            return index - 1;
        }

        /**
         * nextIndex returns next index
         *
         * @return current index
         */
        public int nextIndex() {
            return index;
        }

        /**
         * set method changes lastReturnedItem to passed value
         *
         * @param newItem item to be set
         * @throws IllegalStateException if list is empty or invalid last item
         */
        public void set(E newItem) {

            // Set item at position to passed value
            if (zLastReturnedItem != null) {
                zLastReturnedItem.zData = newItem;
            } else {
                throw new IllegalStateException();
            }
        }

        /**
         * remove method removes node at current position
         *
         * @throws IllegalStateException if list is empty or invalid last item
         */
        public void remove() {

            // Throw exception if list is empty or invalid last item
            if (zLastReturnedItem == null) throw new IllegalStateException();

            // If item is at head, link head to next item
            if (zLastReturnedItem == zHead) {
                zHead = zNextItem;
                zHead.zPrev = null;

                // If item is at tail, link prev item to tail, update to null
            } else if (zLastReturnedItem == zTail) {
                zLastReturnedItem.zPrev.zNext = null;
                zTail = zLastReturnedItem.zPrev;
                zTail.zNext = null;

                // If item is neither head nor tail, link its previous item to its next item
            } else if (zLastReturnedItem != zHead && zLastReturnedItem != zTail) {
                zLastReturnedItem.zNext.zPrev = zLastReturnedItem.zPrev;
                zLastReturnedItem.zPrev.zNext = zLastReturnedItem.zNext;
            }

            // Decrement size and index
            zSize--;
            index--;
        }

        /**
         * next increments the iterator
         *
         * @return the last item returned data
         * @throws NoSuchElementException if no next element
         */
        public E next() {

            // Throw exception if no next element
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {

                // Increment lastItemReturned, nextItem, index, and return data
                zLastReturnedItem = zNextItem;
                zNextItem = zNextItem.zNext;
                index++;
                return zLastReturnedItem.zData;
            }
        }

        /**
         * previous method decrements the iterator
         *
         * @return the last item returned data
         * @throws NoSuchElementException if no previous element
         */
        public E previous() {

            // Throw exception if no previous element
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            // If no next item, update to tail
            if (zNextItem == null) {
                zNextItem = zTail;
            } else {

                // Otherwise, update to previous node
                zNextItem = zNextItem.zPrev;
            }

            // Store data, decrement size
            zLastReturnedItem = zNextItem;
            index--;
            return zLastReturnedItem.zData;
        }

        /**
         * Add method inserts new node given object and updates prev and next references
         *
         * @param obj
         */
        public void add(E obj) {

            // If empty list, add to tail and head
            if (zHead == null) {
                // add to an empty list
                zHead = new Node<E>(obj);
                zTail = zHead;

                // If adding at head, link new item to next item
            } else if (zNextItem == zHead) {
                Node<E> newNode = new Node<E>(obj);
                newNode.zNext = zNextItem;
                zNextItem.zPrev = newNode;
                zHead = newNode;

                // If adding at tail, link new item to tail
            } else if (zNextItem == null) {
                Node<E> newNode = new Node<E>(obj);
                zTail.zNext = newNode;
                newNode.zPrev = zTail;
                zTail = newNode;

                // If adding elsewhere, link previous item to new item to next item
            } else {
                Node<E> newNode = new Node<E>(obj);
                newNode.zPrev = zNextItem.zPrev;
                zNextItem.zPrev.zNext = newNode;
                newNode.zNext = zNextItem;
                zNextItem.zPrev = newNode;
            }

            // Increment size and index
            zSize++;
            index++;
        }
    }


}


    
    
