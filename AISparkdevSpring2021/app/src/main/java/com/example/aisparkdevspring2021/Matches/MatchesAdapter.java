package com.example.aisparkdevspring2021.Matches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aisparkdevspring2021.R;

import java.util.List;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolders> {
    private List<MatchesObject> matchesList;
    private Context context;

    public MatchesAdapter(List<MatchesObject>matchesList,Context context){
        this.matchesList=matchesList;
        this.context =context;

    }

    @Override
        public MatchesViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null,false );
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutView.setLayoutParams(lp);
        MatchesViewHolders rcv = new MatchesViewHolders((LayoutView));

    return rcv;
    }
    @Override
    public void onBindViewHolder(MatchesViewHolders holder, int position){
        holder.mMatchId.setText(matchesList.get(position).getUserID());

    }
    @Override
    public int getItemCount(){
        return 0;
    }
    }
