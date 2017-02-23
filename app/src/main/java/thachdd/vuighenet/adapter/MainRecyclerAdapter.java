package thachdd.vuighenet.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import thachdd.vuighenet.R;
import thachdd.vuighenet.model.EpisodeDetail;

/**
 * Created by thachdd on 05/02/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder> {
    private List<EpisodeDetail> mEpisodes;
    private WeakReference<Activity> weakReference;
    private int mCurId = -1;

    public MainRecyclerAdapter(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
        mEpisodes = new ArrayList<>();
    }

    public void setEpisodes(List<EpisodeDetail> episodes) {
        if (mEpisodes != null && mEpisodes.size() > 0) {
            mEpisodes.clear();
        }

        mEpisodes = episodes;
    }

    public void addEpisode(EpisodeDetail episode) {
        if (mEpisodes == null) {
            mEpisodes = new ArrayList<>();
        }

        mEpisodes.add(episode);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mViews;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.main_recycler_item_thumbnail);
            mTitle = (TextView) itemView.findViewById(R.id.main_recycler_item_title);
            mViews = (TextView) itemView.findViewById(R.id.main_recycler_item_views);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_item, parent, false);
        ImageView img = (ImageView) itemView.findViewById(R.id.main_recycler_item_thumbnail);
        int w = img.getWidth();
        int h = (int) 1.0 * w / 16 * 9;
        img.setMinimumHeight(h);
        img.setMaxHeight(h);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EpisodeDetail episode = mEpisodes.get(position);

        String title = episode.getFullName();
        String views = "" + episode.getViews() + " views";

        holder.mViews.setText(views);
        holder.mTitle.setText(title);

        if (mEpisodes.get(position).getId() == mCurId) {
            holder.mTitle.setTextColor(Color.parseColor("#FF9800"));
        }
        else {
            holder.mTitle.setTextColor(Color.parseColor("#757575"));
        }

        Activity activity = weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            Picasso.with(activity.getApplicationContext()).load(episode.getThumbnail()).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mEpisodes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getPlayerId(int pos) {
        return mEpisodes.get(pos).getId();
    }

    public String getLink(int pos) {
        return mEpisodes.get(pos).getLink();
    }

    public void setCurId(int curId) {
        mCurId = curId;
    }
}
