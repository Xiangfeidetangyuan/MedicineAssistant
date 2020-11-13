package org.unboxing.medicineassistant.activity.setinform;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;


import org.unboxing.medicineassistant.dao.InformDao;
import org.unboxing.medicineassistant.R;
import org.unboxing.medicineassistant.entity.Inform;
import java.util.List;



public class InformAdapter extends ArrayAdapter<Inform>

    {
        private static final String TAG ="dao" ;
        private String userName; //用户名
        InformDao informdao = new InformDao(getContext());
        private int resourceID;
        private  List<Inform> object;

     public InformAdapter(Context context, int resource, List<Inform> objects,String username) {
        super(context, resource,objects);
        resourceID=resource;
        userName=username;
        object=objects;

    }
     public View getView(final int position , View  convertView, ViewGroup parent){

        final Inform inform=getItem(position);//获取当前项的inform实例
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);

        TextView inform_time = (TextView) view.findViewById(R.id.inform_item_time_textview) ;
        TextView infrom_context=(TextView)view.findViewById(R.id.infrom_context);


        Log.d("时间是",inform.getHour()+" ,"+inform.getMinute());
        inform_time.setText(inform.getHour()+":"+inform.getMinute());
        infrom_context.setText(inform.getTitle()+":"+inform.getContent());
        Switch statusbutton = (Switch)view.findViewById(R.id.switch1);
        if(inform.getStatus()==1)
            statusbutton.setChecked(true);
        else
            statusbutton.setChecked(false);
        //绑定状态
         statusbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked)
                     informdao.setInfromStatus(inform.getInformid(),1);
                 else
                     informdao.setInfromStatus(inform.getInformid(),0);
             }
         });

        ImageButton bn_delete = (ImageButton)view.findViewById(R.id.imageButton_delete);
        bn_delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    delInfrom(inform.getInformid());
                    object.remove(position);
                    notifyDataSetChanged();
                //  ((Activity)getContext()).finish();
                }
            });

        ImageButton bn_edit = (ImageButton)view.findViewById(R.id.imageButton_edit); //重新编辑提醒
        bn_edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String TAG="editinform";
                    Log.d(TAG,"");
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("inform",inform);
                    intent.putExtras(bundle);
                    intent.setClass(getContext(), EditInformActivity.class);
                    //启动
                    getContext().startActivity(intent);
                    ((Activity)getContext()).finish();

                }
            });

        return view;

    }

        private void delInfrom(long id) {

            informdao.delInformById(id);
            ContentResolver cr = getContext().getContentResolver();
            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
            int rows = cr.delete(deleteUri, null, null);

        }



    }

