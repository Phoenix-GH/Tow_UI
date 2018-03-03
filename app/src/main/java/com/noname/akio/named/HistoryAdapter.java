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
import android.widget.TextView;

import com.noname.akio.named.clientAPI.Response.Storages.Storage;
import com.noname.akio.named.clientAPI.Response.Tows.Tow;
import com.noname.akio.named.clientAPI.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Tow> towList;
    HashMap<Integer, Storage> storageList;
    public HistoryAdapter() {

    }

    public HistoryAdapter(Context mContext,ArrayList<Tow> towList,HashMap<Integer, Storage> storageList) {

        this.mContext = mContext;
        this.towList = towList;
        this.storageList = storageList;
    }

    public int getCount() {
        if(towList!=null)
        return towList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return towList.get(i);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View history_row = inflater.inflate(R.layout.history_row, viewGroup, false);

        TextView history_row_title = (TextView) history_row.findViewById(R.id.history_row_title);
        TextView history_sub_title = (TextView) history_row.findViewById(R.id.history_sub_title);
        TextView history_row_text = (TextView) history_row.findViewById(R.id.history_row_text);
        TextView date_badge = (TextView) history_row.findViewById(R.id.date_badge);
        TextView date_subbadge = (TextView) history_row.findViewById(R.id.date_subbadge);
        TextView vehicle_badge = (TextView) history_row.findViewById(R.id.vehicle_badge);

        history_row_title.setTypeface(MainActivity.lato_bold);
        history_sub_title.setTypeface(MainActivity.lato_regular);
        history_row_text.setTypeface(MainActivity.lato_bold_italic);
        Tow item = towList.get(position);
        Storage storageItem = new Storage();
        if(storageList.get(item.getId())!=null)

         storageItem = storageList.get(item.getId());


        date_badge.setText(Util.getFormatDate(item.getTow_date()));
        date_subbadge.setText(item.getTowed_from());
        String title = String.format("%s %s(%s)",item.getMake(),item.getModel(),item.getColor());
        history_row_title.setText(title);
        history_sub_title.setText(storageItem.getAddress());
        history_row_text.setText(item.getNotes());
        vehicle_badge.setText(String.format("%d",item.getId()));
        SpannableStringBuilder sb = new SpannableStringBuilder("Notes:" + " " + history_row_text.getText());
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        final  int color = typedValue.data;

        ForegroundColorSpan fcs = new ForegroundColorSpan(color);

        sb.setSpan(new RelativeSizeSpan(1.15f), 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        history_row_text.setText(sb);
        return history_row;

    }
}
