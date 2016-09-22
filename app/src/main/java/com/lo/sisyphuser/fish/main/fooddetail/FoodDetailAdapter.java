package com.lo.sisyphuser.fish.main.fooddetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.main.fooddetail.FoodDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 2016/9/18.
 */
public class FoodDetailAdapter extends FragmentStatePagerAdapter {
    private List<Food> dataList;
    public FoodDetailAdapter(FragmentManager fm,List<Food> dataList) {
        super(fm);
        this.dataList=dataList;
    }

    @Override
    public Fragment getItem(int position) {
        return FoodDetailFragment.newInstance(dataList.get(position).getId());
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
    public void setDataList(List<Food> data){
        dataList.addAll(data);
        notifyDataSetChanged();
    }
}
