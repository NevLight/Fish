package com.lo.sisyphuser.fish.presenter;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.BaseUrl;
import com.lo.sisyphuser.fish.been.Food;
import com.lo.sisyphuser.fish.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.model.FoodDetailModel;
import com.lo.sisyphuser.fish.model.IFoodDetailModel;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.LoadSuccess;
import com.lo.sisyphuser.fish.view.FoodDetailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/14.
 */
public class FoodDetailPresenter {
    private FoodDetailView foodDetailView;
    private IFoodDetailModel iFoodDetailModel;
    private String id;
    public FoodDetailPresenter(String id,FoodDetailView foodDetailView){
        this.id=id;
        this.foodDetailView=foodDetailView;
        this.iFoodDetailModel=new FoodDetailModel();
    }

    public void getDetail(){
        String url= BaseUrl.FOOD_DETAIL;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("id",id);
        paraMap.put("key",BaseUrl.KEY);
        iFoodDetailModel.loadList(url, paraMap, new FoodDetailListener() {
            @Override
            public void loadSuccess(String json) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                        foodDetailView.getContentSuccess(list.get(0));
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        foodDetailView.getContentFail(errorMessage);
                    }
                });
            }

            @Override
            public void loadFail(Exception e) {
                L.e("NET",e.getMessage());
                foodDetailView.getContentFail("网络连接异常");
            }
        });
    }

    public void onDestory(){
        iFoodDetailModel.cancle();
    }

    public interface FoodDetailListener{
        public void loadSuccess(String json);
        public void loadFail(Exception e);
    }
}
