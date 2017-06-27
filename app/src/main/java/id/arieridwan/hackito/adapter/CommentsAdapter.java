package id.arieridwan.hackito.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fobid.linkabletext.view.LinkableCallback;
import com.github.fobid.linkabletext.view.OnLinkClickListener;
import com.github.fobid.linkabletext.widget.LinkableTextView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.features.comments.CommentsActivity;
import id.arieridwan.hackito.features.replies.DialogRepliesFragment;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.utils.StringHelper;

/**
 * Created by arieridwan on 24/06/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<ItemComments> mItemComments = new ArrayList<>();
    private ItemComments mData;

    public CommentsAdapter(List<ItemComments> comments) {
        this.mItemComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comments, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData = mItemComments.get(position);
        if (mData != null)
            try {
                holder.tvUserName.setText(mData.getBy());
                holder.tvComment.setText(Html.fromHtml(mData.getText()));
                holder.tvCommentTime.setText(StringHelper.getRelativeTime(mData.getTime()));
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
        LinkableTextView tvComment;
        @BindView(R.id.tv_reply)
        TextView tvReply;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvReply.setOnClickListener(view -> {
                try {
                    ArrayList list = (ArrayList) mItemComments.get(getAdapterPosition()).getKids();
                    showEditDialog(list, itemView.getContext());
                } catch (Exception e) {
                    Log.e("ViewHolder: ", e.getMessage().toString());
                }
            });
            tvComment.setOnLinkClickListener((type, value) -> {
                if(type == 5)
                    new FinestWebView.Builder(itemView.getContext()).show(value);
            });
        }
    }

    private void showEditDialog(ArrayList list, Context context) {
        CommentsActivity activity = (CommentsActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();
        DialogRepliesFragment fragmentReplies = DialogRepliesFragment.newInstance(list);
        fragmentReplies.show(fm, "fragment_replies");
    }

}
