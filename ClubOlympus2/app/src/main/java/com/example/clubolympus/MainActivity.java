package com.example.clubolympus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clubolympus.data.ClubOlympusContract;
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

import com.example.clubolympus.data.OlympusContentProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Member;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton floatingActionButton;
    ListView listView;
    MemberCursorAdapter memberCursorAdapter;
    private static final int MEMBER_LOADER = 111;//уникальная константа для LoaderManager , чтобы он понял какой именно CursorLoader ему необходимо запустить

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        listView = (ListView)findViewById(R.id.listView);

        memberCursorAdapter = new MemberCursorAdapter(getApplicationContext(), null, false);
        listView.setAdapter(memberCursorAdapter);
        //getSupportLoaderManager().initLoader(MEMBER_LOADER, null, this);//deprecated
        LoaderManager.getInstance(this).initLoader(MEMBER_LOADER, null, this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewMemberActivity.class);

                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), AddNewMemberActivity.class);

                Uri uri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, l);

                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projections = {
                MemberEntry._ID,
                MemberEntry.FIRST_NAME,
                MemberEntry.LAST_NAME,
                MemberEntry.GENDER,
                MemberEntry.KIND_OF_SPORT
        };

        return new CursorLoader(getApplicationContext(), MemberEntry.CONTENT_URI, projections, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}
