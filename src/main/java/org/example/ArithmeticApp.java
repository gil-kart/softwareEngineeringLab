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
        String[] strArray = null;
        strArray = str.split(" ");
        //TODO: fix getting spaces
//
        String operator ="+-/*";
        int len = str.length()-1;
        for(int i =0; i<len; i++){
            //System.out.println(strArray[i]); //two operations in a row
            if(operator.contains(Character.toString(str.charAt(i))) && operator.contains(Character.toString(str.charAt(i+1)))){
                System.out.println("invalid expression: " + str); //two operations in a row
            }
        }
        int index=0;
        int[] nums = new int[str.length()];
        char[] operations_arr = new char[str.length()];
        int counter_nums=0; int counter_operations=0;
        while(index<=str.length()) {
            String num_string_first =new String();
            if(index<str.length()&&operator.contains(Character.toString(str.charAt(index)))==false&& Character.toString(str.charAt(index))!=" "){
                while(index<str.length()&&operator.contains(Character.toString(str.charAt(index)))==false&& Character.toString(str.charAt(index))!=" "
                ){
                    num_string_first+= Character.toString(str.charAt(index));
                    nums[counter_nums] = Integer.valueOf(num_string_first, base);
                    index++;
                }
                index--;
                counter_nums++;
                 //two operations in a row
            }
            index++;
//            while(Character.toString(str.charAt(index))==" "||operator.contains(Character.toString(str.charAt(index)))){
//                index++;
//            }
//
//            String num_string_second =new String();
//            while(operator.contains(Character.toString(str.charAt(index)))==false&& Character.toString(str.charAt(index))!=" " &&
//                    index<str.length()-1){
//                num_string_second+= Character.toString(str.charAt(index));
//                index++;
//            }
//            nums[counter] = Integer.valueOf(num_string_second, base);
//            System.out.println("second: " + nums[counter]); //two operations in a row
////            if(operator.contains(Character.toString(str.charAt(index)))){
////
////            }
////            index++;
        }
        System.out.println("nums arr: " + Arrays.toString(nums));
        index=0;
        while(index<str.length()-1) {

            if(operator.contains(Character.toString(str.charAt(index)))){
                operations_arr[counter_operations] = str.charAt(index);
                counter_operations++;
                }

            index++;
            }
        System.out.println("operations_arr: " + Arrays.toString(operations_arr)); //two operations in a row
        String priority_operations = "*/";
        int final_num = 0; boolean flag_operations = false; int i=0;
        while( i<counter_operations){
           if(i<counter_operations-1 &&
                    priority_operations.contains(Character.toString(operations_arr[i+1])) && !priority_operations.contains(Character.toString(operations_arr[i]))){
                //case of second operations is priority, first isn't, then do second first
                flag_operations=true;
                if(i==0){
                    final_num = nums[1];
                }
                if(operations_arr[i+1]=='*'){
                    final_num += nums[i+1]*nums[i+2] ;
                }
                if(operations_arr[i+1]=='/'){
                    if(nums[i+2]==0){
                        System.out.println("Error: division by zero");
                    }
                    final_num +=nums[i+1] / nums[i+2];
                }
                //TODO: fix for case of first
            }
            if(operations_arr[i]=='+'){
                final_num += nums[i]+ nums[i+1] ;
            }
            if(operations_arr[i]=='-'){
                final_num += (nums[i]-nums[i+1]);
            }
            if(operations_arr[i]=='*'){
                final_num += nums[i]*nums[i+1];
            }
            if(operations_arr[i]=='/'){
                if(nums[i+1]==0){
                    System.out.println("Error: division by zero");
                }
                final_num += nums[i]/=nums[i+1];
            }

            if(flag_operations){
                i++;
            }
            i++;
        }
        String final_num_str = Integer.toString(final_num);

        //TODO: add error Error: invalid expression: "6A" not valid base 16 - hexa
        if(base==16){
            final_num_str = Integer.toHexString(final_num);
        }
        if(base==8){
            final_num_str = Integer.toOctalString(final_num);
        }
        if(base==2){
            final_num_str = Integer.toBinaryString(final_num);
        }
        System.out.println("The value of expression "+ str+ " is: " + final_num_str);

//        Integer.valueOf(str, base); //outputs int from string converted from base
//        Integer.toHexString();
//        Integer.toOctalString();
//        Integer.toBinaryString();

    }

    //flow:
    //1. convert number to decimal base,
    // 2. apply computations
    // convert to original base and return output
    // use native java funcs!
}
