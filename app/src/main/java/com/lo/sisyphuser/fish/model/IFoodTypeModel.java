package com.lo.sisyphuser.fish.model;

import com.lo.sisyphuser.fish.presenter.FoodTypePresenter;

import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public interface IFoodTypeModel {
    public void loadList(String url, Map<String,String> paraMap, FoodTypePresenter.FoodTypeListener foodTypeListener);
    public void cancle();
}
