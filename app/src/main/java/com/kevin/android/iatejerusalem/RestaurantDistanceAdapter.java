package com.kevin.android.iatejerusalem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class RestaurantDistanceAdapter extends ArrayAdapter<Restaurant> {


    private Context mContext;
    int colorChoice = 1;

    public RestaurantDistanceAdapter(Context context, ArrayList<Restaurant> restaurants) {
        super(context, R.layout.restaurant_distance_list, restaurants);
        mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int colorGrey = mContext.getResources().getColor(R.color.light_grey);
        int colorWhite = mContext.getResources().getColor(R.color.white);
        colorChoice++;

        View listItemView = convertView;

        final Restaurant currentRestaurant = getItem(position);

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_distance_list, parent, false);
        }


        TextView restaurantName = (TextView) listItemView.findViewById(R.id.restaurant_distance_name);
        restaurantName.setText(currentRestaurant.getRestaurantName());

        TextView restaurantPhone = (TextView) listItemView.findViewById(R.id.restaurant_distance_phone_number);
        restaurantPhone.setText(currentRestaurant.getPhoneNumber());

        Button restaurantWebsite = (Button) listItemView.findViewById(R.id.restaurant_distance_website);
        restaurantWebsite.setText(R.string.website1);

        TextView restaurantAddresss = (TextView) listItemView.findViewById(R.id.restaurant_distance_address);
        restaurantAddresss.setText(currentRestaurant.getRestaurantAddress());

        TextView restaurantType = (TextView) listItemView.findViewById(R.id.restaurant_distance_type);
        restaurantType.setText(currentRestaurant.getRestaurantType());

        TextView restaurantDistance = (TextView) listItemView.findViewById(R.id.restaurant_distance);
        NumberFormat formatter = new DecimalFormat("#0.00");
        String distance = formatter.format(currentRestaurant.getRestaurantDistance());
        distance = distance + mContext.getString(R.string.km);
        restaurantDistance.setText(distance);

        Button callButton = (Button) listItemView.findViewById(R.id.distance_call_button);

        Button mapButton = (Button) listItemView.findViewById(R.id.distance_map_button);

        View textViews = listItemView.findViewById(R.id.text_box_distance);
        if (currentRestaurant.getBackgroundColor() == -1) {
            currentRestaurant.setBackgroundColor(colorChoice);
            if (currentRestaurant.getBackgroundColor() % 2 == 0) {
                textViews.setBackgroundColor(colorGrey);
            } else {
                textViews.setBackgroundColor(colorWhite);
            }
        } else if (currentRestaurant.getBackgroundColor() % 2 == 0) {
            textViews.setBackgroundColor(colorGrey);
        } else {
            textViews.setBackgroundColor(colorWhite);
        }

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = currentRestaurant.getPhoneNumber();
                phoneIntent.setData(Uri.parse(mContext.getString(R.string.tel) + phoneNumber));
                mContext.startActivity(phoneIntent);
            }
        });
        restaurantWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = currentRestaurant.getWebsite();
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(url));
                mContext.startActivity(webIntent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = currentRestaurant.getRestaurantAddress();
                Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.google_nav) + address));
                mContext.startActivity(mapsIntent);

            }
        });


        return listItemView;
    }
}
