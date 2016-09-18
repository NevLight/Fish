package com.lo.sisyphuser.fish.view;

import com.lo.sisyphuser.fish.been.Food;

/**
 * Created by xx on 2016/9/14.
 */
public interface FoodDetailView {
    public void getContentSuccess(Food food);
    public void getContentFail(String errorMessage);
}
