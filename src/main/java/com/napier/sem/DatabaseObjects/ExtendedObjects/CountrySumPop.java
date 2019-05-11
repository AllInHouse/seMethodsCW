package com.napier.sem.DatabaseObjects.ExtendedObjects;

import com.napier.sem.App;
import com.napier.sem.DatabaseObjects.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Add the column SumPop to a base of Country
 */
public class CountrySumPop extends Country {

    public int SumPop;

    @Override
    public boolean ParseRSET(ResultSet rset) {
        if(rset == null){
            App.log.warn("CountrySumPop RSET is null, returning false.");
            return false;
        }

        boolean setSomething = super.ParseRSET(rset);
        try{
            this.SumPop = rset.getInt("SumPop");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: SumPop"); }
        return setSomething;
    }
}