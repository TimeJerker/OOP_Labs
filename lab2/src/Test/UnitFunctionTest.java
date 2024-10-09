package Test;

import function.UnitFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnitFunctionTest {
    @Test
    public void testZeroFunction() {
        UnitFunction zeroFunction = new UnitFunction();

        assertEquals(zeroFunction.getterCt(), zeroFunction.apply(10));
        assertEquals(zeroFunction.getterCt(), zeroFunction.apply(0));
    }
}