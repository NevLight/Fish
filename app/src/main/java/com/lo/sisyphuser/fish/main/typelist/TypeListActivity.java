package com.lo.sisyphuser.fish.main.typelist;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.main.fooddetail.FoodDetailActivity;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TypeListActivity extends AppCompatActivity implements TypeListContract.View, View.OnClickListener {

    @Bind(R.id.tv_head_title)
    TextView tvTitle;
    @Bind(R.id.tv_head_back)
    View tvBack;
    @Bind(R.id.rlv_content)
    RecyclerView rlvContent;
    @Bind(R.id.progressBar)
    View progressBar;
    @Bind(R.id.tv_fail)
    View tvFail;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;

    private TypeListAdapter adapter;
    private ArrayList<Food> adapterList;

    private String id;
    private String type;
    private TypeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        presenter = new TypeListPresenter(this, id);
        tvTitle.setText(type);
        tvBack.setOnClickListener(this);
        tvFail.setOnClickListener(this);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start();
            }
        });
    }

    private void initAdapter() {
        adapterList = new ArrayList<>();
        adapter = new TypeListAdapter(this, adapterList,id, new TypeListCallBack() {
            @Override
            public void loadMore() {
                presenter.loadMore();
            }

            @Override
            public void itemClidk(int position) {
                Intent intent = new Intent(TypeListActivity.this, FoodDetailActivity.class);
                intent.putExtra("name", adapterList.get(position).getTitle());
                intent.putExtra("id",id);
                intent.putExtra("position",position);
                intent.putParcelableArrayListExtra("data",adapterList);
                startActivity(intent);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlvContent.setLayoutManager(layoutManager);
        rlvContent.setAdapter(adapter);
        rlvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int last=layoutManager.findLastVisibleItemPosition();
                int total=layoutManager.getItemCount();
                if(last>=total-1&&dy>0){
                    presenter.loadMore();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_back:
                onBackPressed();
                break;
            case R.id.tv_fail:
                presenter.start();
                break;
        }
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
        rlvContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContent() {
        rlvContent.setVisibility(View.GONE);
    }

    @Override
    public void toastMessage(String message) {
        ToastUtil.showToast(this,message);
    }

    @Override
    public void clearContentData(){
        adapterList.clear();
    }

    @Override
    public void addContentData(List<Food> list) {
        adapterList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void moreMessageState(int state) {
        adapter.setType(state);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshing() {
        if(refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }
    }

    interface TypeListCallBack{
        void loadMore();
        void itemClidk(int position);
    }
}
