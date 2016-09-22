package com.lo.sisyphuser.fish.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.main.foodtype.FoodTypeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, FoodTypeActivity.class);
                startActivity(intent);
                finish();
            }
        },0);
    }
}
