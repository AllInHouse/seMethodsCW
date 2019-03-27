package com.napier.sem.DatabaseObjects;

import org.springframework.beans.TypeMismatchException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country class, same as in the database.
 */
public class Country implements DataObject{

    public Country(){
    }

    public boolean ParseRSET(ResultSet rset){
        boolean setSomething = false; //Using this to make sure something actually gets set
        try{
            this.Name = rset.getString("Code");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Code"); }

        try{
            this.Code = rset.getString("Name");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Name"); }

        try{
            this.Continent = rset.getString("Continent");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Continent"); }

        try{
            this.Region = rset.getString("Region");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Region"); }

        try{
            this.SurfaceArea = rset.getFloat("SurfaceArea");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: SurfaceArea"); }

        try{
            this.IndepYear = rset.getInt("IndepYear");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: IndepYear"); }

        try{
            this.Population = rset.getInt("Population");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Population"); }

        try{
            this.LifeExpectancy = rset.getFloat("LifeExpectancy");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: LifeExpectancy"); }

        try{
            this.GNP = rset.getFloat("GNP");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: GNP"); }

        try{
            this.GNPOld = rset.getFloat("GNPOld");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: GNPOld"); }

        try{
            this.LocalName = rset.getString("LocalName");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: LocalName"); }

        try{
            this.GovernemntForm = rset.getString("GovernmentForm");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: GovernmentForm"); }

        try{
            this.HeadOfState = rset.getString("HeadOfState");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: HeadOfState"); }

        try{
            this.Capital = rset.getInt("Capital");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Capital"); }

        try{
            this.Code2 = rset.getString("Code2");
            setSomething = true;
        } catch (SQLException sqlE) { System.out.println("Caught exception reading rset :: Code2"); }
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
    public int Population;

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
