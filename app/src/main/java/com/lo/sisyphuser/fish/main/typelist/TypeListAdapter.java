package com.lo.sisyphuser.fish.main.typelist;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.source.been.Food;
import com.lo.sisyphuser.fish.util.Dip2PxUtil;
import com.lo.sisyphuser.fish.main.fooddetail.FoodDetailActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by xx on 2016/9/13.
 */
public class TypeListAdapter extends RecyclerView.Adapter<TypeListAdapter.Holder> {

    private static final int NOMAL = 0;
    private static final int LOAD_MORE = 1;
    public static final int LOADING=0;
    public static final int TRY_AGAIN=1;
    public static final int NO_MESSAGE=2;
    private Context context;
    private List<Food> list;
    private TypeListActivity.TypeListCallBack callBack;
    private int lastPosition = -1;
    private int type=0;
    private String typeId;

    public TypeListAdapter(Context context, List<Food> list,String typeId,TypeListActivity.TypeListCallBack callBack) {
        this.context = context;
        this.list = list;
        this.typeId=typeId;
        this.callBack=callBack;
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size())
            return LOAD_MORE;
        else
            return NOMAL;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NOMAL)
            return new Holder(LayoutInflater.from(context).inflate(R.layout.item_type_list, parent, false), viewType);
        else
            return new Holder(LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false), viewType);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        if(getItemViewType(position)==NOMAL) {
            holder.tvTitle.setText(list.get(position).getTitle());
            holder.tvImtro.setText(list.get(position).getImtro());
            holder.tvIngredients.setText(list.get(position).getIngredients());
            holder.tvBurden.setText(list.get(position).getBurden());
            setAnimation(holder.itemView, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.itemClidk(position);
                }
            });
        }else{
            switch (type){
                case LOADING:
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.tvTryAgain.setVisibility(View.GONE);
                    holder.tvNoMessage.setVisibility(View.GONE);
                    break;
                case TRY_AGAIN:
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tvTryAgain.setVisibility(View.VISIBLE);
                    holder.tvNoMessage.setVisibility(View.GONE);
                    break;
                case NO_MESSAGE:
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tvTryAgain.setVisibility(View.GONE);
                    holder.tvNoMessage.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    private void setAnimation(View animationView, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator anim = ObjectAnimator.ofFloat(animationView, "translationY", Dip2PxUtil.dip2px(context, 30), 0);
            anim.setDuration(500);
            anim.start();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvImtro, tvIngredients, tvBurden;
        private View itemView;
        private View progressBar,tvTryAgain,tvNoMessage;

        public Holder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            if(type==NOMAL) {
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tvImtro = (TextView) itemView.findViewById(R.id.tv_imtro);
                tvIngredients = (TextView) itemView.findViewById(R.id.tv_ingredients);
                tvBurden = (TextView) itemView.findViewById(R.id.tv_burden);
                this.itemView = itemView;
            }else{
                progressBar=itemView.findViewById(R.id.progressBar);
                tvTryAgain=itemView.findViewById(R.id.tv_try_again);
                tvNoMessage=itemView.findViewById(R.id.tv_no_message);
                tvTryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.loadMore();
                    }
                });
            }
        }
    }
}
