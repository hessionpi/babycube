package com.rjzd.baby.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.baby.R;
import com.rjzd.baby.entity.DrawerMenu;
import com.rjzd.baby.ui.adapter.recycleadapter.BaseViewHolder;
import com.rjzd.baby.ui.adapter.recycleadapter.XMBaseAdapter;
import java.util.List;

/**
 * create time: 2018/7/4  13:47
 * create author: Hition
 * descriptions: 侧滑菜单数据适配器
 */

public class MenuAdapter extends XMBaseAdapter<DrawerMenu> {

    public MenuAdapter(Context context, List<DrawerMenu> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuHolder(parent, R.layout.item_drawer_menu);
    }

    private class MenuHolder extends BaseViewHolder<DrawerMenu>{

        private TextView mMenu;

        MenuHolder(ViewGroup parent, int res) {
            super(parent, res);
            mMenu = $(R.id.tv_menu);
        }

        @Override
        public void setData(DrawerMenu data) {
            mMenu.setText(data.getMenuText());
            mMenu.setCompoundDrawablesWithIntrinsicBounds(data.getMenuDrawable(),0,0,0);
        }
    }

}
