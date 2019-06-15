package com.agamidev.g2mdtask.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.agamidev.g2mdtask.Models.CountryModel;
import com.agamidev.g2mdtask.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder>  implements Filterable {

    private Context context;
    private ArrayList<CountryModel> countriesArrayList;
    private ArrayList<CountryModel> countriesListFiltered;


    public CountriesAdapter(Context context, ArrayList<CountryModel> countriesArrayList){
        this.context = context;
        this.countriesArrayList = countriesArrayList;
        this.countriesListFiltered = countriesArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_country_item, parent, false);

        return new CountriesAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final CountryModel c = countriesListFiltered.get(position);

        holder.tv_country_name.setText(c.getName());
        holder.tv_country_brief.setText(c.getBrief());



//        Glide.with(context)
//                .load(n.getUrlToImage())
//                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.load_image)
//                        .fitCenter())
//                .into(holder.iv_image);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerItemClickListener.onItemClick(countriesListFiltered.get(holder.getAdapterPosition()));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return countriesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countriesListFiltered = countriesArrayList;
                } else {
                    ArrayList<CountryModel> filteredList = new ArrayList<>();
                    for (CountryModel row : countriesArrayList) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    countriesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countriesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countriesListFiltered = (ArrayList<CountryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_country_name)
        TextView tv_country_name;
        @BindView(R.id.tv_country_brief)
        TextView tv_country_brief;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(MyViewHolder.this,itemView);
        }
    }
}
