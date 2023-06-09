package com.group6.donor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DistributionAdapter extends RecyclerView.Adapter<DistributionAdapter.MyViewHolder> implements Filterable {

    List<DistributionClass> distribution, distributionClassFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    DistributionCustomFilter filter;

    public DistributionAdapter(List<DistributionClass> distribution, Context context, RecyclerViewClickListener listener) {
        this.distribution = distribution;
        this.distributionClassFilter = distribution;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_distribution, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mRecipient.setText(distribution.get(position).getRecipient());
        // type in list_item_donor.xml which reveals 2 texts in the list
        holder.mType.setText(distribution.get(position).getQuantity() + " / "
                + distribution.get(position).getDistributionLocation());
        holder.mDistributionDate.setText(distribution.get(position).getDistributionDate());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        Glide.with(context)
                .load(distribution.get(position).getPicture())
                .apply(requestOptions)
                .into(holder.mPicture);

//        final Boolean love = donors.get(position).getLove();

//        if (love){ holder.mLove.setImageResource(R.drawable.likeon);
//        } else { holder.mLove.setImageResource(R.drawable.likeof); }

    }

    @Override
    public int getItemCount() {
        return distribution.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new DistributionCustomFilter((ArrayList<DistributionClass>) distributionClassFilter,this);

        }
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private CircleImageView mPicture;
        private ImageView mLove;
        private TextView mRecipient, mType, mDistributionDate;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mRecipient = itemView.findViewById(R.id.Recipient);
            mType = itemView.findViewById(R.id.type);
//            mLove = itemView.findViewById(R.id.love);
            mDistributionDate = itemView.findViewById(R.id.DistributionDate);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
//            mLove.setOnClickListener(this);
        }

        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.row_container) {
//                mListener.onRowClick(mRowContainer, getAdapterPosition());
//            }
//        }
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
