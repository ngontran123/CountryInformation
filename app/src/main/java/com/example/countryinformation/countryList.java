package com.example.countryinformation;
import android.content.Context;
import android.view.*;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class countryList extends BaseAdapter implements countryListIF {
    Context context;
    List<Country> countryList;
    List<Country> countryListCopy;
    public countryList(Context context, List<Country> countryList,List<Country> coutnryListCopy) {
        this.context = context;
        this.countryList = countryList;
        this.countryListCopy=coutnryListCopy;
    }


    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Country getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.country_list, parent, false);
        final Country country = countryList.get(position);
        ImageView img = (ImageView) v.findViewById(R.id.countryId);
        TextView countryName = (TextView) v.findViewById(R.id.countryName);
        countryName.setText(country.getCountryName());
        String imageUrl = country.getImgUrl();
        InputStream inputStream = null;
        Picasso.get().load(imageUrl).into(img);
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.count = countryList.size();
                    results.values = countryList;
                } else {
                    List<Country> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Country s : countryListCopy) {
                        if (s.getCountryName().toUpperCase().contains(searchStr)) {
                            resultsData.add(s);
                        }
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryList = (ArrayList<Country>) results.values;
                countryListCopy=countryListCopy;
                notifyDataSetChanged();
            }


        };

    }
}
