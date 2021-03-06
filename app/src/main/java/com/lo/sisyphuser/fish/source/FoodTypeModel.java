package com.lo.sisyphuser.fish.source;

import com.lo.sisyphuser.fish.source.base.IFoodTypeModel;
import com.lo.sisyphuser.fish.main.foodtype.FoodTypePresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodTypeModel implements IFoodTypeModel {

    private RequestCall call;

    @Override
    public void loadList(String url, Map<String, String> paraMap, final FoodTypeListener foodTypeListener) {
        call = OkHttpUtils.post()
                .url(url)
                .params(paraMap)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                foodTypeListener.loadFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                foodTypeListener.loadSuccess(response);
            }
        });
    }

    @Override
    public void cancle() {
        if (call != null)
            call.cancel();
    }
}
