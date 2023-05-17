package com.group6.donor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class DistributionEditorActivity extends AppCompatActivity {

    private Spinner mStatusSpinner;
    private EditText mRecipient, mDistributionLocation, mQuantity, mDistributionDate, mNotes;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();

    private int mStatus = 0;
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_APPROVED = 1;
    public static final int STATUS_REJECTED = 2;
    public static final int STATUS_IN_PROGRESS = 3;
    public static final int STATUS_DISTRIBUTED = 4;
    public static final int STATUS_COMPLETED = 5;
    public static final int STATUS_CANCELED = 6;

    private String Recipient, DistributionLocation, Quantity, Notes, picture, DistributionDate;
    private int DistributionID, Status;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_distribution);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecipient = findViewById(R.id.Recipient);
        mDistributionLocation = findViewById(R.id.DistributionLocation);
        mQuantity = findViewById(R.id.Quantity);
        mDistributionDate = findViewById(R.id.DistributionDate);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);
        mNotes = findViewById(R.id.Notes);

        mStatusSpinner = findViewById(R.id.Status);
        mDistributionDate = findViewById(R.id.DistributionDate);

        mDistributionDate.setFocusableInTouchMode(false);
        mDistributionDate.setFocusable(false);
        mDistributionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DistributionEditorActivity.this, date, myCalendar
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
        DistributionID = intent.getIntExtra("DistributionID", 0);
        Recipient = intent.getStringExtra("Recipient");
        DistributionLocation = intent.getStringExtra("DistributionLocation");
        Quantity = intent.getStringExtra("Quantity");
        DistributionDate = intent.getStringExtra("DistributionLocation");
        picture = intent.getStringExtra("picture");
        Notes = intent.getStringExtra("Notes");
        Status = intent.getIntExtra("Status", 0);

        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {

        if (DistributionID != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + Recipient.toString());

            mRecipient.setText(Recipient);
            mDistributionLocation.setText(DistributionLocation);
            mQuantity.setText(Quantity);
            mNotes.setText(Notes);
            mDistributionDate.setText(DistributionDate);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(DistributionEditorActivity.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

            switch (Status) {
                case STATUS_APPROVED:
                    mStatusSpinner.setSelection(1);
                    break;
                case STATUS_REJECTED:
                    mStatusSpinner.setSelection(2);
                    break;
                case STATUS_IN_PROGRESS:
                    mStatusSpinner.setSelection(3);
                    break;
                case STATUS_DISTRIBUTED:
                    mStatusSpinner.setSelection(4);
                    break;
                case STATUS_COMPLETED:
                    mStatusSpinner.setSelection(5);
                    break;
                case STATUS_CANCELED:
                    mStatusSpinner.setSelection(6);
                    break;
                default:
                    mStatusSpinner.setSelection(0);
                    break;
            }

        } else {
            getSupportActionBar().setTitle("Add an item");
        }
    }

    private void setupSpinner(){
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_status_options, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mStatusSpinner.setAdapter(statusSpinnerAdapter);

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Approved")) {
                        mStatus = STATUS_APPROVED;
                    } else if (selection.equals("Rejected")) {
                        mStatus = STATUS_REJECTED;
                    } else if (selection.equals("In Progress")) {
                        mStatus = STATUS_IN_PROGRESS;
                    } else if (selection.equals("Distributed")) {
                        mStatus = STATUS_DISTRIBUTED;
                    } else if (selection.equals("Completed")) {
                        mStatus = STATUS_COMPLETED;
                    } else if (selection.equals("Canceled")) {
                        mStatus = STATUS_CANCELED;
                    } else {
                        mStatus = STATUS_PENDING;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStatus = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (DistributionID == 0){

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
                imm.showSoftInput(mRecipient, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (DistributionID == 0) {

                    if (TextUtils.isEmpty(mRecipient.getText().toString()) ||
                            TextUtils.isEmpty(mDistributionLocation.getText().toString()) ||
                            TextUtils.isEmpty(mQuantity.getText().toString()) ||
                            TextUtils.isEmpty(mNotes.getText().toString()) ||
                            TextUtils.isEmpty(mDistributionDate.getText().toString()) ){
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

                    updateData("update", DistributionID);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(DistributionEditorActivity.this);
                dialog.setMessage("Delete this item?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", DistributionID, picture);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            setDistributionDate();
        }

    };

    private void setDistributionDate() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDistributionDate.setText(sdf.format(myCalendar.getTime()));
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

        String Recipient = mRecipient.getText().toString().trim();
        String DistributionLocation = mDistributionLocation.getText().toString().trim();
        String Quantity = mQuantity.getText().toString().trim();
        String Notes = mNotes.getText().toString().trim();
        int status = mStatus;
        String DistributionDate = mDistributionDate.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DistributionClass> call = apiInterface.insertDistribution(key, Recipient, DistributionLocation, Quantity, Notes, Status, DistributionDate, picture);

        call.enqueue(new Callback<DistributionClass>() {
            @Override
            public void onResponse(Call<DistributionClass> call, Response<DistributionClass> response) {

                progressDialog.dismiss();

                Log.i(DistributionEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(DistributionEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DistributionClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DistributionEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String Recipient = mRecipient.getText().toString().trim();
        String DistributionLocation = mDistributionLocation.getText().toString().trim();
        String Quantity = mQuantity.getText().toString().trim();
        String Notes = mNotes.getText().toString().trim();
        int status = mStatus;
        String DistributionDate = mDistributionDate.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DistributionClass> call = apiInterface.updateDistribution(key, id, Recipient, DistributionLocation, Quantity, Notes, Status, DistributionDate, picture);

        call.enqueue(new Callback<DistributionClass>() {
            @Override
            public void onResponse(Call<DistributionClass> call, Response<DistributionClass> response) {

                progressDialog.dismiss();

                Log.i(DistributionEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(DistributionEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DistributionEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DistributionClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DistributionEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id, final String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<DistributionClass> call = apiInterface.deleteDistribution(key, id, pic);

        call.enqueue(new Callback<DistributionClass>() {
            @Override
            public void onResponse(Call<DistributionClass> call, Response<DistributionClass> response) {

                progressDialog.dismiss();

                Log.i(DistributionEditorActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(DistributionEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DistributionEditorActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DistributionClass> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DistributionEditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void readMode(){

        mRecipient.setFocusableInTouchMode(false);
        mDistributionLocation.setFocusableInTouchMode(false);
        mQuantity.setFocusableInTouchMode(false);
        mNotes.setFocusableInTouchMode(false);
        mRecipient.setFocusable(false);
        mDistributionLocation.setFocusable(false);
        mQuantity.setFocusable(false);
        mNotes.setFocusable(false);

        mStatusSpinner.setEnabled(false);
        mDistributionDate.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        mRecipient.setFocusableInTouchMode(true);
        mDistributionLocation.setFocusableInTouchMode(true);
        mQuantity.setFocusableInTouchMode(true);
        mNotes.setFocusableInTouchMode(true);

        mStatusSpinner.setEnabled(true);
        mDistributionDate.setEnabled(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}
