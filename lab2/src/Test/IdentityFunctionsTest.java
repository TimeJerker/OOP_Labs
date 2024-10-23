package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import function.IdentityFunctions;
import org.junit.jupiter.api.Test;

class IdentityFunctionsTest {
    @Test
    public void testIdentityFunctions(){
        IdentityFunctions identityFunction = new IdentityFunctions();

        assertEquals(1, identityFunction.apply(1));
        assertEquals(0.00001, identityFunction.apply(0.00001));
    }

    @Test
    public void testEquals(){
        IdentityFunctions identityFunction = new IdentityFunctions();
        assertEquals(true, identityFunction.equals(identityFunction));

        Object obj = new Object();
        assertEquals(false, identityFunction.equals(obj));
    }

    @Test
    public void testHashCode(){
        IdentityFunctions identityFunction = new IdentityFunctions();
        assertEquals(1, identityFunction.hashCode());
    }

    @Test
    public void testClone(){
        IdentityFunctions identityFunction = new IdentityFunctions();
        Object identityFunction2 = identityFunction.clone();
        assertEquals(true, identityFunction2 instanceof IdentityFunctions);
    }
}