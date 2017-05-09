package com.example.stephen.medicationtracker;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

/**
 * Created by Stephen on 04-05-2017.
 */
public class PickTimesCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> dosages = new ArrayList<String>();

    private Context context;
    public PickTimesCustomAdapter(ArrayList<String> list, Context context, ArrayList<String> dosages) {
        this.list = list;
        this.dosages = dosages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }


    @Override
    public long getItemId(int pos) {
       // return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_timeslistview, null);
        }

        //Handle TextView and display string from your list
        TextView pickTimes = (TextView)view.findViewById(R.id.textViewPickTimes);
        pickTimes.setText(list.get(position));
        //     listItemText.setText(list.get(position));

        ImageButton doseMinusButtonAdd = (ImageButton)view.findViewById(R.id.doseMinusButtonAdd);
        ImageButton dosePlusButtonAdd = (ImageButton)view.findViewById(R.id.dosePlusButtonAdd);
        final TextView listItemText = (TextView)view.findViewById(R.id.textViewDoseAdd);

        if(dosages.size()!=0) {
            listItemText.setText(dosages.get(position));
        }
        doseMinusButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dosagePlus;
                String getDosePlus = listItemText.getText().toString();
                dosagePlus = Integer.parseInt(getDosePlus);
                dosagePlus --;
                listItemText.setText(String.valueOf(dosagePlus));
            }
        });
        dosePlusButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dosagePlus;
                String getDosePlus = listItemText.getText().toString();
                dosagePlus = Integer.parseInt(getDosePlus);
                dosagePlus ++;
                listItemText.setText(String.valueOf(dosagePlus));
            }
        });


        return view;
    }


}
