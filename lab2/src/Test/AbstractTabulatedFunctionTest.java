package Test;

import exceptions.DifferentLengthArrayException;
import function.AbstractTabulatedFunction;
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
}