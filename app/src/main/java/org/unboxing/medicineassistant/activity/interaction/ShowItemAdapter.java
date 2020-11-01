package org.unboxing.medicineassistant.activity.interaction;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.unboxing.medicineassistant.entity.LevelInfo;

import java.util.ArrayList;
import java.util.List;

public class ShowItemAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater inflater;
    private List<LevelInfo> displayItem;
    private List<LevelInfo> item;
    private Context context;
    private Filter mFilter;

    public static class ViewHolder{
        TextView Title;
        TextView Text;
    }

    public ShowItemAdapter(Context context, List<LevelInfo> data){
        inflater = LayoutInflater.from(context);
        this.displayItem = data;
        this.context = context;
        item = data;
    }

    @Override
    public int getCount() {
        return displayItem.size();
    }

    @Override
    public Object getItem(int position) {
        return displayItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, null);
            viewHolder = new ViewHolder();

            viewHolder.Title = convertView.findViewById(android.R.id.text1);
            viewHolder.Text = convertView.findViewById(android.R.id.text2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Title.setText(displayItem.get(position).getTitle());
        viewHolder.Text.setText(displayItem.get(position).getDesc());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }
    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter{
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<LevelInfo> list ;

            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = item;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (LevelInfo levelInfo :item){
                    if (levelInfo.getTitle().contains(charSequence)|| levelInfo.getDesc().contains(charSequence)){
                        list.add(levelInfo);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中
            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            displayItem = (List<LevelInfo>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }
}
