package id.arieridwan.hackito.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.fobid.linkabletext.widget.LinkableTextView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.utils.StringHelper;

/**
 * Created by arieridwan on 25/06/2017.
 */

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.ViewHolder> {

    private List<ItemComments> mList = new ArrayList<>();
    private ItemComments mData;


    public RepliesAdapter(List<ItemComments> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_replies, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData = mList.get(position);
        holder.tvUserName.setText(mData.getBy());
        holder.tvComment.setText(Html.fromHtml(mData.getText()));
        holder.tvCommentTime.setText(StringHelper.getRelativeTime(mData.getTime()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_comment)
        LinkableTextView tvComment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvComment.setOnLinkClickListener((type, value) -> {
                if(type == 5)
                    new FinestWebView.Builder(itemView.getContext()).show(value);
            });
        }
    }

}
