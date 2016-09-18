package com.lo.sisyphuser.fish.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.been.FoodTypeGroup;
import com.lo.sisyphuser.fish.presenter.FoodTypePresenter;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.ToastUtil;
import com.lo.sisyphuser.fish.view.adapter.FoodTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodTypeActivity extends AppCompatActivity implements FoodTypeView, View.OnClickListener, ExpandableListView.OnChildClickListener {

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
    private FoodTypePresenter foodTypePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
        ButterKnife.bind(this);
        foodTypePresenter=new FoodTypePresenter(this);
        initView();
        initAdapter();
        initData();
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

    private void initData(){
        foodTypePresenter.getList();
    }

    @Override
    public void getContentSuccess(List<FoodTypeGroup> contentList) {
        elvContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.GONE);
        adapterList.clear();
        adapterList.addAll(contentList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getContentFail(String errorInfo) {
        elvContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.VISIBLE);
        ToastUtil.showToast(this,errorInfo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_fail://重新加载
                elvContent.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tvFail.setVisibility(View.GONE);
                foodTypePresenter.getList();
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
}
