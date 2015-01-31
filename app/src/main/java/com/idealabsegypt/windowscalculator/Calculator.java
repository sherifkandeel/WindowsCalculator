package com.idealabsegypt.windowscalculator;

import java.lang.reflect.Array;
import java.util.Arrays;

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


    //Memory functions
    double memory;
    boolean isMemoryInUse;

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


        memory =0f;
        isMemoryInUse=false;
    }


    public void pressed(String buttonValue) {
        checkToAllowDecimalPoint();

        if(buttonValue.equals("MS"))
        {
            memory=Double.parseDouble(LargeDisplay);
            isMemoryInUse=true;
        }
        else if(buttonValue.equals("MC")){
            memory=0;
            isMemoryInUse=false;
        }
        else if(buttonValue.equals("M+")){
            memory+=Double.parseDouble(LargeDisplay);
            isMemoryInUse=true;
        }
        else if(buttonValue.equals("M-")){
            memory-=Double.parseDouble(LargeDisplay);
            isMemoryInUse=true;
        }
        else if(buttonValue.equals("MR")){
            LargeDisplay=String.valueOf(memory);
        }

        if (buttonValue.equals("C")) {
            ClearAll();
        }

        if(buttonValue.equals("CE")){
            //to be implemented
           // ClearAll();
            LargeDisplay="0";
        }

        if (buttonValue.equals("√")) {
            double value = Double.parseDouble(LargeDisplay);
            if (value < 0)
                clearDisplayFirst = true;
            smallDisplay = displayNested("sqrt");
            //sortSmallDisplay(s);
            value = Math.sqrt(value);
            LargeDisplay = String.valueOf(value);
            buff = value;


        }

        if (buttonValue.equals("1/x")) {
            smallDisplay = displayNested("reciproc");
//            String s = "reciproc(" + LargeDisplay + ")";
            //sortSmallDisplay(s);
            double value = Double.parseDouble(LargeDisplay);
            value = 1 / value;
            LargeDisplay = String.valueOf(value);
            buff = value;
        }

        if (buttonValue.equals("%")) {
            double value = Double.parseDouble(LargeDisplay);
            double fo = 0;
            if (firstOperand.length() != 0)
                fo = Double.parseDouble(firstOperand);
            value = (fo * value) / 100;
            LargeDisplay = String.valueOf(value);
            buff = value;

        }

        if (buttonValue.equals("±")) {
            double value = Double.parseDouble(LargeDisplay);
            value *= -1f;
            LargeDisplay = String.valueOf(value);
            buff = value;
        }

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

            if (!buttonValue.equals("=")) {
                if (!smallDisplay.trim().endsWith(")")) {
                    sortSmallDisplay(LargeDisplay);
                }
                sortSmallDisplay(buttonValue);
            } else {
                smallDisplay = " ";
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
                smallDisplay = " ";
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

    private String displayNested(String operator) {
        if(smallDisplay.trim().endsWith(")")){
            String[] values = smallDisplay.split(" ");
            values[values.length-1] = operator+"("+values[values.length-1]+")";

            String Arrayout="";
            for (int i=0; i <values.length;i++)
            {
                Arrayout+=values[i]+" ";
            }
            return Arrayout;
        }
        else{
            return smallDisplay+" "+ operator +"(" + LargeDisplay + ")";
        }
    }


    private void sortSmallDisplay(String toAdd) {
        smallDisplay += " " + toAdd;

    }

    private void ClearAll() {
        smallDisplay = " ";
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
        if (LargeDisplay.endsWith(".0"))
            LargeDisplay = LargeDisplay.replace(".0", "");
        else if (LargeDisplay.contains(".")) {
            double value = Double.parseDouble(LargeDisplay);
            value = (double) Math.round(value * 1000000000) / 1000000000;
            LargeDisplay = String.valueOf(value);
        }

        return LargeDisplay;
    }

    public String getSmallDisplay() {
        return smallDisplay;
    }

    public int getMSymbol(){
        if(isMemoryInUse) return 1;
        return 0;
    }


}
