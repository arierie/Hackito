package id.arieridwan.hackito.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.features.detail.DetailActivity;
import id.arieridwan.hackito.models.ItemStories;
import id.arieridwan.hackito.utils.Constants;
import id.arieridwan.hackito.utils.StringHelper;

/**
 * Created by arieridwan on 20/06/2017.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<ItemStories> mList = new ArrayList<>();
    private ItemStories mData;
    private Context mContext;

    public StoriesAdapter(List<ItemStories> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData = mList.get(position);
        String count;
        String score;
        holder.tvTitle.setText(mData.getTitle());
        holder.tvAuthor.setText(mData.getAuthor());
        holder.tvTime.setText(StringHelper.getRelativeTime(mData.getTime()));
        holder.tvUrl.setText(StringHelper.getHost(mData.getUrl()));
        try{
            count = String.valueOf(mData.getDescendants());
        }catch (Exception e){
            count = "0";
        }
        holder.tvCommentCount.setText(count);
        try {
            score = String.valueOf(mData.getScore());
        }catch (Exception e){
            score = "0";
        }
        holder.tvPoint.setText(score+" points");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void movetoDetail(){
        Intent i = new Intent(mContext, DetailActivity.class);
        i.putExtra(Constants.COMMENT, mData);
        mContext.startActivity(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_url)
        TextView tvUrl;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.tv_point)
        TextView tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                try {
                    mData = mList.get(getAdapterPosition());
                    movetoDetail();
                } catch (Exception e){
                    Log.e("ViewHolder: ", e.getMessage().toString());
                }
            });
        }
    }
}
