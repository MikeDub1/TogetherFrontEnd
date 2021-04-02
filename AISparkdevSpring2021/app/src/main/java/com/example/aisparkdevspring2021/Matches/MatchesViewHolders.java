package com.example.aisparkdevspring2021.Matches;

import android.view.View;
import android.widget.TextView;
import com.example.aisparkdevspring2021.R;


import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId;


    public MatchesViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchId=(TextView) itemView.findViewById(R.id.Matchid);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle a  = new Bundle();
        a.pushString("matchId",mMatchId.getText().toString());
        intent.putExtras(a);
        view.getContext().startActivity(intent);
        
    }
}
