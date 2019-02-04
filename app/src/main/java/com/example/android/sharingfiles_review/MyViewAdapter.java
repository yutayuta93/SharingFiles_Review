package com.example.android.sharingfiles_review;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    //インナーインターフェース(オブザーバー)
    OnItemSelectedListener listener;
    public interface OnItemSelectedListener{
        void onItemSelected(View v,ListItem item);
    }

    //データソース
    private ArrayList<ListItem> data;

    //コンストラクタ
    public MyViewAdapter(ArrayList<ListItem> data, Activity activity){
        this.data = data;
        listener = (OnItemSelectedListener)activity;

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //リスト項目のレイアウト(list_item)を表すビューを生成
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,
                false);
        //生成したレイアウトからビューホルダーを生成し、返す
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int mPosition = position;
        holder.image.setImageBitmap(data.get(position).getImage());
        holder.text.setText(data.get(position).getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(v,data.get(mPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
