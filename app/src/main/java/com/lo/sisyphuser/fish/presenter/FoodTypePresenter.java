package com.lo.sisyphuser.fish.presenter;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.BaseUrl;
import com.lo.sisyphuser.fish.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.model.FoodTypeModel;
import com.lo.sisyphuser.fish.model.IFoodTypeModel;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.LoadSuccess;
import com.lo.sisyphuser.fish.util.ToastUtil;
import com.lo.sisyphuser.fish.view.FoodTypeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodTypePresenter {
    private FoodTypeView foodTypeView;
    private IFoodTypeModel iFoodTypeModel;
    public FoodTypePresenter(FoodTypeView foodTypeView){
        this.foodTypeView=foodTypeView;
        iFoodTypeModel=new FoodTypeModel();
    }
    /**
     * 获取数据
     */
    public void getList(){
        final Map<String,String> paraMap=new HashMap<>();
        paraMap.put("key",BaseUrl.KEY);
        iFoodTypeModel.loadList(BaseUrl.FOODTYPE, paraMap, new FoodTypeListener() {
            @Override
            public void loadSuccess(String json) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        List<FoodTypeGroup> list = JSON.parseArray(json.getJSONArray("result").toString(), FoodTypeGroup.class);
                        foodTypeView.getContentSuccess(list);
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        foodTypeView.getContentFail(errorMessage);
                    }
                });
            }

            @Override
            public void loadFail(Exception e) {
                L.e("NET",e.getMessage());
                foodTypeView.getContentFail("网络连接异常");
            }
        });
    }

    public void onDestroy(){
        iFoodTypeModel.cancle();
    }

    public interface FoodTypeListener{
        public void loadSuccess(String json);
        public void loadFail(Exception e);
    }

}
