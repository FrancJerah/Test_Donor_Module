package com.group6.donor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorEditorActivity extends AppCompatActivity {

    private Spinner mGenderSpinner;
    private EditText mName, mAddress, mContactNumber, mBirth, mEmail;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();

    private int mGender = 0;
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    private String name, Address, ContactNumber, picture, birth, Email;
    private int DonorID, gender;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_donor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.Address);
        mContactNumber = findViewById(R.id.ContactNumber);
        mBirth = findViewById(R.id.birth);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);
        mEmail = findViewById(R.id.Email);

        mGenderSpinner = findViewById(R.id.gender);
        mBirth = findViewById(R.id.birth);

        mBirth.setFocusableInTouchMode(false);
        mBirth.setFocusable(false);
        mBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DonorEditorActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        setupSpinner();

        Intent intent = getIntent();
        DonorID = intent.getIntExtra("DonorID", 0);
        name = intent.getStringExtra("name");
        Address = intent.getStringExtra("Address");
        ContactNumber = intent.getStringExtra("ContactNumber");
        birth = intent.getStringExtra("birth");
        picture = intent.getStringExtra("picture");
        Email = intent.getStringExtra("Email");
        gender = intent.getIntExtra("gender", 0);

        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {

        if (DonorID != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + name.toString());

            mName.setText(name);
            mAddress.setText(Address);
            mContactNumber.setText(ContactNumber);
            mEmail.setText(Email);
            mBirth.setText(birth);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(DonorEditorActivity.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

            switch (gender) {
                case GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }

        } else {
            getSupportActionBar().setTitle("Add a Pet");
        }
    }

    private void setupSpinner(){
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else {
                        mGender = GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (DonorID == 0){

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (DonorID == 0) {

                    if (TextUtils.isEmpty(mName.getText().toString()) ||
                            TextUtils.isEmpty(mAddress.getText().toString()) ||
                            TextUtils.isEmpty(mContactNumber.getText().toString()) ||
                            TextUtils.isEmpty(mEmail.getText().toString()) ||
                            TextUtils.isEmpty(mBirth.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {

                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();

                    }

                } else {

                    updateData("update", DonorID);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(DonorEditorActivity.this);
                dialog.setMessage("Delete this pet?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", DonorID, picture);
                    }
                });
                dialog.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };

    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBirth.setText(sdf.format(myCalendar.getTime()));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String name = mName.getText().toString().trim();
        String Address = mAddress.getText().toString().trim();
        String ContactNumber = mContactNumber.getText().toString().trim();
        String Email = mEmail.getText().toString().trim();
        int gender = mGender;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DonorsClass> call = apiInterface.insertDonor(key, name, Address, ContactNumber, Email, gender, birth, picture);

        call.enqueue(new Callback<DonorsClass>() {
            @Override
            public void onResponse(Call<DonorsClass> call, Response<DonorsClass> response) {

                progressDialog.dismiss();

                Log.i(DonorEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(DonorEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DonorsClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DonorEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String name = mName.getText().toString().trim();
        String Address = mAddress.getText().toString().trim();
        String ContactNumber = mContactNumber.getText().toString().trim();
        String Email = mEmail.getText().toString().trim();
        int gender = mGender;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DonorsClass> call = apiInterface.updateDonor(key, id, name, Address, ContactNumber, Email, gender, birth, picture);

        call.enqueue(new Callback<DonorsClass>() {
            @Override
            public void onResponse(Call<DonorsClass> call, Response<DonorsClass> response) {

                progressDialog.dismiss();

                Log.i(DonorEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(DonorEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DonorEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DonorsClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DonorEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DonorsClass> call = apiInterface.deleteDonor(key, id, pic);

        call.enqueue(new Callback<DonorsClass>() {
            @Override
            public void onResponse(Call<DonorsClass> call, Response<DonorsClass> response) {

                progressDialog.dismiss();

                Log.i(DonorEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(DonorEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DonorEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DonorsClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DonorEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void readMode(){

        mName.setFocusableInTouchMode(false);
        mAddress.setFocusableInTouchMode(false);
        mContactNumber.setFocusableInTouchMode(false);
        mEmail.setFocusableInTouchMode(false);
        mName.setFocusable(false);
        mAddress.setFocusable(false);
        mContactNumber.setFocusable(false);
        mEmail.setFocusable(false);

        mGenderSpinner.setEnabled(false);
        mBirth.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        mName.setFocusableInTouchMode(true);
        mAddress.setFocusableInTouchMode(true);
        mContactNumber.setFocusableInTouchMode(true);
        mEmail.setFocusableInTouchMode(true);

        mGenderSpinner.setEnabled(true);
        mBirth.setEnabled(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}
