package com.lo.sisyphuser.fish.main.typelist;

import com.alibaba.fastjson.JSON;
import com.lo.sisyphuser.fish.main.BaseUrl;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.source.base.ITypeListModel;
import com.lo.sisyphuser.fish.source.TypeListModel;
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
public class TypeListPresenter implements TypeListContract.Presenter {
    private TypeListContract.View typeListView;
    private ITypeListModel iTypeListModel;
    private String id;
    private int page;
    private boolean loadMore=false;
    public TypeListPresenter (TypeListContract.View typeListView,String id){
        this.typeListView=typeListView;
        this.id=id;
        this.iTypeListModel=new TypeListModel();
    }


    @Override
    public void start() {
        iTypeListModel.cancle();
        page=0;
        loadMore=false;
        typeListView.hideContent();
        typeListView.hideLoadFail();
        typeListView.showLoading();
        String url= BaseUrl.TYPE_LIST;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("cid",id);
        paraMap.put("key",BaseUrl.KEY);
        paraMap.put("pn",String.valueOf(10*page));
        paraMap.put("format","1");
        iTypeListModel.loadList(url, paraMap, new ITypeListModel.TypeListListener() {
            @Override
            public void loadSuccess(String json,final Map<String,String> hMap) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        if(typeListView!=null) {
                            typeListView.hideLoading();
                            typeListView.showContent();
                            List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                            typeListView.clearContentData();
                            typeListView.addContentData(list);
                            int totalNum = json.getJSONObject("result").getInt("totalNum");
                            if (totalNum <= Integer.valueOf(hMap.get("pn")) + 10)
                                typeListView.moreMessageState(TypeListAdapter.NO_MESSAGE);
                            else {
                                loadMore = true;
                                typeListView.moreMessageState(TypeListAdapter.LOADING);
                            }
                        }
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        if(typeListView!=null) {
                            typeListView.hideLoading();
                            typeListView.showLoadFail();
                            typeListView.toastMessage(errorMessage);
                        }
                    }
                });
            }

            @Override
            public void loadFail(Exception e,Map<String,String> hMap) {
                if(typeListView!=null) {
                    typeListView.hideLoading();
                    typeListView.showLoadFail();
                    typeListView.toastMessage("网络连接异常");
                }
            }
        });
    }

    @Override
    public void loadMore() {
        if(!loadMore)
            return;
        page++;
        loadMore=false;
        String url= BaseUrl.TYPE_LIST;
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put("cid",id);
        paraMap.put("key",BaseUrl.KEY);
        paraMap.put("pn",String.valueOf(10*page));
        paraMap.put("format","1");
        iTypeListModel.loadList(url, paraMap, new ITypeListModel.TypeListListener() {
            @Override
            public void loadSuccess(String json,final Map<String,String> hMap) {
                LoadSuccess.onSuccess(json, new LoadSuccess.LoadSuccessCallBack() {
                    @Override
                    public void code200(JSONObject json) throws JSONException {
                        if (typeListView!=null) {
                            typeListView.hideRefreshing();
                            List<Food> list = JSON.parseArray(json.getJSONObject("result").getJSONArray("data").toString(), Food.class);
                            int totalNum = json.getJSONObject("result").getInt("totalNum");
                            typeListView.addContentData(list);
                            if (totalNum <= Integer.valueOf(hMap.get("pn")) + 10)
                                typeListView.moreMessageState(TypeListAdapter.NO_MESSAGE);
                            else
                                loadMore=true;
                        }
                    }
                    @Override
                    public void codeOther(String errorMessage) {
                        if (typeListView != null) {
                            typeListView.moreMessageState(TypeListAdapter.TRY_AGAIN);
                            page--;
                            typeListView.toastMessage(errorMessage);
                        }
                    }
                });
            }

            @Override
            public void loadFail(Exception e,Map<String,String> hMap) {
                if (typeListView!=null) {
                    typeListView.moreMessageState(TypeListAdapter.TRY_AGAIN);
                    page--;
                    typeListView.toastMessage("网络连接异常");
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        iTypeListModel.cancle();
        typeListView=null;
    }

}
