package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rjzd.baby.R;
import com.rjzd.baby.ui.adapter.FragmentsViewPagerAdapter;
import com.rjzd.baby.ui.fragment.BabyFragment;
import com.rjzd.baby.ui.fragment.MomFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/20  9:46
 * create author: Administrator
 * descriptions: 准妈妈，孕宝宝变化
 */
public class PregnancyChangesActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;


    @BindView(R.id.rb_baby)
    RadioButton rbBaby;
    @BindView(R.id.rb_mom)
    RadioButton rbMom;
    @BindView(R.id.rg_pregnancy)
    RadioGroup rgPregnancy;
    @BindView(R.id.baby_indicator)
    TabLayout babyIndicator;
    @BindView(R.id.baby_viewpager)
    ViewPager babyViewpager;
    @BindView(R.id.ll_baby)
    LinearLayout llBaby;
    @BindView(R.id.mom_indicator)
    TabLayout momIndicator;
    @BindView(R.id.mom_viewpager)
    ViewPager momViewpager;
    @BindView(R.id.ll_mom)
    LinearLayout llMom;

    int babyId;
    int babyStage;
    private final static int BABY=0;
    private final static int MOM=1;
    int type;
    public static void startActivity(Context context, int babyId,int babyStage,int type) {
        Intent intent = new Intent(context, PregnancyChangesActivity.class);
        intent.putExtra("babyId", babyId);
        intent.putExtra("babyStage", babyStage);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    ArrayList<Fragment> mFragments = new ArrayList<>();
    ArrayList<Fragment> Fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_changes);
        ButterKnife.bind(this);
        type=  getIntent().getIntExtra("type",0);
        selectView(type);
        babyId=  getIntent().getIntExtra("babyId",0);
        babyStage=  getIntent().getIntExtra("babyStage",0);

        ivBack.setOnClickListener(this);
      //  llBaby.setVisibility(View.VISIBLE);
        rgPregnancy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_baby:
                        llMom.setVisibility(View.GONE);
                        llBaby.setVisibility(View.VISIBLE);

                        break;

                    case R.id.rb_mom:
                        llBaby.setVisibility(View.GONE);
                        llMom.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });

        for (int i = 1; i <= 38; i++) {
            BabyFragment fragment = new BabyFragment();
            MomFragment faagment = new MomFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("week", i+3);
            bundle.putInt("babyId", babyId);
            fragment.setArguments(bundle);
            faagment.setArguments(bundle);
            mFragments.add(fragment);
            Fragments.add(faagment);
        }


        setBabyView();
        setMomView();
    }
    void selectView(int type){
       switch (type){
           case BABY:
               rbBaby.setChecked(true);
               llMom.setVisibility(View.GONE);
               llBaby.setVisibility(View.VISIBLE);
               break;
           case MOM:
               rbMom.setChecked(true);
               llBaby.setVisibility(View.GONE);
               llMom.setVisibility(View.VISIBLE);
               break;
       }
    }
    void setBabyView() {


        FragmentsViewPagerAdapter fragmentAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), mFragments, getResources().getStringArray(R.array.pregnancy_week_change));
        babyViewpager.setAdapter(fragmentAdapter);
        babyViewpager.setCurrentItem(babyStage-1);
        babyIndicator.setupWithViewPager(babyViewpager);
        babyIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    void setMomView() {
        FragmentsViewPagerAdapter pagerAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), Fragments, getResources().getStringArray(R.array.pregnancy_week_change));
        momViewpager.setAdapter(pagerAdapter);
        momViewpager.setCurrentItem(babyStage-1);
        momIndicator.setupWithViewPager(momViewpager);
        momIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
