package com.lo.sisyphuser.fish.main.fooddetail;

import com.lo.sisyphuser.fish.main.BasePresenter;
import com.lo.sisyphuser.fish.source.been.Food;

import java.util.List;

/**
 * Created by xx on 2016/9/22.
 */
public interface FoodDetailContract {
    interface ActivityPresenter extends BasePresenter{
        @Override
        void onDestroy();

        @Override
        void start();
    }

    interface ActivityView {
        void addFood(List<Food> list);
    }

    interface FragmentPresenter extends BasePresenter{
        @Override
        void onDestroy();

        @Override
        void start();

    }

    interface FragmentView{

        void showLoading();

        void hideLoading();

        void showFail();

        void hideFail();

        void showDetail(Food food);

        void hideDetail();

        void toastMessage(String message);
    }
}
