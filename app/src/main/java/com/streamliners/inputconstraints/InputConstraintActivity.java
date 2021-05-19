package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.streamliners.inputconstraints.databinding.ActivityInputConstraintsBinding;

public class InputConstraintActivity extends AppCompatActivity {
    private static final int REQUEST_INPUT = 0;
    ActivityInputConstraintsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityInputConstraintsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }


    /**
     * Taking Boolean of the checked boxes and Sending them into another activity
     *
     */
    public void takeInput(View view) {
        Bundle bundle = new Bundle();
//        Validating if the uppercase checkbox is checked
        if(b.upperCase.isChecked())
            bundle.putString(Constants.UPPERCASE,"true");
//        Validating same for lowercase, digits, operators and others CheckBoxes
        if(b.lowercaseAlpha.isChecked())
            bundle.putString(Constants.LOWERCASE,"true");

        if(b.digits.isChecked())
            bundle.putString(Constants.DIGITS,"true");

        if(b.mathOperations.isChecked())
            bundle.putString(Constants.OPERATORS,"true");

        if(b.symbols.isChecked())
            bundle.putString(Constants.OTHERS,"true");

        if(bundle.isEmpty()){
            Toast.makeText(this,"Please select Something!",Toast.LENGTH_SHORT).show();
            return;
        }

//        Making intent for sending bundle
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtras(bundle);

//      Start Activity to get result from other activity
        startActivityForResult(intent,REQUEST_INPUT);
    }

//    Method for getting result from an activity
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_INPUT && resultCode == RESULT_OK){
            assert data != null;
            b.resultText.setText("Your Input is " + data.getStringExtra(Constants.INPUT));
            b.resultText.setVisibility(View.VISIBLE);
        }
    }


}