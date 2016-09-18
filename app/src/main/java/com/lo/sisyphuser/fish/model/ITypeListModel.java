package com.lo.sisyphuser.fish.model;

import com.lo.sisyphuser.fish.presenter.TypeListPresenter;

import java.util.Map;

/**
 * Created by xx on 2016/9/13.
 */
public interface ITypeListModel {
    public void loadList(String url, Map<String,String> paraMap, TypeListPresenter.TypeListListener listener);
    public void cancle();
}
