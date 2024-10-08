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

    public int getCount() {
        return 0;
    }

    public double getX(int index) {
        return 0;
    }

    public double getY(int index) {
        return 0;
    }

    public void setY(int index, double value) {

    }

    public int indexOfX(double x) {
        return 0;
    }

    public int indexOfY(double y) {
        return 0;
    }

    public double leftBound() {
        return 0;
    }

    public double rightBound() {
        return 0;
    }

    public double apply(double x) {
        return 0;
    }
}
