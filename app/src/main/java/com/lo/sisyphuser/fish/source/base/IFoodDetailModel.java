package com.lo.sisyphuser.fish.source.base;

import com.lo.sisyphuser.fish.main.fooddetail.FoodDetailFragmentPresenter;

import java.util.Map;

/**
 * Created by xx on 2016/9/14.
 */
public interface IFoodDetailModel {
    public void loadList(String url, Map<String,String> paraMap, FoodDetailFragmentPresenter.FoodDetailListener foodDetailListener);
    public void cancle();
}
