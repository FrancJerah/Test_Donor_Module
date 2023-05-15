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

public class InventoryActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InventoryAdapter inventoryAdapter;
    private List<InventoryClass> inventoryClassList;
    ApiInterface apiInterface;
    InventoryAdapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new InventoryAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(InventoryActivity.this, InventoryEditorActivity.class);
                intent.putExtra("ItemID", inventoryClassList.get(position).getItemID());
                intent.putExtra("ItemName", inventoryClassList.get(position).getItemName());
                intent.putExtra("Quantity", inventoryClassList.get(position).getQuantity());
                intent.putExtra("Description", inventoryClassList.get(position).getDescription());
                intent.putExtra("picture", inventoryClassList.get(position).getPicture());
                intent.putExtra("Expiration", inventoryClassList.get(position).getExpiration());
                startActivity(intent);

            }

//            @Override
//            public void onLoveClick(View view, int position) {
//
//            }

//            @Override
//            public void onLoveClick(View view, int position) {
//
//                final int id = inventoryClassList.get(position).getItemID();
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
                    startActivity(new Intent(InventoryActivity.this, InventoryEditorActivity.class));
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
        searchView.setQueryHint("Search Inventory...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                inventoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                inventoryAdapter.getFilter().filter(newText);
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

    public void getInventory(){

        Call<List<InventoryClass>> call = apiInterface.getInventory();
        call.enqueue(new Callback<List<InventoryClass>>() {
            @Override
            public void onResponse(Call<List<InventoryClass>> call, Response<List<InventoryClass>> response) {
                progressBar.setVisibility(View.GONE);
                inventoryClassList = response.body();
                Log.i(InventoryActivity.class.getSimpleName(), response.body().toString());
                inventoryAdapter = new InventoryAdapter(inventoryClassList, InventoryActivity.this, listener);
                recyclerView.setAdapter(inventoryAdapter);
                inventoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<InventoryClass>> call, Throwable t) {
                Toast.makeText(InventoryActivity.this,
                        "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void updateLove(final String key, final int id, final Boolean love){
//
////        Call<InventoryClass> call = apiInterface.updateLove(key, id, love);
//
//        call.enqueue(new Callback<InventoryClass>() {
//            @Override
//            public void onResponse(Call<InventoryClass> call, Response<InventoryClass> response) {
//
//                Log.i(InventoryActivity.class.getSimpleName(), "Response "+response.toString());
//
//                String value = response.body().getValue();
//                String message = response.body().getMassage();
//
//                if (value.equals("1")){
//                    Toast.makeText(InventoryActivity.this, message, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(InventoryActivity.this, message, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<InventoryClass> call, Throwable t) {
//                Toast.makeText(InventoryActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getInventory();
    }

}
