package Test.concurrent;

import concurrent.SynchronizedTabulatedFunction;
import function.LinkedListTabulatedFunction;
import function.Point;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

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
}