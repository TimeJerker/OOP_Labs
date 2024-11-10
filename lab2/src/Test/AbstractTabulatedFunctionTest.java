package Test;

import function.AbstractTabulatedFunction;
import function.LinkedListTabulatedFunction;
import org.junit.jupiter.api.Test;
import function.ArrayTabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

    @Test
    public void CheckLengthIsTheSameTest() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        double[] yValues = {2.0, 3.0, 5.0, 6.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    public void CheckSortedTest() {
        double[] xValues = {1.0, 2.0, 3.0, 4.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
    }

    @Test
    void testToString(){
        double[] xValues = new double[]{1.0, 2.0, 3.0};
        double[] yValues = new double[]{4.0, 5.0, 6.0};
        LinkedListTabulatedFunction function1 = new LinkedListTabulatedFunction(xValues, yValues);

        String stringLinkedList = "LinkedListTabulatedFunction size = 3\n[1.0; 4.0]\n[2.0; 5.0]\n[3.0; 6.0]\n";

        assertEquals(stringLinkedList, function1.toString());
    }
}