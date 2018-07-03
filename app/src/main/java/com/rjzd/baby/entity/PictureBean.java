package com.rjzd.baby.entity;/**
 * Created by Administrator on 2018/6/14.
 */

import java.io.Serializable;

/**
 * create time: 2018/6/14  9:50
 * create author: Administrator
 * descriptions: PictureBean
 */

public class PictureBean implements Serializable {
private  String path;
private String displayName;

    public PictureBean(String path, String displayName) {
        this.path = path;
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
