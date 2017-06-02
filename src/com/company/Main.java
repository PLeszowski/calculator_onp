package com.company;
import java.util.Stack;


public class Main {

    public static void main(String[] args) {

        String[] splitEquation;
        String equationONP = "";
        //String equation = "12 + 2 * ( 3 * 4 + 10 / 5 )"; //12 2 3 4 * 10 5 / + * +
        String equation = "12 + 3 * ( 4 + 10 / 5 ) * 3"; //12 2 3 4 * 10 5 / + * +


        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();

        splitEquation = equation.split(" ");

        //-To-Postfix-algorithm--------------------------------------------------

        for (String element : splitEquation) {

            if (element.matches("\\d+")) { // if element is a constant or variable add to stack1
                stack1.push(element);
            } else if (element.equals("(")) {  // if element is ( add to stack2
                stack2.push(element);
            } else if (element.equals(")")) {  // if element is ) move form stack2 to stack 1 until ( is reached, remove ( from stack 2 but dont copy to stack1

                while (!stack2.lastElement().equals("(")) {
                    stack1.push(stack2.pop());
                }
                stack2.pop();
            } else {

                if (stack2.isEmpty() || element.equals("^")) { // if stack2 is empty, or element is ^(highest prio) add to stack2
                    stack2.push(element);
                }

                else if (element.equals("*") || element.equals("/")) { // else if * or /
                    // if element has higher prio than the one on top of stack 2, add to stack 2
                    if (stack2.lastElement().equals("+") || stack2.lastElement().equals("-") || stack2.lastElement().equals(")") || stack2.lastElement().equals("(") || stack2.isEmpty()) {
                        stack2.push(element);
                    }
                    else { // else move form stack2 to stack 1 until lower prio element
                        while (!stack2.lastElement().equals("(") && !stack2.lastElement().equals("+") && !stack2.lastElement().equals("-") && !stack2.lastElement().equals(")") && !stack2.isEmpty()) {
                            stack1.push(stack2.pop());
                        } // and add element to stack2
                        stack2.push(element);
                    }
                }

                else { // if + or -
                    // if element has higher prio than the one on top of stack 2, add to stack 2
                    if (stack2.lastElement().equals("(") || stack2.isEmpty()) {
                        stack2.push(element);
                    }
                    else { // else move form stack2 to stack 1 until lower prio element
                        while (!stack2.lastElement().equals("(") && !stack2.isEmpty()) {
                            stack1.push(stack2.pop());
                        } // and add element to stack2
                        stack2.push(element);
                    }
                }

            }
        }//end for


        if (!stack2.isEmpty()) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }

        // stack 1 back to string
        splitEquation = new String[stack1.size()];
        for(int i = stack1.size() - 1; i >= 0; i--){
            splitEquation[i] = stack1.pop();
        }

        //-Postfix-calculation-algorithm------------------------------------------------------------------------

        String result = "";

        double x = 0, y = 0;

        Calculator calc = new Calculator();
        Stack<String> stackONP = new Stack<>();

        for (String element : splitEquation) {

            if (element.matches("\\d+")) {
                stackONP.push(element);
            }
            else {
                y = Double.parseDouble(stackONP.pop());
                x = Double.parseDouble(stackONP.pop());
                try {
                    result = String.valueOf(calc.eval(x, y, element));
                } catch (DivisionByZeroException e) {
                    e.printStackTrace();
                }
                stackONP.push(result);
            }
        }
        System.out.println(result);
    }
}