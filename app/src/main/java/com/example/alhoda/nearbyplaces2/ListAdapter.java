package com.example.alhoda.nearbyplaces2;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {

    Context context;
    private  ArrayList<String>  values;
    private  ArrayList<String>  numbers;
    private  ArrayList<String>  image;
    private ArrayList<Bitmap>  icons ;
    private  ArrayList<String>  phones;
    private ArrayList<String>  distances ;

    public ListAdapter(Context context, ArrayList<String>  values, ArrayList<String>  numbers, ArrayList<String> images,ArrayList<String> phones,ArrayList<String> distances){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.numbers = numbers;
        this.image = images;
        this.phones = phones;
        this.distances = distances;
        icons = new ArrayList<Bitmap>();
        convertToBitMap(images);
    }

    @Override
    public int getCount() {
        return values.size();

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
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.aNametxt);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.aVersiontxt);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconIV);
            viewHolder.ratingBar =(RatingBar)convertView.findViewById(R.id.ratingbar);
            viewHolder.phoneicon =(ImageView) convertView.findViewById(R.id.phoneIcon);
            viewHolder.phoneNo = (TextView) convertView.findViewById(R.id.phoneNo);
            viewHolder.caricon =(ImageView) convertView.findViewById(R.id.carIcon);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtName.setText(values.get(position));
        viewHolder.txtVersion.setText("Rating: "+numbers.get(position)+"/5");
        viewHolder.icon.setImageBitmap(icons.get(position));
        viewHolder.ratingBar.setRating(Float.parseFloat(numbers.get(position)));
        viewHolder.phoneicon.setImageResource(R.drawable.ic_local_phone_black_24dp );
        viewHolder.phoneNo.setText("0"+phones.get(position));
        viewHolder.caricon.setImageResource(R.drawable.ic_directions_car_black_24dp );
        viewHolder.distance.setText(Integer.toString((int)(Double.parseDouble(distances.get(position))))+ " M");

        return convertView;
    }



    public ArrayList<Bitmap> convertToBitMap(ArrayList<String>  urls){
        int i;

        for (i=0; i<urls.size();i++ ){
            byte[] decodedString = Base64.decode(urls.get(i), Base64.DEFAULT); 
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            icons.add(decodedByte);
        }
        return icons;
    }


    private static class ViewHolder {

        TextView txtName;
        TextView phoneNo;
        TextView txtVersion;
        TextView distance;
        ImageView icon;
        RatingBar ratingBar;
       ImageView phoneicon;
        ImageView caricon;

    }

}