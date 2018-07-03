package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.BaseResponse;
import com.rjzd.baby.presenter.impl.BabyPresenter;
import com.rjzd.baby.tools.ToastUtils;
import com.rjzd.baby.ui.adapter.BabyManageAdapter;
import com.rjzd.baby.ui.widget.MyItemDecoration;
import com.rjzd.baby.ui.widget.ToolsbarView;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;
import com.rjzd.baby.view.IView;
import com.zd.baby.api.model.BabyInfo;
import com.zd.baby.api.model.ResAllBaby;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.rv_mybabys)
    RecyclerView rvBabys;

    private boolean showBornOnly = false;


    private BabyManageAdapter babyManageAdapter;

    private BabyPresenter presenter;


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
        layoutTitleBar.setRightBtnVisible(View.VISIBLE);
        layoutTitleBar.setOnRightClickListener(new ToolsbarView.OnRightClickListener() {
            @Override
            public void onRightImgClick() {

            }

            @Override
            public void onRightBtnClick() {
                // 响应添加宝宝事件
                AddBabyActivity.startActivity(BabyManageActivity.this,false,showBornOnly);
            }
        });

        ((DefaultItemAnimator) rvBabys.getItemAnimator()).setSupportsChangeAnimations(false);
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 14, Color.parseColor("#f5f5f5"));
        rvBabys.addItemDecoration(decoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvBabys.setLayoutManager(linearLayoutManager);
        babyManageAdapter = new BabyManageAdapter(this);
        babyManageAdapter.setBabyManageListener(this);
        rvBabys.setAdapter(babyManageAdapter);


    }

    /**
     * 修改宝宝
     * @param baby   宝宝信息
     */
    @Override
    public void onBabyUpdate(BabyInfo baby) {
        // 跳转到修改界面
        AddBabyActivity.startActivity(this,baby);
    }

    /**
     * 删除宝宝
     * @param babyId    宝宝id
     */
    @Override
    public void onBabyDelete(int babyId,int position) {
        if(babyManageAdapter.getCount() == 1){
            ToastUtils.show(this,"至少需要保留一个宝宝！");
            return ;
        }

        DialogManager.showDialogWith2ButtonNoTitle(this, R.string.tips_delete_baby, R.string.ok, R.string.cancel, new UniversalDialogListener() {
            @Override
            public void onNegative() {

            }

            @Override
            public void onPositive() {
                // 确定要删除，调用删除接口，同时刷新本地，从列表移除此项
                presenter.deleteBaby(babyId);
                babyManageAdapter.remove(position);
            }
        });
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onComplete(BaseResponse data, int flag) {
        if(null != data){
            switch (data.getReturnValue()){
                case 0:
                    switch (flag){
                        case BabyConstants.BABY_MANAGE_GET:
                            ResAllBaby babyData = (ResAllBaby) data.getReturnData();
                            List<BabyInfo> babys = babyData.getBabyList();
                            if(null!=babys && !babys.isEmpty()){
                                if(babyManageAdapter.getCount()>0){
                                    babyManageAdapter.clear();
                                }
                                babyManageAdapter.addAll(babys);

                                // 判断怀孕中或者备孕只能够选其一
                                for(BabyInfo baby : babys){
                                    if(baby.getBabyStatus() == 0 || baby.getBabyStatus() == 1){
                                        showBornOnly = true;
                                        break;
                                    }else{
                                        showBornOnly = false;
                                    }
                                }
                            }
                            break;

                        case BabyConstants.BABY_MANAGE_DELETE:
                            EventBus.getDefault().post("refresh");

                            ToastUtils.show(this,"删除成功啦……");
                            break;
                    }
                    break;

                case 500:
                    // 服务端错误
                    Logger.e(data.getReturnMsg());
                    break;

                default:
                    ToastUtils.show(this,data.getReturnMsg());
                    break;

            }
        }

    }

    @Override
    public void onFailShow(int flag) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocation(String command) {
        if(!TextUtils.isEmpty(command) && command.equals("refresh")){
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
