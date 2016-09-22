package com.lo.sisyphuser.fish.main.fooddetail;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.main.BaseUrl;
import com.lo.sisyphuser.fish.source.TypeListModel;
import com.lo.sisyphuser.fish.source.base.ITypeListModel;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.util.LoadSuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/22.
 */
public class FoodDetailActivityPresenter implements FoodDetailContract.ActivityPresenter{

    private FoodDetailContract.ActivityView view;
    private int page;
    private String id;
    private ITypeListModel model;
    private boolean isLoading=false;

    public FoodDetailActivityPresenter(FoodDetailContract.ActivityView view,int page,String id){
        this.view=view;
        this.page=page;
        this.id=id;
        this.model=new TypeListModel();
    }

    @Override
    public void start() {
        if(isLoading)
            return;
        page++;
        isLoading=true;
        String url= BaseUrl.TYPE_LIST;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("cid",id);
        paraMap.put("key",BaseUrl.KEY);
        paraMap.put("pn",String.valueOf(10*page));
        paraMap.put("format","1");
        model.loadList(url, paraMap, new ITypeListModel.TypeListListener() {
            @Override
            public void loadSuccess(String json, Map<String, String> hMap) {
                if(view!=null){
                    LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                        @Override
                        public void code200(JSONObject json) throws JSONException {
                            List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                            view.addFood(list);
                        }

                        @Override
                        public void codeOther(String errorMessage) {
                            page--;
                        }
                    });
                    isLoading=false;
                }
            }

            @Override
            public void loadFail(Exception e, Map<String, String> hMap) {
                if(view!=null){
                    page--;
                    isLoading=false;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        model.cancle();
        view=null;
    }
}
