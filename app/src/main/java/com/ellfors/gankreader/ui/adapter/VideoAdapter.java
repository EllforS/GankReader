package com.ellfors.gankreader.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.base.BaseViewHolder;
import com.ellfors.gankreader.model.VideoModel;

import java.util.List;

public class VideoAdapter extends BaseRcvAdapter
{
    private Context context;
    private List<VideoModel> list;

    public VideoAdapter(Context context, List<VideoModel> list)
    {
        super(false, false);
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_video,parent,false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ItemViewHolder)
        {
            ((ItemViewHolder) holder).tv_title.setText(list.get(position).getDesc());
            ((ItemViewHolder) holder).tv_author.setText(list.get(position).getWho());
            if(list.get(position).getPublishedAt().length() > 10)
            {
                ((ItemViewHolder) holder).tv_time.setText(list.get(position).getPublishedAt().substring(0,10));
            }
        }
    }

    @Override
    public int getItemSize()
    {
        return list.size() == 0 || list == null ? 0 : list.size();
    }

    private class ItemViewHolder extends BaseViewHolder
    {
        ImageView iv_image;
        TextView tv_title;
        TextView tv_author;
        TextView tv_time;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            iv_image = $(R.id.item_video_img);
            tv_title = $(R.id.item_video_title);
            tv_author = $(R.id.item_video_author);
            tv_time = $(R.id.item_video_time);
        }
    }

}
