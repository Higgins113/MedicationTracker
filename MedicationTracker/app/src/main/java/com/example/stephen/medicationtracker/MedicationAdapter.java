package com.example.stephen.medicationtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stephen on 09-03-2017.
 */
public class MedicationAdapter extends ArrayAdapter<Medication> {
    public MedicationAdapter(Context context, ArrayList<Medication> medications) {
        super(context, 0,medications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Medication medication = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter, parent, false);
        }

        TextView medName = (TextView) convertView.findViewById(R.id.medName);
        //TextView medType = (TextView) convertView.findViewById(R.id.medType);
       // TextView ingestion = (TextView) convertView.findViewById(R.id.ingestion);
        TextView medQuantity = (TextView) convertView.findViewById(R.id.medQuantity);
      //  TextView medDosage = (TextView) convertView.findViewById(R.id.medDosage);
      //  TextView medStartDate = (TextView) convertView.findViewById(R.id.medStartDate);
       // TextView medDuration = (TextView) convertView.findViewById(R.id.medDuration);

        medName.setText(medication.getMedName() + " ");
       // medType.setText(medication.getMedType()+ " ");
       // ingestion.setText(medication.getIngestion()+ " ");
        medQuantity.setText(medication.getMedQuantity()+ " ");
       // medDosage.setText(String.valueOf(medication.getDosage())+ " ");
      //  medStartDate.setText(medication.getMedStartDate()+ " ");
       // medDuration.setText(medication.getDuration()+ " ");

        return convertView;
    }
}