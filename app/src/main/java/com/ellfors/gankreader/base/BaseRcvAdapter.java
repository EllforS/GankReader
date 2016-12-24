package com.ellfors.gankreader.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    protected static final int TYPE_HEADER = 11;
    protected static final int TYPE_ITEM = 12;
    protected static final int TYPE_FOOTER = 13;

    private boolean isHasHeader = false;
    private boolean isHasFooter = false;

    public BaseRcvAdapter(boolean isHasHeader, boolean isHasFooter) {
        this.isHasHeader = isHasHeader;
        this.isHasFooter = isHasFooter;
    }

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int getItemSize();

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (isHasHeader) {
            onBindHolder(holder, position - 1);
        } else {
            onBindHolder(holder, position);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isHasHeader)
                        onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition() - 1);
                    else
                        onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (isHasHeader)
                        onItemLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition() - 1);
                    else
                        onItemLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getItemSize();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHasHeader) {
            if (isHasFooter) {
                if (position == 0) {
                    return TYPE_HEADER;
                } else if (position + 1 == getItemSize()) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_ITEM;
                }
            } else {
                if (position == 0) {
                    return TYPE_HEADER;
                } else {
                    return TYPE_ITEM;
                }
            }
        } else {
            if (isHasFooter) {
                if (position == getItemSize() - 1) {
                    return TYPE_FOOTER;
                } else {
                    return TYPE_ITEM;
                }
            } else {
                return TYPE_ITEM;
            }
        }
    }

    /**
     * 为GridViewLayout FootView设置占位为一行
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof GridLayoutManager) {

            final GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //footview
                    if (getItemViewType(position) % TYPE_FOOTER == 0 && isHasFooter) {
                        return gridManager.getSpanCount();
                    }
                    //headview
                    else if (position == 0 && isHasHeader) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    /**
     * 为StaggeredGridLayout FootView设置占位为一行
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        //HeaderView
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
        //FooterView
        else if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() % TYPE_FOOTER == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }
}
