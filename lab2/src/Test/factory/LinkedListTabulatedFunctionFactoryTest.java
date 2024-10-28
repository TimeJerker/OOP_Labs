package Test.factory;

import function.LinkedListTabulatedFunction;
import function.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionFactoryTest {
    @Test
    void testEqual(){
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        assertInstanceOf(LinkedListTabulatedFunction.class, factory.create(new double[]{0, 1, 2}, new double[]{3, 4, 5}));
    }
}