package com.noname.akio.named;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    List<String> listItem;
    Context mContext;

    public MenuAdapter()
    {

    }
    public MenuAdapter(Context mContext, List<String> listItem) {

        this.mContext = mContext;
        this.listItem = listItem;
    }

    public int getCount() {
        if(listItem!=null)
            return listItem.size();
        else
            return 0;
    }

    public Object getItem(int arg0) {
            return listItem.get(arg0);
        }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.menu_row, viewGroup, false);
        TextView name = (TextView) row.findViewById(R.id.menu_item_text);

        ImageView imageView = (ImageView) row.findViewById(R.id.menu_item_icon);
        String item = listItem.get(position);
        name.setText(item);
        if(position == 0)
        {
            imageView.setImageResource(R.drawable.workout_builder_icon);
        }
        else if(position ==1)
        {
            imageView.setImageResource(R.drawable.profile);
            ImageView borderBottom = (ImageView)row.findViewById(R.id.item_bottom_border);
            borderBottom.setVisibility(View.GONE);
        }
        return row;
    }
}
