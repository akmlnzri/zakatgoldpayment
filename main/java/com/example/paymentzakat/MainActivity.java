package com.example.paymentzakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText weight, currentgold;
    RadioButton keep, wear;
    Button calculate, clear;
    RadioGroup radioGroup;
    TextView totvalue, totgoldzakat, totzakat;
    float outweight, outcurrentgold, goldtype, outvalue, outgoldzakat, outzakat;

    String Shared_pref = "sharedPrefs";
    String gold_weight = "weight";
    String gold_value = "currentvalue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        currentgold = (EditText) findViewById(R.id.currentgold);
        keep = (RadioButton) findViewById(R.id.keep);
        wear = (RadioButton) findViewById(R.id.wear);
        calculate = (Button) findViewById(R.id.calculate);
        clear = (Button) findViewById(R.id.clear);
        totvalue = (TextView) findViewById(R.id.totvalue);
        totgoldzakat = (TextView) findViewById(R.id.totgoldzakat);
        totzakat = (TextView) findViewById(R.id.totzakat);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        boolean keepChecked = keep.isChecked();
        boolean wearChecked = wear.isChecked();
        calculate.setOnClickListener(this);
        clear.setOnClickListener(this);

        calculate.setBackgroundColor(Color.BLACK);
        clear.setBackgroundColor(Color.BLACK);

        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        String weightgold = sharedPreferences.getString(gold_weight, "");
        weight.setText(weightgold);
        String goldvalue = sharedPreferences.getString(gold_value, "");
        currentgold.setText(goldvalue);

        //String weight = sharedPref.getString("weight", "");
        //String currentgold = sharedPref.getString("current_gold", "");
        //String totvalue = sharedPref.getString("tot_value", "");
        //String totgoldzakat = sharedPref.getString("tot_gold_zakat", "");
        //String totzakat = sharedPref.getString("tot_zakat", "");

        //sharedPreferences1 = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        //outweight = sharedPreferences1.getFloat("weight", 0);
        //weight.setText("" + outweight);

        //sharedPreferences2 = this.getSharedPreferences("currentgold", Context.MODE_PRIVATE);
        //outcurrentgold = sharedPreferences2.getFloat("currentgold", 0);
        //currentgold.setText("" + outcurrentgold);

    }

    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(gold_weight, weight.getText().toString());
        editor.putString(gold_value, currentgold.getText().toString());

        editor.apply();

        //Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show();

        switch (view.getId()) {

            case R.id.calculate:
                CalculateOutput();

                //editor.putString("weight", weight.getText().toString());
                //editor.putString("current_gold", currentgold.getText().toString());

                //editor.putString("tot_value", totvalue.getText().toString());
                //editor.putString("tot_gold_zakat", totgoldzakat.getText().toString());
                //editor.putString("tot_zakat", totzakat.getText().toString());
                //editor.apply();
                break;

            case R.id.clear:
                Clear();
                Clearbutton();
                break;
        }
    }

    public void CalculateOutput() {
        try {
            outweight = Float.parseFloat(weight.getText().toString());

            if (keep.isChecked())
                goldtype = 85;
            else if (wear.isChecked())
                goldtype = 200;
            outcurrentgold = Float.parseFloat(currentgold.getText().toString());

            outvalue = outweight * outcurrentgold;
            outgoldzakat = (outweight - goldtype) * outcurrentgold;
            outzakat = (float) (outgoldzakat * 0.025);

            //SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            //editor1.putFloat("weight", outweight);
            //editor1.apply();

            //SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            //editor2.putFloat("gold", outcurrentgold);
            //editor2.apply();

            totvalue.setText("Total value of the gold is " + String.format("RM%.2f", outvalue));
            totgoldzakat.setText("Total gold value zakat payable is " + String.format("RM%.2f", outgoldzakat));
            totzakat.setText("Total zakat is " + String.format("RM%.2f", outzakat));

        } catch(java.lang.NumberFormatException nfe){

            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();

        }
    }

    public void Clear(){
        weight.setText("");
        currentgold.setText("");

        totvalue.setText("Total value of the gold");
        totgoldzakat.setText("Total gold value zakat payable");
        totzakat.setText("Total zakat");
    }

    public void Clearbutton(){
        //keep.setChecked(false);
        //wear.setChecked(false);
        radioGroup.clearCheck();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this, "This is home", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.about:
                //Toast.makeText(this, "This is about", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
