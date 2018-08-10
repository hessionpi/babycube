package com.rjzd.baby.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.NetWorkUtil;
import com.rjzd.baby.ui.adapter.PregnancyMomAdapter;
import com.rjzd.baby.ui.tools.imgloader.ImageLoader;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.PregnancyMomChange;
import com.zd.baby.api.model.ResPregnancyMomChanges;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */
/**
 * create time: 2018/6/7 9:46
 * create author: Administrator
 * descriptions: 妈妈fragment
 */
public class MomFragment extends Fragment implements IView,View.OnClickListener{
int mPage;

LinearLayout ll_no_network;
LinearLayout ll_mom;
ImageView iv_mom;
RecyclerView rv_mom;
    PregnancyMomAdapter momAdapter;
BabyPresenter babyPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          mPage = getArguments().getInt("week");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mom, container, false);
        ll_no_network = view.findViewById(R.id.ll_no_network);
        ll_mom = view.findViewById(R.id.ll_mom);
        iv_mom = view.findViewById(R.id.iv_mom);
        rv_mom =view.findViewById(R.id.rv_mom);
        babyPresenter=new BabyPresenter(this);
        initView();
        return view;
    }
    void loadData(){
        if (NetWorkUtil.isNetworkConnected(getActivity())) {
            ll_mom.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
            babyPresenter.pregnancyMomChanges(mPage);
        }else{
            ll_no_network.setVisibility(View.VISIBLE);
            ll_mom.setVisibility(View.GONE);
        }

    }
    private void initView() {

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_mom.setLayoutManager(verticalLayoutManager);
        rv_mom.setNestedScrollingEnabled(false);
        momAdapter = new PregnancyMomAdapter(getActivity());
        rv_mom.setAdapter(momAdapter);
        loadData();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (0 == data.getReturnValue()) {
            ResPregnancyMomChanges momChanges = (ResPregnancyMomChanges) data.getReturnData();
            if (momChanges != null) {
                ImageLoader.loadTransformImage(getActivity(),momChanges.getImgAddress(),iv_mom);
                List<PregnancyMomChange> pregnancyMomChanges = momChanges.getMomChanges();
                momAdapter.setData(pregnancyMomChanges);
                momAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(getActivity(),errorCode);
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
