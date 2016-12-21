package com.kevin.android.iatejerusalem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView choiceTab = (TextView) findViewById(R.id.tab_type);
        choiceTab.setOnClickListener(onClickListener);
        TextView distanceTab = (TextView) findViewById(R.id.tab_distance);
        distanceTab.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tab_type:
                    Intent typeActivity = new Intent(MainActivity.this, FoodChoice.class);
                    startActivity(typeActivity);
                    break;
                case R.id.tab_distance:
                    Intent distanceActivity = new Intent(MainActivity.this, RestaurantDistanceList.class);
                    startActivity(distanceActivity);
                    break;
            }
        }
    };

}




