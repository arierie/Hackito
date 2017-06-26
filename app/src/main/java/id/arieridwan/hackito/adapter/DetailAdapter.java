package id.arieridwan.hackito.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import id.arieridwan.hackito.features.replies.DialogRepliesFragment;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.utils.StringHelper;

/**
 * Created by arieridwan on 24/06/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<ItemComments> mItemComments = new ArrayList<>();
    private ItemComments mData;
    private Context mContext;

    public DetailAdapter(List<ItemComments> comments) {
        this.mItemComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData = mItemComments.get(position);
        String replyCount;
        try {
            replyCount = String.valueOf(mData.getKids().size());
        } catch (Exception e) {
            replyCount = "0";
        }
        if (mData != null)
            try {
                holder.tvUserName.setText(mData.getBy());
                holder.tvComment.setText(Html.fromHtml(mData.getText()));
                holder.tvCommentTime.setText(StringHelper.getRelativeTime(mData.getTime()));
                holder.tvReply.setText(replyCount + " replies");
            } catch (Exception e) {
                Log.e("onBindViewHolder: ", e.getMessage().toString());
            }
    }

    @Override
    public int getItemCount() {
        return mItemComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_reply)
        TextView tvReply;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvReply.setOnClickListener(view -> {
                ArrayList list = (ArrayList) mItemComments.get(getAdapterPosition()).getKids();
                showEditDialog(list);
            });
        }
    }

    private void showEditDialog(ArrayList list) {
        DetailActivity activity = (DetailActivity) mContext;
        FragmentManager fm = activity.getSupportFragmentManager();
        DialogRepliesFragment fragmentReplies = DialogRepliesFragment.newInstance(list);
        fragmentReplies.show(fm, "fragment_replies");
    }

}
