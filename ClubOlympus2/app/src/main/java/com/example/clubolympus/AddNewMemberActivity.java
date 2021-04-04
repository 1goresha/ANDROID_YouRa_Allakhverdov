package com.example.clubolympus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clubolympus.model.ClubMember;
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class AddNewMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText firstNameEditText, lastNameEditText, kindOfSportEditText;
    Spinner genderSpinner;
    ArrayAdapter arrayAdapter;
    ClubMember clubMember;
    public static final int ADD_NEW_MEMBER_LOADER = 222;
    public Uri uriReceived;
    public int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        Intent intent = getIntent();
        uriReceived = intent.getData();
        if (uriReceived == null) {
            setTitle("Add new club member");
        } else {
            setTitle("Edit the club member ");
            LoaderManager.getInstance(this).initLoader(ADD_NEW_MEMBER_LOADER, null, this);

        }

        clubMember = new ClubMember();

        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        kindOfSportEditText = (EditText) findViewById(R.id.kind_of_sport_edit_text);
        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gender_array, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getResources().getStringArray(R.array.gender_array)[position].equals("male")) {
                    clubMember.setGenderIndex(MemberEntry.GENDER_MALE);
                    gender = MemberEntry.GENDER_MALE;
                } else if (getResources().getStringArray(R.array.gender_array)[position].equals("female")) {
                    clubMember.setGenderIndex(MemberEntry.GENDER_FEMALE);
                    gender = MemberEntry.GENDER_FEMALE;
                } else {
                    clubMember.setGenderIndex(MemberEntry.GENDER_UNKNOWN);
                    gender = MemberEntry.GENDER_UNKNOWN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clubMember.setGenderIndex(MemberEntry.GENDER_UNKNOWN);
                gender = MemberEntry.GENDER_UNKNOWN;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        if (uriReceived == null){
            MenuItem menuItem = menu.findItem(R.id.delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveClubMember();
                return true;
            case R.id.delete:
                showDeleteDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveClubMember() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String kindOfSport = kindOfSportEditText.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "enter the first name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "enter the last name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(kindOfSport)) {
            Toast.makeText(this, "enter the kind of sport", Toast.LENGTH_SHORT).show();
            return;
        } else if (gender == MemberEntry.GENDER_UNKNOWN) {
            Toast.makeText(this, "choose the gender", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.FIRST_NAME, firstName);
        contentValues.put(MemberEntry.LAST_NAME, lastName);
        contentValues.put(MemberEntry.GENDER, clubMember.getGenderIndex());
        contentValues.put(MemberEntry.KIND_OF_SPORT, kindOfSport);

        ContentResolver contentResolver = getContentResolver();
        if (uriReceived == null) {
            Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);
            if (uri == null) {
                Toast.makeText(this, "Insertion data in the table is failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Insertion is successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            int rowsUpdated = contentResolver.update(uriReceived, contentValues, null, null);
            if (rowsUpdated == 0) {
                Toast.makeText(this, "Updating failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Updating is successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projections = {
                MemberEntry.FIRST_NAME,
                MemberEntry.LAST_NAME,
                MemberEntry.GENDER,
                MemberEntry.KIND_OF_SPORT
        };

        return new CursorLoader(getApplicationContext(), uriReceived, projections, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            String firstName = data.getString(data.getColumnIndexOrThrow(MemberEntry.FIRST_NAME));
            String lastName = data.getString(data.getColumnIndexOrThrow(MemberEntry.LAST_NAME));
            int gender = data.getInt(data.getColumnIndexOrThrow(MemberEntry.GENDER));
            String kindOfSport = data.getString(data.getColumnIndexOrThrow(MemberEntry.KIND_OF_SPORT));

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            genderSpinner.setSelection(gender);
            kindOfSportEditText.setText(kindOfSport);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void showDeleteDialog() {
        if (uriReceived != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to delete the club member");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteClubMember();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.create().show();
        }
    }

    private void deleteClubMember() {
        int rowDeleted = getContentResolver().delete(uriReceived, null, null);
        if (rowDeleted == 0) {
            Toast.makeText(this, "Deleting failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Club member is deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
