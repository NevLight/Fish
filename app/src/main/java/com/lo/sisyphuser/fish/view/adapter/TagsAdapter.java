package com.lo.sisyphuser.fish.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;

/**
 * Created by xx on 2016/9/14.
 */
public class TagsAdapter extends BaseAdapter {
    private Context context;
    private String[] data;
    public TagsAdapter(Context context,String s){
        this.context=context;
        if(!TextUtils.isEmpty(s)){
            data=s.split(";");
        }
    }
    @Override
    public int getCount() {
        return data==null?0:data.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public void setTags(String s){
        if(!TextUtils.isEmpty(s)){
            data=s.split(";");
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_tags,null);
        ((TextView)(view.findViewById(R.id.tv_tag))).setText(data[i]);
        return view;
    }
}
