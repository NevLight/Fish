package com.lo.sisyphuser.fish.presenter;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.BaseUrl;
import com.lo.sisyphuser.fish.been.Food;
import com.lo.sisyphuser.fish.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.model.ITypeListModel;
import com.lo.sisyphuser.fish.model.TypeListModel;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.LoadSuccess;
import com.lo.sisyphuser.fish.view.TypeListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public class TypeListPresenter {
    private TypeListView typeListView;
    private ITypeListModel iTypeListModel;
    private String id;
    public TypeListPresenter(TypeListView typeListView,String id){
        this.typeListView=typeListView;
        this.id=id;
        this.iTypeListModel=new TypeListModel();
    }

    public void getList(int page){
        String url= BaseUrl.TYPE_LIST;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("cid",id);
        paraMap.put("key",BaseUrl.KEY);
        paraMap.put("pn",String.valueOf(10*page));
        paraMap.put("format","1");
        iTypeListModel.loadList(url, paraMap, new TypeListListener() {
            @Override
            public void loadSuccess(String json,final Map<String,String> hMap) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                        String pn=hMap.get("pn");
                        int totalNum=json.getJSONObject("result").getInt("totalNum");
                        if("0".equals(pn))
                            typeListView.getContentSuccess(list);
                        else
                            typeListView.addContentSuccess(list);
                        if(totalNum<=Integer.valueOf(hMap.get("pn"))+10)
                            typeListView.noMoreMessage();
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        String pn=hMap.get("pn");
                        if ("0".equals(pn))
                            typeListView.getContentFail(errorMessage);
                        else
                            typeListView.addContentError(errorMessage);
                    }
                });
            }

            @Override
            public void loadFail(Exception e,Map<String,String> hMap) {
                L.e(e.getMessage());
                String pn=hMap.get("pn");
                if ("0".equals(pn))
                    typeListView.getContentFail("网络连接异常");
                else
                    typeListView.addContentError("网络连接异常");
            }
        });
    }
    public void onDestroy(){
        iTypeListModel.cancle();
    }

    public interface TypeListListener{
        public void loadSuccess(String json,Map<String,String> hMap);
        public void loadFail(Exception e,Map<String,String> hMap);
    }
}
