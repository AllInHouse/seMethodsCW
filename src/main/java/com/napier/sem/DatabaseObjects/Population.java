package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Population implements DataObject{

    public Population(){
    }

    /**
     * Quick way to assign the data returned from a query for the City table
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
            this.TotalPopulation = rset.getInt("TotalPopulation");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopulation"); }

        try{
            this.TotalPopInCities = rset.getInt("TotalPopInCities");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopInCities"); }

        try{
            this.TotalPopNotInCities = rset.getInt("TotalPopNotInCities");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: TotalPopNotInCities"); }

        return setSomething;
    }

    /**
     * Name of the location
     */
    public String Name;

    /**
     * Total population
     */
    public int TotalPopulation;

    /**
     * Total Population Living in Cities
     */
    public int TotalPopInCities;

    /**
     * Total Population Living in Cities Percent
     */
    public String TotalPopInCitiesPercent = (TotalPopInCities / TotalPopulation)*100 + "%" ;

    /**
     * Total Population not living in cities
     */
    public int TotalPopNotInCities;

    /**
     * Total Population Not Living in Cities Percent
     */
    public String TotalPopNotInCitiesPercent = (TotalPopNotInCities / TotalPopulation)*100 + "%" ;

}
