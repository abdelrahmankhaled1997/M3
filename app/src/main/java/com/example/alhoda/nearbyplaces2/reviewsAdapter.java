package com.example.alhoda.nearbyplaces2;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class reviewsAdapter extends BaseAdapter {

    Context context;
   private ArrayList<String> ratings1;
    private ArrayList<String> reviews1;
    public reviewsAdapter(Context context, ArrayList<String> ratings, ArrayList<String> reviews) {
        //super(context, R.layout.single_list_app_item, utilsArrayList);

        this.ratings1 = ratings;
        this.reviews1 = reviews;
        Log.d("listsize",String.valueOf(ratings.size()));
        this.context=context;

    }

    @Override
    public int getCount() {
        return ratings1.size();

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.reviewlist_design, parent, false);
           viewHolder.review = (TextView) convertView.findViewById(R.id.review2);


           viewHolder.rating = (RatingBar) convertView.findViewById(R.id.rating2);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.rating.setRating(Float.parseFloat(ratings1.get(position)));
       viewHolder.review.setText(reviews1.get(position).toString());


        return convertView;
    }




    private static class ViewHolder {

       TextView review ;
       RatingBar rating;


    }

}