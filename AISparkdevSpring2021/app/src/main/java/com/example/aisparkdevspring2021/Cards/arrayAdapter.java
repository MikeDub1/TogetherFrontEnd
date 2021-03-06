package com.example.aisparkdevspring2021.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aisparkdevspring2021.R;

import java.util.List;

public class  arrayAdapter extends ArrayAdapter<cards> {

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        cards card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView nameBack = (TextView) convertView.findViewById(R.id.name_back);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageView imageBack = (ImageView) convertView.findViewById(R.id.image_back);


        name.setText(card_item.getName());
        switch(card_item.getProfileImageUrl()){
            case "default":
                Glide.with(getContext()).load(R.mipmap.ic_launcher).into(image);
                Glide.with(getContext()).load(R.mipmap.ic_launcher).into(imageBack);
                break;
            default:
                Glide.with(getContext()).load(card_item.getProfileImageUrl()).into(image);
                Glide.with(getContext()).load(card_item.getProfileImageUrl()).into(imageBack);
                break;

        }


       // image.setImageResource(R.mipmap.ic_launcher);
        return convertView;

    }
}