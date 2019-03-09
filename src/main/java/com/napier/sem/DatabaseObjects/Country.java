package com.napier.sem.DatabaseObjects;

/**
 * Country class, same as in the database.
 */
public class Country {

    public Country(){
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
