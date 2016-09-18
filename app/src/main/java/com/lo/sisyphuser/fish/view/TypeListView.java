package com.lo.sisyphuser.fish.view;

import com.lo.sisyphuser.fish.been.Food;

import java.util.List;

/**
 * Created by xx on 2016/9/13.
 */
public interface TypeListView {
    /**
     * 获取列表信息成功
     * @param contentList
     */
    public void getContentSuccess(List<Food> contentList);

    /**
     * 添加列表信息成功
     * @param contentList
     */
    public void addContentSuccess(List<Food> contentList);

    /**
     * 获取列表信息失败
     */
    public void getContentFail(String errorInfo);

    /**
     * 添加列表信息失败
     * @param errorInfo
     */
    public void addContentError(String errorInfo);

    /**
     * 没有更多信息
     */
    public void noMoreMessage();
}
