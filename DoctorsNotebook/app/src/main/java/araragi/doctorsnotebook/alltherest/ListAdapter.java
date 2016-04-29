package araragi.doctorsnotebook.alltherest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 29.01.16.
 */
public class ListAdapter extends android.support.v4.widget.CursorAdapter{



    public ListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);

    }





    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        View v = LayoutInflater.from(context).inflate(R.layout.my_list_layout, parent, false);
        return v;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {


            TextView listItem = (TextView) view.findViewById(R.id.list_item);
            TextView listItem2 = (TextView) view.findViewById(R.id.list_item_2);


            String msg = cursor.getString(DBAdapter.COL_FAMILY_NAME) + "\n" +
                    cursor.getString(DBAdapter.COL_DATA);

            listItem.setText(msg);

            String msg2 = cursor.getString(DBAdapter.COL_DIAGNOSIS) +
                    "\n" + cursor.getString(DBAdapter.COL_ANAESTHESIA);
            listItem2.setText(msg2);

            if (cursor.getString(DBAdapter.COL_PAYMENT).equals("net")) {
                listItem2.setTextColor(Color.RED);
            } else {
                listItem2.setTextColor(Color.BLACK);
            }


    }


}
