package com.lo.sisyphuser.fish.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lo.sisyphuser.fish.MyApplication;
import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.been.Food;
import com.lo.sisyphuser.fish.been.FoodStep;
import com.lo.sisyphuser.fish.myview.NoScrollGridView;
import com.lo.sisyphuser.fish.presenter.FoodDetailPresenter;
import com.lo.sisyphuser.fish.util.BitmapLoadUtil;
import com.lo.sisyphuser.fish.util.Dip2PxUtil;
import com.lo.sisyphuser.fish.util.ToastUtil;
import com.lo.sisyphuser.fish.view.adapter.TagsAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity implements FoodDetailView,View.OnClickListener {

    @Bind(R.id.tv_head_back)
    View tvBack;
    @Bind(R.id.tv_head_title)
    TextView tvTitle;
    @Bind(R.id.iv_food_detail_pic)
    ImageView ivFoodDetail;
    @Bind(R.id.gv_tags)
    NoScrollGridView gvTags;
    @Bind(R.id.tv_imtro)
    TextView tvImtro;
    @Bind(R.id.tv_ingredients)
    TextView tvIngredients;
    @Bind(R.id.tv_burden)
    TextView tvBurden;
    @Bind(R.id.ll_step)
    LinearLayout llStep;
    @Bind(R.id.sv_content)
    View svContent;
    @Bind(R.id.progressBar)
    View progressBar;
    @Bind(R.id.tv_fail)
    View tvFail;

    private String id;
    private String name;

    private TagsAdapter tagsAdapter;
    private FoodDetailPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }

    private void initView() {
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        name=intent.getStringExtra("name");
        presenter=new FoodDetailPresenter(id,this);
        int width=((MyApplication)getApplication()).getWidth();
        int column=(width- Dip2PxUtil.dip2px(this,75+10+10+10))/Dip2PxUtil.dip2px(this,70);
        int horSpace=(width- Dip2PxUtil.dip2px(this,75+10+10+10)-Dip2PxUtil.dip2px(this,70)*column)/column;
        gvTags.setNumColumns(column);
        gvTags.setHorizontalSpacing(horSpace);
        tvBack.setOnClickListener(this);
        tvTitle.setText(name);
        tvFail.setOnClickListener(this);
    }

    private void initData(){
        svContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvFail.setVisibility(View.GONE);
        presenter.getDetail();
    }
    @Override
    public void getContentSuccess(Food food){
        svContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.GONE);
        LoadDetail(food);
    }
    @Override
    public void getContentFail(String errorMessage){
        svContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.VISIBLE);
        ToastUtil.showToast(this,errorMessage);
    }

    private void LoadDetail(Food food){
        BitmapLoadUtil.loadNetBitmap(ImageLoader.getInstance(),ivFoodDetail,food.getAlbums().get(0),R.drawable.shape_pic_default);
        if(tagsAdapter==null){
            tagsAdapter=new TagsAdapter(this,food.getTags());
            gvTags.setAdapter(tagsAdapter);
        }else{
            tagsAdapter.setTags(food.getTags());
            tagsAdapter.notifyDataSetChanged();
        }
        tvIngredients.setText(food.getIngredients());
        tvBurden.setText(food.getBurden());
        tvImtro.setText(food.getImtro());
        List<FoodStep> stepList=food.getSteps();
        if(stepList!=null)
            for(FoodStep foodStep:stepList){
                View step= LayoutInflater.from(this).inflate(R.layout.item_step,null);
                ((TextView)step.findViewById(R.id.tv_step)).setText(foodStep.getStep());
                BitmapLoadUtil.loadNetBitmap(ImageLoader.getInstance(),(ImageView)step.findViewById(R.id.iv_step),foodStep.getImg(),R.drawable.shape_pic_default);
                llStep.addView(step);
            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_head_back:
                onBackPressed();
                break;
            case R.id.tv_fail:
                svContent.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tvFail.setVisibility(View.GONE);
                presenter.getDetail();
                break;
        }
    }
}
