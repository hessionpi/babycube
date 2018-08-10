package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rjzd.baby.R;
import com.rjzd.baby.entity.Tools;
import com.rjzd.baby.ui.adapter.ToolsAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.dialog.DialogManager;
import com.rjzd.baby.ui.widget.dialog.UniversalDialogListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolsActivity extends AppCompatActivity implements XMBaseAdapter.OnItemClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_pregnancy)
    RecyclerView rvPregnancy;
    @BindView(R.id.rv_baby)
    RecyclerView rvBaby;
    ToolsAdapter pregnancyToolsAdapter;
    ToolsAdapter babyToolsAdapter;

    List<Tools> pregnancyTools = new ArrayList<>();
    List<Tools> babyTools = new ArrayList<>();
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ToolsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvPregnancy.setLayoutManager(gridLayoutManager);
        pregnancyToolsAdapter = new ToolsAdapter(this);
        rvPregnancy.setNestedScrollingEnabled(false);
        rvPregnancy.setAdapter(pregnancyToolsAdapter);
        getpregnancyToolsData();
        pregnancyToolsAdapter.setData(pregnancyTools);
pregnancyToolsAdapter.setOnItemClickListener(this);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);
        rvBaby.setLayoutManager(gridLayoutManager1);
        babyToolsAdapter = new ToolsAdapter(this);
        rvBaby.setNestedScrollingEnabled(false);
        rvBaby.setAdapter(babyToolsAdapter);
        getBabyToolsData();
        babyToolsAdapter.setData(babyTools);
        babyToolsAdapter.setOnItemClickListener(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void getpregnancyToolsData() {


        Tools tools2 = new Tools();
        tools2.setId(R.drawable.baby_change);
        tools2.setName("胎宝宝变化");


        Tools tools3 = new Tools();
        tools3.setId(R.drawable.mom_change);
        tools3.setName("准妈妈变化");

        Tools tools4 = new Tools();
        tools4.setId(R.drawable.medical);
        tools4.setName("产检时间表");


        pregnancyTools.add(tools2);
        pregnancyTools.add(tools3);
        pregnancyTools.add(tools4);

    }

    void getBabyToolsData() {


        Tools tools2 = new Tools();
        tools2.setId(R.drawable.eat);
        tools2.setName("能不能吃");

        babyTools.add(tools2);

    }


    @Override
    public void onItemClick(int position) {
        DialogManager.showToolsDialog(this, R.string.msg, R.string.tools_ok, new UniversalDialogListener() {
            @Override
            public void onNegative() {

            }

            @Override
            public void onPositive() {

            }
        });
    }
}
