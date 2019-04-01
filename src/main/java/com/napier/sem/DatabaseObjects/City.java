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

    public boolean ParseRSET(ResultSet rset){
        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.ID = rset.getInt("ID");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: ID"); }

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: Name"); }

        try{
            this.CountryCode = rset.getString("CountryCode");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: CountryCode"); }

        try{
            this.District = rset.getString("District");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: District"); }

        try{
            this.Population = rset.getInt("Population");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.debug("Column does not exist in RSET :: Population"); }
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
     * City District
     */
    public String District;

    /**
     * City Population
     */
    public int Population;

}
