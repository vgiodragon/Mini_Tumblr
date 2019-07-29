package com.giotec.mini_tumblr.Fragments.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.giotec.mini_tumblr.Models.Post_Item;
import com.giotec.mini_tumblr.R;

import java.io.InputStream;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PostAdapterViewHolder>{

    private List<Post_Item> mposts;
    private Context context;
    private String TAG="GIODEBUG_ADAPTER";

    public MyAdapter(Context context, List<Post_Item> mposts) {
        this.context = context;
        this.mposts = mposts;
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

        Post_Item mpost = mposts.get(position);
        String type = mpost.getType();
        holder.tv_BlogName.setText(mpost.getBlog_item().getName());
        Glide.with(context)
                .load(mpost.getBlog_item().getAvatar()).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv_avatar);
        holder.tv_body.setText(mpost.getBody());
        holder.tv_tags.setText(mpost.getTags());
        if(mpost.isLiked()) holder.iv_like.setImageResource(R.drawable.ic_favorite_red_24dp);

        if (type.equals("text")){
            holder.iv_photo.setVisibility(View.GONE);
            holder.tv_title.setText(mpost.getTitle());
        }else if(type.equals("photo")){
            holder.tv_title.setVisibility(View.GONE);
            //new DownloadImageTask(holder.iv_photo).execute(mpost.getUrlPhoto());
            Glide.with(context)
                    .load(mpost.getUrlPhoto())//.thumbnail(0.5f)
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

        public PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_BlogName = itemView.findViewById(R.id.tv_BlogName);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_body = itemView.findViewById(R.id.tv_body);
            tv_tags = itemView.findViewById(R.id.tv_tags);
            iv_like = itemView.findViewById(R.id.iv_like);
        }
    }

    @Override
    public long getItemId(int position) {

        Post_Item mpost = mposts.get(position);
        return mpost.getId();
    }
}
