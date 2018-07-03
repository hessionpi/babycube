package com.rjzd.baby.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.Province;
import com.rjzd.baby.ui.adapter.ProvinceAdapter;
import com.rjzd.baby.ui.widget.MyItemDecoration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create time: 2018/6/11  18:46
 * create author: Hition
 * descriptions: 选择地区-选择省份或直辖市
 */

public class ChooseProvinceActivity extends BaseActivity {

    @BindView(R.id.rv_province)
    RecyclerView rvProvince;

    private String provinceStr;


    private ProvinceAdapter adapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChooseProvinceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_province);
        ButterKnife.bind(this);
        provinceStr = readAssetsJson(this);
        initView();

        EventBus.getDefault().register(this);
    }

    private void initView() {
        MyItemDecoration decoration = new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e8e8e8"));
        rvProvince.addItemDecoration(decoration);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvProvince.setLayoutManager(verticalLayoutManager);
        List<Province> provinceList = getAllProvince();
        if(null != provinceList && !provinceList.isEmpty()){
            adapter = new ProvinceAdapter(this,provinceList);
            rvProvince.setAdapter(adapter);
        }

        adapter.setOnItemClickListener((int position) -> {
            // 跳转至选择城市界面
            Province province = adapter.getItem(position);
            ChooseCityActivity.startActivity(ChooseProvinceActivity.this,province.getName(),province.getCity());
        });
    }

    /**
     * 将json解析成List<Province> 对象
     * @return  List<Province>
     */
    private List<Province> getAllProvince(){
        Type type = new TypeToken<List<Province>>(){}.getType();
        Gson gson = new Gson();
        if(!TextUtils.isEmpty(provinceStr)){
            return gson.fromJson(provinceStr,type);
        }
        return null;
    }

    /**
     * 读取assets下的json文件
     */
    private String readAssetsJson(Context context){
        try {
            InputStream is = context.getAssets().open("json/regions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLocation(Province province) {
        if(null != province){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
