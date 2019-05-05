package com.napier.sem.DatabaseObjects.ExtendedObjects;

import com.napier.sem.App;
import com.napier.sem.DatabaseObjects.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Add the city population column to a base of Country
 */
public class CountryCityPopulation extends Country {

    public int CityPopulation;

    @Override
    public boolean ParseRSET(ResultSet rset) {
        boolean setSomething = super.ParseRSET(rset);
        try{
            this.CityPopulation = rset.getInt("CityPopulation");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: CityPopulation"); }
        return setSomething;
    }
}