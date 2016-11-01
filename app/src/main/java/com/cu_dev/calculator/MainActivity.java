package com.cu_dev.calculator;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cu_dev.calculator.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static final int RC_USER_DATA = 0;
    private ActivityMainBinding binding;

    public enum Operations {
        Add, Subtract, Multiply, Divide, Factorial
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        CalculatorData data = new CalculatorData();
        data.num1.set(13l);
        binding.setData(data);
        Log.i("chris", "Calcualtor started");

    }
    public void add(View view) {
        operate(Operations.Add);
    }
    public void subtract(View view) {
        operate(Operations.Subtract);
    }

    public void multiply(View view){
        operate(Operations.Multiply);
    }

    public void divide(View view) {
        try {
            operate(Operations.Divide);
        } catch (ArithmeticException e) {
            Toast.makeText(this, "Not so fast buster!", Toast.LENGTH_LONG).show();
            binding.getData().num2.set(1l);
        }
    }

    public void factorial(View view) {
        operate(Operations.Factorial);
    }


    public void operate(Operations op) {
        long num1 = binding.getData().num1.get();
        long num2 = binding.getData().num2.get();
        long result;
        switch (op){
            case Add:
                result = num1 + num2;
                break;
            case Subtract:
                result = num1 - num2;
                break;
            case Multiply:
                result = num1 * num2;
                break;
            case Divide:
                result = num1 / num2;
                break;
            case Factorial:
                result = fact(num1);
                break;
            default:
                result = 0;
        }
        binding.getData().result.set(result);
    }

    private long fact(long num1) {
        return num1 <= 1 ? 1 : num1 * fact(num1 - 1);
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, UserInfoActivity.class);

        startActivityForResult(intent, RC_USER_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("chris", "Got activity result");
        if (requestCode == RC_USER_DATA) {
            if (resultCode == RESULT_OK) {
                binding.getData().userAge.set(data.getLongExtra(getString(R.string.ext_user_age), 0));
                binding.getData().userName.set(data.getStringExtra(getString(R.string.ext_user_name)));
            } else {
                Toast.makeText(this, "Failed to get your user data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
