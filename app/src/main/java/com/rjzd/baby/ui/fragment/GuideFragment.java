package com.rjzd.baby.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.rjzd.baby.R;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.ui.activity.MainActivity;
import com.rjzd.baby.ui.activity.MemberActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/29  10:37
 * create author: Hition
 * descriptions: 引导页Fragment
 */


public class GuideFragment extends Fragment {

    @BindView(R.id.iv_guide_top)
    ImageView ivGuideTop;
    @BindView(R.id.iv_guide_bottom)
    ImageView ivGuideBottom;
    @BindView(R.id.btn_open_now)
    Button btnOpenNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        // 引导页序列号 1、2、3、4
        int sequence = getArguments().getInt("guide_sequence");
        switch (sequence){
            case 1:
                ivGuideTop.setImageResource(R.drawable.guide1_top);
                ivGuideBottom.setImageResource(R.drawable.guide1_bottom);
                break;

            case 2:
                ivGuideTop.setImageResource(R.drawable.guide2_top);
                ivGuideBottom.setImageResource(R.drawable.guide2_bottom);
                break;

            case 3:
                ivGuideTop.setImageResource(R.drawable.guide3_top);
                ivGuideBottom.setImageResource(R.drawable.guide3_bottom);
                break;


            case 4:
                ivGuideTop.setImageResource(R.drawable.guide4_top);
                ivGuideBottom.setImageResource(R.drawable.guide4_bottom);
                btnOpenNow.setVisibility(View.VISIBLE);
                break;
        }

        btnOpenNow.setOnClickListener((View v) -> {
            // 判断是进入首页还是去注册、登录
            LoginModel model = UserInfoCenter.getInstance().getLoginModel();
            if (model == null) {
                MemberActivity.startActivity(getActivity());
            } else {
                MainActivity.startActivity(getActivity());
            }
            getActivity().finish();
        });
    }


}
