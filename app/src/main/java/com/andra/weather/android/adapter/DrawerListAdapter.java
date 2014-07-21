package com.andra.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andra.weather.android.R;

public class DrawerListAdapter extends BaseAdapter {

    private final Context mContext;

    public DrawerListAdapter(Context context) {
        super();

        mContext = context;
    }
    @Override
    public int getCount() {

        // Two elements int the drawer's list
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            // inflate the layout for each item of listView
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.drawer_list_item, viewGroup, false);
            holder = new ViewHolder();

            holder.itemTextView  = (TextView)  convertView.findViewById(R.id.itemText);
            holder.itemImageView = (ImageView) convertView.findViewById(R.id.itemImage);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.itemTextView.setText
                (mContext.getResources().getStringArray(R.array.left_drawer_options)[position]);
        holder.itemImageView.setImageDrawable
                (position == 0 ? mContext.getResources().getDrawable(R.drawable.ic_drawer_today) :
                          mContext.getResources().getDrawable(R.drawable.ic_drawer_forecast));

        return convertView;
    }

    private static class ViewHolder {
        TextView itemTextView;
        ImageView itemImageView;
    }
}
