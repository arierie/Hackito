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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.arieridwan.hackito.R;
import id.arieridwan.hackito.adapter.RepliesAdapter;
import id.arieridwan.hackito.models.ItemComments;
import id.arieridwan.hackito.network.ApiClient;
import id.arieridwan.hackito.network.ApiServices;
import id.arieridwan.hackito.utils.Constants;

/**
 * Created by arieridwan on 26/06/2017.
 */

public class DialogRepliesFragment extends DialogFragment implements DialogRepliesView {

    @BindView(R.id.rv_replies)
    RecyclerView rvReplies;
    private List<ItemComments> mList = new ArrayList<>();
    private RepliesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DialogRepliesPresenter presenter;
    private ApiServices apiServices;

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
        getDialog().setTitle("Replies");
        mAdapter = new RepliesAdapter(mList);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvReplies.setAdapter(mAdapter);
        rvReplies.setLayoutManager(mLayoutManager);
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
        presenter = new DialogRepliesPresenterImpl(this, apiServices);
        presenter.loadReplies(getArguments().getIntegerArrayList(Constants.COMMENT));
    }

    @Override
    public void getItemReplies(List<ItemComments> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        Log.e("startLoading: ", "loading..");
    }

    @Override
    public void stopLoading() {
        Log.e("stopLoading: ", "loading done.");
    }
}
