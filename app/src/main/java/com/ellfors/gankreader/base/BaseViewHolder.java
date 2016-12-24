package com.ellfors.gankreader.base;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder
{
    private View mItemView;

    public BaseViewHolder(View itemView)
    {
        super(itemView);
        this.mItemView = itemView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int id)
    {
        return (T) mItemView.findViewById(id);
    }
}
