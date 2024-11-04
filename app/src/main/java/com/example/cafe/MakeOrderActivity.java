package com.example.cafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MakeOrderActivity extends AppCompatActivity {

    private static final String EXTRA_USER_NAME = "userName";

    private TextView textViewGreetings;
    private TextView textViewAdditives;

    private RadioGroup radioGroupDrinks;
    private RadioButton radioButtonTea;
    private RadioButton radioButtonCoffee;

    private CheckBox checkboxSugar;
    private CheckBox checkboxMilk;
    private CheckBox checkboxLemon;

    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private Button buttonMakeOrder;

    private String drink;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        initViews();
        setupUserName();

        radioGroupDrinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                if (id == radioButtonTea.getId()) {
                    onUserChoseTea();
                } else if (id == radioButtonCoffee.getId()) {
                    onUserChoseCoffee();
                }
            }
        });

        radioButtonTea.setChecked(true);

        buttonMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserMadeOrder();
            }
        });
    }

    private void onUserMadeOrder() {
        ArrayList<String> additives = new ArrayList<>();
        if (checkboxSugar.isChecked()) {
            additives.add(checkboxSugar.getText().toString());
        }
        if (checkboxMilk.isChecked()) {
            additives.add(checkboxMilk.getText().toString());
        }
        if (radioButtonTea.isChecked() && checkboxLemon.isChecked()) {
            additives.add(checkboxLemon.getText().toString());
        }

        String drinkType = "";
        if (radioButtonTea.isChecked()){
            drinkType = spinnerTea.getSelectedItem().toString();
        } else if (radioButtonCoffee.isChecked()) {
            drinkType = spinnerCoffee.getSelectedItem().toString();
        }

        Intent intent = OrderDetailActivity.newIntent(
                this,
                userName,
                drink,
                additives.toString(),
                drinkType
        );
        startActivity(intent);
    }

    private void onUserChoseTea() {
        drink = getString(R.string.tea);
        String additivesLabel = getString(R.string.additives, drink);
        textViewAdditives.setText(additivesLabel);
        checkboxLemon.setVisibility(View.VISIBLE);
        spinnerTea.setVisibility(View.VISIBLE);
        spinnerCoffee.setVisibility(View.INVISIBLE);
    }

    private void onUserChoseCoffee() {
        drink = getString(R.string.coffee);
        String additivesLabel = getString(R.string.additives, drink);
        textViewAdditives.setText(additivesLabel);
        checkboxLemon.setVisibility(View.INVISIBLE);
        spinnerTea.setVisibility(View.INVISIBLE);
        spinnerCoffee.setVisibility(View.VISIBLE);
    }



    private void setupUserName() {
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);
        String greetings = getString(R.string.greeting, userName);
        textViewGreetings.setText(greetings);
    }

    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, MakeOrderActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        return intent;
    }

    private void initViews() {
        textViewGreetings = findViewById(R.id.textViewGreetings);
        textViewAdditives = findViewById(R.id.textViewAdditives);
        radioGroupDrinks = findViewById(R.id.radioGroupDrinks);
        radioButtonTea = findViewById(R.id.radioButtonTea);
        radioButtonCoffee = findViewById(R.id.radioButtonCoffee);
        checkboxSugar = findViewById(R.id.checkboxSugar);
        checkboxMilk = findViewById(R.id.checkboxMilk);
        checkboxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        buttonMakeOrder = findViewById(R.id.buttonMakeOrder);
    }
}