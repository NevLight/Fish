package com.lo.sisyphuser.fish.source.base;

import com.lo.sisyphuser.fish.main.foodtype.FoodTypePresenter;

import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public interface IFoodTypeModel {
    void loadList(String url, Map<String,String> paraMap, FoodTypeListener foodTypeListener);
    void cancle();

    interface FoodTypeListener{
        void loadSuccess(String json);
        void loadFail(Exception e);
    }
}
