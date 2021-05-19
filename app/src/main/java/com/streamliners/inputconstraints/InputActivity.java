package com.streamliners.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.streamliners.inputconstraints.databinding.ActivityInputBinding;

import java.util.Objects;

public class InputActivity extends AppCompatActivity {
    ActivityInputBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setTitle("Input Activity");
        hidingErrors();

//        Restoring data
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        b.inputTextField.getEditText().setText(prefs.getString(Constants.SAVE,""));
    }

    private void hidingErrors() {
        Objects.requireNonNull(b.inputTextField.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.inputTextField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //    Method for sending result back to the same activity
    public void sendResult(View view) {
        String input = Objects.requireNonNull(b.inputTextField.getEditText()).getText().toString().trim();

//        Setting errors
        if (input.isEmpty()) {
            b.inputTextField.setError("Please enter something!");
        }
        else if (!input.matches(validateInput())) {
            b.inputTextField.setError("Invalid! enter according to the checked boxes.");

         /*   if (!input.matches(Constants.UPPERCASE)) {
                b.inputTextField.setError("Please enter Uppercase letter!");
            }
            if (!input.matches(Constants.LOWERCASE)) {
                b.inputTextField.setError("Please enter lowercase letter");
            }
            if (!input.matches(Constants.DIGITS)) {
                b.inputTextField.setError("Please enter digits");
            }
            if (!input.matches(Constants.OPERATORS)) {
                b.inputTextField.setError("Please enter +, -, /, *");
            }
            if (!input.matches(Constants.OTHERS)) {
                b.inputTextField.setError("Please enter symbols");
            }*/

        }

//        Sending back result using new intent to the previous activity
        else {
            Intent intent = new Intent(this, InputConstraintActivity.class);
            intent.putExtra(Constants.INPUT, input);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
//        Setting specific errors acc to the checked boxes

    private String validateInput() {
//        Getting bundle
        Bundle bundle = getIntent().getExtras();

//        Getting the bundle and key-Values of the checked boxes
        for (String str : bundle.keySet()) {
            bundle.getString(str);
        }
//        Append Regex into string using string builder
        StringBuilder regex = new StringBuilder();

//        Checking the regex for our checkbox inputs
        regex.append("^([");
//        Checking if we get value of Uppercase then append Regex for uppercase into the String
        if (Boolean.parseBoolean(bundle.getString(Constants.UPPERCASE, "false")))
            regex.append("A-Z");

//        Checking same for lowercase, digits, operations and symbols
        if (Boolean.parseBoolean(bundle.getString(Constants.LOWERCASE, "false")))
            regex.append("a-z");

        if (Boolean.parseBoolean(bundle.getString(Constants.DIGITS, "false")))
            regex.append("0-9");

        if (Boolean.parseBoolean(bundle.getString(Constants.OPERATORS, "false")))
            regex.append("+-/*%");

        if (Boolean.parseBoolean(bundle.getString(Constants.OTHERS, "false")))
            regex.append("@#\\\\^{}\\]\"\"^()?`~!;:''.,|\\[");


        regex.append("])+");
//        Returning Final String
        return regex.toString();


    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.edit().putString(Constants.SAVE, b.inputTextField.getEditText().getText().toString().trim());
    }
}
