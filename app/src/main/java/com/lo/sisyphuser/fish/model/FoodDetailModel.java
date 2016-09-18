package com.lo.sisyphuser.fish.model;

import com.lo.sisyphuser.fish.presenter.FoodDetailPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xx on 2016/9/14.
 */
public class FoodDetailModel implements IFoodDetailModel {

    private RequestCall call;

    @Override
    public void loadList(String url, Map<String, String> paraMap, final FoodDetailPresenter.FoodDetailListener foodDetailListener) {
        call= OkHttpUtils.post()
                .url(url)
                .params(paraMap)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                foodDetailListener.loadFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                foodDetailListener.loadSuccess(response);
            }
        });
    }

    @Override
    public void cancle() {
        if(call!=null)
            call.cancel();
    }
}
