package com.giotec.mini_tumblr.Fragments.Home;

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
import com.giotec.mini_tumblr.Models.Blog_item;
import com.giotec.mini_tumblr.R;
import com.giotec.mini_tumblr.Utils.Connections;
import com.giotec.mini_tumblr.Utils.Utils;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.PhotoSize;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PostAdapterViewHolder>{

    private List<Post> mposts;
    private Context context;
    private String TAG="GIODEBUG_ADAPTER";

    public MyAdapter(Context context, List<Post> mposts) {
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

        Post mpost = mposts.get(position);

        Blog_item mblog = Utils.getBlogbyName(mpost.getBlogName());
        holder.tv_BlogName.setText(mblog.getName());
        //ImageView avatar = holder.iv_avatar;

        Glide.with(context)
                .load(mblog.getAvatar())
                .into(holder.iv_avatar);
        String type = mpost.getType().getValue();
        holder.tv_tags.setText(Utils.cleanTags(mpost.getTags()));
        //Log.d("GIODEBUG_ADAPTER","type "+ type);
        if (type.equals("text")){
            holder.iv_photo.setVisibility(View.GONE);
            TextPost textPost = (TextPost) mposts.get(position);
            holder.tv_title.setText(textPost.getTitle());
            holder.tv_body.setText(textPost.getBody());
        }else if(type.equals("photo")){
            holder.tv_title.setVisibility(View.GONE);
            PhotoPost photoPost = (PhotoPost) mposts.get(position);
            String url="";
            for (Photo photo: photoPost.getPhotos()){
                PhotoSize photoSize= photo.getSizes().get(0);
                url = photoSize.getUrl();
                break;
            }

            Glide.with(context)
                    .load(url)
                    .into(holder.iv_photo);
            holder.tv_body.setText(Utils.cleanCaption(photoPost.getCaption()));
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


        public PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_BlogName = itemView.findViewById(R.id.tv_BlogName);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_body = itemView.findViewById(R.id.tv_body);
            tv_tags = itemView.findViewById(R.id.tv_tags);
        }
    }
}
