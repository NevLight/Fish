package com.lo.sisyphuser.fish.source.base;

import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public interface ITypeListModel {
    void loadList(String url, Map<String,String> paraMap, TypeListListener listener);
    void cancle();

    interface TypeListListener{
        void loadSuccess(String json,Map<String,String> hMap);
        void loadFail(Exception e,Map<String,String> hMap);
    }
}
