package function;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {

    static final class Node{
        private Node prev, next;
        private double x, y;

        Node() {
            this.next = null;
            this.prev = null;
        }

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            this.next = null;
            this.prev = null;
        }

        public String toString() {
            return String.format("(%.1f; %.1f)", x, y);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) { //Проверяем, что объект не null и имеет тот же класс
                return false;
            }

            Node other = (Node) o;
            return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0; //Сравниваем координаты
        }

        public int hashCode() {
            int hashX = (int) Double.doubleToLongBits(x);
            int hashY = (int) Double.doubleToLongBits(y);
            return hashX ^ hashY;
        }

        protected Node clone() {
            return new Node(this.x, this.y);
        }
    }

    private Node head;

    public String ToString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        do{
            sb.append(current.toString());
            current = current.next;
            if (current != head) {
                sb.append("; ");
            }
        }while (current != head);
            return sb.toString();
    }

    public int hashCode() {
        int hash = 7;
        Node current = head;
         do{
            hash = 31 * hash + current.hashCode();
            current = current.next;
        }while (current != head);
        return hash;
    }

    public boolean Equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TabulatedFunction otherFunction)) return false;

        LinkedListTabulatedFunction other;
        if (otherFunction instanceof ArrayTabulatedFunction) {
            other = convertArrayToLinkedList((ArrayTabulatedFunction) otherFunction);
        } else{
            other = (LinkedListTabulatedFunction) otherFunction;
        }
        if (this.count != other.count) return false;

        Node current1 = this.head;
        Node current2 = other.head;
        do{
            if (!current1.equals(current2)) {
                return false;
            }
            current1 = current1.next;
            current2 = current2.next;
        }while (current1 != head);
        return true;
    }

    public LinkedListTabulatedFunction() {
        this.head = null;
        this.nodes = new Node[0];
        this.count = 0;
    }

    public LinkedListTabulatedFunction convertArrayToLinkedList(ArrayTabulatedFunction arrayFunction) {
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction();
        int count = arrayFunction.getCount();
        for (int i = 0; i < count; i++) {
            double xValue = arrayFunction.getX(i);
            double yValue = arrayFunction.getY(i);
            linkedListFunction.add(xValue, yValue);
        }
        return linkedListFunction;
    }
    public void add(double xValue, double yValue) {
        Node newNode = new Node(xValue, yValue);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != head && current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        Node[] newNodesArray = new Node[count + 1];
        System.arraycopy(nodes, 0, newNodesArray, 0, nodes.length);
        newNodesArray[count] = newNode;
        nodes = newNodesArray;
        count++;
    }



    public LinkedListTabulatedFunction Clone() {
        if (nodes == null) {
            return new LinkedListTabulatedFunction();
        }

        Node[] clonedNodes = new Node[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            clonedNodes[i] = nodes[i].clone(); 
        }

        return new LinkedListTabulatedFunction(clonedNodes);
    }


    public boolean[] getNode() {
        if (nodes == null || nodes.length == 0) {
            return new boolean[0];
        }

        boolean[] result = new boolean[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            result[i] = nodes[i] != null;
        }
        return result;
    }

    private Node[] nodes;

    public LinkedListTabulatedFunction(Node[] nodes) {
        this.nodes = nodes;
    }

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
        if (x < head.x) {
            throw new IllegalArgumentException("Lesser than left left bound");
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
        return super.getCount();
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
                ind = count + ind - 1;
                return interpolate(x, ind);
            }
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            private Node node = head;
            private int i = 0;
            @Override
            public boolean hasNext() {
                return i < count;
            }
            @Override
            public Point next() {
                if (node == null){ throw new NoSuchElementException("There are no other elements");}
                Point point = new Point(node.x, node.y);
                i++;
                if(hasNext()) {node = node.next;}
                else{ node = null;}
                return point;
            }
        };
    }
}
