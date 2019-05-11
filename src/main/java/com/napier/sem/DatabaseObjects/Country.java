package com.napier.sem.DatabaseObjects;

import com.napier.sem.App;
import org.springframework.beans.TypeMismatchException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country class, same as in the database.
 */
public class Country implements DataObject{

    public Country(){
    }

    /**
     * Quick way to assign the data returned from a query for the Country table
     * @param rset ResultSet object from the database
     * @return true if anything has been updated, false if nothing has been changed or rset is null
     */
    public boolean ParseRSET(ResultSet rset){
        if(rset == null){
            App.log.warn("Country RSET is null, returning false.");
            return false;
        }

        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.Code = rset.getString("Code");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Code"); }

        try{
            this.Name = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Name"); }

        try{
            this.Continent = rset.getString("Continent");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Continent"); }

        try{
            this.Region = rset.getString("Region");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Region"); }

        try{
            this.SurfaceArea = rset.getFloat("SurfaceArea");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: SurfaceArea"); }

        try{
            this.IndepYear = rset.getInt("IndepYear");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: IndepYear"); }

        try{
            this.Population = rset.getLong("Population");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Population"); }

        try{
            this.LifeExpectancy = rset.getFloat("LifeExpectancy");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: LifeExpectancy"); }

        try{
            this.GNP = rset.getFloat("GNP");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: GNP"); }

        try{
            this.GNPOld = rset.getFloat("GNPOld");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: GNPOld"); }

        try{
            this.LocalName = rset.getString("LocalName");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: LocalName"); }

        try{
            this.GovernemntForm = rset.getString("GovernmentForm");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: GovernmentForm"); }

        try{
            this.HeadOfState = rset.getString("HeadOfState");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: HeadOfState"); }

        try{
            this.Capital = rset.getInt("Capital");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Capital"); }

        try{
            this.Code2 = rset.getString("Code2");
            setSomething = true;
        } catch (SQLException sqlE) { App.log.trace("Column does not exist in RSET :: Code2"); }
        return setSomething;
    }

    /**
     * The country code
     */
    public String Code;

    /**
     * The country name
     */
    public String Name;


    ///** This will probably get changed... .. Not the best way to do it*/
    //public enum Continents { ASIA, EUROPE;}
    /** Country continent -> Could possibly be an enum as above */
    public String Continent;

    /**
     * Country Region
     */
    public String Region;

    /**
     * Country SurfaceArea
     */
    public Float SurfaceArea;

    /**
     * Country IndepYear
     */
    public int IndepYear;

    /**
     * Country Population
     */
    public Long Population;

    /**
     * Country LifeExpectancy
     */
    public Float LifeExpectancy;

    /**
     * Country GNP
     */
    public Float GNP;

    /**
     * Country GNPOld
     */
    public Float GNPOld;

    /**
     * Country LocalName
     */
    public String LocalName;

    /**
     * Country GovernmentForm
     */
    public String GovernemntForm;

    /**
     * Country HeadOfState
     */
    public String HeadOfState;

    /**
     * Country Capital
     * In form of ID number
     */
    public int Capital;

    /**
     * Country 2 letter code
     */
    public String Code2;
}
