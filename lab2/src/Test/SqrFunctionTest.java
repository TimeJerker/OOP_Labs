package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import function.SqrFunction;

class SqrFunctionTest {
    @Test
    public void testApply() {
        SqrFunction sqrFunction = new SqrFunction();

        assertEquals(9, sqrFunction.apply(3));
        assertEquals(9, sqrFunction.apply(-3));
    }
}