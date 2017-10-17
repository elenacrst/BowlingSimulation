package com.example.elena.bowlingsimulation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elena.bowlingsimulation.R;

import java.util.List;

/**
 * Created by Elena on 9/4/2017.
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>{

    private List<Integer> data;

    public void setData(List<Integer> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);

        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        holder.textView.setText(data.get(position)+"");
    }

    @Override
    public int getItemCount() {
        if(data==null || data.size()==0)
            return 0;
        return data.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ScoreViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
        }
    }
}
