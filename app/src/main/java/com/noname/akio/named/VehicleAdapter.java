package com.noname.akio.named;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.noname.akio.named.clientAPI.Response.Storages.Storage;
import com.noname.akio.named.clientAPI.Response.Tows.Tow;
import com.noname.akio.named.clientAPI.Response.Vehicles.Vehicle;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VehicleAdapter extends BaseAdapter {

    List<String> listItem;
    Context mContext;
    ArrayList<Vehicle> vehicleList;

    public VehicleAdapter() {

    }

    public VehicleAdapter(Context mContext, ArrayList<Vehicle> vehicleList) {

        this.mContext = mContext;
        this.vehicleList = vehicleList;

    }

    public int getCount() {
        if (vehicleList != null)
            return vehicleList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return vehicleList.get(i);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vehicle_row = inflater.inflate(R.layout.vehicle_info_row, viewGroup, false);

        TextView make_model = (TextView) vehicle_row.findViewById(R.id.make_model_value);
        TextView color = (TextView) vehicle_row.findViewById(R.id.color_value);
        TextView license_plate = (TextView) vehicle_row.findViewById(R.id.license_plate_value);
        TextView[] array = new TextView[7];
        array[0] = (TextView) vehicle_row.findViewById(R.id.button1);
        array[1] = (TextView) vehicle_row.findViewById(R.id.button5);
        array[2] = (TextView) vehicle_row.findViewById(R.id.button2);
        array[3] = (TextView) vehicle_row.findViewById(R.id.button6);
        array[4] = (TextView) vehicle_row.findViewById(R.id.button3);
        array[5] = (TextView) vehicle_row.findViewById(R.id.button7);
        array[6] = (TextView) vehicle_row.findViewById(R.id.button4);

        Vehicle item;
        item = vehicleList.get(position);
        make_model.setText(item.getMake() + ", " + item.getModel());
        color.setText(item.getColor());
        license_plate.setText(item.getLicense());


        for (TextView view : array)
            view.setVisibility(View.GONE);
        if (item.getReasons().length() > 0) {
            String[] reasonArray = item.getReasons().split("\\|");
            for (int i = 0; i < reasonArray.length; i++) {
                array[i].setVisibility(View.VISIBLE);
                array[i].setText(reasonArray[i]);
            }
        }
        return vehicle_row;

    }
}
