package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.api.ExceptionHandler;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.adapter.BabyManageAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.ToolsbarView;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ResAllBaby;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/1  14:40
 * create author: Hition
 * descriptions: 宝宝管理
 */

public class BabyManageActivity extends BaseActivity implements BabyManageAdapter.OnBabyManageListener, IView {


    @BindView(R.id.layout_title_bar)
    ToolsbarView layoutTitleBar;
    @BindView(R.id.tv_pregnancy)
    TextView tvPregnancy;
    @BindView(R.id.rv_mybaby_pregnancy)
    RecyclerView rvMybabyPregnancy;
    @BindView(R.id.tv_babies)
    TextView tvBabies;
    @BindView(R.id.rv_mybabies)
    RecyclerView rvMybabies;


    private BabyManageAdapter pregnancyBabyAdapter;
    private BabyManageAdapter babyManageAdapter;
    private BabyPresenter presenter;
    private int deleteBabyId;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BabyManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_babys);
        ButterKnife.bind(this);
        initView();

        presenter = new BabyPresenter(this);
        presenter.getAllBabys();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        layoutTitleBar.setRightImgVisible(View.VISIBLE);
        layoutTitleBar.setOnRightClickListener(new ToolsbarView.OnRightClickListener() {
            @Override
            public void onRightImgClick() {
                // 响应添加宝宝事件
                AddBabyActivity.startActivity(BabyManageActivity.this);
            }

            @Override
            public void onRightBtnClick() {

            }
        });

        // 怀孕中宝宝
        ((DefaultItemAnimator) rvMybabyPregnancy.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager pregnancyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMybabyPregnancy.setLayoutManager(pregnancyLayoutManager);
        pregnancyBabyAdapter = new BabyManageAdapter(this);
        pregnancyBabyAdapter.setBabyManageListener(this);
        rvMybabyPregnancy.setAdapter(pregnancyBabyAdapter);

        // 已出生宝宝
        ((DefaultItemAnimator) rvMybabies.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMybabies.setLayoutManager(linearLayoutManager);
        babyManageAdapter = new BabyManageAdapter(this);
        babyManageAdapter.setBabyManageListener(this);
        rvMybabies.setAdapter(babyManageAdapter);

        pregnancyBabyAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                BabyInfo babyInfo = pregnancyBabyAdapter.getItem(position);
                AddBabyActivity.startActivity(BabyManageActivity.this, babyInfo);
            }
        });
        babyManageAdapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                BabyInfo babyInfo = babyManageAdapter.getItem(position);
                AddBabyActivity.startActivity(BabyManageActivity.this, babyInfo);
            }
        });
    }

    /**
     * 删除宝宝
     *
     * @param babyId 宝宝id
     */
    @Override
    public void onBabyDelete(int babyId,int babyStatus, int position) {
        int remainBaby = pregnancyBabyAdapter.getCount() + babyManageAdapter.getCount();
        if (remainBaby == 1) {
            ToastUtils.show(this, "至少需要保留一个宝宝！");
            return;
        }

        deleteBabyId = babyId;
        DialogManager.showDialogWith2ButtonNoTitle(this, R.string.tips_delete_baby, R.string.ok, R.string.cancel, new UniversalDialogListener() {
            @Override
            public void onNegative() {

            }

            @Override
            public void onPositive() {
                // 确定要删除，调用删除接口，同时刷新本地，从列表移除此项
                presenter.deleteBaby(babyId);
                if(babyStatus == 1){
                    pregnancyBabyAdapter.remove(position);
                    if(pregnancyBabyAdapter.getCount() == 0){
                        tvPregnancy.setVisibility(View.GONE);
                    }
                }else if(babyStatus == 2){
                    babyManageAdapter.remove(position);
                    if(babyManageAdapter.getCount() == 0){
                        tvBabies.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if (null != data) {
            switch (data.getReturnValue()) {
                case 0:
                    switch (flag) {
                        case BabyConstants.BABY_MANAGE_GET:
                            ResAllBaby babyData = (ResAllBaby) data.getReturnData();
                            List<BabyInfo> babys = babyData.getBabyList();
                            if (null != babys && !babys.isEmpty()) {
                                List<BabyInfo> pregancy = new ArrayList<>();
                                List<BabyInfo> birth = new ArrayList<>();
                                for(BabyInfo binfo : babys){
                                    if(binfo.getBabyStatus() == 1){
                                        pregancy.add(binfo);
                                    }else if(binfo.getBabyStatus() == 2){
                                        birth.add(binfo);
                                    }
                                }

                                if(pregancy.isEmpty()){
                                    tvPregnancy.setVisibility(View.GONE);
                                }else{
                                    tvPregnancy.setVisibility(View.VISIBLE);
                                }
                                if(birth.isEmpty()){
                                    tvBabies.setVisibility(View.GONE);
                                }else{
                                    tvBabies.setVisibility(View.VISIBLE);
                                }
                                pregnancyBabyAdapter.setData(pregancy);
                                pregnancyBabyAdapter.notifyDataSetChanged();
                                babyManageAdapter.setData(birth);
                                babyManageAdapter.notifyDataSetChanged();
                            }
                            break;

                        case BabyConstants.BABY_MANAGE_DELETE:
                            EventBus.getDefault().post("delete_baby/"+deleteBabyId);
                            ToastUtils.show(this, "删除成功啦……");
                            break;
                    }
                    break;

                case 500:
                    // 服务端错误
                    Logger.e(data.getReturnMsg());
                    break;

                default:
                    ToastUtils.show(this, data.getReturnMsg());
                    break;

            }
        }

    }

    @Override
    public void onFailShow(int errorCode) {
        ExceptionHandler.handleException(this, errorCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventRefreshBaby(String refresh) {
        if (!TextUtils.isEmpty(refresh) && refresh.equals("refresh_baby")) {
            presenter.getAllBabys();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
        EventBus.getDefault().unregister(this);
    }
}
