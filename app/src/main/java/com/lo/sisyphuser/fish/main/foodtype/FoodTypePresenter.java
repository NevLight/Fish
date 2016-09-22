package com.lo.sisyphuser.fish.main.foodtype;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.main.BaseUrl;
import com.lo.sisyphuser.fish.source.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.source.FoodTypeModel;
import com.lo.sisyphuser.fish.source.base.IFoodTypeModel;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.LoadSuccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodTypePresenter implements FoodTypeContract.Presenter{
    private FoodTypeContract.View foodTypeView;
    private IFoodTypeModel iFoodTypeModel;
    public FoodTypePresenter(FoodTypeContract.View foodTypeView){
        this.foodTypeView=foodTypeView;
        iFoodTypeModel=new FoodTypeModel();
    }

    @Override
    public void start() {
        foodTypeView.hideContent();
        foodTypeView.hideLoadFail();
        foodTypeView.showLoading();
        final Map<String,String> paraMap=new HashMap<>();
        paraMap.put("key",BaseUrl.KEY);
        iFoodTypeModel.loadList(BaseUrl.FOODTYPE, paraMap, new IFoodTypeModel.FoodTypeListener() {
            @Override
            public void loadSuccess(String json) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        if(foodTypeView!=null) {
                            List<FoodTypeGroup> list = JSON.parseArray(json.getJSONArray("result").toString(), FoodTypeGroup.class);
                            foodTypeView.hideLoading();
                            foodTypeView.showContent();
                            foodTypeView.setContentData(list);
                        }
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        if(foodTypeView!=null) {
                            foodTypeView.hideLoading();
                            foodTypeView.showLoadFail();
                            foodTypeView.toastMessage(errorMessage);
                        }
                    }
                });
            }

            @Override
            public void loadFail(Exception e) {
                L.e("NET",e.getMessage());
                if (foodTypeView!=null) {
                    foodTypeView.hideLoading();
                    foodTypeView.showLoadFail();
                    foodTypeView.toastMessage("网络连接异常");
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        iFoodTypeModel.cancle();
        foodTypeView=null;
    }

}
