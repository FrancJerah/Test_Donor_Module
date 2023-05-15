package com.group6.donor;

import android.widget.Filter;

import java.util.ArrayList;

public class DonorCustomFilter extends Filter {

    DonorAdapter donorAdapter;
    ArrayList<DonorsClass> filterList;

    public DonorCustomFilter(ArrayList<DonorsClass> filterList, DonorAdapter donorAdapter)
    {
        this.donorAdapter = donorAdapter;
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
            ArrayList<DonorsClass> filteredDonors =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredDonors.add(filterList.get(i));
                }
            }

            results.count= filteredDonors.size();
            results.values= filteredDonors;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        donorAdapter.donors = (ArrayList<DonorsClass>) results.values;

        //REFRESH
        donorAdapter.notifyDataSetChanged();

    }
}