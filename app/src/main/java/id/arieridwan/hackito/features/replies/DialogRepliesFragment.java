package id.arieridwan.hackito.features.replies;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.App;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.RepliesAdapter;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.utils.Constants;

/**
 * Created by arieridwan on 26/06/2017.
 */

public class DialogRepliesFragment extends DialogFragment
        implements DialogRepliesContract.View {

    @BindView(R.id.rv_replies)
    RecyclerView rvReplies;
    @BindView(R.id.progressview)
    RelativeLayout progressview;
    @BindView(R.id.tv_label)
    TextView tvLabel;

    private List<ItemComments> mList = new ArrayList<>();
    private RepliesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Inject
    DialogRepliesPresenter presenter;

    public DialogRepliesFragment() {

    }

    public static DialogRepliesFragment newInstance(ArrayList<Integer> item) {
        DialogRepliesFragment frag = new DialogRepliesFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(Constants.COMMENT, item);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_replies, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInjector();
        mAdapter = new RepliesAdapter(mList);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvReplies.setAdapter(mAdapter);
        rvReplies.setLayoutManager(mLayoutManager);
        try {
            List<Integer> integers = getArguments().getIntegerArrayList(Constants.COMMENT);
            if (integers != null) {
                if (integers.size() != 0)
                    presenter.loadReplies(getArguments().getIntegerArrayList(Constants.COMMENT));
                else
                    noReplies();
            } else {
                noReplies();
            }
        } catch (Exception e) {
            Log.e("onViewCreated: ", e.getMessage().toString());
        }
    }

    private void initInjector() {
        DaggerDialogRepliesComponent.builder()
                .netComponent(((App) getActivity().getApplicationContext()).getNetComponent())
                .dialogRepliesModule(new DialogRepliesModule(this))
                .build().inject(this);
    }

    private void noReplies(){
        tvLabel.setText(R.string.no_replies);
        progressview.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getItemReplies(List<ItemComments> list) {
        if(list.size() != 0) {
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }else {
            startLoadingError(new Exception("List is empty"));
        }
    }

    @Override
    public void startLoading() {
        progressview.setVisibility(View.VISIBLE);
        rvReplies.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startLoadingError(Throwable e) {
        tvLabel.setText(R.string.no_replies);
    }

    @Override
    public void stopLoading() {
        progressview.setVisibility(View.INVISIBLE);
        rvReplies.setVisibility(View.VISIBLE);
    }
}
