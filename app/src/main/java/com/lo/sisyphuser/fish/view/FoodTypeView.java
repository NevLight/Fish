package com.lo.sisyphuser.fish.view;

import com.lo.sisyphuser.fish.been.FoodTypeGroup;

import java.util.List;

/**
 * Created by xx on 2016/9/13.
 */
public interface FoodTypeView {
    /**
     * 获取列表信息成功
     * @param contentList
     */
    public void getContentSuccess(List<FoodTypeGroup> contentList);

    /**
     * 获取列表信息失败
     */
    public void getContentFail(String errorInfo);

}
