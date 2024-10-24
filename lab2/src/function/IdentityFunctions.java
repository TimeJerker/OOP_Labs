package function;

public class IdentityFunctions implements MathFunction{
    public double apply(double x) {
        return x;
    }

    @Override
    public String toString(){
        return "IdentityFunction: This function returns the input value unchanged and also provides methods for comparing, cloning, and string representation of an object";
    }

    @Override
    public boolean equals(Object o){
        return o instanceof IdentityFunctions;
    }

    @Override
    public int hashCode() {
        int hash = 1; // Начальное значение
        return hash; // Для данного класса, так как он не имеет полей
    }

    public IdentityFunctions clone(){
        return new IdentityFunctions();
    }
}
