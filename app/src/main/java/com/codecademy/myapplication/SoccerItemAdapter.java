package com.codecademy.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SoccerItemAdapter extends RecyclerView.Adapter<SoccerItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<SoccerItem> mSoccerItemsData;
    private ArrayList<SoccerItem> mSoccerItemDataAll;
    private Context mContext;
    private int lastPosition=-1;

    SoccerItemAdapter(Context context, ArrayList<SoccerItem> itemList) {
        this.mSoccerItemDataAll=itemList;
        this.mSoccerItemsData=itemList;
        this.mContext=context;
    }

    @Override
    public Filter getFilter() {
        return soccerFiter;
    }
    private Filter soccerFiter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SoccerItem> filteredList=new ArrayList<>();
            FilterResults results=new FilterResults();

            if(charSequence==null || charSequence.length()==0){
                results.count=mSoccerItemDataAll.size();
                results.values=mSoccerItemDataAll;
            }else{
                String filterPattern=charSequence.toString().toLowerCase().trim();
                for(SoccerItem item: mSoccerItemDataAll){
                    if(item.getSoccerName().toLowerCase().contains(filterPattern)|| item.getDetails().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count=filteredList.size();
                results.values=filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mSoccerItemsData=(ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitileText;
        private TextView mDetailsText;
        private TextView mLocationText;
        private ImageView mItemImage;
        private TextView mTeamsText;
        private TextView mDateText;
        public ViewHolder(View itemView) {
            super(itemView);
            mTitileText=itemView.findViewById(R.id.itemTitle);
             mDetailsText=itemView.findViewById(R.id.Details);
             mLocationText=itemView.findViewById(R.id.helyszin);
             mItemImage=itemView.findViewById(R.id.itemImage);
             mTeamsText=itemView.findViewById(R.id.letszam);
             mDateText=itemView.findViewById(R.id.datum);
        }

        public void bindTo(SoccerItem currentItem) {
            mTitileText.setText(currentItem.getSoccerName());
            mDetailsText.setText(currentItem.getDetails());
            mLocationText.setText(currentItem.getLocation());
            mTeamsText.setText(("Csapatok: "+currentItem.getTotalTeams()+"/"+currentItem.getCurrentTeams()));
            mDateText.setText(currentItem.getDate());
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));

    }

    @Override
    public void onBindViewHolder(SoccerItemAdapter.ViewHolder holder, int position) {
        SoccerItem currentItem=mSoccerItemDataAll.get(position);

        holder.bindTo(currentItem);
        if(holder.getAdapterPosition()>lastPosition){
            Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition=holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mSoccerItemsData.size();
    }


}



