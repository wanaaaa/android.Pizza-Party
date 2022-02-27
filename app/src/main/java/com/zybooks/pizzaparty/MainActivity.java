package com.zybooks.pizzaparty;

import static android.util.Log.println;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "WanActivity====>>>";

    public final static int SLICES_PER_PIZZA = 8;

    private EditText mNumAttendEditText;
    private EditText mNumToppingEditText;
    private TextView mNumPizzasTextView;
    private TextView mNumTotalPriceView;
    private RadioGroup mHowHungryRadioGroup;
//    =============
    private int mTotalPizzas;
    private final String KEY_TOTAL_PIZZAS = "totalPizzas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("adf", "asdasdfasfd====================>>>>>>>>>>>>>>>>>>>>");
        // Assign the widgets to fields
        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);
        mNumToppingEditText = findViewById(R.id.num_toppings);
        mNumTotalPriceView = findViewById(R.id.num_total_price_view);
        mNumPizzasTextView = findViewById(R.id.num_pizzas_text_view);
        mHowHungryRadioGroup = findViewById(R.id.hungry_radio_group);

        mNumAttendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String numAttendStr = charSequence.toString();
                    int numAttInt = Integer.parseInt(numAttendStr);
                    if (numAttInt > 200) {
                        CharSequence text = "Hello toast! It's too much, less than 2005";
                        int duration = Toast.LENGTH_LONG;
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        }) ;

        if(savedInstanceState != null) {
            mTotalPizzas = savedInstanceState.getInt(KEY_TOTAL_PIZZAS);
            displayTotal();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TOTAL_PIZZAS, mTotalPizzas);
    }
    private void displayTotal() {
        String totalText = getString(Integer.parseInt("9999"), mTotalPizzas);
        mNumPizzasTextView.setText(totalText);
    }

    public  void calTotalFun(View view) {
        Log.d(TAG, "calTotalFun: =>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void calculateClick(View view) {

        int numAttend;
        int intTopping = 0;
        try {
            String numAttendStr = mNumAttendEditText.getText().toString();
            numAttend = Integer.parseInt(numAttendStr);

            String numToppingStr = mNumToppingEditText.getText().toString();
            intTopping = Integer.parseInt(numToppingStr);
            //Log.d("numopping ###########", numToppingStr);
        }
        catch (NumberFormatException ex) {
            numAttend = 0;
        }

        // Get hunger level selection
        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
        PizzaCalculator.HungerLevel hungerLevel = PizzaCalculator.HungerLevel.RAVENOUS;
        if (checkedId == R.id.light_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.LIGHT;
        }
        else if (checkedId == R.id.medium_radio_button) {
            hungerLevel = PizzaCalculator.HungerLevel.MEDIUM;
        }

        // Get the number of pizzas needed
        PizzaCalculator calc = new PizzaCalculator(numAttend, hungerLevel);
        int totalPizzas = calc.getTotalPizzas();
//        int totalPrice =
        // Place totalPizzas into the string resource and display
        //String totalText = getString(totalPizzas, R.string.total_pizzas);
        //String totalText = getString(R.string.total_pizzas, totalPizzas);
        mNumPizzasTextView.setText("The toital Pizza is: " + totalPizzas);
        int totalPriceInt = intTopping * 3  + totalPizzas*10 ;
        String ss = String.valueOf(totalPriceInt);

        mNumTotalPriceView.setText(ss);
        //mNumPizzasTextView.setText(totalText)

        //===================================================================================
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        //        // Get the text that was typed into the EditText
//        String numAttendStr = mNumAttendEditText.getText().toString();
//
//        // Convert the text into an integer
//        int numAttend = Integer.parseInt(numAttendStr);
//
//        // Determine how many slices on average each person will eat
//        int slicesPerPerson = 0;
//        int checkedId = mHowHungryRadioGroup.getCheckedRadioButtonId();
//        if (checkedId == R.id.light_radio_button) {
//            slicesPerPerson = 2;
//        }
//        else if (checkedId == R.id.medium_radio_button) {
//            slicesPerPerson = 3;
//        }
//        else if (checkedId == R.id.ravenous_radio_button) {
//            slicesPerPerson = 4;
//        }
//
//        // Calculate and show the number of pizzas needed
//        int totalPizzas = (int) Math.ceil(numAttend * slicesPerPerson / (double) SLICES_PER_PIZZA);
//        mNumPizzasTextView.setText("Total pizzas: " + totalPizzas);

    }//calculateClick
}


