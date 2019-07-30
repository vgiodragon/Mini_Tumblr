package com.giotec.mini_tumblr.UI.Fragments.Home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.giotec.mini_tumblr.Models.PostItem;
import com.giotec.mini_tumblr.R;
import com.giotec.mini_tumblr.Utils.Connections;
import com.giotec.mini_tumblr.Utils.Utils;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private boolean loading = false;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private String TAG = "GIODEBUG_HOME";
    private List<PostItem> post_items;
    private ProgressBar mprogressbar;
    private static int n_loaded;
    // TODO: Rename and change types of parameters


    private static OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(OnFragmentInteractionListener mListener2) {
        Home fragment = new Home();
        mListener = mListener2;
        n_loaded= 0;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        post_items = Utils.getPosts();
        adapter = new MyAdapter(getActivity().getApplicationContext(), post_items,mListener );
        adapter.setHasStableIds(true);
        recyclerView = v.findViewById(R.id.my_recycler_noticia);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mprogressbar = v.findViewById(R.id.progressBar);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)&& newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG,"Cargara");
                    mprogressbar.setVisibility(View.VISIBLE);
                    new LoadMorePosts().execute();
                }
            }
        });
        return v;
    }

    private class LoadMorePosts extends AsyncTask<Void, Void, Boolean> {
        private boolean newposts=false;
        @Override
        protected Boolean doInBackground(Void... voids) {
            if(!loading){
                loading=true; n_loaded ++;
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("limit", Utils.getLimit());
                params.put("offset", Utils.getLimit()*n_loaded);
                //If clien is null entonces no hay internet
                JumblrClient client = Connections.getClient();
                if(Connections.getClient()==null){
                    client =Connections.getClientwithToken(getContext());
                }

                try {
                    List<Post> posts = client.userDashboard(params);
                    post_items.addAll(Utils.filterPosts(posts));
                    newposts = true;
                }catch (Exception ex) {
                    Log.d(TAG,ex.getMessage());
                    return false;
                }
            }else{
                Log.d(TAG,"Already loading...");
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean internet) {
            super.onPostExecute(internet);
            if (!internet)
                Toast.makeText(getContext(), getString(R.string.noInternet),
                        Toast.LENGTH_LONG).show();
            if(newposts)
                adapter.notifyDataSetChanged();
            mprogressbar.setVisibility(View.INVISIBLE);
            loading=false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void setLike(ImageView iv_like,boolean like);
        void Eliminate(View iv, int position);
    }

    public void RemovePost(int position){
        post_items.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,post_items.size());
    }

}
