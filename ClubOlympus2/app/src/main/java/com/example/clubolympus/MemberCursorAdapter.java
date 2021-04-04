package com.example.clubolympus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class MemberCursorAdapter extends CursorAdapter {

    public MemberCursorAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.member_view_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idTextViewItem = (TextView) view.findViewById(R.id.id_view_item);
        TextView firstNameViewItem = (TextView) view.findViewById(R.id.first_name_view_item);
        TextView lastNameViewItem = (TextView) view.findViewById(R.id.last_name_view_item);
        TextView genderViewItem = (TextView) view.findViewById(R.id.gender_view_item);
        TextView kindOfSportViewItem = (TextView) view.findViewById(R.id.kind_of_sport_view_item);

        String _id = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry._ID)).toString();
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.LAST_NAME));
        String gender = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.GENDER)).toString();
        String kindOfSport = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.KIND_OF_SPORT));

        idTextViewItem.setText(_id);
        firstNameViewItem.setText(firstName);
        lastNameViewItem.setText(lastName);
        genderViewItem.setText(gender);
        kindOfSportViewItem.setText(kindOfSport);
    }
}
