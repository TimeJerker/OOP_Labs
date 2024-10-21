package function;

public class IdentityFunctions implements MathFunction{
    public double apply(double x) {
        return x;
    }

    public String toString(){
        return "";
    }

    public boolean equals(Object o){
        return o instanceof IdentityFunctions;
    }

//    @Override
//    public int hashCode(){
//        return;
//    }

    public Object clone(){
        return new IdentityFunctions();
    }
}
