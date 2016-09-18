package com.lo.sisyphuser.fish.model;

import com.lo.sisyphuser.fish.presenter.TypeListPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by xx on 2016/9/13.
 */
public class TypeListModel implements ITypeListModel{

    private RequestCall call;

    @Override
    public void loadList(String url, final Map<String, String> paraMap, final TypeListPresenter.TypeListListener listener) {
        call= OkHttpUtils.post()
                .url(url)
                .params(paraMap)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.loadFail(e,paraMap);
            }

            @Override
            public void onResponse(String response, int id) {
                listener.loadSuccess(response,paraMap);
            }
        });
    }

    @Override
    public void cancle() {
        if(call!=null)
            call.cancel();
    }
}
