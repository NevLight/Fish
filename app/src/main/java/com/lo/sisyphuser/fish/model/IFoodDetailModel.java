package com.lo.sisyphuser.fish.model;

import com.lo.sisyphuser.fish.presenter.FoodDetailPresenter;

import java.util.Map;

/**
 * Created by xx on 2016/9/14.
 */
public interface IFoodDetailModel {
    public void loadList(String url, Map<String,String> paraMap, FoodDetailPresenter.FoodDetailListener foodDetailListener);
    public void cancle();
}
