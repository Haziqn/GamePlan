package com.example.a15017523.gameplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017523 on 2/8/2017.
 */

public class CustomAdapter extends ArrayAdapter {
        private ArrayList<OBJECT> object;
        private Context context;
        int id;
        DatabaseHelper databaseHelper;
        private TextView textViewCat, textViewTitle, textViewDesc, textViewDatetime, textViewReminder;

    public CustomAdapter(Context context, int resource, ArrayList<OBJECT> objects) {
        super(context, resource, objects);
        // Store the notes that is passed to this adapter
        id = resource;
        object= objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    public View getView(int i, View convertView, ViewGroup parent) {

        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.obj_row, parent, false);

        textViewCat = (TextView) rowView.findViewById(R.id.tvCat);
        textViewTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        textViewDesc = (TextView) rowView.findViewById(R.id.tvDesc);
        textViewDatetime = (TextView) rowView.findViewById(R.id.tvDatetime);
        textViewReminder = (TextView) rowView.findViewById(R.id.tvRem);
        OBJECT object1 = object.get(i);
        String catid = object1.getCategories().toString();
        databaseHelper = new DatabaseHelper(getContext());
        ArrayList <CATEGORIES> al_tv = databaseHelper.getCatById(catid);
        textViewCat.setText("Category: " + al_tv.get(0).getCATEGORIES().toString());
        textViewTitle.setText("Title: " + object1.getTitle().toString());
        textViewDesc.setText("Description: " + object1.getDescription());
        textViewDatetime.setText("Datetime: " + object1.getDatetime().toString());
        textViewReminder.setText("Reminder: " + object1.getReminder().toString());
        return rowView;
    }
}
