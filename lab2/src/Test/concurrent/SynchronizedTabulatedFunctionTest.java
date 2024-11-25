package Test.concurrent;

import concurrent.SynchronizedTabulatedFunction;
import function.ArrayTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.Point;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    @Test
    void testSetCount(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(func.getCount(), synFunc.getCount());
    }

    @Test
    void testGetX(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(func.getX(1), synFunc.getX(1));
    }

    @Test
    void testGetY(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(5, synFunc.getY(3));
    }

    @Test
    void testSetY(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        synFunc.setY(2, 5);
        assertEquals(5, synFunc.getY(2));
    }

    @Test
    void testIndexOfX(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(-1, synFunc.indexOfX(10));
    }

    @Test
    void testIndexOfY(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(1, synFunc.indexOfY(3));
    }

    @Test
    void testLeftBound(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(1, synFunc.leftBound());
    }

    @Test
    void testRightBound(){
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 4.0, 5.0};
        ArrayTabulatedFunction func = new ArrayTabulatedFunction(xValues,yValues);
        SynchronizedTabulatedFunction synFunc = new SynchronizedTabulatedFunction(func);
        assertEquals(4, synFunc.rightBound());
    }

    @Test
    void testIteratorWhile(){
        double[] xArray = {1.0, 2.0, 3.5, 4.0};
        double[] yArray = {1.0, 2.0, 3.0, 4.0};
        SynchronizedTabulatedFunction synchronixed = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        Iterator<Point> iterator = synchronixed.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            function.Point point = iterator.next();
            assertEquals(synchronixed.getX(index), point.x, 1e-9);
            assertEquals(synchronixed.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(synchronixed.getCount(), index);
    }

    @Test
    void testIteratorFor(){
        double[] xArray = {1.0, 2.6, 4.0, 10.0};
        double[] yArray = {0.0, 3.0, 2.0, 1.0};
        SynchronizedTabulatedFunction synchronixed = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        int index = 0;

        for(function.Point point : synchronixed){
            assertEquals(synchronixed.getX(index), point.x, 1e-9);
            assertEquals(synchronixed.getY(index), point.y, 1e-9);
            index++;
        }

        assertEquals(synchronixed.getCount(), index);
    }

    @Test
    void testDoSynchronously1(){
        double[] xArray = {1.0, 2.0, 3.5, 4.0};
        double[] yArray = {1.0, 2.0, 3.0, 4.0};
        SynchronizedTabulatedFunction synchronixed = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        double value = synchronixed.doSynchronously(f -> f.getY(2));

        assertEquals(3, value);
    }

    @Test
    void testDoSynchronously2(){
        double[] xArray = {1.0, 2.0, 3.5, 4.0};
        double[] yArray = {1.0, 2.0, 3.0, 4.0};
        SynchronizedTabulatedFunction synchronixed = new SynchronizedTabulatedFunction(new LinkedListTabulatedFunction(xArray, yArray));

        synchronixed.doSynchronously(f -> {
            f.setY(1, 10.0);
            return null;
        });

        assertEquals(10.0, synchronixed.doSynchronously(f -> f.getY(1)));
    }
}