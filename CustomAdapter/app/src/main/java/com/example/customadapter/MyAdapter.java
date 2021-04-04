package com.example.customadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<String> data;

    public MyAdapter(ArrayList<String> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.list_view_item, parent, false);

            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.bell);
        viewHolder.textView.setText(data.get(position));
        return convertView;
    }

    public static class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
