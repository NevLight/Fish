package com.lo.sisyphuser.fish.main.fooddetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lo.sisyphuser.fish.MyApplication;
import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.source.been.FoodStep;
import com.lo.sisyphuser.fish.myview.NoScrollGridView;
import com.lo.sisyphuser.fish.util.BitmapLoadUtil;
import com.lo.sisyphuser.fish.util.Dip2PxUtil;
import com.lo.sisyphuser.fish.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FoodDetailFragment extends Fragment implements FoodDetailContract.FragmentView, View.OnClickListener {
    private static final String ID = "id";

    private String id;
    private Context context;
    private FoodDetailFragmentPresenter presenter;
    private TagsAdapter tagsAdapter;

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


    public FoodDetailFragment() {
    }
    public static FoodDetailFragment newInstance(String id) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID);
        }
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_detail, container, false);
        ButterKnife.bind(this,view);
        initView();
        presenter.start();
        return view;
    }

    private void initView(){
        presenter=new FoodDetailFragmentPresenter(id,this);
        int width=((MyApplication)context.getApplicationContext()).getWidth();
        int column=(width- Dip2PxUtil.dip2px(context,75+10+10+10))/Dip2PxUtil.dip2px(context,70);
        int horSpace=(width- Dip2PxUtil.dip2px(context,75+10+10+10)-Dip2PxUtil.dip2px(context,70)*column)/column;
        gvTags.setNumColumns(column);
        gvTags.setHorizontalSpacing(horSpace);
        tvFail.setOnClickListener(this);
    }

    private void setData(Food food){
        BitmapLoadUtil.loadNetBitmap(ImageLoader.getInstance(),ivFoodDetail,food.getAlbums().get(0),R.drawable.shape_pic_default);
        if(tagsAdapter==null){
            tagsAdapter=new TagsAdapter(context,food.getTags());
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
                View step= LayoutInflater.from(context).inflate(R.layout.item_step,null);
                ((TextView)step.findViewById(R.id.tv_step)).setText(foodStep.getStep());
                BitmapLoadUtil.loadNetBitmap(ImageLoader.getInstance(),(ImageView)step.findViewById(R.id.iv_step),foodStep.getImg(),R.drawable.shape_pic_default);
                llStep.addView(step);
            }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_fail:
                presenter.start();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null)
            presenter.onDestroy();
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
    public void showFail() {
        tvFail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFail() {
        tvFail.setVisibility(View.GONE);
    }

    @Override
    public void showDetail(Food food) {
        svContent.setVisibility(View.VISIBLE);
        setData(food);
    }

    @Override
    public void hideDetail() {
        svContent.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String message) {
        ToastUtil.showToast(context,message);
    }
}
