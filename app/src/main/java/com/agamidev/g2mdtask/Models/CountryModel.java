package com.agamidev.g2mdtask.Models;

public class CountryModel {
    int id;
    String name;
    String brief;

    public CountryModel(int id, String name, String brief){
        this.id = id;
        this.name = name;
        this.brief = brief;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBrief() {
        return brief;
    }
}
