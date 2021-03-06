package com.idealabsegypt.windowscalculator;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    Calculator calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        calc = new Calculator();

    }

    public void onClick(View v) {
        String text = ((Button)v).getText().toString();
        TextView tv = (TextView) findViewById(R.id.LargeDisplay);
        calc.pressed(text);
        String toDisplay = calc.getLargeDisplay();
        tv.setText(toDisplay);

        String toDisplayAbove = calc.getSmallDisplay();
        TextView stv  = (TextView) findViewById(R.id.smallDisplay);
        stv.setText(toDisplayAbove);

        TextView memoryView = (TextView) findViewById(R.id.memoryDisplay);
        int visibility = calc.getMSymbol();
        if(visibility==1)
            memoryView.setVisibility(View.VISIBLE);
        else
            memoryView.setVisibility(View.INVISIBLE);

    }

    public void exitApp(View v)
    {
        System.exit(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
