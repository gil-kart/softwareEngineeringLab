package org.example;
import java.util.*;

public class Sum {
    public static void main(String[] Args) {

        Input input = get_input();
        if(!check_expression_valid(input))
            return;
        Expression exp = parse_expression(input.expression);
        if(wrong_base_digits(exp, input.base))
            return;
        List<Float> list_of_num = create_list_of_decimals(exp, input.base);
        float res = calc_in_decimal(list_of_num, exp.signs);
        print_result_in_orig_base(res, input);

    }

    private static void print_result_in_orig_base(float res, Input input) {
        String result="";
        if(input.base == 2)
            result = Integer.toBinaryString((int) res);
        else if(input.base == 8)
            result = Integer.toOctalString((int) res);
        else if(input.base == 10)
            result = String.valueOf((int) res);
        else if(input.base == 16)
            result = Integer.toHexString((int) res);
        System.out.println("The value of expression " + input.orig_expression + " is: " + result.toUpperCase());

    }


    private static Boolean check_expression_valid(Input input) {
        if(division_by_zero(input.expression)){
            System.out.println("Error: trying to divide by 0 (evaluated: \"0\")");
            return false;
        }
        if(invalid_expression(input.expression)){
            System.out.println("Error: invalid expression: \"\"");
            return false;
        }
        return true;
    }

    private static boolean wrong_base_digits(Expression exp, int base) {
        for(int i=0; i<exp.terminals.size() ; i++) {
            char[] check = exp.terminals.get(i);
            String str = new String(check);
            try{
                Integer.valueOf(str, base);
            }
            catch (Exception e){
                System.out.println("Error: invalid expression: " + "\"" + str + "\"");
                return true;
            }
        }
        return false;
    }

    private static Expression parse_expression(String expression) {
        List<char[]> terminals =new ArrayList<>();
        List<Character> signs = new ArrayList<>();

        int len = expression.length();
        int last_sign_index = 0;

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
        char[] terminal = new char[len - last_sign_index - 1];
        expression.getChars(last_sign_index + 1, len, terminal, 0);
        terminals.add(terminal);

        return new Expression(terminals, signs);
    }

    private static Input get_input() {
        boolean input_not_correct = true;
        int base = 10;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter base (2/8/10/16):");
        while(input_not_correct) {
            base = input.nextInt();
            if (base != 2 && base != 8 && base != 10 && base != 16) {
                System.out.println("Error - this base isn\'t supported. Please enter a base (2/8/10/16):");
            }
            else{
                input_not_correct = false;
            }
        }

        System.out.println("Enter expression:");
        String expression = input.nextLine();
        String orig_expression = input.nextLine();
        expression = orig_expression.replaceAll(" ","");
        input.close();
        return new Input(base, expression, orig_expression);
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

    public static List<Float> create_list_of_decimals(Expression exp, int base){
        List<Float> list_of_num = new ArrayList<>();
        for(int i=0; i<exp.terminals.size() ; i++) {
            char[] check = exp.terminals.get(i);
            String str = new String(check);
            float number;
            try{
                number = Integer.valueOf(str, base);
            }
            catch (Exception e){
                System.out.println("Error: invalid expression: " + "\"" + str + "\"");
                return list_of_num;
            }
            list_of_num.add(number);
        }
        return list_of_num;
    }
    public static float calc_in_decimal(List<Float> list_of_num, List<Character> signs) {
        for(int i=0; i<signs.size();i++){
            if(signs.get(i).equals('*')||signs.get(i).equals('/')){
                float left_var = list_of_num.get(i);
                float right_var = list_of_num.get(i+1);
                if(signs.get(i).equals('*'))
                    list_of_num.set(i, left_var * right_var);
                else
                    list_of_num.set(i, Float.valueOf((int)(left_var / right_var)));
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

class Input {
    int base;
    String expression;
    String orig_expression;
    Input(int _base, String _expression,String _orig_expression)
    {
        base = _base;
        expression = _expression;
        orig_expression =  _orig_expression;
    }
}

class Expression {
    List<char[]> terminals;
    List<Character> signs;
    Expression(List<char[]> _terminals,List<Character> _signs)
    {
        terminals = _terminals;
        signs = _signs;
    }
}