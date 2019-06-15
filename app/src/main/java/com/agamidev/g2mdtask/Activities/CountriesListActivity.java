package com.agamidev.g2mdtask.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.agamidev.g2mdtask.Adapters.CountriesAdapter;
import com.agamidev.g2mdtask.Interfaces.CountriesContract;
import com.agamidev.g2mdtask.CountriesListFiles.CountriesInteractorImp;
import com.agamidev.g2mdtask.CountriesListFiles.CountriesPresenter;
import com.agamidev.g2mdtask.Models.CountryModel;
import com.agamidev.g2mdtask.CountriesListFiles.DatabaseManager;
import com.agamidev.g2mdtask.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountriesListActivity extends AppCompatActivity implements CountriesContract.CountriesView {


    @BindView(R.id.rv_countries)
    RecyclerView rv_countries;
    @BindView(R.id.et_country_name)
    EditText et_country_name;
    @BindView(R.id.et_country_brief)
    EditText et_country_brief;

    CountriesAdapter mCountriesAdapter;

    DatabaseManager mDatabaseManager;

    CountriesPresenter presenter;

    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_list);
        ButterKnife.bind(this);
        init();
        presenter = new CountriesPresenter(this,new CountriesInteractorImp(mDatabaseManager));
        loadCountries("%");
    }

    private void init() {
        mDatabaseManager = new DatabaseManager(this);
        firstTime = true;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CountriesListActivity.this);
        rv_countries.setLayoutManager(layoutManager);
    }

    public void loadCountries(String countryName){
        presenter.loadCountries(countryName);
    }

    public void addCountry(View view) {
        String countryName = et_country_name.getText().toString();
        String countryBrief = et_country_brief.getText().toString();
        if (countryName.length()>0 && countryBrief.length()>0) {
            presenter.addCountry(countryName, countryBrief);
        }else {
            Toast.makeText(getApplicationContext(),"Fill Spaces!",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView sv = (SearchView) menu.findItem(R.id.search_countries).getActionView();
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length()!=0){
                    loadCountries("%" + query + "%");
                }else{
                    loadCountries("%");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0){
                    loadCountries("%" + newText + "%");
                }else{
                    loadCountries("%");
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setDataToRecycler(ArrayList<CountryModel> nCountriesArrayList) {
        if (firstTime) {
            mCountriesAdapter = new CountriesAdapter(this, nCountriesArrayList);
            rv_countries.setAdapter(mCountriesAdapter);
            firstTime = false;
        }else {
            mCountriesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void databaseFailure(String error) {
        Toast.makeText(getApplicationContext(), error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void countryAddStatus(Boolean status, String msg) {
        if (status){
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
            et_country_name.setText("");
            et_country_brief.setText("");
        }else {
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
