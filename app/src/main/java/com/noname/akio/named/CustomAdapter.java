package com.noname.akio.named;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noname.akio.named.clientAPI.Response.Contracts.Contract;
import com.noname.akio.named.clientAPI.Response.Vehicles.VehicleList;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    List<String> listItem;
    Context mContext;
    ArrayList<Contract> contractList;
    ArrayList<VehicleList> vehicleList;
    public CustomAdapter()
    {

    }
    public CustomAdapter(Context mContext, ArrayList<Contract> contractList, ArrayList<VehicleList> vehicleList) {

        this.mContext = mContext;
        this.contractList = contractList;
        this.vehicleList = vehicleList;
    }

    public int getCount() {

        return contractList.size() + 1 + vehicleList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View apartment_row = inflater.inflate(R.layout.apartment_row, viewGroup, false);
        View vehicle_row = inflater.inflate(R.layout.vehicle_row, viewGroup, false);
        View title_row = inflater.inflate(R.layout.title_row, viewGroup, false);
        TextView apartment_row_title=(TextView)apartment_row.findViewById(R.id.apartment_row_title);
        TextView apartment_row_text=(TextView)apartment_row.findViewById(R.id.apartment_row_text);
        TextView badge_selected=(TextView)apartment_row.findViewById(R.id.badge_selected);

        TextView vehicle_badge=(TextView)vehicle_row.findViewById(R.id.vehicle_badge);
        TextView vehiclet_row_title=(TextView)vehicle_row.findViewById(R.id.vehicle_row_title);
        TextView vehiclet_row_text=(TextView)vehicle_row.findViewById(R.id.vehicle_row_text);
        TextView date_badge=(TextView)vehicle_row.findViewById(R.id.date_badge);

        TextView vehicle_header=(TextView)title_row.findViewById(R.id.title);

        if(position <contractList.size())
        {
            if(position == 0)
            {
                apartment_row_title.setTypeface(MainActivity.lato_semibold);
                apartment_row_text.setTypeface(MainActivity.lato_medium_italic);
                vehiclet_row_title.setTypeface(MainActivity.lato_bold);
                vehiclet_row_text.setTypeface(MainActivity.lato_medium_italic);
                vehicle_badge.setTypeface(MainActivity.lato_bold);
                date_badge.setTypeface(MainActivity.lato_regular);

                vehicle_header.setTypeface(MainActivity.lato_semibold);
            }
            if(position%2 == 1)
            {
                LinearLayout mainLayout=(LinearLayout)apartment_row.findViewById(R.id.main_layout);
                TypedValue typedValue = new TypedValue();
                mContext.getTheme().resolveAttribute(R.attr.panelBackground, typedValue, true);
                final  int color = typedValue.data;
                mainLayout.setBackgroundColor(color);
            }
            if(position == MainActivity.selectedIndex)
                badge_selected.setVisibility(View.VISIBLE);
            else
                badge_selected.setVisibility(View.GONE);
            Contract item = contractList.get(position);
            apartment_row_title.setText(item.getName());
            apartment_row_text.setText(item.getAddress());
            return apartment_row;
        }
        else if(position == contractList.size())
            return title_row;
        else {
            VehicleList item = vehicleList.get(position - 1 - contractList.size());
            vehicle_header.setText(item.getName());
            date_badge.setText(item.getCreated_at());

            vehiclet_row_title.setText(item.getName());
            vehiclet_row_text.setText(item.getStatus());
            vehicle_badge.setText(String.format("%d", item.getId()));
            //vehiclet_row_text
            return vehicle_row;
        }
    }
}
