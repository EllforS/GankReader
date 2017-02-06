package com.ellfors.gankreader.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.base.BaseViewHolder;
import com.ellfors.gankreader.model.StudyModel;

import java.util.List;

public class LikeAdapter extends BaseRcvAdapter
{
    private Context context;
    private List<StudyModel> list;

    public LikeAdapter(Context context, List<StudyModel> list)
    {
        super(false, false);
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_study,parent,false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ItemViewHolder)
        {
            if(!TextUtils.isEmpty(list.get(position).getDesc()))
                ((ItemViewHolder) holder).tv_title.setText(list.get(position).getDesc());
            if(!TextUtils.isEmpty(list.get(position).getPublishedAt()) && list.get(position).getPublishedAt().length() > 10)
                ((ItemViewHolder) holder).tv_time.setText(list.get(position).getPublishedAt().substring(0,10));
            if(!TextUtils.isEmpty(list.get(position).getWho()))
                ((ItemViewHolder) holder).tv_author.setText(list.get(position).getWho());
            ((ItemViewHolder) holder).iv_logo.setBackgroundResource(R.drawable.drawer_like);
        }
    }

    @Override
    public int getItemSize()
    {
        return list == null || list.size() == 0 ? 0 : list.size();
    }

    private class ItemViewHolder extends BaseViewHolder
    {
        ImageView iv_logo;
        TextView tv_title;
        TextView tv_time;
        TextView tv_author;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            iv_logo = $(R.id.item_study_logo);
            tv_title = $(R.id.item_study_title);
            tv_time = $(R.id.item_study_time);
            tv_author = $(R.id.item_study_author);
        }
    }
}
