package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CountryLanguage, same as in the database
 */
public class CountryLanguage implements DataObject{

    public CountryLanguage(){
    }

    /**
     * Quick way to assign the data returned from a query for the CountryLanguage table
     * @param rset ResultSet object from the database
     * @return true if anything has been updated, false if nothing has been changed or rset is null
     */
    public boolean ParseRSET(ResultSet rset){
        if(rset == null){
            App.log.warn("CountryLanguage RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.CountryCode = rset.getString("CountryCode");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: CountryCode"); }

        try{
            this.Language = rset.getString("Language");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: Language"); }

        try{
            this.IsOfficial = rset.getBoolean("IsOfficial");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: isOfficial"); }

        try{
            this.Percentage = rset.getFloat("Percentage");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: Percentage"); }
        return setSomething;
    }

    /**
     * CountryLanguage CountryCode
     */
    public String CountryCode;

    /**
     * CountryLanguage Language
     */
    public String Language;

    /**
     * CountryLanguage IsOfficial
     * In DB this is an enum of 'T' or 'F', boolean would be easier?
     */
    public boolean IsOfficial;

    /**
     * CountryLanguage Percentage
     */
    public Float Percentage;
}
