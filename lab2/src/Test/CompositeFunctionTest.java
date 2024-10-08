package Test;

import function.CompositeFunction;
import function.IdentityFunctions;
import function.MathFunction;
import function.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeFunctionTest {
    @Test
    void testCompositeFunctions() {
        MathFunction sqrFunction = new SqrFunction();
        MathFunction identityFunctions=new IdentityFunctions();
        CompositeFunction compositeFunction1 = new CompositeFunction(sqrFunction, sqrFunction);
        CompositeFunction compositeFunction2 = new CompositeFunction(identityFunctions, sqrFunction);
        CompositeFunction compositeFunction3 = new CompositeFunction(identityFunctions, identityFunctions);

        assertEquals(16, compositeFunction1.apply(2));
        assertEquals(4, compositeFunction2.apply(2));
        assertEquals(2, compositeFunction3.apply(2));
    }
}