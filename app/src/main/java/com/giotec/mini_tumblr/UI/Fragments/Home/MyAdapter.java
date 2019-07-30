package com.giotec.mini_tumblr.UI.Fragments.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.giotec.mini_tumblr.Models.PostItem;
import com.giotec.mini_tumblr.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PostAdapterViewHolder>{

    private List<PostItem> mposts;
    private Context context;
    private String TAG="GIODEBUG_ADAPTER";
    Home.OnFragmentInteractionListener mListener;

    public MyAdapter(Context context, List<PostItem> mposts, Home.OnFragmentInteractionListener mListener) {
        this.context = context;
        this.mposts = mposts;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new PostAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {

        PostItem mpost = mposts.get(position);
        String type = mpost.getType();
        holder.tv_BlogName.setText(mpost.getBlog_item().getName());
        Glide.with(context)
                .load(mpost.getBlog_item().getAvatar()).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_avatar);
        holder.tv_body.setText(mpost.getBody());
        holder.tv_tags.setText(mpost.getTags());
        mListener.setLike(holder.iv_like,mpost.isLiked());

        if (type.equals("text")){
            holder.iv_photo.setVisibility(View.GONE);
            holder.tv_title.setText(mpost.getTitle());
        }else if(type.equals("photo")){
            holder.tv_title.setVisibility(View.GONE);
            Glide.with(context)
                    .load(mpost.getUrlPhoto())
                    .fitCenter().dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(holder.iv_photo);
        }else
            Log.d(TAG,"ELSE "+ type);
    }

    @Override
    public int getItemCount() {
        return mposts.size();
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_avatar;
        public TextView tv_BlogName;
        public ImageView iv_photo;
        public TextView tv_title;
        public TextView tv_body;
        public TextView tv_tags;
        public ImageView iv_like;
        public ImageView iv_cross;

        public PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_BlogName = itemView.findViewById(R.id.tv_BlogName);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_body = itemView.findViewById(R.id.tv_body);
            tv_tags = itemView.findViewById(R.id.tv_tags);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mposts.get(getAdapterPosition())
                            .setLiked(!mposts.get(getAdapterPosition()).isLiked());
                    mListener.setLike(iv_like,mposts.get(getAdapterPosition()).isLiked());
                }
            });
            iv_cross = itemView.findViewById(R.id.iv_cros);
            iv_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.Eliminate(v,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        PostItem mpost = mposts.get(position);
        return mpost.getId();
    }


}
