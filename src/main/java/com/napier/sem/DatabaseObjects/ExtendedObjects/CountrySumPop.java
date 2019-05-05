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
        boolean setSomething = super.ParseRSET(rset);
        try{
            this.SumPop = rset.getInt("SumPop");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: SumPop"); }
        return setSomething;
    }
}