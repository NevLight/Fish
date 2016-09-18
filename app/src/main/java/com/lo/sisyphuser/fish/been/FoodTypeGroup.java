package com.lo.sisyphuser.fish.been;

import java.util.List;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodTypeGroup {
    private String parentId;
    private String name;
    private List<FoodTypeChild> list;

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public List<FoodTypeChild> getList() {
        return list;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<FoodTypeChild> list) {
        this.list = list;
    }
}
