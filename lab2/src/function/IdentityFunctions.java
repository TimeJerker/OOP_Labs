package function;

public class IdentityFunctions implements MathFunction{
    public double apply(double x) {
        return x;
    }

    public String toString(){
        return "IdentityFunction: This function returns the input value unchanged";
    }

    @Override
    public boolean equals(Object o){
        return o instanceof IdentityFunctions; //(this == o);
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
