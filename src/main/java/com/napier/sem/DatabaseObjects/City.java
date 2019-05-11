package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * City class, same as in the database
 */
public class City implements DataObject{

    public City(){
    }

    /**
     * Quick way to assign the data returned from a query for the City table
     * @param rset ResultSet object from the database
     * @return true if anything has been updated, false if nothing has been changed or rset is null
     */
    public boolean ParseRSET(ResultSet rset){
        if(rset == null){
            App.log.warn("City RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.ID = rset.getInt("ID");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: ID"); }

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Name"); }

        try{
            this.CountryCode = rset.getString("CountryCode");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: CountryCode"); }

        try{
            this.Country = rset.getString("Country");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Country"); }

        try{
            this.District = rset.getString("District");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: District"); }

        try{
            this.Region = rset.getString("Region");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Region"); }

        try{
            this.Population = rset.getLong("Population");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Population"); }
        return setSomething;
    }

    /**
     * City ID
     */
    public int ID;

    /**
     * City Name
     */
    public String Name;

    /**
     * City CountryCode
     */
    public String CountryCode;

    /**
     * Country name to make some queries easier
     */
    public String Country;

    /**
     * City District
     */
    public String District;

    /**
     * Region to make some queries easier
     */
    public String Region;

    /**
     * City Population
     */
    public Long Population;

}
