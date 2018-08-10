package com.rjzd.baby.entity;

/**
 * create time: 2018/7/4  13:45
 * create author: Hition
 * descriptions: DrawerMenu 侧滑菜单
 */

public class DrawerMenu {
    private int menuDrawable;

    private int menuText;

    public DrawerMenu(int menuDrawable, int menuText) {
        this.menuDrawable = menuDrawable;
        this.menuText = menuText;
    }

    public int getMenuDrawable() {
        return menuDrawable;
    }

    public int getMenuText() {
        return menuText;
    }
}
