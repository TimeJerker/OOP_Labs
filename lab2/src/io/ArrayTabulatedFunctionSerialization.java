package io;

import function.ArrayTabulatedFunction;
import function.TabulatedFunction;
import operations.DifferentialOperator;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args){

        try(BufferedOutputStream outputFlow = new BufferedOutputStream(new FileOutputStream("output/serialized_array_functions.bin"))){
            ArrayTabulatedFunction mainFunction = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0, 4.0}, new double[]{2.0, 3.0, 4.0, 5.0});

            TabulatedFunction firstDerivative = new TabulatedDifferentialOperator().derive(mainFunction);
            TabulatedFunction secondDerivative = new TabulatedDifferentialOperator().derive(firstDerivative);

            FunctionsIO.serialize(outputFlow, mainFunction);
            FunctionsIO.serialize(outputFlow, firstDerivative);
            FunctionsIO.serialize(outputFlow,secondDerivative);

        }catch (IOException e){
            e.printStackTrace();
        }
        try(BufferedInputStream inputFlow = new BufferedInputStream(new FileInputStream("output/serialized_array_functions.bin"))){
            TabulatedFunction deserializedMain = FunctionsIO.deserialize(inputFlow);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(inputFlow);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(inputFlow);

            System.out.println("Main Function: ");
            System.out.println(deserializedMain);
            System.out.println("First Derivative: ");
            System.out.println(deserializedFirstDerivative);
            System.out.println("Second Derivative: ");
            System.out.println(deserializedSecondDerivative);
        }catch (ClassNotFoundException cl){
            cl.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
