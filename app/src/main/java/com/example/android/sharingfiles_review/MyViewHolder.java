package com.example.android.sharingfiles_review;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CardView view;
    SquareImageView image;
    TextView text;

    public MyViewHolder(View itemView) {
        super(itemView);
        view =(CardView)itemView;
        image = itemView.findViewById(R.id.image);
        text = itemView.findViewById(R.id.text);
    }


}
