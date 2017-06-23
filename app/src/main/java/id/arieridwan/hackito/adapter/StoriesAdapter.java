package id.arieridwan.hackito.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.models.Item;
import id.arieridwan.hackito.utils.StringHelper;

/**
 * Created by arieridwan on 20/06/2017.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<Item> mList = new ArrayList<>();
    private Item mData;

    public StoriesAdapter(List<Item> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData = mList.get(position);
        String count;
        String score;
        holder.tvTitle.setText(mData.getTitle());
        holder.tvAuthor.setText(mData.getAuthor());
        holder.tvTime.setText(StringHelper.getDateCurrentTimeZone(mData.getTime()));
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
        holder.tvPoint.setText(score+" Points");
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
        }
    }
}
