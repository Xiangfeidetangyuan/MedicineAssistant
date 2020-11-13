package org.unboxing.medicineassistant.activity.setinform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.unboxing.medicineassistant.dao.MedicineDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.medicine;

import java.util.List;

public class SearchMedicineAdapter extends ArrayAdapter<String> {
    private final int resourceId;
    private String userName; //用户名
    public SearchMedicineAdapter(Context context, int textViewResourceId, List objects, String username) {
        super(context,  textViewResourceId, objects);
        resourceId = textViewResourceId;
        userName=username;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
         final String medicinename =  getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        TextView medicienName = (TextView) view.findViewById(R.id.medicinenametextView);//获取该布局内的文本视图

        medicienName.setText(medicinename);//为文本视图设置文本内容
        medicienName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String TAG="选择了";
                Log.d(TAG,medicinename);
                Intent intent=new Intent();
                MedicineDao me = new MedicineDao(getContext());
                medicine medicinedemo = me.getmedicinebyname(medicinename);
                Log.d(TAG,medicinedemo.getName());
                Bundle bundle=new Bundle();
                bundle.putSerializable("Medicine",medicinedemo);
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
}
