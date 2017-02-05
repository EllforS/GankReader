package com.ellfors.gankreader.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.base.BaseViewHolder;
import com.ellfors.gankreader.model.FuliModel;
import com.ellfors.gankreader.utils.GlideLoadUtils;

import java.util.List;

public class FuliAdapter extends BaseRcvAdapter
{
    private Context context;
    private List<FuliModel> list;

    public FuliAdapter(Context context, List<FuliModel> list)
    {
        super(false, true);
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType)
    {
        if(viewType == TYPE_ITEM)
        {
            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_fuli,parent,false));
        }
        else if(viewType == TYPE_FOOTER)
        {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_footerview,parent,false));
        }
        return null;
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof ItemViewHolder)
        {
            GlideLoadUtils.loadImage(context,list.get(position).getUrl(),((ItemViewHolder) holder).iv_item);
        }
    }

    @Override
    public int getItemSize()
    {
        return list.size() == 0 || list == null ? 0 : list.size() + 1;
    }

    private class ItemViewHolder extends BaseViewHolder
    {
        ImageView iv_item;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            iv_item = $(R.id.item_fuli_img);
        }
    }

    private class FooterViewHolder extends BaseViewHolder
    {
        public FooterViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
