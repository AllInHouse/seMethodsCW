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

    public boolean ParseRSET(ResultSet rset){
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
