package com.example.listfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class MainList extends ListFragment {

    private String[] data = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, data);
        setListAdapter(arrayAdapter);
//        MyAdapter myAdapter = new MyAdapter(getActivity(), R.layout.list_fragment_row, data);
//        setListAdapter(myAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, null);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    public class MyAdapter extends ArrayAdapter<String>{

        Context mContext;
        String[] objects;

        MyAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);

            mContext = context;
            this.objects = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            return super.getView(position, convertView, parent);
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layoutInflater != null){
                View row = layoutInflater.inflate(R.layout.list_fragment_row, parent, false);
                TextView textView = (TextView)row.findViewById(R.id.text);
                ImageView imageView = (ImageView)row.findViewById(R.id.image);

                textView.setText(objects[position]);
                imageView.setImageResource(R.drawable.panda);
                return row;
            }
            return null;
        }
    }
}
