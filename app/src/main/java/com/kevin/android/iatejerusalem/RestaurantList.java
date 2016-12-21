package com.kevin.android.iatejerusalem;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class RestaurantList extends AppCompatActivity {
    int restaurantType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_listview);
        int restaurantChoice = getIntent().getIntExtra(getString(R.string.type_1), restaurantType);
        restaurantType = restaurantChoice;
        createList(restaurantChoice);
        createButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.param), restaurantType);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restaurantType = savedInstanceState.getInt(getString(R.string.param));
        createList(restaurantType);
    }

    public void createButtons() {
        int id;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton allButton = (RadioButton) findViewById(R.id.radio_button_all);
        RadioButton fineDiningButton = (RadioButton) findViewById(R.id.radio_button_fine_dining);
        RadioButton middleEastButton = (RadioButton) findViewById(R.id.radio_button_middle_east);
        RadioButton cafeButton = (RadioButton) findViewById(R.id.radio_button_cafe);
        RadioButton otherButton = (RadioButton) findViewById(R.id.radio_button_other);

        allButton.setOnClickListener(onClickListener);
        fineDiningButton.setOnClickListener(onClickListener);
        middleEastButton.setOnClickListener(onClickListener);
        cafeButton.setOnClickListener(onClickListener);
        otherButton.setOnClickListener(onClickListener);

        switch (restaurantType) {
            case 1:
                id = R.id.radio_button_fine_dining;
                break;
            case 2:
                id = R.id.radio_button_middle_east;
                break;
            case 3:
                id = R.id.radio_button_cafe;
                break;
            case 5:
                id = R.id.radio_button_other;
                break;
            default:
                id = R.id.radio_button_all;
                break;
        }
        radioGroup.check(id);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radio_button_fine_dining:
                    createList(1);
                    restaurantType = 1;
                    break;
                case R.id.radio_button_middle_east:
                    createList(2);
                    restaurantType = 2;
                    break;
                case R.id.radio_button_cafe:
                    createList(3);
                    restaurantType = 3;
                    break;
                case R.id.radio_button_other:
                    createList(5);
                    restaurantType = 5;
                    break;
                default:
                    createList(0);
                    break;
            }

        }
    };

    public void createList(int type) {
        final ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(R.drawable.grandcafe, getString(R.string.a1), getString(R.string.a2), getString(R.string.a3), getString(R.string.a4), getString(R.string.a5), getString(R.string.a6)));
        restaurantList.add(new Restaurant(R.drawable.eucalyptus, getString(R.string.b1), getString(R.string.b2), getString(R.string.b3), getString(R.string.b4), getString(R.string.b5), getString(R.string.b6)));
        restaurantList.add(new Restaurant(R.drawable.station9, getString(R.string.c1), getString(R.string.c2), getString(R.string.c3), getString(R.string.c4), getString(R.string.c5), getString(R.string.c6)));
        restaurantList.add(new Restaurant(R.drawable.pinati, getString(R.string.d1), getString(R.string.d2), getString(R.string.d3), getString(R.string.d4), getString(R.string.d5), getString(R.string.d6)));
        restaurantList.add(new Restaurant(R.drawable.tmol, getString(R.string.e1), getString(R.string.e2), getString(R.string.e3), getString(R.string.e4), getString(R.string.e5), getString(R.string.e6)));
        restaurantList.add(new Restaurant(R.drawable.sophia, getString(R.string.f1), getString(R.string.f2), getString(R.string.f3), getString(R.string.f4), getString(R.string.f5), getString(R.string.f6)));
        restaurantList.add(new Restaurant(R.drawable.waldorf, getString(R.string.g1), getString(R.string.g2), getString(R.string.g3), getString(R.string.g4), getString(R.string.g5), getString(R.string.g6)));
        restaurantList.add(new Restaurant(R.drawable.sheyane, getString(R.string.h1), getString(R.string.h2), getString(R.string.h3), getString(R.string.h4), getString(R.string.h5), getString(R.string.h6)));
        restaurantList.add(new Restaurant(R.drawable.eighteen, getString(R.string.i1), getString(R.string.i2), getString(R.string.i3), getString(R.string.i4), getString(R.string.i5), getString(R.string.i6)));
        restaurantList.add(new Restaurant(R.drawable.piccolino, getString(R.string.j1), getString(R.string.j2), getString(R.string.j3), getString(R.string.j4), getString(R.string.j5), getString(R.string.j6)));
        restaurantList.add(new Restaurant(R.drawable.redheifer, getString(R.string.k1), getString(R.string.k2), getString(R.string.k3), getString(R.string.k4), getString(R.string.k5), getString(R.string.k6)));
        restaurantList.add(new Restaurant(R.drawable.villagegreen, getString(R.string.l1), getString(R.string.l2), getString(R.string.l3), getString(R.string.l4), getString(R.string.l5), getString(R.string.l6)));
        restaurantList.add(new Restaurant(R.drawable.azura, getString(R.string.m1), getString(R.string.m2), getString(R.string.m3), getString(R.string.m4), getString(R.string.m5), getString(R.string.m6)));
        restaurantList.add(new Restaurant(R.drawable.darna, getString(R.string.n1), getString(R.string.n2), getString(R.string.n3), getString(R.string.n4), getString(R.string.n5), getString(R.string.n6)));
        restaurantList.add(new Restaurant(R.drawable.caffit, getString(R.string.o1), getString(R.string.o2), getString(R.string.o3), getString(R.string.o4), getString(R.string.o5), getString(R.string.o6)));
        restaurantList.add(new Restaurant(R.drawable.angelica, getString(R.string.p1), getString(R.string.p2), getString(R.string.p3), getString(R.string.p4), getString(R.string.p5), getString(R.string.p6)));
        restaurantList.add(new Restaurant(R.drawable.morgenscafe, getString(R.string.q1), getString(R.string.q2), getString(R.string.q3), getString(R.string.q4), getString(R.string.q5), getString(R.string.q6)));
        restaurantList.add(new Restaurant(R.drawable.laboca, getString(R.string.r1), getString(R.string.r2), getString(R.string.r3), getString(R.string.r4), getString(R.string.r5), getString(R.string.r6)));
        restaurantList.add(new Restaurant(R.drawable.gabriel, getString(R.string.s1), getString(R.string.s2), getString(R.string.s3), getString(R.string.s4), getString(R.string.s5), getString(R.string.s6)));
        restaurantList.add(new Restaurant(R.drawable.papagio, getString(R.string.t1), getString(R.string.t2), getString(R.string.t3), getString(R.string.t4), getString(R.string.t5), getString(R.string.t6)));
        restaurantList.add(new Restaurant(R.drawable.ima, getString(R.string.u1), getString(R.string.u2), getString(R.string.u3), getString(R.string.u4), getString(R.string.u5), getString(R.string.u6)));
        restaurantList.add(new Restaurant(R.drawable.tommys, getString(R.string.v1), getString(R.string.v2), getString(R.string.v3), getString(R.string.v4), getString(R.string.v5), getString(R.string.v6)));
        restaurantList.add(new Restaurant(R.drawable.bensira, getString(R.string.w1), getString(R.string.w2), getString(R.string.w3), getString(R.string.w4), getString(R.string.w5), getString(R.string.w6)));
        restaurantList.add(new Restaurant(R.drawable.happyfish, getString(R.string.x1), getString(R.string.x2), getString(R.string.x3), getString(R.string.x4), getString(R.string.x5), getString(R.string.x6)));
        restaurantList.add(new Restaurant(R.drawable.bardak, getString(R.string.y1), getString(R.string.y2), getString(R.string.y3), getString(R.string.y4), getString(R.string.y5), getString(R.string.y6)));
        restaurantList.add(new Restaurant(R.drawable.mamillarooftop, getString(R.string.z1), getString(R.string.z2), getString(R.string.z3), getString(R.string.z4), getString(R.string.z5), getString(R.string.z6)));
        restaurantList.add(new Restaurant(R.drawable.scala, getString(R.string.aa1), getString(R.string.aa2), getString(R.string.aa3), getString(R.string.aa4), getString(R.string.aa5), getString(R.string.aa6)));
        restaurantList.add(new Restaurant(R.drawable.manoubashouk, getString(R.string.ab1), getString(R.string.ab2), getString(R.string.ab3), getString(R.string.ab4), getString(R.string.ab5), getString(R.string.ab6)));
        restaurantList.add(new Restaurant(R.drawable.medita, getString(R.string.ac1), getString(R.string.ac2), getString(R.string.ac3), getString(R.string.ac4), getString(R.string.ac5), getString(R.string.ac6)));
        restaurantList.add(new Restaurant(R.drawable.fortuna, getString(R.string.ad1), getString(R.string.ad2), getString(R.string.ad3), getString(R.string.ad4), getString(R.string.ad5), getString(R.string.ad6)));
        restaurantList.add(new Restaurant(R.drawable.luciana, getString(R.string.ae1), getString(R.string.ae2), getString(R.string.ae3), getString(R.string.ae4), getString(R.string.ae5), getString(R.string.ae6)));
        restaurantList.add(new Restaurant(R.drawable.agasvtapuah, getString(R.string.af1), getString(R.string.af2), getString(R.string.af3), getString(R.string.af4), getString(R.string.af5), getString(R.string.af6)));


        ArrayList<Restaurant> parameterList = new ArrayList<>();
        if (type == 0) {
            parameterList = restaurantList;
        } else {
            for (int i = 0; i < restaurantList.size(); i++) {
                Restaurant currentRestaurant = restaurantList.get(i);
                switch (type) {
                    case 1:
                        if (currentRestaurant.getRestaurantType().equals(getString(R.string.fine_dining))) {
                            parameterList.add(currentRestaurant);
                        }
                        break;
                    case 2:
                        if (currentRestaurant.getRestaurantType().equals(getString(R.string.middle_eastern))) {
                            parameterList.add(currentRestaurant);
                        }
                        break;
                    case 3:
                        if (currentRestaurant.getRestaurantType().equals(getString(R.string.cafe))) {
                            parameterList.add(currentRestaurant);
                        }
                        break;
                    case 5:
                        if (currentRestaurant.getRestaurantType().equals(getString(R.string.fine_dining)) || currentRestaurant.getRestaurantType().equals(getString(R.string.middle_eastern)) || currentRestaurant.getRestaurantType().equals(getString(R.string.cafe))) {
                            break;
                        } else {
                            parameterList.add(currentRestaurant);
                        }
                        break;
                    default:
                        parameterList.add(currentRestaurant);

                }

            }


        }
        RestaurantAdapter adapter = new RestaurantAdapter(this, parameterList);
        ListView listView = (ListView) findViewById(R.id.restaurant_listview);
        listView.setAdapter(adapter);
    }


}
