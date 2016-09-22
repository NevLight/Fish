package com.lo.sisyphuser.fish.main.foodtype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lo.sisyphuser.fish.R;
import com.lo.sisyphuser.fish.source.been.FoodTypeGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodTypeAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<FoodTypeGroup> list;
    public FoodTypeAdapter(Context context, List<FoodTypeGroup> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        List childList=list.get(i).getList();
        return childList==null?0:childList.size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param i 组位置
     * @param b 该组是展开状态还是伸缩状态
     * @param view 重用已有的视图对象
     * @param viewGroup 返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupHolder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_food_type_group,null);
            groupHolder=new GroupHolder(view);

            view.setTag(groupHolder);
        }else{
            groupHolder= (GroupHolder) view.getTag();
        }
        groupHolder.tvTypeName.setText(list.get(i).getName());
        if(b)
            groupHolder.ivArrow.setRotation(90f);
        else
            groupHolder.ivArrow.setRotation(0);
        return view;
    }
    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param i 组位置
     * @param i1 子元素位置
     * @param b 子元素是否处于组中的最后一个
     * @param view 重用已有的视图(View)对象
     * @param viewGroup 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder childHolder;
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_food_type_child,null);
            childHolder=new ChildHolder(view);
            view.setTag(childHolder);
        }else{
            childHolder= (ChildHolder) view.getTag();
        }
        childHolder.tvName.setText(list.get(i).getList().get(i1).getName());
        if(b){
            childHolder.childLine.setVisibility(View.GONE);
            childHolder.groupLine.setVisibility(View.VISIBLE);
        }else{
            childHolder.childLine.setVisibility(View.VISIBLE);
            childHolder.groupLine.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupHolder{
        @Bind(R.id.tv_type_name)
        TextView tvTypeName;
        @Bind(R.id.iv_arrow)
        View ivArrow;
        public GroupHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    class ChildHolder{
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.view_child)
        View childLine;
        @Bind(R.id.view_group)
        View groupLine;
        public ChildHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
