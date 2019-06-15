package com.agamidev.g2mdtask.CountriesListFiles;

import com.agamidev.g2mdtask.Interfaces.CountriesContract;
import com.agamidev.g2mdtask.Models.CountryModel;

import java.util.ArrayList;

public class CountriesPresenter implements CountriesContract.CountriesPresenterInterface
        ,CountriesContract.CountriesImp.OnDataReadyListener, CountriesContract.CountriesImp.OnDataAddedSuccessfully {

    private CountriesContract.CountriesImp countriesInteractor;
    private CountriesContract.CountriesView countriesView;

    public CountriesPresenter(CountriesContract.CountriesView countriesView, CountriesContract.CountriesImp countriesInteractor){
        this.countriesInteractor = countriesInteractor;
        this.countriesView = countriesView;
    }

    @Override
    public void addCountry(String name, String brief) {
        countriesInteractor.addToDatabase(name, brief,this);
    }

    @Override
    public void loadCountries(String row) {
        countriesInteractor.getDataFromDatabase(this, row);
    }

    @Override
    public void loaded(ArrayList<CountryModel> countriesArrayList) {
        countriesView.setDataToRecycler(countriesArrayList);
    }

    @Override
    public void errorLoading(String error) {
        countriesView.databaseFailure(error);
    }

    @Override
    public void added(String msg) {
        countriesInteractor.getDataFromDatabase(this, "%");
        countriesView.countryAddStatus(true,msg);
    }

    @Override
    public void errorAdding(String msg) {
        countriesView.countryAddStatus(false,msg);
    }
}
