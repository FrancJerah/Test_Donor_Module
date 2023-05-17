package com.group6.donor;

import android.widget.Filter;

import java.util.ArrayList;

public class DistributionCustomFilter extends Filter {

    DistributionAdapter distributionAdapter;
    ArrayList<DistributionClass> filterList;

    public DistributionCustomFilter(ArrayList<DistributionClass> filterList, DistributionAdapter distributionAdapter)
    {
        this.distributionAdapter = distributionAdapter;
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
            ArrayList<DistributionClass> filtererdDistribution =new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getRecipient().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filtererdDistribution.add(filterList.get(i));
                }
            }

            results.count= filtererdDistribution.size();
            results.values= filtererdDistribution;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        distributionAdapter.distribution = (ArrayList<DistributionClass>) results.values;

        //REFRESH
        distributionAdapter.notifyDataSetChanged();

    }
}