package com.group6.donor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributionActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DistributionAdapter distributionAdapter;
    private List<DistributionClass> distributionClassList;
    ApiInterface apiInterface;
    DistributionAdapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new DistributionAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(DistributionActivity.this, DistributionEditorActivity.class);
                intent.putExtra("DistributionID", distributionClassList.get(position).getDistributionID());
                intent.putExtra("Recipient", distributionClassList.get(position).getRecipient());
                intent.putExtra("DistributionLocation", distributionClassList.get(position).getDistributionLocation());
                intent.putExtra("Quantity", distributionClassList.get(position).getQuantity());
                intent.putExtra("Notes", distributionClassList.get(position).getNotes());
                intent.putExtra("Status", distributionClassList.get(position).getStatus());
                intent.putExtra("picture", distributionClassList.get(position).getPicture());
                intent.putExtra("DistributionDate", distributionClassList.get(position).getDistributionDate());
                startActivity(intent);

            }

//            @Override
//            public void onLoveClick(View view, int position) {
//
//                final int id = donorsClassList.get(position).getDonorID();
//                final Boolean love = donorsClassList.get(position).getLove();
//                final ImageView mLove = view.findViewById(R.id.love);
//
//                if (love){
//                    mLove.setImageResource(R.drawable.likeof);
//                    donorsClassList.get(position).setLove(false);
//                    updateLove("update_love", id, false);
//                    donorAdapter.notifyDataSetChanged();
//                } else {
//                    mLove.setImageResource(R.drawable.likeon);
//                    donorsClassList.get(position).setLove(true);
//                    updateLove("update_love", id, true);
//                    donorAdapter.notifyDataSetChanged();
//                }
//
//            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DistributionActivity.this, DistributionEditorActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Recipient...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                distributionAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                distributionAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDistribution(){

        Call<List<DistributionClass>> call = apiInterface.getDistribution();
        call.enqueue(new Callback<List<DistributionClass>>() {
            @Override
            public void onResponse(Call<List<DistributionClass>> call, Response<List<DistributionClass>> response) {
                progressBar.setVisibility(View.GONE);
                distributionClassList = response.body();
                Log.i(DistributionActivity.class.getSimpleName(), response.body().toString());
                distributionAdapter = new DistributionAdapter(distributionClassList, DistributionActivity.this, listener);
                recyclerView.setAdapter(distributionAdapter);
                distributionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DistributionClass>> call, Throwable t) {
                Toast.makeText(DistributionActivity.this, "rp :"+
                        t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void updateLove(final String key, final int id, final Boolean love){
//
//        Call<DonorsClass> call = apiInterface.updateLove(key, id, love);
//
//        call.enqueue(new Callback<DonorsClass>() {
//            @Override
//            public void onResponse(Call<DonorsClass> call, Response<DonorsClass> response) {
//
//                Log.i(DistributionActivity.class.getSimpleName(), "Response "+response.toString());
//
//                String value = response.body().getValue();
//                String message = response.body().getMassage();
//
//                if (value.equals("1")){
//                    Toast.makeText(DistributionActivity.this, message, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(DistributionActivity.this, message, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DonorsClass> call, Throwable t) {
//                Toast.makeText(DistributionActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getDistribution();
    }

}
