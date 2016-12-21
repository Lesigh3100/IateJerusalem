package com.kevin.android.iatejerusalem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

    private Context mContext;
    int colorChoice = 1;
    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        super(context, R.layout.restaurantlist, restaurants);
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
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.restaurantlist, parent, false);
        }

        ImageView image = (ImageView) listItemView.findViewById(R.id.restaurantImage);

        if (currentRestaurant.hasImage()) {
            image.setImageResource(currentRestaurant.getImageId());
        } else {
            image.setVisibility(View.GONE);
        }
        TextView restaurantName = (TextView) listItemView.findViewById(R.id.restaurant_name);
        restaurantName.setText(currentRestaurant.getRestaurantName());

        TextView restaurantPhone = (TextView) listItemView.findViewById(R.id.restaurant_phone_number);
        restaurantPhone.setText(currentRestaurant.getPhoneNumber());

        TextView restaurantHours = (TextView) listItemView.findViewById(R.id.restaurant_hours);
        restaurantHours.setText(currentRestaurant.getHours());

        TextView restaurantWebsite = (TextView) listItemView.findViewById(R.id.restaurant_website);
        restaurantWebsite.setText(R.string.website1);

        TextView restaurantAddresss = (TextView) listItemView.findViewById(R.id.restaurant_address);
        restaurantAddresss.setText(currentRestaurant.getRestaurantAddress());

        TextView restaurantType = (TextView) listItemView.findViewById(R.id.restaurant_type);
        restaurantType.setText(currentRestaurant.getRestaurantType());

        ImageView typeTab = (ImageView) listItemView.findViewById(R.id.restaurant_type_image);
        if (currentRestaurant.getRestaurantType().equals(getContext().getString(R.string.fine_dining))) {
            typeTab.setImageResource(R.drawable.type_rectangle_fine_dining);
        }
        else if (currentRestaurant.getRestaurantType().equals(getContext().getString(R.string.middle_eastern))){
            typeTab.setImageResource(R.drawable.type_rectangle_middle_eastern);
        }
        else if (currentRestaurant.getRestaurantType().equals(getContext().getString(R.string.cafe))){
            typeTab.setImageResource(R.drawable.type_rectangle_cafe);
        }
        else {
            typeTab.setImageResource(R.drawable.type_rectangle_other);
        }

        View textViews = listItemView.findViewById(R.id.text_box);
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

        restaurantPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = currentRestaurant.getPhoneNumber();
                phoneIntent.setData(Uri.parse(mContext.getString(R.string.tel_no) + phoneNumber));
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
        restaurantAddresss.setOnClickListener(new View.OnClickListener() {
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
