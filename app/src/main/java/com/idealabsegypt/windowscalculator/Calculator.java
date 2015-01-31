package com.idealabsegypt.windowscalculator;

/**
 * Created by sherif on 1/28/2015.
 */
public class Calculator {
    private String smallDisplay;
    private String LargeDisplay;
    double mem;
    double buff;
    double total;
    boolean AfterDecimal;

    //operations
    String firstOperand;
    String secondOperand;
    String Op;
    boolean isOperation; //false is number, true is operation
    boolean clearDisplayFirst;

    public Calculator() {
        smallDisplay = "";
        LargeDisplay = "0";
        mem = 0f;
        buff = 0f;
        total = 0f;
        AfterDecimal = false;
        clearDisplayFirst = false;
        isOperation = false;

        firstOperand = "";
        secondOperand = ""; //to mark it's been used or not

    }


    public void pressed(String buttonValue) {
        checkToAllowDecimalPoint();


        if (buttonValue.equals("←")) {
            LargeDisplay = removeLastChar(LargeDisplay);
            buff = Double.parseDouble(LargeDisplay);


        }
        //Handling operations
        else if (buttonValue.equals("+") ||
                buttonValue.equals("-") ||
                buttonValue.equals("*") ||
                buttonValue.equals("/") ||
                buttonValue.equals("=")) {
            if (isOperation) {
                //to add 3+ functionality, you can simply implement here that if + then = we should put secondop to be equal to first op
                Op = buttonValue;
                clearDisplayFirst = true;
                return;
            }


            isOperation = true;
            if (firstOperand.equals("")) {
                firstOperand = LargeDisplay;
            } else if (firstOperand.length() != 0 && secondOperand.length() == 0) {
                secondOperand = LargeDisplay;
                firstOperand = doOperation(firstOperand, Op, secondOperand);
                LargeDisplay = firstOperand;
                secondOperand = "";
            }


            Op = buttonValue;
            clearDisplayFirst = true;

            if (Op.equals("=")) {
                firstOperand = "";
                secondOperand = "";
                Op = "";
                isOperation = false;
            }
        }


        //this should be replaced by a more strong one
        if (LargeDisplay.length() == 16) return;


        //numbers and decimal points
        else if (buttonValue.equals(".") && !AfterDecimal) {
            checkToClearDisplayFirst();
            AfterDecimal = true;
            LargeDisplay += buttonValue;
        } else if (buttonValue.equals("0") ||
                buttonValue.equals("1") ||
                buttonValue.equals("2") ||
                buttonValue.equals("3") ||
                buttonValue.equals("4") ||
                buttonValue.equals("5") ||
                buttonValue.equals("6") ||
                buttonValue.equals("7") ||
                buttonValue.equals("8") ||
                buttonValue.equals("9")) {
            isOperation = false;
            checkToClearDisplayFirst();
            if (LargeDisplay.equals("0")) LargeDisplay = "";

            LargeDisplay += buttonValue;
            buff = Double.parseDouble(LargeDisplay);
        }


    }

    private String doOperation(String firstOperand, String op, String secondOperand) {
        double result = 0f;
        double one = Double.parseDouble(firstOperand);
        double two = Double.parseDouble(secondOperand);
        switch (op) {
            case "+":
                result = one + two;
                break;
            case "-":
                result = one - two;
                break;
            case "*":
                result = one * two;
                break;
            case "/":
                result = one / two;
                break;
            default:
                result = one;
        }
        return String.valueOf(result);
    }

    private void checkToAllowDecimalPoint() {
        if (!LargeDisplay.contains("."))
            AfterDecimal = false;
        else
            AfterDecimal = true;
    }


    private void checkToClearDisplayFirst() {
        if (clearDisplayFirst) {
            LargeDisplay = "0";
            buff = Double.parseDouble(LargeDisplay);
            clearDisplayFirst = false;
        }


    }

    private static String removeLastChar(String str) {
        if (str.length() == 1) {
            return "0";
        }

        return str.substring(0, str.length() - 1);
    }


    public String getLargeDisplay() {
        return LargeDisplay;
    }

    public String getSmallDisplay() {
        return smallDisplay;
    }


}
