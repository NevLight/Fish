package com.lo.sisyphuser.fish.main.fooddetail;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.main.typelist.TypeListContract;
import com.lo.sisyphuser.fish.source.been.Food;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity implements  FoodDetailContract.ActivityView,View.OnClickListener {

    @Bind(R.id.tv_head_back)
    View tvBack;
    @Bind(R.id.tv_head_title)
    TextView tvTitle;
    @Bind(R.id.viewpage)
    ViewPager viewPager;

    private String id;
    private String name;
    private int position;
    private List<Food> foodList;
    private FoodDetailAdapter pagerAdapter;
    private FoodDetailContract.ActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        position=intent.getIntExtra("position",0);
        foodList=intent.getParcelableArrayListExtra("data");
        presenter = new FoodDetailActivityPresenter(this, (foodList.size()-1)/10,id);
        tvBack.setOnClickListener(this);
        tvTitle.setText(name);
    }

    private void initAdapter(){
        pagerAdapter=new FoodDetailAdapter(getSupportFragmentManager(),foodList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(foodList.get(position).getTitle());
                if (pagerAdapter.getCount() <= position + 2) {
                    presenter.start();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_head_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void addFood(List<Food> list) {
        pagerAdapter.setDataList(list);
    }
}
