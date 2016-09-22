package com.lo.sisyphuser.fish.main.typelist;

import com.lo.sisyphuser.fish.main.BasePresenter;
import com.lo.sisyphuser.fish.source.been.Food;

import java.util.List;

/**
 * Created by xx on 2016/9/21.
 */
public interface TypeListContract {
    interface Presenter extends BasePresenter{
        @Override
        void start();

        @Override
        void onDestroy();

        void loadMore();
    }

    interface View{
        void showLoading();

        void hideLoading();

        void showLoadFail();

        void hideLoadFail();

        void showContent();

        void hideContent();

        void toastMessage(String message);

        void clearContentData();

        void addContentData(List<Food> list);

        void moreMessageState(int state);

        void hideRefreshing();
    }

}
