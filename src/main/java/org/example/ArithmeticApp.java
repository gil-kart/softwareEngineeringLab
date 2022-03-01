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
//        String[] strArray = null;
//        strArray = str.split(" ");
//
        String operator ="+-/*";
        int len = str.length()-1;
        for(int i =0; i<len; i++){
            if(operator.contains(Character.toString(str.charAt(i))) && operator.contains(Character.toString(str.charAt(i+1)))){
                System.out.println("invalid expression: " + str); //two operations in a row
            }
        }
        //transferring input string to nums array
        int index=0;
        int[] nums = new int[str.length()];
        char[] operations_arr = new char[str.length()];
        int counter_nums=0; int counter_operations=0;
        while(index<=str.length()) {
            String num_string_first =new String();
            if(index<str.length()&&operator.contains(Character.toString(str.charAt(index)))==false&& str.charAt(index)!=' '){
                while(index<str.length()&&operator.contains(Character.toString(str.charAt(index)))==false&& str.charAt(index)!=' ')
                {
                    num_string_first+= Character.toString(str.charAt(index));
                    System.out.println("num_string_first: " + num_string_first);
                    //checking if input is  legal according to base:
                    if(base==8) {
                        for (int i = 0; i < counter_nums; i++) {
                            char[] chars = ("" + num_string_first).toCharArray();
                            for(int j=0; j<chars.length; j++){
                                if (chars[j] < '0' || chars[j] > '8') {
                                    System.out.println("Error: invalid expression:" + num_string_first);
                                    return;
                                }
                            }
                        }
                    }
                    if(base==2) {
                        for (int i = 0; i < counter_nums; i++) {
                            char[] chars = ("" + num_string_first).toCharArray();
                            for (int j = 0; j < chars.length; j++) {
                                if (chars[j] < '0' || chars[j] > '1') {
                                    System.out.println("Error: invalid expression:" + num_string_first);
                                    return;
                                }
                            }
                        }
                    }
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
        System.out.println("nums arr: " + Arrays.toString(nums)); //TODO: remove later

        //transferring operations in input string to operations array
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
        int final_num = 0; boolean flag_operations = false; boolean finished_operations = false; int i=0; int j=0;
        while( i<counter_operations){
//            if(j==counter_operations-1){
//                if(operations_arr[i]=='+'){
//                    final_num += nums[j] ;
//                }
//                if(operations_arr[i]=='-'){
//                    final_num -= nums[j];
//                }
//                if(operations_arr[i]=='*'){
//                    final_num *= nums[i];
//                }
//                if(operations_arr[i]=='/'){
//                    if(nums[i+1]==0){
//                        System.out.println("Error: division by zero");
//                    }
//                    final_num += nums[i]/=nums[i+1];
//                }
//            }
           if(i<counter_operations-1 &&
                    priority_operations.contains(Character.toString(operations_arr[i+1])) &&
                   !priority_operations.contains(Character.toString(operations_arr[i]))){
                //case of second operations is priority, first isn't, then do second first
                flag_operations=true;
//                if(i==0){
//                    final_num = nums[1];
//                }
                if(operations_arr[i+1]=='*'){
                    //final_num += nums[i+1]*nums[i+2] ;
                    nums[i+1] = nums[i+1]*nums[i+2] ;
                }
                if(operations_arr[i+1]=='/'){
                    if(nums[i+2]==0){
                        System.out.println("Error: division by zero");
                    }
                    //final_num += nums[i+1]/nums[i+2];
                    nums[i+1] = nums[i+1]/nums[i+2];
                }
//                if(i+2==counter_operations){
//                    finished_operations=true; //applied all operations
//                }

               //TODO: fix for case of 1*2+3*1+5
            }
           if(!finished_operations){
               if (i == 0 && (flag_operations==false && !priority_operations.contains(Character.toString(operations_arr[i])))) {
                   final_num = nums[i];
               }
               if(flag_operations ){//&& i+2!=counter_operations){ //TODO: fix
                   final_num = nums[i];
               }
               if (operations_arr[i] == '+') {
                   final_num += nums[i + 1];
               }
               if (operations_arr[i] == '-') {
                   final_num -= nums[i + 1];
               }
               if (operations_arr[i] == '*') {
                   final_num += nums[i]*nums[i+1] ;
                   //nums[i + 1] = nums[i] * nums[i + 1];

               }
               if (operations_arr[i] == '/') {
                   if (nums[i + 1] == 0) {
                       System.out.println("Error: division by zero");
                       System.exit(0);
                   }
                   final_num += nums[i]/nums[i+1];
                   //nums[i + 1] = nums[i] / nums[i + 1];
               }
           }
            if(flag_operations){
                i++;
            }
            flag_operations=false;
            i++;
//            j+=2;
        }
        String final_num_str = Integer.toString(final_num);
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

    }

    //flow:
    //1. convert number to decimal base,
    // 2. apply computations
    // convert to original base and return output
    // use native java funcs!
}


//if add or minus
//apply to final num
//
//else num i+1 = operation
//
//in case of rules:
//save operation value in num i +1
//
//spaces problem:
//change to char check?
//
//16a how to check not valid?
//if not hexa?
//
//https://www.google.com/search?q=java+check+if+number+is+legal+hexa&client=firefox-b-m&ei=RdAdYqPkDceq9u8PrvueMA&oq=java+check+if+number+is+legal+hexa&gs_lcp=ChNtb2JpbGUtZ3dzLXdpei1zZXJwEAM6BwgAEEcQsAM6BAghEAo6BAgeEApKBAhBGABQ5hRYqxxg3y1oAXABeACAAZwBiAHJBJIBAzAuNJgBAKABAcgBBMABAQ&sclient=mobile-gws-wiz-serp