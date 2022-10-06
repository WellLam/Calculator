package com.example.a2105_caculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText display;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        display.setShowSoftInputOnFocus(false);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.display).equals(display.getText().toString())){
                    display.setText("");
                }
            }
        });
    }


    public void updateText(String strToAdd){
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if (getString(R.string.display).equals(display.getText().toString())){
            display.setText(strToAdd);
        }else {
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
            display.setSelection(cursorPos + 1);
        }

    }
    public int getPriority(String str) {
        int pri=-1;

        if(str.equals("("))
            pri=0;
        else if(str.equals("+") || str.equals("-"))
            pri=1;
        else if(str.equals("×") || str.equals("÷"))
            pri=2;

        return pri;//优先级
    }

    public String[] split() {
        ArrayList<String> strList=new ArrayList<String>();
        int splitIndex = 0;
        String a = display.getText().toString();

        for(int i=0;i< a.length(); i++) {
            if(a.charAt(i)=='+' || a.charAt(i)=='-' || a.charAt(i)=='×' || a.charAt(i)=='÷' ){
                if(splitIndex == i) { //处理连续多个操作符
                    if(a.charAt(i)=='-') {     //处理操作符"( + - * /"符号后面遇到"-"情况时，将后面的数字当做负数处理
                        splitIndex=i;
                        continue;
                    }
                    else
                        strList.add(a.substring(splitIndex, i+1));
                }
                else {
                    strList.add(a.substring(splitIndex, i));
                    strList.add(a.substring(i, i+1));
                }
                splitIndex=i+1;
            }
            if((i==a.length()-1) && (splitIndex<=i))
                strList.add(a.substring(splitIndex));
        }

        return strList.toArray(new String[0]);
    }

    public String[] getSuffixExpression(String[] expression) {
        Stack<String> strStack=new Stack<String>();
        ArrayList<String> strList=new ArrayList<String>();

        for(int i=0;i<expression.length;i++) {

            if(expression[i].equals("+") || expression[i].equals("-") ||expression[i].equals("×") ||expression[i].equals("÷")) {
                if(strStack.isEmpty())
                    strStack.push(expression[i]);
                else {
                    while((!strStack.isEmpty()) && (getPriority(strStack.peek())>=getPriority(expression[i]))) {
                        strList.add(strStack.pop());
                    }
                    strStack.push(expression[i]);
                }
            }
            else
                strList.add(expression[i]);
        }
        while(!strStack.isEmpty())
            strList.add(strStack.pop());

        return strList.toArray(new String[0]);
    }

    public double getResult(String[] expression) {
        Stack<Double> strStack=new Stack<Double>();
        double num1,num2;
        double result=0;

        for(int i=0;i<expression.length;i++) {
            if(expression[i].equals("+") || expression[i].equals("-") ||expression[i].equals("×") ||expression[i].equals("÷")) {
                String str=expression[i];
                switch(str) {
                    case "+":{num1=strStack.pop();num2=strStack.pop();result=num2+num1;strStack.push(result);break;}
                    case "-":{num1=strStack.pop();num2=strStack.pop();result=num2-num1;strStack.push(result);break;}
                    case "×":{num1=strStack.pop();num2=strStack.pop();result=num2*num1;strStack.push(result);break;}
                    case "÷":{num1=strStack.pop();num2=strStack.pop();result=num2/num1;strStack.push(result);break;}
                }
            }
            else
                strStack.push(Double.valueOf(expression[i]));
        }

        result=0;
        while(!strStack.isEmpty()) {
            result+=strStack.pop();
        }
        return result;
    }

    public String getResult() {
        String res = String.valueOf(getResult(getSuffixExpression(split())));
        return res;
    }

    // numbersBTN
    public void oneBTN(View view){
        updateText("1");
    }

    public void twoBTN(View view){
        updateText("2");
    }

    public void threeBTN(View view){
        updateText("3");
    }

    public void fourBTN(View view){
        updateText("4");
    }

    public void fiveBTN(View view){
        updateText("5");
    }

    public void sixBTN(View view){
        updateText("6");
    }

    public void sevenBTN(View view){
        updateText("7");
    }

    public void eightBTN(View view){
        updateText("8");
    }

    public void nineBTN(View view){
        updateText("9");
    }

    public void zeroBTN(View view){
        updateText("0");
    }


    // functionBTN
    public void addBTN(View view){
        updateText("+");
    }

    public void subtractBTN(View view){
        updateText("-");
    }

    public void multiplyBTN(View view){
        updateText("×");
    }

    public void divideBTN(View view){
        updateText("÷");
    }

    public void pointBTN(View view){
        updateText(".");
    }



    public void clearBTN(View view){
        display.setText("");
    }


    //result
    public void equalsBTN(View view){
        display.setText(getResult());
    }
}