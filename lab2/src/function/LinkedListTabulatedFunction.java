package function;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction{

    protected static final class Node{
        Node prev, next;
        double x, y;

        Node() {
            this.x = x;
            this.y = y;
            this.next = null;
            this.prev = null;
        }

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

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {

        if (xValues.length != yValues.length) throw new IllegalArgumentException("The number of X and Y does not match");
        for (int i = 1; i < xValues.length; i++) {
            if (xValues[i - 1] >= xValues[i]) throw new IllegalArgumentException("X is not ordered");
        }

        if (xValues.length < 2) {
            throw new IllegalArgumentException("The number of elements is less than two");
        }

        for (int i = 0; i < xValues.length; i++) {
            this.addNode(xValues[i], yValues[i]);
        }
        this.count = xValues.length;
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if(xFrom>xTo){
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        this.count = count;

        head = new Node();
        Node val = head;

        double step = (xTo - xFrom) /count; //Задаётся шаг

        for (int i = 0; i < count; i++) {
            val.x = xFrom + i * step;
            val.y = source.apply(val.x);
            val.next = new Node();
            val.next.prev = val;
            val = val.next;
        }
        //Добавление последнего узла
        val.x = xFrom + count * step;
        val.y = source.apply(val.x);
        val.next = head;
        head.prev = val;

    }

    @Override
    protected int floorIndexOfX(double x) {
        if (head == null) {
            throw new IllegalStateException("Head is null");
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
            throw new IllegalStateException("Head is null");
        }

        Node lastNode = head;
        Node penulNode = lastNode.next;

        return lastNode.y + (x - lastNode.x) * (penulNode.y - lastNode.y) / (penulNode.x - lastNode.x);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (head == null) {
            throw new IllegalStateException("Head is null");
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
    public double interpolate(double x, int floorIndex) {
        if (floorIndex < 0 || floorIndex >= count - 1) {
            throw new IllegalArgumentException();
        }
        if(x<leftBound()|| x>rightBound()){
            throw new IllegalArgumentException();
        }

        Node node1 = getNode(floorIndex);
        Node node2 = getNode(floorIndex + 1);

        return node1.y + (x - node1.x) * (node2.y - node1.y) / (node2.x - node1.x);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node val = head;
        for (int i = 0; i < count; i++) {
            if (val.x == x){
                return i;
            }
            val = val.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node val = head;
        for (int i = 0; i < count; i++) {
            if (val.y == y){
                return i;
            }
            val = val.next;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        if(head == null) {
            throw new IllegalStateException("Head is null");
        }
        return head.x;
    }

    @Override
    public double rightBound() {
        if(head == null) {
            throw new IllegalStateException("Head is null");
        }
        return head.prev.x;
    }

    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int ind = indexOfX(x);
            if (ind != -1) {
                return getY(ind);
            } else {
                if(ind<0){ ind=count+ind-1;}
                return interpolate(x, ind);
            }
        }
    }

}
