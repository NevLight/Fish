package com.lo.sisyphuser.fish.view;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.been.Food;
import com.lo.sisyphuser.fish.presenter.TypeListPresenter;
import com.lo.sisyphuser.fish.util.L;
import com.lo.sisyphuser.fish.util.ToastUtil;
import com.lo.sisyphuser.fish.view.adapter.TypeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TypeListActivity extends AppCompatActivity implements TypeListView, View.OnClickListener {

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
    private List<Food> adapterList;

    private String id;
    private String type;
    private TypeListPresenter presenter;
    private int page = 0;
    private boolean loadMore=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initData();
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
                page=0;
                presenter.getList(page);
                loadMore=false;
            }
        });
    }

    private void initAdapter() {
        adapterList = new ArrayList<>();
        adapter = new TypeListAdapter(this, adapterList, new TypeListCallBack() {
            @Override
            public void loadMore() {
                loadMore=false;
                page++;
                presenter.getList(page);
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
                if(last>=total-1&&dy>0&&loadMore){
                    loadMore=false;
                    page++;
                    presenter.getList(page);
                }
            }
        });
    }

    private void initData() {
        rlvContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvFail.setVisibility(View.GONE);
        presenter.getList(page);
    }

    @Override
    public void getContentSuccess(List<Food> contentList) {
        rlvContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.GONE);
        adapterList.clear();
        adapterList.addAll(contentList);
        adapter.notifyDataSetChanged();
        loadMore=true;
        if(refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }

    }

    @Override
    public void addContentSuccess(List<Food> contentList) {
        adapterList.addAll(contentList);
        adapter.notifyDataSetChanged();
        loadMore=true;
    }

    @Override
    public void getContentFail(String errorInfo) {
        rlvContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvFail.setVisibility(View.VISIBLE);
        ToastUtil.showToast(this, errorInfo);
        loadMore=false;
    }

    @Override
    public void addContentError(String errorInfo) {
        if (page > 0)
            page--;
        ToastUtil.showToast(this, errorInfo);
        loadMore=false;
        adapter.setType(TypeListAdapter.TRY_AGAIN);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noMoreMessage() {
        loadMore=false;
        adapter.setType(TypeListAdapter.NO_MESSAGE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_back:
                onBackPressed();
                break;
            case R.id.tv_fail:
                rlvContent.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tvFail.setVisibility(View.GONE);
                presenter.getList(0);
                break;
        }
    }

    public interface TypeListCallBack {
        public void loadMore();
    }
}
