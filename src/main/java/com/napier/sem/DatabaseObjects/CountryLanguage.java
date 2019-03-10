package com.napier.sem.DatabaseObjects;

/**
 * CountryLanguage, same as in the database
 */
public class CountryLanguage {

    public CountryLanguage(){
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
