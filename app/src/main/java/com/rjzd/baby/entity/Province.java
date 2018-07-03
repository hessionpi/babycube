package com.rjzd.baby.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * create time: 2018/6/11  18:41
 * create author: Hition
 * descriptions: 省份实体
 */

public class Province implements Serializable {

    private String name;

    private String chooseCity;

    private ArrayList<String> city;

    public Province(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCity() {
        return city;
    }

    public void setCity(ArrayList<String> city) {
        this.city = city;
    }

    public String getChooseCity() {
        return chooseCity;
    }

    public void setChooseCity(String chooseCity) {
        this.chooseCity = chooseCity;
    }
}
