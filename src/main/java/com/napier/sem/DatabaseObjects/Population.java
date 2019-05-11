package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Population implements DataObject{

    public Population(){
    }

    /**
     * Quick way to assign the data returned from a population query
     * @param rset ResultSet object from the database
     * @return true if anything has been updated, false if nothing has been changed or rset is null
     */
    public boolean ParseRSET(ResultSet rset){
        if(rset == null){
            App.log.warn("Population RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Name"); }

        try{
            this.TotalPopulation = rset.getLong("TotalPopulation");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopulation"); }

        try{
            this.TotalPopInCities = rset.getLong("TotalPopInCities");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopInCities"); }

        try{
            this.TotalPopNotInCities = rset.getLong("TotalPopNotInCities");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopNotInCities"); }

        UpdateCalculatedValues(); //Update the percent values based on any data from the RSET.

        return setSomething;
    }

    /**
     * Name of the location
     */
    public String Name;

    /**
     * Total population
     */
    public Long TotalPopulation;

    /**
     * Total Population Living in Cities
     */
    public Long TotalPopInCities;

    /**
     * Total Population Living in Cities Percent
     */
    public String TotalPopInCitiesPercent;


    private String getTotalPopInCitiesPercent(){
        if(TotalPopInCities == 0 || TotalPopulation == 0) return "0%";
        else return (TotalPopInCities / TotalPopulation)*100 + "%" ;
    }

    /**
     * Total Population not living in cities
     */
    public Long TotalPopNotInCities;

    /**
     * Total Population Not Living in Cities Percent
     */
    public String TotalPopNotInCitiesPercent;

    private String getTotalPopNotInCitiesPercent(){
        if(TotalPopNotInCities == 0 || TotalPopulation == 0) return "0%";
        else return (TotalPopNotInCities / TotalPopulation)*100 + "%" ;
    }

    public void UpdateCalculatedValues(){
        TotalPopNotInCitiesPercent = getTotalPopNotInCitiesPercent();
        TotalPopInCitiesPercent = getTotalPopInCitiesPercent();
    }

}
