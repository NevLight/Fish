package com.lo.sisyphuser.fish.main.foodtype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.main.typelist.TypeListActivity;
import com.lo.sisyphuser.fish.source.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodTypeActivity extends AppCompatActivity implements FoodTypeContract.View, View.OnClickListener, ExpandableListView.OnChildClickListener {

    @Bind(R.id.tv_head_back)
    View tvBack;
    @Bind(R.id.tv_head_title)
    TextView tvTitle;
    @Bind(R.id.elv_content)
    ExpandableListView elvContent;
    @Bind(R.id.progressBar)
    View progressBar;
    @Bind(R.id.tv_fail)
    View tvFail;

    private FoodTypeAdapter adapter;
    private List<FoodTypeGroup> adapterList;
    private FoodTypeContract.Presenter foodTypePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
        ButterKnife.bind(this);
        foodTypePresenter=new FoodTypePresenter(this);
        initView();
        initAdapter();
        foodTypePresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        foodTypePresenter.onDestroy();
    }

    private void initView() {
        tvBack.setVisibility(View.GONE);
        tvTitle.setText("菜品分类");
        tvFail.setOnClickListener(this);
        elvContent.setOnChildClickListener(this);
    }

    private void initAdapter(){
        adapterList=new ArrayList<>();
        adapter=new FoodTypeAdapter(this,adapterList);
        elvContent.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_fail://重新加载
                foodTypePresenter.start();
                break;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        Intent intent=new Intent(this,TypeListActivity.class);
        intent.putExtra("id",adapterList.get(i).getList().get(i1).getId());
        intent.putExtra("type",adapterList.get(i).getList().get(i1).getName());
        startActivity(intent);
        return true;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFail() {
        tvFail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadFail() {
        tvFail.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        elvContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        elvContent.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String message){
        ToastUtil.showToast(this,message);
    }

    @Override
    public void setContentData(List<FoodTypeGroup> list) {
        adapterList.clear();
        adapterList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}