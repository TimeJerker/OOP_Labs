package function;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction{

    protected static final class Node{
        Node prev, next;
        double x, y;

        Node(double x, double y) {
            this.x = x;
            this.y = y;
            this.next = null;
            this.prev = null;
        }

    }
    private Node head;

    private void addNode(double x, double y){

        if(head!=null){
            Node last = head.next;
            while(last.next!=head){
                last = last.next;
            }
            Node prevLast = last;
            last.next = new Node(x, y);
            last = last.next;
            last.prev = prevLast;
            last.next = head;
            head.prev = last;
        } else{
            head = new Node(x, y);
            head.prev = head;
            head.next = head;
        }
        ++count;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (head == null) {
            return -1;
        }

        Node current = head;
        int index = 0;

        while (current.next != head && current.next.x < x) {
            current = current.next;
            index++;
        }
        return index;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (head == null) {
            return -1;
        }

        Node lastNode = head;
        Node penulNode = lastNode.next;

        return lastNode.y + (x - lastNode.x) * (penulNode.y - lastNode.y) / (penulNode.x - lastNode.x);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (head == null) {
            return -1;
        }

        Node lastNode = head.prev;
        Node penulNode = lastNode.prev; // Предпоследний узел

        return lastNode.y + (x - lastNode.x) * (penulNode.y - lastNode.y) / (penulNode.x - lastNode.x);
    }

    private Node getNode(int index){
        Node val = head;

        if(index>0) {
            for (int i = 0; i < index; i++) {
                val = val.next;
            }
        } else{
            for (int i = index; i < 0; i++) {
                val = val.prev;
            }
        }

        return val;
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (floorIndex < 0 || floorIndex >= count - 1) {
            return -1;
        }

        Node node1 = getNode(floorIndex);
        Node node2 = getNode(floorIndex + 1);

        return node1.y + (x - node1.x) * (node2.y - node1.y) / (node2.x - node1.x);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public double getX(int index) {
        return 0;
    }

    @Override
    public double getY(int index) {
        return 0;
    }

    @Override
    public void setY(int index, double value) {

    }

    @Override
    public int indexOfX(double x) {
        return 0;
    }

    @Override
    public int indexOfY(double y) {
        return 0;
    }

    @Override
    public double leftBound() {
        return 0;
    }

    @Override
    public double rightBound() {
        return 0;
    }

    @Override
    public double apply(double x) {
        return 0;
    }
}
