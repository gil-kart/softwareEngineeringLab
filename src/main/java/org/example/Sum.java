package org.example;

import java.util.*;

public class Sum {
    public static void main(String[] Args) {
        // Allow user input
        boolean input_not_correct = true;
        int base = 10;
        Scanner input = new Scanner(System.in);

        System.out.println("Enter base (2/8/10/16):");
        while(input_not_correct) {
            base = input.nextInt();
            if (base != 2 && base != 8 && base != 10 && base != 16) {
                System.out.println("Error - this base isn’t supported. Please enter a base (2/8/10/16):\n");
            }
            else{
                input_not_correct = false;
            }
        }

        System.out.println("Enter expression:");
        String expression = input.nextLine();
        expression = input.nextLine();
        String save_expression = expression;
        expression = expression.replaceAll(" ","");

        List<char[]> terminals =new ArrayList<char[]>();
        List<Character> signs = new ArrayList<>();

        int len = expression.length();
        int last_sign_index = 0;
        float res=0;
        for(int i=0; i<len ; i++){
            if(expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' || expression.charAt(i) == '/'){
                signs.add(expression.charAt(i));
                if(last_sign_index == 0) {
                    char[] terminal = new char[i - last_sign_index];
                    expression.getChars(last_sign_index, i, terminal, 0);
                    terminals.add(terminal);
                }
                else {
                    char[] terminal = new char[i - last_sign_index - 1];
                    expression.getChars(last_sign_index + 1, i, terminal, 0);
                    terminals.add(terminal);
                }
                last_sign_index = i;
            }
        }
        if(division_by_zero(expression)){
            System.out.println("Error: trying to divide by 0 (evaluated: \"0\")");
            input.close();
            return;
        }
        if(invalid_expression(expression)){
            System.out.println("Error: invalid expression: \"\"");
            input.close();
            return;
        }

        char[] terminal = new char[len - last_sign_index - 1];
        expression.getChars(last_sign_index + 1, len, terminal, 0);
        terminals.add(terminal);


        List<Float> list_of_num = new ArrayList<Float>();
        for(int i=0; i<terminals.size() ; i++) {
            char[] check = terminals.get(i);
            String str = new String(check);
            float number=0;
            try{
                number = Integer.valueOf(str, base);
            }
            catch (Exception e){
                System.out.println("Error: invalid expression: " + "\"" + str + "\"");
                input.close();
                return;
            }
            list_of_num.add(number);
        }
        res = calc_in_decimal(list_of_num, signs);

        String result="";
        if(base == 2)
            result = Integer.toBinaryString((int) res);
        else if(base == 8)
            result = Integer.toOctalString((int) res);
        else if(base == 10)
            result = String.valueOf(res);
        else if(base == 16)
            result = Integer.toHexString((int) res);

        System.out.println("The value of expression " + save_expression + " is: " + result.toUpperCase());

        input.close();
    }

    public static Boolean division_by_zero(String expression){
        for(int i=0; i<expression.length() - 1; i++)
            if(expression.charAt(i) == '/' && expression.charAt(i+1) == '0')
                return true;
        return false;
    }
    public static Boolean invalid_expression(String expression){
        for(int i=0; i<expression.length() - 1; i++)
            if((expression.charAt(i) == '/' || expression.charAt(i) == '*' || expression.charAt(i) == '+' || expression.charAt(i) == '-')
                    && (expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '+' || expression.charAt(i + 1) == '-'))
                return true;
        return false;
    }


    public static float calc_in_decimal(List<Float> list_of_num, List<Character> signs) {
        for(int i=0; i<signs.size();i++){
            if(signs.get(i).equals('*')||signs.get(i).equals('/')){
                float left_var = list_of_num.get(i);
                float right_var = list_of_num.get(i+1);
                if(signs.get(i).equals('*'))
                    list_of_num.set(i, left_var * right_var);
                else
                    list_of_num.set(i, left_var / right_var);
                list_of_num.remove(i+1);
                signs.remove(i);
                i--;
            }
        }
        float res = list_of_num.get(0);
        for(int i=0; i<signs.size();i++){
            float right_var = list_of_num.get(i+1);
            if(signs.get(i).equals('+'))
                res += right_var;
            else
                res -= right_var;
        }
        return res;
    }
}
