package org.example;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Arrays;

public class ArithmeticApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter base (2/8/10/16): ");
        int base= input.nextInt();
        while(base!= 2 && base!= 8 && base!= 10 && base!= 16){
            input = new Scanner(System.in);
            System.out.println("Error – this base isn’t supported. Please enter a base (2/8/10/16):");
            base= input.nextInt();
        }

        Scanner input_string = new Scanner(System.in);
        System.out.println("Enter expression:");
        String str= input_string.nextLine();
        if(str==""){
            System.out.println("invalid expression: " + str);
        }
        String[] expression = new String[str.length()];
        String operator ="+-/*";
        for(int i =0; i<str.length()+1; i++){
//            if(operator.contains(str[i]) && operator.contains(str[i+1])){
//                System.out.println("invalid expression: " + str); //two operations in a row
//            }
        }
        //TODO: add error division by zero
        //TODO: add error Error: invalid expression: "6A" not valid base 16 - hexa
        System.out.println("The value of expression "+ str+ " is: ");

    }

    //flow:
    //1. convert number to decimal base,
    // 2. apply computations
    // convert to original base and return output
    // use native java funcs!
}
