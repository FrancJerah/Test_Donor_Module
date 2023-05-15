package com.group6.donor;

import android.widget.Filter;

import java.util.ArrayList;

public class InventoryCustomFilter extends Filter {

    InventoryAdapter inventoryAdapter;
    ArrayList<InventoryClass> filterList;

    public InventoryCustomFilter(ArrayList<InventoryClass> filterList, InventoryAdapter inventoryAdapter)
    {
        this.inventoryAdapter = inventoryAdapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<InventoryClass> filteredInventory =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getItemName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredInventory.add(filterList.get(i));
                }
            }

            results.count= filteredInventory.size();
            results.values= filteredInventory;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        inventoryAdapter.inventory = (ArrayList<InventoryClass>) results.values;

        //REFRESH
        inventoryAdapter.notifyDataSetChanged();

    }
}