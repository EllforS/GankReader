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
import com.ellfors.gankreader.ui.fragment.MainFragment;

import java.util.List;

public class StudyAdapter extends BaseRcvAdapter
{
    private String tag;
    private Context context;
    private List<StudyModel> list;

    public StudyAdapter(String tag, Context context, List<StudyModel> list)
    {
        super(false, true);
        this.tag = tag;
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_ITEM)
        {
            return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_study, parent, false));
        }
        else if (viewType == TYPE_FOOTER)
        {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_footerview, parent, false));
        }
        return null;
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ItemViewHolder)
        {
            if (!TextUtils.isEmpty(list.get(position).getDesc()))
                ((ItemViewHolder) holder).tv_title.setText(list.get(position).getDesc());
            if (!TextUtils.isEmpty(list.get(position).getPublishedAt()) && list.get(position).getPublishedAt().length() > 10)
                ((ItemViewHolder) holder).tv_time.setText(list.get(position).getPublishedAt().substring(0, 10));
            if (!TextUtils.isEmpty(list.get(position).getWho()))
                ((ItemViewHolder) holder).tv_author.setText(list.get(position).getWho());
            switch (tag)
            {
                case MainFragment.TAG_ANDROID:
                    ((ItemViewHolder) holder).iv_logo.setBackgroundResource(R.drawable.icon_android);
                    break;
                case MainFragment.TAG_IOS:
                    ((ItemViewHolder) holder).iv_logo.setBackgroundResource(R.drawable.icon_ios);
                    break;
                case MainFragment.TAG_WEB:
                    ((ItemViewHolder) holder).iv_logo.setBackgroundResource(R.drawable.icon_web);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemSize()
    {
        return list == null || list.size() == 0 ? 0 : list.size() + 1;
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

    private class FooterViewHolder extends BaseViewHolder
    {
        public FooterViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
