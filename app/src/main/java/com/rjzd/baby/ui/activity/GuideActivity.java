package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.model.LoginModel;
import com.rjzd.baby.model.UserInfoCenter;
import com.rjzd.baby.ui.fragment.GuideFragment;
import com.rjzd.baby.ui.widget.CircleIndicator;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vp_splash)
    ViewPager vpSplash;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    @BindView(R.id.indicator)
    CircleIndicator indicator;


     private List<Fragment> fragments = new ArrayList<>();


     public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        GuideFragment guide1 = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("guide_sequence",1);
        guide1.setArguments(bundle);

        GuideFragment guide2 = new GuideFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("guide_sequence",2);
        guide2.setArguments(bundle2);

        GuideFragment guide3 = new GuideFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("guide_sequence",3);
        guide3.setArguments(bundle3);

        GuideFragment guide4 = new GuideFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("guide_sequence",4);
        guide4.setArguments(bundle4);

        fragments.add(guide1);
        fragments.add(guide2);
        fragments.add(guide3);
        fragments.add(guide4);

        vpSplash.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        indicator.setViewPager(vpSplash);
        tvSkip.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        LoginModel model = UserInfoCenter.getInstance().getLoginModel();
        switch (v.getId()) {
            case R.id.tv_skip:
                MainActivity.startActivity(this);
                finish();
                break;
        }

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            return super.instantiateItem(container, position);
        }

        //防止重新销毁视图
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //如果注释这行，那么不管怎么切换，page都不会被销毁
            super.destroyItem(container, position, object);
        }
    }


}
