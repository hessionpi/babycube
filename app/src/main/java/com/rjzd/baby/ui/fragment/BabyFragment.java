package com.rjzd.baby.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.ui.adapter.PregnancyBabyAdapter;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.PregnancyBabyChange;
import com.zd.baby.api.model.ResPregnancyBabyChanges;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6.
 */

/**
 * create time: 2018/6/6  9:46
 * create author: Administrator
 * descriptions: 宝宝fragment
 */
public class BabyFragment extends Fragment implements IView ,View.OnClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";

    RecyclerView rvBaby;
    LinearLayout ll_no_network;
    BabyPresenter presenter;
    PregnancyBabyAdapter babyAdapter;
int mPage;
int babyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("week");
        babyId = getArguments().getInt("babyId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baby, container, false);
        rvBaby =view.findViewById( R.id.rv_baby);
        ll_no_network = view.findViewById(R.id.ll_no_network);
        presenter = new BabyPresenter(this);
        initView();

        return view;
    }
void loadData(){
    if (NetWorkUtil.isNetworkConnected(getActivity())) {
        rvBaby.setVisibility(View.VISIBLE);
        ll_no_network.setVisibility(View.GONE);
        presenter.pregnancyBabyChanges(babyId, mPage);
    }else{
        ll_no_network.setVisibility(View.VISIBLE);
        rvBaby.setVisibility(View.GONE);
    }

}
    private void initView() {
        loadData();
        ll_no_network.setOnClickListener(this);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvBaby.setLayoutManager(verticalLayoutManager);
        babyAdapter = new PregnancyBabyAdapter(getActivity());
        rvBaby.setAdapter(babyAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (0 == data.getReturnValue()) {
            ResPregnancyBabyChanges babyChanges = (ResPregnancyBabyChanges) data.getReturnData();
            if (babyChanges != null) {
                List<PregnancyBabyChange> pregnancyBabyChanges = babyChanges.getBabyChanges();
                babyAdapter.setData(pregnancyBabyChanges);
                babyAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFailShow(int flag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_no_network:
                loadData();
                break;
        }
    }
}