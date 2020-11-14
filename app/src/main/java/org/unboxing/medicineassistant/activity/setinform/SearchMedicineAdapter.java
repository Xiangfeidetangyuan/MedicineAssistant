package org.unboxing.medicineassistant.activity.setinform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import org.unboxing.medicineassistant.activity.interaction.ShowItemAdapter;
import org.unboxing.medicineassistant.dao.MedicineDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.Inform;
import org.unboxing.medicineassistant.entity.InteractionLevelInfo;
import org.unboxing.medicineassistant.entity.medicine;

import java.util.ArrayList;
import java.util.List;

public class SearchMedicineAdapter extends ArrayAdapter<medicine> implements Filterable {
    private final int resourceId;
    private String userName; //用户名
    private  Filter mFilter; //过滤器
    private  List<medicine>  filterlist; //过滤的list
    private  List<medicine>  filteredlist; //过滤后的list

    public SearchMedicineAdapter(Context context, int textViewResourceId, List<medicine> objects, String username) {
        super(context,  textViewResourceId, objects);
        resourceId = textViewResourceId;
        userName=username;
        filterlist=objects;
        filteredlist=objects;
    }
    @Override
    public int getCount() {
        return filteredlist.size();
    }

    @Override
    public medicine getItem(int position) {
        return filteredlist.get(position);
    }
    public View getView(final  int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        TextView medicienName = (TextView) view.findViewById(R.id.medicinenametextView);//获取该布局内的文本视图
        Log.d("size",filteredlist.size()+" "+position);

        medicienName.setText(getItem(position).getName());//为文本视图设置文本内容

        medicienName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String TAG="选择了";
                Log.d(TAG,getItem(position).getName());
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("Medicine",getItem(position));
                intent.putExtras(bundle);
                intent.putExtra("userName",userName);
                //启动
                intent.setClass(getContext(), SetInformActivity.class);
                getContext().startActivity(intent);
                ((Activity)getContext()).finish();

            }
        });
        return view;
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
            List<medicine> list ;
            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list=filterlist;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for ( medicine  item: filterlist){
                    if (item.getName().contains(charSequence)){
                        list.add(item);
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
            filteredlist=(List<medicine>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }
}
