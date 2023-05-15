package com.group6.donor;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> implements Filterable {

    List<InventoryClass> inventory, inventoryClassFilter;
    private InventoryActivity context;
    private RecyclerViewClickListener mListener;
    InventoryCustomFilter filter;

    public InventoryAdapter(List<InventoryClass> inventory, InventoryActivity context, RecyclerViewClickListener listener) {
        this.inventory = inventory;
        this.inventoryClassFilter = inventory;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventory, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mItemName.setText(inventory.get(position).getItemName());
        // type in list_item_inventory.xml which reveals 2 texts in the list
        holder.mType.setText(inventory.get(position).getQuantity() + " / "
                + inventory.get(position).getDescription());
        holder.mExpiration.setText(inventory.get(position).getExpiration());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        Glide.with(context)
                .load(inventory.get(position).getPicture())
                .apply(requestOptions)
                .into(holder.mPicture);

//        final Boolean love = inventory.get(position).getLove();

//        if (love){
//            holder.mLove.setImageResource(R.drawable.likeon);
//        } else {
//            holder.mLove.setImageResource(R.drawable.likeof);
//        }

    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new InventoryCustomFilter((ArrayList<InventoryClass>) inventoryClassFilter,this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private CircleImageView mPicture;
//        private ImageView mLove;
        private TextView mItemName, mType, mExpiration;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mItemName = itemView.findViewById(R.id.ItemName);
            mType = itemView.findViewById(R.id.type);
//            mLove = itemView.findViewById(R.id.love);
            mExpiration = itemView.findViewById(R.id.Expiration);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
//            mLove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
//                case R.id.love:
//                    mListener.onLoveClick(mLove, getAdapterPosition());
//                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
//        void onLoveClick(View view, int position);
    }

}
