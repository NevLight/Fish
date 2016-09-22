package com.lo.sisyphuser.fish.main.fooddetail;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.main.BaseUrl;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.source.FoodDetailModel;
import com.lo.sisyphuser.fish.source.base.IFoodDetailModel;
import com.lo.sisyphuser.fish.util.LoadSuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/14.
 */
public class FoodDetailFragmentPresenter implements FoodDetailContract.FragmentPresenter{
    private FoodDetailContract.FragmentView foodDetailView;
    private IFoodDetailModel iFoodDetailModel;
    private String id;
    public FoodDetailFragmentPresenter(String id, FoodDetailContract.FragmentView foodDetailView){
        this.id=id;
        this.foodDetailView=foodDetailView;
        this.iFoodDetailModel=new FoodDetailModel();
    }

    @Override
    public void onDestroy() {
        iFoodDetailModel.cancle();
        foodDetailView = null;
    }

    @Override
    public void start() {
        foodDetailView.showLoading();
        foodDetailView.hideDetail();
        foodDetailView.hideFail();
        String url= BaseUrl.FOOD_DETAIL;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("id",id);
        paraMap.put("key",BaseUrl.KEY);
        iFoodDetailModel.loadList(url, paraMap, new FoodDetailListener() {
            @Override
            public void loadSuccess(String json) {
                if(foodDetailView!=null) {
                    LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                        @Override
                        public void code200(JSONObject json) throws JSONException {
                            List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                            foodDetailView.showDetail(list.get(0));
                            foodDetailView.hideLoading();
                        }

                        @Override
                        public void codeOther(String errorMessage) {
                            foodDetailView.showFail();
                            foodDetailView.hideLoading();
                            foodDetailView.toastMessage(errorMessage);
                        }

                    });
                }
            }

            @Override
            public void loadFail(Exception e) {
                if(foodDetailView!=null) {
                    foodDetailView.showFail();
                    foodDetailView.hideLoading();
                    foodDetailView.toastMessage("网络连接异常");
                }
            }
        });
    }

    public interface FoodDetailListener{
        void loadSuccess(String json);
        void loadFail(Exception e);
    }
}
