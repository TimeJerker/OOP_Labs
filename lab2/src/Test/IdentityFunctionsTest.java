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
}