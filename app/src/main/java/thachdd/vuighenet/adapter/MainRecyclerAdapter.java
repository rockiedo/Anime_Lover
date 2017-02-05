package thachdd.vuighenet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import thachdd.vuighenet.R;
import thachdd.vuighenet.model.Episode;

/**
 * Created by thachdd on 05/02/2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder> {
    private List<Episode> mListEpisode;

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

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
