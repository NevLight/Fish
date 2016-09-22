package com.lo.sisyphuser.fish.main.foodtype;

import com.lo.sisyphuser.fish.main.BasePresenter;
import com.lo.sisyphuser.fish.source.been.FoodTypeGroup;

import java.util.List;

/**
 * Created by xx on 2016/9/21.
 */
public interface FoodTypeContract {
    interface Presenter extends BasePresenter{
        @Override
        void start();

        @Override
        void onDestroy();
    }
    interface View{

        void showLoading();

        void hideLoading();

        void showLoadFail();

        void hideLoadFail();

        void showContent();

        void hideContent();

        void toastMessage(String message);

        void setContentData(List<FoodTypeGroup> list);

    }
}
