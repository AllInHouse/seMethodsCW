package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CapitalCity implements DataObject{

    public CapitalCity(){
    }

    public boolean ParseRSET(ResultSet rset) {
        if(rset == null){
            App.log.warn("CapitalCity RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Name"); }

        try{
            this.Country = rset.getString("Country");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Country"); }

        try{
            this.Continent = rset.getString("Continnt");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Continent"); }

        try{
            this.Population = rset.getLong("Population");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Population"); }
        return setSomething;
    }

    /**
     * The name of the capital city
     */
    public String Name;

    /**
     * Country the capital city is in
     */
    public String Country;

    /**
     * Continent the capital city is in
     */
    public String Continent;

    /**
     * Population of the capital city
     */
    public Long Population;

}
