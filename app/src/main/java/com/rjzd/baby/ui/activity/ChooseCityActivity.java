package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.Province;
import com.rjzd.baby.ui.adapter.CommonTextAdapter;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import com.rjzd.baby.ui.widget.MyItemDecoration;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/11  18:46
 * create author: Hition
 * descriptions: 选择地区-选择城市或区
 */

public class ChooseCityActivity extends BaseActivity {


    @BindView(R.id.rv_city)
    RecyclerView rvCity;

    private String province;
    private ArrayList<String> citys;

    public static void startActivity(Context context,String province, ArrayList<String> citys) {
        Intent intent = new Intent(context, ChooseCityActivity.class);
        intent.putExtra("province",province);
        intent.putExtra("city_list", citys);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        ButterKnife.bind(this);
        province = getIntent().getStringExtra("province");
        citys = getIntent().getStringArrayListExtra("city_list");
        initView();
    }

    private void initView() {
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e8e8e8"));
        rvCity.addItemDecoration(decoration);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvCity.setLayoutManager(verticalLayoutManager);
        if(null!=citys && !citys.isEmpty()){
            CommonTextAdapter adapter = new CommonTextAdapter(this,citys);
            rvCity.setAdapter(adapter);
            adapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // 把省份和城市带回去
                    String city = adapter.getItem(position);
                    Province target = new Province(province);
                    target.setChooseCity(city);
                    EventBus.getDefault().post(target);
                    finish();
                }
            });
        }
    }


}
