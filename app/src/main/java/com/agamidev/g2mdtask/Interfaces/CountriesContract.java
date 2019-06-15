package com.agamidev.g2mdtask.Interfaces;

import com.agamidev.g2mdtask.Models.CountryModel;

import java.util.ArrayList;

public interface CountriesContract {

    interface CountriesPresenterInterface{
        void addCountry(String name, String brief);
        void loadCountries(String row);
        void onDestroy();
    }

    interface CountriesView{
        void setDataToRecycler(ArrayList<CountryModel> countriesArrayList);
        void databaseFailure(String error);

        void countryAddStatus(Boolean status, String msg);
    }

    interface CountriesImp{
        interface OnDataAddedSuccessfully{
            void added(String msg);
            void errorAdding(String msg);
        }
        interface OnDataReadyListener{
            void loaded(ArrayList<CountryModel> countriesArrayList);
            void errorLoading(String error);
        }
        void addToDatabase(String name, String brief, OnDataAddedSuccessfully onDataAddedSuccessfully);
        void getDataFromDatabase(OnDataReadyListener onDataReadyListener, String row);
    }


}
