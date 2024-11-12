package io;

import function.TabulatedFunction;
import function.factory.ArrayTabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args){
        String file = "input/function.txt";
        try(
                FileReader arrayReader = new FileReader(file);
                FileReader linkedListReader = new FileReader(file);
                BufferedReader bufferedArrayReader = new BufferedReader(arrayReader);
                BufferedReader bufferedLinkedReader = new BufferedReader(linkedListReader);
                ){
            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(bufferedArrayReader, new ArrayTabulatedFunctionFactory());
            TabulatedFunction linkedListFunction = FunctionsIO.readTabulatedFunction(bufferedLinkedReader, new ArrayTabulatedFunctionFactory());;
            System.out.println(arrayFunction);
            System.out.println(linkedListFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
