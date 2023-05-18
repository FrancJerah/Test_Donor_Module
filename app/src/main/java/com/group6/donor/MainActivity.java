package com.group6.donor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private CardView donorCard;
    private CardView inventoryCard;
    private CardView distributionCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donorCard = findViewById(R.id.donor_card);
        inventoryCard = findViewById(R.id.inventory_card);
        distributionCard = findViewById(R.id.distribution_card);

        donorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DonorsActivity
                Intent intent = new Intent(MainActivity.this, DonorActivity.class);
                startActivity(intent);
            }
        });

        inventoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start InventoryActivity
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        distributionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DistributionsActivity
                Intent intent = new Intent(MainActivity.this, DistributionActivity.class);
                startActivity(intent);
            }
        });
    }
}
