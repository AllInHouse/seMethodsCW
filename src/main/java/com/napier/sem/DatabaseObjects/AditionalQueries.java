package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AditionalQueries implements DataObject {


    /**
     * Quick way to assign the data returned from a query for the aditional queries
     * @param rset ResultSet object from the database
     * @return true if anything has been updated, false if nothing has been changed or rset is null
     */
    public boolean ParseRSET(ResultSet rset){
        if(rset == null){
            App.log.warn("AditionalQueries RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.Population = rset.getLong("Population");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Population"); }

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Name"); }

        return setSomething;
    }

    /**
     * Name of the object being queried
     */
    public String Name;

    /**
     * The Population
     */
    public long Population;
}
