package Test;

import function.AbstractTabulatedFunction;
import org.junit.jupiter.api.Test;
import function.ArrayTabulatedFunction;
import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

    @Test
    void testConstructorLengths() {
        double[] xValues = {1.0, 2.0, 6.0};
        double[] yValues = {4.0, 5.0};

        assertThrows(IllegalArgumentException.class, () -> AbstractTabulatedFunction.Length(xValues, yValues));
    }

    @Test
    void testConstructorSorted() {
        double[] xValues = {1.0, 5.0, 2.0};

        assertThrows(IllegalArgumentException.class, () -> ArrayTabulatedFunction.Sorted(xValues));
    }
}