package com.kevin.android.iatejerusalem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FoodChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_choice);

        TextView israeliTab = (TextView) findViewById(R.id.tab_israeli);
        israeliTab.setOnClickListener(onClickListener);
        TextView fineDiningTab = (TextView) findViewById(R.id.tab_fine_dining);
        fineDiningTab.setOnClickListener(onClickListener);
        TextView cafeTab = (TextView) findViewById(R.id.tab_cafe);
        cafeTab.setOnClickListener(onClickListener);
        TextView otherTab = (TextView) findViewById(R.id.tab_other);
        otherTab.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tab_israeli:
                    Intent middleEastActivity = new Intent(FoodChoice.this, RestaurantList.class);
                    middleEastActivity.putExtra(getResources().getString(R.string.type1), 2);
                    startActivity(middleEastActivity);
                    break;
                case R.id.tab_cafe:
                    Intent cafeActivity = new Intent(FoodChoice.this, RestaurantList.class);
                    cafeActivity.putExtra(getResources().getString(R.string.type1), 3);
                    startActivity(cafeActivity);
                    break;
                case R.id.tab_fine_dining:
                    Intent fineDiningActivity = new Intent(FoodChoice.this, RestaurantList.class);
                    fineDiningActivity.putExtra(getResources().getString(R.string.type1), 1);
                    startActivity(fineDiningActivity);
                    break;
                case R.id.tab_other:
                    Intent otherActivity = new Intent(FoodChoice.this, RestaurantList.class);
                    otherActivity.putExtra(getResources().getString(R.string.type1), 5);
                    startActivity(otherActivity);
                    break;

            }
        }
    };
}
