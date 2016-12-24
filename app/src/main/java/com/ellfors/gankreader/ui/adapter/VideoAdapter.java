package com.ellfors.gankreader.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemSize()
    {
        return list.size() == 0 || list == null ? 0 : list.size();
    }

    private class ItemViewHolder extends BaseViewHolder
    {


        public ItemViewHolder(View itemView)
        {
            super(itemView);
        }
    }

}
