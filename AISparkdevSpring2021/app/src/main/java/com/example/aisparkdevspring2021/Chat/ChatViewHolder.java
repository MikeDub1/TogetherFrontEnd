package com.example.aisparkdevspring2021.Chat;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.example.aisparkdevspring2021.R;


public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    
    public ChatViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
    }
}
