package org.unboxing.medicineassistant.activity.interaction;

import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.List;

public class ListAllAdapter extends ArrayAdapter<String> {
    public ListAllAdapter(Context context, int textViewResourceId,  List objects) {
        super(context,  textViewResourceId, objects);
    }
}
